package project;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;


/* **************************************************************
 * This class holds data and functionalities of a RCM 
 */
public class Rcm extends Observable 
				implements Serializable{			// RCM can be observed
	
	///////////   Constants   ///////////
	static final int OFF = 0;			// RCM is disabled
    static final int IDLE = 1;    		// RCM is available
    static final int IN_SESSION = 2;	// RCM is serving a customer 
    static final int WAIT_FOR_PMT_METHOD = 3;	// waiting for user to choose cash/coupon
    static final int WAIT_FOR_USER_GET_PMT = 4;	// waiting for user to p/u pmt in dispenser
    
    ///////////   Data members   ///////////
	private String id;				// id of RCM	
	private int state;				// state of this RCM
	private String location; 		// location of RCM	
	private boolean operational;	// operational state of RCM
	private boolean activated;		// operational state of RCM
	private double currentMoney;	// current amount of money
	private double maxWeight;		// max weight RCM can hold
	private double currentWeight;	// current weight of RCM	
	
	private ArrayList<SessionLog> transactionLog =	// list of all transaction 
					new ArrayList<SessionLog>();
		
	private ArrayList<RecyclableType> recyclableType = 	// list of recyclable types
			new ArrayList<RecyclableType>();
	
	private SessionManager sessionManager = 			// handles customer session
					new SessionManager();
	
	
	
	public Rcm(String id) {
		this.id = id;		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setOperational(boolean operational) {
		this.operational = operational;
	}
	
	public boolean isOperational() {
		return operational;
	}
	
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public boolean isActivated() {
		return activated;
	}	
	
	public double getCurrentMoney(){
		return currentMoney;
	}
	
	public void setCurrentMoney(double money) {
		currentMoney = money;
		moneyChange();	// notify amount changing
	}
	
	public double getCurrentWeight() {
		return currentWeight;
	}
	
	public void setCurrentWeight(double weight) {
		currentWeight = weight;
		weightChange();	// notify weight changing
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public boolean isFull () {
		return (currentWeight == maxWeight);
	}
	
	public boolean isOutOfMoney() {
		return (currentMoney == 0);
	}
	
	
	/* this method is called when RCM weight changes 
	 */
	public void weightChange() {			
		if (isFull()) {	// if full
			operational = false;		// change state
			String s1 = "RCM " + id + " is full";	// create message
			String s2 = "RCM Full";
			String [] s = {s1, s2};
			setChanged();
			notifyObservers(s);			// notify obverser with message
		}
	}
	
	/* this method is called when RCM money changes 
	 */
	public void moneyChange() {		
		if ( isOutOfMoney() ){		// if out of money
			String s1 = "RCM " + id + " is out of money!";	// create message
			String s2 = "RCM Out Of Money";
			String [] s = {s1, s2};
			setChanged();
			notifyObservers(s);			// notify obverser with message
		}	
	}
	
	
	public double getMaxWeight() {
		return maxWeight;
	}
	
	public void setMaxWeight(double maxWeight){
		this.maxWeight = maxWeight;
	}
	
	public ArrayList<RecyclableType> getRecyclableType() {
		return recyclableType;
	}
	
	public void setRecyclableType
		(ArrayList<RecyclableType> recyclableType) {
		this.recyclableType = recyclableType;
	}
	
	
	public ArrayList<SessionLog>getTransactionLog(){
		return transactionLog;
	}	
	
	
	/* this method is called when user wants to start 
	 * a session 
	 * 	-return 0 if session started okay
	 * 	-return 1 if could not start a session
	 */
	public int startSession() {
		if (operational && state == IDLE)	{	// if RCM is working and not serving a customer
			sessionManager.startSession();		// calling session manager to handle	
			state = IN_SESSION;					// change RCM state
			return 0;
		}
		else {
			return 1;		// cannot start a new session
		}
	}
	
	/* this method inserts an item
	 * 	-return 0 if item is accepted
	 * 	-return 1 if item is not acceptable
	 * 	-return 2 if item is accepted and RCM gets full.
	 * 	-return 3 if RCM is not in service.
	 */
	public int insertItem(Item item) {	
		
		if ( operational && state == IN_SESSION) {// start to insert after a new session started
			int result = 
					sessionManager.insertItem(item);// calling session manager to handle
			
			if (! operational){	
				return 2;
			}
			else 
				return result;
		}		
		return 3;
	}
	
	
	/* this method is called when user wants to end 
	 * a session 
	 */
	public void endSession(boolean isCoupon) {			
		if ( state == WAIT_FOR_PMT_METHOD || state == IN_SESSION) {
			sessionManager.endSession(isCoupon);		// calling session manager to handle	
			state = IDLE;						// change session state after session ended
		}	
	}
	
	
	/* this method returns current session log 
	 */
	public SessionLog getCurrentSessionLog() {
		return sessionManager.getCurrentSessionLog();
	}	
	
	
	/* return recyclable item type this RCM accepts 
	 */	
	public String recyclableType() {
		String s = "";
		for (RecyclableType type: recyclableType)
			s += type.toString() + "\n";
		return s;
	}
	
	public String toString() {
		String s = "RCM " + id ;
		if (location == null) {
			location = "not set";
		}
		s += "   -   Location:  " + location;
		s += "\n   Max weight: " + maxWeight;
		s += "  -  Current weight: " + currentWeight;
		s += "\n   Current money:  " + 
				NumberFormat.getCurrencyInstance().format(currentMoney);
		s += "\n   Current state:  ";
		if (operational) {
			s += "Operation";
		}
		else {
			s += "NOT operational";
		}
		
		switch (state) {
			case OFF:	s += " - OFF";
						break;
			case IDLE:	s += " - IDLE";
						break;
			case IN_SESSION: s += " - IN SESSION";
						break;
			case WAIT_FOR_PMT_METHOD: s += " - WAIT FOR PMT METHOD";
						break;
			case WAIT_FOR_USER_GET_PMT: s += " - USER GETTING PMT";
			break;
		}
		
		return s;
	}
	
	
	/****************************************************************************
	 * this is an inner class of RCM. it takes care of one
	 * customer session. 
	 */
	class SessionManager implements Serializable{
		SessionLog sessionLog;
		
		/* this method is called when customer starts
		 * a session 
		 */
		private void startSession() {		// create a new session log	
			sessionLog = new SessionLog();			
		}
		
		/* this method receives an item and adds it to the
		 * session or rejects if it's not recyclable
		 * 	-return 1 if item is not acceptable
		 * 	-return 0 if item is accepted	 
		 */
		private int insertItem(Item item) {								
			double price = getPrice(item);			
			if (price == -1) {		// item not acceptable				
				return 1;				
			}			
			
			double weight = item.getWeight();
			double newCurrentWeight = currentWeight + weight;
			if (newCurrentWeight > maxWeight) {		// if too heavy, not accepted		
				return 1;
			}	
			
			currentWeight = newCurrentWeight;	// update current weight			
			weightChange();
			
			item.setPrice(price);				// set item price 			
			sessionLog.addItem(item);			// add item to session log				
			return 0;
		}
		
		/* this method checks price of an item
		 * -input: item
		 * -output: price of item if it's recyclable
		 * -1 if not recyclable
		 */
		private double getPrice(Item item) {			
			double price = -1;			
			String material = item.getMaterial();	// get material
			double weight = item.getWeight();		// get weight
			
			outterLoop:								// loop thru each recycable type
			for (RecyclableType type: recyclableType) {				
				ArrayList<String> typeMaterial = type.getMaterial();
				double typeMinWeight = type.getMinWeight();
				double typeMaxWeight = type.getMaxWeight();
				double typePrice = type.getPrice();
				for (String s : typeMaterial) {		// check if material matched					
					if (material.equals(s)) {							
						if (weight > typeMinWeight && // check if weight within range
								weight <= typeMaxWeight) {							
							price = typePrice;
							break outterLoop;
						}						
					}
				}
			}			
			return price;
		}
		
		/* this method is called when customer ends
		 * a session 
		 */
		private void endSession(boolean isCoupon) {		
			if (sessionLog.getNumberOfItem() > 0) {	// if there is some items in session
				sessionLog.setDate(new Date());		// set date
				double amt = sessionLog.getAmount();
				if (isCoupon) {						// if user wants coupon
					sessionLog.setCash(0);
					sessionLog.setCoupon(amt);
				}
				else {								// if user wants cash
					if (currentMoney >= amt) {		// if enough money to pay in full
						sessionLog.setCash(amt);
					}
					else {							// if not enough money		
						sessionLog.setCash(currentMoney);	// pay with current amount
						sessionLog.setCoupon(amt-currentMoney);	// pay rest w/ coupon
					}					
				}
				currentMoney -= sessionLog.getCash();	// update current money
				if (sessionLog.getCash() > 0)			// if pay w/ cash					
					moneyChange();						// update current amt
				transactionLog.add(sessionLog);			// add current session log to RCM log			
			}			
		}
		
		/* this method returns current session log 
		 */
		public SessionLog getCurrentSessionLog() {
			return sessionLog;
		}
	}		// end SessionManager class		
}

