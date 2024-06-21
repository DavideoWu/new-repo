package view;

import java.util.List;

import controller.Controller2;

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

  //void setListeners(Controller2 controller2, Controller2 controller21);
}
