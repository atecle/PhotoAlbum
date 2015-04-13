package cs213.photoAlbum.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cs213.photoAlbum.model.Photo;

/**
 *
 */

@SuppressWarnings("rawtypes")
public class IconListCellRenderer extends JLabel implements ListCellRenderer {


	/**Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */

	public IconListCellRenderer() {
		
		setOpaque(true);
		setHorizontalAlignment(JLabel.LEFT);
	}
	
	/**
	 * 
	 */
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {


		if (isSelected) {

			setBackground(Color.blue);
			setForeground(Color.WHITE);
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		ImageIcon icon = ((Photo) value).getThumbnail();

		setIcon(icon);
		setText(((Photo) value).getCaption());
		setAlignmentX(JLabel.CENTER_ALIGNMENT);


		return this;
	}
}
