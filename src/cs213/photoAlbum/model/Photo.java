package cs213.photoAlbum.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class encapsulates a Photo object and methods needed for altering attributes of this Photo.
 * Implements Serializable, converting an instance Photo object into a sequence of bytes.
 *
 */
public class Photo implements Serializable {

	/** Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;


	/** A unique per user filename */
	private String filename;

	/** A caption for the photo */
	private String caption;

	/** Modification date of file */
	private Calendar date;

	/** Set of tags for file */
	private ArrayList<Tag> tags;

	/** A list of album names to which this photo belongs */
	private ArrayList<String> albumNames;

	/** Thumbnail icon */
	private ImageIcon thumbnail = null;
	
	/** file object **/
	private File file;


	/**
	 * Constructs photo object from filename and caption
	 * 
	 * @param filename Name of file that must exist on disk
	 * @param caption Photo's caption to be associated with photo object across all albums it's added to
	 */
	public Photo(String filename, String caption) {

		this.filename = filename;
		File f = new File(filename);
		this.file = f;

		Image image = null;

		try {
			image = ImageIO.read(file).getScaledInstance(100, 67, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			//should never happen, this exception is caught outside of constructor call when checking if selected file is an image.
			System.err.println("IOException while reading image file. " + e.getMessage());
		}
		
		this.thumbnail = new ImageIcon(image);

		Calendar cal = Calendar.getInstance();
		Date date = new Date(file.lastModified());
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		setDate(cal);


		this.caption = caption;
		tags = new ArrayList<Tag>();
		albumNames = new ArrayList<String>();
	}

	public Photo (File file, String caption) throws IOException{

		this.filename = file.getCanonicalPath();


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
	 * Returns the name of the photo object
	 * 
	 * @return Name of this photo object
	 */
	public String getName() {
		return filename;
	}

	/**
	 * Returns caption of the photo 
	 * 
	 * @return Caption of the photo
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Sets the caption for the photo to the given string cap
	 * 
	 * @param cap Photo's caption
	 */
	public void setCaption(String cap) {
		this.caption = cap;
	}

	/**
	 * Returns the date of the photo
	 * 
	 * @return Date variable that contains the date
	 */
	public Calendar getDate() {
		return date;
	}


	/**
	 * Replaces an old caption with a new caption
	 * 
	 * @param text String that will replace the original caption
	 */
	public void editCaption(String text) {

		this.caption = text;
	}

	/**
	 * Sets date of photo. Corresponds to last modified property of file
	 * 
	 * @param date Calendar object that contains the date of the photo
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * Adds tag to tag list
	 * 
	 * @param type Contains the type of the tag
	 * @param text Contains the text of the tag
	 */
	public void addTag(int type, String text) {
		tags.add(new Tag(type, text));
	}

	/**
	 * Removes tag identified by type and value from this photo object
	 * 
	 * @param tag Tag object to be removed
	 */
	public void removeTag(Tag tag) {
		tags.remove(tag);
	}

	/**
	 * Checks if a photo contains a tag
	 * 
	 * @param tag Tag object to be checked for existence
	 * @return True if and only if this photo object has tag identified by type and value, or just value if type is unknown.
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
	 * Called when photo is added to an album, this method is used for book keeping.
	 * 
	 * @param albumName Name of the album
	 */
	public void addtoAlbum(String albumName) {
		albumNames.add(albumName);
	}

	/**
	 * Called when this photo is removed from album, method is used for book keeping.
	 * 
	 * @param albumName Name of the album
	 */
	public void removeFromAlbum(String albumName) {
		albumNames.remove(albumName);
	}

	/**
	 * Gets list of albums this photo has been added to
	 * 
	 * @return List of albums this photo belongs to.
	 */
	public ArrayList<String> getAlbumNames() {
		return albumNames;
	}

	/**
	 * Gets the tags associated with a photo
	 * 
	 * @return List of tags this photo has
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}

	/**
	 * Returns the tags associated to a single photo object
	 * 
	 * @return Number of tags this photo has
	 */
	public int tags() {
		return tags.size();
	}

	/**
	 * 
	 */
	public ImageIcon getThumbnail() {

		if (thumbnail == null) {

			try {
				Image image = ImageIO.read(new File("assets/blankIcon.png")).getScaledInstance(100, 67, Image.SCALE_SMOOTH);
				this.thumbnail = new ImageIcon(image);
			} catch (IOException e) {
				System.err.println("Can't find blank icon image. " + e.getMessage());
			}
		}
		return thumbnail;
	}



}
