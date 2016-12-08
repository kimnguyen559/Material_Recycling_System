package project;

import java.io.Serializable;
import java.text.NumberFormat;


/* **************************************************************
* This class holds data of a recycling item 
*/
public class Item implements Serializable{
	private double weight;		// weight of item in pound
	private double price;		// price for the item
	private String material;	// material of the item
	
	
	/*	constructor w/ 2 args
	 */
	public Item(double weight, String material) {
		this(weight, 0, material);
	}

	
	/*	constructor w/ 3 args 
	 */
	public Item(double weight, double price, String material) {
		this.weight = weight;
		this.price = price;
		this.material = material;
	}

	
	/*	returns item weight 
	 */
	public double getWeight () {
		return weight;
	}
	
	
	/*	returns item price 
	 */
	public double getPrice () {
		return price;
	}

	
	/*	returns item material 
	 */
	public String getMaterial() {
		return material;
	}
	
	
	/* 	set item price 
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	
	/*	item toString
	 */
	public String toString() {		
		String s= material ;
		s += "    " + weight + " lbs";
		s += "    " + Main.doubleToMoneyStr(price);
		return s;
	}	
}	// end Item class

