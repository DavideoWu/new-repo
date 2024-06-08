package Controller;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import Model.ModelMock;
import View.ViewMock;

import static org.junit.Assert.*;

/**
 * Methods - appendable (append the result)
 * Each method is displaying a certain text.
 * Generic message
 * Appendable - displaying outputs to the user.
 */
public class ControllerTest {

  @Test
  public void testGainLoss() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("gain-loss\n" +  "MSFT\n" + "2024-03-15\n"
            + "2024-03-07\n");
    ModelMock mock = new ModelMock(log);
    ViewMock view = new ViewMock(log2);

    Controller controller = new Controller(mock, view, in);
    controller.go();

    assertEquals("stockSymbol: MSFT, startDate: 2024-03-15, endDate: 2024-03-07\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
            + "Successfully called writeMessage\n"
            + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
            + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
            + "Successfully called returnGainOrLoss\n" + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testXDayAverage() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("x-day-average\n" +  "AAPL\n" + "2024-05-15\n" + "15\n");
    ModelMock mock = new ModelMock(log);
    ViewMock view = new ViewMock(log2);

    Controller controller = new Controller(mock, view, in);
    controller.go();
    assertEquals("stockSymbol: AAPL, date: 2024-05-15, daysBefore: 15\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called XDayAverageMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testXDayCrossover() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("x-day-crossover\n" +  "BA\n" + "2022-08-29\n" + "8\n");
    ModelMock mock = new ModelMock(log);
    ViewMock view = new ViewMock(log2);

    Controller controller = new Controller(mock, view, in);
    controller.go();

    assertEquals("stockSymbol: BA, date: 2022-08-29, daysBefore: 8\n",
            log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called XDayCrossoverMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testCreatePortfolio() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("create-portfolio\n" +  "UAL\n" + "93\n" + "notyet\n"
            + "DAL\n" + "62\n" + "DONE\n" + "2022-05-22\n");
    ModelMock mock = new ModelMock(log);
    ViewMock view = new ViewMock(log2);

    Controller controller = new Controller(mock, view, in);
    controller.go();
    assertEquals("stockSymbol: UAL, numberOfShares: 93\n"
                    + "stockSymbol: DAL, numberOfShares: 62, date: 2022-05-22\n", log.toString());
    assertEquals("Successfully called welcomeMessage\n"
                    + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called writeMessage\n" + "Successfully called writeMessage\n"
                    + "Successfully called portfolioMessage\n"
                    + "Successfully called farewell\n",
            log2.toString());
  }

  @Test
  public void testQuit() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    Reader in = new StringReader("quit\n");
    ViewMock view = new ViewMock(log2);
    ModelMock model = new ModelMock(log);
    Controller controller = new Controller(model, view, in);
    controller.go();

    assertEquals("Successfully called welcomeMessage\n" + "Successfully called farewell\n",
            log2.toString());
  }

}