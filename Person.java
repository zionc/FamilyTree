package treeAssignment;

import java.util.ArrayList;

public class Person {
	
	private String name;
	private String dateOfBirth;
	private String placeOfBirth;
	private String spouse;
	private String description;
	private String parent;
	private ArrayList<Person> children;
	public Person(String name, String dateOfBirth, String placeOfBirth,String spouse) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
		this.spouse = spouse;
		
	}
	
	public void setChildren(ArrayList<Person> children) {
		this.children = children;
	}
	
	public Person() {
		this("N/A","N/A","N/A","N/A");
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	
	public String getSpouse() {
		return spouse;
	}

	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + " born " + dateOfBirth + ", in " + placeOfBirth + "\n");
		sb.append("Spouse: " + spouse);
		sb.append("\nParent: " + parent);
		sb.append("\n" + description);
		sb.append("\nChildren: " + "\n");
		/*for(Person p: children) {
			sb.append(p.getName());
			sb.append(", ");
			
		} */
		return sb.toString();
		
	}

}
