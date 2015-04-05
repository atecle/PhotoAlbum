package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.util.Helper;

public class CollectionView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTable table;

	private JScrollPane scrollPane;

	private DefaultTableModel listModel;

	private User user;

	private ArrayList<Album> albums;

	private Client client;

	private JButton searchButton, openButton, renameButton, deleteButton, createButton;


	@SuppressWarnings("serial")
	public CollectionView(User u, Client c) {

		super(u.getName() + "'s Collection");
		setSize(800,600);

		this.user = u;
		this.client = c;
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

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

		/*
		 * Adds the logout button in the top right. Think it might be better to just logout when the window is closed.
		 * JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		logoutButton = new JButton("Logout");
		logoutButton.setHorizontalAlignment(JButton.RIGHT);
		topPanel.add(logoutButton);
		contentPane.add(topPanel, BorderLayout.NORTH);
		 */
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

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		buttonPanel.add(createButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(openButton);
		buttonPanel.add(renameButton);
		buttonPanel.add(searchButton);

		contentPane.add(buttonPanel, BorderLayout.PAGE_END);
		add(contentPane);

	}

}
