package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class that stores the status of the stock.
 */
public class ModelImp implements Model {

  private Map<Stock, Integer> portfolio = new HashMap<>();

  private List<String[]> dataList = getStockData("msft");

  private int dateIndex;

  // represents the stock symbols.
  ArrayList<String> portfolioKeys = new ArrayList<>();

  // represents the value of the portfolio.
  double portfolioValue = 0;

  // represents the number of shares.
  ArrayList<Integer> shares = new ArrayList<>();

  // represents the closing prices.
  ArrayList<Double> closingPrices = new ArrayList<>();

  ArrayList<Double> values = new ArrayList<>();
  ArrayList<String> message = new ArrayList<>();

  //used in rebalance
  private ArrayList<Integer> percents = new ArrayList<Integer>();
  ArrayList<Stock> unitedStocks = new ArrayList<Stock>();
  ArrayList<Integer> unitedStocksShares = new ArrayList<Integer>();




  /**
   * Gets the gain or loss of the stock.
   * @param stockSymbol The symbol of the stock.
   * @param startDate The start date for the comparison.
   * @param endDate The end date for the comparison.
   * @return Whether the price change was a gain or loss.
   */
  public double[] getGainOrLoss(String stockSymbol, String startDate, String endDate)
          throws IllegalArgumentException {
    //call getDataForStocks on this symbol. Should throw exception if invalid symbol.
    String stockData = getDataForStocks(stockSymbol);
    saveToCSVFile(stockData);
    List<String[]> dataList = readCSVFile("output.csv");

    int startDateIndex = getDateIndex(startDate, dataList);
    int endDateIndex = getDateIndex(endDate, dataList);

    if (startDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid start date.");
    } else if (endDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid end date.");
    } else if (getDateIndex(startDate, dataList) > getDateIndex(endDate, dataList)) {
      throw new IllegalArgumentException("Error: Start date cannot be later than end date!");
    } else {
      double[] startAndFinalPrices = new double[2];
      startAndFinalPrices[0] = Double.parseDouble(dataList.get(startDateIndex)[4]);
      startAndFinalPrices[1] = Double.parseDouble(dataList.get(endDateIndex)[4]);

      return startAndFinalPrices;
    }
  }

  /**
   * Gets the average of all the stocks from the amount of daysBefore to the date.
   * @param stockSymbol The symbol of the stock.
   * @param date The date of the stock.
   * @param daysBefore How many days before the date we should calculate the stock.
   * @return The average of the prices for all days from the amount of daysBefore to the date.
   */
  public double getXDayAverage(String stockSymbol, String date, int daysBefore)
          throws IllegalArgumentException {
    String stockData = getDataForStocks(stockSymbol);
    saveToCSVFile(stockData);
    List<String[]> dataList = readCSVFile("output.csv");
    this.dataList = dataList;

    double sum = 0;
    int dateIndex = getDateIndex(date, dataList);
    this.dateIndex = dateIndex;

    if (daysBefore < 0) {
      throw new IllegalArgumentException("Error: Cannot go back negative days.");
    } else if (dateIndex == -1) {
      throw new IllegalArgumentException("Error: Entered invalid or non-existent date");
    } else if (dateIndex + daysBefore > dataList.size()) {
      throw new IllegalArgumentException("Error: Number of days before goes beyond the data");
    } else {
      for (int i = dateIndex; i < dateIndex + daysBefore; i++) {
        sum += Double.parseDouble(dataList.get(i)[4]);
      }
    }
    return sum / daysBefore;
  }

  /**
   * Gets the amount of days that are crossovers.
   * @param stockSymbol The symbol of the stock.
   * @param date The date of the stock.
   * @param daysBefore How many days before the date we should calculate the stock.
   * @return All the x-day crossovers.
   * @throws IllegalArgumentException If the x-day average is invalid or does not exist.
   */
  public List<String[]> getXDayCrossovers(String stockSymbol, String date, int daysBefore)
          throws IllegalArgumentException {

    List<String[]> listOfCrossovers = new ArrayList<>();
    /*
    Double average is set to x-day average. It gets the average, or throws exceptions.
     */
    double average = getXDayAverage(stockSymbol, date, daysBefore);

    for (int i = dateIndex; i < dateIndex + daysBefore; i++) {
      if (Double.parseDouble(dataList.get(i)[4]) > average) {
        listOfCrossovers.add(dataList.get(i));
      }
    }
    return listOfCrossovers;
  }

