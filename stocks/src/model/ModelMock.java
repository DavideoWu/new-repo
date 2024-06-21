package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The model's mock, used to help the testing of the model.
 */
public class ModelMock implements Model {

  final StringBuilder log;

  /**
   * The constructor of the model.
   * @param log Records the output when the mock is called.
   */
  public ModelMock(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * Gets the gain or loss of the stock.
   * @param stockSymbol The symbol of the stock.
   * @param startDate The start date for the comparison.
   * @param endDate The end date for the comparison.
   * @return Whether the price change was a gain or loss.
   */
  public double[] getGainOrLoss(String stockSymbol, String startDate, String endDate) {
    log.append(String.format("stockSymbol: %s, startDate: %s, endDate: %s\n", stockSymbol,
            startDate, endDate));
    return new double[2];
  }

  /**
   * Gets the average of all the stocks from the amount of daysBefore to the date.
   * @param stockSymbol The symbol of the stock.
   * @param date The date of the stock.
   * @param daysBefore How many days before the date we should calculate the stock.
   * @return The average of the prices for all days from the amount of daysBefore to the date.
   */
  public double getXDayAverage(String stockSymbol, String date, int daysBefore) {
    log.append(String.format("stockSymbol: %s, date: %s, daysBefore: %d\n", stockSymbol, date,
            daysBefore));
    return 0.0;
  }

  /**
   * Gets the amount of days that are crossovers.
   * @param stockSymbol The symbol of the stock.
   * @param date The date of the stock.
   * @param daysBefore How many days before the date we should calculate the stock.
   * @return All the x-day crossovers.
   * @throws IllegalArgumentException If the x-day average is invalid or does not exist.
   */
  public List<String[]> getXDayCrossovers(String stockSymbol, String date, int daysBefore) {
    log.append(String.format("stockSymbol: %s, date: %s, daysBefore: %d\n", stockSymbol, date,
            daysBefore));
    return new ArrayList<>();
  }

  /**
   * Creates the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param date Date portfolio is created.
   * @param numberOfShares The number of shares each stock has.
   */
  public void createPortfolio(String stockSymbol, String date, int numberOfShares) {
    log.append(String.format("stockSymbol: %s, date: %s, numberOfShares: %d\n",
            stockSymbol, date, numberOfShares));
  }

  /**
   * Purchase a specific number of shares of a specific stock on a specified date,
   *     and add them to the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares to buy.
   * @param date The date of the stock.
   */
  public void purchaseShares(String stockSymbol, int numberOfShares, String date) {
    log.append(String.format("stockSymbol: %s, numberOfShares: %d, date: %s\n", stockSymbol,
            numberOfShares, date));
  }

  /**
   * Sell a specific number of shares of a specific stock
   *     on a specified date from a given portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares Number of shares to sell.
   * @param date The date of the stock.
   */
  public void sellShares(String stockSymbol, int numberOfShares, String date) {
    log.append(String.format("stockSymbol: %s, numberOfShares: %d, date: %s\n", stockSymbol,
            numberOfShares, date));
  }


  /**
   * Gets the composition of the portfolio, consisting of a list of stocks
   *     and the number of shares of each stock.
   * @param date The date we want to see the composition of the portfolio.
   * @return The list of stocks, and the number of shares of each stock.
   */
  public String getPortfolioComposition(String date) {
    log.append(String.format("date: %s\n", date));
    return log.toString();
  }


  /**
   * Gets the cost of the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares each stock has.
   * @param date The date of the stock.
   * @return The cost of the portfolio.
   */
  public double getPortfolioCost(String stockSymbol, int numberOfShares, String date) {
    log.append(String.format("stockSymbol: %s, numberOfShares: %d, date: %s\n", stockSymbol,
            numberOfShares, date));
    return 0.0;
  }

  @Override
  public String getDistributionPortfolioValue(String date) {
    log.append(String.format("date: %s\n", date));
    return log.toString();
  }

  @Override
  public String rebalancedPortfolioValue(List<Integer> percentList, String date) {
    log.append(String.format("percentList: %s, date: %s\n", percentList, date));
    return log.toString();
  }

  @Override
  public String performanceOverTime(String stockSymbol, String startDate, String endDate) {
    log.append(String.format("stockSymbol: %s, startDate: %s, endDate: %s\n",
            stockSymbol, startDate, endDate));
    return log.toString();
  }

  @Override
  public String portfolioPerformanceOvertime(String startDate, String endDate) {
    log.append(String.format("startDate: %s, endDate: %s\n", startDate, endDate));
    return log.toString();
  }

  @Override
  public Map<Stock, Integer> getPortfolio() {
    return null;
  }

  @Override
  public void setString(String s) {
    //empty
  }

  @Override
  public void savePortfolioAsFile(Map<Stock, Integer> portfolio, String fileName) {
    //empty
  }

  @Override
  public void convertFileToPortfolio(String filepath) {
    //empty
  }

  @Override
  public String getString() {
    return "";
  }

}