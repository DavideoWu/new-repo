package View;

import java.io.PrintStream;
import java.util.List;

public class ViewImp implements View {

  private PrintStream out;

  public ViewImp(PrintStream out) {
    this.out = out;
  }

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

  //writes a message outputted to the user.
  public void writeMessage(String message) {
    out.println(message);
  }

  public void XDayCrossOverMessage(List<String[]> crossoverList) {
    writeMessage("The following dates cross over the average:");
    for (String[] strings : crossoverList) {
      writeMessage(strings[0]);
    }
  }

  public void XDayAverageMessage(double average) {
    writeMessage("Average price:");
    writeMessage("" + average);
  }

  public void portfolioMessage(double sum, String date) {
    writeMessage("Cost of your portfolio at " + date + ":");
    writeMessage("" + sum);
  }

  public void welcomeMessage() {
    writeMessage("Welcome to Stocks!\n"
            + "These are the following actions you can perform: \n"
            + "gain-loss (Obtain the gain or loss of a stock over a period)\n"
            + "x-day-average (Obtain a stock's x-day moving average, starting at a date)\n"
            + "x-day-crossover (Obtain a stock's x-day crossovers over a period of time)\n"
            + "create-portfolio (create a portfolio of multiple stocks, and it's value on a day\n"
            + "quit (quits the program)");
  }

  public void farewell() {
    writeMessage("Thank you for using the program!");
  }


}

/**
 * Methods - appendable (append the result)
 * Each method is displaying a certain text.
 * Generic message
 * Appendable - displaying outputs to the user.
 */