  /**
   * Creates the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares each stock has.
   */
  public void createPortfolio(String stockSymbol, String date, int numberOfShares) {
    portfolio.put(new Stock(stockSymbol, date), numberOfShares);
    portfolioKeys.add(stockSymbol);
    shares.add(numberOfShares);
  }

  /**
   * Purchase a specific number of shares of a specific stock on a specified date,
   *     and add them to the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares to buy.
   * @param date The date of the stock.
   */
  public void purchaseShares(String stockSymbol, int numberOfShares, String date) {
    ifStatement(stockSymbol, date, numberOfShares);
  }

  /**
   * Sell a specific number of shares of a specific stock
   *     on a specified date from a given portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares to sell.
   * @param date The date of the stock.
   */
  public void sellShares(String stockSymbol, int numberOfShares, String date) {
    if (portfolio.isEmpty()) {
      throw new IllegalArgumentException("Error: Empty portfolio");
    }

    System.out.println("getStock: " + getStock(stockSymbol, date));
    System.out.println("Number of shares: " + numberOfShares);
    System.out.println("Shares list: " + shares);
    System.out.println("Portfolio: " + portfolio);

    int sharesLeft = numberOfShares;
    while (sharesLeft > 0) {

      for (Stock stock : portfolio.keySet()) {

        if (stock.getStockSymbol().equals(stockSymbol) && getDateIndex(stock.getDate(), dataList)
                > getLatestDateIndex()) {
          if (portfolio.get(stock) > sharesLeft) {
            portfolio.put(getStock(stockSymbol, date), portfolio.get(getStock(stockSymbol, date))
                    - numberOfShares);
            sharesLeft = 0;
          } else {
            sharesLeft = sharesLeft - portfolio.get(stock);
            portfolio.remove(stock);
          }
        }
      }
    }
  }

  //gets latest date of a portfolio.
  private int getLatestDateIndex() {
    int smallestDateIndex = 1000;
    List<String[]> list = getStockData("msft");
    for (String[] row: list) {
      if (getDateIndex(row[4], list) < smallestDateIndex) {
        smallestDateIndex = getDateIndex(row[4], list);
      }
    }
    return smallestDateIndex;
  }

  /**
   * Helper function returns a stock given the date and symbol.
   * @param date The date we want.
   * @param stockSymbol The stock symbol.
   * @return A stock of the portfolio with that symbol and date.
   */
  public Stock getStock(String stockSymbol, String date) throws IllegalArgumentException {
    for (Stock stock: portfolio.keySet()) {
      if (stock.getDate().equals(date) && stock.getStockSymbol().equals(stockSymbol)) {
        return stock;
      }
    }
    throw new IllegalArgumentException("Stock does not exist");
  }



  /**
   * Gets the composition of the portfolio, consisting of a list of stocks
   *     and the number of shares of each stock.
   * @param date The date we want to see the composition of the portfolio.
   * @return The list of stocks, and the number of shares of each stock.
   */
  public String getPortfolioComposition(String date) {
    if (portfolio.isEmpty()) {
      throw new IllegalArgumentException("Error: Empty portfolio");
    }

    List<String> message = new ArrayList<>(); // Initialize message list

    for (Stock stock : portfolio.keySet()) {
      String stockSymbol = stock.getStockSymbol();
      int numberOfShares = portfolio.get(stock);

      String stockData = getDataForStocks(stockSymbol);
      saveToCSVFile(stockData);
      List<String[]> dataList = readCSVFile("output.csv");

      int dateIndex = getDateIndex(date, dataList);
      if (dateIndex == -1) {
        throw new IllegalArgumentException("Error: Invalid or non-existent date.");
      }

      // Construct message for each stock
      String stockMessage = "Stock: " + stockSymbol + ", Number of shares: " + numberOfShares;
      message.add(stockMessage);
    }

    // Build the return string from message
    return "The composition of the portfolio on " + date + " is:\n"
            + String.join(".\n", message) + ".";
  }




