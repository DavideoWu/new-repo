package model;

import java.util.List;

/**
 * Stores the status of the stock.
 */
public interface Model {
  double[] getGainOrLoss(String stockSymbol, String startDate, String endDate);

  double getXDayAverage(String stockSymbol, String date, int daysBefore);

  List<String[]> getXDayCrossovers(String stockSymbol, String date, int daysBefore);

  void createPortfolio(String stockSymbol, String date, int numberOfShares);

  void purchaseShares(String stockSymbol, int numberOfShares, String date);

  void sellShares(String stockSymbol, int numberOfShares, String date);

  String getPortfolioComposition(String date);

  double getPortfolioCost(String stockSymbol, int numberOfShares, String date);

  String getDistributionPortfolioValue(List<String> stockList, String date);

  String rebalancedPortfolioValue(List<Stock> stockList, List<Integer> percentList, String date);

  String portfolioPerformanceOvertime(String startDate, String endDate);

}