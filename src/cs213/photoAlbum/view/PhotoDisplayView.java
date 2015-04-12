package cs213.photoAlbum.view;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

/**
 *  This class is responsible for the display and the functionality on the PhotoDisplayView window. 
 *  It displays a photo, once selected, in its own listing the tags associated with the specified photo. 
 *  The user can move through the rest of their pictures in their photo album in the slideshow feature.
 *
 */

public class PhotoDisplayView extends JFrame {

	private JPanel contentPane, tagPane;

	private JScrollPane photoScrollPane, tagScrollPane;

	private JTable tagTable;

	private DefaultTableModel tableModel;

	private JButton forwardButton, backButton;

	private BufferedImage forwardArrow, backArrow;

	private JLabel imageLabel, tagLabel;

	private Client client;

	private ArrayList<Tag> displayedTags;

	private int currIndex;

	private int albumSize;

	/**
	 * Class constructor which creates the frame of the PhotoDisplayView Window
	 * 
	 * @param c  Allows access to the stored data
	 * @param listModel Needed component to implement the list of photos
	 * @param photoName The name of the photo
	 */

	public PhotoDisplayView(Client c, final DefaultListModel<Photo> listModel, final String photoName) {

		super(photoName);
		setSize(800, 600);

		this.client = c;

		contentPane = new JPanel(new BorderLayout());
		tagPane = new JPanel(new FlowLayout());


		this.albumSize = listModel.size();

		for (int i = 0; i < albumSize; i++) {
			Photo p = listModel.get(i);
			if (p.getName().compareTo(photoName) == 0) {
				this.currIndex = i;
				break;
			}
		}

		displayedTags = client.getPhoto(photoName).getTags();

		String[] titles = {"Type", "Value"};
		String[][] data = new String[displayedTags.size()][2];

		for (int i = 0; i < displayedTags.size(); i++) {
			data[i][0] = displayedTags.get(i).getTypeAsString();
			data[i][1] = displayedTags.get(i).getValue();
		}

		tableModel = new DefaultTableModel(data, titles) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		tagTable = new JTable(tableModel);
		tagTable.setFocusable(false);
		tagTable.setRowSelectionAllowed(false);
		tagScrollPane = new JScrollPane(tagTable);


		try {
			backArrow = ImageIO.read(new File("assets/back_arrow.png"));
			forwardArrow = ImageIO.read(new File("assets/forward_arrow.png"));
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}

		forwardButton = new JButton();
		backButton = new JButton();

		forwardButton.setIcon(new ImageIcon(forwardArrow));;
		backButton.setIcon(new ImageIcon(backArrow));

		forwardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				currIndex++;

				if (currIndex == albumSize) currIndex = 0;

				Photo p = listModel.get(currIndex);

				try {
					imageLabel.setIcon(new ImageIcon(ImageIO.read(client.getPhoto(p.getName()).getFile())));
				} catch (IOException e1) {
					System.err.println("IOException " + e1.getMessage());
				}

				/*	imageLabel.setIcon(img);
				scrollPane = new JScrollPane(imageLabel);
				contentPane.add(scrollPane, BorderLayout.CENTER);*/

				repaint();
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				currIndex--;

				if (currIndex == -1) currIndex = albumSize - 1;



				Photo p = listModel.get(currIndex);
				try {
					imageLabel.setIcon(new ImageIcon(ImageIO.read(client.getPhoto(p.getName()).getFile())));
				} catch (IOException e1) {
					System.err.println("IOException " + e1.getMessage());
				}

				repaint();

			}
		});

		ImageIcon img = null;

		try {
			img = new ImageIcon(ImageIO.read(client.getPhoto(photoName).getFile()));
		} catch (IOException e) {
			System.err.println("IOException " + e.getMessage());
		}

		imageLabel = new JLabel("", img, JLabel.CENTER);
		tagLabel = new JLabel("Tags:");
		//photoScrollPane = new JScrollPane(imageLabel);
		contentPane.add(imageLabel, BorderLayout.CENTER);
		contentPane.add(backButton, BorderLayout.WEST);
		contentPane.add(forwardButton, BorderLayout.EAST);

		tagPane = new JPanel(new FlowLayout());

		tagPane.add(tagLabel);
		tagPane.add(tagTable);
		contentPane.add(tagPane, BorderLayout.PAGE_END);
		setContentPane(contentPane);


	}




}