  /**
   * Gets the cost of the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares added to the stock.
   * @param date The date of the stock.
   * @return The cost of the portfolio.
   */
  public double getPortfolioCost(String stockSymbol, int numberOfShares, String date) {
    if (portfolio.isEmpty()) {
      throw new IllegalArgumentException("Error: Empty portfolio");
    }

    ifStatement(stockSymbol, date, numberOfShares);
    //forLoop(date);
    ArrayList<Double> values = new ArrayList<>();
    double value;
    System.out.println("Stock stock: " + portfolio.keySet());
    for (Stock stock: portfolio.keySet()) {
      System.out.println("Stock: " + stock.getStockSymbol());


      System.out.println("Stock: " + portfolio.get(stock));

      List<String[]> dataList = getStockData(stock.getStockSymbol());
      //System.out.println("DataList: " + dataList);
      int dateIndex = getDateIndex(date, dataList);
      if (dateIndex == -1) {
        throw new IllegalArgumentException("Error: invalid or non-existent date.");
      }
      closingPrices.add(Double.parseDouble(dataList.get(dateIndex)[4]));
      value = (Double.parseDouble(dataList.get(dateIndex)[4]) * portfolio.get(stock));
      System.out.println("Value: " + value);
      values.add(value);
      portfolioValue += value;
      //System.out.println("Portfolio Value: " + portfolioValue);
    }
    System.out.println("getPortfolioValue: " + portfolioValue);
    return portfolioValue;
  }

  /**
   * Adds shares to a stock in the portfolio, or creates a new one if stock does not exist.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares added to the stock.
   * @param date The date of the stock.
   */
  private void ifStatement(String stockSymbol, String date, int numberOfShares) {
    Stock stockToAdd = new Stock(stockSymbol, date);

    if (portfolio.containsKey(stockToAdd)) {
      // If the stock already exists, add the number of shares to the existing entry
      portfolio.put(stockToAdd, portfolio.get(stockToAdd) + numberOfShares);
    } else {
      // If the stock does not exist, create a new entry in the portfolio
      portfolio.put(stockToAdd, numberOfShares);
    }
  }


  /**
   * Gets the value of each individual stock within the portfolio.
   * @param date The date we want to get the value at.
   * @return The value of each individual stock within the portfolio.
   */
  public String getDistributionPortfolioValue(String date) {
    ArrayList<Double> thisValues = new ArrayList<>();
    //thisValues.clear();
    double value;
    for (Stock stock : portfolio.keySet()) {
      String stockData = getDataForStocks(stock.getStockSymbol());
      saveToCSVFile(stockData);
      List<String[]> dataList = readCSVFile("output.csv");
      int dateIndex = getDateIndex(date, dataList);
      if (dateIndex == -1) {
        throw new IllegalArgumentException("Error: invalid or non-existent date.");
      }
      value = (Double.parseDouble(dataList.get(dateIndex)[4]) * portfolio.get(stock));
      thisValues.add(value);
    }

    Collections.sort(thisValues);

    message.clear();
    for (int i = 0; i < thisValues.size(); i++) {
      message.add("Stock: " + portfolioKeys.get(i) + "Value is: " + thisValues.get(i));
    }
    System.out.println(thisValues);
    return "The distribution of the value of the portfolio on " + date + " is: \n"
            + message.toString().replace(", ", ".\n")
            .replace("Value is", ", Value is")
            .replace("]", ".")
            .replace("[", "");
  }





