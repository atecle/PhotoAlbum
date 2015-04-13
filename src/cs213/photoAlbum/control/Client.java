/**
 * @author Adam Tecle
 * 
 */

package cs213.photoAlbum.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * {@inheritDoc}
	 */
	public boolean addUser(String id, String name) {

		return backendInterface.addUser(id, name);
	}

	public boolean addUser(String id, String name, String password) {

		return backendInterface.addUser(id, name, password);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteUser(String id) {

		return backendInterface.deleteUser(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<User> getUsers() {

		return backendInterface.getUsers();

	}

	private void setMode(int mode) {
		this.mode = mode;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean login(String id) {

		if (!backendInterface.userExists(id)) 
			return false;

		setMode(INTERACTIVE);
		this.id = id;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeUsers() {
		try {
			backendInterface.writeUsers();
		} catch (IOException e) {
			System.err.println("Error: IOException while serializing user data. " + e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean createAlbum(String name) {

		User user = backendInterface.getUser(id);
		if (user == null) return false;			//shouldn't happen

		return user.addAlbum(name);
	}

	/**
	 *	{@inheritDoc}
	 */
	public boolean deleteAlbum(String name) {

		User user = backendInterface.getUser(id);
		if (user == null) return false;

		return user.deleteAlbum(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Album> listAlbums() {

		User user = backendInterface.getUser(id);

		return user.getAlbums();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCurrentUserID() {
		return id;
	}


	/**
	 * {@inheritDoc}
	 */
	public List<Photo> listPhotos(String albumname) {

		User user = backendInterface.getUser(id);

		Album album = user.getAlbum(albumname);

		if (album == null) {

			return null;
		}

		return album.getPhotos();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addPhoto(String filename, String caption, String albumName) {

		User user = backendInterface.getUser(id);
		Album album = null;
		File file = new File(filename);
		Photo photo = null;
		String canonicalPath = "";

		if ((album = user.getAlbum(albumName)) == null || !file.exists()) {

			return false;
		}

		if (album.containsPhoto(filename)) {

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
			if (photo == null) {

				try {
					canonicalPath = file.getCanonicalPath();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				photo = new Photo(canonicalPath, caption);

				user.addPhoto(photo);
			}
			else {
				if (caption.trim().length() != 0) {
					photo.setCaption(caption);
				}
			}
		}

		photo.addtoAlbum(albumName);
		album.addPhoto(photo);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public User getUser() {
		return backendInterface.getUser(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean movePhoto(String filename, String oldAlbumName, String newAlbumName) {

		User user = backendInterface.getUser(id);
		Album album = null;

		if ((album = user.getAlbum(oldAlbumName)) == null) {

			return false;
		}

		if ((user.getAlbum(newAlbumName)) == null) {

			return false;
		}

		if (!album.containsPhoto(filename)) {

			return false;
		}

		return user.movePhoto(filename, oldAlbumName, newAlbumName);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removePhoto(String filename, String albumName) {

		User user = backendInterface.getUser(id);
		Photo photo = null;
		Album album = null;

		if ((album = user.getAlbum(albumName)) == null) {

			return false;
		}

		if (!album.deletePhoto(filename)) {

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
	 * 
	 */
	public boolean removePhotoFromAlbum(String photoName, String albumName) {

		Photo photo = backendInterface.getUser(id).getPhoto(photoName);

		return photo.removeFromAlbum(albumName);
	}

	/**
	 * {@inheritDoc}
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
			return false;
		}

		User user = backendInterface.getUser(id);

		Tag tag = new Tag(type, tagValue);
		photo = user.getPhoto(filename);

		if (photo == null) {

			return false;
		}

		if (photo.hasTag(tag)) {

			return false;
		}

		photo.addTag(type, tagValue);

		return true;
	}

	/**
	 * {@inheritDoc}
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
			return false;
		}

		User user = backendInterface.getUser(id);
		Tag tag = new Tag(type, tagValue);
		photo = user.getPhoto(filename);

		if (photo == null) {

			return false;
		}

		if (!photo.hasTag(tag)) {

			return false;
		}

		photo.removeTag(tag);

		return true;
	}


	/**
	 * {@inheritDoc}
	 */
	public Photo getPhoto(String filename) {
		return backendInterface.getUser(id).getPhoto(filename);
	}

	/**
	 *	{@inheritDoc}
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
	 * {@inheritDoc}
	 */
	public ArrayList<Photo> getPhotosByTag(ArrayList<Tag> tags) {

	
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

	/**
	 * {@inheritDoc}
	 */
	public boolean renameAlbum(String oldName, String newName) {

		return backendInterface.getUser(id).renameAlbum(oldName, newName);

	}


	/**
	 * 
	 */
	public boolean checkPassword(String pw, String id) {
		return backendInterface.checkPassword(pw, id);
	}


}
