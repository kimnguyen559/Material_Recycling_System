package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;



public class RcmGui extends JPanel {
	
	///////////////   constants   ///////////////	
	private static final int MIN = 1;	// 	min value for a random weight
	private static final int MAX = 7;	// 	max value for a random weight
	
	
	///////////////   data members   ///////////////
	private JTextArea displayArea;	// display area
	
	private JButton glass;			// glass button
	private JButton alum;			// aluminum button
	private JButton plastic;		// plastic button
	
	private JButton start;			// start button
	private JButton end;			// end button
	
	private JButton dispenser;		// money/coupon dispenser
	
	private JRadioButton cash;		// cash radio button
	private JRadioButton coupon;	// coupon radio button
	private JButton okBtn; 			// okay/confirm button
	
	private Rcm rcm;				// associated RCM
	private ViewControler viewControler = 		// to control what shows to user
						new ViewControler();
	
	
	/* constructor 
	 */
	public RcmGui(Rcm rcm) {
		this.rcm = rcm;		// assign a RCM to this GUI
		
		setLayout(new BorderLayout(10,10));
		
		JPanel panel1 = new WelcomePanel();		// welcome/top panel	
		JPanel panel2 = createCenterPanel();	// center panel
		JPanel panel3 = createBottomPanel();	// bottom panel
		
		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.CENTER);
		add(panel3, BorderLayout.SOUTH);
		
