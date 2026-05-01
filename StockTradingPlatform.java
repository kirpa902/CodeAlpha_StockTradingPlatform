import java.util.*;

// Stock class
class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

// Transaction class
class Transaction {
    String type; // BUY or SELL
    String stockSymbol;
    int quantity;
    double price;

    Transaction(String type, String stockSymbol, int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }
}

// User class
class User {
    double balance;
    HashMap<String, Integer> portfolio;
    ArrayList<Transaction> transactions;

    User(double balance) {
        this.balance = balance;
        portfolio = new HashMap<>();
        transactions = new ArrayList<>();
    }

    void buyStock(Stock stock, int quantity) {
        double cost = stock.price * quantity;

        if (balance >= cost) {
            balance -= cost;
            portfolio.put(stock.symbol,
                    portfolio.getOrDefault(stock.symbol, 0) + quantity);

            transactions.add(new Transaction("BUY", stock.symbol, quantity, stock.price));

            System.out.println("Bought " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);

        if (owned >= quantity) {
            balance += stock.price * quantity;
            portfolio.put(stock.symbol, owned - quantity);

            transactions.add(new Transaction("SELL", stock.symbol, quantity, stock.price));

            System.out.println("Sold " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough shares!");
        }
    }

    void showPortfolio(HashMap<String, Stock> market) {
        double totalValue = balance;

        System.out.println("\n--- Portfolio ---");
        for (String symbol : portfolio.keySet()) {
            int qty = portfolio.get(symbol);
            double value = qty * market.get(symbol).price;
            totalValue += value;

            System.out.println(symbol + ": " + qty + " shares | Value: " + value);
        }

        System.out.println("Cash Balance: " + balance);
        System.out.println("Total Portfolio Value: " + totalValue);
    }
}

// Stock Market class
class StockMarket {
    HashMap<String, Stock> stocks;

    StockMarket() {
        stocks = new HashMap<>();
        stocks.put("AAPL", new Stock("AAPL", 150));
        stocks.put("GOOG", new Stock("GOOG", 2800));
        stocks.put("TSLA", new Stock("TSLA", 700));
    }

    void displayMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock s : stocks.values()) {
            System.out.println(s.symbol + " : $" + s.price);
        }
    }
}

// Main Class
public class StockTradingPlatform {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StockMarket market = new StockMarket();
        User user = new User(10000); // starting balance

        while (true) {
            System.out.println("\n1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    market.displayMarket();
                    break;

                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();

                    if (!market.stocks.containsKey(buySymbol)) {
                        System.out.println("Invalid stock!");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();

                    user.buyStock(market.stocks.get(buySymbol), buyQty);
                    break;

                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();

                    if (!market.stocks.containsKey(sellSymbol)) {
                        System.out.println("Invalid stock!");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();

                    user.sellStock(market.stocks.get(sellSymbol), sellQty);
                    break;

                case 4:
                    user.showPortfolio(market.stocks);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}