package controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Model;
import view.View;

/**
 * The class that connects the status to the input and output the user sees.
 */
public class Controller implements controller.ControllerInterface {

  private final Model model;
  private final View view;
  private Readable in;

  /**
   * The constructor of the controller.
   * @param model The model.
   * @param view The view.
   * @param in The input.
   */
  public Controller(Model model, View view, Readable in) {
    this.model = model;
    this.view = view;
    this.in = in;
  }


  /**
   * Executes the program, and goes through all the possible cases the user may encounter.
   * @throws IllegalStateException when null.
   */
  public void control() throws IllegalArgumentException {
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

    List<Integer> portfolio = new ArrayList<>();

    int weight = 0;

    //if quit = true, then exit program
    boolean quit = false;

    //checks if repeat due to invalid entry
    boolean validStockSymbol = false;

    //used in portfolio to determine if done
    boolean isDone = false;

    while (!quit) {
      if (!scan.hasNextLine()) {
        break;
      }
      instructions = scan.nextLine();

      switch (instructions) {
        case "gain-loss":
          while (!validStockSymbol) {
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
          quit = true;
          break;
        case "x-day-average":
          while (!validStockSymbol) {
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
              System.out.println(average);

              view.xDayAverageMessage(average);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              view.writeMessage(e.getMessage());
            }
          }
          quit = true;
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

              view.writeMessage("Obtaining crossovers of  " + stockSymbol + " from " + date
                      + " going back to " + numDaysBefore + ".");
              List<String[]> xDayCrossoverList = model.getXDayCrossovers(stockSymbol, date,
                      numDaysBefore);
              view.xDayCrossOverMessage(xDayCrossoverList);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              view.writeMessage(e.getMessage());
            }
          }
          quit = true;
          break;
        case "create-portfolio":
          while (!validStockSymbol) {
            while (!isDone) {
              try {
                askForStock("create-portfolio");

                stockSymbol = scan.nextLine();

                view.writeMessage("Enter the number of shares to add:");
                numShares = scan.nextInt();
                scan.nextLine();
                if (numShares <= 0) {
                  throw new IllegalArgumentException("Must enter positive number of shares");
                }

                view.writeMessage("Type DONE if done entering shares. Otherwise, type anything to "
                        + "continue entering ");

                status = scan.nextLine();

                if (status.equalsIgnoreCase("DONE")) {

                  view.writeMessage("Enter a date");
                  date = scan.nextLine();

                  double portfolioSum = model.getPortfolioCost(stockSymbol, numShares, date);

                  view.portfolioMessage(portfolioSum, date);
                  isDone = true;
                  validStockSymbol = true;

                } else {
                  view.writeMessage("Enter a date");
                  date = scan.nextLine();
                  model.createPortfolio(stockSymbol, date, numShares);
                }
              } catch (IllegalArgumentException e) {
                view.writeMessage(e.getMessage());
              }
            }
          }
          quit = true;
          break;
        case "purchase shares":
          while (!validStockSymbol) {
            while (!isDone) {
              try {
                askForStock("purchase shares");

                stockSymbol = scan.nextLine();

                view.writeMessage("Enter the number of shares to buy:");
                numShares = scan.nextInt();
                scan.nextLine();
                if (numShares <= 0) {
                  throw new IllegalArgumentException("Must enter positive number of shares");
                }

                view.writeMessage("Enter a date");
                date = scan.nextLine();

                view.writeMessage("Type DONE if done entering shares. Otherwise, type anything to "
                        + "continue entering ");

                status = scan.nextLine();

                if (status.equalsIgnoreCase("DONE")) {

                  double portfolioSum = model.getPortfolioCost(stockSymbol, numShares, date);

                  view.portfolioMessage(portfolioSum, date);
                  isDone = true;
                  validStockSymbol = true;

                } else {
                  model.purchaseShares(stockSymbol, numShares, date);
                }
              } catch (IllegalArgumentException e) {
                view.writeMessage(e.getMessage());
              }
            }
          }
          quit = true;
          break;
        case "sell shares":
          while (!validStockSymbol) {
            while (!isDone) {
              try {
                askForStock("sell shares");

                stockSymbol = scan.nextLine();

                view.writeMessage("Enter the number of shares to sell:");
                numShares = scan.nextInt();
                scan.nextLine();
                if (numShares <= 0) {
                  throw new IllegalArgumentException("Must enter positive number of shares");
                }

                view.writeMessage("Enter a date");
                date = scan.nextLine();

                view.writeMessage("Type DONE if done entering shares. Otherwise, type anything to "
                        + "continue entering ");

                status = scan.nextLine();

                if (status.equalsIgnoreCase("DONE")) {

                  double portfolioSum = model.getPortfolioCost(stockSymbol, numShares, date);

                  view.portfolioMessage(portfolioSum, date);
                  isDone = true;
                  validStockSymbol = true;

                } else {
                  model.sellShares(stockSymbol, numShares, date);
                }
              } catch (IllegalArgumentException e) {
                view.writeMessage(e.getMessage());
              }
            }
          }
          quit = true;
          break;
        //        case "rebalanced portfolio value":
        //          while (!validStockSymbol) {
        //            while (!isDone) {
        //              try {
        //                view.writeMessage("List of weight values:");
        //
        //                weight = scan.nextInt();
        //                scan.nextLine();
        //                if (weight <= 0) {
        //                  throw new IllegalArgumentException("Must enter positive number");
        //                }
        //
        //                view.writeMessage("Enter a date");
        //                date = scan.nextLine();
        //
        //                view.writeMessage("Type DONE if done entering shares. Otherwise,
        //                type anything to "
        //                        + "continue entering ");
        //
        //                status = scan.nextLine();
        //
        //                if (status.equalsIgnoreCase("DONE")) {
        //
        //                  double portfolioSum = model.getPortfolioCost(stockSymbol,
        //                  numShares, date);
        //
        //                  view.portfolioMessage(portfolioSum, date);
        //                  isDone = true;
        //                  validStockSymbol = true;
        //
        //                } else {
        //                  model.sellShares(stockSymbol, numShares, date);
        //                }
        //              } catch (IllegalArgumentException e) {
        //                view.writeMessage(e.getMessage());
        //              }
        //            }
        //          }
        //          quit = true;
        //          break;
        case "quit":
          quit = true;
          break;
        default:
          view.writeMessage("Undefined instructions: " + instructions + ". Please try again with "
                  + "valid instructions");
      }
    }

    view.farewell();

  }

  private void askForStock(String action) {
    view.writeMessage("Chose " + action);
    view.writeMessage("Input the stock you want:");
  }
}