package cs213.photoAlbum.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Client;

/**
 * This class is responsible for the display and the functionality on the RecaptionView window. 
 * The user can recaption a specific photo, specifying it with a new tag value and corresponding tag type.
 *
 */
public class RecaptionView extends JFrame {

	private JPanel contentPane;
	
	private JButton saveButton;
	
	private JLabel captionLabel;
	
	private JTextField captionField;
	
	private Client client;
	
	/**
	 * Class constructor which creates the frame of the RecaptionView window
	 * 
	 * @param c  Allows access to the stored data
	 * @param photoName The name of the photo
	 */
	
	public RecaptionView(Client c, final String photoName) {
		
		super("Recaption Photo");
		this.client = c;
		setSize(300, 230);
		setResizable(false);
		
		contentPane = new JPanel(new FlowLayout());
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String caption = captionField.getText().trim();
				
				if (caption.length() != 0) {
					client.getPhoto(photoName).setCaption(caption);
				}
				else {
					client.getPhoto(photoName).setCaption(" ---- ");
				}
				
				dispose();
				
				
			}
		});
		
		
		captionLabel = new JLabel("Enter a caption:");
		captionField = new JTextField(20);
		
		contentPane.add(captionLabel);
		contentPane.add(captionField);
		contentPane.add(saveButton);
		setContentPane(contentPane);
	}
}
