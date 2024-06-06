package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * Takes in an input
 *
 *
 */
public class Controller implements ControllerInterface {

  private final Model model;
  private final View view;
  Readable in;

  public Controller(Model model, View view) {
    this.model = model;
    this.view = view;
  }



  public void go() {
    View.welcomeMessage();

    //scanner that takes in inputs
    Scanner scan = new Scanner(in);

    //instructions given to the controller, and chooses a model to pass info
    //to.
    String instructions = "";
    String stockSymbol = "";
    boolean quit = false;
    boolean validStockSymbol = false;

    while(!quit) {
      instructions = scan.nextLine();

      switch(instructions) {
        case "gain-loss":
          while(!validStockSymbol) {
            try {
              askForStock("gain-loss");

              //implement viewer later.
              View.writeMessage("Please enter an available stock:");
              stockSymbol = scan.nextLine();

              View.writeMessage("Enter a start date:");
              String startDate = scan.nextLine();

              View.writeMessage("Enter an end date:");
              String endDate = scan.nextLine();

          /*
          Announces that data has been sent to the model.
           */
              View.writeMessage("Obtaining gain or loss for " + stockSymbol + "from " + startDate
                      + "to " + endDate + ".");
              double[] finalPrices = model.getGainOrLoss(stockSymbol, startDate, endDate);
              View.gainLossMessage(finalPrices);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              e.getMessage();
            }
            //If an invalid input was sent, it'll send back an output saying that.
          }
          break;
        case "x-day-average":
          while(!validStockSymbol) {
            try {
              askForStock("x-day-average");

              View.writeMessage("Please enter an available stock:");
              stockSymbol = scan.nextLine();

              View.writeMessage("Enter a start date:");
              String startDate = scan.nextLine();

              View.writeMessage("Enter the number of days before date:");
              int x = scan.nextInt();

              View.writeMessage("Obtaining average of " + stockSymbol + "from " + startDate
                      + "going back to " + x + ".");

              double average = model.getXDayAverage(stockSymbol, startDate, x);

              View.XDayAverageMessage(average);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              e.getMessage();
            }
          }
          break;
        case "x-day-crossover":
          while (!validStockSymbol) {
            try {
              askForStock("x-day-crossover");

              View.writeMessage("Please enter an available stock:");
              stockSymbol = scan.nextLine();

              View.writeMessage("Enter a start date:");
              String date = scan.nextLine();

              View.writeMessage("Enter the number of days before date:");
              int y = scan.nextInt();

              View.writeMessage("Obtaining crossovers of  " + stockSymbol + "from " + date
                      + "going back to " + y + ".");
              List<String[]> xDayCrossoverList = model.getXDayCrossovers(stockSymbol, date, y);
              View.XDayCrossOverMessage(xDayCrossoverList);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              e.getMessage();
            }
          }
          break;
        case "create-portfolio":
          while(!validStockSymbol) {
            /*

             */
          }
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
}
