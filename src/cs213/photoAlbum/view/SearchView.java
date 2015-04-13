package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

@SuppressWarnings("serial")
public class SearchView extends JFrame {
	
	private JPanel contentPane, listPanel, 
	buttonPanel, datePanel, tagPanel, createSearchPanel;
	
	private Client client;
	
	private JTextField startYear, startMonth, startDay, endYear, endMonth, endDay, tagValue;
	
	private JRadioButton locationRButton, keywordRButton, personRButton;
	
	private JLabel dateLabel;//, keywordLabel, locationLabel, personLabel;
	
	private JButton searchButton, createButton, addTagButton, deleteTagButton;
	
	private Date start, end;
	
	private JComboBox<Tag> tagBox;
	
	private ArrayList<Tag> tagList;
	
	private JScrollPane scrollPane;

	private JList<Photo> photoList;

	private DefaultListModel<Photo> listModel;
	
	//tablemodel to make call to createorrenamealbumview 
	public SearchView(Client c, DefaultTableModel tableModel) {
		
		super("Search " + c.getUser().getName() + "'s Photos");
		setSize(820, 400);
		
		this.client = c;
		
		start = null;
		end = null; 
		
		tagList = new ArrayList<Tag>();
		Tag t = new Tag(-1, "   ");
		
		tagBox = new JComboBox<Tag>();
		tagBox.addItem(t);
		
		listModel = new DefaultListModel<Photo>();
		
		startYear = new JTextField(4);
		startMonth = new JTextField(2);
		startDay = new JTextField(2);
		endYear = new JTextField(4);
		endMonth = new JTextField(2);
		endDay = new JTextField(2);
		tagValue = new JTextField(10);
		
		dateLabel = new JLabel("Enter start and end date (YYYY/MM/DD)");
		/*keywordLabel = new JLabel("Keyword:");
		locationLabel = new JLabel("Location:");
		personLabel = new JLabel("Person:");
		*/
		
		contentPane = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(1, 4));
		listPanel = new JPanel(new BorderLayout());
		datePanel = new JPanel(new GridLayout(3, 1));
		tagPanel = new JPanel(new GridLayout(4, 1));
		createSearchPanel = new JPanel(new FlowLayout());
		
		searchButton = new JButton("Search");
		createButton = new JButton("Create Album");
		addTagButton = new JButton("Add Tag");
		deleteTagButton = new JButton("Delete Tag");
		
		ButtonGroup buttonGroup = new ButtonGroup();
		locationRButton = new JRadioButton("Location");
		keywordRButton = new JRadioButton("Keyword");
		personRButton = new JRadioButton("Person");
		buttonGroup.add(locationRButton);
		buttonGroup.add(keywordRButton);
		buttonGroup.add(personRButton);

		
		photoList = new JList<Photo>(listModel);
		scrollPane = new JScrollPane(photoList);
		
		listPanel.add(scrollPane, BorderLayout.CENTER);
		
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
		
		buttonPanel.add(datePanel);
		
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
		/*//tagPanel.add(locationLabel);
		tagPanel.add(locationRButton);
		//tagPanel.add(keywordLabel);
		tagPanel.add(keywordRButton);
		//tagPanel.add(personLabel);
		tagPanel.add(personRButton);
		*/
		buttonPanel.add(tagPanel);
		
		createSearchPanel.add(searchButton);
		createSearchPanel.add(createButton);
		buttonPanel.add(createSearchPanel);
		contentPane.add(listPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(contentPane);
		
	}

}
