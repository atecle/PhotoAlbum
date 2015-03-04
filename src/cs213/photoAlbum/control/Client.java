package cs213.photoAlbum.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.IBackend;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;
import cs213.photoAlbum.model.User;

/**
 * The Client class provides an interface to the user to perform user functions. 
 * Acts as an intermediary between the user and the backend interface, ensuring validity of pass  
 */
public class Client implements IClient
{

	/**IBackend object declaration*/
	private IBackend backendInterface;

	/**Command mode, set to integer 0*/
	private static final int COMMAND = 0;
	
	/**Interactive mode, set to integer 1*/
	private static final int INTERACTIVE = 1;

	/**Initializes mode to 0, command mode*/
	@SuppressWarnings("unused")
	private int mode = 0;

	/**Id of current user*/
	private String id;

	/**
	 * Constructs a client object that makes calls to the backend using the passed object,
	 * which implements the backend interface.
	 * 
	 * @param backendInterface IBackend object
	 */
	public Client(IBackend backendInterface) {
		this.backendInterface = backendInterface;
		setMode(COMMAND);
	}


	/**
	 * Creates a new user and adds to the backend database. Passed ID should be unique.
	 * 
	 * @param id Unique id of new user
	 * @param name Name of user. Need not be unique.
	 * @return True if and only if id is unique among all users.
	 */
	public boolean addUser(String id, String name) {

		return backendInterface.addUser(id, name);
	}

	/**
	 * Deletes a user from the backend database, identified by their unique id.
	 * 
	 * @param id Unique ID of user caller wants to delete
	 * @return True if and only if passed ID belongs to a user in backend database
	 */
	public boolean deleteUser(String id) {

		return backendInterface.deleteUser(id);
	}

	/**
	 * Gets ArrayList of currently stored users
	 * 
	 * @return ArrayList<User> of currently stored users.
	 */
	public ArrayList<User> getUsers() {

		return backendInterface.getUsers();

	}

	/** */
	private void setMode(int mode) {
		this.mode = mode;
	}


	/**
	 * Login as a user and switch to interactive mode in command view. 
	 * 
	 *  @param id User Id the caller wants to log in as
	 *  @return True if and only if passed id belongs to a user stored in backend
	 */
	public boolean login(String id) {

		if (!backendInterface.userExists(id)) 
			return false;

		setMode(INTERACTIVE);
		this.id = id;
		return true;
	}

	/**
	 * Writes user data to disk upon quit. 
	 */
	public void writeUsers() {
		try {
			backendInterface.writeUsers();
		} catch (IOException e) {
			System.err.println("Error: IOException while serializing user data. " + e.getMessage());
		}
	}

	/**
	 * Creates album for currently logged in user. 
	 * Passed album name must be unique among current user's album names.
	 * 
	 * @param name Album name to be added
	 * @return True if and only if name parameter is unique to user album names. 
	 */
	public boolean createAlbum(String name) {

		User user = backendInterface.getUser(id);
		if (user == null) return false;			//shouldn't happen

		return user.addAlbum(name);
	}

	/**
	 * Deletes album for currently logged in user. Passed album name must exist in user album collection.
	 * 
	 * @param name Album name to be deleted
	 * @return True if and only if passed album name is the name of an existing album in user's collection
	 */
	public boolean deleteAlbum(String name) {

		User user = backendInterface.getUser(id);
		if (user == null) return false;

		return user.deleteAlbum(name);
	}

	/**
	 * Returns list of albums of user currently logged in
	 * 
	 * @return ArrayList<Album> of albums for currently logged in user.
	 */
	public ArrayList<Album> listAlbums() {

		User user = backendInterface.getUser(id);

		return user.getAlbums();
	}

	/**
	 * Returns Id of currently logged in user
	 * 
	 * @return String id of currently logged in user
	 */
	public String getCurrentUserID() {
		return id;
	}


