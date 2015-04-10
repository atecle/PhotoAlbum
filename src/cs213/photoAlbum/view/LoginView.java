package cs213.photoAlbum.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.User;

/**
 * 
 * This class is responsible for the display and the functionality on the LoginView window. 
 * The Login window will allow for a special user "admin" to login, which will then call the AdminView class.
 * Once a user is successfully logged in, the CollectionView class is implemented. 
 *
 */
public class LoginView extends JFrame {

	/**Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;

	/**Label for list of users*/
	private JLabel userLabel;

	/**Textfield for password input*/
	private JPasswordField passwordField;

	/**Holds the list of users currently stored*/
	private JComboBox<User> usersList;

	/**Login button for current users or special user "admin"*/
	private JButton loginButton;

	/**The frame of the LoginView*/
	private JPanel window;

	final private User admin = new User("-1", "Admin");

	private DefaultComboBoxModel<User> comboBoxModel;

	/**Client object declaration*/
	public Client client;

	/**
	 * Class constructor which creates the frame of the LoginView Window
	 * 
	 * @param c Allows access to the stored data
	 */
	public LoginView(Client c) {

		super("Login");

		setSize(300, 250);
		setResizable(false);
		this.client = c;

		usersList = new JComboBox<User>();
		comboBoxModel = new DefaultComboBoxModel<User>(client.getUsers().toArray(new User[client.getUsers().size()]));
		comboBoxModel.addElement(admin);
		usersList.setModel(comboBoxModel);

		userLabel = new JLabel("User: ");

		passwordField = new JPasswordField(20);
		passwordField.setEditable(true);

		usersList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				User user = (User) usersList.getSelectedItem();
				if (user == null)  {
					passwordField.setEditable(true);
					return;	
				}
				if (user.getName().compareTo("Admin") == 0) {
					passwordField.setText("");
					passwordField.setEditable(false);
				}
				else {
					passwordField.setEditable(true);
				}

			}
		});

		loginButton = new JButton("Login");

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String pw = new String(passwordField.getPassword());
				User user = (User) usersList.getSelectedItem();

				if (user.getName().compareTo("Admin") == 0) {

					final AdminView adminView = new AdminView(client);

					adminView.addWindowListener(new WindowAdapter() {

						public void windowClosing(WindowEvent e) {


							client.writeUsers();
							usersList.removeAllItems();
							comboBoxModel = new DefaultComboBoxModel<User>(client.getUsers().toArray(new User[client.getUsers().size()]));
							comboBoxModel.addElement(admin);
							usersList.setModel(comboBoxModel);
							setVisible(true);
							adminView.dispose();
							return;

						}
					});

					adminView.setLocationRelativeTo(null);
					adminView.setVisible(true);
					setVisible(false);
				}
				else {

					if (user.loginSuccess(pw)) {

						final CollectionView collectionView = new CollectionView(user, client);
						client.login(user.getID());

						collectionView.addWindowListener(new WindowAdapter() {

							public void windowClosing(WindowEvent e) {

								client.writeUsers();
								setVisible(true);
								collectionView.dispose();
								return;

							}
						});

						collectionView.setLocationRelativeTo(null);
						collectionView.setVisible(true);
						setVisible(false);
					}
					else {
						
						JOptionPane.showMessageDialog(null, "Entered wrong password. Try again.", 
								"Incorrect password", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		});

		window = new JPanel(new FlowLayout());
		window.add(userLabel);
		window.add(usersList);
		window.add(passwordField);
		window.add(loginButton);

		add(window);

	}


}




