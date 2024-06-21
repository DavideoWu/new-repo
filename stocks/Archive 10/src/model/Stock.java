package model;


import java.util.List;


import static model.ModelImp.getDataForStocks;
import static model.ModelImp.getDateIndex;
import static model.ModelImp.readCSVFile;
import static model.ModelImp.saveToCSVFile;

/**
 * Class representing the stock, which is used as a key.
 */
public class Stock {
  private String stockSymbol;
  private String date;



  /**
   * Constructor for stock.
   * @param stockSymbol The stock symbol.
   * @param date The start date we want to get the values at.
   */
  public Stock(String stockSymbol, String date) {
    String stockData = getDataForStocks(stockSymbol);
    saveToCSVFile(stockData);
    List<String[]> dataList = readCSVFile("output.csv");
    int dateIndex = getDateIndex(date, dataList);
    if (dateIndex == -1) {
      throw new IllegalArgumentException("Error: Stock does not exist for this date!");
    }


    this.stockSymbol = stockSymbol;
    this.date = date;
  }


  public String getStockSymbol() {
    return stockSymbol;
  }


  public String getDate() {
    return date;
  }

  @Override
  public String toString() {
    return "Stock{"
            + "stockSymbol='" + stockSymbol + '\''
            + ", date='" + date + '\''
            + '}';
  }
}