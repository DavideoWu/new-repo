package model;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Stores the status of the stock.
 */
public interface Model {
  double[] getGainOrLoss(String stockSymbol, String startDate, String endDate);

  double getXDayAverage(String stockSymbol, String date, int daysBefore);

  List<String[]> getXDayCrossovers(String stockSymbol, String date, int daysBefore);

  void createPortfolio(String stockSymbol, String date, int numberOfShares);

  double getPortfolioCost(String stockSymbol, int numberOfShares, String date);

  void purchaseShares(String stockSymbol, int numberOfShares, String date);

  void sellShares(String stockSymbol, int numberOfShares, String date); //

  String getPortfolioComposition(String date); //

  String getDistributionPortfolioValue(String date); //

  String rebalancedPortfolioValue(List<Integer> percentList, String date); //

  String performanceOverTime(String stockSymbol, String startDate, String endDate);

  String portfolioPerformanceOvertime(String startDate, String endDate);

  Map<Stock, Integer> getPortfolio();

  String getString();

  void setString(String s);

  void savePortfolioAsFile(Map<Stock, Integer> portfolio, String fileName);

  void convertFileToPortfolio(String filepath) throws FileNotFoundException;
}