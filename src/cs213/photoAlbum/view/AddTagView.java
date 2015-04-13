/**
 * @author Adam Tecle
 * 
 */

package cs213.photoAlbum.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

/**
 * This class is responsible for the display and the functionality on the AddTagView window.
 * The user can add a new tag to a selected photo, specifying the tag value and the tag type (either location,
 * keyword or person)
 *
 */

@SuppressWarnings("serial")
public class AddTagView extends JFrame {

	/** To replace default content pane **/
	private JPanel contentPane;

	/**JButton for creating saving an added tag*/
	private JButton saveButton;

	/**RadioButtons for location, keyword and person option for tag type*/
	private JRadioButton locButton, keywordButton, personButton;

	/**ButtonGroup to store the three tag types*/
	private ButtonGroup group;
	
	/**JLabels for tag value, location, keyword and person options*/
	private JLabel tagValueLabel, locLabel, keywordLabel, personLabel;

	/**TextField for tag value input*/
	private JTextField tagValueField;

	/**Client object declaration*/
	private Client client;
	
	
/**
 * Class constructor which creates the frame of the AddTagView Window
 * 
 * @param c Allows access to the stored data 
 * @param photoName The name of the selected photo
 */
	public AddTagView(Client c, final String photoName) {

		super("Add Tag");
		this.client = c;
		
		setSize(300, 150);
		setResizable(false);

		group = new ButtonGroup();

		locButton = new JRadioButton();
		keywordButton = new JRadioButton();
		personButton = new JRadioButton();

		group.add(locButton);
		group.add(keywordButton);
		group.add(personButton);
		
		tagValueLabel = new JLabel("Enter tag value: ");
		locLabel = new JLabel("Location:");
		keywordLabel = new JLabel("Keyword:");
		personLabel = new JLabel("Person:");
		
		tagValueField = new JTextField(22);

		saveButton = new JButton("Save");

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!locButton.isSelected() && !keywordButton.isSelected() && !personButton.isSelected()) {
					JOptionPane.showMessageDialog(null, "Must select tag type.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String tagValue = tagValueField.getText().trim();

				if (tagValue.length() == 0) {
					JOptionPane.showMessageDialog(null, "Tag value cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Tag t = null;
				Photo p = client.getPhoto(photoName);

				if (locButton.isSelected()) {

					t = new Tag(Tag.LOCATION, tagValue);

					if (p.hasTag(t)) {
						
						JOptionPane.showMessageDialog(null, "Photo already has this tag.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					p.addTag(t);
				}

				else if (keywordButton.isSelected()) {

					t = new Tag(Tag.KEYWORD, tagValue);
					
					if (p.hasTag(t)) {
						
						JOptionPane.showMessageDialog(null, "Photo already has this tag.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					p.addTag(t);
				}

				else if (personButton.isSelected()) {

					t = new Tag(Tag.PERSON, tagValue);
					
					if (p.hasTag(t)) {

						JOptionPane.showMessageDialog(null, "Photo already has this tag.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					p.addTag(t);
				}

				//client.writeUsers();
				dispose();
			}
		});
		
		contentPane = new JPanel(new FlowLayout());
		
		contentPane.add(tagValueLabel);
		contentPane.add(tagValueField);
		
		contentPane.add(locLabel);
		contentPane.add(locButton);
		
		contentPane.add(keywordLabel);
		contentPane.add(keywordButton);
		
		contentPane.add(personLabel);
		contentPane.add(personButton);
		
		contentPane.add(saveButton);

		setContentPane(contentPane);
	}
}
