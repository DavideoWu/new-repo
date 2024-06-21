package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import model.Model;
import view.GUIView;

public class Controller2 implements ActionListener, KeyListener {
  private Model model;
  private GUIView view;

  public Controller2(Model m, GUIView v) {
    model = m;
    view = v;
    v.setListeners(this, this);
  }

  // Reads to see what the button the user pressed was.
  @Override
  public void actionPerformed(ActionEvent e) {

    String stockSymbol;
    String numShares;
    String dates;
    switch (e.getActionCommand()) {

      case "done-button" :
        try {
          System.out.println(e.getActionCommand());
          view.clearCanvas();
          //view.handleDoneButtonClick();
          stockSymbol = view.getInputStockSymbol();
          numShares = view.getInputNumberOfShares();
          dates = view.getInputDate();

          System.out.println("Stock Symbol: " + stockSymbol);
          System.out.println("Number of Shares: " + numShares);
          System.out.println("Date: " + dates);
          System.out.println("Clicked done");

          //throw error if numShares is not int.

          //nothing was inputted when clicking done
          //portfolio-composition only needs date.
          System.out.println(view.getInputAction());
          if (view.getInputAction().equals("Get composition")) {
            if (!dates.isEmpty()) {
              System.out.println("Getting composition");
              String composition = model.getPortfolioComposition(dates);
              System.out.println(composition);
              view.displayComposition(composition);
              view.handleDoneButtonClick();
            }
            //if not portfolio-composition, needing all three inputs.
          } else if (view.getInputAction().equals("Portfolio to file")) {
            if (!dates.isEmpty()) {
              System.out.println("FILE WAS CALLED");
              System.out.println(dates);
              model.savePortfolioAsFile(model.getPortfolio(), dates);
              view.handleDoneButtonClick();
            } else {
              throw new IllegalArgumentException("Cannot save to a nameless file");
            }
          } else if (view.getInputAction().equals("File to portfolio")) {
            System.out.println("FTP WAS CALLED");
            System.out.println(dates);
            model.convertFileToPortfolio(dates);
            System.out.println(model.getPortfolio());
            view.handleDoneButtonClick();
          } else {
            //if all the three inputs are filled in
            if (!(stockSymbol.isEmpty() || numShares.isEmpty() || dates.isEmpty())) {
              if (view.getInputAction().equals("create-portfolio")) {
                try {
                  Integer.parseInt(numShares);
                } catch (NumberFormatException a) {
                  throw new IllegalArgumentException("Number of shares must be int");
                }

                System.out.println("Creating Portfolio");
                System.out.println("numOfShares: " + numShares);
                model.createPortfolio(stockSymbol, dates, Integer.parseInt(numShares));
                view.handleDoneButtonClick();
              } else if (view.getInputAction().equals("purchase-shares")) {
                try {
                  Integer.parseInt(numShares);
                } catch (NumberFormatException a) {
                  throw new IllegalArgumentException("Number of shares must be int");
                }

                model.purchaseShares(stockSymbol, Integer.parseInt(numShares), dates);
                view.handleDoneButtonClick();
                System.out.println("Purchasing shares");
              } else if (view.getInputAction().equals("sell-shares")) {
                try {
                  Integer.parseInt(numShares);
                } catch (NumberFormatException a) {
                  throw new IllegalArgumentException("Number of shares must be int");
                }

                System.out.println("Selling shares");
                model.sellShares(stockSymbol, Integer.parseInt(numShares), dates);
                view.handleDoneButtonClick();
              }
              //if there are missing inputs
            } else {
              view.handleDoneButtonClick();
            }
          }
        } catch (IllegalArgumentException | FileNotFoundException i) {
          view.showErrorMessage(i.getMessage());
          view.handleDoneButtonClick();
        }
        break;
      case "enter-button" :
        try {
          System.out.println(e.getActionCommand());
          //view.clearCanvas();
          //view.handleDoneButtonClick();
          stockSymbol = view.getInputStockSymbol();
          numShares = view.getInputNumberOfShares();
          dates = view.getInputDate();

          System.out.println("Stock Symbol: " + stockSymbol);
          System.out.println("Number of Shares: " + numShares);
          System.out.println("Date: " + dates);
          System.out.println("Clicked done");

          if (stockSymbol.isEmpty() || numShares.isEmpty() || dates.isEmpty()) {
            throw new IllegalArgumentException("One of the fields is empty");
          }

          try {
            Integer.parseInt(numShares);
          } catch (NumberFormatException a) {
            throw new IllegalArgumentException("Number of shares must be int");
          }

          if (view.getInputAction().equals("create-portfolio")) {
            System.out.println("numOfShares: " + numShares);
            model.createPortfolio(stockSymbol, dates, Integer.parseInt(numShares));
            System.out.println("Creating Portfolio");
          } else if (view.getInputAction().equals("purchase-shares")) {
            model.purchaseShares(stockSymbol, Integer.parseInt(numShares), dates);
            System.out.println("Purchasing shares");
          } else if (view.getInputAction().equals("sell-shares")) {
            model.sellShares(stockSymbol, Integer.parseInt(numShares), dates);
            System.out.println("Selling shares");
          }
        } catch (IllegalArgumentException a) {
          view.showErrorMessage(a.getMessage());
        }
        break;

      case "create-portfolio":
        view.clearAllInputString();
        view.clearCanvas();
        view.handleButtonCreateClick();
        break;

      case "purchase-shares" :
        view.clearAllInputString();
        view.clearCanvas();
        view.handleButtonBuyClick();
        break;

      case "sell-shares" :
        view.clearAllInputString();
        view.clearCanvas();
        view.handleButtonSellClick();
        break;
      case "distribution-portfolio-value" :
        System.out.println(e.getActionCommand());
        view.clearCanvas();
        break;
      case "portfolio-composition" :
        System.out.println(e.getActionCommand());
        view.clearAllInputString();
        view.clearCanvas();
        break;
      case "portfolio-to-file" :
        System.out.println(e.getActionCommand());
        view.clearAllInputString();
        view.clearCanvas();
        view.handleButtonFileClick();
        break;
      case "file-to-portfolio" :
        System.out.println(e.getActionCommand());
        view.clearAllInputString();
        view.clearCanvas();
        view.handlePortfolioClick();
    }
  }

  // Reads what the user typed.
  @Override
  public void keyTyped(KeyEvent e) {
//    switch (e.getKeyChar()) {
//      case 'd': //toggle color
//        view.getNumStocks();
//        break;
//    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
//    switch (e.getKeyCode()) {
//      case KeyEvent.VK_C: //caps
//        String text = model.getString();
//        text = text.toUpperCase();
//        view.setEchoOutput(text);
//        break;
//    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
//    switch (e.getKeyCode()) {
//      case KeyEvent.VK_C: //caps
//        String text = model.getString();
//        view.setEchoOutput(text);
//        break;
//    }
  }
}
