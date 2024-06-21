package controller;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import model.ModelMock;
import view.ViewMockImp;

import static org.junit.Assert.assertEquals;

/**
 * Tests the controller of the program.
 */
public class ControllerTest {

  @Test
  public void testGainLoss() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("gain-loss\n" +  "MSFT\n" + "2024-03-15\n"
            + "2024-03-07\n");
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    controller.Controller controller = new controller.Controller(mock, view, in);
    controller.control();

    assertEquals("stockSymbol: MSFT, startDate: 2024-03-15, endDate: 2024-03-07\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called returnGainOrLoss\n"
                    + "Successfully called writeMessage\n" + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testXDayAverage() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("x-day-average\n" +  "AAPL\n" + "2024-05-15\n" + "15\n");
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    controller.Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("stockSymbol: AAPL, date: 2024-05-15, daysBefore: 15\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called xDayAverageMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testXDayCrossover() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("x-day-crossover\n" +  "BA\n" + "2022-08-29\n" + "8\n");
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    controller.Controller controller = new controller.Controller(mock, view, in);
    controller.control();

    assertEquals("stockSymbol: BA, date: 2022-08-29, daysBefore: 8\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called xDayCrossoverMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testCreatePortfolio() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("create-portfolio\n"
            + "UAL\n" + "93\n" + "not yet\n" + "2024-04-03\n"
            + "DAL\n" + "62\n" + "DONE\n" + "2022-05-22\n");
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    controller.Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("stockSymbol: DAL, numberOfShares: 62, date: 2022-05-22\n", log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called portfolioMessage\n"
                    + "Successfully called writeMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testPurchaseShares() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("purchase-shares\n"
            +  "UAL\n" + "93\n" + "2022-05-22\n" + "DONE\n" );
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    controller.Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("stockSymbol: UAL, " +
                    "numberOfShares: 93, date: 2022-05-22\n"
                    + "stockSymbol: UAL, numberOfShares: 93, date: 2022-05-22\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called portfolioMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testSellShares() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("sell-shares\n"
            + "UAL\n"
            + "3\n"
            + "2022-06-22\n"
            + "DONE\n" );
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    controller.Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("stockSymbol: UAL, numberOfShares: 3, date: 2022-06-22\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called portfolioMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testPortfolioComposition() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("portfolio-composition\n"
            + "2022-06-22\n"
            + "DONE\n" );
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    controller.Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("date: 2022-06-22\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testRebalancedPortfolioValue() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("rebalanced-portfolio\n"
            + "UAL\n" + "(30, 40, 30)\n" + "2022-06-22\n" + "DONE\n");
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testDistributionPortfolioValue() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader(" distributed-portfolio\n"
            + "UAL\n" + "(30, 40, 30)\n" + "2022-06-22\n" + "DONE\n");
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());

  }

  @Test
  public void testPortfolioPerformanceOvertime() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("portfolio-performance\n"
            + "2022-06-02\n" + "2022-06-22\n" + "DONE\n");
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testPerformanceOverTime() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader(" stock-performance\n"
            + "UAL\n" + "2022-06-02\n" + "2022-06-22\n" + "DONE\n");
    ModelMock mock = new ModelMock(log);
    ViewMockImp view = new ViewMockImp(log2);

    Controller controller = new controller.Controller(mock, view, in);
    controller.control();
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testQuit() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("quit\n");
    ViewMockImp view = new ViewMockImp(log2);
    ModelMock model = new ModelMock(log);
    controller.Controller controller = new controller.Controller(model, view, in);
    controller.control();

    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());
  }

}