package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import cs213.photoAlbum.app.PhotoAlbumApp;
import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Backend;


public class AdminView extends JFrame
{

	private static final long serialVersionUID = 1L;
	private static JFrame nysm;
	private JButton add;
	private JButton delete;
	private JButton logout;
	private JButton listUsers;
	private JPanel window;
	
	public AdminView(String pageHead)
	{
		//Page Title
		super(pageHead);
		
		//defining JButtons
		add = new JButton("Add User");
		delete = new JButton("Delete User");
		logout = new JButton("Logout");
		listUsers = new JButton("List Users");
		
		//Action Listeners
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			LoginView.nowYouSeeMe();
			nysm.dispose();


								
			}
								
		});
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			AddUserView.nowYouSeeMe();

								
			}
			
			
					
		});
		

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			DeleteUserView.nowYouSeeMe();

								
			}
			
			
					
		});
	
		
		//Panel that will display the text
		JPanel contentPane = new JPanel();				
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEtchedBorder());
		contentPane.setPreferredSize(new Dimension(550,300));
		contentPane.setBackground(Color.WHITE);

		//adding buttons to bottomPanel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));	
		bottomPanel.add(add);
		bottomPanel.add(delete);
		bottomPanel.add(logout);
		
		
		//Adding panels to window
		window = new JPanel(new FlowLayout());
		window.add(contentPane, BorderLayout.CENTER);
		window.add(bottomPanel, BorderLayout.PAGE_END);
		add(window);
	

	}
	
	
	
	
	
    //This is called in LoginView so AdminView can be seen!
	public static void nowYouSeeMe() 
	{
		// TODO Auto-generated method stub

		nysm = new AdminView("Administrative View");
		nysm.setSize(600,400);
		nysm.setVisible(true);
		
		//Want to have the window centered on screen
		nysm.setLocationRelativeTo(null);
		nysm.setResizable(true);  
		nysm.setMinimumSize(nysm.getMinimumSize());
		nysm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}


}




