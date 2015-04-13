/**
 * @author Adam Tecle
 * 
 */

package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
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
import cs213.photoAlbum.util.Helper;

/**
 *  This class is responsible for the display and the functionality on the PhotoDisplayView window. 
 *  It displays a photo, once selected, in its own listing the tags associated with the specified photo. 
 *  The user can move through the rest of their pictures in their photo album in the slideshow feature.
 *
 */

@SuppressWarnings("serial")
public class PhotoDisplayView extends JFrame {

	/** To replace default content pane **/
	private JPanel contentPane, tagPane;

	/**Provides a scrollable view for the list of tags*/
	private JScrollPane tagScrollPane;

	/** Table to organize the display of the tags value and corresponding type*/
	private JTable tagTable;

	/**Works with JTable to hold the names of the tags values and tag types*/
	private DefaultTableModel tableModel;

	/**JButtons for moving the slideshow forward and backwards*/
	private JButton forwardButton, backButton;

	/**Images for display of forward and back buttons*/
	private BufferedImage forwardArrow, backArrow;

	/**JLabel for the specific image and tag*/
	private JLabel imageLabel, tagLabel;

	/**Client object declaration*/
	private Client client;

	/**Holds the list of tags associated with a specific image*/
	private ArrayList<Tag> displayedTags;

	/**The variable that stores the current place within the album*/
	private int currIndex;

	/**The size of the album*/
	private int albumSize;

	/**Setting the dimension of the pictures displayed*/
	private static final Dimension boundary = new Dimension(700, 700);

	/**HashMap to store the images in a user's album*/
	private HashMap<String, Image> images;

	/**
	 * Class constructor which creates the frame of the PhotoDisplayView Window
	 * 
	 * @param c  Allows access to the stored data
	 * @param listModel Needed component to implement the list of photos
	 * @param photoName The name of the photo
	 */

	public PhotoDisplayView(Client c, final DefaultListModel<Photo> listModel, final String photoName, String albumName) {

		super(c.getPhoto(photoName).getCaption() + " : " + Helper.formatDate(c.getPhoto(photoName).getDate().getTime()));
		setSize(800, 650);

		this.client = c;
		images = new HashMap<String, Image>();

		contentPane = new JPanel(new BorderLayout());
		tagPane = new JPanel(new FlowLayout());


		this.albumSize = listModel.size();

		//finding the index of the current photo in the listModel
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

				displayedTags = p.getTags();

				DefaultTableModel dm = (DefaultTableModel) tagTable.getModel();
				int rowCount = dm.getRowCount();

				for (int i = rowCount - 1; i >= 0; i--) {
					dm.setValueAt("", i, 0);
					dm.setValueAt("", i, 1);
				}

				dm.setRowCount(displayedTags.size());

				for (int i = 0; i < displayedTags.size(); i++) {
					dm.setValueAt(displayedTags.get(i).getTypeAsString(), i, 0);
					dm.setValueAt(displayedTags.get(i).getValue(), i, 1);
				}

				tagTable.setModel(dm);
				Image img = images.get(p.getName());

				BufferedImage bimg = (BufferedImage)img;

				Dimension newDimension = getScaledDimension(new Dimension(bimg.getWidth(), bimg.getHeight()));

				img = img.getScaledInstance((int)newDimension.getWidth(), (int)newDimension.getHeight(), 
						Image.SCALE_SMOOTH);

				imageLabel.setIcon(new ImageIcon(img));

				setTitle(p.getCaption() + " : " + Helper.formatDate(p.getDate().getTime()));

				repaint();
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				currIndex--;

				if (currIndex == -1) currIndex = albumSize - 1;

				Photo p = listModel.get(currIndex);

				displayedTags = p.getTags();

				DefaultTableModel dm = (DefaultTableModel) tagTable.getModel();

				int rowCount = dm.getRowCount();

				for (int i = rowCount - 1; i >= 0; i--) {
					dm.setValueAt("", i, 0);
					dm.setValueAt("", i, 1);
				}


				dm.setRowCount(displayedTags.size());

				for (int i = 0; i < displayedTags.size(); i++) {
					dm.setValueAt(displayedTags.get(i).getTypeAsString(), i, 0);
					dm.setValueAt(displayedTags.get(i).getValue(), i, 1);
				}

				tagTable.setModel(dm);

				Image img = images.get(p.getName());

				BufferedImage bimg = (BufferedImage)img;

				Dimension newDimension = getScaledDimension(new Dimension(bimg.getWidth(), bimg.getHeight()));
				img = img.getScaledInstance((int)newDimension.getWidth(), (int)newDimension.getHeight(), 
						Image.SCALE_SMOOTH);

				imageLabel.setIcon(new ImageIcon(img));

				setTitle(p.getCaption() + " : " + Helper.formatDate(p.getDate().getTime()));
				repaint();

			}
		});

		//loading img objects into a hashtable to limit time spent doing disk IO
		for (Photo photo : client.getUser().getAlbum(albumName).getPhotos()) {
			Image img = null;

			try {
				img = ImageIO.read(photo.getFile());
			} catch (IOException ex) {
				System.err.println("IOException while loading image hashmap. " + ex.getMessage());
			}
			images.put(photo.getName(), img);
		}

		Image img = images.get(photoName);

		BufferedImage bimg = (BufferedImage)img;

		Dimension newDimension = getScaledDimension(new Dimension(bimg.getWidth(), bimg.getHeight()));
		img = img.getScaledInstance((int)newDimension.getWidth(), (int)newDimension.getHeight(), 
				Image.SCALE_SMOOTH);

		imageLabel = new JLabel("", new ImageIcon(img), JLabel.CENTER);
		tagLabel = new JLabel("Tags:");
		contentPane.add(imageLabel, BorderLayout.CENTER);
		contentPane.add(backButton, BorderLayout.WEST);
		contentPane.add(forwardButton, BorderLayout.EAST);

		tagPane = new JPanel(new BorderLayout());
		tagScrollPane.setPreferredSize(new Dimension(100, 100));

		tagPane.add(tagLabel, BorderLayout.PAGE_START);
		tagPane.add(tagScrollPane, BorderLayout.CENTER);
		contentPane.add(tagPane, BorderLayout.PAGE_END);
		setContentPane(contentPane);


	}

	/**
	 * Returns new dimension from imgSize parameter that is within display area boundary
	 * @param imgSize The width and height of the image
	 * @return
	 */
	private static Dimension getScaledDimension(Dimension imgSize) {

		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		// first check if we need to scale width
		if (original_width > bound_width) {
			//scale width to fit
			new_width = bound_width;
			//scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			//scale height to fit instead
			new_height = bound_height;
			//scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}

		return new Dimension(new_width, new_height);
	}


}
