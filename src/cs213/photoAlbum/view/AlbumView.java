/**
 * @author Adam Tecle
 * 
 */

package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Photo;

/**
 * 
 * This class is responsible for the display and the functionality on the AlbumView window.
 * A list of a user's photo by thumbnail image are displayed. The functions for adding a photo,
 * removing a photo, recaptioning a photo, opening a photo, adding a tag and removing a tag are
 * available.
 *
 */
@SuppressWarnings("serial")
public class AlbumView extends JFrame {

	private JPanel contentPane, buttonPanel;

	private JButton addButton, removeButton,
	moveButton, recaptionButton, openButton, 
	addTagButton, removeTagButton;

	private JScrollPane scrollPane;

	private JList<Photo> photoList;

	private DefaultListModel<Photo> listModel;

	private PhotoDisplayView photoView;

	private Client client;

	/**
	 * Class constructor which creates the frame of the AdminView Window
	 * 
	 * @param c  Allows access to the stored data
	 * @param albumName The name of the album
	 */

	@SuppressWarnings("unchecked")
	public AlbumView(Client c, final String albumName) {

		super(c.getUser() +  "'s " + albumName);
		setSize(800, 400);
		setResizable(true);
		this.client = c;

		photoList = new JList<Photo>();
		listModel = new DefaultListModel<Photo>();
		IconListCellRenderer renderer = new IconListCellRenderer();

		for (Photo photo : client.getUser().getAlbum(albumName).getPhotos()) {
			listModel.addElement(photo);
		}

		photoList = new JList<Photo>(listModel);
		photoList.setCellRenderer(renderer);
		//photoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(photoList);

		addButton = new JButton("Add Photo");

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//strange behavior below. files are only filtered after I select Image Files in the combo box. By default, all are choosable. 
				//so can't guarantee through filechooser that user selects an image, hence isImage() call


				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
				fc.setFileFilter(imageFilter);
				fc.addChoosableFileFilter(imageFilter);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();

					if (!isImage(file))  {
						JOptionPane.showMessageDialog(null, "Invalid file type.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					String filePath = null;
					boolean addSuccess = false;

					try {

						addSuccess = client.addPhoto((filePath = file.getCanonicalPath()), " ---- ", albumName);
					} catch (IOException e2) {
						return;
					}

					if (!addSuccess) {
						JOptionPane.showMessageDialog(null, "Album already contains this photo", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					Photo newPhoto = client.getPhoto(filePath);
					listModel.addElement(newPhoto);
				}

				return;
			}
		});

		removeButton = new JButton("Remove Photo");
		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Photo p = photoList.getSelectedValue();

				if (p == null) return;
				
				int selectedOption = JOptionPane.showConfirmDialog(null, 
						"Are you sure?", 
						"Choose", 
						JOptionPane.YES_NO_OPTION); 
				if (selectedOption == JOptionPane.NO_OPTION) {
					return;
				}

				listModel.removeElement(p);
				p.removeFromAlbum(albumName);
				client.getUser().deletePhoto(p.getName());
				client.getUser().getAlbum(albumName).deletePhoto(p.getName());
				//client.writeUsers();

			}
		});
		
		moveButton = new JButton("Move");
		
		moveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Photo p = photoList.getSelectedValue();
				
				if (p == null) return;
				
				final MoveView moveView = new MoveView(client, listModel, albumName, p);
				
				moveView.setLocationRelativeTo(null);
				moveView.setVisible(true);
				
				moveView.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent e) {
						
						moveView.dispose();
					}
				});
				
				
			}
		});

		recaptionButton = new JButton("Recaption");
		recaptionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Photo p = photoList.getSelectedValue();
				if (p == null) return;

				final RecaptionView recapView = new RecaptionView(client, p.getName());

				recapView.setLocationRelativeTo(null);
				recapView.setVisible(true);
				recapView.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent e) {
						
						recapView.dispose();
					}
				});
				
			}
		});


		addTagButton = new JButton("Add Tag");
		addTagButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				final Photo p = photoList.getSelectedValue();

				if (p == null) return;

				final AddTagView addTagView = new AddTagView(client, p.getName());
				addTagView.setLocationRelativeTo(null);
				addTagView.setVisible(true);

				addTagView.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent e) {
						
						addTagView.dispose();
					}
				});
			}
		});


		removeTagButton = new JButton("Remove Tag");		
		removeTagButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Photo p = photoList.getSelectedValue();

				if (p == null) return;
				
			

				final RemoveTagView rmTagView = new RemoveTagView(client, p.getName());

								
				rmTagView.setLocationRelativeTo(null);
				rmTagView.setVisible(true);
				
			

				rmTagView.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent e) {
						
						rmTagView.dispose();
					}
				});
				

			}
		});

		openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Photo p = photoList.getSelectedValue();
				if (p == null) return;

				photoView = new PhotoDisplayView(client, listModel, p.getName(), albumName);

				photoView.setLocationRelativeTo(null);
				photoView.setVisible(true);
				setVisible(false);
				photoView.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent e) {

						setVisible(true);
					}
				});


			}
		});

		contentPane = new JPanel(new BorderLayout());
		contentPane.add(scrollPane, BorderLayout.CENTER);

		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(openButton);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(moveButton);
		buttonPanel.add(addTagButton);
		buttonPanel.add(removeTagButton);
		buttonPanel.add(recaptionButton);


		contentPane.add(buttonPanel, BorderLayout.PAGE_END);

		setContentPane(contentPane);

	}

	/**
	 * Checks if a file is an image file
	 * 
	 * @param file The file being checked if it's an image file
	 * @return true if an image file, false otherwise
	 */

	private static boolean isImage(File file) {

		String fileName = file.getName();
		String ext = null;

		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			ext = fileName.substring(fileName.lastIndexOf(".")+1);
		else ext = "";

		String [] exts = ImageIO.getReaderFileSuffixes();
		for (String s : exts) {
			if (s.compareToIgnoreCase(ext) == 0) {
				return true;
			}
		}

		return false;

	}

}
