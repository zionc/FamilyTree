

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 * Main method to display GUI
 * @author zionchilagan
 *
 */
public class TreeMain extends Application {
	
	
	/**
	 * Constructs a tree by first reading a file, then constructs
	 * the GUI representation for the tree
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Family Tree");
		BufferedReader in = null;
		Tree<Person> tree = null;
		String treeFileName = "familytree.txt";
		String treeInfoFileName = "familyinfo.txt";
		try {
			File file = new File(treeInfoFileName);
			if(file.exists()) {
				in = new BufferedReader(new FileReader(treeFileName));
				tree = Tree.readBinaryTree(file, in);
				TreeGUI main = new TreeGUI(tree);
				Scene scene = new Scene(main,1400,900);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			else
				throw new FileNotFoundException(treeInfoFileName + " does not exist");
			
		} catch(NullPointerException e) {
			System.out.println("Null Pathname " );
		} catch(FileNotFoundException e) {
			System.out.println(treeFileName + " was not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(in != null)
					in.close();
			} catch(IOException e) {
				System.out.println("Could not close file");
			}
		} 
		
		
		
		
	}
	
	/**
	 * Run program
	 * 
	 */
	public static void main(String[]args) {
		launch(args);
	}
}
