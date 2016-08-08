import java.util.*;
import java.text.*;
 
public class Stocks {
 
private int shares;
private int price;
private int temp;
private static int total;
private int finalPrice;
private int finalShares;
private Queue<Stocks> StockList = new LinkedList<Stocks>();
 
private static NumberFormat nf = NumberFormat.getCurrencyInstance();
public static void main(String[] args){
 
 
    Scanner scan = new Scanner(System.in);
 
    System.out.println("Enter transaction sequence:");
 
    String input = scan.nextLine().trim();
    String[] inputArray = new String[50];
    String[] inputArray2 = new String[50];
    int numShares, sharePrice;
 
    inputArray = input.split(";");
 
    for (String i : inputArray) {
        if (i.toUpperCase().contains("BUY")) {
            inputArray2 = i.split(" ");
            inputArray2[4] = inputArray2[4].substring(1);
 
            try {
                numShares = Integer.parseInt(inputArray2[1]);
                sharePrice = Integer.parseInt(inputArray2[4]);
 
                Stocks newStock = new Stocks(numShares,sharePrice);
                newStock.buy();
 
            } catch (NumberFormatException e) {
                System.out.println("Error");
                return;
            }
 
        }
 
        else if (i.toUpperCase().contains("SELL")) {
            inputArray2 = input.split(" ");
            inputArray2[4] = inputArray2[4].substring(1);
 
            try {
                numShares = Integer.parseInt(inputArray2[1]);
                sharePrice = Integer.parseInt(inputArray2[4]);
 
                Stocks newStock = new Stocks(numShares,sharePrice);
                newStock.sell();
 
            } catch (NumberFormatException e) {
                System.out.println("Error");
                return;
            }
        } else {
            System.out.println("Error - input does not contain buy/sell");
        }
    }System.out.println(nf.format(getTotal()));
}
 
public Stocks()
{
    shares      = 0;
    price       = 0;
 
}
 
public Stocks(int shares, int price)
{
    this.shares     = shares;
    this.price      = price;
}
 
public int getShares()
{
    return this.shares;
}
 
public int getPrice()
{
    return this.price;
}
 
public void setShares(int shares)
{
    this.shares = shares;
}
 
public void setPrice(int price)
{
    this.price = price;
}
 
public void sell() {
    int sharesToSell = this.getShares();
    int priceToSell = this.getPrice();
 
    while (!StockList.isEmpty()) {
 
         int numShares = StockList.peek().getShares();
         int sharePrice = StockList.peek().getPrice();
 
        if (numShares < sharesToSell || numShares == sharesToSell) {
            temp = sharesToSell - numShares; // remaining shares to sell
            finalShares = sharesToSell - temp; // # shares selling at price
            finalPrice = priceToSell - sharePrice; // shares sold at adjusted price
            total += (finalPrice * finalShares); // Calculates total price
            StockList.remove();
            sharesToSell = temp; // Remaining shares needed to be sold @ price
           
        }
 
        if (numShares > sharesToSell) {
            temp = numShares - sharesToSell; // Remaining shares that were bought
            finalPrice = priceToSell - sharePrice; // Shares sold at adjusted price
            total += (finalPrice * sharesToSell); // adds to running total
            StockList.peek().setShares(temp);
           
        }
    }
}
 
public void buy() {
    int numShares = this.getShares();
    int priceToBuy = this.getPrice();
 
    Stocks newStock = new Stocks(numShares,priceToBuy);
    StockList.add(newStock); // adds stock to list
 
    int temptotal = (numShares * priceToBuy); // decreases running total
    total += (-1 * temptotal);
}
 
public static int getTotal() { // gets total profit (or loss)
    return total;
}
 
}