  /**
   * Distributes the amount of money of each stock.
   * @param percentList the distribution of the stocks in the portfolio.
   * @param date The date we want to get the values at.
   * @return The rebalanced values according to the percent for each stock.
   */
  public String rebalancedPortfolioValue(List<Integer> percentList,
                                         String date) {

    if (portfolio.isEmpty()) {
      throw new IllegalArgumentException("Error: Empty portfolio");
    }

    int percentSum = 0;
    for (Integer integer : percentList) {
      percentSum += integer;
    }
    if (percentSum != 100) {
      throw new IllegalArgumentException("Percent sum must equal 100.");
    }

    ArrayList<Double> values = new ArrayList<>();
    ArrayList<Double> newValues = new ArrayList<>();
    ArrayList<Double> newNumberOfSharesList = new ArrayList<>();
    double value;
    for (Stock stock: portfolio.keySet()) {
      String stockData = getDataForStocks(stock.getStockSymbol());
      saveToCSVFile(stockData);
      List<String[]> dataList = readCSVFile("output.csv");
      int dateIndex = getDateIndex(date, dataList);
      if (dateIndex == -1) {
        throw new IllegalArgumentException("Error: invalid or non-existent date.");
      }
      closingPrices.add(Double.parseDouble(dataList.get(dateIndex)[4]));
      value = (Double.parseDouble(dataList.get(dateIndex)[4]) * portfolio.get(stock));
      values.add(value);
      portfolioValue += value;
      System.out.println("Portfolio value: " + portfolioValue);
    }

    double newValue;
    double newNumberOfShares;
    double changeBy;

    message.clear();
    for (int i = 0; i < values.size(); i++) {
      newValue = portfolioValue * percentList.get(i) / 100;
      newValues.add(newValue);

      //System.out.println("closing price: " + closingPrices.get(i));
      newNumberOfShares = newValue / closingPrices.get(i);
      //System.out.println("newValue: " + newValue);
      newNumberOfSharesList.add(newNumberOfShares);
      System.out.println("portfolio to string: " + portfolio.values());
      String stockSymbol = portfolioKeys.get(i);
      System.out.println("Shares: " + shares.get(i));

      if (newNumberOfShares > shares.get(i)) {
        changeBy = shares.get(i) + newNumberOfShares;
        purchaseShares(stockSymbol, (int) changeBy, date);
      } else if (newNumberOfShares < shares.get(i)) {
        changeBy = shares.get(i) - newNumberOfShares;
        sellShares(stockSymbol, (int) changeBy, date);
      }

      message.add("Stock: " + portfolioKeys.get(i)
              + "Value is: " + newValues.get(i));
      //+ " Number of shares is: " + newNumberOfSharesList.get(i));
    }
    return "The rebalanced distribution of the value of the portfolio on " + date + " is: \n"
            + message.toString().replace(", ", ".\n")
            .replace("Value is", ", Value is")
            .replace("]", ".")
            .replace("[", "");
  }


  private double totalPortfolio(String date) {
    double value = 0;
    double portfolioValue = 0;
    for (Stock stock: portfolio.keySet()) {
      List<String[]> dataList = getStockData(stock.getStockSymbol());
      //System.out.println("DataList: " + dataList);
      int dateIndex = getDateIndex(date, dataList);
      if (dateIndex == -1) {
        throw new IllegalArgumentException("Error: invalid or non-existent date.");
      }
      value = (Double.parseDouble(dataList.get(dateIndex)[4]) * portfolio.get(stock));
      portfolioValue += value;
    }
    return value;
  }







