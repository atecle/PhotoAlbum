package cs213.photoAlbum.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.User;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel userLabel;
	private JPasswordField passwordTextField;
	private BufferedImage loginIcon;
	private JComboBox<String> usersList;
	private JButton loginButton;
	private JPanel window;
	private Client client;
	private static JFrame nysm;


	public LoginView(Client c) {

		super("Login");

		setSize(300, 250);
		setResizable(true);
		this.client = c;
		final ArrayList<User> users = client.getUsers();
		usersList = new JComboBox<String>();

		User admin = new User("-1", "Admin");
		for (User user : users) {
			usersList.addItem(user.getName());
		}
		usersList.addItem(admin.getName());



		userLabel = new JLabel("User: ");

		passwordTextField = new JPasswordField(20);
		passwordTextField.setEditable(true);

		usersList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) usersList.getSelectedItem();
				if (name.compareTo("Admin") == 0) {
					passwordTextField.setText("");
					passwordTextField.setEditable(false);
				}
				else {
					passwordTextField.setEditable(true);
				}

			}
		});

		try {

			loginIcon = ImageIO.read(new File("assets/login.png"));
		} catch (IOException e) {

			System.err.println("Error loading login icon. " + e.getMessage());
		}

		loginButton = new JButton(new ImageIcon(loginIcon));
		loginButton.setBorderPainted(false);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String pw = passwordTextField.getPassword().toString();
				String name = (String) usersList.getSelectedItem();
				if (name.compareTo("Admin") == 0) {
					//This is where you would spawn the admin view window and close 
					AdminView.nowYouSeeMe(); 
				}
				else {

					User user = users.get(usersList.getSelectedIndex());
					
					if (user.loginSuccess(pw)) {
						
						final CollectionView collectionView = new CollectionView(user, client);
						client.login(user.getID());
						
						collectionView.addWindowListener(new WindowAdapter() {
							
							public void windowClosing(WindowEvent e) {
								
								client.writeUsers();
								setVisible(true);
								collectionView.dispose();

							}
						});

						collectionView.setLocationRelativeTo(null);
						collectionView.setVisible(true);
						setVisible(false);
					}
				}
			}
		});

		window = new JPanel(new FlowLayout());
		window.add(userLabel);
		window.add(usersList);
		window.add(passwordTextField);
		window.add(loginButton);
		
		add(window);

	}

	//This is called in LoginView so AdminView can be seen!
	public static void nowYouSeeMe() 
	{
		// TODO Auto-generated method stub
		Client c = new Client(new Backend());
		nysm = new LoginView(c);
		nysm.setVisible(true);

		//Want to have the window centered on screen
		nysm.setLocationRelativeTo(null);
		nysm.setResizable(true);  
		nysm.setMinimumSize(nysm.getMinimumSize());
		nysm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}



}




