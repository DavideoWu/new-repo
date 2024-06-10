package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

<<<<<<< HEAD
=======
/**
 * The model's mock, used to help the testing of the model.
 */
>>>>>>> 14de389 (added comments)
public class ModelMock implements Model {

  final StringBuilder log;

<<<<<<< HEAD
=======
  /**
   * The constructor of the model.
   * @param log Records the output when the mock is called.
   */
>>>>>>> 14de389 (added comments)
  public ModelMock(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

<<<<<<< HEAD
=======
  /**
   * Gets the gain or loss of the stock.
   * @param stockSymbol The symbol of the stock.
   * @param startDate The start date for the comparison.
   * @param endDate The end date for the comparison.
   * @return Whether the price change was a gain or loss.
   */
>>>>>>> 14de389 (added comments)
  public double[] getGainOrLoss(String stockSymbol, String startDate, String endDate) {
    log.append(String.format("stockSymbol: %s, startDate: %s, endDate: %s\n", stockSymbol,
            startDate, endDate));
    return new double[2];
  }

<<<<<<< HEAD
=======
  /**
   * Gets the average of all the stocks from the amount of daysBefore to the date.
   * @param stockSymbol The symbol of the stock.
   * @param date The date of the stock.
   * @param daysBefore How many days before the date we should calculate the stock.
   * @return The average of the prices for all days from the amount of daysBefore to the date.
   */
>>>>>>> 14de389 (added comments)
  public double getXDayAverage(String stockSymbol, String date, int daysBefore) {
    log.append(String.format("stockSymbol: %s, date: %s, daysBefore: %d\n", stockSymbol, date,
            daysBefore));
    return 0.0;
  }

<<<<<<< HEAD
=======
  /**
   * Gets the amount of days that are crossovers.
   * @param stockSymbol The symbol of the stock.
   * @param date The date of the stock.
   * @param daysBefore How many days before the date we should calculate the stock.
   * @return All the x-day crossovers.
   * @throws IllegalArgumentException If the x-day average is invalid or does not exist.
   */
>>>>>>> 14de389 (added comments)
  public List<String[]> getXDayCrossovers(String stockSymbol, String date, int daysBefore) {
    log.append(String.format("stockSymbol: %s, date: %s, daysBefore: %d\n", stockSymbol, date,
            daysBefore));
    return new ArrayList<String[]>();
  }

<<<<<<< HEAD
=======
  /**
   * Creates the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares each stock has.
   */
>>>>>>> 14de389 (added comments)
  public void createPortfolio(String stockSymbol, int numberOfShares) {
    log.append(String.format("stockSymbol: %s, numberOfShares: %d\n", stockSymbol, numberOfShares));
  }


<<<<<<< HEAD
=======
  /**
   * Gets the cost of the portfolio.
   * @param stockSymbol The symbol of the stock.
   * @param numberOfShares The number of shares each stock has.
   * @param date The date of the stock.
   * @return The cost of the portfolio.
   */
>>>>>>> 14de389 (added comments)
  public double getPortfolioCost(String stockSymbol, int numberOfShares, String date) {
    log.append(String.format("stockSymbol: %s, numberOfShares: %d, date: %s\n", stockSymbol,
            numberOfShares, date));
    return 0.0;
  }


<<<<<<< HEAD
}
=======
}
>>>>>>> 14de389 (added comments)
