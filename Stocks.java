package stock;
import java.util.*;
import java.text.*;
 
public class Stocks {
 
private int shares;
private int price;
private static int totalSpent;
private static double capitalGains; //Total profit
private static Queue<Stocks> StockList = new LinkedList<Stocks>();
 
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
            inputArray2 = i.split(" ");
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
    }
    System.out.println("Amount spent: " + nf.format(getTotalSpent()));
    System.out.println("Capital Gains: " + nf.format(getCGains()));
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
 
public static double getCGains() {
	return capitalGains;
}

public static void setCGains(double capitalGains) {
	Stocks.capitalGains = capitalGains;
}

public void sell() {
	
    int sharesToSell = this.getShares();
    int priceToSell = this.getPrice();
 
    while (sharesToSell > 0) {//Keep selling from other stocks until you complete the sale
 
         int numShares = StockList.peek().getShares();
         int sharePrice = StockList.peek().getPrice();
 
			try {

				if (numShares < sharesToSell || numShares == sharesToSell) {
					// temp = sharesToSell - numShares; // remaining shares to sell
					// finalShares = sharesToSell - temp; // # shares selling at price
					// finalPrice = priceToSell - sharePrice; // shares sold at adjusted price
					// total += (finalPrice * finalShares); // Calculates total price
					// StockList.remove();
					// sharesToSell = temp; // Remaining shares needed to be sold @ price
					
					int temp = sharesToSell - numShares; // remaining shares to sell
					int finalShares = sharesToSell - temp; // # shares selling at price
					StockList.remove();
					double cGain = priceToSell - sharePrice;// profit per share
					capitalGains += (cGain * finalShares);// total profit from set of stocks added to total overall profit
					sharesToSell = temp; // Remaining shares needed to be sold @price
				}

				else if (numShares > sharesToSell) {
					int temp = numShares - sharesToSell; // Remaining shares that were bought
					int finalShares = sharesToSell - temp; // # shares selling at price
					double cGain = priceToSell - sharePrice;// profit per share
					capitalGains += (cGain * finalShares);// total profit from set of stocks added to total overall profit
					sharesToSell = temp; // Remaining shares needed to be sold @price
					StockList.peek().setShares(temp);

				}

			} catch (Exception e) {
				// Todo
				System.out.println(e.getMessage());
			}
    }
}
 
public void buy() {
    int numShares = this.getShares();
    int priceToBuy = this.getPrice();
 
    Stocks newStock = new Stocks(numShares,priceToBuy);
    StockList.add(newStock); // adds stock to list
 
    int temptotal = (numShares * priceToBuy); // decreases running total
    totalSpent += (-1 * temptotal);
}

//Testing
public String toString(){
	return "Price:" + price + "#Shares:" + shares;
	
}
 
public static int getTotalSpent() { // gets total profit (or loss)
    return totalSpent;
}
 
}