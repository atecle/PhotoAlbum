/**
 * @author Adam Tecle
 * 
 */

package cs213.photoAlbum.model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * Class backend stores and fetches user data.
 * It contains method for saving a list of all users with their respective details,
 * a load function to retrieve an Array List of current users,
 * a function to retrieve data for a specific user,
 * a function to add a new User,
 * and a function to delete a User.  
 * This uses the Session Persistence model to determine when to save and load data.
 * Implements Serializable and IBackend.
 */
public class Backend implements Serializable, IBackend
{

	/**Universal version identifier for a Serializable class. */
	private static final long serialVersionUID = 1L;
	
	/**The name of the folder where data will be stored */
	public static final String storeDir = "data";
	
	/**The file inside the folder where data will be stored */
	public static final String storeFile = "data.dat";
	
	/** HashMap of User objects */
	private HashMap<String, User> users;

	/**
	 * Constructs backend object, responsible for reading and writing user data to disk, creating/deleting users, and getting user objects.
	 */
	public Backend()
	{
		users = new HashMap<String, User>();

		File tmp = new File("data/data.dat");
		if(tmp.exists()) {
			
			
			try {

				readUsers();
			} catch (ClassNotFoundException e) {

				System.err.println("Error: ClassNotFoundException while loading user data. " + e.getMessage());
			} catch (IOException e) {

				System.err.println("Error: IOException while loading user data. " + e.getMessage());
			}
		} else {	//create file
			
			
			tmp.getParentFile().mkdir();
			try {

				tmp.createNewFile();
			} catch (IOException e) {
				System.err.println("Error: IOException while creating data.dat file");
			}
			
		}
	}

	/**
	 * Deletes a user from backend database
	 * 
	 * @param id User id to be deleted
	 * @returns True if and only if user with id exists
	 */
	@Override
	public boolean deleteUser(String id) {

		if (users.containsKey(id)) {
			users.remove(id);
			return true;
		}

		return false;
	}

	
	/**
	 * Gets a users from corresponding user Id
	 * 
	 * @param id User Id
	 * @return User object associated with id
	 */
	@Override
	public User getUser(String id) {
		return users.get(id);
	}
	
	/**
	 * Gets ArrayList of users stored in backend
	 * 
	 * @return ArrayList of user objects currently stored in backend
	 */
	@Override
	public ArrayList<User> getUsers() {
		return new ArrayList<User>(users.values());
	}

	/**
	 * Creates a user based on unique id and name
	 * 
	 * @param id User Id to be added
	 * @param name User name to be added
	 * @return True if user with id does not already exist in database
	 */
	@Override
	public boolean addUser(String id, String name) {

		if (users.containsKey(id)) 
			return false;

		User user = new User(id, name);
		users.put(user.getID(), user);

		return true;

	}
	
	/**
	 * 
	 */
	@Override
	public boolean addUser(String id, String name, String password) {
		
		if (users.containsKey(id)) 
			return false;

		User user = new User(id, name, password);
		users.put(user.getID(), user);

		return true;
	}

	/**
	 * Writes contents of user HashMap to disk
	 * 
	 * @throws IOException Constructs an IOException with null as its error detail message.
	 */
	@Override
	public void writeUsers() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(users);
		oos.close();
	}

	
	/**
	 * Loads stored user information on disk to user HashMap 
	 * 
	 * @throws IOException Constructs an IOException with null as its error detail message.
	 * @throws ClassNotFoundException No definition for the class with the specified name was found
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void readUsers() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storeDir + File.separator + storeFile));
		users = (HashMap<String, User>)ois.readObject();
		ois.close();
	}

	/**
	 * Checks if user exists in database
	 * 
	 * @param id User Id to be checked
	 * @return True if and only if user with id exists in database.
	 */
	@Override
	public boolean userExists(String id) {
		return users.containsKey(id);
	}

	@Override
	public boolean checkPassword(String pw, String id) {
		return users.get(id).loginSuccess(pw);
	}
}
