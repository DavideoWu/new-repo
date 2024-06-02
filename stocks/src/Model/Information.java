package Model;


public interface Information {

  /**
   * Creates a stock.
   * @param stockSymbol the ticker symbol used to trade the stock.
   * @param name the name of the stock.
   * @param price the price of the stock.
   */
  void stock(String stockSymbol, String name, double price);

  void portfolio(stock);

}
