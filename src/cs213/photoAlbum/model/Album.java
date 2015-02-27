package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Album implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/** A unique album name per user **/
	private String name;
	
	private Date start;
	
	private Date end;
	
	/** Hashmap storing photos in this album, keyed by unique filename **/
	private HashMap<String, Photo> photos;
	
	public Album(String name) {
		this.name = name;
		photos = new HashMap<String, Photo>();
	}
	
	/** 
	 * Adds a photo to the album
	 * @oaram	photo Passes in a Photo object
	 */
	public void addPhoto(Photo photo) {
		
		setStartandEnd(photo.getDate().getTime());
		photos.put(photo.getName(), photo);
	}
	
    /**
     * Returns the name of the photo
     * 
     * @param name  String name is set to the name variable passed in
     * @return The name of the photo is returned
     */
	public Photo getPhoto(String filename) 
	{
		
		return photos.get(filename);
	}
	
	
	/**
	 * Returns the name of the album
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns how many photos are in an album 
	 * @return count
	 */
	public int getSize() {
		return photos.size();
	}
		
	/**
	 * Returns the list of photos in the album
	 * @return photos	
	 */
	public ArrayList<Photo> getPhotos() {
		return new ArrayList<Photo>(photos.values());
	}
	
	/**
	 * Deletes a photo from an album.
	 * @param name	The String of the name passed in
	 */
	public boolean deletePhoto(String filename) {
		
		if (!photos.containsKey(filename)) return false;
		
		photos.remove(filename);
		return true;
	}
	
	private void setStartandEnd(Date date) {
		
		if (start == null && end == null) {
			start = date;
			end = date;
			return;
		}
		
		if (date.before(start)) start = date;
		if (date.after(end)) end = date;
	}
	
	
	/**
	 * Get date of earliest photo in this album
	 * @return Date of earliest photo
	 */
	public Date getStartDate() {
		return start;
	}
	
	/**
	 * Get Date of latest photo in this album
	 * @return Date of latest photo.
	 */
	public Date getEndDate() {
		return end;
	}
	
	/**
	 * 
	 * @param filename
	 * @return true if album contains photo.
	 */
	public boolean containsPhoto(String filename) {
		
		return photos.containsKey(filename);
	}
}
