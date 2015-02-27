package cs213.photoAlbum.model;

import java.io.IOException;
import java.util.ArrayList;

public interface IBackend {

	public boolean deleteUser(String id);

	public ArrayList<User> getUsers();
	
	public boolean addUser(String id, String name);
	
	public User getUser(String id);
	
	public void readUsers() throws IOException, ClassNotFoundException;
	
	public void writeUsers() throws IOException;
	
	public boolean userExists(String id);
	
	
}
