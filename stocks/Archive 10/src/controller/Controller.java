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

    // Scanner that takes in inputs
    Scanner scan = new Scanner(in);

    // Variables for handling inputs
    String instructions;
    String stockSymbol;
    String status;
    String date;
    int numDaysBefore;
    int numShares;
    int percentNum;

    boolean quit = false;
    boolean validStockSymbol;
    boolean isDone;

    while (!quit) {
      // Prompt for new instructions
      view.writeMessage("Enter a command or 'quit' to exit:");
      if (!scan.hasNextLine()) {
        break;
      }
      instructions = scan.nextLine().trim();

      // Reset flags for each iteration
      validStockSymbol = false;
      isDone = false;

      switch (instructions) {
        case "gain-loss":
          while (!validStockSymbol) {
            try {
              askForStock("gain-loss");

              stockSymbol = scan.nextLine();

              view.writeMessage("Enter a start date:");
              String startDate = scan.nextLine();

              view.writeMessage("Enter an end date:");
              String endDate = scan.nextLine();

              view.writeMessage("Obtaining gain or loss for " + stockSymbol + " from " + startDate
                      + " to " + endDate + ".");

              double[] finalPrices = model.getGainOrLoss(stockSymbol, startDate, endDate);

              view.returnGainOrLoss(finalPrices);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              view.writeMessage(e.getMessage());
            }
          }
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
                scan.nextLine();  // Consume the newline character
              } catch (InputMismatchException e) {
                scan.nextLine();  // Clear invalid input
                throw new IllegalArgumentException("Enter a valid number of days.");
              }

              view.writeMessage("Obtaining average of " + stockSymbol + " from " + startDate
                      + " going back to " + numDaysBefore + " days before.");

              double average = model.getXDayAverage(stockSymbol, startDate, numDaysBefore);

              view.xDayAverageMessage(average);
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
                scan.nextLine();  // Consume the newline character
              } catch (InputMismatchException e) {
                scan.nextLine();  // Clear invalid input
                throw new IllegalArgumentException("Enter a valid number of days.");
              }

              view.writeMessage("Obtaining crossovers of " + stockSymbol + " from " + date
                      + " going back to " + numDaysBefore + ".");
              List<String[]> xDayCrossoverList = model.getXDayCrossovers(stockSymbol, date,
                      numDaysBefore);
              view.xDayCrossOverMessage(xDayCrossoverList);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              view.writeMessage(e.getMessage());
            }
          }
          break;
        case "create-portfolio":
          while (!validStockSymbol) {
            while (!isDone) {
              try {
                askForStock("create-portfolio");

                stockSymbol = scan.nextLine();

                view.writeMessage("Enter the number of shares to add:");
                numShares = scan.nextInt();
                scan.nextLine();  // Consume the newline character
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
          break;
        case "purchase-shares":
          while (!validStockSymbol) {
            while (!isDone) {
              try {
                askForStock("purchase shares");

                stockSymbol = scan.nextLine();

                view.writeMessage("Enter the number of shares to buy:");
                numShares = scan.nextInt();
                scan.nextLine();  // Consume the newline character
                if (numShares <= 0) {
                  throw new IllegalArgumentException("Must enter positive number of shares");
                }

                view.writeMessage("Enter a date");
                date = scan.nextLine();

                view.writeMessage("Type DONE if done entering shares. Otherwise, type anything to "
                        + "continue entering ");

                status = scan.nextLine();

                if (status.equalsIgnoreCase("DONE")) {
                  model.purchaseShares(stockSymbol, numShares, date);

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
          break;
        case "sell-shares":
          while (!validStockSymbol) {
            while (!isDone) {
              try {
                askForStock("sell shares");

                stockSymbol = scan.nextLine();

                view.writeMessage("Enter the number of shares to sell:");
                numShares = scan.nextInt();
                scan.nextLine();  // Consume the newline character
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
                if (e.getMessage().equals("Error: Empty portfolio")) {
                  view.writeMessage(e.getMessage());
                  break;
                } else {
                  view.writeMessage(e.getMessage());
                }
              }
            }
          }
          break;
        case "portfolio-composition":
          while (!validStockSymbol) {
            try {
              view.writeMessage("Got portfolio composition");

              view.writeMessage("Enter a date");
              date = scan.nextLine();

              String composition = model.getPortfolioComposition(date);
              view.writeMessage(composition);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Error: Empty portfolio")) {
                view.writeMessage(e.getMessage());
                break;
              } else {
                view.writeMessage(e.getMessage());
              }
            }
          }
          break;
        case "rebalance-portfolio":
          while (!validStockSymbol) {
            try {
              view.writeMessage("Got re-balance portfolio");

              view.writeMessage("Enter a date");
              date = scan.nextLine();

              List<Integer> percentList = new ArrayList<Integer>();

              for (int i = 0; i < model.getPortfolio().size(); i++) {
                view.writeMessage("Enter a percent");
                percentNum = scan.nextInt();
                scan.nextLine();
                percentList.add(percentNum);
              }

              String output = model.rebalancedPortfolioValue(percentList, date);

              view.writeMessage(output);


            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Error: Empty portfolio")) {
                view.writeMessage(e.getMessage());
                break;
              } else {
                view.writeMessage(e.getMessage());
              }
            }
          }
          break;
        case "distribution-portfolio-value":
          while (!validStockSymbol) {
            try {
              view.writeMessage("distribution portfolio value");

              view.writeMessage("Enter a date");
              date = scan.nextLine();

              String distribution = model.getDistributionPortfolioValue(date);
              view.writeMessage(distribution);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Error: Empty portfolio")) {
                view.writeMessage(e.getMessage());
                break;
              } else {
                view.writeMessage(e.getMessage());
              }
            }
          }
          break;
        case "stock-performance":
          while (!validStockSymbol) {
            try {
              askForStock("stock performance");

              stockSymbol = scan.nextLine();

              view.writeMessage("Enter a start date");
              String startDate = scan.nextLine();

              view.writeMessage("Enter an end date");
              String endDate = scan.nextLine();

              String stockPerformance = model.performanceOverTime(stockSymbol, startDate, endDate);
              view.writeMessage(stockPerformance);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Error: Portfolio is empty")) {
                view.writeMessage(e.getMessage());
                break;
              } else {
                view.writeMessage(e.getMessage());
              }
            }
          }
          break;
        case "portfolio-performance":
          while (!validStockSymbol) {
            try {
              view.writeMessage("Chose portfolio-performance");

              view.writeMessage("Enter a start date");
              String startDate = scan.nextLine();

              view.writeMessage("Enter an end date");
              String endDate = scan.nextLine();

              String performance = model.portfolioPerformanceOvertime(startDate, endDate);
              view.writeMessage(performance);
              validStockSymbol = true;
            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Error: Portfolio is empty")) {
                view.writeMessage(e.getMessage());
                break;
              } else {
                view.writeMessage(e.getMessage());
              }
            }
          }
          break;
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
