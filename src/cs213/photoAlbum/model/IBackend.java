/**
 * @author Adam Tecle
 * 
 */

package cs213.photoAlbum.model;

import java.io.IOException;
import java.util.ArrayList;

/**
 *  Interface IBackend contains group of related methods signatures with empty bodies.
 *  Details what methods can be invoked in Backend, defining their behaviors. 
 */
public interface IBackend {
	
	/**
	 * Deletes a user from backend database
	 * 
	 * @param id User id to be deleted
	 */
	public boolean deleteUser(String id);
	

	/**
	 * Gets ArrayList of users stored in backend
	 * 
	 */
	public ArrayList<User> getUsers();
	
	/**
	 * Creates a user based on unique id and name
	 * 
	 * @param id User Id to be added
	 * @param name User name to be added
	 */
	public boolean addUser(String id, String name);
	
	/**
	 * 
	 */
	public boolean addUser(String id, String name, String password);
	

	/**
	 * Gets a users from corresponding user Id
	 * 
	 * @param id User Id
	 */
	public User getUser(String id);
	
	/**
	 * Loads stored user information on disk to user HashMap 
	 * 
	 * @throws IOException Constructs an IOException with null as its error detail message.
	 * @throws ClassNotFoundException no definition for the class with the specified name was found
	 */
	public void readUsers() throws IOException, ClassNotFoundException;
	
	/**
	 * Writes contents of user HashMap to disk
	 * 
	 * @throws IOException Constructs an IOException with null as its error detail message.
	 */
	public void writeUsers() throws IOException;
	
	/**
	 * Checks if user exists in database
	 * 
	 * @param id User Id to be checked
	 */
	public boolean userExists(String id);
	
	/**
	 * 
	 */
	public boolean checkPassword(String pw, String id);
	
	
}
