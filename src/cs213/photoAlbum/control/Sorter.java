package cs213.photoAlbum.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

public class Sorter {

	/**
	 * Sorts list of tags. Location tags first, person tags next, and keyword tags last. Tags of same type sorted alphabetically by tag value.
	 * @param tags
	 * @return Sorted list of tags
	 */
	public static ArrayList<Tag> sortTags(ArrayList<Tag> tags) {

		ArrayList<Tag> locations = new ArrayList<Tag>();
		ArrayList<Tag> people = new ArrayList<Tag>();
		ArrayList<Tag> keywords = new ArrayList<Tag>();

		for (Tag tag : tags) {

			switch (tag.getType()) {
			case 0:
				locations.add(tag);
				break;
			case 1:
				people.add(tag);
				break;
			case 2:
				keywords.add(tag);
				break;
			}
		}
		
		// Didn't implement Comparable in Tag because I can't define a natural order between tags of different types.
		
		Collections.sort(locations, new Comparator<Tag>() {
			@Override
			public int compare(Tag lhs, Tag rhs) {
		        return lhs.getValue().compareTo(rhs.getValue());
		    }
		});
		
		
		Collections.sort(people, new Comparator<Tag>() {
			@Override
			public int compare(Tag lhs, Tag rhs) {
		        return lhs.getValue().compareTo(rhs.getValue());
		    }
		});
		
		Collections.sort(keywords, new Comparator<Tag>() {
			@Override
			public int compare(Tag lhs, Tag rhs) {
		        return lhs.getValue().compareTo(rhs.getValue());
		    }
		});		
		
		tags = new ArrayList<Tag>();
		tags.addAll(locations);
		tags.addAll(people);
		tags.addAll(keywords);
		
		return tags;
	}
	
	/**
	 * Sorts list of photos by date
	 * @param photos
	 * @return chronologically sorted list of photo objects
	 */
	public static ArrayList<Photo> sortDates(ArrayList<Photo> photos) {
		
		Collections.sort(photos, new Comparator<Photo>() {
			@Override
			public int compare(Photo lhs, Photo rhs) {
				return lhs.getDate().compareTo(rhs.getDate());
			}
		});
		
		return photos;
	}
}
