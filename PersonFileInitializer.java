package treeAssignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PersonFileInitializer {
	
	private File file;
	private ArrayList<Person> peopleList;
	private ArrayList<String> nameList;
	private Person person;
	public PersonFileInitializer(File file, ArrayList<String> nameList) {
		this.file = file;
		this.nameList = nameList;
		peopleList = new ArrayList<Person>();
	}
	
	public PersonFileInitializer(File file, String name) {
		this.file = file;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			person = this.addPerson(br, name);
			
		} catch(IOException e) {
			System.out.println("I/O error");
		} finally {
			try {
				if(br != null) {
					br.close();
					System.out.println("File closed");
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void createPeople() {
		BufferedReader br = null;
		try {
			
			//System.out.println("Size: " + nameList.size());
			for(int i = 0; i < nameList.size(); i++) {
				br = new BufferedReader(new FileReader(file));
				addPerson(br,nameList.get(i));
				//System.out.println(nameList.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
					System.out.println("File closed");
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Person addPerson(BufferedReader br, String name) throws IOException {
		
		String data;
		Person person = new Person();
		while((data = br.readLine()) != null) {
			if(data.equals(name)) {
				//System.out.println("Name: " + data);
				
				person.setName(data);
				data = br.readLine();
				//System.out.println("D.O.B: " + data);
				person.setDateOfBirth(data);
				data = br.readLine();
				//System.out.println("P.O.B: " + data);
				person.setPlaceOfBirth(data);
				data = br.readLine();
				//System.out.println("Spouse: " + data);
				person.setSpouse(data);
				String description = "";
				while(!(data = br.readLine()).equals("End")) {
					description+=data;
				}
				//System.out.println("Description: " + description);
				person.setDescription(description);
				//peopleList.add(person);
			}
		}
		return person;
		
			
	}
	
	
	
	public ArrayList<Person> getPeople() {
		return peopleList;
	}
	
	

}
