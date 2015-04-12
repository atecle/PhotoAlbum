package cs213.photoAlbum.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import cs213.photoAlbum.util.Helper;

/**
 * Class User contains the information relating to a single user. 
 * The class contains the methods for adding, deleting and renaming an album. 
 * Implements Serializable, converting an instance Tag object into a sequence of bytes.
 */
public class User implements Serializable {

	/**Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;

	/** Unique ID used to log in */
	private String id;

	/** User's full name */
	private String name;

	/** HashMap of User's albums, keyed by unique album name. Stores album objects, which store references to 
	 * the photos in the photo HashMap */
	private HashMap<String, Album> albums;

	/** HashMap of Photo objects, keyed by unique file name */
	private HashMap<String, Photo> photos;

	private String password;

	/**
	 * Instantiates a new User object with a name and a unique id
	 * 
	 * @param id User's Id
	 * @param name User's name
	 */
	public User(String id, String name) {
		this.id = id;
		this.name = name;
		albums = new HashMap<String, Album>();
		photos = new HashMap<String, Photo>();
		password = "";
	}

	
	public User(String id, String name, String pw) {
		this.id = id;
		this.name = name;
		albums = new HashMap<String, Album>();
		photos = new HashMap<String, Photo>();
		this.password = pw;
	}

	/**
	 * Adds a new album 
	 * 
	 * @param name Album name
	 * @return Boolean value indicating success or failure
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
	 *  
	 *  @param name Album name to be deleted
	 *  @return Boolean value indicating success or failure
	 */
	public boolean deleteAlbum(String name) {

		if (albums.containsKey(name)) {
			Album album = albums.get(name);
			for (Photo p : album.getPhotos()) {
				p.removeFromAlbum(name);
				if (p.getAlbumNames().size() == 0) {
					deletePhoto(p.getName());
				}
			}
			albums.remove(name);
			return true;
		}

		return false;
	}

	/**
	 * Renames an album from user's list and replaces with new name
	 * 
	 * @param oldName The current name of the album
	 * @param newName The new name for the album that will replace the current name
	 */
	public boolean renameAlbum(String oldName, String newName) {

		if (albums.containsKey(oldName) && !albums.containsKey(newName)) {
			Album album = albums.remove(oldName);
			album.setName(newName);
			albums.put(newName, album);
			for (Photo p : album.getPhotos()){
				p.removeFromAlbum(oldName);
				p.addtoAlbum(newName);
			}
			return true;
		}

		return false;
	}

	/**
	 * Returns User Id
	 * 
	 * @return The user Id
	 */
	public String getID() {
		return id;
	}

	/**
	 * Returns the albums associated with a single user
	 * 
	 * @return ArrayList containing this user's albums. 
	 */
	public ArrayList<Album> getAlbums() {
		return new ArrayList<Album>(albums.values());
	}

	/**
	 * Gets an album object from user collection by name.
	 * 
	 * @param name Album name
	 * @return Album associated with name
	 */
	public Album getAlbum(String name) {
		return albums.get(name);
	}

	/**
	 * Gets the name of a user
	 * 
	 * @return Name of user.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Moves photo associated with filename from oldAlbumName to newAlbumName. 
	 * 
	 * @param filename Must exist in oldAlbumname
	 * @param oldAlbumName Must exist in user collection and contain photo associated with filename
	 * @param newAlbumName Must exist in user collection and not already contain photo associated with filename
	 * @return True if and only if both oldAlbumName and newAlbumName exist, oldAlbumName contains filename, and newAlbumName does not contain filename
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
	 * Checks if a filename of a photo exists under this user
	 * 
	 * @param filename Name of the photo
	 * @return True if and only if filename exists in photos
	 */
	public boolean hasPhoto(String filename) {
		return photos.containsKey(filename);
	}

	/**
	 * Adds photo object to photos
	 * 
	 * @param Photo object to delete
	 */
	public void addPhoto(Photo photo) {

		photos.put(photo.getName(), photo);
	}

	/**
	 * Deletes photo from photos
	 * 
	 * @param filename Name of photo to delete
	 */
	public void deletePhoto(String filename) {

		photos.remove(filename);
	}

	/**
	 * Gets photo associated with filename
	 * 
	 * @param filename Name of the photo
	 * @return Photo object associated with filename, null if filename does not exist in photos.
	 */
	public Photo getPhoto(String filename) {

		String canonicalPath = "";

		try {

			canonicalPath = Helper.getCanonicalPath(filename);
		} catch (IOException e) {
			System.out.println("Error: IOException getting canonical path of " + filename);
			return null;
		}

		return photos.get(canonicalPath);
	}

	/**
	 * Returns keys of photo HashMap as an ArrayList<Photo>
	 * 
	 * @return ArrayList of keys of photo HashMap
	 */
	public ArrayList<Photo> getPhotos() {
		return new ArrayList<Photo>(photos.values());
	}

	public boolean loginSuccess(String enteredPassword) {
		
		if (password == null) return true;
		return password.compareTo(enteredPassword) == 0 || password.length() == 0;
	}
	
	public String toString() {
		
		return name;
	}

}
