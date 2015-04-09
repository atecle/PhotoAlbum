package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.util.Helper;

/**
 * This class is responsible for the display and the functionality on the CollectionView window.
 * This is the first window the user sees once they have successfully logged in. 
 * A list of the user's albums, date range and number of photos associated with each album are listed.
 * The user can choose to open an album, rename an album, delete an existing album, create a new album and search.
 *
 */
public class CollectionView extends JFrame {
	
	/**Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;

	/**The frame of CollectionView*/
	private JPanel contentPane, buttonPanel;

	/**Organizes album, date range and number of photos*/
	private JTable table;

	/**ScrollPane declaration*/
	private JScrollPane scrollPane;

	/**Works with JTable to organize layout of data*/
	private DefaultTableModel listModel;

	/**User object declaration*/
	private User user;

	/**Holds list of albums for the current logged in user*/
	private ArrayList<Album> albums;

	/**Client object declaration*/
	private Client client;

	/**Search, Open, Rename, Delete and Create buttons*/
	private JButton searchButton, openButton, renameButton, deleteButton, createButton;

/**
 * Class constructor which creates the frame of the CollectionView Window
 * 
 * @param u Allows access to the logged in user's information
 * @param c Allows access to the stored data in Client
 */
	@SuppressWarnings("serial")
	public CollectionView(User u, Client c) {

		super(u.getName() + "'s Collection");
		setSize(600,400);

		this.user = u;
		this.client = c;
		contentPane = new JPanel(new BorderLayout());

		this.albums = user.getAlbums();

		String[] colNames = {"Album", "Date Range", "Number of Photos"};

		String[][] data = new String[albums.size()][3];

		for (int i = 0; i < albums.size(); i++) {

			Album album = albums.get(i);
			data[i][0] = album.getName();
			String startDate = Helper.formatDate(album.getStartDate());
			startDate = startDate.compareTo("No date") == 0 ? "No start date" : startDate.substring(0, startDate.indexOf("-"));
			String endDate = Helper.formatDate(album.getEndDate());
			endDate = endDate.compareTo("No date") == 0 ? "No end date" : endDate.substring(0, endDate.indexOf("-"));
			data[i][1] = startDate + " - " + endDate;
		
			data[i][2] = album.getSize() + "";
		}

		 listModel = new DefaultTableModel(data, colNames)
		{
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(listModel);
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		contentPane.add(scrollPane, BorderLayout.CENTER);

		searchButton = new JButton("Search Photo");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


			}
		});

		openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				final int row = table.getSelectedRow();
				if (row == -1) return;
				
				final String albumName = (String) table.getValueAt(row, 0);
				AlbumView albumView = new AlbumView(client, albumName);
				albumView.setLocationRelativeTo(null);
				albumView.setVisible(true);
				setVisible(false);
				albumView.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent e) {
						
						client.writeUsers();
						int newSize = client.getUser().getAlbum(albumName).getSize();
						table.setValueAt(newSize, row, 2);
						String newStart = Helper.formatDate(client.getUser().getAlbum(albumName).getStartDate());
						newStart = newStart.compareTo("No date") == 0 ? "No start date" : newStart.substring(0, newStart.indexOf("-"));
						String newEnd = Helper.formatDate(client.getUser().getAlbum(albumName).getEndDate());
						newEnd = newEnd.compareTo("No date") == 0 ? "No end date" : newEnd.substring(0, newEnd.indexOf("-"));
						table.setValueAt(newStart + " - " + newEnd, row, 1);
						setVisible(true);
					}
				});
				
			}
		});

		renameButton = new JButton("Rename");
		renameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int row = table.getSelectedRow();
				if (row == -1) return;
				
				String oldName = (String) table.getValueAt(row, 0);
				CreateOrRenameAlbumView crav = 
						new CreateOrRenameAlbumView(client, listModel, "Rename Album", false, oldName, row);
				crav.setLocationRelativeTo(null);
				crav.setVisible(true);
			}
		});

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) return;
				String name = user.getAlbums().get(index).getName();
				client.deleteAlbum(name);
				((DefaultTableModel) table.getModel()).removeRow(index);
			}
		});
	

		createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				CreateOrRenameAlbumView crav = new CreateOrRenameAlbumView(client, listModel, "Create Album", true, "", -1);
				crav.setLocationRelativeTo(null);
				crav.setVisible(true);

			}
		});

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		buttonPanel.add(createButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(openButton);
		buttonPanel.add(renameButton);
		buttonPanel.add(searchButton);

		contentPane.add(buttonPanel, BorderLayout.PAGE_END);
		setContentPane(contentPane);

	}

}
