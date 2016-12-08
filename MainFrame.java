package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;


/* Constructs a frame with a RMOS and a RCM.  */

 public class MainFrame extends JFrame
 	implements Observer{ 
	 
	 private Container contentPane;		// content pane of frame
	 private RcmGui rcmPanel;			// right panel is RCM GUI
	 private RmosGui rmosPanel;			// left panel is RMOS GUI
	 private Rmos rmos;					// RMOS associated with RMOS GUI
	 private Rcm rcm;					// RCM associated with RCM GUI
	 private Admin admin;				// Admin associated with this GUI
	 
	 
	 /* constructor 
	  */
	 public MainFrame(Rmos rmos, Rcm rcm, Admin admin) {
		 this.rmos = rmos;			// assign a RMOS to left panel
		 this.rcm = rcm;			// assign a RCM to right panel
		 this.admin = admin;		// assign an Admin person to this GUI
		 rcm.addObserver(this);		// observe RCM (to check when full/out of money)
		 
		 contentPane = getContentPane();
		 setLayout(new BorderLayout()); 
		 createContentPane();				// build content pane
		 rmosPanel.setFrame(this);
		 
		 pack();		 
		 
		 addWindowListener(new WindowAdapter() {	// save data before closing
			    public void windowClosing(WindowEvent e) {
			    	try{
						FileOutputStream fileOut = new FileOutputStream("project.ser");
						ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
						outStream.writeObject(admin);
						outStream.close();
						fileOut.close();
					}catch(IOException i){
						i.printStackTrace();
					}	    	
			    }
		});		
	}
 
 
 /* Creates the content pane which holds a RMOS gui and a RCM gui
 */
	 public void createContentPane()
	 {	   
		 Color color = new Color(139, 191, 63);
		 Border border = BorderFactory.createLineBorder(color, 3);				 					
	   
		 rcmPanel = new RcmGui(rcm);
		 rmosPanel = new RmosGui(rmos);
    
		 rcmPanel.setBorder(border);
		 rmosPanel.setBorder(border);
   
		 contentPane.add(rcmPanel,BorderLayout.EAST);
		 contentPane.add(rmosPanel,BorderLayout.WEST);
	 }
	 
	 
	 /* 	call RCM GUI to refresh its display
	  */
	 public void refreshRcmGui() {
		 rcmPanel.refreshDisplay();;
	 }
	 
	 
	 /* *******************************************************
	    * this method is called when GUI got notified from RCM
	    *********************************************************/
		public void update(Observable observable, Object arg){			
			String[] str = (String[]) arg; 			
			JOptionPane.showMessageDialog(null, str[0],str[1], // show dialog box with message from param args
					    					JOptionPane.ERROR_MESSAGE);		
		}	
}