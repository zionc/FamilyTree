

import java.util.ArrayList;

import javafx.scene.image.Image;
/**
 * Person class represents a person in the family tree
 * each person is represented by a name, d.o.b, place of birth,
 * spouse, a description, and a list of children.
 * @author zionchilagan
 *
 */


public class Person {
	
	/** attributes for a person */
	private String name;
	private String dateOfBirth;
	private String placeOfBirth;
	private String spouse;
	private String description;
	private ArrayList<Person> children;
	private Image image = null;
	
	/**
	 * Constructs Person with a name, date of birth, place of birth and spouse
	 * @param name - name of person
	 * @param dateOfBirth - person's date of birth
	 * @param placeOfBirth - person's origin
	 * @param spouse - person's spouse (if any)
	 */
	public Person(String name, String dateOfBirth, String placeOfBirth,String spouse) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
		this.spouse = spouse;
		children = new ArrayList<Person>();
	
	}
	
	/**
	 * Constructs no-arg Person
	 */
	public Person() {
		this("","","","");
	}
	
	/**
	 * Sets the children for this person
	 * @param children - children of this person
	 */
	public void setChildren(ArrayList<Person> children) {
		this.children = children;
	}

	/**
	 * Gets name
	 * @return - name of person
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name
	 * @param name - new name to set to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get date of birth
	 * @return - date of birth for person
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	/**
	 * Sets date of birth
	 * @param dateOfBirth - new birthdate
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets person's origin
	 * @return - place of birth of Person
	 */
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	/**
	 * Set origin
	 * @param placeOfBirth - new place of birth for person
	 */
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	
	/**
	 * Get person's spouse
	 * @return - spouse of person
	 */
	public String getSpouse() {
		return spouse;
	}

	/**
	 * Set the person's spouse
	 * @param spouse - new spouse of person
	 */
	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}
	
	/**
	 * Get description for Person
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets description for person
	 * @param description - new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Get string representation of person
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + " born " + dateOfBirth + ", in " + placeOfBirth + "\n");
		sb.append("Spouse: " + spouse);
		sb.append("\n" + description);
		sb.append("\nChildren: " + "\n");
		for(int i = 0; i < children.size(); i++) {
			sb.append(children.get(i).getName());
			if(i != children.size()-1) {
				sb.append(",");
			}
		}
		return sb.toString();
		
	}

	

}
