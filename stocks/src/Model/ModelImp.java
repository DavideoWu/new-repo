package Model;

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

public class ModelImp implements Model {

  private Map<String, Integer> portfolio = new HashMap<>();

  private List<String[]> dataList;

  private int dateIndex;

  /*
  Gain loss function: take in stockSymbol, get stock data, convert to csv. file, run
  excel calculations based on startDate and endDate, return. True -> stock gained
  False -> stock lost.
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

  public List<String[]> getXDayCrossovers(String stockSymbol, String date, int daysBefore)
          throws IllegalArgumentException {

    List<String[]> listOfCrossovers = new ArrayList<>();
    /*
    Double average is set to xdayaverage. It gets the average, or throws exceptions.
     */
    double average = getXDayAverage(stockSymbol, date, daysBefore);

    for (int i = dateIndex; i < dateIndex + daysBefore; i++) {
      if (Double.parseDouble(dataList.get(i)[4]) > average) {
        listOfCrossovers.add(dataList.get(i));
      }
    }
    return listOfCrossovers;
  }


  public void createPortfolio(String stockSymbol, int numberOfShares) {
    portfolio.put(stockSymbol, numberOfShares);
  }

  public double getPortfolioCost(String stockSymbol, int numberOfShares, String date) {
    double sum = 0;

    portfolio.put(stockSymbol, numberOfShares);
    for (String stockKey: portfolio.keySet()) {
      String stockData = getDataForStocks(stockKey);
      saveToCSVFile(stockData);
      List<String[]> dataList = readCSVFile("output.csv");

      int dateIndex = getDateIndex(date, dataList);
      if (dateIndex == -1) {
        throw new IllegalArgumentException("Error: invalid or non-existent date.");
      }

      sum += (Double.parseDouble(dataList.get(dateIndex)[4]) * numberOfShares);
    }

    return sum;
  }


  private static String getDataForStocks(String stockSymbol) {
    String apiKey = "W0M1JOKC82EZEQA8";
    //ticker symbol for Google
    URL url = null;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey="+apiKey+"&datatype=csv");
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

      while ((b=in.read())!=-1) {
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
  private static void saveToCSVFile(String stockData) {
    try {
      String[] stockDataLines = stockData.split(System.lineSeparator());
      BufferedWriter CSVConversion = new BufferedWriter(new FileWriter("output.csv"));
      for (String line: stockDataLines) {
        CSVConversion.write(line);
        CSVConversion.newLine();
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
  private static List<String[]> readCSVFile(String filepath) {
    List<String[]> CSVFileToList = new ArrayList<>();
    try {
      BufferedReader read = new BufferedReader(new FileReader(filepath));
      String line;
      while ((line = read.readLine()) != null) {
        CSVFileToList.add(line.split(","));
      }
      if (!CSVFileToList.isEmpty()) {
        CSVFileToList.removeFirst();
      }
    } catch (IOException e) {
      System.out.println("failed to read file");
    }

    return CSVFileToList;
  }

  private int getDateIndex(String date, List<String[]> lines) {
    int dateIndex = -1;
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i)[0].equals(date)) {
        dateIndex = i;
        break;
      }
    }
    return dateIndex;
  }
}
