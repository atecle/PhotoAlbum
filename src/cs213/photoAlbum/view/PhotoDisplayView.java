package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Photo;

public class PhotoDisplayView extends JFrame {

	private JPanel contentPane, tagPane;

	private JScrollPane scrollPane;

	private JButton forwardButton, backButton;

	private BufferedImage forwardArrow, backArrow;

	private JLabel imageLabel, tagLabel;

	private Client client;

	private int currIndex;
	
	private int albumSize;


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
				
				ImageIcon img = null;
				
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
				
				ImageIcon img = null;
				
				Photo p = listModel.get(currIndex);
				try {
					img = new ImageIcon(ImageIO.read(client.getPhoto(p.getName()).getFile()));
				} catch (IOException e1) {
					System.err.println("IOException " + e1.getMessage());
				}
				
				contentPane.remove(scrollPane);
				
				imageLabel = new JLabel("", img, JLabel.CENTER);
				scrollPane = new JScrollPane(imageLabel);
				contentPane.add(scrollPane, BorderLayout.CENTER);
				setContentPane(contentPane);
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
		scrollPane = new JScrollPane(imageLabel);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(backButton, BorderLayout.WEST);
		contentPane.add(forwardButton, BorderLayout.EAST);

		tagPane = new JPanel(new FlowLayout());

		tagPane.add(tagLabel);
		contentPane.add(tagPane, BorderLayout.PAGE_END);
		setContentPane(contentPane);


	}


}
