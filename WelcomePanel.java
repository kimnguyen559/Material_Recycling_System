package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WelcomePanel extends JPanel{
	public WelcomePanel() {
		setLayout(new BorderLayout());
		
		ImageIcon icon = new ImageIcon("recycle_3.png");
		add(new JLabel(icon), BorderLayout.WEST);
		
		JLabel label = new JLabel("  Welcome to EcoRe! ", SwingConstants.CENTER);  	
		label.setFont(new Font ("SansSerif", Font.BOLD, 24));
		label.setForeground(new Color(243,132,62));		
		add(label, BorderLayout.CENTER);
		
		icon = new ImageIcon("recycle_2.png");
		add(new JLabel(icon), BorderLayout.EAST);		  
	   }
}
