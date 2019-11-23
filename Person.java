package treeAssignment;

public class Person {
	
	private String name;
	private String dateOfBirth;
	private String placeOfBirth;
	private String spouse;
	private String description;
	public Person(String name, String dateOfBirth, String placeOfBirth,String spouse) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
		this.spouse = spouse;
	}
	
	public Person() {
		this("N/A","N/A","N/A","N/A");
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
		return name + " born " + dateOfBirth + ", in " + placeOfBirth;
	}

}
