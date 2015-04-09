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

public class AlbumView extends JFrame {

	private JPanel contentPane, buttonPanel;

	private JButton addButton, removeButton, 
	recaptionButton, openButton, 
	addTagButton, removeTagButton;

	private JScrollPane scrollPane;

	private JList<Photo> photoList;

	private DefaultListModel<Photo> listModel;

	private PhotoDisplayView photoView;

	private Client client;

	public AlbumView(Client c, final String albumName) {

		super(c.getUser() +  "'s " + albumName);
		setSize(700, 400);
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

				listModel.removeElement(p);

			}
		});

		recaptionButton = new JButton("Recaption");
		recaptionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Photo p = photoList.getSelectedValue();
				if (p == null) return;

				RecaptionView recapView = new RecaptionView(client, p.getName());

				recapView.setLocationRelativeTo(null);
				recapView.setVisible(true);
				recapView.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent e) {

						client.writeUsers();
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

				AddTagView addTagView = new AddTagView(client, p.getName());
				addTagView.setLocationRelativeTo(null);
				addTagView.setVisible(true);
				
				addTagView.addWindowListener(new WindowAdapter() {
					
					public void windowClosing(WindowEvent e) {
						
						client.writeUsers();
						
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
				
				RemoveTagView rmTagView = new RemoveTagView(client, p.getName());
				
				rmTagView.setLocationRelativeTo(null);
				rmTagView.setVisible(true);
				
				rmTagView.addWindowListener(new WindowAdapter() {
					
					public void windowClosing(WindowEvent e) {
						
						client.writeUsers();
					}
				});
				
			}
		});

		openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				final PhotoDisplayView photoView = new PhotoDisplayView(client);
			}
		});

		contentPane = new JPanel(new BorderLayout());
		contentPane.add(scrollPane, BorderLayout.CENTER);

		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(openButton);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(addTagButton);
		buttonPanel.add(removeTagButton);
		buttonPanel.add(recaptionButton);


		contentPane.add(buttonPanel, BorderLayout.PAGE_END);

		setContentPane(contentPane);

	}

	private static boolean isImage(File file) {

		String fileName = file.getName();
		String ext = null;

		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			ext = fileName.substring(fileName.lastIndexOf(".")+1);
		else ext = "";

		String [] exts = ImageIO.getReaderFileSuffixes();
		for (String s : exts) {
			if (s.compareTo(ext) == 0) {
				return true;
			}
		}

		return false;

	}

}
