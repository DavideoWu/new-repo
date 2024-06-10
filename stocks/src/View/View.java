package View;

import java.util.List;

/**
<<<<<<< HEAD
 * Interface for view
=======
 * The interface of the view.
>>>>>>> 14de389 (added comments)
 */
public interface View {
  void writeMessage(String message);

  void returnGainOrLoss(double[] gainOrLoss);

  void XDayCrossOverMessage(List<String[]> crossoverList);

  void XDayAverageMessage(double average);

  void portfolioMessage(double sum, String date);

  void welcomeMessage();

  void farewell();


}
