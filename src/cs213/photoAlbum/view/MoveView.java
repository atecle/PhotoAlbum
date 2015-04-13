package cs213.photoAlbum.view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Album;

public class MoveView extends JFrame {

	private JPanel contentPane, buttonPanel;
	
	private JButton moveButton;

	private DefaultListModel<Album> listModel;
	
	private JList<Album> albumList;
	
	private Client client;
	
	public MoveView(Client c) {
		
		super("Select an album to move photo");
		
		this.client = c;
	}
}
