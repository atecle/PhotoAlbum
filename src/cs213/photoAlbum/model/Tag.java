package cs213.photoAlbum.model;

import java.io.Serializable;

public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int LOCATION = 0;
	
	public static final int PERSON = 1;
	
	public static final int KEYWORD = 2;

	private int type;
	
	private String value;
	
	
	/** Creates a new tag given the type of the tag and it's value
	 * @param type - The type of the tag (Person, Location, etc...)
	 * @param value - The value of the tag 
	 */
	
	public Tag(int type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the type of the tag
	 * @return type	
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Return the value of the tag
	 * @return value	the value of the tag
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Set the type of the tag to given string typeSet
	 * @param typeSet	integer that indicates the type of tag
	 */
	public void setType(int typeSet){
		this.type = typeSet;
	}
	
	
	/**
	 * Set the value of the tag to given string valueSet
	 * @param valueSet	String that will be set as value	
	 */
	public void setValue(String valueSet){
		this.value = valueSet;
	}
	
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
	
	@Override
	public boolean equals(Object o) {
		boolean retVal = false;
		
		if (o instanceof Tag) {
			Tag tag = (Tag)o;
			
			if (tag.getType() == -1) {
				retVal = (this.value.compareTo(tag.getValue()) == 0);
			} else {
				retVal = (this.type == tag.getType() && this.value.compareTo(tag.getValue()) == 0);
			}
		}
		
		return retVal;
	}

}