		viewControler.showStartMessage();		// display starting message
	}
	
	/* this method creates RCM center panel with a display
	    * area and 3 buttons of material choice: glass,
	    * aluminum and plastic	
	    */   
	   private JPanel createCenterPanel(){  
		   JPanel panel = new JPanel();
		   panel.setLayout(new BorderLayout(10,0));
		   
		   JPanel panel1 = new JPanel();
		   panel1.setLayout(new FlowLayout());
		   String s = "";
		   displayArea = new JTextArea();
		   displayArea.setText(s);
		   displayArea.setBackground(Color.WHITE);		   
		   displayArea.setForeground(new Color(243,132,62));		   
		   displayArea.setFont(new Font ("SansSerif", Font.BOLD, 16));
		   
		   JScrollPane scrollPane = new JScrollPane(displayArea);
		   scrollPane.setPreferredSize(new Dimension(350,250));
		   scrollPane.setBorder(null);
		   displayArea.setLineWrap(true);
		   displayArea.setWrapStyleWord(true);
		   panel1.add(scrollPane);
		   
		   JPanel panel2 = new JPanel();
		   panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 8));
	      
		   Border border = BorderFactory.createLineBorder
				   					(new Color(0,91,10), 2); 
		   ImageIcon icon = new ImageIcon("beer.png");
		   
		   glass = new JButton(icon);
		   glass.setBorder(border);
		   glass.setContentAreaFilled(false);
		   glass.setFocusPainted(false);
		   glass.setOpaque(false);
		   glass.addActionListener( new GlassActionListener() );
		   panel2.add(glass);
	      
		   border = BorderFactory.createLineBorder
				   					(new Color(204,0,0), 2); 
		   icon = new ImageIcon("alum_1.png");
		   
		   alum = new JButton(icon);
		   alum.setBorder(border);
		   alum.setContentAreaFilled(false);
		   alum.setFocusPainted(false); 
		   alum.setOpaque(false);
		   alum.addActionListener( new AlumActionListener() );
		   panel2.add(alum);
		   
		   border = BorderFactory.createLineBorder(new Color(0,83,153), 2);   
		   icon = new ImageIcon("water.png");
		   
		   plastic = new JButton(icon);
		   plastic.setBorder(border);
		   plastic.setContentAreaFilled(false);
		   plastic.setFocusPainted(false);
		   plastic.setOpaque(false);
		   plastic.addActionListener( new PlasticActionListener() );
		   panel2.add(plastic);
	      
		   panel.add(panel1, BorderLayout.NORTH);
		   panel.add(panel2, BorderLayout.SOUTH);
		   
		   return panel;
	   }
	   

	   /* this method creates RCM bottom panel
	   */
	   private JPanel createBottomPanel()	   {
		   JPanel panel = new JPanel();
		   panel.setLayout(new BorderLayout(5,5));	   
		   
		   JPanel panel1 = new JPanel();
		   panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
		   
		   ImageIcon icon = new ImageIcon("start_small_bw.png");
		   start = new JButton(icon);	   
		   Border emptyBorder = BorderFactory.createEmptyBorder();
		   start.setBorder(emptyBorder);
		   start.setBorderPainted(false); 
	       start.setContentAreaFilled(false); 
	       start.setFocusPainted(false); 
	       start.setOpaque(false);
		   start.addActionListener( new StartActionListener() );
		   panel1.add(start);	      
		           
		   icon = new ImageIcon("end_small_bw.png");
		   end = new JButton(icon);	
		   end.setBorder(emptyBorder);
		   end.setBorderPainted(false); 
	       end.setContentAreaFilled(false); 
	       end.setFocusPainted(false); 
	       end.setOpaque(false);
		   end.addActionListener( new EndActionListener() );
		   panel1.add(end);
		      
		   Border border = BorderFactory.createLineBorder(Color.BLACK, 1);         
		   icon = new ImageIcon("cash_receive_small.png");
		   dispenser = new JButton(icon);
		   dispenser.setBorder(border);	    
	       dispenser.setContentAreaFilled(false); 
	       dispenser.setFocusPainted(false); 
	       dispenser.setOpaque(false);
		   dispenser.addActionListener( new DispenserActionListener() );
		   panel1.add(dispenser);
		    
		   JPanel panel2 = new JPanel();
		   panel2.setLayout(new FlowLayout(FlowLayout.CENTER,20,8));
		      
		   JLabel label = new JLabel("Select payment method: "); 
		   panel2.add(label);
		   
		   cash = new JRadioButton("Cash", true);
		   coupon = new JRadioButton("Coupon");
		   
		   ButtonGroup group = new ButtonGroup();
		   group.add(cash);
		   group.add(coupon);
		   
		   panel2.add(cash);
		   panel2.add(coupon);
		   
		   okBtn = new JButton("OK");
		   okBtn.setBackground(Color.black);
		   okBtn.setForeground(Color.white);
		   okBtn.addActionListener( new OkActionListener() );
		   
		   panel2.add(okBtn);	
		   
		   panel.add(panel1, BorderLayout.NORTH);
		   panel.add(panel2, BorderLayout.SOUTH);
		   
		   return panel;
	   }	
	   
	   
	   /*	Tie a RCM to this GUI 
	    */
	   public void setRcm(Rcm rcm) {
		   this.rcm = rcm;
	   }
	   
	   
	   /*	refresh GUI display to show
	    * 	current state of RCM
	    */
	   public void refreshDisplay() {
		   viewControler.showStartMessage();	// let view controler do the work
	   }
	   
	   
	   /* =================================   Event Handlers   ================================= 
	    */
		/* ******************************************************************
	    * Inner class to handle event when user presses 'start' button
	    */
	   private class StartActionListener implements ActionListener {
		     public void actionPerformed( ActionEvent e ) {
		    	viewControler.startSession();		// call view controler to start session		    	 
		     }
	   }			///////////////////////// end StartActionListener class
	   
	   
	   /* ******************************************************************
	    * Inner class to handle event when user presses end button
	    */
	   private class EndActionListener implements ActionListener {
		     public void actionPerformed( ActionEvent e ) {
		    	viewControler.getPaymentMode();		// call view controler to display message for payment mode		    	 
		     }
	   }			///////////////////////// end EndActionListener class
	   
	   
	   /* ******************************************************************
	    * Inner class to handle event when user presses ok button for
	    * payment method
	    */
	   private class OkActionListener implements ActionListener {
		     public void actionPerformed( ActionEvent e ) {
		    	 boolean isCoupon;
		    	 if (coupon.isSelected())  {	// check if coupon button is selected
		    		 isCoupon = true;
		    	 }	
		    	 else {
		    		 isCoupon = false;
		    	 }
		    	viewControler.endSession(isCoupon);;		// call view controler to end session		    	 
		     }
	   }			///////////////////////// end OkActionListener class
	   
	   
	   /* ******************************************************************
	    * Inner class to handle event when user presses dispenser button 
	    */
	   private class DispenserActionListener implements ActionListener {
		     public void actionPerformed( ActionEvent e ) {
		    	 if (rcm.getState() == Rcm.WAIT_FOR_USER_GET_PMT) {
		    		 rcm.setState(Rcm.IDLE);	// change state
		    		 viewControler.showStartMessage();	// show message so user can start new session
		    	 }
		     }
	   }			///////////////////////// end DispenserActionListener class
	   
	   
	   /* ******************************************************************
	    * Inner class to handle event when user presses glass button
	    */
	   private class GlassActionListener implements ActionListener {
		   
		     public void actionPerformed( ActionEvent e ) {		    	 
		    	 viewControler.insertItem("Glass");		// call insert a glass item
		     } 
	   }			///////////////////////// end GlassActionListener class	
	   
	   
	   /* ******************************************************************
	    * Inner class to handle event when user presses aluminum button
	    */
	   private class AlumActionListener implements ActionListener {
		   
		     public void actionPerformed( ActionEvent e ) {		    	 
		    	 viewControler.insertItem("Aluminum");		// call insert an aluminum item
		     } 
	   }			///////////////////////// end AlumActionListener class
	   
	   
	   /* ******************************************************************
	    * Inner class to handle event when user presses plastic button
	    */
	   private class PlasticActionListener implements ActionListener {
		   
		     public void actionPerformed( ActionEvent e ) {		    	 
		    	 viewControler.insertItem("Plastic");		// call insert a plastic item
		     } 
	   }			///////////////////////// end PlasticActionListener class   
	   
	   
	   /* =================================   View Controler   ================================= 
	    */
		/* ******************************************************************
	    * Inner class to control what shows to user
	    */
	   private class ViewControler{
		   
		   /* show message when GUI is just open 
		    */
		   private void showStartMessage() {	
			   String s = "";
			   if (rcm.isOperational()) {	// if RCM not full
				   s += "Press start button to start recycling ";

				   if (rcm.isOutOfMoney()) {	// if out of money
					   s += "\n\nOut OF MONEY";
					   s += "\nYou will be paid with coupon";
				   }
			   }
			   else {	// if RCM full
				   s += "OUT OF ORDER ";
			   }
			   displayArea.setText(s);
		   }
		   
		   
		   /* starts a user session 
		    */		   
		   private void startSession() {
			   int result = rcm.startSession();		// call back end to start new session
		    	 if (result == 0) {					// if back end is ok to start new session
		    		 displayArea.setFont(new Font ("SansSerif", Font.BOLD, 16));
		    		 String s = "Starting new session...  \n\n";
		    		 s += "Insert item or press end button to quit ";
		    		 displayArea.setText(s);
		    	 }
		   }
		   
		   
		   /* inserts an item to session 
		    */		   
		   private void insertItem(String material) {
			   
			   Item item = new Item(computeWeight(), material);	// create an item
			   int result = rcm.insertItem(item);				// call back end to insert item
			   
			   if (result == 3) {		// if RCM is not in session, do nothing
				   return;
			   }
			   
			   String s = item.toString();
			   
			   if (result == 1) {			// item not acceptable
				   s +=  "    NOT ACCEPTED";
				   s += "\n\nAcceptable items:\n";
				   s += rcm.recyclableType();
				   s += "\nInsert other item or press end button to quit \n";
				   	
		    	 }
			   else {						// item accepted
				   s +=  "    ACCEPTED";
				   
				   if (result == 2) {					// if full after this item
					   s += "\n\nMachine is now full ";				   		 
					   s += "\n\nPress end button to quit \n";					  
				   }
				   
				   else {			// if not full, ask for more item
					   s += "\n\nInsert more item or press end to quit \n";
				   }			  
			   }
			   
			   displayArea.setText(s);
		   }
		   
		   
		   /* display message for payment method if 
		    * RCM is in session state. 
		    */
		   private void getPaymentMode() {
			   if ( rcm.getState() == Rcm.IN_SESSION) {
				   String s = "Session ending... \n";
				   s += "\nSelect Cash or Coupon";
				   s += "\nthen click OK button to confirm \n";
				   Font font = new Font ("SansSerif", Font.BOLD, 16);
				   displayArea.setFont(font);
				   displayArea.setText(s);
				   rcm.setState(Rcm.WAIT_FOR_PMT_METHOD);	// change RCM state
			   }
		   }
		   
		   
		   /* this method ends an user session 
		    */		
		   private void endSession(boolean isCoupon) {
			   if (rcm.getState() == Rcm.WAIT_FOR_PMT_METHOD) {	// change RCM state
			   String s = "";		   
			   Font font = new Font ("SansSerif", Font.BOLD, 14);
			   displayArea.setFont(font);		   
			   rcm.endSession(isCoupon);	// call back end to end session
			   SessionLog sessionLog = rcm.getCurrentSessionLog();	// get current session log
			   	if ( (sessionLog != null) && 		// if there is item in session
						 (sessionLog.getNumberOfItem() > 0)) {
					 s += "--RECEIPT:\n";					// generate receipt
					 s += sessionLog.toString();
					 s += "\nTake your cash/coupon and receipt at the dispenser \n";
					 
				 } else {		// no item
					 showStartMessage();	// display start message
					 return;
				 }
			   displayArea.setText(s);		
			   rcm.setState(Rcm.WAIT_FOR_USER_GET_PMT);	// change RCM state
			   }
		   }	
		   
		   
		   /* this method generates a random weight between MAX
		    * and MIN. 
		    */			   
		   private double computeWeight() {
			   double difference = rcm.getMaxWeight() - 
					   rcm.getCurrentWeight();
			   if (difference <= 5) {
				   return difference;
			   }
			   else {
				   Random r = new Random();
				   int weight = r.nextInt(MAX - MIN + 1) + MIN;
				   return weight;
			   }
		   }		   
	   }
}
