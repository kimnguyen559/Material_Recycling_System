package project;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import GUI_1.RCM;


/* **************************************************************
 * This class holds data and functionalities of a RMOS. 
 */
public class Rmos extends Observable
			implements Observer, Serializable{
	
	///////////   Constants   ///////////
	private static final double MONEY = 15;			// amount of money RMOS loads to RCM
	private static final double MAX_WEIGHT = 30;	// max weight for RCM members
	private static final 							// recyclable types RCM members can accept
		ArrayList<RecyclableType> RECYCLABLE_TYPE = // list of recyclable type at this RMOS	
				recyclableTypeList();	
	
	///////////   Data Members   ///////////
	private ArrayList<Rcm> rcmList = 
					new ArrayList<Rcm>();	// list of RCM members
	
	private HashMap<String,ArrayList<SessionLog>> transactionLog = 	// key is RCM IDs
			new HashMap<String, ArrayList<SessionLog>>();			// values is list of RCM session logs
	
	private HashMap<String,ArrayList<Date>> emptyDate = // key is RCM IDs
			new HashMap<String, ArrayList<Date>>();		// values is date the RCM got emptied
	
	
	/* constructor 
	 */
	public Rmos () {		
	}
	
	
	/* This method adds a RCM to the RMOS system 
	 */
	public String addRcm() {			
		int size = rcmList.size();
		Rcm rcm = new Rcm(""+ (size+1));		// create new RCM 
		String id = rcm.getId();		
		
		rcmList.add(rcm);						// add the new RCM to the system
		
		ArrayList<SessionLog> log = 			// add new element to RMOS transaction log
				new ArrayList<SessionLog>();
		transactionLog.put(id, log);					
		
		ArrayList<Date> date = 					// add new element to RMOS empty date log
				new ArrayList<Date>();		
		emptyDate.put(id, date);		
		
		return id;		// return new RCM id
	}
	
	
	/* this method activate a RCM 
	 */	
	public void activateRcm(String id,String location) {
		Rcm rcm = getRcm(id);
		rcm.setCurrentMoney(MONEY);				// load money
		rcm.setMaxWeight(MAX_WEIGHT);			// set max weight
		rcm.setRecyclableType(RECYCLABLE_TYPE);	// load acceptable types
		rcm.setState(Rcm.IDLE);					// set state
		rcm.setOperational(true);				// set operational
		rcm.setActivated(true);
		rcm.setLocation(location);				// set location
		rcm.addObserver(this);					// observe this RCM		
	}
	
	
	/* This method search for a RCM with an target id.
	 *  -input: target id
	 *  -output: matched RCM if found or null
	 */
	public Rcm getRcm(String id) {		
		if (! rcmList.isEmpty()) {				// if RCM list is not empty, then search
			for (Rcm rcm: rcmList) {
				if (rcm.getId().equals(id)) {	// if id matched target
					return rcm;
				}				
			}
		}
		return null;		
	}	
	
	
	/* this method return RCM member list 
	 */	 
	public ArrayList<Rcm> getRcmList() {
		return rcmList;
	}	
	
	
	/* this method returns empty date log of target RCM
	 * 	- input: target id
	 * 	- output: empty dates log
	 */
	public ArrayList<Date> getEmptyDates(String id) {
		for(Map.Entry<String, ArrayList<Date>> entry : 	// loop thru each entry
			emptyDate.entrySet()){
			if (entry.getKey().equals(id)) {			// if RCM id matches target
				return entry.getValue();				// return empty dates list
			}
		}
		return null;		
	}
	
	
	/* this method returns transaction log of target RCM
	 * 	- input: target id
	 * 	- output: transaction log of target RCM
	 */
	public ArrayList<SessionLog> getRcmTransactionLog(String id) {
		for(Map.Entry<String, ArrayList<SessionLog>> entry : 	// loop thru each entry
			transactionLog.entrySet()){
			if (entry.getKey().equals(id)) {	// if RCM id matched target
				return entry.getValue();		// return RCM transaction log
			}
		}
		return null;		
	}
	
	
	/* this method is called when RMOS got notified from RCM
	 */
	public void update(Observable observable, Object arg){		
		String [] s = (String[])arg;		
		setChanged();
		notifyObservers(arg);			// notify obverser when full or out of money	
	}
	
	
	/* This method unload all items out of a RCM 
	 */	
	public void emptyRcm(String id) {		
		Rcm rcm = getRcm(id);		
		if (rcm == null) {			
			return;
		}		
		ArrayList<SessionLog> log = 			// get transaction log from RCM
				rcm.getTransactionLog();
		transactionLog.get(id).addAll(log);		// update record at RMOS side
		log.clear();  							// clear record at RCM side.
		emptyDate.get(id).add(new Date());		// add date to empty dates list
		rcm.setCurrentWeight(0);				// reset current weight
		rcm.setOperational(true);				// reset RCM status		
	}
	
	/* This method load money to a RCM 
	 */	
	public void loadMoney(String id) {
		Rcm rcm = getRcm(id);		
		if (rcm == null) {			
			return;
		}		
		rcm.setCurrentMoney(MONEY);					
	}
	
	
	/* This method build the recyclable item list 
	 */
	private static ArrayList<RecyclableType> recyclableTypeList() {
		ArrayList<String> material = new ArrayList<String>();
		material.add("Glass");
		material.add("Aluminum");
		material.add("Plastic");
		
		RecyclableType t1 = new RecyclableType
						(0, 2,material, 1);
		RecyclableType t2 = new RecyclableType
						(2, 5,material, 2);
		ArrayList<RecyclableType> recyclableTypeList = 
				new ArrayList<RecyclableType> ();
		recyclableTypeList.add(t1);
		recyclableTypeList.add(t2);
		
		return recyclableTypeList;		
	}	
	
}
