package cs213.photoAlbum.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cs213.photoAlbum.util.Helper;


/**
 * This class encapsulates an Album object and methods needed for altering attributes of this Album.
 * Implements Serializable, converting an instance Album object into a sequence of bytes.
 *
 */
public class Album implements Serializable {

	/**Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;

	/** A unique album name per user */
	private String name;
	
	/**Start date of an album */
	private Date start;
	
	/**End date of an album */
	private Date end;
	
	/** Hashmap storing photos in this album, keyed by unique filename */
	private HashMap<String, Photo> photos;
	
	/** 
	 * Constructs an Album with the album's name
	 * 
	 * @param name Album's name
	 */
	public Album(String name) {
		this.name = name;
		photos = new HashMap<String, Photo>();
	}
	
	/** 
	 * Adds a photo to the album
	 * 
	 * @param photo Passed in photo object
	 */
	public void addPhoto(Photo photo) {
		
		setStartandEnd(photo.getDate().getTime());
		photos.put(photo.getName(), photo);
	}
	
    /**
     * Returns the name of the photo
     * 
     * @param filename String name is set to the name variable passed in
     * @return Name of the photo
     */
	public Photo getPhoto(String filename) 
	{
		String canonicalPath = null;
		
		try {
			canonicalPath = Helper.getCanonicalPath(filename);
		} catch (IOException e) {
			
			System.err.println("Error: IOException getting canonical path from " + filename);
		}
		
		if (canonicalPath == null) return null;
		
		return photos.get(canonicalPath);
	}
	
	
	/**
	 * Returns the name of the album
	 * 
	 * @return Name of the album
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the album 
	 * 
	 * @param name Set name of the album
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns how many photos are in an album 
	 * 
	 * @return Size of the album
	 */
	public int getSize() {
		return photos.size();
	}
		
	/**
	 * Returns list of photos in the album
	 * 
	 * @return ArrayList of photos
	 */
	public ArrayList<Photo> getPhotos() {
		return new ArrayList<Photo>(photos.values());
	}
	
	/**
	 * Deletes a photo from an album.
	 * 
	 * @param filename String of the name passed in
	 * @return True if filename removed, false otherwise
	 */
	public boolean deletePhoto(String filename) {
		
		String canonicalPath = null;
		try {
			
			canonicalPath = Helper.getCanonicalPath(filename);
		} catch (IOException e) {
			System.err.println("Error: IOException getting canonical path from " + filename);
		}
		
		if (canonicalPath == null) return false;
		
		if (!photos.containsKey(canonicalPath)) 
			return false;
		
		photos.remove(canonicalPath);
			return true;
	}
	
	/**
	 * Sets the start date and end date for album.
	 * 
	 * @param date Album's start and end date
	 * @return  If start and end dates are null, the start and end are set to the date passed in. 
	 */
	private void setStartandEnd(Date date) {
		
		if (start == null && end == null) {
			start = date;
			end = date;
			return;
		}
		
		if (date.before(start)) 
			start = date;
		if (date.after(end)) 
			end = date;
	}
	
	
	/**
	 * Gets the date of earliest photo in this album
	 * 
	 * @return Date of earliest photo
	 */
	public Date getStartDate() {
		return start;
	}
	
	/**
	 * Gets date of latest photo in this album
	 * 
	 * @return Date of latest photo.
	 */
	public Date getEndDate() {
		return end;
	}
	
	/**
	 * Checks if photo is contained in album
	 * 
	 * @param filename Name of album being checked
	 * @return True if album contains photo.
	 */
	public boolean containsPhoto(String filename) {
		
		String canonicalPath = null;
		
		try {
			
			canonicalPath = Helper.getCanonicalPath(filename);
		} catch (IOException e) {
			System.err.println("Error: IOException getting canonical path from " + filename);
		}
		
		if (canonicalPath == null) return false;
		
		
		return photos.containsKey(canonicalPath);
	}
}
