package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;




public class AddUserView extends JFrame 
{

	private static JFrame nysm;
	private JButton add;
	private JTextField userID;
	private JTextField userName;
	private JPanel window;


	
	public AddUserView(String pageHead)
	{
		super(pageHead);
		
		add= new JButton(" Add User");
		add.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		userID = new JTextField(10);
		userName = new JTextField(10);
		
		
		JLabel nameLabel = new JLabel("Enter New User Name: ");
		JLabel IDLabel = new JLabel("Enter New User ID: ");

		

		//Action Listeners
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    //String ID = userID.getText();
				//String name = userName.getText();

				System.out.println("This is where we're adding a user");

								
			}
								
		});
		
		JPanel background = new JPanel();
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(userName);
		
		JPanel IdPanel = new JPanel();
		IdPanel.add(IDLabel);
		IdPanel.add(userID);
		
		content.add(namePanel);
		content.add(IdPanel);
		content.add(add);
		
		add(background, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
		
	}
	
	
	
	
	
	public static void nowYouSeeMe() 
	{
		// TODO Auto-generated method stub
		
		nysm = new AddUserView("Add User");
		nysm.setSize(300,150);
		nysm.setVisible(true);
		// center on screen
		nysm.setLocationRelativeTo(null);
		nysm.setResizable(false); 
		nysm.setMinimumSize(nysm.getMinimumSize());

		nysm.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				nysm.dispose();
			}
		});
		
	}

}
