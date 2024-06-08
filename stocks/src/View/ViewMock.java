package View;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

/**
 * Methods - appendable (append the result)
 * Each method is displaying a certain text.
 * Generic message
 * Appendable - displaying outputs to the user.
 */
public class ViewMock implements View {

  final StringBuilder log;

  public ViewMock(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }


  public void returnGainOrLoss(double[] gainOrLoss) {
    log.append("Successfully called returnGainOrLoss\n");
  }

  //writes a message outputted to the user.
  public void writeMessage(String message) {
    log.append("Successfully called writeMessage\n");
  }

  public void XDayCrossOverMessage(List<String[]> crossoverList) {
    log.append("Successfully called XDayCrossoverMessage\n");
  }

  public void XDayAverageMessage(double average) {
    log.append("Successfully called XDayAverageMessage\n");
  }

  public void portfolioMessage(double sum, String date) {
    log.append("Successfully called portfolioMessage\n");
  }

  public void welcomeMessage() {
    log.append("Successfully called welcomeMessage\n");
  }

  public void farewell() {
    log.append("Successfully called farewell\n");
  }
}
