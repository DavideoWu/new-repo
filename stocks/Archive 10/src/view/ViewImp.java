package view;

import java.io.PrintStream;
import java.util.List;

/**
 * The class that displays the text and status to the user.
 */
public class ViewImp implements view.View {

  private PrintStream out;

  /**
   * The constructor of the view.
   * @param out The output.
   */
  public ViewImp(PrintStream out) {
    this.out = out;
  }

  /**
   * Check if the stock gained or lost value.
   * @param gainOrLoss If the stock gained or lost value.
   */
  public void returnGainOrLoss(double[] gainOrLoss) {
    double startPrice = gainOrLoss[0];
    double endPrice = gainOrLoss[1];
    writeMessage("Start price: " + startPrice);
    writeMessage("End price: " + endPrice);
    if (startPrice > endPrice) {
      writeMessage("loss");
    } else if (startPrice < endPrice) {
      writeMessage("gain");
    }
  }

  /**
   * writes a message outputted to the user.
   * @param message The message outputted to the user.
   */
  public void writeMessage(String message) {
    out.println(message);
  }

  /**
   * Display all the crossover days.
   * @param crossoverList The list containing all the crossover days.
   */
  public void xDayCrossOverMessage(List<String[]> crossoverList) {
    writeMessage("The following dates cross over the average:");
    for (String[] strings : crossoverList) {
      writeMessage(strings[0]);
    }
  }

  /**
   * Gets the average of all the stock prices.
   * @param average The average of all the stock prices.
   */
  public void xDayAverageMessage(double average) {
    writeMessage("Average price:");
    writeMessage("" + average);
  }

  /**
   * Gets the message for the portfolio.
   * @param sum sum of all stock prices in the portfolio.
   * @param date the date we want to look at the stock prices at.
   */
  public void portfolioMessage(double sum, String date) {
    writeMessage("Cost of your portfolio at " + date + ":");
    writeMessage("" + sum);
  }

  /**
   * Displays the welcome message.
   */
  public void welcomeMessage() {
    writeMessage("Welcome to Stocks!\n"
            + "These are the following actions you can perform: \n"
            + "gain-loss (Obtain the gain or loss of a stock over a period)\n"
            + "x-day-average (Obtain a stock's x-day moving average, starting at a date)\n"
            + "x-day-crossover (Obtain a stock's x-day crossovers over a period of time)\n"
            + "create-portfolio (create a portfolio of multiple stocks, and it's value on a day\n"
            + "purchase-shares (purchase a number of shares on a day)\n"
            + "sell-shares (sell a number of shares on a day)\n"
            + "rebalance-portfolio (give some percentages to portfolio to rebalance it)\n"
            + "portfolio-composition (get the composition of a portfolio)\n"
            + "distribution-portfolio-value (distribution of the portfolio)\n"
            + "stock-performance (performance of a stock over time)\n"
            + "portfolio-performance\n"
            + "quit (quits the program)");
  }

  /**
   * Displays the farewell.
   */
  public void farewell() {
    writeMessage("Thank you for using the program!");
  }


}