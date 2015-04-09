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

public class RecaptionView extends JFrame {

	private JPanel contentPane;
	
	private JButton saveButton;
	
	private JLabel captionLabel;
	
	private JTextField captionField;
	
	private Client client;
	
	public RecaptionView(Client c, final String photoName) {
		
		super("Recaption Photo");
		this.client = c;
		setSize(300, 230);
		
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
