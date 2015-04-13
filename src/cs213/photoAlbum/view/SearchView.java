/**
 * @author Adam Tecle
 * 
 */
package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

@SuppressWarnings("serial")
public class SearchView extends JFrame {

	private JPanel contentPane, listPanel, 
	buttonPanel, datePanel, tagPanel, createSearchPanel, chooseSearchTypePanel, cardPanel;

	private Client client;

	private JTextField startYear, startMonth, startDay, endYear, endMonth, endDay, tagValue;

	private JRadioButton locationRButton, keywordRButton, personRButton, dateRButton, tagRButton;

	private JLabel dateLabel;//, keywordLabel, locationLabel, personLabel;

	private JButton searchButton, createButton, addTagButton, deleteTagButton, cancelButton;

	private Date start, end;

	private JComboBox<Tag> tagBox;

	private ArrayList<Tag> tagList;

	private JScrollPane scrollPane;

	private JList<Photo> photoList;

	private DefaultListModel<Photo> listModel;

	private CardLayout cardLayout;

	//tablemodel to make call to createorrenamealbumview 
	public SearchView(Client c, DefaultTableModel tableModel) {

		super("Search " + c.getUser().getName() + "'s Photos");
		setSize(660, 460);

		this.client = c;

		start = null;
		end = null; 

		tagList = new ArrayList<Tag>();
		Tag t = new Tag(-1, "   ");

		tagBox = new JComboBox<Tag>();

		listModel = new DefaultListModel<Photo>();

		cardLayout = new CardLayout();

		startYear = new JTextField(4);
		startMonth = new JTextField(2);
		startDay = new JTextField(2);
		endYear = new JTextField(4);
		endMonth = new JTextField(2);
		endDay = new JTextField(2);
		tagValue = new JTextField(10);

		dateLabel = new JLabel("Enter start and end date (YYYY/MM/DD)");

		contentPane = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(1, 2));
		chooseSearchTypePanel = new JPanel(new FlowLayout());

		listPanel = new JPanel(new BorderLayout());
		datePanel = new JPanel(new GridLayout(3, 1));
		tagPanel = new JPanel(new GridLayout(4, 1));
		createSearchPanel = new JPanel(new FlowLayout());
		cardPanel = new JPanel(cardLayout);

		searchButton = new JButton("Search");
		createButton = new JButton("Create Album");
		cancelButton = new JButton("Cancel");

		locationRButton = new JRadioButton("Location");
		keywordRButton = new JRadioButton("Keyword");
		personRButton = new JRadioButton("Person");
		dateRButton = new JRadioButton("Search by Date");
		tagRButton = new JRadioButton("Search by Tag");

		addTagButton = new JButton("Add Tag");
		deleteTagButton = new JButton("Delete Tag");

		final ButtonGroup tagButtonGroup = new ButtonGroup();
		tagButtonGroup.add(locationRButton);
		tagButtonGroup.add(keywordRButton);
		tagButtonGroup.add(personRButton);

		final ButtonGroup searchTypeButtonGroup = new ButtonGroup();
		searchTypeButtonGroup.add(dateRButton);
		searchTypeButtonGroup.add(tagRButton);


		createSearchPanel.add(searchButton);
		createSearchPanel.add(createButton);
		createSearchPanel.add(cancelButton);

		JPanel tagPanelRow1 = new JPanel(new FlowLayout());
		JPanel tagPanelRow2 = new JPanel(new FlowLayout());
		JPanel tagPanelRow3 = new JPanel(new FlowLayout());
		JPanel tagPanelRow4 = new JPanel(new FlowLayout());
		tagPanelRow1.add(tagBox);
		tagPanelRow2.add(locationRButton);
		tagPanelRow2.add(keywordRButton);
		tagPanelRow2.add(personRButton);
		tagPanelRow3.add(tagValue);
		tagPanelRow4.add(addTagButton);
		tagPanelRow4.add(deleteTagButton);
		tagPanel.add(tagPanelRow1);
		tagPanel.add(tagPanelRow2);
		tagPanel.add(tagPanelRow3);
		tagPanel.add(tagPanelRow4);

		JPanel datePanelRow1 = new JPanel(new FlowLayout());
		JPanel datePanelRow2 = new JPanel(new FlowLayout());
		JPanel datePanelRow3 = new JPanel(new FlowLayout());

