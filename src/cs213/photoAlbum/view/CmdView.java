/**
 * @author Jennifer DeLaOsa
 * @author Adam Tecle
 */

package cs213.photoAlbum.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.control.Sorter;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.util.Helper;

/**
 * 
 * Class CmdView contains the location of the initial calls and will respond to command line arguments
 * submitted by the user. Initially, a simple command line mode in activated. Once the user
 * is logged in, a more extensive interactive mode is then activated. 
 *
 */
public class CmdView 
{


	/** Client object declaration*/
	private static Client client;
	
	public CmdView(Client client) {
		CmdView.client = client;
	}
	
	/** 
	 * 
	 * User can enter: delete user <tag> <name>, list user, add user <tag> <name>
	 */
	public void parseInput(String input, Scanner sc) 
	{
		//int userId = 0;
		//String userName = null;

		String[] tokens = input.split("\\s+");
		String id;
		String name;
		switch (tokens[0].trim()) {
		case "listusers":

			ArrayList<User> users = client.getUsers();

			if (users.size() == 0) {

				System.out.println("No users exist.");
				return;
			}

			for (User user : users) {

				System.out.println(user.getID());
			}
			System.out.println();
			return;

		case "adduser":

			if (tokens.length != 3) {

				System.out.println("Usage: adduser <user id> <user name>");
				System.out.println();

				return;
			}

			id = tokens[1];
			name = tokens[2];

			if (!client.addUser(id, name)) {

				System.out.println("User with id " + id + " already exists.");
				System.out.println();
			} else {

				System.out.println("Created user with id " + id + " and name " + name);
				System.out.println();
			}

			return;

		case "deleteuser":

			if (tokens.length != 2) {

				System.out.println("Usage: deleteuser <user id>");
				System.out.println();
				return;
			}

			id = tokens[1];
			if (!client.deleteUser(id)) {

				System.out.println("User with id " + id + " does not exist.");
				System.out.println();
			} else {

				System.out.println("Deleted user with id " + id + ".");
				System.out.println();
			}

			return;

		case "login":

			if (tokens.length != 2) {

				System.out.println("Usage: login <user id>");
				System.out.println();
				return;
			}

			id = tokens[1];

			if (!client.login(id)) {

				System.out.println("User with id " + id + " does not exist.");
				System.out.println();
			} else {

				System.out.println(" ==== Login successful ==== ");
				System.out.println();
				System.out.println("Now in interactive mode. Enter 'logout' to quit");
				while (true) {
					input = sc.nextLine();
					interactiveMode(input, sc);
				}

			}
			return;
		case "quit":

			client.writeUsers();
			System.exit(0);
			sc.close();

			return;
		default:

			System.out.println("Not a valid command");
			System.out.println();
			return;
		}

	}


