package Model;

public interface Model {
  void getGainOrLoss(String stockSymbol, String startDate, String endDate);

  void getXDayAverage(String stockSymbol, String date, int daysBefore);

  void getXDayCrossovers(String stockSymbol, String date, int daysBefore);

  void createPortfolio(String date);

}
