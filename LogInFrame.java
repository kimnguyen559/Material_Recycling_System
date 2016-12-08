package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


/*	This class shows a log in page for Admin
 * to sign in 
 */
public class LogInFrame extends JFrame 
					implements ActionListener{
	private Container contentPane; 		// frame content pane 
	private JTextField userId;			// user id text field
	private JPasswordField password;	// user password field
	private JButton signIn;				// sign in button
	private String id;					// user id
	private String pw;					// user password
	private JLabel messageDisplay;		// display area (when wrong id/pw)
	private Admin admin;				// system admin
	
	
	/*	Constructor 
	 */
	 public LogInFrame()
	   {  
		  contentPane = getContentPane();
		  setLayout(new BorderLayout());      
	      createContentPane();				// build content pane
	      readData();						// read saved id & pw	      
	      pack();
	   }
	   
	 /* Creates the control panel which holds welcome panel,
	  * center panel and bottom panel
	  */
	   public void createContentPane()
	   {
		   JPanel welcomePanel = new WelcomePanel();
		   JPanel centerPanel = createCenterPanel();
		   JPanel bottomPanel = createBottomPanel();
		   
		   contentPane.add(welcomePanel, BorderLayout.NORTH);
		   contentPane.add(centerPanel, BorderLayout.CENTER);
		   contentPane.add(bottomPanel,BorderLayout.SOUTH);	      
	   }	   
	   
	   
	   /*	create center panel 
	    */	   
	   public JPanel createCenterPanel() {
		   JPanel panel = new JPanel();
		   panel.setLayout(new GridLayout(2,2));
		   
		   Color color = new Color(51, 102, 51);
		   
		   JLabel userLabel = new JLabel("User id:  ");	// id label
		   panel.add(userLabel);
		   
		   userId = new JTextField(10);		// id text field
		   userId.setForeground(color);
		   panel.add(userId);
		   
		   JLabel passwordLabel = new JLabel("Password:  ");	// pw label
		   panel.add(passwordLabel);
		   
		   password = new JPasswordField(10);	// pw text field
		   password.setEchoChar('*');
		   password.setForeground(color);
		   panel.add(password);
		   
		   JPanel panel2 = new JPanel();
		   panel2.setLayout(new FlowLayout());
		   
		   Font font = new Font ("SansSerif", Font.BOLD, 13);		   
		   messageDisplay = new JLabel(" ");			// display area (for wrong id/pw)   
		   messageDisplay.setForeground(new Color(189, 1, 2));
		   messageDisplay.setFont(font);
		   panel2.add(messageDisplay);
		   
		   JPanel panel1 = new JPanel();
		   panel1.setLayout(new BorderLayout());
		   panel1.add(panel, BorderLayout.NORTH);
		   panel1.add(panel2, BorderLayout.SOUTH);
		   
		   return panel1;		   
	   }
	   
	   
	   /* create bottom panel of frame 
	    */
	   public JPanel createBottomPanel(){
		   JPanel panel = new JPanel();
		   panel.setLayout(new FlowLayout());
		   signIn = new JButton("Sign In");			// sign in button
		   signIn.setBackground(new Color(243,132,62));
		   signIn.setForeground(Color.white);
		   Border emptyBorder = BorderFactory.createEmptyBorder();
		   signIn.setBorder(emptyBorder);
		   Font font = new Font ("SansSerif", Font.BOLD, 16);
		   signIn.setFont(font);
		   signIn.addActionListener( this );		// set event handler
		   panel.add(signIn);
		   return panel;		   
	   }
	   
	   
	   /* handle event when user click 'sign in' button
	    */			   
	   public void actionPerformed( ActionEvent event ){
		   String input1 = userId.getText();
		   String input2 = String.valueOf(password.getPassword());
		   
		   if ( input1.equals(id) && input2.equals(pw)) {	// when correct id & pw
			   Rmos rmos = admin.getRmos();					// get RMOS
			   Rcm rcm = rmos.getRcm("1");					// get RCM 1
			   JFrame mainFrame = new MainFrame				// create a frame hold RMOS & RCM 1
					   				(rmos, rcm, admin);
			   mainFrame.setDefaultCloseOperation
			   						(JFrame.EXIT_ON_CLOSE);
			   mainFrame.setTitle("EcoRe System");
			   mainFrame.setLocationRelativeTo(null);
			   mainFrame.setVisible(true);					// show new frame (w/ RMOS & RCM1)
			   this.setVisible(false);						// close current (log-in) frame
		   }
		   
		   else{					// if wrong id/pw
			   userId.setText("");		// clear id text field
			   userId.setCaretPosition(0);
			   password.setText("");	// clear pw text field
			   messageDisplay.setText	// display error message
			   			("Wrong user id and/or password");
		   }
	    }
	   
	   
	   /* read saved id & password. 
	    */
	   private void readData(){		   
		   try{
				FileInputStream fileIn =new FileInputStream("project.ser");	// open saved .ser file
				ObjectInputStream in = new ObjectInputStream(fileIn);
				admin = (Admin) in.readObject();	// read Admin obj
				in.close();
				fileIn.close();
			}catch(IOException i){
				System.out.println(".ser file not found");
				System.out.println("Exiting...");
				return;
			}catch(ClassNotFoundException c){
				System.out.println("Admin class not found");
				System.out.println("Exiting...");
				return;
			}
		   id = admin.getUserId();		// read admin id	   
		   pw = admin.getPassword();	// read admin pw	   
	   }
	   
	   
	   public static void main (String [] args) {
			////////////////   show log-in frame   ////////////////			
			JFrame frame = new LogInFrame();		// create log-in frame
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("EcoRe System");
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);   				// show the frame  
	   }
}
