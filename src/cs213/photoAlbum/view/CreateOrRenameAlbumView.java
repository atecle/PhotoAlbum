package cs213.photoAlbum.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Album;

@SuppressWarnings("serial")
public class CreateOrRenameAlbumView extends JFrame {


	private JPanel contentPane;
	private JLabel albumNameLabel;
	private JTextField albumNameField;
	private JButton saveButton;
	private Client client;
	private DefaultTableModel listModel;
	private boolean add;
	private String albumName;

	public CreateOrRenameAlbumView(Client c, DefaultTableModel mod, String title, final boolean add, final String albumName, final int row) {

		super(title);
		this.client = c;
		this.listModel = mod;
		setSize(new Dimension(300, 120));

		contentPane = new JPanel(new FlowLayout());
		albumNameLabel = new JLabel("Album Name: ");
		albumNameField = new JTextField(20);
		if (!add) albumNameField.setText(albumName);

		saveButton = new JButton("Save");

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String newAlbumName = albumNameField.getText().trim();

				if (newAlbumName.length() == 0) {
					JOptionPane.showMessageDialog(getParent(), "Album name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (add) {

					if (!client.createAlbum(newAlbumName)) {
						JOptionPane.showMessageDialog(getParent(), "Album name already exists in user's collection", 
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					listModel.addRow(new Object[]{newAlbumName, "No start date - No end date", "0"});
					client.writeUsers();
					dispose();
					return;
				} 
				else {
					
					if (!client.renameAlbum(albumName, newAlbumName)) {
						
						JOptionPane.showMessageDialog(getParent(), "Name conflicts with existing album", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					client.writeUsers();
					listModel.setValueAt(newAlbumName, row, 0);
					dispose();
					return;
				}
			}
		});


		contentPane.add(albumNameLabel);
		contentPane.add(albumNameField);
		contentPane.add(saveButton, FlowLayout.RIGHT);
		setContentPane(contentPane);

	}

}
