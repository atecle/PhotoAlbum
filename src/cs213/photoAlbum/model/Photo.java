package cs213.photoAlbum.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This class encapsulates a photo object and methods needed for altering attributes of this photo.
 *
 */
public class Photo implements Serializable {

	private static final long serialVersionUID = 1L;


	/** A unique per user filename **/
	private String filename;
	
	/** A caption for the photo **/
	private String caption;
	
	/** Modification date of file **/
	private Calendar date;
	
	/** Set of tags for file **/
	private ArrayList<Tag> tags;
	
	/** A list of album names to which this photo belongs **/
	private ArrayList<String> albumNames;
	
	/**
	 * Constructs photo object from filename and caption
	 * @param filename - must exist on disk
	 * @param caption - to be associated with photo object across all albums it's added to
	 */
	public Photo(String filename, String caption) {
		
		this.filename = filename;
		File file = new File(filename);
		
		Calendar cal = Calendar.getInstance();
		Date date = new Date(file.lastModified());
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		setDate(cal);
		
		
		this.caption = caption;
		tags = new ArrayList<Tag>();
		albumNames = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @return filename of this photo object
	 */
	public String getName() {
		return filename;
	}
	
	/**
	 * Returns caption of the photo 
	 * @return caption 	caption of the photo
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * Set the caption for the photo to the given string cap
	 * @param cap	The String that holds the desired caption
	 */
	public void setCaption(String cap) {
		this.caption = cap;
	}
	
	/**
	 * Returns the date of the photo
	 * @return date		variable that contains the date
	 */
	public Calendar getDate() {
		return date;
	}
	
	
	/**
	 * Replaces an old caption to a new caption
	 * @param text	The String that will replace the original caption
	 */
	public void editCaption(String text) {
		
		this.caption = text;
	}
	
	/**
	 * Set date of photo. Corresponds to last modified property of file
	 * @param date
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	/**
	 * Add tag to tag list
	 * @param Tag type 
	 * @param String text 
	 */
	public void addTag(int type, String text) {
		tags.add(new Tag(type, text));
	}
	
	/**
	 * Remove tag identified by type and value from this photo object
	 * @param type
	 * @param value
	 */
	public void removeTag(Tag tag) {
		tags.remove(tag);
	}
	
	/**
	 * 
	 * @param type
	 * @param value
	 * @return true if and only if this photo object has tag identified by type and value. Or just value if type is unknown.
	 */
	public boolean hasTag(Tag tag) {
		
		if (tag.getType() == -1) {
			return 	(tags.contains(new Tag(Tag.LOCATION, tag.getValue()))
					|| tags.contains(new Tag(Tag.KEYWORD, tag.getValue()))
					|| tags.contains(new Tag(Tag.PERSON, tag.getValue())));
		}

		return tags.contains(tag);
	}
	
	/**
	 * Called when photo is added to an album, this method is used for book  keeping.
	 * @param albumName
	 */
	public void addtoAlbum(String albumName) {
		albumNames.add(albumName);
	}
	
	/**
	 * Called when this photo is removed from album, method is used for book keeping.
	 * @param albumName
	 */
	public void removeFromAlbum(String albumName) {
		albumNames.remove(albumName);
	}
	
	/**
	 * Get list of albums this photo has been added to
	 * @return list of albums this photo belongs to.
	 */
	public ArrayList<String> getAlbumNames() {
		return albumNames;
	}
	
	/**
	 * 
	 * @return List of tags this photo has
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}
	
	/**
	 * 
	 * @return number of tags this photo has
	 */
	public int tags() {
		return tags.size();
	}
}
