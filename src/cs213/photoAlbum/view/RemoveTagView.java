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
import cs213.photoAlbum.model.Tag;

public class RemoveTagView extends JFrame {

	private JPanel contentPane, buttonPane;
	
	private JButton deleteButton;
	
	private DefaultTableModel listModel;
	
	private JTable table;
	
	private JScrollPane scrollPane;
	
	private Client client;
	
	private ArrayList<Tag> tags;
	
	public RemoveTagView(Client c, final String photoName) {
		
		super("Remove Tag");
		
		setSize(300, 200);
		
		this.client = c;
		
		contentPane = new JPanel(new BorderLayout());
		
		this.tags = client.getPhoto(photoName).getTags();
		
		String[] colNames = {"Type", "Value"};
		
		String[][] data = new String[tags.size()][2];
		
		for (int i = 0; i < tags.size(); i++) {
			
			String type = null;
			
			switch(tags.get(i).getType()) {
			case Tag.KEYWORD:
				type = "Keyword";
			case Tag.LOCATION:
				type = "Location";
			case Tag.PERSON:
				type = "Person";
			}
			
			data[i][0] = type;
			data[i][1] = tags.get(i).getValue();
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
		
		deleteButton = new JButton("Delete");
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int index = table.getSelectedRow();
				
				if (index == -1) return;
				
				Tag t = tags.get(index);
				
				client.getPhoto(photoName).removeTag(t);
				listModel.removeRow(index);
				client.writeUsers();

			}
		});
		
		buttonPane = new JPanel(new FlowLayout());
		buttonPane.add(deleteButton);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);
		
		setContentPane(contentPane);
		
		
	}
}