	/**
	 * Returns an ArrayList<Photo> of all photos stored in passed album name.
	 * 
	 * @param albumname Name of album that must exist in user's album collection
	 * @return ArrayList<Photo> of photo objects stored in albumname, null if albumname does not exist
	 */
	public ArrayList<Photo> listPhotos(String albumname) {

		User user = backendInterface.getUser(id);

		Album album = user.getAlbum(albumname);

		if (album == null) {

			System.out.println("Album " + albumname + " does not exist.");
			return null;
		}

		return album.getPhotos();
	}

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
	public boolean addPhoto(String filename, String caption, String albumName) {

		User user = backendInterface.getUser(id);
		Album album = null;
		File file = new File(filename);
		Photo photo = null;
		String canonicalPath = "";

		if ((album = user.getAlbum(albumName)) == null || !file.exists()) {

			System.out.println("Album " + albumName + " does not exist.");
			return false;
		}

		if (album.containsPhoto(filename)) {

			System.out.println("Photo " + filename + " already exists in album " + albumName);
			return false;
		}

		if (!user.hasPhoto(filename)) {

			
			try {
				canonicalPath = file.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			photo = new Photo(canonicalPath, caption);
			
			user.addPhoto(photo);
		} else {

			photo = user.getPhoto(canonicalPath);
		}

		photo.addtoAlbum(albumName);
		album.addPhoto(photo);
		return true;
	}

	/**
	 * Returns User object of currently logged in user
	 * 
	 * @return User object corresponding to currently logged in user
	 */
	public User getUser() {
		return backendInterface.getUser(id);
	}

	/**
	 * Moves photo associated with filename from oldAlbumName to newAlbumName. 
	 * Checks validity of input arguments and passes to user method.
	 * 
	 * @param filename Name of file that must exist in oldAlbumname
	 * @param oldAlbumName Album's old name that must exist in user collection and contain photo associated with filename
	 * @param newAlbumName Album's new name that must exist in user collection and not already contain photo associated with filename
	 * @return True if and only if both oldAlbumName and newAlbumName exist, oldAlbumName contains filename, and newAlbumName does not contain filename
	 */
	public boolean movePhoto(String filename, String oldAlbumName, String newAlbumName) {

		User user = backendInterface.getUser(id);
		Album album = null;

		if ((album = user.getAlbum(oldAlbumName)) == null) {

			System.out.println("Album " + oldAlbumName + " does not exist for user " + id);
			return false;
		}

		if ((user.getAlbum(newAlbumName)) == null) {

			System.out.println("Album " + newAlbumName + " does not exist for user " + id);
			return false;
		}

		if (!album.containsPhoto(filename)) {

			System.out.println("Photo " + filename + " does not exist in " + oldAlbumName);
			return false;
		}

		return user.movePhoto(filename, oldAlbumName, newAlbumName);
	}

	/**
	 * Removes photo from album
	 * 
	 * @param filename Name of file that must exist in albumName
	 * @param albumName Album name that must exist in user album collection
	 * @return True if and only if albumName exists, and filename exists in albumName
	 */
	public boolean removePhoto(String filename, String albumName) {

		User user = backendInterface.getUser(id);
		Photo photo = null;
		Album album = null;

		if ((album = user.getAlbum(albumName)) == null) {

			System.out.println("Album " + albumName + " does not exist.");
			return false;
		}

		if (!album.deletePhoto(filename)) {

			System.out.println("Photo " + filename + " is not in album " + albumName);
			return false;
		}

		photo = user.getPhoto(filename);
		photo.removeFromAlbum(albumName);

		if (photo.getAlbumNames().size() == 0) {

			//We have deleted the photo from all albums, so now delete it from the user.
			user.deletePhoto(filename);
		}

		return true;
	}

	/**
	 * Adds tag to a photo. If photo associated with filename is present in multiple albums, 
	 * tag will added to all references of filename
	 * 
	 * @param filename Name of file that must exist somewhere in users collection.
	 * @param tagType Type of tag that must be 0, 1, or 2
	 * @param tagValue Value of tag
	 * @return True if and only if tagType is of existing type and filename exists in user collection
	 */
	public boolean addTag(String filename, String tagType, String tagValue) {

		Photo photo = null;
		int type = -1;

		switch (tagType.trim()) {
		case "Location:":
			type = Tag.LOCATION;
			break;
		case "Keyword:":
			type = Tag.KEYWORD;
			break;
		case "Person:":
			type = Tag.PERSON;
			break;
		default: 
			System.out.println("Tag type " + tagType + " does not exist");
			return false;
		}

		User user = backendInterface.getUser(id);

		Tag tag = new Tag(type, tagValue);
		photo = user.getPhoto(filename);

		if (photo == null) {

			System.out.println("Photo " + filename + " does not exist for user " + id);
			return false;
		}

		if (photo.hasTag(tag)) {

			System.out.println("Tag already exists for " + filename + " " + tagType + ":" + tagValue);
			return false;
		}

		photo.addTag(type, tagValue);

		return true;
	}

	/**
	 * Deletes a tag from a photo. If photo associated with filename is present in multiple albums, 
	 * tag will be deleted from all references of filename
	 * 
	 * @param filename Name of file that must exist in user collection
	 * @param tagType Tag type of tag wanted for deletion. Must be 0, 1, or 2
	 * @param tagValue Value of tag wanted for deletion.
	 * @return True if and only if filename exists, and tag identified by tagType and tagValue exists for filename.
	 */
	public boolean deleteTag(String filename, String tagType, String tagValue) {

		int type = -1;
		Photo photo = null;


		switch (tagType.trim()) {
		case "Location:":
			type = Tag.LOCATION;
			break;
		case "Keyword:":
			type = Tag.KEYWORD;
			break;
		case "Person:":
			type = Tag.PERSON;
			break;
		default: 
			System.out.println("Tag type " + tagType + " does not exist");
			return false;
		}

		User user = backendInterface.getUser(id);
		Tag tag = new Tag(type, tagValue);
		photo = user.getPhoto(filename);

		if (photo == null) {

			System.out.println("Photo " + filename + " does not exist for user " + id);
			return false;
		}

		if (!photo.hasTag(tag)) {

			System.out.println("Tag does not exist for " + filename + " " + tagType + ":" + tagValue);
			return false;
		}

		photo.removeTag(tag);

		return true;
	}


	/**
	 * Gets photo object associated with filename.
	 * 
	 * @param filename Name of file that must exist in user collection
	 * @return Photo object associated with filename. Null if filename does not exist.
	 */
	public Photo getPhoto(String filename) {
		return backendInterface.getUser(id).getPhoto(filename);
	}

	/**
	 * Returns ArrayList<Photo> of all photos after start date and before end date.
	 * 
	 * @param startDate Begin date range
	 * @param endDate End date range.
	 * @return ArrayList<Photo> of all photos within specified range.
	 */
	public ArrayList<Photo> getPhotosbyDate(Date startDate, Date endDate) {

		ArrayList<Photo> photos = backendInterface.getUser(id).getPhotos();

		ArrayList<Photo> photosSubset = new ArrayList<Photo>(photos.size());

		for (Photo photo : photos) {
			if (photo.getDate().getTime().after(startDate) && photo.getDate().getTime().before(endDate)) {
				photosSubset.add(photo);
			}
		}

		return photosSubset;
	}
	
	/**
	 * Returns Arraylist<Photo> of all photos by tag indicated by tokens
	 * 
	 * @param tokens Contains the tag type of photos 
	 * @return ArrayList<Photo> of all photo objects having all tags indicated in tokens
	 */
	public ArrayList<Photo> getPhotosByTag(ArrayList<String> tokens) {
		
		ArrayList<Tag> tags = new ArrayList<Tag>();
		Tag tag = null;
		
		for (int i = 1; i < tokens.size(); ) {
			
			switch(tokens.get(i)) {
			case "Location:":
				tag = new Tag(Tag.LOCATION, tokens.get(i + 1));
				tags.add(tag);
				i+=2;
				break;
			case "Person:":
				tag = new Tag(Tag.PERSON, tokens.get(i + 1));
				tags.add(tag);
				i+=2;
				break;
			case "Keyword:":
				tag = new Tag(Tag.KEYWORD, tokens.get(i + 1));
				tags.add(tag);
				i+=2;
				break;
			default:
				
				if (tokens.get(i).charAt(tokens.get(i).length() - 1) == ':') {
					//entered a bad tag type.
					tag = new Tag(-1, tokens.get(i));
					tags.add(tag);
					i+=2;
					break;
				}
				//didn't enter a tag type.
				tag = new Tag(-1, tokens.get(i));
				tags.add(tag);
				i++;
				break;
			}
		}
		
		ArrayList<Photo> allPhotos = backendInterface.getUser(id).getPhotos();
		ArrayList<Photo> tagPhotos = new ArrayList<Photo>();
		
		
		for (Photo photo : allPhotos) {
			
			boolean add = true;
		
			for (Tag t : tags) {
				
				if (!photo.hasTag(t)) {
					add = false;
				}
			}
			
			if (add) {
				tagPhotos.add(photo);
			}
		}
		return tagPhotos;
	}
}
