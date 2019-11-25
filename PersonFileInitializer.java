

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * PersonFileInitializer reads data file and based on a specific name,
 * constructs Person objects with additional data in the file
 * @author zionchilagan
 *
 */
public class PersonFileInitializer {
	
	/** File to read data from */
	private File file;
	
	
	/** Person object to create */
	private Person person;
	
	
	
	/**
	 * Constructs PersonFileInitializer with a given file, and
	 * single name, which is used to locate the person's data
	 * in the file
	 * @param file - File to read from
	 * @param name - Name to target
	 */
	public PersonFileInitializer(File file, String name) {
		this.file = file;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			person = this.addPerson(br, name);
			
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			System.out.println("I/O error");
		} finally {
			try {
				if(br != null) {
					br.close();
					
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Parses the File and targets a specific name, once it 
	 * finds the name, then a Person object is created with
	 * a name, date of birth, place of birth, spouse and description
	 * @param br - BufferedReader which opens file for reading
	 * @param name - Name of person
	 * @return - Person created
	 * @throws IOException - If I/O Exception occurs, throw 
	 */
	
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
			else {
				person.setName(name);
			}
		}
		return person;
		
			
	}
	
	/**
	 * Get person created
	 * @return
	 */
	public Person getPerson() {
		return person;
	}
	
	
	
	

}
