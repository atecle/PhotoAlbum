package cs213.photoAlbum.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * @return True if and only if id is unique among all users.
	 */
	public boolean addUser(String id, String name);
	
	/**
	 * Deletes a user from the backend database, identified by their unique id.
	 * 
	 * @param id Unique ID of user caller wants to delete
	 * @return True if and only if passed ID belongs to a user in backend database
	 */
	public boolean deleteUser(String id);
	

	/**
	 * Gets ArrayList of currently stored users
	 * 
	 * @return ArrayList of currently stored users.
	 */
	public List<User> getUsers();
	
	/**
	 * Login as a user and switch to interactive mode in command view. 
	 * 
	 *  @param id User Id the caller wants to log in as
	 *  @return True if and only if passed id belongs to a user stored in backend
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
	 * @return True if and only if name parameter is unique to user album names. 
	 */
	public boolean createAlbum(String name);
	
	/**
	 * Deletes album for currently logged in user. Passed album name must exist in user album collection.
	 * 
	 * @param name Album name to be deleted
	 * @return True if and only if passed album name is the name of an existing album in user's collection
	 */
	public boolean deleteAlbum(String name);
	
	/**
	 * Returns list of albums of user currently logged in
	 * 
	 * @return ArrayList of albums for currently logged in user.
	 */
	public List<Album> listAlbums();
	
	/**
	 * Returns Id of currently logged in user
	 * 
	 * @return String id of currently logged in user
	 */
	public String getCurrentUserID();
	
	/**
	 * Returns an ArrayList of all photos stored in passed album name.
	 * 
	 * @param albumname Name of album that must exist in user's album collection
	 * @return ArrayList of photo objects stored in albumname, null if albumname does not exist
	 */
	public List<Photo> listPhotos(String albumname);
	
	/**
	 * Adds a photo to an album. Passed filename must be the name of an existing file on disk, need not be a photo file. Caption will
	 * be associated with photo, unless filename has already been added to an album in this user's collection, in which case the previous
	 * caption will be retained. AlbumName is name of album to which the photo is being added.
	 * 
	 * @param filename Name of an existing file on disk
	 * @param caption  The caption to be associated with file, only if photo is being added for first time.
	 * @param albumName Album name of an existing album, that does not already contain photo associated with filename.
	 * @return True if and only if filename is an existing file, albumName exists, and albumName does not already contain filename
	 */
	public boolean addPhoto(String filename, String caption, String albumName);
	
	/**
	 * Returns User object of currently logged in user
	 * 
	 * @return User object corresponding to currently logged in user
	 */
	public User getUser();
	
	/**
	 * Moves photo associated with filename from oldAlbumName to newAlbumName. 
	 * Checks validity of input arguments and passes to user method.
	 * 
	 * @param filename Name of file that must exist in oldAlbumname
	 * @param oldAlbumName Album's old name that must exist in user collection and contain photo associated with filename
	 * @param newAlbumName Album's new name that must exist in user collection and not already contain photo associated with filename
	 * @return True if and only if both oldAlbumName and newAlbumName exist, oldAlbumName contains filename, and newAlbumName does not contain filename
	 */
	public boolean movePhoto(String filename, String oldAlbumName, String newAlbumName);
	
	/**
	 * Removes photo from album
	 * 
	 * @param filename Name of file that must exist in albumName
	 * @param albumName Album name that must exist in user album collection
	 * @return True if and only if albumName exists, and filename exists in albumName
	 */
	public boolean removePhoto(String filename, String albumName);
	
	/**
	 * Adds tag to a photo. If photo associated with filename is present in multiple albums, 
	 * tag will added to all references of filename
	 * 
	 * @param filename Name of file that must exist somewhere in users collection.
	 * @param tagType Type of tag that must be 0, 1, or 2
	 * @param tagValue Value of tag
	 * @return True if and only if tagType is of existing type and filename exists in user collection
	 */
	public boolean addTag(String filename, String tagType, String tagValue);
	

	/**
	 * Deletes a tag from a photo. If photo associated with filename is present in multiple albums, 
	 * tag will be deleted from all references of filename
	 * 
	 * @param filename Name of file that must exist in user collection
	 * @param tagType Tag type of tag wanted for deletion. Must be 0, 1, or 2
	 * @param tagValue Value of tag wanted for deletion.
	 * @return True if and only if filename exists, and tag identified by tagType and tagValue exists for filename.
	 */
	public boolean deleteTag(String filename, String tagType, String tagValue);
	

	/**
	 * Gets photo object associated with filename.
	 * 
	 * @param filename Name of file that must exist in user collection
	 * @return Photo object associated with filename. Null if filename does not exist.
	 */
	public Photo getPhoto(String filename);
	

	/**
	 * Returns ArrayList of all photos after start date and before end date.
	 * 
	 * @param startDate Begin date range
	 * @param endDate End date range.
	 * @return ArrayList of all photos within specified range.
	 */
	public List<Photo> getPhotosbyDate(Date startDate, Date endDate);
	
	/**
	 * Returns Arraylist of all photos by tag indicated by tokens
	 * 
	 * @param tokens Contains the tag type of photos 
	 * @return ArrayList of all photo objects having all tags indicated in tokens
	 */
	public List<Photo> getPhotosByTag(List<String> tokens);
	
	/**
	 * Rename an album.
	 * 
	 * @param oldName album to be renamed
	 * @param newName new name of album
	 * @return
	 */
	public boolean renameAlbum(String oldName, String newName);
	
}
