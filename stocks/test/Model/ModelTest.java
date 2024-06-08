package Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Methods - appendable (append the result)
 * Each method is displaying a certain text.
 * Generic message
 * Appendable - displaying outputs to the user.
 */
public class ModelTest {

  ModelImp model = new ModelImp();


  @Test
  public void testGetGainOrLoss() {

    double[] modelGainOrLoss = model.getGainOrLoss("AAPL", "2024-05-24",
            "2024-05-20");
    double delta = 0.001;
    assertEquals(189.98, modelGainOrLoss[0], delta);
    assertEquals(191.0400, modelGainOrLoss[1], delta);

    try {
      model.getGainOrLoss("HELLO", "2023-03-03", "2023-02-29");
      fail("Should have caught illegal stockSymbol");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      model.getGainOrLoss("AAPL", "2024-06-10", "2023-11-11");
      fail("Should have caught illegal start date");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      model.getGainOrLoss("AAPL", "2024-05-16", "1500-05-05");
      fail("Should have caught illegal start date");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testGetXDayAverage() {

    double modelGetXDayAverage = model.getXDayAverage("AAPL", "2024-03-12", 3);

    assertEquals(172.23666666666668, modelGetXDayAverage, 0.001);

    try {
      model.getXDayAverage("GOOGLE", "2024-03-12", 5);
      fail("Should have caught illegal stock symbol");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      model.getXDayAverage("GOOGL", "2001-13-10", 5);
      fail("Should have caught illegal date");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      model.getXDayAverage("GOOGL", "2022-05-10", 10000);
      fail("Should have caught illegal days before");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testXDayCrossovers() {
    //average: 173.38444444444445
    List<String[]> expected = new ArrayList<>();
    expected.add("2024-03-04,176.1500,176.9000,173.7900,175.1000,81510101".split(","));
    expected.add("2024-03-01,179.5500,180.5300,177.3800,179.6600,73563082".split(","));
    expected.add("2024-02-29,181.2700,182.5700,179.5300,180.7500,136682597".split(","));



    List<String[]> modelGetXDayCrossovers = model.getXDayCrossovers("AAPL", "2024-03-12", 9);
    for (int i = 0; i < modelGetXDayCrossovers.size(); i++) {
      assertEquals(expected.get(i)[0], modelGetXDayCrossovers.get(i)[0]);
    }

    try {
      model.getXDayAverage("TEEESLA", "2021-08-10", 5);
      fail("Should have caught illegal stock symbol");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      model.getXDayAverage("TSLA", "2021-08-88", 5);
      fail("Should have caught illegal date");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      model.getXDayAverage("TSLA", "2021-08-11", 15324);
      fail("Should have caught illegal days before");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

  }

  @Test
  public void testPortfolio() {
    Map<String, Integer> resultPortfolio = new HashMap<>();
    resultPortfolio.put("AAPL", 5);
    resultPortfolio.put("MSFT", 7);
    resultPortfolio.put("BA", 3);

    model.createPortfolio("AAPL", 5);
    model.createPortfolio("MSFT", 7);
    model.createPortfolio("BA", 3);

    Map<String, Integer> actualPortfolio = model.getPortfolio();

    for (String stockKey: resultPortfolio.keySet()) {
      assertTrue(actualPortfolio.containsKey(stockKey));
      assertEquals(actualPortfolio.get(stockKey), resultPortfolio.get(stockKey));
    }

    //test getting value works
    double totalCost = model.getPortfolioCost("MSFT", 2, "2024-04-03");
    assertEquals(5187.06, totalCost, 0.001);
    //420.4500 * 9 + 169.6500*5 + 184.9200 * 3

    try {
      model.getPortfolioCost("MKROSOFT", 29, "2004-09-10");
      fail("Should have caught illegal stock symbol");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      model.getPortfolioCost("MSFT", Integer.MAX_VALUE, "2004-09-88");
      fail("Should have caught illegal date");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}