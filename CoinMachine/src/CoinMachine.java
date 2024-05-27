public class CoinMachine {

	public static void main(String[] args) {
	
	int change;	
	int cash = Integer.parseInt(args[0]);
	System.out.println("Amount received: " + cash);
	int price = Integer.parseInt(args[1]);
	System.out.println("Cost of the item: " + price);
	change = cash - price; // toonies = 2*100, loonie = 1*100, quarter = 0.25*100, dime = 0.1*100, nickel = 0.05*100, pennies = 0.01*5*100
	System.out.println("Required change: " + change);
	
	int toonies = change /200; // change < 200, it will output 0
	change %= 200; // the remainder doesn't change if change < 200, in this case 185/200=0 with a remainder of 185
	int loonie = change / 100; 
	change %= 100; // change = change % 100
	int quarter = change / 25;
	change %= 25;
	int dime = change / 10;
	change %= 10;
	int nickel = change / 5;
	change %= 5;
	System.out.println("Change: \n    toonies x " + toonies + 
			"\n    loonie x " + loonie +
			"\n    quarter x " + quarter +
			"\n    dime x " + dime +
			"\n    nickel x " + nickel);		
	}

}