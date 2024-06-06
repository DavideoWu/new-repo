package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.InputMismatchException;
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
  private Readable in;

  public Controller(Model model, View view, Readable in) {
    this.model = model;
    this.view = view;
    this.in = in;
  }



  public void go() {
    view.welcomeMessage();

    //scanner that takes in inputs
    Scanner scan = new Scanner(in);

    //instructions given to the controller, and chooses a model to pass info
    //to.
    String instructions = "";

    //stock symbol for obtaining API data
    String stockSymbol = "";

    //status used in portfolio to determine if user is done entering shares
    String status = "";

    String date = "";

    //used in x-day average/crossover, how many days back want to check price.
    int numDaysBefore = 0;

    //num of shares want to add in portfolio
    int numShares = 0;

    //if quit = true, than exit program
    boolean quit = false;

    //checks if the stocksymbol added is valid
    boolean validStockSymbol = false;

    //used in portfolio to determine if done
    boolean isDone = false;

    while(!quit) {
      instructions = scan.nextLine();

      switch(instructions) {
        case "gain-loss":
          while(!validStockSymbol) {
            try {
              askForStock("gain-loss");

              //implement viewer later.
              stockSymbol = scan.nextLine();

              view.writeMessage("Enter a start date:");
              String startDate = scan.nextLine();

              view.writeMessage("Enter an end date:");
              String endDate = scan.nextLine();

          /*
          Announces that data has been sent to the model.
           */
              view.writeMessage("Obtaining gain or loss for " + stockSymbol + " from " + startDate
                      + " to " + endDate + ".");
              double[] finalPrices = model.getGainOrLoss(stockSymbol, startDate, endDate);
              view.returnGainOrLoss(finalPrices);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              view.writeMessage(e.getMessage());
            }
            //If an invalid input was sent, it'll send back an output saying that.
          }
          break;
        case "x-day-average":
          while(!validStockSymbol) {
            try {
              askForStock("x-day-average");

              stockSymbol = scan.nextLine();

              view.writeMessage("Enter a start date:");
              String startDate = scan.nextLine();

              view.writeMessage("Enter the number of days before date:");

              try {
                numDaysBefore = scan.nextInt();
                scan.nextLine();
              } catch (InputMismatchException e) {
                throw new IllegalArgumentException("Enter a valid number of days.");
              }

              view.writeMessage("Obtaining average of " + stockSymbol + " from " + startDate
                      + " going back to " + numDaysBefore + " days before.");

              double average = model.getXDayAverage(stockSymbol, startDate, numDaysBefore);
              view.XDayAverageMessage(average);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              view.writeMessage(e.getMessage());
            }
          }
          break;
        case "x-day-crossover":
          while (!validStockSymbol) {
            try {
              askForStock("x-day-crossover");

              stockSymbol = scan.nextLine();

              view.writeMessage("Enter a start date:");
              date = scan.nextLine();

              view.writeMessage("Enter the number of days before date:");
              try {
                numDaysBefore = scan.nextInt();
                scan.nextLine();
              } catch (InputMismatchException e) {
                throw new IllegalArgumentException("Enter a valid number of days.");
              }

              view.writeMessage("Obtaining crossovers of  " + stockSymbol + "from " + date
                      + "going back to " + numDaysBefore + ".");
              List<String[]> xDayCrossoverList = model.getXDayCrossovers(stockSymbol, date,
                      numDaysBefore);
              view.XDayCrossOverMessage(xDayCrossoverList);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              view.writeMessage(e.getMessage());
            }
          }
          break;
        case "create-portfolio":
          while(!validStockSymbol) {
            try {
              askForStock("create-portfolio");

              view.writeMessage("Please enter an available stock:");
              stockSymbol = scan.nextLine();

              view.writeMessage("Enter the number of shares to add:");
              numShares = scan.nextInt();

              view.writeMessage("Type DONE if done entering shares");
              status = scan.nextLine();
              if (status.equalsIgnoreCase("DONE")) {
                isDone = true;
              }
            } catch (IllegalArgumentException e) {
              view.writeMessage(e.getMessage());
            }
          }
        case "quit":
          quit = true;
          break;
        default:
          view.writeMessage("Undefined instructions: " + instructions + ". Please try again with " +
                  "valid instructions");
      }
    }

    view.farewell();

  }

  private void askForStock(String action) {
    view.writeMessage("Chose " + action);
    view.writeMessage("Input the stock you want:");
  }
}
