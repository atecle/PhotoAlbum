/**
 * @author Adam Tecle
 * 
 */
package cs213.photoAlbum.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

	public static String getCanonicalPath(String filename) throws IOException {
		
		File file = new File(filename);
		
		if (file.exists()) {
			
			return file.getCanonicalPath();
		} else {
			return null;
		}
	}
	
	/**
	 * Formats the date correctly
	 * 
	 * @param date The date to be formatted
	 * @return Formatted date
	 */
	public static String formatDate(Date date) {
		DateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		if (date == null) return "No date";
		return outputFormat.format(date);
	}

}
