package view;

import java.util.List;
import java.util.Objects;

/**
 * The view's mock, used to help the testing of the view.
 */
public class ViewMockImp implements view.View {

  final StringBuilder log;

  /**
   * The constructor of the view.
   * @param log The output.
   */
  public ViewMockImp(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }


  /**
   * Check if the stock gained or lost value.
   * @param gainOrLoss If the stock gained or lost value.
   */
  public void returnGainOrLoss(double[] gainOrLoss) {
    log.append("Successfully called returnGainOrLoss\n");
  }

  /**
   * writes a message outputted to the user.
   * @param message The message outputted to the user.
   */
  public void writeMessage(String message) {
    log.append("Successfully called writeMessage\n");
  }

  public void xDayCrossOverMessage(List<String[]> crossoverList) {
    log.append("Successfully called xDayCrossoverMessage\n");
  }

  public void xDayAverageMessage(double average) {
    log.append("Successfully called xDayAverageMessage\n");
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