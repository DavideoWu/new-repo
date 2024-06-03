package Controller;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class ControllerTest {
  private String expectedWelcome = "Welcome to Stocks!\n"
          + "These are the following actions you can perform: \n"
          + "gain-loss (Obtain the gain or loss of a stock over a period)\n"
          + "x-day-average (Obtain a stock's x-day moving average, starting at a date)\n"
          + "x-day-crossover (Obtain a stock's x-day crossovers over a period of time)\n"
          + "create-portfolio (create a portfolio of multiple stocks, and it's value on a day\n"
          + "quit (quits the program)";

  @Test
  public void testWelcome() {
    String[] lines = separateLines("quit");
    assertEquals(expectedWelcome, lines[0] + "\n" + lines[1] + "\n"  + lines[2] + "\n"
            + lines[3] + "\n" + lines[4] + "\n" + lines[5] + "\n" + lines[6]);
  }

  @Test
  public void testQuit() {
    String[] lines = separateLines("quit");
    assertEquals("Thank you for using this program!", lines[7]);
  }

  @Test
  public void testEnterStockForModels() {
    String[] lines = separateLines("gain-loss");
    assertEquals("Type in a stock:", lines[7]);

    String[] lines1 = separateLines("x-day-average");
    assertEquals("Type in a stock:", lines1[7]);

    String[] lines2 = separateLines("x-day-crossover");
    assertEquals("Type in a stock:", lines2[7]);

    String[] lines3 = separateLines("create-portfolio");
    assertEquals("Type in a stock:", lines3[7]);
  }

  @Test
  public void test

  private String[] separateLines(String command) {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader(command);
    Controller newController = new Controller(in, out);
    newController.go();
    String[] lines = out.toString().split(System.lineSeparator());
    return lines;
  }

}