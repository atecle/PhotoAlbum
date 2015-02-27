package cs213.photoAlbum.control;

import java.util.ArrayList;
import java.util.Date;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;

public interface IClient {

	public boolean addUser(String id, String name);
	
	public boolean deleteUser(String id);
	
	public ArrayList<User> getUsers();
	
	public boolean login(String id);
	
	public void writeUsers();
	
	public boolean createAlbum(String name);
	
	public boolean deleteAlbum(String name);
	
	public ArrayList<Album> listAlbums();
	
	public String getCurrentUserID();
	
	public ArrayList<Photo> listPhotos(String albumname);
	
	public boolean addPhoto(String filename, String caption, String albumName);
	
	public User getUser();
	
	public boolean movePhoto(String filename, String oldAlbumName, String newAlbumName);
	
	public boolean removePhoto(String filename, String albumName);
	
	public boolean addTag(String filename, String tagType, String tagValue);
	
	public boolean deleteTag(String filename, String tagType, String tagValue);
	
	public Photo getPhoto(String filename);
	
	public ArrayList<Photo> getPhotosbyDate(Date startDate, Date endDate);
	
	
}
