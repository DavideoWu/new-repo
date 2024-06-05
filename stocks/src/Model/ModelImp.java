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
import java.util.List;

public class ModelImp implements Model {

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
      throw new IllegalArgumentException("Enter a valid start date.");
    }
    else if (endDateIndex == -1) {
      throw new IllegalArgumentException("Enter a valid end date.");
    }
    else if (getDateIndex(startDate, dataList) > getDateIndex(endDate, dataList)) {
      throw new IllegalArgumentException("Start date cannot be later than end date!");
    } else {
      double[] startAndFinalPrices = new double[2];
      startAndFinalPrices[0] = Double.parseDouble(dataList.get(startDateIndex)[4]);
      startAndFinalPrices[1] = Double.parseDouble(dataList.get(endDateIndex)[4]);

      return startAndFinalPrices;
    }
  }

  public void getXDayAverage(String stockSymbol, String date, int daysBefore) {

  }

  public void getXDayCrossovers(String stockSymbol, String date, int daysBefore) {

  }

  public void createPortfolio(String date) {

  }

  /*
This helper functions takes in a stock key. With this stock key, it can
produce a String in CSV format with the stock data for many dates.
 */
  private static String getDataForStocks(String stockSymbol) {
    String apiKey = "W0M1JOKC82EZEQA8";
    //ticker symbol for Google
    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
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
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */
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
      CSVFileToList.add(read.readLine().split(","));
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