  /**
   * Finds performance overtime.
   * @param stockSymbol the distribution of the stocks in the portfolio.
   * @param startDate The start date we want to get the values at.
   * @param endDate The end date.
   * @return The rebalanced values according to the percent for each stock.
   */
  public String performanceOverTime(String stockSymbol, String startDate, String endDate) {
    // Check if portfolio is empty
    if (portfolio.isEmpty()) {
      throw new IllegalArgumentException("Error: Portfolio is empty");
    }

    // Initialize lists for dates and values over time
    final List<String> dates = new ArrayList<>();
    final List<Double> valuesOverTime = new ArrayList<>();
    final List<String> message = new ArrayList<>();

    System.out.println("StartDate: " + startDate);
    System.out.println("EndDate: " + endDate);

    // Fetch and parse stock data for the given symbol
    String stockData = getDataForStocks(stockSymbol);
    saveToCSVFile(stockData);
    List<String[]> dataList = readCSVFile("output.csv");

    // Get indices of start and end dates
    int startDateIndex = getDateIndex(startDate, dataList);
    int endDateIndex = getDateIndex(endDate, dataList);

    // Validate start and end date indices
    if (startDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid start date.");
    } else if (endDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid end date.");
    } else if (startDateIndex < endDateIndex) {
      throw new IllegalArgumentException("Error: Start date cannot be later than end date!");
    }

    System.out.println("startDateIndex: " + startDateIndex);
    System.out.println("endDateIndex: " + endDateIndex);
    System.out.println("DataListSize: " + dataList.size());

    // Calculate total number of shares for the specified stock symbol
    int numberOfShares = 0;
    for (Stock stock : portfolio.keySet()) {
      if (stock.getStockSymbol().equals(stockSymbol)) {
        numberOfShares += portfolio.get(stock);
      }
    }

    // Calculate values over time and populate dates list
    for (int i = startDateIndex; i >= endDateIndex; i--) {
      double value = (Double.parseDouble(dataList.get(i)[4]) * numberOfShares);
      valuesOverTime.add(value);
      dates.add(dataList.get(i)[0]);
    }

    // Convert values over time to asterisk bars
    List<String> valuesToBars = valuesToBar(valuesOverTime, 100);

    // Print debug information
    System.out.println("Values: " + valuesOverTime);
    System.out.println("Dates: " + dates);

    // Construct message for output
    for (int i = 0; i < valuesToBars.size(); i++) {
      message.add("Date: " + dates.get(i) + ", Value is: " + valuesToBars.get(i));
    }

    // Return formatted performance over time message
    return "The performance over time of the " + stockSymbol + " stock is: \n"
            + "* = 100.\n"
            + String.join(".\n", message) + ".";
  }




  /**
   * Finds portfolio performance overtime.
   * @param startDate The start date we want to get the values at.
   * @param endDate The end date.
   * @return The rebalanced values according to the percent for each stock.
   */
  public String portfolioPerformanceOvertime(String startDate, String endDate) {
    if (portfolio.isEmpty()) {
      throw new IllegalArgumentException("Error: Portfolio is empty");
    }


    if (!portfolioStocksHaveDates(startDate, endDate)) {
      throw new IllegalArgumentException("Error: Not all the stocks have the given start"
              + " and end dates");
    }

    // Initialize necessary variables
    double portfolioValue;
    double value;
    final ArrayList<String> dates = new ArrayList<>();
    ArrayList<Double> valuesOverTime = new ArrayList<>();
    ArrayList<String> message = new ArrayList<>();

    // Debug print for start and end dates
    System.out.println("StartDate: " + startDate);
    System.out.println("EndDate: " + endDate);

    // Get stock data for example stock AAPL to find date indices
    List<String[]> dataList = getStockData("AAPL");

    // Find indices for start and end dates
    int startDateIndex = getDateIndex(startDate, dataList);
    int endDateIndex = getDateIndex(endDate, dataList);

    // Check for valid date indices
    if (startDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid start date.");
    } else if (endDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid end date.");
    } else if (startDateIndex < endDateIndex) {
      throw new IllegalArgumentException("Error: Start date cannot be later than end date!");
    }

    // Debug print for indices
    System.out.println("StartDateIndex: " + startDateIndex);
    System.out.println("EndDateIndex: " + endDateIndex);

    // Iterate through the dates from end to start index
    for (int i = startDateIndex; i >= endDateIndex; i--) {
      // Reset portfolio value for the current date
      portfolioValue = 0;
      // Iterate over each stock in the portfolio
      for (Stock stock : portfolio.keySet()) {
        // Get stock data for the current stock
        List<String[]> list = getStockData(stock.getStockSymbol());
        // Get closing price for the current date
        double closingPrice = Double.parseDouble(list.get(i)[4]);
        // Calculate the value for the current stock
        value = closingPrice * portfolio.get(stock);
        // Add to the total portfolio value
        portfolioValue += value;
      }
      // Add date and portfolio value to lists
      dates.add(dataList.get(i)[0]);
      valuesOverTime.add(portfolioValue);
    }

    // Convert values to bars using * notation
    List<String> valuesToBars = valuesToBar(valuesOverTime, 500);

    // Debug print for values and dates
    System.out.println("Values: " + valuesOverTime);
    System.out.println("Dates: " + dates);

    // Create message for each date and value
    for (int i = 0; i < valuesToBars.size(); i++) {
      message.add("Date: " + dates.get(i) + ", Value is: " + valuesToBars.get(i));
    }

    // Format the final message
    return "The performance over time of the portfolio is: \n"
            + "500 = How much the astricks are worth.\n"
            + String.join(".\n", message) + ".";
  }

