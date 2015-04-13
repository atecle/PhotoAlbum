/**
 * @author Adam Tecle
 * 
 */

package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Tag;

/**
 * This class is responsible for the display and the functionality on the RemoveTagView window. 
 * A user can delete a currently existing tag that exists under a specific photo.
 *
 */
@SuppressWarnings("serial")
public class RemoveTagView extends JFrame {
	
	/** To replace default content pane **/
	private JPanel contentPane, buttonPane;
	
	/**Button to remove a tag*/
	private JButton deleteButton;
	
	/**Works with JTable to hold the names of the tags values and tag types*/
	private DefaultTableModel listModel;
	
	/** Table to organize the display of the tags value and corresponding type*/
	private JTable table;
	
	/**Provides a scrollable view for the list of tags*/
	private JScrollPane scrollPane;
	
	/**Client object declaration*/
	private Client client;
	
	/**Holds the list of tags associated with a photo*/
	private ArrayList<Tag> tags;
	
	/**
	 * Class constructor which creates the frame of the RemoveTagView window
	 * 
	 * @param c  Allows access to the stored data
	 * @param photoName The name of the photo
	 */
	public RemoveTagView(Client c, final String photoName) {
		
		super("Remove Tag");
		
		setSize(300, 200);
		setResizable(false);
		setResizable(false);

		
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
				break;
			case Tag.LOCATION:
				type = "Location";
				break;
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
				
				int selectedOption = JOptionPane.showConfirmDialog(null, 
						"Are you sure?", 
						"Choose", 
						JOptionPane.YES_NO_OPTION); 
				if (selectedOption == JOptionPane.NO_OPTION) {
					return;
				}
				
				Tag t = tags.get(index);
				
				client.getPhoto(photoName).removeTag(t);
				listModel.removeRow(index);
				//client.writeUsers();

			}
		});
		
		buttonPane = new JPanel(new FlowLayout());
		buttonPane.add(deleteButton);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);
		
		setContentPane(contentPane);
		
		
	}
}