	private static void interactiveMode(String input, Scanner sc) {

		ArrayList<String> tokens = tokenizer(input);
		if (tokens.size() == 0) return;
		String name;

		while (true) {

			switch(tokens.get(0)) {

			case "createAlbum":

				if (tokens.size() != 2) {

					System.out.println("Usage: createAlbum \"<album name>\"");
					return;
				}

				name = tokens.get(1);

				if (!client.createAlbum(name)) {

					System.out.println("Album exists for user " + client.getCurrentUserID() + ":\n"+name);
				} else {

					System.out.println("Created album for user " + client.getCurrentUserID() + ":\n" + name);
				}
				return;
			case "deleteAlbum":


				if (tokens.size() != 2) {

					System.out.println("Usage: deleteAlbum \"<album name>\"");
					return;
				}

				name = tokens.get(1);

				if (!client.deleteAlbum(name)) {

					System.out.println("Album does not exist for user " + client.getCurrentUserID() + ":\n"+name);
				} else {

					System.out.println("Deleted album from user " + client.getCurrentUserID() + ":\n" + name);
				}
				return;	
			case "listAlbums":

				if (tokens.size() != 1) {

					System.out.println("Usage: listAlbums");
					return;
				} 

				ArrayList<Album> albums = client.listAlbums();

				if (albums.size() == 0) {

					System.out.println("No albums exist for user " + client.getCurrentUserID());
					return;
				}

				System.out.println("Albums for user id " + client.getCurrentUserID());
				for (Album album : albums) {

					System.out.print(album.getName() + " number of photos: " + ((album.getSize())) + ", ");
					if (album.getSize() == 0) {
						System.out.println("No start date - No end date");
					} else {

						System.out.println(Helper.formatDate(album.getStartDate()) + " - " + Helper.formatDate(album.getEndDate()));
					}

				}

				return;
			case "listPhotos":

				if (tokens.size() != 2) {

					System.out.println("Usage: listPhotos <album name>");
					return;
				}

				ArrayList<Photo> photos = (ArrayList<Photo>) client.listPhotos(tokens.get(1));

				if (photos != null) {

					System.out.println("Photos for album " + tokens.get(1));

					for (Photo photo : photos) {

						System.out.println(photo.getName() +  " - " + Helper.formatDate(photo.getDate().getTime()));

					}
				}
				return;
			case "addPhoto":

				if (tokens.size() != 4) {

					System.out.println("Usage: addPhoto <filename> <caption> <album name>");
					return;
				}

				if (client.addPhoto(tokens.get(1), tokens.get(2), tokens.get(3))) {

					System.out.println("Added photo " + tokens.get(1) + ": " + client.getPhoto(tokens.get(1)).getCaption() +
							" - Album: " + tokens.get(3));
				}

				return;
			case "movePhoto":

				if (tokens.size() != 4) {

					System.out.println("Usage: movePhoto <filename> <old album name> <new album name>");
					return;
				}

				if (client.movePhoto(tokens.get(1), tokens.get(2), tokens.get(3))) {

					System.out.println("Moved photo " + tokens.get(1) + ":\n" + tokens.get(1) + " - from album " 
							+ tokens.get(2) + " to album " + tokens.get(3));
				}

				return;
			case "removePhoto":


				if (tokens.size() != 3) {

					System.out.println("Usage: removePhoto <filename> <album name>");
					return;
				}

				if (client.removePhoto(tokens.get(1), tokens.get(2))) {

					System.out.println("Removed photo:\n" + tokens.get(1) +  " - From album " + tokens.get(2));
				}
				return;
			case "addTag":

				if (tokens.size() != 4) {

					System.out.println("Usage: addTag <filename> <tag type>:\"<tag value>\"");
					return;
				}

				if (client.addTag(tokens.get(1), tokens.get(2), tokens.get(3))) {

					System.out.println("Added tag:\n" + tokens.get(1) + " " + tokens.get(2)  + tokens.get(3));
				}
				return;
			case "deleteTag":

				if (tokens.size() != 4) {

					System.out.println("Usage: deleteTag <filename> <tag type>:\"<tag value>\"");
					return;
				}

				if (client.deleteTag(tokens.get(1), tokens.get(2), tokens.get(3))) {

					System.out.println("Deleted tag:\n" + tokens.get(1) + " " + tokens.get(2)  + tokens.get(3));
				}

				return;
			case "listPhotoInfo":

				if (tokens.size() != 2) {

					System.out.println("Usage: listPhotoInfo <file name>");
					return;
				}


				Photo photo = client.getPhoto(tokens.get(1));

				if (photo == null) {

					System.out.println("Photo " + tokens.get(1) + " does not exist.");
					return;
				}

				ArrayList<String> albumNames = photo.getAlbumNames();

				System.out.print("Album: " + albumNames.get(0));
				for (int i = 1; i < albumNames.size(); i++) {

					System.out.println(", " + albumNames.get(i));
				}

				System.out.println();
				System.out.println("Date: " + Helper.formatDate(photo.getDate().getTime()));
				System.out.println("Caption: " + photo.getCaption());

				ArrayList<Tag> tags = photo.getTags();
				tags = Sorter.sortTags(tags);

				System.out.println("Tags: ");

				for (Tag tag : tags) {

					System.out.println(tag);
				}

				return;
			case "getPhotosByDate":

				if (tokens.size() != 3) {

					System.out.println("Usage: getPhotosbyDate <start date> <end date>");
					return;
				}

				String startDate = tokens.get(1), endDate = tokens.get(2);
				Date parsedStartDate = null, parsedEndDate = null;

				if ((parsedStartDate = checkDateFormat(startDate)) == null) {

					System.out.println("Error: Start date has bad format.");
					return;
				}


				if ((parsedEndDate = checkDateFormat(endDate)) == null) {

					System.out.println("Error: End date has bad format.");
					return;
				}

				photos = (ArrayList<Photo>) client.getPhotosbyDate(parsedStartDate, parsedEndDate);

				photos = Sorter.sortDates(photos);
				System.out.println("Photos for user " + client.getCurrentUserID() + " in range " + startDate + " - " + endDate);

				for (Photo p : photos) {

					System.out.print(p.getCaption() + " - "  + "Album: " + p.getAlbumNames().get(0));

					for (int i = 1; i < p.getAlbumNames().size(); i++) {
						System.out.print(", " + p.getAlbumNames().get(i));
					}

					System.out.println(" - Date: " + Helper.formatDate(p.getDate().getTime()));
				}

				return;
			case "getPhotosByTag":

				if (tokens.size() < 2) {

					System.out.println("Usage: getPhotosByTag [<tagType>:]\"<tagValue>\" [,[<tagType>:]\"<tagValue>\"]...");
					return;
				}

				System.out.print("Photos for user " + client.getCurrentUserID() + " with tags " + tokens.get(0));

				for (int i = 1; i < tokens.size(); i++) {

					System.out.print(" " + tokens.get(i));
				}
				
				System.out.println();
				photos = (ArrayList<Photo>) client.getPhotosByTag(tokens);
				
				for (Photo p : photos) {
					
					System.out.print(p.getCaption() + " - Album: " + p.getAlbumNames().get(0));
					
					for (int i = 1; i < p.getAlbumNames().size(); i++) {
						
						System.out.print(", " + p.getAlbumNames().get(i));
					}
					
					System.out.println(" - Date: " + Helper.formatDate(p.getDate().getTime()));
				}

				return;
			case "logout":
				sc.close();
				client.writeUsers();
				System.exit(0);
				return;
			default:
				System.out.println("You entered " + tokens.get(0));
				System.out.println("Not a valid command");
				return;
			}
		}

	}



	private static ArrayList<String> tokenizer(String input) {
		ArrayList<String> matchList = new ArrayList<String>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher regexMatcher = regex.matcher(input);
		while (regexMatcher.find()) {
			if (regexMatcher.group(1) != null) {
				// Add double-quoted string without the quotes
				matchList.add(regexMatcher.group(1));
			} else if (regexMatcher.group(2) != null) {
				// Add single-quoted string without the quotes
				matchList.add(regexMatcher.group(2));
			} else {
				// Add unquoted word
				matchList.add(regexMatcher.group());
			}
		}
		return matchList;
	}

	/**
	 * Checks the format of the Date for correctness
	 * 
	 * @param date The date which will be format checked
	 * @return returns date if correctly formatted, null otherwise.
	 */
	private static Date checkDateFormat(String date) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");

		try {
			Date parsedDate  = dateFormat.parse(date);
			return parsedDate;
		} catch (ParseException e) {
			return null;
		}	
	}


}