		datePanelRow1.add(dateLabel);
		datePanelRow2.add(startYear);
		datePanelRow2.add(startMonth);
		datePanelRow2.add(startDay);
		datePanelRow3.add(endYear);
		datePanelRow3.add(endMonth);
		datePanelRow3.add(endDay);
		datePanel.add(datePanelRow1);
		datePanel.add(datePanelRow2);
		datePanel.add(datePanelRow3);

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (tagRButton.isSelected()) {

					ArrayList<Photo> searchResult = null;
					if (tagList.size() == 0) {
						searchResult = client.getUser().getPhotos();
					} else {
						searchResult = client.getPhotosByTag(tagList);
					}

					listModel.clear();

					for (Photo p : searchResult) {
						listModel.addElement(p);
					}

				} else if (dateRButton.isSelected()) {

					String startYearInput = startYear.getText().trim();
					String startMonthInput = startMonth.getText().trim();
					String startDayInput = startDay.getText().trim();

					String endYearInput = endYear.getText().trim();
					String endMonthInput = endMonth.getText().trim();
					String endDayInput = endDay.getText().trim();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

					int lengthSum = startYearInput.length() + startMonthInput.length() + startDayInput.length() 
							+ endYearInput.length() + endMonthInput.length() + endDayInput.length();

					if (lengthSum != 16) {
						JOptionPane.showMessageDialog(null, "Date input must be in the form YYYY/MM/DD", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (!isInteger(startYearInput) ||
							!isInteger(startMonthInput) ||
							!isInteger(startDayInput) ||
							!isInteger(endYearInput) ||
							!isInteger(endMonthInput) ||
							!isInteger(endDayInput))
					{
						JOptionPane.showMessageDialog(null, "Only integer values accepted.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					int day1 = 0, day2 = 0, month1 = 0, month2 = 0;

					try {
						day1 = Integer.parseInt(startDayInput);
						day2 = Integer.parseInt(endDayInput);
						month1 = Integer.parseInt(startMonthInput);
						month2 = Integer.parseInt(endMonthInput);
					} catch(NumberFormatException ex) {
						ex.printStackTrace();
					}

					if (day1 > 31 || day2 > 31 || day1 <= 0 || day2 <= 0
							|| month1 > 12 || month2 > 12 || month1 <=0 || month2 <=0) {
						JOptionPane.showMessageDialog(null, "Invalid value for month and/or day.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						start = sdf.parse(startYearInput+startMonthInput+startDayInput);
						end = sdf.parse(endYearInput+endMonthInput+endDayInput);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (start == null || end == null) {
						JOptionPane.showMessageDialog(null, "Date input is invalid. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					ArrayList<Photo> searchResult = null;



					searchResult = client.getPhotosbyDate(start, end);


					listModel.clear();

					for (Photo p : searchResult) {
						listModel.addElement(p);
					}

				}

			}
		});

		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (listModel.size() == 0) return;

				String newAlbumName = JOptionPane.showInputDialog(null, "Enter an albumName");

				if (newAlbumName == null) return;
				
				if (newAlbumName.trim().length() == 0) {
					
					JOptionPane.showMessageDialog(null, "Album name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!client.getUser().addAlbum(newAlbumName)) {
					JOptionPane.showMessageDialog(null, "Album name already exists in collection.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				for (int i = 0; i < listModel.size(); i++) {
					Photo p = listModel.get(i);
					p.addtoAlbum(newAlbumName);
					client.getUser().getAlbum(newAlbumName).addPhoto(p);
				}

				JOptionPane.showMessageDialog(null, "Successfully created album");
				return;
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Choose search type");
				tagList.clear();
				tagBox.removeAllItems();
				searchTypeButtonGroup.clearSelection();
			}
		});

		addTagButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String tagValueInput = tagValue.getText().trim();
				Tag t = null;

				if (tagValueInput.length() == 0) {
					JOptionPane.showMessageDialog(null, "Tag value cannot be null.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (locationRButton.isSelected()) {

					t = new Tag(Tag.LOCATION, tagValueInput);
				}
				else if (keywordRButton.isSelected()) {

					t = new Tag(Tag.KEYWORD, tagValueInput);
				}
				else if (personRButton.isSelected()) {

					t = new Tag(Tag.PERSON, tagValueInput);
				}
				//no tag type specified
				else {
					t = new Tag(-1, tagValueInput);
				}

				tagList.add(t);
				tagBox.addItem(t);
				tagButtonGroup.clearSelection();
				tagValue.setText("");
			}
		});

		deleteTagButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Tag t = (Tag) tagBox.getSelectedItem();

				if (t == null) {
					return;
				}

				tagList.remove(t);
				tagBox.removeItem(t);
			}
		});

		dateRButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Search dates");
			}
		});

		tagRButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Search tags");
			}
		});

		IconListCellRenderer renderer = new IconListCellRenderer();
		photoList = new JList<Photo>(listModel);
		photoList.setCellRenderer(renderer);
		photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(photoList);

		listPanel.add(scrollPane, BorderLayout.CENTER);

		chooseSearchTypePanel.add(dateRButton);
		chooseSearchTypePanel.add(tagRButton);

		cardPanel.add(chooseSearchTypePanel, "Choose search type");
		cardPanel.add(tagPanel, "Search tags");
		cardPanel.add(datePanel, "Search dates");
		cardLayout.show(cardPanel, "Choose search type");

		createSearchPanel.add(searchButton);
		createSearchPanel.add(createButton);
		createSearchPanel.add(cancelButton);

		buttonPanel.add(cardPanel);
		buttonPanel.add(createSearchPanel);
		contentPane.add(listPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(contentPane);

	}


	private boolean isInteger(String input) {


		try {
			Integer.parseInt(input);
		} catch(NumberFormatException ex) {
			return false;
		}

		return true;
	}

}
