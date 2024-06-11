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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class that stores the status of the stock.
 */
public class ModelImp implements Model {

  private final Map<String, Integer> portfolio = new HashMap<>();

  private List<String[]> dataList;

  private int dateIndex;

  // represents the stock symbols.
  ArrayList<String> portfolioKeys = new ArrayList<>();

  // represents the value of the portfolio.
  double portfolioValue = 0;

  // represents the number of shares.
  ArrayList<Integer> shares = new ArrayList<>();

  // represents the closing prices.
  ArrayList<Double> closingPrices = new ArrayList<>();



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
    }
    else if (endDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid end date.");
    }
    else if (getDateIndex(startDate, dataList) > getDateIndex(endDate, dataList)) {
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
    }
    else if (dateIndex == -1) {
      throw new IllegalArgumentException("Error: Entered invalid or non-existent date");
    }
    else if (dateIndex + daysBefore > dataList.size()) {
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
  public void createPortfolio(String stockSymbol, int numberOfShares) {
    portfolio.put(stockSymbol, numberOfShares);
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
    ifStatement(stockSymbol, numberOfShares);
  }

  /**
   * Sell a specific number of shares of a specific stock
   *     on a specified date from a given portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares to sell.
   * @param date The date of the stock.
   */
  public void sellShares(String stockSymbol, int numberOfShares, String date) {
    if (portfolio.get(stockSymbol) <= numberOfShares) {
      portfolio.remove(stockSymbol);
    } else {
      portfolio.put(stockSymbol, portfolio.get(stockSymbol) - numberOfShares);
    }
  }


  /**
   * Gets the composition of the portfolio, consisting of a list of stocks
   *     and the number of shares of each stock.
   * @param date The date we want to see the composition of the portfolio.
   * @return The list of stocks, and the number of shares of each stock.
   */
  public String getPortfolioComposition(String date) {
    forLoop(date);
    return "The composition of the portfolio on " + date + " is: \n"
            + portfolio.toString().replace(",", "." + "\n" + "Stock:")
            .replace("=", ", Number of shares: ")
            .replace("{", "Stock: ")
            .replace("}", ".");
  }

  ArrayList<Double> getPortfolioValue = new ArrayList<>();
  ArrayList<String> getDatesPortfolioValue = new ArrayList<>();
  /**
   * Gets the cost of the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares added to the stock.
   * @param date The date of the stock.
   * @return The cost of the portfolio.
   */
  public double getPortfolioCost(String stockSymbol, int numberOfShares, String date) {
    ifStatement(stockSymbol, numberOfShares);
    forLoop(date);
    return portfolioValue;
  }


  private void ifStatement(String stockSymbol, int numberOfShares) {
    if (portfolio.containsKey(stockSymbol)) {
      portfolio.put(stockSymbol, portfolio.get(stockSymbol) + numberOfShares);
    } else {
      portfolio.put(stockSymbol, numberOfShares);
    }
  }

  ArrayList<Double> values = new ArrayList<>();
  ArrayList<String> message = new ArrayList<>();

  /**
   * Gets the value of each individual stock within the portfolio.
   * @param date The date we want to get the value at.
   * @return The value of each individual stock within the portfolio.
   */
  public String getDistributionPortfolioValue(String date) {
    forLoop(date);
    for (int i = 0; i < values.size(); i++) {
      message.add("Stock: " + portfolioKeys.get(i) + "Value is: " + values.get(i));
    }
    System.out.println(values);
    return "The distribution of the value of the portfolio on " + date + " is: \n"
            + message.toString().replace(", ", ".\n")
            .replace("Value is", ", Value is")
            .replace("]", ".")
            .replace("[", "");
  }


  ArrayList<Double> newValues = new ArrayList<>();
  ArrayList<Double> newNumberOfSharesList = new ArrayList<>();

  /**
   * Distributes the amount of money of each stock.
   * @param percentList the distribution of the stocks in the portfolio.
   * @param date The date we want to get the values at.
   * @return The rebalanced values according to the percent for each stock.
   */
  public String rebalancedPortfolioValue(List<Integer> percentList, String date) {
    forLoop(date);
    double newValue;
    double newNumberOfShares;
    double changeBy;
    int percentSum = 0;
    for (int i = 0; i < percentList.size(); i++) {
      percentSum += percentList.get(i);
      if (percentSum > 100) {
        throw new IllegalArgumentException("Cannot go over 100%");
      }
    }
    for (int i = 0; i < values.size(); i++) {
      newValue = values.get(i) * percentList.get(i) / 100;
      newValues.add(newValue);
      newNumberOfShares = newValue / closingPrices.get(i);
      newNumberOfSharesList.add(newNumberOfShares);
      String stockSymbol = portfolioKeys.get(i);
      System.out.println("Shares: " + shares.get(i));
      if (newNumberOfShares > shares.get(i)) {
        changeBy = shares.get(i) + newNumberOfShares;
        purchaseShares(stockSymbol, (int) changeBy, date);
      } else if (newNumberOfShares < shares.get(i)) {
        changeBy = shares.get(i) - newNumberOfShares;
        sellShares(stockSymbol, (int) changeBy, date);
      }
      message.add("Stock: " + portfolioKeys.get(i) + "Value is: " + newValues.get(i));
    }
    return "The rebalanced distribution of the value of the portfolio on " + date + " is: \n"
            + message.toString().replace(", ", ".\n")
            .replace("Value is", ", Value is")
            .replace("]", ".")
            .replace("[", "");
  }

  //private final Map<String, Double> performanceProgress = new HashMap<>();



  public String performanceOverTime(String stockSymbol, String startDate, String endDate) {
    final ArrayList<String> dates = new ArrayList<>();
    ArrayList<Double> valuesOverTime = new ArrayList<>();

    System.out.println("StartDate: " + startDate);
    System.out.println("EndDate: " + endDate);

    String stockData = getDataForStocks(stockSymbol);
    saveToCSVFile(stockData);
    List<String[]> dataList = readCSVFile("output.csv");

    int startDateIndex = getDateIndex(startDate, dataList);
    int endDateIndex = getDateIndex(endDate, dataList);

    if (startDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid start date.");
    }
    else if (endDateIndex == -1) {
      throw new IllegalArgumentException("Error: Enter a valid end date.");
    }
    else if (getDateIndex(startDate, dataList) < getDateIndex(endDate, dataList)) {
      throw new IllegalArgumentException("Error: Start date cannot be later than end date!");
    }

    System.out.println("startDateIndex: " + startDateIndex);
    System.out.println("endDateIndex: " + endDateIndex);
    System.out.println("DataListSize: " + dataList.size());

    for (int i = startDateIndex; i >= endDateIndex; i--) {
      //forLoop(dataList.get(i)[0]);
      double value;
      value = (Double.parseDouble(dataList.get(i)[4]) * portfolio.get(stockSymbol));
      valuesOverTime.add(value);
      dates.add(dataList.get(i)[0]);
    }

    System.out.println("Values: " + valuesOverTime);
    System.out.println("Dates: " + dates);

    for (int i = 0; i < valuesOverTime.size(); i++) {
      message.add("Date: " + dates.get(i) + "Value is: " + valuesOverTime.get(i));
    }
    return "The performance over time of the " + stockSymbol + " stock is: \n"
            + message.toString().replace(", ", ".\n")
            .replace("Value is", ", Value is")
            .replace("]", ".")
            .replace("[", "");
  }

  /**
   * Helper function that converts a list of doubles into a list of astericks.
   * @param values The list of doubles.
   * @param size How much we want the astericks to represent.
   * @return A list of astericks representing the values.
   */
  private List<String> valuesToBar(List<Double> values, int size) {
    ArrayList<String> barsOverTime = new ArrayList<String>();

    StringBuilder bar = new StringBuilder();
    int asterickSize = size;
    int numOfAstericks = 0;

    for (Double value: values) {
      bar = new StringBuilder();
      numOfAstericks = (int) (value % asterickSize);
      for (int i = 0; i < numOfAstericks; i++) {
        bar.append("*");
      }
      barsOverTime.add(String.valueOf(bar));
    }
    return barsOverTime;
  }

  protected void forLoop(String date) {
    double value;
    for (String stockKey: portfolio.keySet()) {
      String stockData = getDataForStocks(stockKey);
      saveToCSVFile(stockData);
      List<String[]> dataList = readCSVFile("output.csv");
      int dateIndex = getDateIndex(date, dataList);
      if (dateIndex == -1) {
        throw new IllegalArgumentException("Error: invalid or non-existent date.");
      }
      closingPrices.add(Double.parseDouble(dataList.get(dateIndex)[4]));
      value = (Double.parseDouble(dataList.get(dateIndex)[4]) * portfolio.get(stockKey));
      values.add(value);
      portfolioValue += value;
    }
  }

  /*
  changed these helper methods to protected so they can be used by the stock class.
   */

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
    }
    catch (MalformedURLException e) {
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

    }
    catch (IOException e) {
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
  public Map<String, Integer> getPortfolio() {
    return portfolio;
  }

}