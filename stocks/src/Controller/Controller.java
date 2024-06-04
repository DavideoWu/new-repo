package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import Model.GainLoss;
import Model.Portfolio;
import Model.XDayAverage;
import Model.XDayCrossOver;

/**
 * Takes in an input
 *
 *
 */
public class Controller {

  private GainLoss gainLoss;
  private Portfolio portfolio;
  private XDayAverage xdayaverage;
  private XDayCrossOver xdaycrossover;


  InputStream in;
  OutputStream out;


  public Controller(InputStream in, OutputStream out) {
    this.in = in;
    this.out = out;
  }



  public void go() {
    //scanner that takes in inputs
    Scanner scan = new Scanner(in);

    //instructions given to the controller, and chooses a model to pass info
    //to.
    String instructions = "";
    String stockSymbol = "";
    String stockData = "";

    while(!instructions.equals("quit")) {
      instructions = scan.next();

      switch(instructions) {
        case "gain-loss":
          askForStock("gain-loss");
          stockSymbol = scan.next();
          while (!stockList.contains(stockSymbol)) {
            writeMessage("Please a valid stock");
            stockSymbol = scan.next();
          }
          stockData = getDataForStock(stockSymbol);
          //inputting data to the gainLoss model:

          writeMessage("Enter a start date:");
          String startDate = scan.next();

          writeMessage("Enter an end date:");
          String endDate = scan.next();

          model.getGainLoss(stockData, startDate, endDate);

          break;
        case "x-day-average":
          askForStock("x-day-average");
          stockSymbol = scan.next();
          while(!stockList.contains(stockSymbol)) {
            writeMessage("Please enter an available stock");
          }
          stockData = getDataForStock(stockSymbol);

          writeMessage("Enter a date:");
          String date = scan.next();

          writeMessage("Enter the number of days of the average:");
          int x = scan.nextInt();

          model.getXDayAverage()
        case "x-day-crossover":


      }
    }

  }

  //writes a message outputted to the user.
  private void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);

    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  private void askForStock(String action) {
    writeMessage("Chose " + action + System.lineSeparator());
    writeMessage("Input the stock you want:" + System.lineSeparator());
  }

  /*
  This helper functions takes in a stock key. With this stock key, it can
  produce a String in CSV format with the stock data for many dates.
   */
  private String getDataForStock(String stockSymbol) {
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
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+stockSymbol);
    }
    return output.toString();
  }

}
