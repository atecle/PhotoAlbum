package cs213.photoAlbum.model;

import java.io.Serializable;

public class Tag implements Serializable {

	/**
	 * This class encapsulates a Tag object and methods needed for altering attributes of this Tag.
	 * Implements Serializable, converting an instance Tag object into a sequence of bytes.
	 */
	
	/**Universal version identifier for a Serializable class.*/
	private static final long serialVersionUID = 1L;

	/** Type Location of a tag, associated with int 0 */
	public static final int LOCATION = 0;
	
	/** Type Person of a tag, associated with int 1 */
	public static final int PERSON = 1;
	
	/** Type Keyword of a tag, associated with int 2  */
	public static final int KEYWORD = 2;
	
	/** Type of a tag */
	private int type;
	
	/** Value of a tag */
	private String value;
	
	
	/** 
	 * Constructs a new tag given the type of the tag and it's value
	 * 
	 * @param type Tag type (Person, Location, etc...)
	 * @param value Tag value 
	 */
	
	public Tag(int type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the type of the tag
	 * 
	 * @return Type of the tag
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Returns the value of the tag
	 * 
	 * @return Value of the tag
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the type of the tag to given string typeSet
	 * 
	 * @param typeSet Integer that indicates the type of tag
	 */
	public void setType(int typeSet){
		this.type = typeSet;
	}
	
	
	/**
	 * Sets the value of the tag to given string valueSet
	 * 
	 * @param valueSet String that will be set as the value	
	 */
	public void setValue(String valueSet){
		this.value = valueSet;
	}
	
	/**
	 * Overrides toString method for proper output when printing objects. 
	 * Sets the tagString to location, person or keyword depending on the
	 * type.
	 *  
	 * @return String that contains type and value of the tag
	 */
	@Override
	public String toString() {
		
		String tagString = null;
		switch(type) {
		case 0:
			tagString = "Location:";
			break;
		case 1:
			tagString = "Person:";
			break;
		case 2:
			tagString = "Keyword:";
			break;
		}
		
		tagString+=value;
		
		return tagString;
	}
	
	/**
	 * Overrides the equals method to check for equality of values inside the objects.
	 * Checks if tag objects have same data or not.
	 * 
	 * @return True if tag objects have same data, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		boolean retVal = false;
		
		if (o instanceof Tag) {
			Tag tag = (Tag)o;
			
			if (tag.getType() == -1) {
				retVal = (this.value.compareToIgnoreCase(tag.getValue()) == 0);
			} else {
				retVal = (this.type == tag.getType() && this.value.compareToIgnoreCase(tag.getValue()) == 0);
			}
		}
		
		return retVal;
	}

}
