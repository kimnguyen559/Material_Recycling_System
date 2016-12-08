package project;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;


/* ************************************************************** 
 * This class holds data of one customer session 
 */
class SessionLog implements Serializable{
	private int numberOfItem = 0;		// number of recycled items in one session
	private double totalWeight = 0;		// total weight of items in one session
	private double totalGlass = 0;		// total weight of glass items 
	private double totalAlum = 0;		// total weight of aluminum items
	private double totalPlastic = 0;	// total weight of plastic items
	private double amount = 0;			// total prices of recycled item in one session
	private double cash = 0;			// total cash payment in one session
	private double coupon = 0;			// total coupon payment in one session	
	private Date date;					// date of the session
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	/* this method adds a recycled item to the session 
	 */
	public void addItem(Item item) {			
		items.add(item);
		numberOfItem++;					// increase session total # of items
		amount += item.getPrice();		// increase session total amount of money
		double w = item.getWeight();	// get the weight of the item
		totalWeight += w;				// increase the weight of the session
		String material = item.getMaterial();	// get item material
		
		switch (material)
		{
		    case "Glass":
		    	totalGlass += w;
		        break;
		    case "Aluminum":
		    	totalAlum += w;
		        break;
		    case "Plastic":
		    	totalPlastic += w;
		        break;		    
		}				
	}	
	
	public void setCash(double cash) {
		this.cash = cash;
	}
	
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	
	public int getNumberOfItem() {
		return numberOfItem;
	}
	
	public double getTotalWeight () {
		return totalWeight;
	}
	
	public double getGlass() {
		return totalGlass;
	}
	
	public double getAlum() {
		return totalAlum;
	}
	
	public double getPlastic() {
		return totalPlastic;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public double getCash() {
		return cash;
	}
	
	public double getCoupon() {
		return coupon;
	}
	
	public void setDate (Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String toString() {
		String str = "";
		
		if (items.size() > 0 ) {
			str += date + "\n";
		
			for (Item i : items) {
				str += i.toString();	
				str += "\n";
			}	
			
			str += "    Number of items: " + numberOfItem + "\n";
			str += "    Total weight: " + totalWeight + " lbs\n";
			str += "         Glass: " + totalGlass + " lbs\n";
			str += "         Aluminum: " + totalAlum + " lbs\n";
			str += "         Plastic: " + totalPlastic + " lbs\n";
			str += "    Amount: " + Main.doubleToMoneyStr(amount) + "\n";
			str += "         Cash payment: " + 
									Main.doubleToMoneyStr(cash) + "\n";
			str += "         Coupon payment: " + 
									Main.doubleToMoneyStr(coupon) + "\n";
		}
		return str;
	}
}		// end SessionLog class


