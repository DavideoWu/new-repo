package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModelMock implements Model {

  final StringBuilder log;

  public ModelMock(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  public double[] getGainOrLoss(String stockSymbol, String startDate, String endDate) {
    log.append(String.format("stockSymbol: %s, startDate: %s, endDate: %s\n", stockSymbol,
            startDate, endDate));
    return new double[2];
  }

  public double getXDayAverage(String stockSymbol, String date, int daysBefore) {
    log.append(String.format("stockSymbol: %s, date: %s, daysBefore: %d\n", stockSymbol, date,
            daysBefore));
    return 0.0;
  }

  public List<String[]> getXDayCrossovers(String stockSymbol, String date, int daysBefore) {
    log.append(String.format("stockSymbol: %s, date: %s, daysBefore: %d\n", stockSymbol, date,
            daysBefore));
    return new ArrayList<String[]>();
  }

  public void createPortfolio(String stockSymbol, int numberOfShares) {
    log.append(String.format("stockSymbol: %s, numberOfShares: %d\n", stockSymbol, numberOfShares));
  }


  public double getPortfolioCost(String stockSymbol, int numberOfShares, String date) {
    log.append(String.format("stockSymbol: %s, numberOfShares: %d, date: %s\n", stockSymbol,
            numberOfShares, date));
    return 0.0;
  }


}
