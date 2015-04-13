package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

public class MoveView extends JFrame {

	private JPanel contentPane, buttonPanel;
	
	private JScrollPane scrollPane;
	
	private JButton moveButton;
	
	private JList<Album> albumList;
	
	private DefaultListModel<Album> listModel;
	
	private Client client;
	
	public MoveView(Client c, final DefaultListModel<Photo> photoList, final String albumName, final Photo photo) {
		
		super("Select an album to move photo");
		this.client = c;
		setSize(300, 150);
		
		contentPane = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new FlowLayout());
		listModel = new DefaultListModel<Album>();
		
		ArrayList<Album> albums = client.getUser().getAlbums();
		
		albums.remove(client.getUser().getAlbum(albumName));
		
		for (Album album : albums) {
			listModel.addElement(album);
		}

		albumList = new JList<Album>(listModel);
		scrollPane = new JScrollPane(albumList);
		
		moveButton = new JButton("Move");
		
		
		moveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Album album = albumList.getSelectedValue();
				if (album == null) return;
				
				if (!client.addPhoto(photo.getName(), photo.getCaption(), album.getName())) {
					
					JOptionPane.showMessageDialog(null, "Photo already exists in album", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				client.getUser().getAlbum(albumName).deletePhoto(photo.getName());
				photoList.removeElement(photo);
				photo.removeFromAlbum(albumName);
				dispose();
				return;
			}
		});
		
		buttonPanel.add(moveButton);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		setContentPane(contentPane);
		
	}
}
