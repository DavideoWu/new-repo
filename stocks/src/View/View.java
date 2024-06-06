package View;

import java.util.List;

public interface View {
  void writeMessage(String message);

  void returnGainOrLoss(double[] gainOrLoss);

  void XDayCrossOverMessage(List<String[]> crossoverList);

  void XDayAverageMessage(double average);

  void portfolioMessage(double sum, String date);

  void welcomeMessage();

  void farewell();


}
