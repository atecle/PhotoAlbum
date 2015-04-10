package cs213.photoAlbum.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.User;


/**
 * This class is responsible for the display and the functionality on the DeleteUserView window. 
 * The user "admin" can add a new user by inputting a new user name and a corresponding user id.
 *
 */

public class AddUserView extends JFrame 
{

	/**Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;

	/** To replace default content pane **/
	private JPanel contentPane;

	/**Add button for creating a new user*/
	private JButton addButton;

	/**Textfield for user id and name input */
	private JTextField userIDField, userNameField;

	/** Label for user name, id, and password field, **/
	private JLabel nameLabel, userIDLabel, userPasswordLabel;
	
	/**Textfield for password input*/
	private JPasswordField passwordField;

	/**Client object declaration*/
	private Client client;

	private DefaultListModel<User> listModel;
	


	/**
	 * Class constructor which creates the frame of the AddUserView Window
	 * 
	 * @param pageHead Title of the AdminView Window
	 * @param c  Allows access to the stored data 
	 */
	public AddUserView(Client c, DefaultListModel<User> m)
	{
		super("Add User");
		this.client = c;
		this.listModel = m;
		setSize(300,230);
		setResizable(false);

		addButton = new JButton("Add User");
		addButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

		userIDField = new JTextField(20);
		userNameField = new JTextField(20);
		passwordField = new JPasswordField(20);
		passwordField.setEditable(true);

		nameLabel = new JLabel("Enter User Name: ");
		userIDLabel = new JLabel("Enter User ID: ");
		userPasswordLabel = new JLabel("Enter Password: ");

		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String id = userIDField.getText();

				String name = userNameField.getText();
				
				String password = new String(passwordField.getPassword());				
				
				if (id.length() == 0) {

					JOptionPane.showMessageDialog(null, "ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (name.length() == 0) {

					JOptionPane.showMessageDialog(null, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!client.addUser(id, name, password)) {

					JOptionPane.showMessageDialog(null, "User with specified ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				User user = new User(id, name, password);
				listModel.addElement(user);
				client.writeUsers();
				dispose();

			}

		});

		contentPane = new JPanel(new FlowLayout());

		contentPane.add(nameLabel);
		contentPane.add(userNameField);
		contentPane.add(userIDLabel);
		contentPane.add(userIDField);
		contentPane.add(userPasswordLabel);
		contentPane.add(passwordField);
		contentPane.add(addButton);

		setContentPane(contentPane);

	}
}



