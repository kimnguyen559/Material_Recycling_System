package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


/*	this GUI of the RMOS 
 */
public class RmosGui extends JPanel 
			implements Observer{
	
	////////	constants	////////
	private static final String STR = 
			"\nSelect an option in the control list" +
			"\nThen press OK button to confirm";
	
	 static final int ACTIVATE_RCM_1 = 0;	// GUI states
	 static final int ACTIVATE_RCM_2 = 1;
	 static final int EMPTY_RCM = 2;    		
	 static final int LOAD_MONEY = 3;	
	 static final int CURRENT_STATUS = 4;	
	 static final int USAGE_STATS_1 = 5;	
	 static final int USAGE_STATS_2 = 6;
	 static final int EMPTY_DATES_1 = 7;
	 static final int EMPTY_DATES_2 = 8;
	
	
	//////// 	data members	////////
	private JTextArea displayArea;			// display area
	private JComboBox<String> controlCb;	// control combo box
	private JButton okControl;				// ok button to confirm control 
	
	private JComboBox<String> rcmCb;		// RCM id combo box
	private JButton okRcm;					// ok button to confirm RCM
	
	private JComboBox<String> frMonthCb;	// from month combo box
	private JComboBox<Integer> frDayCb;		// from day combo box
	private JComboBox<Integer> frYearCb;	// from year combo box
	
	private JComboBox<String> toMonthCb;	// to month combo box
	private JComboBox<Integer> toDayCb;		// to day combo box
	private JComboBox<Integer> toYearCb;	// to year combo box
	
	private JButton okDate;					// ok button to confirm dates
	
	private Rmos rmos;						// the RMOS associate with this GUI
	
	private ViewControler viewControler = 	// to control what shows to user
						new ViewControler();
	
	private ReportGenerator reportGenerator = 	// to generate reports
			new ReportGenerator();
	
	private int state;						// state of GUI
	private MainFrame frame;				// the frame that hold this RMOS GUi panel
	
	
	/*	constructor
	 */
	public RmosGui(Rmos rmos) {
		this.rmos = rmos;					// RMOS associated with this GUI
		rmos.addObserver(this);					// observe this RCM
		
		setLayout(new BorderLayout(10,10));
		
		JPanel panel1 = new WelcomePanel();		// welcome panel at top
		JPanel panel2 = createCenterPanel();	// center panel
		JPanel panel3 = createBottomPanel();	// bottom panel
		
		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.CENTER);
		add(panel3, BorderLayout.SOUTH);
		
		viewControler.loadRcmId();				// call controler to load RCM ids to combo box list
	}
	
	/* this method creates RMOS center panel 
	    */
	   public JPanel createCenterPanel() {
		   JPanel panel1 = new JPanel();
		   panel1.setLayout(new FlowLayout());
		  
		   Font font1 = new Font ("SansSerif", Font.BOLD, 16);
		   
		   displayArea = new JTextArea();
		   displayArea.setText(STR);   
		   displayArea.setBackground(Color.white);
		   displayArea.setForeground(new Color(243,132,62));		   
		   displayArea.setFont(font1);  
		   JScrollPane scrollPane = new JScrollPane(displayArea);   
		   scrollPane.setPreferredSize(new Dimension(350,250));
		   scrollPane.setBorder(null);
		   displayArea.setLineWrap(true);
		   displayArea.setWrapStyleWord(true);
		   panel1.add(scrollPane);
		   
		   JPanel panel2 = new JPanel();
		   panel2.setLayout(new GridLayout(2,3,20,30));		   
		   	
		   Color color = new Color(139, 191, 63);
		   Font font2 = new Font ("SansSerif", Font.BOLD, 12);
		   Border border = BorderFactory.createLineBorder(color, 1);  	
		   Border emptyBorder = BorderFactory.createEmptyBorder();
		   
		   JLabel label = new JLabel("   Controls");
		   label.setForeground(color);
		   label.setFont(font1);
		   panel2.add(label);
		   
		   String [] list = new String [] {"Add RCM", "Activate RCM", "Unload Items", 
				   			"Load Money", "Current Status", "Usage Stats",
				   			"Empty Dates"};
		   controlCb = new JComboBox<String>(list);		   
		   controlCb.setBorder(emptyBorder);
		   controlCb.setBorder(border);		    
		   controlCb.setOpaque(false);
		   controlCb.setForeground(color);
		   controlCb.setBackground(color.white);
		   controlCb.setFont(font2);
		   
		   panel2.add(controlCb);
		   
		   okControl = new JButton("OK");		  
		   okControl.setBackground(color);
		   okControl.setForeground(color.white);
		   okControl.setFont(font1);
		   okControl.setBorder(emptyBorder);
		   okControl.addActionListener( new ControlActionListener() );	// add listener for control ok button 	   
		   panel2.add(okControl);		   
		   
		   //color = new Color(238, 141, 9);
		   color = new Color(231, 146, 181);
		   label = new JLabel("   RCM");
		   label.setForeground(color);
		   label.setFont(font1);
		   panel2.add(label);
		   
		   list = new String []{"1"};
		   rcmCb = new JComboBox<String>(list);
		   border = BorderFactory.createLineBorder(color, 1);  	
		   rcmCb.setBorder(emptyBorder);
		   rcmCb.setBorder(border);		  
		   rcmCb.setOpaque(false);
		   rcmCb.setForeground(color);
		   rcmCb.setBackground(color.white);
		   rcmCb.setFont(font2);
		   panel2.add(rcmCb);
		   
		   okRcm = new JButton("OK");
		   okRcm.setBackground(color);
		   okRcm.setForeground(color.white);
		   okRcm.setFont(font1);
		   okRcm.setBorder(emptyBorder);		   
		   okRcm.addActionListener( new RcmActionListener() );	// add listener for RCM ok button
		   panel2.add(okRcm);	
		   
		   JPanel panel = new JPanel();
		   panel.setLayout(new BorderLayout(10,20));
		   panel.add(panel1, BorderLayout.NORTH);
		   panel.add(panel2, BorderLayout.SOUTH);
		   
		   return panel;		   
	   }
	   
	   
	   /* this method creates RMOS bottom panel 
	    */   
	   public JPanel createBottomPanel(){
		   JPanel panel = new JPanel();
		   panel.setLayout(new GridLayout(2,5,10,20));		   
		   
		   Color color = new Color(117, 163, 209);
		   Font font1 = new Font ("SansSerif", Font.BOLD, 14);
		   Font font2 = new Font ("SansSerif", Font.BOLD, 12);
		   Border border = BorderFactory.createLineBorder(color, 1);	
		   Border emptyBorder = BorderFactory.createEmptyBorder();
		   
		   JLabel label = new JLabel("   From");
		   label.setForeground(color);
		   label.setFont(font1);
		   panel.add(label);
		   
		   String [] month = new String[]{"Jan", "Fev", "Mar",
				   						"Apr", "May", "Jun",
				   						"Jul", "Aug", "Sep",
				   						"Oct", "Nov", "Dec"};
		   frMonthCb = new JComboBox<String>(month);		   
		   frMonthCb.setBorder(border);		   
		   frMonthCb.setOpaque(false);
		   frMonthCb.setForeground(color);
		   frMonthCb.setBackground(color.white);
		   frMonthCb.setFont(font2);
		   panel.add(frMonthCb);
		   
		   Integer [] day = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				   							11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
				   							21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
				   							31};
		   frDayCb = new JComboBox<Integer>(day);
		   frDayCb.setBorder(border);		  
		   frDayCb.setOpaque(false);
		   frDayCb.setForeground(color);
		   frDayCb.setBackground(color.white);
		   frDayCb.setFont(font2);
		   panel.add(frDayCb);
		   
		   Integer [] year = new Integer[]{2016};
		   frYearCb = new JComboBox<Integer>(year);
		   frYearCb.setBorder(border);		 
		   frYearCb.setOpaque(false);
		   frYearCb.setForeground(color);
		   frYearCb.setBackground(color.white);
		   frYearCb.setFont(font2);		   
		   panel.add(frYearCb);
		   
		   label = new JLabel(" ");
		   panel.add(label);
		   
		   label = new JLabel("   To");
		   label.setForeground(color);
		   label.setFont(font1);
		   panel.add(label);		   
		   
		   toMonthCb = new JComboBox<String>(month);
		   toMonthCb.setBorder(border);		   
		   toMonthCb.setOpaque(false);
		   toMonthCb.setForeground(color);
		   toMonthCb.setBackground(color.white);
		   toMonthCb.setFont(font2);
		   panel.add(toMonthCb);		   
		  
		   toDayCb = new JComboBox<Integer>(day);
		   toDayCb.setBorder(border);		  
		   toDayCb.setOpaque(false);
		   toDayCb.setForeground(color);
		   toDayCb.setBackground(color.white);
		   toDayCb.setFont(font2);
		   panel.add(toDayCb);		   
		   
		   toYearCb = new JComboBox<Integer>(year);
		   toYearCb.setBorder(border);		 
		   toYearCb.setOpaque(false);
		   toYearCb.setForeground(color);
		   toYearCb.setBackground(color.white);
		   toYearCb.setFont(font2);
		   panel.add(toYearCb);	
		   
		   okDate = new JButton("OK");
		   okDate.setBackground(color);
		   okDate.setForeground(color.white);
		   okDate.setFont(font1);
		   okDate.setBorder(emptyBorder);
		   okDate.addActionListener( new DateActionListener() );	// listener to date ok button
		   panel.add(okDate);
		  
		   return panel;		   
	   }
	   
	   
	   /*	Tie a RMOS to this GUI 
	    */
	   public void setRmos(Rmos rmos) {
		   this.rmos = rmos;
	   }
	   
	   
	   /*	Assign a Frame to hold this GUI 
	    */
	   public void setFrame(MainFrame frame) {
		   this.frame = frame;
	   }
	   
	   
	   /*	Get the Frame holding this GUI 
	    */
	   public MainFrame getFrame() {
		   return frame;
	   }
	   
	   
	   /* *******************************************************
	    * this method is called when GUI got notified from RCM
		*********************************************************/
		public void update(Observable observable, Object arg){			
			String[] str = (String[]) arg; 			
			JOptionPane.showMessageDialog(null, str[0],str[1],		// show message diaglog with message from 
					    					JOptionPane.ERROR_MESSAGE);		// parameters	
		}
	
	
	   /* ===================================   Event handlers   ===================================
	    
	    /* ******************************************************************
	    * Inner class to handle event when user presses ok button after
	    * selecting an option in the control combo box.
	    */
	   	private class ControlActionListener implements ActionListener {
	   		public void actionPerformed( ActionEvent e ) {			    	 
	   			String str = (String)controlCb.getSelectedItem(); 
	   			
	   			if ( str.equals("Add RCM")) {		// if user wants to add a RCM 
	   				viewControler.addRcm ();
	   			}
	   			
	   			else if ( str.equals("Activate RCM")) {	   // if user want to activate a RCM   	    	 
	      	    	 state = ACTIVATE_RCM_1;
	      	    	 String s = "Select an RCM then press OK button";
	      	    	 displayArea.setText(s);
	      	    	 viewControler.loadRcmId();		// re-load RCM id
	      		}
	   			
	   			else if ( str.equals("Current Status")) {	      	    	
	      	    	 state = CURRENT_STATUS;
	      	    	 String s = "Select an RCM then press OK button";
	      	    	 displayArea.setText(s);
	      	    	 viewControler.loadRcmId();
	      		}
	   			
	   			else if ( str.equals("Unload Items")) {	      	    	 
	      	    	 state = EMPTY_RCM;
	      	    	 String s = "Select an RCM then press OK button";
	      	    	 displayArea.setText(s);
	      	    	viewControler.loadRcmId();
	   			}
	   			
	   			else if ( str.equals("Load Money")) {	     	    	 
	     	    	 state = LOAD_MONEY;
	     	    	 String s = "Select an RCM then press OK button";
	     	    	 displayArea.setText(s);
	     	    	 viewControler.loadRcmId();
	  			}
	   			
	   			else if ( str.equals("Usage Stats")) {	     	    	 
	     	    	 state = USAGE_STATS_1;	     	    	 
	     	    	 viewControler.loadReportId();
	     	    	 String s = "Select an RCM then press OK button";
	     	    	 displayArea.setText(s);
	  			}
	   			
	   			else if ( str.equals("Empty Dates")) {	     	    	 
	     	    	 state = EMPTY_DATES_1;	     	    	 
	     	    	 String s = "Select an RCM then press OK button";
	     	    	 displayArea.setText(s);
	     	    	 viewControler.loadRcmId();
	  			}
	   		}
	   	}		/////////////   end ControlActionListener class  /////////////
	   	
	   	
	   	/* ******************************************************************
		    * Inner class to handle event when user presses ok button after
		    * selecting an RCM in the combo box.
		    */
		   private class RcmActionListener implements ActionListener {
			     public void actionPerformed( ActionEvent e ) {  
			    	 
			    	 if ( state == ACTIVATE_RCM_1) {
			    		 viewControler.isRcmActivated ();
			    	 }
			    	 
			    	 else if ( state == ACTIVATE_RCM_2) {        	    	 
			    		 viewControler.activateRcm();
	        	     }
			    	 
			    	 else if ( state == CURRENT_STATUS) {        	    	
			    		 viewControler.getCurrentStatus();
	        	     }
			    	 
			    	 else if ( state == EMPTY_RCM) {        	    	
	        	    	 viewControler.emptyRcm();
	        	     }
			    	 
			    	 else if ( state == LOAD_MONEY) {        	    	
	        	    	 viewControler.loadMoneyRcm();
	        	     }	
			    	 
			    	 else if ( state == USAGE_STATS_1 ||
			    			 state == USAGE_STATS_2) {        	    	
			    		 state = USAGE_STATS_2;		     	    	 
		     	    	 String s = "Select start date and end date then press OK";
		     	    	 displayArea.setText(s);
	        	     }
			    	 
			    	 else if ( state == EMPTY_DATES_1 ||
			    			 state == EMPTY_DATES_2) {        	    	
			    		 state = EMPTY_DATES_2;		     	    	 
		     	    	 String s = "Select start date and end date then press OK";
		     	    	 displayArea.setText(s);
	        	     }
			     }
		   }		/////////////   end RcmActionListener class  /////////////
		   
		   
		   /* ******************************************************************
		    * Inner class to handle event when user presses ok button after
		    * selecting from date and to date.
		    */
		   private class DateActionListener implements ActionListener {
			     public void actionPerformed( ActionEvent e ) {
			    	 if ( state == USAGE_STATS_2) {        	    	
			    		 viewControler.usageReport();
	        	     }
			    	 
			    	 else if ( state == EMPTY_DATES_2) {        	    	
			    		 viewControler.emptyDatesReport();
	        	     }
			     }
		   }		/////////////   end RcmActionListener class  /////////////
	   	
	   	
	   	/* ===================================   View Controler   ===================================
	    
	    /* ******************************************************************	     
	    /* Inner class to control what shows to user
	    */
	   	private class ViewControler {	   		
	   		
	   		/* gets list of associated RCMs id.
	 	    */
	 	   private ArrayList<String> getRcmId() {
	 		   ArrayList<Rcm> rcmList = rmos.getRcmList();
	 		   ArrayList<String> rcmId = new ArrayList<String>();
	 		   for (Rcm rcm : rcmList) {			// get all RCM id
	 			   rcmId.add(rcm.getId());
	 		   }
	 		   
	 		   return rcmId;	
	 	   }
	 	   
	 	   
	 	  /* this methods reload RCM id list to combo box
	 	    */
	 	   private void loadRcmId() {	 
	 		  ArrayList<String> rcmId = getRcmId();
	 		   rcmCb.setModel						// upload RCM id to combo box
	 		   		( new DefaultComboBoxModel( rcmId.toArray()) );	
	 	   }
	 	   
	 	   
	 	  /* this methods reload RCM id + a string "all RCMs" to combo box
	 	    */
	 	   private void loadReportId() {	 		   
	 		  ArrayList<String> rcmId = getRcmId();
	 		  rcmId.add("All RCMs");
	 		 rcmCb.setModel						// upload RCM id to combo box
		   		( new DefaultComboBoxModel( rcmId.toArray()) );	 		  
	 	   }
	 	   
	 	   
	 	  /* this methods add RCM to system
	 	   */		   
		   	private void addRcm () {
			   String id = rmos.addRcm();	// call back end to add new RCM
			   String s = "Added new RCM - id: " + id + "\n";
			   s += STR;
			   displayArea.setText(s);
			   loadRcmId();		// reload RCM id after new RCM added			   
		   	}
		   	
		   	
		   	/* this method verifies if a RCM is activated
		   	 */		   
		   	private void isRcmActivated () {
		   		String id = (String)rcmCb.getSelectedItem();	// get RCM id
				   
		   		Rcm rcm = rmos.getRcm(id);
		   		String s = "";
				   
		   		if ( rcm.isActivated() ) {		// if RCM already activated
		   			s += "RCM " + id + " already activated ";
		   			s += "\n\nSelect another RCM then press OK button";				  
		   		}
		   		else {
					s += "Enter location then press OK button ";					
					state = ACTIVATE_RCM_2;
		   		}
				   displayArea.setText(s);
		   	}
		   	
		   	
		   	/* this method activates a RCM  
		   	 */
		   	private void activateRcm() {
		   		String id = (String)rcmCb.getSelectedItem();	// read RCM id
		   		String location = displayArea.getText();		// read location
				   
		   		rmos.activateRcm(id, location);	// call back end to activate RCM
		   		String s = "RCM " + id + " is activated ";
		   		s += "\n";		   		
		   		s += STR;
		   		displayArea.setText(s);		   				   
		   	}
		   	
		   	
		   	/* this method display current status of a RCM 
		   	 */
		   	private void getCurrentStatus() {
		   		String id = (String)rcmCb.getSelectedItem();	// get RCM id
		   		Rcm rcm = rmos.getRcm(id);	// get RCM
		   		String s = rcm.toString();	// get RCM string
		   		s += "\n";
		   		s += STR;
		   		displayArea.setText(s);		
		   		double maxWeight = rcm.getMaxWeight();
		   		double currentWeight = rcm.getCurrentWeight();
		   		
		   		ArrayList<String> names = new ArrayList<String>();	// array of bar chart names
	   			ArrayList<Double> values = new ArrayList<Double>();	// array of bar chart values
	   			
	   			names.add("Max Weight");	// add max weight
	   			values.add(maxWeight);
	   			
	   			names.add("Current Weight");	// add current weight
	   			values.add(currentWeight);
	   			
	   			String chartTitle = "RCM " + id + "    -    Weight Status";	// bar chart title
		   		
		   		reportGenerator.drawUsageChar(names,values, chartTitle); 	// draw chart for current weight status
		   	}
		   	
		   	
		   	/* this method empty an RCM 
		   	 */
		   	private void emptyRcm() {
		   		String id = (String)rcmCb.getSelectedItem();	// get RCM id	
		   		rmos.emptyRcm(id);		// call back end to empty RCM
		   		String s = "RCM " + id + " is now empty";
		   		s += "\n";
		   		s += STR;
		   		displayArea.setText(s);	
		   		frame.refreshRcmGui();		// refresh RCM GUi display to show RCM is now operational		   
		   	}
		   	
		   	
		   	/* this method load money to an RCM 
		   	 */
		   	private void loadMoneyRcm() {
		   		String id = (String)rcmCb.getSelectedItem();	// get RCM id
		   		rmos.loadMoney(id);			// call back end to load money to RCM
		   		String s = "RCM " + id + " has money now";
		   		s += "\n";
		   		s += STR;
		   		displayArea.setText(s);
		   		frame.refreshRcmGui();		// refresh RCM GUi display to show RCM now has money			   
		   	}	
		   	
		   	
		   	/* this method generates report on usage statistics 
		   	 */
		   	private void usageReport() {
		   		String id = (String)rcmCb.getSelectedItem();	// read RCM id		   		
		   		
		   		String fromMonthStr = (String)frMonthCb.getSelectedItem();	// read start month
		   		int fromM = monthStrToInt(fromMonthStr);	// change month format
		   		
		   		String toMonthStr = (String)toMonthCb.getSelectedItem();	// read end month 
		   		int toM = monthStrToInt(toMonthStr);	// change end format
		   		
		   		int fromD = (int)frDayCb.getSelectedItem();  		
		   		
		   		int toD = (int)toDayCb.getSelectedItem();		   		
		   		
		   		int fromY = (int)frYearCb.getSelectedItem();		   		
		   		
		   		int toY = (int)toYearCb.getSelectedItem();
		   		
		   		Calendar calendar = Calendar.getInstance();	// create start Date obj
				calendar.clear();
				calendar.set(Calendar.MONTH, fromM);
				calendar.set(Calendar.DAY_OF_MONTH, fromD);
				calendar.set(Calendar.YEAR, fromY);
				Date startDate = calendar.getTime();		// refresh RCM GUi display to show updated state	
				
				calendar = Calendar.getInstance();		// create end Date obj
				calendar.clear();
				calendar.set(Calendar.MONTH, toM);
				calendar.set(Calendar.DAY_OF_MONTH, toD);
				calendar.set(Calendar.YEAR, toY);
				Date endDate = calendar.getTime();
				
				if ( ! id.equals("All RCMs")) {		// for single RCM report 
					reportGenerator.rcmUsageStatsReport(id, startDate, endDate);
				}
				else	// for all RCMs report
					reportGenerator.rmosUsageStatsReport(startDate, endDate);
		   	}
		   	
		   	
		   	/* this method generates report on empty dates
		   	 */
		   	private void emptyDatesReport() {
		   		String id = (String)rcmCb.getSelectedItem();
		   		
		   		String fromMonthStr = (String)frMonthCb.getSelectedItem();
		   		int fromM = monthStrToInt(fromMonthStr);
		   		
		   		String toMonthStr = (String)toMonthCb.getSelectedItem();
		   		int toM = monthStrToInt(toMonthStr);
		   		
		   		int fromD = (int)frDayCb.getSelectedItem();  		
		   		
		   		int toD = (int)toDayCb.getSelectedItem();		   		
		   		
		   		int fromY = (int)frYearCb.getSelectedItem();		   		
		   		
		   		int toY = (int)toYearCb.getSelectedItem();
		   		
		   		Calendar calendar = Calendar.getInstance();
				calendar.clear();
				calendar.set(Calendar.MONTH, fromM);
				calendar.set(Calendar.DAY_OF_MONTH, fromD);
				calendar.set(Calendar.YEAR, fromY);
				Date startDate = calendar.getTime();			
				
				calendar = Calendar.getInstance();
				calendar.clear();
				calendar.set(Calendar.MONTH, toM);
				calendar.set(Calendar.DAY_OF_MONTH, toD);
				calendar.set(Calendar.YEAR, toY);
				Date endDate = calendar.getTime();				
				
				reportGenerator.rcmEmptyDateReport(id, startDate, endDate);				
		   	}
		   	
		   	
		   	/* converse month name to an integer 
		   	 */
		   	private int monthStrToInt(String month) {		   		
		   		if (month.equals("Jan")) {
		   			return 0;
		   		}
		   		
		   		else if (month.equals("Fev")) {
		   			return 1;
		   		}
		   		
		   		else if (month.equals("Mar")) {
		   			return 2;
		   		}		   		
		   		
		   		else if (month.equals("Apr")) {
		   			return 3;
		   		}
		   		
		   		else if (month.equals("May")) {
		   			return 4;
		   		}
		   		
		   		else if (month.equals("Jun")) {
		   			return 5;
		   		}
		   		
		   		else if (month.equals("Jul")) {
		   			return 6;
		   		}
		   		
		   		else if (month.equals("Aug")) {
		   			return 7;
		   		}
		   		
		   		else if (month.equals("Sep")) {
		   			return 8;
		   		}
		   		
		   		else if (month.equals("Oct")) {
		   			return 9;
		   		}
		   		
		   		else if (month.equals("Nov")) {
		   			return 10;
		   		}
		   		
		   		else 
		   			return 11;		   		
		   	}
	   	}		/////////////   end ViewControler class   /////////////
	   	
	   	
	   	/* ===================================   Report Generator   ===================================
	    
	    /* ******************************************************************	     
	    /* Inner class to generate report
	    */
	   	private class ReportGenerator {
	   		int totalNumberOfItems;		// total number of items
	   		double totalWeight;			// total weight of a RCM
	   		double totalGlass;			// total glass weight of a RCM
	   		double totalAlum;
	   		double totalPlastic;
	   		double totalAmount;	 		// total amount  		
	   		double totalCash;			// total cash
	   		double totalCoupon;
	   		double totalRmos;			// total weight of all RCMs in RMOS
	   		ArrayList<Double> rcmWeight = 	// list of RCM weight
	   				new ArrayList<Double>();
	   		
	   		
	   		/*	this method generates usage statistics report
	   		 * of one single RCM 
	   		 */	
	   		private void rcmUsageStatsReport(String id, 
   					Date startDate, Date endDate) {
	   			totalNumberOfItems = 0;	// zero out old data
   				totalWeight = 0;
   				totalGlass = 0;
   				totalAlum = 0;
   				totalPlastic = 0;
   				totalAmount = 0;
   				totalCash = 0;
   				totalCoupon = 0;
   				String str = "";
   				
   				Calendar cal = Calendar.getInstance();
   				cal.setTime(startDate);
   				int month1 = cal.get(Calendar.MONTH) + 1;
   				int day1 = cal.get(Calendar.DAY_OF_MONTH);
   				int year1 = cal.get(Calendar.YEAR);
   				
   				cal.setTime(endDate);
   				int month2 = cal.get(Calendar.MONTH) + 1;
   				int day2 = cal.get(Calendar.DAY_OF_MONTH);
   				int year2 = cal.get(Calendar.YEAR);		
   				
   				rcmUsageStats(id, startDate, endDate);	// call to get updated data on RCM
   				
   				str += "RCM: "+id +" "+ month1+"/"+day1+"/"+year1;
   				str += " - "+month2+"/"+day2+"/"+year2;
   				str += "\n     -Number of items: " + totalNumberOfItems;
   				str += "\n     -Total weight: " + totalWeight + " lbs";
   				str += "\n         Glass: " + totalGlass + " lbs";
   				str += "\n         Aluminum: " + totalAlum + " lbs";
   				str += "\n         Plastic: " + totalPlastic + " lbs";
   				str += "\n     -Total amount: " + (totalCash + totalCoupon);
   				str += "\n         Cash: " + totalCash;
   				str += "\n         Coupon: " + totalCoupon;
   		 		
   				displayArea.setText(str);		// display report in text   				
   				
   				if ( totalWeight > 0 ) {   		// if RCM has any transaction	
   					String chartTitle = "RCM " + id + "    ";
   	   				chartTitle += month1+"/"+day1+"/"+year1;
   	   				chartTitle += " - "+month2+"/"+day2+"/"+year2;
   	   				
   					ArrayList<String> names = new ArrayList<String>();	// array of material names
   					ArrayList<Double> values = new ArrayList<Double>();	// array of weight of each material				   
	   			 
	   				names.add("Total weight");
	   				values.add(totalWeight);				
	   			
	   				names.add("Glass");
	   				values.add(totalGlass);
	   				   			
				
					names.add("Aluminum");
	   				values.add(totalAlum);
	   							
				
					names.add("Plastic");
	   				values.add(totalPlastic);				
				 
	   				drawUsageChar(names,values, chartTitle);  // draw weight stats chart
				
	   				names = new ArrayList<String>();	// array of bar chart names
	   				values = new ArrayList<Double>();	// array of bar chart values
				
	   				names.add("Total Amount");			
	   				values.add(totalAmount);
				
	   				names.add("Total Cash");
	   				values.add(totalCash);
				
	   				names.add("Total Coupon");
	   				values.add(totalCoupon);
				
	   				drawUsageChar(names,values, chartTitle); 	// draw money stats chart
   				}
   		}
	   		
	   		
	   		/*	this method generates usage statistics report
	   		 * of all RCMs in RMOS system 
	   		 */	
	   		private void rmosUsageStatsReport( Date startDate, Date endDate) {
	   			totalNumberOfItems = 0;		// zero out old data
   				totalWeight = 0;
   				totalGlass = 0;
   				totalAlum = 0;
   				totalPlastic = 0;
   				totalAmount = 0;
   				totalCash = 0;
   				totalCoupon = 0;
   				totalRmos = 0;
   				rcmWeight.clear();
   				String str = "";
   				
   				Calendar cal = Calendar.getInstance();
   				cal.setTime(startDate);
   				int month1 = cal.get(Calendar.MONTH) + 1;
   				int day1 = cal.get(Calendar.DAY_OF_MONTH);
   				int year1 = cal.get(Calendar.YEAR);
   				
   				cal.setTime(endDate);
   				int month2 = cal.get(Calendar.MONTH) + 1;
   				int day2 = cal.get(Calendar.DAY_OF_MONTH);
   				int year2 = cal.get(Calendar.YEAR);		
   				
   				ArrayList<String> rcmIdList = viewControler.getRcmId();	// get all RCM ids
   				for (String id : rcmIdList) {		// walk thru each RCM
   					totalNumberOfItems = 0;
   	   				totalWeight = 0;
   	   				totalGlass = 0;
   	   				totalAlum = 0;
   	   				totalPlastic = 0;
   	   				totalAmount = 0;
   	   				totalCash = 0;
   	   				totalCoupon = 0;
   					rcmUsageStats(id, startDate, endDate);	// get data of the RCM
   					totalRmos += totalWeight;				// add to total weight of RMOS
   					rcmWeight.add(totalWeight);				// add RCM weight to list
   				}
   				
   				str += "RMOS     "+ month1+"/"+day1+"/"+year1;
   				str += " - "+month2+"/"+day2+"/"+year2;   				
   				str += "\n     -Total weight: " + totalRmos + " lbs";
   				
   				for (int i = 0 ; i < rcmIdList.size() ; i++) {  // walk thru each RCM id 				
   					str += "\n         RCM " + rcmIdList.get(i) + 	// show RCM id & weight
   							": " + rcmWeight.get(i) + " lbs";
   				}
   		 		
   				displayArea.setText(str);		// display report in text   				
   				
   				if ( totalRmos > 0 ) {   		// if there is data in RMOS		
   					String chartTitle = "RMOS    ";
   	   				chartTitle += month1+"/"+day1+"/"+year1;
   	   				chartTitle += " - "+month2+"/"+day2+"/"+year2;
   	   				
   					ArrayList<String> names = new ArrayList<String>();	// bar names
   					ArrayList<Double> values = new ArrayList<Double>();	// bar values			   
	   			 
	   				names.add("RMOS Total");	// add total RMOS weight
	   				values.add(totalRmos);	
	   				
	   				for (int i = 0 ; i < rcmIdList.size() ; i++) {	   				
	   					names.add("RCM " + rcmIdList.get(i));		// add RCM id
	   					values.add(rcmWeight.get(i));				// add RCM weight
	   				} 							
				 
	   				drawUsageChar(names,values, chartTitle);		// draw chart	
   				}
   		}	   		
	   		
	   		/* show a frame with a bar chart
	   		 * 	input: -names: array of names of each bar
	   		 * 			-values: array of correspondent value of bars
	   		 */
	   		private void drawUsageChar(ArrayList<String> names,
	   								ArrayList<Double> values, 
	   								String chartTitle) {	
	   			Color color1 = new Color(99, 66, 113);			// colors to draw chart
	   			Color color2 = new Color(255, 113, 72);
	   			Color color3 = new Color(233, 188, 27);
	   			Color color4 = new Color(189, 1, 2);	   			
	   			Color [] colors = {color1, color2, color3, color4, // set colors of bars
						   			color1, color2, color3, color4};
	   			
	   			BarChart barChart = new BarChart();		// create a bar chart
	   			JFrame charFrame = new JFrame();		// frame to hold chart
	   			charFrame.add(barChart);
	   			charFrame.pack();
	   			charFrame.setTitle(chartTitle);
	   			barChart.showBarChart(names, values,colors);	// call to paint chart
	   			charFrame.setLocationRelativeTo(null);
	   			charFrame.setVisible(true);					// show frame
	   		}  	
	   	
	   	
	   	/*	this method calculates usage statistics of one RCM 
   		 */	   		
   		private void rcmUsageStats(String id, 
   					Date startDate, Date endDate) {	    		
   			ArrayList<SessionLog> log = 			// get transaction log of the RCM
   					rmos.getRcmTransactionLog(id);
   			if ( (log != null) && (log.size() > 0) ) {	
   				for (SessionLog sl : log){			// loop thru each session log 
   					Date date = sl.getDate();
   					if ( !(date.before(startDate) || date.after(endDate))) {  // if date within range							
   						totalNumberOfItems += sl.getNumberOfItem();			// add number of items
   						totalWeight += sl.getTotalWeight();					// add total weight
   						totalGlass += sl.getGlass();			
   						totalAlum += sl.getAlum();
   						totalPlastic += sl.getPlastic();
   						totalCash += sl.getCash();
   						totalCoupon += sl.getCoupon();
   						totalAmount += sl.getCash() + sl.getCoupon();
   					}			
   				}   					 				
   			} 
   		}
   			
   			/* this method generates empty dates statistics of a
   			 * RCM in a duration. 
   			 */
   			public void rcmEmptyDateReport(String id, 
   					Date startDate, Date endDate) {
   				int numberOftimes = 0;
   				Date lastEmpty;
   				String str = "";
   				
   				Calendar cal = Calendar.getInstance();
   				cal.setTime(startDate);
   				int month1 = cal.get(Calendar.MONTH) + 1;
   				int day1 = cal.get(Calendar.DAY_OF_MONTH);
   				int year1 = cal.get(Calendar.YEAR);
   				
   				cal.setTime(endDate);
   				int month2 = cal.get(Calendar.MONTH) + 1;
   				int day2 = cal.get(Calendar.DAY_OF_MONTH);
   				int year2 = cal.get(Calendar.YEAR);	
   				
   				str += "RCM "+id +"     "+ month1+"/"+day1+"/"+year1;
					str += " - "+month2+"/"+day2+"/"+year2;   				
   				ArrayList<Date> emptyDates = 				// get list of empty dates
   						rmos.getEmptyDates(id);
   				if ( (emptyDates != null) && (emptyDates.size() > 0) ){
   					for (Date date : emptyDates){   		// check each empty date					
   						if ( !(date.before(startDate) || date.after(endDate))) {	// if within date range
   	   							numberOftimes++;				// increase number of empty times					
   	   					}   								
   					}
   					
   					lastEmpty = emptyDates.get(emptyDates.size() -1);	// get last empty date
   					cal.setTime(lastEmpty);
   					int month = cal.get(Calendar.MONTH) + 1;
   					int day = cal.get(Calendar.DAY_OF_MONTH);
   					int year = cal.get(Calendar.YEAR);	   					
   					str += "\n     -Number of empty times : " + numberOftimes;
   					str += "\n     -Last empty: " + month+"/"+day+"/"+year;			
   		 		}
   				
   				else {
   					str += "\n     No record available.";
   				}
   				displayArea.setText(str);	
   			}	
   		}
	   	
	   	
	   	/* ===================================   Report Generator   ===================================	   	
	   	
	   	/* this class draw a bar chart in a panel 
	   	 */
	   	private class BarChart extends JPanel{
	   		private ArrayList<String> names;	// bar names
	   		private ArrayList<Double> values;	// bar values
	   		private Color[] colors;				// bar colors
	   		
	   		
	   		/* this method shows the chart on screen
	   		 */
	   		public void showBarChart(ArrayList<String> names, 
	   				ArrayList<Double> values,
	   				Color[] colors) {
	   			this.names = names;
	   			this.values = values;
	   			this.colors = colors;
	   			repaint() ;			// paint the carvas
	   		}
	   		
	   		
	   		/*	paint chart	   		 
	   		 */
	   		protected void paintComponent(Graphics g) {
	   			super.paintComponent(g);
	   			int width = getWidth();				// get screen width
	   			int height = getHeight();			// get screen height
	   			int interval = (width - 40)/(names.size());	// gap between 2 bars
	   			int individualWidth = 						// bar width
	   					(int)(((width - 40)/24)*0.6);	
	   			
	   			double maxValue = 0;				// find max value
	   			for ( int i = 0; i < values.size() ; i++) {
	   				if (maxValue < values.get(i)) {
	   					maxValue = values.get(i);
	   				}
	   			}
	   			
	   			int x = 30;
	   			g.drawLine
	   				(10,  height - 45, width- 10,  height - 45);	// draw x axis
	   			
	   			for ( int i = 0; i < values.size() ; i++) {		// walk thru each value and draw a bar
	   				Color color = colors[i];		// set color of the bar	
	   				g.setColor(color);
	   				
	   				int barHeight = 				// calculate bar height based on value
	   						(int)(((double)values.get(i)/(double)maxValue)*(height-65));
	   				
	   				g.fillRect						// draw a filled rectangular
	   					(x, height-45-barHeight, individualWidth, barHeight);
	   				
	   				g.drawString(names.get(i), x-10, height-30);	// draw name at bottom of bar
	   				
	   				double value = Math.floor(values.get(i) * 100) / 100;
	   				g.drawString(value+"", x-5, height-45-barHeight-5); // draw value at top of bar
	   				
	   				x += interval;		// move one interval to draw next bar
	   			}		
	   		}
	   		
	   		
	   		/* this method sets size of the panel 
	   		 */
	   		public Dimension getPreferredSize() {
	   			return new Dimension(400,300);
	   		}
	   	
	}

}
