package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

/**
 * The test of the model.
 */
public class ModelTest {

  model.ModelImp model = new model.ModelImp();


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

  /**
   * Purchase a specific number of shares of a specific stock on a specific date,
   * and add them to the portfolio.
   */
  @Test
  public void testPurchaseShares() {
    model.purchaseShares("AAPL", 5, "2024-04-03");
    assertEquals(5, (int) model.getPortfolio().get(model.getStock("AAPL", "2024-04-03")));
  }

  @Test
  public void testPurchaseSharesToExisingStockDifferentDate() {
    model.createPortfolio("MSFT", "2024-02-02", 7);
    model.purchaseShares("MSFT", 2, "2024-04-02");
    assertEquals(2, (int) model.getPortfolio().get(model.getStock("MSFT", "2024-04-02")));
  }

  @Test
  public void testPurchaseSharesToExisingStockSameDate() {
    model.createPortfolio("MSFT", "2024-02-02", 7);
    model.purchaseShares("MSFT", 2, "2024-02-02");
    assertEquals(9, (int) model.getPortfolio().get(model.getStock("MSFT", "2024-02-02")));
  }

  @Test
  public void testSellShares() {
    model.createPortfolio("AAPL", "2023-03-21",5);
    model.sellShares("AAPL", 3, "2023-03-21");
    assertEquals(2, (int) model.getPortfolio()
            .get(model.getStock("AAPL", "2023-03-21")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellSharesRemoveStock() {
    model.createPortfolio("AAPL", "2024-04-03", 5);
    model.sellShares("AAPL", 5, "2024-04-03");
    assertNull(model.getPortfolio().get(model.getStock("AAPL", "2024-04-03")));
  }

  @Test
  public void testPortfolioComposition() {
    // List of stocks and number of shares of each stock.
    model.createPortfolio("MSFT", "2023-09-01",7);
    model.createPortfolio("AAPL", "2023-09-01", 5);
    model.createPortfolio("BA", "2023-09-01",3);


    // testing composition
    String expectedComposition = "The composition of the portfolio on 2024-04-03 is:\n"
            + "Stock: MSFT, Number of shares: 7.\n"
            + "Stock: AAPL, Number of shares: 5.\n"
            + "Stock: BA, Number of shares: 3.";
    assertEquals(expectedComposition, model.getPortfolioComposition("2024-04-03"));

    // tests selling changing composition
    model.sellShares("AAPL", 3, "2023-09-01");
    String expectedComposition2 = "The composition of the portfolio on 2024-04-04 is:\n"
            + "Stock: MSFT, Number of shares: 7.\n"
            + "Stock: AAPL, Number of shares: 2.\n"
            + "Stock: BA, Number of shares: 3.";
    assertEquals(expectedComposition2, model.getPortfolioComposition("2024-04-04"));
    assertEquals(2, (int) model.getPortfolio()
            .get(model.getStock("AAPL", "2023-09-01")));

    // tests purchasing changing composition
    model.purchaseShares("MSFT", 2, "2024-04-05");
    String expectedComposition3 = "The composition of the portfolio on 2024-04-05 is:\n"
            + "Stock: MSFT, Number of shares: 9.\n"
            + "Stock: AAPL, Number of shares: 2.\n"
            + "Stock: BA, Number of shares: 3.";
    assertEquals(expectedComposition3, model.getPortfolioComposition("2024-04-05"));

    model.sellShares("AAPL", 2, "2023-09-01");
    String expectedComposition4 = "The composition of the portfolio on 2024-04-08 is:\n"
            + "Stock: MSFT, Number of shares: 9.\n"
            + "Stock: BA, Number of shares: 3.";
    assertEquals(expectedComposition4, model.getPortfolioComposition("2024-04-08"));

    try {
      model.getPortfolioComposition("2024-04-90");
      fail("Should have caught illegal date");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testPortfolioComposition2() {
    model.createPortfolio("MSFT", "2023-09-01",7);
    model.createPortfolio("AAPL", "2023-09-01", 5);
    model.createPortfolio("BA", "2023-09-01",3);
    model.sellShares("AAPL", 3, "2023-09-01");
    String expectedComposition = "The composition of the portfolio on 2023-09-01 is:\n"
            + "Stock: MSFT, Number of shares: 7.\n"
            + "Stock: AAPL, Number of shares: 2.\n"
            + "Stock: BA, Number of shares: 3.";

    assertEquals(expectedComposition, model.getPortfolioComposition("2023-09-01"));
  }

  @Test
  public void testPortfolioCost() {
    //test getting value works

    // the value should be zero before the date of its first purchase.
    double totalCost0 = model.getPortfolioCost(
            "MSFT", 0, "2004-04-02");
    assertEquals(0, totalCost0, 0.001);

    Map<String, Integer> resultPortfolio = new HashMap<>();
    resultPortfolio.put("AAPL", 5);
    resultPortfolio.put("MSFT", 7);
    resultPortfolio.put("BA", 3);

    model.createPortfolio("AAPL", "2003-03-12",5);
    model.createPortfolio("MSFT", "2003-04-11", 7);
    model.createPortfolio("BA", "2003-06-11",3);

    Map<Stock, Integer> actualPortfolio = model.getPortfolio();

//    for (String stockKey: resultPortfolio.keySet()) {
//      assertTrue(actualPortfolio.containsKey(stockKey));
//      assertEquals(actualPortfolio.get(stockKey), resultPortfolio.get(stockKey));
//    }

    //test getting value works
    double totalCost = model.getPortfolioCost(
            "MSFT", 2, "2024-04-03");
    //420.4500 * 9 + 169.6500*5 + 184.9200 * 3
    assertEquals(5187.06, totalCost, 0.001);

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

  @Test
  public void testPortfolioCost2() {
    //test getting value works


    // the value should be zero before the date of its first purchase.
    double totalCost0 = model.getPortfolioCost(
            "MSFT", 0, "2004-04-02");
    assertEquals(0, totalCost0, 0.001);

    Map<String, Integer> resultPortfolio = new HashMap<>();
    resultPortfolio.put("AAPL", 1);
    resultPortfolio.put("MSFT", 1);
    resultPortfolio.put("BA", 1);

    model.createPortfolio("AAPL", "2005-11-09",1);
    model.createPortfolio("MSFT", "2014-02-20",1);
    model.createPortfolio("BA", "2015-02-03",1);

    Map<Stock, Integer> actualPortfolio = model.getPortfolio();

    //test getting value works
    double totalCost = model.getPortfolioCost(
            "AAPL", 0, "2024-04-03");
    //420.4500 * 9 + 169.6500*5 + 184.9200 * 3
    assertEquals(775.02, totalCost, 0.001);

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


  @Test
  public void testPortfolioCost3() {
    //test getting value works

    // the value should be zero before the date of its first purchase.
    double totalCost0 = model.getPortfolioCost(
            "MSFT", 0, "2004-04-02");
    assertEquals(0, totalCost0, 0.001);

    Map<String, Integer> resultPortfolio = new HashMap<>();
    resultPortfolio.put("AAPL", 2);
    resultPortfolio.put("MSFT", 3);
    resultPortfolio.put("BA", 1);

    model.createPortfolio("AAPL", "2006-02-01",2);
    model.createPortfolio("MSFT", "2005-02-22",3);
    model.createPortfolio("BA", "2005-02-15",1);

    Map<Stock, Integer> actualPortfolio = model.getPortfolio();

//    for (String stockKey: resultPortfolio.keySet()) {
//      assertTrue(actualPortfolio.containsKey(stockKey));
//      assertEquals(actualPortfolio.get(stockKey), resultPortfolio.get(stockKey));
//    }

    //test getting value works
    double totalCost = model.getPortfolioCost(
            "AAPL", 0, "2024-04-03");
    //420.4500 * 3 + 169.6500*2 + 184.9200 * 1
    assertEquals(1785.57, totalCost, 0.001);

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

  @Test
  public void testDistributionPortfolioValue() {
    Stock mkroft = new Stock("MSFT", "2006-02-09");
    Stock aapl = new Stock("AAPL", "2006-06-01");
    Stock ba = new Stock("BA", "2006-02-03");

    model.createPortfolio(mkroft.getStockSymbol(), "2006-02-09",7);
    model.createPortfolio(aapl.getStockSymbol(), "2006-06-01",5);
    model.createPortfolio(ba.getStockSymbol(), "2006-02-03", 3);

    String expectedDistribution = "The distribution of the value "
            + "of the portfolio on 2024-04-03 is: \n"
            + "Stock: BA, Value is: 554.76.\n"
            + "Stock: AAPL, Value is: 848.25.\n"
            + "Stock: MSFT, Value is: 2943.15.";
    List<String> stockList = List.of("BA", "AAPL", "MSFT");
    assertEquals(expectedDistribution, model.getDistributionPortfolioValue(stockList,"2024-04-03"));
  }

  @Test
  public void testRebalancedPortfolioValue() {

    // list of stock symbols
    // list of percentages
    //

    Stock aapl = new Stock("AAPL", "2024-04-03");
    Stock ba = new Stock("BA", "2024-04-03");
    Stock mkroft = new Stock("MSFT", "2024-04-03");

    model.createPortfolio(aapl.getStockSymbol(), "2024-04-03",5);
    model.createPortfolio(ba.getStockSymbol(), "2024-04-03",3);
    model.createPortfolio(mkroft.getStockSymbol(), "2024-04-03",7);


//    model.createPortfolio("AAPL", "2024-04-03",5);
//    model.createPortfolio("BA", "2024-04-03",3);
//    model.createPortfolio("MSFT", "2024-04-03", 7);

    String expectedDistribution = "The rebalanced distribution of the value "
            + "of the portfolio on 2024-04-03 is: \n"
            + "Stock: AAPL, Value is: 1303.848.\n"
            + "Stock: BA, Value is: 1303.848.\n"
            + "Stock: MSFT, Value is: 1738.464.";

    // percentList(30, 40, 30)
    List<Stock> stocks = List.of(aapl, ba, mkroft);
    List<Integer> percents = List.of(30, 30, 40);
    assertEquals(expectedDistribution, model.rebalancedPortfolioValue(stocks, percents,"2024-04-03"));
  }

//  @Test(expected = IllegalArgumentException.class)
//  public void testRebalancedPortfolioValueInvalidPercentSum() {
//    model.createPortfolio("MSFT", "2024-04-02", 7);
//    model.createPortfolio("AAPL", "2003-03-01",5);
//    model.createPortfolio("BA", "2023-12-12",3);
//
//    String expectedDistribution = "The rebalanced distribution of the value "
//            + "of the portfolio on 2024-04-03 is: \n"
//            + "Stock: MSFT, Value is: 882.945.\n"
//            + "Stock: AAPL, Value is: 339.3.\n"
//            + "Stock: BA, Value is: 166.428.";
//    // percentList(30, 40, 40)
//    List<Integer> percents = List.of(30, 40, 40);
//    assertEquals(expectedDistribution, model.rebalancedPortfolioValue(percents, "2024-04-03"));
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testRebalancedPortfolioValueInvalidDate() {
//    model.createPortfolio("MSFT", "2020-03-13",7);
//    model.createPortfolio("AAPL", "2000-09-09",5);
//    model.createPortfolio("BA", "2019-02-21",3);
//
//    String expectedDistribution = "The rebalanced distribution of the value "
//            + "of the portfolio on 2024-04-03 is: \n"
//            + "Stock: MSFT, Value is: 882.945.\n"
//            + "Stock: AAPL, Value is: 339.3.\n"
//            + "Stock: BA, Value is: 166.428.";
//    // percentList(30, 40, 30)
//    List<Integer> percents = List.of(30, 40, 40);
//    assertEquals(expectedDistribution, model.rebalancedPortfolioValue(percents, "2024-04-90"));
//  }

  @Test
  public void testPerformanceOverTimeAAPL() {
    model.createPortfolio("AAPL", "2023-02-01", 5);

    String expected = "The performance over time of the AAPL stock is: \n"
            + "Date: 2024-04-03, Value is: ************************************************.\n"
            + "Date: 2024-04-04, Value is: ********************************************.\n"
            + "Date: 2024-04-05, Value is: ***********************************************.\n"
            + "Date: 2024-04-08, Value is: ******************************************.";

    assertEquals(expected, model.performanceOverTime(
            "AAPL", "2024-04-03", "2024-04-08"));
  }

  @Test
  public void testPerformanceOverTimeMSFT() {
    model.createPortfolio("MSFT", "2017-08-21", 7);

    String expected = "The performance over time of the MSFT stock is: \n"
            + "Date: 2024-04-03, Value is: *******************************************.\n"
            + "Date: 2024-04-04, Value is: *************************.\n"
            + "Date: 2024-04-05, Value is: ****************************.\n"
            + "Date: 2024-04-08, Value is: **********************.";

    assertEquals(expected, model.performanceOverTime(
            "MSFT", "2024-04-03", "2024-04-08"));
  }


}