  /*
  helper method for performanceovertime. If all the stocks in portfolio have these dates,
  return true.
   */
  private boolean portfolioStocksHaveDates(String startDate, String endDate) {
    boolean stocksHaveDates = true;
    for (Stock stock: portfolio.keySet()) {
      List<String[]> dataList = getStockData(stock.getStockSymbol());

      List<String> stockDates = new ArrayList<String>();
      for (String[] strings : dataList) {
        stockDates.add(strings[0]);
      }
      if (!stockDates.contains(startDate) || !stockDates.contains(endDate)) {
        stocksHaveDates = false;
        break;
      }
    }
    return stocksHaveDates;
  }

  private List<String[]> getStockData(String stockSymbol) {
    String data = getDataForStocks(stockSymbol);
    saveToCSVFile(data);
    return readCSVFile("output.csv");
  }



  /**
   * Helper function that converts a list of doubles into a list of astericks.
   * @param values The list of doubles.
   * @param unitValue How much we want the astericks to represent.
   * @return A list of astericks representing the values.
   */
  private List<String> valuesToBar(List<Double> values, int unitValue) {
    ArrayList<String> barsOverTime = new ArrayList<>();

    for (Double value : values) {
      StringBuilder bar = new StringBuilder();
      int numOfAsterisks = (int) (value / unitValue); // Correct calculation: number of asterisks
      for (int i = 0; i < numOfAsterisks; i++) {
        bar.append("*");
      }
      barsOverTime.add(bar.toString());
    }
    return barsOverTime;
  }


  protected static String getDataForStocks(String stockSymbol) {
    String apiKey = "W0M1JOKC82EZEQA8";
    //ticker symbol for Google
    URL url = null;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {

      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char)b);
      }

      if (output.toString().contains("Error Message")) {
        throw new IllegalArgumentException("Invalid API call. Enter a valid stock symbol");
      }

    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    return output.toString();
  }

  /*
  Saves the output of calling API stock data into a csv file called output.csv.
   */
  protected static void saveToCSVFile(String stockData) {
    try {
      String[] stockDataLines = stockData.split(System.lineSeparator());
      BufferedWriter csvConversion = new BufferedWriter(new FileWriter("output.csv"));
      for (String line : stockDataLines) {
        csvConversion.write(line);
        csvConversion.newLine();
      }
    } catch (IOException e) {
      System.out.println("Threw IOException");
    }
  }

  /*
  Reads the content of the CSV file line by line, converting that into a list of String[]
  (with each line being a data set and each member of the String[] being a component of
  that data)
   */
  protected static List<String[]> readCSVFile(String filepath) {
    List<String[]> csvFileToList = new ArrayList<>();
    try {
      BufferedReader read = new BufferedReader(new FileReader(filepath));
      String line;
      while ((line = read.readLine()) != null) {
        csvFileToList.add(line.split(","));
      }
      if (!csvFileToList.isEmpty()) {
        csvFileToList.removeFirst();
      }
    } catch (IOException e) {
      System.out.println("failed to read file");
    }
    return csvFileToList;
  }

  /*
  changed to static method
   */
  protected static int getDateIndex(String date, List<String[]> lines) {
    int dateIndex = -1;
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i)[0].equals(date)) {
        dateIndex = i;
        break;
      }
    }
    return dateIndex;
  }

  /*
  used for testing
   */
  public Map<Stock, Integer> getPortfolio() {
    return portfolio;
  }

}