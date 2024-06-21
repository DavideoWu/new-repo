package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public interface GUIView {
  void setListeners(ActionListener clicks, KeyListener keys);

  void clearCanvas();

  String getInputStockSymbol();

  String getInputNumberOfShares();

  String getInputDate();

  String getInputAction();

  String getInputString4();

  void clearAllInputString();

  void resetFocus();

  void setEchoOutput(String s, String s1, String s2);


  void handleDoneButtonClick();

  void handleEnterButtonClick();

  void handleQuitButtonClick();

  void handleBackButtonClick();

  void handleButtonCreateClick();

  void handleButtonBuyClick();

  void handleButtonSellClick();

  void showErrorMessage(String message);

  void handleButtonCompositionClick();

  void displayComposition(String composition);

  void handleButtonFileClick();

  void handlePortfolioClick();
}
