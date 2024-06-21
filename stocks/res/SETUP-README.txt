Run the program by creating a portfolio by using model.createPortfolio and adding a stock symbol,
date, and number of shares:

To start this program, type in one of the instructions on screen. Type in the action you
want to perform. Follow the instructions onscreen to input important data, such as
dates and stocksymbols. If there is an error, the program will tell you, and you will
be asked to input a valid entry. After getting an output, you have the option to do another
action or quit.

create-portfolio
Input stock you want:
(valid stock)
Enter the number of shares to add:
34
Type DONE if done entering shares, Otherwise, tpye anything.
not done
Enter a date
2024-05-29
Input the stock you want:
AAPL
Enter the number of shares to add:
3
Type DONE if done entering shares. Otherwise, type anything to continue entering
DONE
Enter a date:
2024-05-29

model.createPortfolio("MSFT", "2023-09-01",7);
model.createPortfolio("AAPL", "2023-03-01", 5);
model.createPortfolio("BA", “2024-04-03”, 3);

purchase-shares
aapl
4
Enter a date
2024-05-29
DONE

To purchase shares, you can use this method:
model.purchaseShares(“MSFT", "2023-09-01",7);
model.purchaseShares("AAPL", "2023-03-01", 5);
model.purchaseShares("BA", “2024-04-03”,3);

To get the cost of the portfolio on two specific dates, you can use this method:

public double getPortfolioCost(MSFT, 7, 2023-09-01)
public double getPortfolioCost("AAPL", 5, “2023-03-01");
public double getPortfolioCost("BA", 3, “2024-04-03”);

My program supports stocks such as MSFT, APPL, and BA.
Values can be determined on 2024-05/29, 2024-06-11, and 2022-03-19

GUI SETUP-README
Run the jar file in the GUI. Once run, it will give you several options to run the program.
