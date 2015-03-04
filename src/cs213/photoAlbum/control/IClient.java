package cs213.photoAlbum.control;

import java.util.ArrayList;
import java.util.Date;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;

/**
 *  Interface IClient contains group of related methods signatures with empty bodies.
 *  Details what methods can be invoked in Client, defining their behaviors. 
 */
public interface IClient {

	/**
	 * Creates a new user and adds to the backend database. Passed ID should be unique.
	 * 
	 * @param id Unique id of new user
	 * @param name Name of user. Need not be unique.
	 */
	public boolean addUser(String id, String name);
	
	/**
	 * Deletes a user from the backend database, identified by their unique id.
	 * 
	 * @param id Unique ID of user caller wants to delete
	 */
	public boolean deleteUser(String id);
	
	/**
	 * Gets ArrayList of currently stored users
	 */
	public ArrayList<User> getUsers();
	

	/**
	 * Login as a user and switch to interactive mode in command view. 
	 * 
	 *  @param id User Id the caller wants to log in as
	 */
	public boolean login(String id);
	
	/**
	 * Writes user data to disk upon quit. 
	 */
	public void writeUsers();
	
	/**
	 * Creates album for currently logged in user. 
	 * Passed album name must be unique among current user's album names.
	 * 
	 * @param name Album name to be added
	 */
	public boolean createAlbum(String name);
	
	/**
	 * Deletes album for currently logged in user. Passed album name must exist in user album collection.
	 * 
	 * @param name Album name to be deleted
	 */
	public boolean deleteAlbum(String name);
	
	/**
	 * Returns list of albums of user currently logged in
	 * 
	 */
	public ArrayList<Album> listAlbums();
	
	/**
	 * Returns Id of currently logged in user
	 * 
	 */
	public String getCurrentUserID();
	
	/**
	 * Returns an ArrayList<Photo> of all photos stored in passed album name.
	 * 
	 * @param albumname Name of album that must exist in user's album collection
	 */
	public ArrayList<Photo> listPhotos(String albumname);
	
	/**
	 * Adds a photo to an album. Passed filename must be the name of an existing file on disk, need not be a photo file. Caption will
	 * be associated with photo, unless filename has already been added to an album in this user's collection, in which case the previous
	 * caption will be retained. AlbumName is name of album to which the photo is being added.
	 * 
	 * @param filename Name of an existing file on disk
	 * @param caption  The caption to be associated with file, only if photo is being added for first time.
	 * @param albumName Album name of an existing album, that does not already contain photo associated with filename.
	 */
	public boolean addPhoto(String filename, String caption, String albumName);
	
	/**
	 * Returns User object of currently logged in user
	 * 
	 */
	public User getUser();
	
	/**
	 * Moves photo associated with filename from oldAlbumName to newAlbumName. 
	 * Checks validity of input arguments and passes to user method.
	 * 
	 * @param filename Name of file that must exist in oldAlbumname
	 * @param oldAlbumName Album's old name that must exist in user collection and contain photo associated with filename
	 * @param newAlbumName Album's new name that must exist in user collection and not already contain photo associated with filename
	 */
	public boolean movePhoto(String filename, String oldAlbumName, String newAlbumName);
	
	/**
	 * Removes photo from album
	 * 
	 * @param filename Name of file that must exist in albumName
	 * @param albumName Album name that must exist in user album collection
	 */
	public boolean removePhoto(String filename, String albumName);
	/**
	 * Adds tag to a photo. If photo associated with filename is present in multiple albums, 
	 * tag will added to all references of filename
	 * 
	 * @param filename Name of file that must exist somewhere in users collection.
	 * @param tagType Type of tag that must be 0, 1, or 2
	 * @param tagValue Value of tag
	 */
	public boolean addTag(String filename, String tagType, String tagValue);
	/**
	 * Deletes a tag from a photo. If photo associated with filename is present in multiple albums, 
	 * tag will be deleted from all references of filename
	 * 
	 * @param filename Name of file that must exist in user collection
	 * @param tagType Tag type of tag wanted for deletion. Must be 0, 1, or 2
	 * @param tagValue Value of tag wanted for deletion.
	 */
	public boolean deleteTag(String filename, String tagType, String tagValue);
	/**
	 * Gets photo object associated with filename.
	 * 
	 * @param filename Name of file that must exist in user collection
	 */
	public Photo getPhoto(String filename);
	/**
	 * Returns ArrayList<Photo> of all photos after start date and before end date.
	 * 
	 * @param startDate Begin date range
	 * @param endDate End date range.
	 */
	public ArrayList<Photo> getPhotosbyDate(Date startDate, Date endDate);
	
	
}
