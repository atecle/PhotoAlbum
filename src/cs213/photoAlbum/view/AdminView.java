/**
 * @author Adam Tecle
 * 
 */
package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.User;

/**
 * This class is responsible for the display and the functionality on the AdminView window.
 * The user "admin" can add new a user, delete a current user and list all of the users stored.
 * 
 */

public class AdminView extends JFrame
{

	/**Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;

	/**JButton for creating a new user and deleting a user*/
	private JButton add, delete;

	/**Client object declaration*/
	private Client client;

	/**Works with JList to hold the names of the current users stored*/
	private DefaultListModel<User> listModel;
	
	/**List to hold the names of the current stored users */
	private JList<User> userList;

	/** To replace default content pane **/
	private JPanel contentPane, buttonPanel;
	
	
	
	/**
	 * Class constructor which creates the frame of the LoginView Window
	 * 
	 * @param c  Allows access to the stored data 
	 */

	public AdminView(Client c)
	{

		super("Administrative View");
		setSize(600,400);
		setResizable(false);

		this.client = c;
		
		listModel = new DefaultListModel<User>();

		add = new JButton("Add User");
		delete = new JButton("Delete User");
		
		for (User user : client.getUsers()) {
			listModel.addElement(user);
		}
		
		userList = new JList<User>(listModel);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(userList);

		//Logout changed to save on close
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddUserView addUserView = new AddUserView(client, listModel);
				addUserView.setLocationRelativeTo(null);
				addUserView.setVisible(true);
				return;
			}
		});

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = userList.getSelectedIndex();
				
				if (index == -1) return;
				
				int selectedOption = JOptionPane.showConfirmDialog(null, 
						"Are you sure?", 
						"Choose", 
						JOptionPane.YES_NO_OPTION); 
				if (selectedOption == JOptionPane.NO_OPTION) {
					return;
				}
				
				User user = userList.getModel().getElementAt(index);
				client.deleteUser(user.getID());
				((DefaultListModel<User>) userList.getModel()).remove(index);
				client.writeUsers();
				return;
			}
		});


		//Panel that will display the text
		contentPane = new JPanel(new BorderLayout());				
		contentPane.setBorder(BorderFactory.createEtchedBorder());
		contentPane.setPreferredSize(new Dimension(550,300));
		contentPane.setBackground(Color.WHITE);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		//adding buttons to bottomPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));	
		buttonPanel.add(add);
		buttonPanel.add(delete);

		contentPane.add(buttonPanel, BorderLayout.PAGE_END);
		
		setContentPane(contentPane);
		
	}


}




