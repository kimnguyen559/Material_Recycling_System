package project;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

/* **************************************************************
 * This class holds data of a recyclable item type
 */
public class RecyclableType implements Serializable{
	private double minWeight;	// minimun weight in pound
	private double maxWeight;	// maximun weight in pound
	private ArrayList<String> material = 		// list of acceptable material
			new ArrayList<String>();
	private double price;		// price for this recyclable item type	
	
	
	/* constructor
	 */
	public RecyclableType(double minWeight, double maxWeight,
			ArrayList<String> material, double price) {
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
		this.material = material;
		this.price = price;		
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getMinWeight() {
		return minWeight;
	}
	
	public double getMaxWeight() {
		return maxWeight;
	}
	
	public ArrayList<String> getMaterial() {
		return material;
	}
	
	public String toString() {		
		String s = "";
		for (String str: material) {
			s += str + " ";
		}
		s += "    " + minWeight +"-";
		s += maxWeight + " lbs     ";		
		s += Main.doubleToMoneyStr(price);
		return s;		
	}
}
