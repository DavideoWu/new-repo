package View;

import java.io.PrintStream;
import java.util.List;

public class ViewImp implements View {

  private PrintStream out;

  public void StockView(PrintStream out) {
    this.out = out;
  }

  public void returnGainOrLoss(double[] gainOrLoss) {
    double startPrice = gainOrLoss[0];
    double endPrice = gainOrLoss[1];
    out.println(startPrice);
    out.println(endPrice);
    if (startPrice > endPrice) {
      out.println("loss");
    } else if (startPrice < endPrice) {
      out.println("gain");
    }
  }

  //writes a message outputted to the user.
  public void writeMessage(String message) {
    out.println(message);
  }

  public void XDayCrossOverMessage(List<String[]> crossoverList) {
    out.println("The following dates cross over the average:");
    for (String[] strings : crossoverList) {
      out.println(strings[0]);
    }
  }

  public void XDayAverageMessage(double average) {
    out.println("Average price:");
    writeMessage("" + average);
  }

  public void welcomeMessage() {
    out.println("Welcome to Stocks!\n"
            + "These are the following actions you can perform: \n"
            + "gain-loss (Obtain the gain or loss of a stock over a period)\n"
            + "x-day-average (Obtain a stock's x-day moving average, starting at a date)\n"
            + "x-day-crossover (Obtain a stock's x-day crossovers over a period of time)\n"
            + "create-portfolio (create a portfolio of multiple stocks, and it's value on a day\n"
            + "quit (quits the program)");
  }


  public void askForStock(String action) {
    writeMessage(action);
  }
}

/**
 * Methods - appendable (append the result)
 * Each method is displaying a certain text.
 * Generic message
 * Appendable - displaying outputs to the user.
 */