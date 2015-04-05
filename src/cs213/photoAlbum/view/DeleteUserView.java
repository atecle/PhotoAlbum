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


public class DeleteUserView extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame nysm;
	private JButton delete;
	private JTextField userID;
	private JTextField userName;

	public DeleteUserView(String pageHead)
	{
		super(pageHead);
		
		delete= new JButton("Delete User");
		delete.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		userID = new JTextField(10);
		userName = new JTextField(10);
		
		
		JLabel nameLabel = new JLabel("Enter New User Name: ");
		JLabel IDLabel = new JLabel("Enter User ID: ");

		

		//Action Listeners
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   	System.out.println("This is where we're deleting a user");

								
			}
								
		});
		
		JPanel background = new JPanel();
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		/*JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(userName);*/
		
		JPanel IdPanel = new JPanel();
		IdPanel.add(IDLabel);
		IdPanel.add(userID);
		
	    //content.add(namePanel);
		content.add(IdPanel);
		content.add(delete);
		
		add(background, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
		
	}
	
	
	
		
	public static void nowYouSeeMe() 
	{
		// TODO Auto-generated method stub
		
		nysm = new DeleteUserView("Delete User");
		nysm.setSize(300,150);
		nysm.setVisible(true);

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




