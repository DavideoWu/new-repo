package View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ViewTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private ViewImp view;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    view = new ViewImp(System.out);
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testWriteMessage() {
    view.writeMessage("Hello");
    assertEquals("Hello" + "\n", outContent.toString());
  }

  @Test
  public void testXDayCrossoverMessage() {
    List<String[]> crossoverList = new ArrayList<String[]>();

    crossoverList.add("2024-04-08,169.0300,169.2000,168.2400,168.4500,37216858".split(","));
    crossoverList.add("2024-04-02,169.0800,169.3400,168.2302,168.8400,49013991".split(","));
    crossoverList.add("2024-03-26,170.0000,171.4200,169.5800,169.7100,57388449".split(","));

    view.XDayCrossOverMessage(crossoverList);

    assertEquals("The following dates cross over the average:\n" +
            "2024-04-08\n"
            + "2024-04-02\n"
            + "2024-03-26\n" , outContent.toString());
  }

  @Test
  public void testXDayAverageMessage() {
    view.XDayAverageMessage(44.0);
    assertEquals("Average price:\n" + "44.0\n", outContent.toString());
  }

  @Test
  public void testPortfolioMessage() {
    view.portfolioMessage(33.0, "2024-02-24");
    assertEquals("Cost of your portfolio at 2024-02-24:\n" + "33.0\n", outContent.toString());
  }

  @Test
  public void testWelcomeMessage() {
    view.welcomeMessage();
    assertEquals("Welcome to Stocks!\n"
            + "These are the following actions you can perform: \n"
            + "gain-loss (Obtain the gain or loss of a stock over a period)\n"
            + "x-day-average (Obtain a stock's x-day moving average, starting at a date)\n"
            + "x-day-crossover (Obtain a stock's x-day crossovers over a period of time)\n"
            + "create-portfolio (create a portfolio of multiple stocks, and it's value on a day\n"
            + "quit (quits the program)\n", outContent.toString());
  }

  @Test
  public void testFarewell() {
    view.farewell();
    assertEquals("Thank you for using the program!\n", outContent.toString());
  }

}
