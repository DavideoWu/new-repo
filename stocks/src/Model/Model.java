package Model;

import java.util.List;

/**
 * Stores the status of the stock.
 */
public interface Model {
  double[] getGainOrLoss(String stockSymbol, String startDate, String endDate);

  double getXDayAverage(String stockSymbol, String date, int daysBefore);

  List<String[]> getXDayCrossovers(String stockSymbol, String date, int daysBefore);

  void createPortfolio(String stockSymbol, int numberOfShares);

  double getPortfolioCost(String stockSymbol, int numberOfShares, String date);
}