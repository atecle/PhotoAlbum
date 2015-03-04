package cs213.photoAlbum.util;

import java.io.File;
import java.io.IOException;

public class Helper {

	public static String getCanonicalPath(String filename) throws IOException {
		
		File file = new File(filename);
		
		if (file.exists()) {
			
			return file.getCanonicalPath();
		} else {
			return null;
		}
	}
}
