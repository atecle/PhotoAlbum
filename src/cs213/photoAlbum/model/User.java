package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class User contains the information relating to a single user. 
 * The class contains the methods for adding, deleting and renaming an album. 
 */
public class User implements Serializable {


	private static final long serialVersionUID = 1L;

	/** A unique ID used to log in **/
	private String id;
	
	/** User's full name **/
	private String name;
	
	/** HashMap of User's albums, keyed by unique album name. Stores album objects, which store references to 
	 * the photos in the photo HashMap **/
	private HashMap<String, Album> albums;
	
	/** HashMap of Photo objects, keyed by unique file name **/
	private HashMap<String, Photo> photos;
	
	/**
	 * Instantiates a new User object with a name and a unique id
	 * @param String id of new user
	 * @param String name of new user
	 */
	public User(String id, String name) {
		this.id = id;
		this.name = name;
		albums = new HashMap<String, Album>();
		photos = new HashMap<String, Photo>();
	}
	
	/**
	 * Adds a new album 
	 * @param String name of album
	 * @return boolean value indicating success or failure
	 */
	public boolean addAlbum(String name) 
	{
		
		if (!albums.containsKey(name)) {
			albums.put(name, new Album(name));
			return true;
		}
		
		return false;
	}
	
	
	/**
	 *  Deletes an album from user's list given an album name 
	 *  @param String name of album to be deleted
	 *  @return boolean value indicating success or failure
	 */
	public boolean deleteAlbum(String name) {
		
		if (albums.containsKey(name)) {
			albums.remove(name);
			return true;
		}
		
		return false;
	}
	
	/** Renames an album from user's list and replaces with new name
	 * @param oldName The current name of the album
	 * @param newName The new name for the album that will replace the current name
	 */
	public boolean renameAlbum(String oldName, String newName) {
		
		if (albums.containsKey(oldName) && !albums.containsKey(newName)) {
			Album album = albums.remove(oldName);
			album.setName(newName);
			albums.put(newName, album);
			return true;
		}
		
		return false;
	}
	
	/**
	 * @return user id
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * @return An ArrayList containing this user's albums. 
	 */
	public ArrayList<Album> getAlbums() {
		return new ArrayList<Album>(albums.values());
	}
	
	/**
	 * Get an album object from user collection by name.
	 * @return album associated with name
	 */
	public Album getAlbum(String name) {
		return albums.get(name);
	}

	/**
	 * @return name of user.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Moves photo associated with filename from oldAlbumName to newAlbumName. 
	 * @param filename - must exist in oldAlbumname
	 * @param oldAlbumName - must exist in user collection and contain photo associated with filename
	 * @param newAlbumName - must exist in user collection and not already contain photo associated with filename
	 * @return true if and only if both oldAlbumName and newAlbumName exist, oldAlbumName contains filename, and newAlbumName does not contain filename
	 */
	public boolean movePhoto(String filename, String oldAlbumName, String newAlbumName) {
		
		Photo photo = getPhoto(filename);
	
		Album oldAlbum = getAlbum(oldAlbumName);
		
		Album newAlbum = getAlbum(newAlbumName);
		
	
		if (newAlbum.getPhoto(filename) != null) {
			
			//already exists, returns silently.
			return false;
		}
		
		oldAlbum.deletePhoto(filename);
		photo.removeFromAlbum(oldAlbumName);
		newAlbum.addPhoto(photo);
		photo.addtoAlbum(newAlbumName);
		
		return true;
	}
	

	/**
	 * 
	 * @param filename
	 * @return true if and only if filename exists in photos
	 */
	public boolean hasPhoto(String filename) {
		return photos.containsKey(filename);
	}
	
	/**
	 * Adds photo object to photos
	 * @param photo object to delete
	 */
	public void addPhoto(Photo photo) {
		
		photos.put(photo.getName(), photo);
	}
	
	/**
	 * Delete photo from photos
	 * @param filename of photo to delete
	 */
	public void deletePhoto(String filename) {
		
		photos.remove(filename);
	}
	
	/**
	 * Get photo associated with filename
	 * @param filename
	 * @return Photo object associated with filename. Null if filename does not exist in photos.
	 */
	public Photo getPhoto(String filename) {
		return photos.get(filename);
	}
	
	/**
	 * Returns keys of photo HashMap as an ArrayList<Photo>
	 * @return
	 */
	public ArrayList<Photo> getPhotos() {
		return new ArrayList<Photo>(photos.values());
	}
	
}
