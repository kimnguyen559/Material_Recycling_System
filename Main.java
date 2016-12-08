package project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;

import javax.swing.JFrame;

public class Main {
		
		/* This main method hard-codes data to fill RMOS
		 * 		-it adds RCM 1 then loads RCM 1 half full
		 * 		-it adds RCM 2 then loads RCM 2 to max  
		 * 		-it then un-loads RCM 2.	
		 */
	public static void main(String[] args) {	
		System.out.println("starting main....");
		
		////////////////   hard codes   ////////////////
		Rmos rmos = new Rmos ();					// create the rmos
		Admin admin = new Admin
					("coen175", "coen175", rmos);	// create an admin to manage RMOS
			
		/////1st RCM
		String id = rmos.addRcm();					// create 1st RCM				
		rmos.activateRcm(id, "Santa Clara");		// activate RCM 1
		Rcm rcm = rmos.getRcm(id);		
			
		Item i1 = new Item(1, "Glass");				// create some items
		Item i2 = new Item(2, "Plastic");		
		Item i3 = new Item(3, "Aluminum");		
		Item i4 = new Item(4, "Glass");		
		Item i5 = new Item(5, "Plastic");			
			
		rcm.startSession();		// start a session
		rcm.insertItem(i1);		// insert items
		rcm.insertItem(i2);
		rcm.insertItem(i3);
		rcm.insertItem(i4);
		rcm.insertItem(i5);		
		rcm.endSession(false);	// end session, choose cash (false, if coupon: true)
			
		
		/////2nd RCM
		id = rmos.addRcm();					// create 2nd RCM				
		rmos.activateRcm(id, "San Jose");	// activate RCM 2
		rcm = rmos.getRcm(id);			
			
		rcm.startSession();		// start a session
		rcm.insertItem(i1);		// insert items to RCM 2
		rcm.insertItem(i2);
		rcm.insertItem(i3);
		rcm.insertItem(i4);
		rcm.insertItem(i5);	
		rcm.insertItem(i1);					
		rcm.insertItem(i2);
		rcm.insertItem(i3);
		rcm.insertItem(i4);
		rcm.insertItem(i5);	
		rcm.endSession(true);	// end session, choose coupon
		
		rmos.emptyRcm(id);		// empty RCM 2		
			
		try{							// save to file
			FileOutputStream fileOut = new FileOutputStream("project.ser");
			ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
			outStream.writeObject(admin);
			outStream.close();
			fileOut.close();
		}catch(IOException i){
			i.printStackTrace();
		}
			
		System.out.println("Hard-code data saved to file");
		System.out.println("Ending main...");		 
	}				
			
				
	/* 	static method to convert a double to a
	 * currency string (for others to use)
	 */
	public static String doubleToMoneyStr
							(double amt) {
		NumberFormat formatter = 
				NumberFormat.getCurrencyInstance();		
		return formatter.format(amt);
	}
}
