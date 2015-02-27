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
 */
public class Backend implements Serializable, IBackend
{

	public static final String storeDir = "data";

	public static final String storeFile = "data.dat";

	private static final long serialVersionUID = 1L;

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
		}
	}

	@Override
	/**
	 * Delete a user from backend database
	 * @param id - id of user to be deleted
	 * @returns true if and only if user with id exists
	 */
	public boolean deleteUser(String id) {

		if (users.containsKey(id)) {
			users.remove(id);
			return true;
		}

		return false;
	}

	@Override
	/**
	 *
	 * @return User object associated with id
	 */
	public User getUser(String id) {
		return users.get(id);
	}
	
	@Override
	/**
	 * @return ArrayList of user objects currently stored in backend
	 */
	public ArrayList<User> getUsers() {
		return new ArrayList<User>(users.values());
	}

	@Override
	/**
	 * Create a user based on unique id and name
	 * @return true if user with id does not already exist in database
	 */
	public boolean addUser(String id, String name) {

		if (users.containsKey(id)) return false;

		User user = new User(id, name);
		users.put(user.getID(), user);

		return true;

	}

	@Override
	/**
	 * Write contents of user HashMap to disk
	 */
	public void writeUsers() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(users);
		oos.close();
	}

	@Override
	@SuppressWarnings("unchecked")
	/**
	 * Load stored user information on disk to user HashMap 
	 */
	public void readUsers() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storeDir + File.separator + storeFile));
		users = (HashMap<String, User>)ois.readObject();
		ois.close();
	}

	@Override
	/**
	 * @return true if and only if user with id exists in database.
	 */
	public boolean userExists(String id) {
		return users.containsKey(id);
	}

}
