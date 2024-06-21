package view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;


public class JFrameView extends JFrame implements GUIView {
  private final JLabel display;
  private final JLabel display1;
  private final JLabel display2;
  private final JLabel displayAction;
  private final JLabel display4;
  private final JLabel display5;
  private final JLabel display6;
  private final JLabel createSpace;
  private final JButton enterButton;
  private final JButton quitButton;
  private final JButton doneButton;
  private final JButton backButton;
  private final JButton buttonCreate;
  private final JButton buttonBuy;

  private final JButton buttonSell;

  private final JButton buttonFile;

  private final JButton buttonPortfolio;

  private final JButton buttonComposition;

  //private PrintStream out;
  JPanel mainPanel = new JPanel();
  JTextField inputStockSymbol = new JTextField(10);
  JTextField inputNumberOfShares = new JTextField(10);
  JTextField inputDate = new JTextField(10);

  private String composition;



  /**
   * Creates the JFrame.
   * @param caption The caption.
   */
  public JFrameView(String caption) {
    super(caption);

    JTextArea outputTextArea = new JTextArea(10, 10);
    outputTextArea.setEditable(false);


    JFrame frame = new JFrame("Stocks Application");
    frame.setSize(10, 10);
    setLocation(700, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    //JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 100, 10));


    //mainPanel.setLayout(new FlowLayout());

    display = new JLabel("");

    display1 = new JLabel("Welcome to Stocks!");

    display2 = new JLabel("These are the following actions you can perform:");

    displayAction = new JLabel("");

    display4 = new JLabel("");

    display5 = new JLabel("Click 'Quit' to quit the application");

    display6 = new JLabel("");

    // create portfolio
    JPanel buttonPanelCreate = new JPanel();
    buttonCreate = new JButton("create-portfolio");
    buttonCreate.setActionCommand("create-portfolio");
    buttonPanelCreate.add(buttonCreate);

    // buy shares
    JPanel buttonPanelBuy = new JPanel();
    buttonBuy = new JButton("purchase-shares");
    buttonBuy.setActionCommand("purchase-shares");
    buttonPanelBuy.add(buttonBuy);

    // sell shares
    JPanel buttonPanelSell = new JPanel();
    buttonSell = new JButton("sell-shares");
    buttonSell.setActionCommand("sell-shares");
    buttonPanelSell.add(buttonSell);

    // query value and composition
    JPanel buttonPanelFile = new JPanel();
    buttonFile = new JButton("portfolio-to-file");
    buttonFile.setActionCommand("portfolio-to-file");
    buttonPanelFile.add(buttonFile);

    JPanel buttonPanelPortfolio = new JPanel();
    buttonPortfolio = new JButton("file-to-portfolio");
    buttonPortfolio.setActionCommand("file-to-portfolio");
    buttonPanelPortfolio.add(buttonPortfolio);

    JPanel buttonPanelComposition = new JPanel();
    buttonComposition = new JButton("portfolio-composition");
    buttonComposition.setActionCommand("portfolio-composition");
    buttonPanelComposition.add(buttonComposition);

    JPanel buttonPanelEnter = new JPanel();
    enterButton = new JButton("Enter");
    enterButton.setActionCommand("enter-button");
    buttonPanelEnter.add(enterButton);

    JPanel buttonPanelQuit = new JPanel();
    quitButton = new JButton("Quit");
    quitButton.setActionCommand("quit-button");
    buttonPanelQuit.add(quitButton);

    JPanel buttonPanelDone = new JPanel();
    doneButton = new JButton("DONE");
    doneButton.setActionCommand("done-button");
    buttonPanelDone.add(doneButton);

    JPanel buttonPanelBack = new JPanel();
    backButton = new JButton("Main Menu");
    backButton.setActionCommand("back-button");
    buttonPanelBack.add(backButton);

    createSpace = new JLabel(" ");
    createSpace.setAlignmentX(Component.LEFT_ALIGNMENT);

    this.add(inputStockSymbol);
    this.add(inputNumberOfShares);
    this.add(inputDate);

    mainPanel.add(display1);
    mainPanel.add(display2);
    mainPanel.add(createSpace);
    mainPanel.add(buttonCreate);
    mainPanel.add(buttonBuy);
    mainPanel.add(buttonSell);
    mainPanel.add(buttonFile);
    mainPanel.add(buttonPortfolio);
    mainPanel.add(buttonComposition);
    mainPanel.add(createSpace);
    mainPanel.add(display5);
    mainPanel.add(quitButton);

    setContentPane(mainPanel);

    pack();
    setVisible(true);

    enterButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleEnterButtonClick();
      }
    });

    quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleQuitButtonClick();
      }
    });

    backButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleBackButtonClick();
      }
    });

    buttonCreate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleButtonCreateClick();
      }
    });

    buttonBuy.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("LINE 211");
        handleButtonBuyClick();
      }
    });

    buttonSell.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("LINE 218");
        handleButtonSellClick();
      }
    });

    buttonComposition.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleButtonCompositionClick();
      }
    });

  }

  //fix
  ArrayList<String> stockSymbolsList = new ArrayList<>();
  ArrayList<String> numSharesList = new ArrayList<>();
  ArrayList<String> datesList = new ArrayList<>();
  ArrayList<String> names = new ArrayList<>();

  String stockSymbol = "";
  String numShares = "";
  String dates = "";


  /**
   * Called in the controller, handles when done is pressed.
   */
  public void handleDoneButtonClick() {

    // Get input from the text fields
    stockSymbol = inputStockSymbol.getText();
    stockSymbolsList.add(stockSymbol);
    display.setText("Stock: " + stockSymbol);

    numShares = inputNumberOfShares.getText();
    numSharesList.add(numShares);
    display1.setText("Number of shares: " + numShares);

    dates = inputDate.getText();
    datesList.add(dates);
    display2.setText("Date: " + dates);

    // Testing out composition
    System.out.println("DISPLAY ACTION IS " + displayAction.getText());
    System.out.println(composition);

    // Handle different actions based on displayAction
    if (displayAction.getText().equals("Get composition")) {
      display.setText(composition);
    } else if (displayAction.getText().equals("Portfolio to file")) {
      display.setText("Converted portfolio to " + dates);
    } else if (displayAction.getText().equals("File to portfolio")) {
      display.setText("Converted file to portfolio from " + dates);
    } else {
      System.out.println("ADDING NAMES LINE 270: Stock: " + stockSymbol + " Number of Shares: "
              + numShares + " Date: " + dates);
      names.add("Stock: " + stockSymbol + " Number of Shares: " + numShares + " Date: " + dates);
      System.out.println("NAMES AFTER ADDED LINE 272: " + names.toString());

      display.setText(names.toString() + "\n");
    }

    // Update the main panel
    mainPanel.removeAll();
    mainPanel.revalidate();
    mainPanel.repaint();
    System.out.println("PRINT: " + names.toString());
    mainPanel.add(display);
    mainPanel.add(backButton);
  }


  /**
   * Called in the controller, handles when enter is pressed.
   */
  public void handleEnterButtonClick() throws IllegalArgumentException {


    stockSymbol = inputStockSymbol.getText();
    stockSymbolsList.add(stockSymbol);

    numShares = inputNumberOfShares.getText();
    numSharesList.add(numShares);

    dates = inputDate.getText();
    datesList.add(dates);

    names.add("Stock: " + stockSymbol + " Number of Shares: " + numShares + " Date: " + dates);

    clearAllInputString();
  }

  /**
   * Called in the controller, handles when back is pressed.
   */
  public void handleBackButtonClick() {
    mainPanel.removeAll();
    mainPanel.revalidate();
    mainPanel.repaint();
    mainPanel.remove(inputStockSymbol);
    mainPanel.remove(inputNumberOfShares);
    mainPanel.remove(inputDate);
    display1.setText("Welcome to Stocks!");
    display2.setText("These are the following actions you can perform:");
    mainPanel.add(display1);
    mainPanel.add(display2);
    mainPanel.add(createSpace);
    mainPanel.add(buttonCreate);
    mainPanel.add(buttonBuy);
    mainPanel.add(buttonSell);
    mainPanel.add(buttonFile);
    mainPanel.add(buttonPortfolio);
    mainPanel.add(buttonComposition);
    mainPanel.add(createSpace);
    mainPanel.add(display5);
    mainPanel.add(quitButton);
  }

  /**
   * Called in the controller, handles when quit is pressed.
   */
  public void handleQuitButtonClick() {
    mainPanel.removeAll();
    mainPanel.revalidate();
    mainPanel.repaint();
    display.setText("Thank you for using the program!");
    mainPanel.add(display);
  }

  /**
   * Called in the controller, handles when create is pressed.
   */
  public void handleButtonCreateClick() {
    displayAction.setText("create-portfolio");
    mainPanel.add(displayAction);
    mainPanel.add(createSpace);
    display.setText("Input the stock you want: ");
    mainPanel.add(display);
    mainPanel.add(inputStockSymbol);
    display1.setText("Enter the number of shares to add: ");
    mainPanel.add(display1);
    mainPanel.add(inputNumberOfShares);
    display2.setText("Enter a date:");
    mainPanel.add(display2);
    mainPanel.add(inputDate);
    display4.setText("Click 'Enter' to continue adding stocks.");
    mainPanel.add(display4);
    mainPanel.add(enterButton);
    mainPanel.add(display5);
    mainPanel.add(quitButton);
    display6.setText("Click 'DONE' to if you are done adding stocks.");
    mainPanel.add(display6);
    mainPanel.add(doneButton);

  }

  /**
   * Called in the controller, handles when purchase shares is pressed.
   */
  public void handleButtonBuyClick() {
    System.out.println("BUYING");
    displayAction.setText("purchase-shares");
    mainPanel.add(displayAction);
    mainPanel.add(createSpace);
    display.setText("Input the stock you want: ");
    mainPanel.add(display);
    mainPanel.add(inputStockSymbol);
    display1.setText("Enter the number of shares to buy: ");
    mainPanel.add(display1);
    mainPanel.add(inputNumberOfShares);
    display2.setText("Enter a date:");
    mainPanel.add(display2);
    mainPanel.add(inputDate);
    display4.setText("Click 'Enter' to continue buying shares.");
    mainPanel.add(display4);
    mainPanel.add(enterButton);
    mainPanel.add(display5);
    mainPanel.add(quitButton);
    display6.setText("Click 'DONE' to if you are done buying stocks.");
    mainPanel.add(display6);
    mainPanel.add(doneButton);
  }

  /**
   * Called in the controller, handles when sell shares is pressed.
   */
  public void handleButtonSellClick() {
    System.out.println("SELLING");
    displayAction.setText("sell-shares");
    mainPanel.add(displayAction);
    mainPanel.add(createSpace);
    display.setText("Input the stock you want: ");
    mainPanel.add(display);
    mainPanel.add(inputStockSymbol);
    display1.setText("Enter the number of shares to sell: ");
    mainPanel.add(display1);
    mainPanel.add(inputNumberOfShares);
    display2.setText("Enter a date:");
    mainPanel.add(display2);
    mainPanel.add(inputDate);
    display4.setText("Click 'Enter' to continue selling shares.");
    mainPanel.add(display4);
    mainPanel.add(enterButton);
    mainPanel.add(display5);
    mainPanel.add(quitButton);
    display6.setText("Click 'DONE' to if you are done selling stocks.");
    mainPanel.add(display6);
    mainPanel.add(doneButton);
  }

  /**
   * Called in the controller, handles portfolio to file is clicked.
   */
  public void handleButtonFileClick() {
    // Add specific logic for Button 2 click

    displayAction.setText("Portfolio to file");
    display2.setText("Enter a file name:");
    mainPanel.add(display2);
    //use input date for now
    mainPanel.add(inputDate);
    display6.setText("Click 'DONE' to enter a name");
    mainPanel.add(display6);
    mainPanel.add(doneButton);
    mainPanel.add(backButton);
  }

  /**
   * Called in the controller, handles when file to portfolio is clicked.
   */
  public void handlePortfolioClick() {
    // Add specific logic for Button 2 click

    displayAction.setText("File to portfolio");
    display2.setText("Enter a file to retrieve:");
    mainPanel.add(display2);
    //use input date for now
    mainPanel.add(inputDate);
    display6.setText("Click 'DONE' to enter a name");
    mainPanel.add(display6);
    mainPanel.add(doneButton);
    mainPanel.add(backButton);
  }

  /**
   * Called in the controller, handles when composition is pressed.
   */
  public void handleButtonCompositionClick() {
    displayAction.setText("Get composition");
    display2.setText("Enter a date:");
    mainPanel.add(display2);
    mainPanel.add(inputDate);
    display6.setText("Click 'DONE' to if you entered a date.");
    mainPanel.add(display6);
    mainPanel.add(doneButton);
  }

  /**
   * Sets new composition.
   * @param composition the composition from controller.
   */
  public void displayComposition(String composition) {
    this.composition = composition;
  }

  @Override
  public void setListeners(ActionListener clicks, KeyListener keys) {
    this.addKeyListener(keys);
    this.doneButton.addActionListener(clicks);
    this.enterButton.addActionListener(clicks);
    this.quitButton.addActionListener(clicks);
    this.buttonCreate.addActionListener(clicks);
    this.buttonBuy.addActionListener(clicks);
    this.buttonSell.addActionListener(clicks);
    this.buttonFile.addActionListener(clicks);
    this.buttonPortfolio.addActionListener(clicks);
    this.buttonComposition.addActionListener(clicks);
  }

  // Clears my canvas.
  // Create a text field.
  // Ask the user to input their stock.
  @Override
  public void clearCanvas() {
    mainPanel.removeAll();
    mainPanel.revalidate();
    mainPanel.repaint();
  }

  @Override
  public String getInputStockSymbol() {
    System.out.println("Input stock symbol: " + inputStockSymbol.getText());
    return inputStockSymbol.getText();
  }

  @Override
  public String getInputNumberOfShares() {
    return inputNumberOfShares.getText();
  }

  @Override
  public String getInputDate() {
    return inputDate.getText();
  }

  @Override
  public String getInputAction() {
    System.out.println("Display3: " + displayAction.getText());
    return displayAction.getText();
  }

  @Override
  public String getInputString4() {
    return display.getText();
  }

  @Override
  public void clearAllInputString() {
    inputStockSymbol.setText("");
    inputNumberOfShares.setText("");
    inputDate.setText("");
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void setEchoOutput(String s, String s1, String s2) {
    display.setText(s);
    display1.setText(s1);
    display2.setText(s2);
  }

  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(mainPanel, message, "Input Error",
            JOptionPane.ERROR_MESSAGE);
  }

  public void getNumberStocks() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  }
}


