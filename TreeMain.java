package treeAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class TreeMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hiker");
		BufferedReader in = null;
		Tree<Person> tree = null;
		try {
			in = new BufferedReader(new FileReader("familytree.txt"));
			tree = Tree.readBinaryTree(in);
			System.out.println("******Main tester*****");
			
			//System.out.println(tree.preOrderToString());
			
			
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
		
		
		TreeGUI main = new TreeGUI(tree);
		
		//main.setId("pane");
		Scene scene = new Scene(main,1400,900);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
	}
	
	public static void main(String[]args) {
		launch(args);
	}
}