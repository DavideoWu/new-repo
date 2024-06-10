package view;

import java.util.List;

/**
 * The interface of the view.
 */
public interface View {
  void writeMessage(String message);

  void returnGainOrLoss(double[] gainOrLoss);

  void xDayCrossOverMessage(List<String[]> crossoverList);

  void xDayAverageMessage(double average);

  void portfolioMessage(double sum, String date);

  void welcomeMessage();

  void farewell();


}
