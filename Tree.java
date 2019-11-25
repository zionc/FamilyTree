

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;



/**
 * Tree class encompasses a General tree, in which
 * a tree can have zero to multiple sub trees.
 * @author zionchilagan
 *
 *
 */

public class Tree<E> {
	
	/**
	 * Class encompasses a tree node
	 * @author zionchilagan
	 *
	 * @param <E>
	 */
	public static class Node<E> {
		/** data of the node */
		private E data;
		/** List of children of the node */
		private ArrayList<Node<E>> children = new ArrayList<Node<E>>();
		/** Node's Parent node*/
		private Node<E> parentNode;
		
		/**
		 * Constructs a node with data
		 * @param data - data for the node
		 */
		public Node(E data) {
			this.data = data;
			parentNode = null;
		}
		
		/**
		 * Constructs a node with data and parent
		 * @param data - data to set node
		 * @param parent - parent of the node
		 */
		public Node(E data, Node<E> parent) {
			this(data);
			this.parentNode = parent;
		}
		
		/**
		 * Create child node with data, set parent node
		 * of child to calling node
		 * @param data - data of the child
		 */
		public void addChildren(E data) {
			Node<E> temp = new Node<E>(data);
			temp.setParent(this);
			children.add(temp);
		}
		
		/**
		 * Adds node to list
		 * @param node - child node to add
		 */
		public void addChildren(Node<E> node) {
			node.setParent(this);
			children.add(node);
		}
		
		/**
		 * Get list of children 
		 * @return - list of children nodes
		 */
		public ArrayList<Node<E>> getChildren() {
			return children;
		}
		
		/**
		 * Get Node's parent
		 * @return - parent Node
		 */
		public Node<E> getParent() {
			return this.parentNode;
		}
		
		/**
		 * 
		 * @param parent
		 */
		public void setParent(Node<E> parent) {
			parentNode = parent;
		}
		
		public E getData() {
			return data;
		}
		
		public String toString() {
			return "" + data;
		}
		
	}
	
	private Node<E> root;
	
	/**
	 * Constructs a tree, represents leaf
	 * @param data
	 */
	public Tree(E data) {
		root = new Node<E>(data);
	}
	
	/**
	 * Constructs tree given root
	 * @param root
	 */
	public Tree(Node<E> root) {
		this.root = root;
	}
	
	/**
	 * Constructs Tree with a node, and children
	 * as it's sub trees
	 * @param data - data of root node
	 * @param children - children of root node
	 */
	public Tree(E data, ArrayList<Tree<E>> children) {
		this.root = new Node<E>(data);
		for(Tree<E> tree: children) {
			if(tree !=null) {
				addToTree(tree);
			}
		}
		
	}
	
	/**
	 * Helper method to add a tree to root
	 * @param e - tree to add
	 */
	private void addToTree(Tree<E> e) {
		root.addChildren(e.root);	
	}
	
	/**
	 * Get root of tree
	 * @return - root of tree
	 */
	public Node<E> getRoot() {
		return root;
	}
	
	/**
	 * Helper method to transfer data of root's children
	 * to an array list. (Used to create children list
	 * for Person)
	 * @return - List of children
	 */
	private ArrayList<E> transferToPerson() {
		ArrayList<E> children = new ArrayList<E>();
		for(int i = 0; i < root.getChildren().size(); i++) {
			children.add(root.getChildren().get(i).data);
		}
		return children;
	}
	
	/**
	 * Depending on the index, getSubTree returns 
	 * the root's sub tree
	 * @param index - index of desired sub tree
	 * @return - desired sub tree
	 */
	public Tree<E> getSubTree(int index) {
		if(root != null && root.getChildren().get(index) != null) 
			return new Tree<E>(root.getChildren().get(index));
		else
			return null;
	}
	
	/**
	 * Returns whether or not tree is a leaf node
	 * @return
	 */
	public boolean isLeaf() {
		return (root == null || root.getChildren().size() == 0);
	}
	
	/**
	 * String representation of a tree
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Root: " + root.data + "\n");
		for(Node<E> n: root.getChildren()) {
			sb.append("Children: ");
			sb.append(n);
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * Constructs tree by capturing a name, reading a file and
	 * creating Person objects with the given name. A tree is
	 * then constructed as well as it's sub trees as the buffered
	 * reader parses the file
	 * @param bR - BufferedReader to open file for reading
	 * @return - Complete Tree
	 * @throws IOException
	 */
	public static Tree<Person> readBinaryTree(File file, BufferedReader bR)
            throws IOException {
		
        String data = bR.readLine().trim();
        int iterations =  new Integer(data.substring(data.indexOf(" ") +1, data.length()));
        String name = data.substring(0, data.indexOf(" "));
        Person person = new PersonFileInitializer(file,name).getPerson();
        String imageName = name + ".png";
        
        try { Image image = new Image(imageName );
        	person.setImage(image);
        } catch(NullPointerException e) {
        	System.out.println(imageName + " is null");
        } catch(IllegalArgumentException e) {
        	System.out.println(imageName + " is unsupported");
        } 
        
        //person.setImage(image);
        
        if (iterations == 0) {
            Tree<Person> tree = new Tree<Person>(person);
           
            return tree;
        } else {
        	ArrayList<Tree<Person>> list = new ArrayList<Tree<Person>>();
        	for(int i = 0; i < iterations; i++) {
        		Tree<Person> tree = readBinaryTree(file,bR);
        		list.add(tree);
        	}
        	Tree<Person> temp = new Tree<Person>(person,list);
        	
        	person.setChildren(temp.transferToPerson());

        	
        return temp;
        }
       
    }
	
	/**
	 * Constructs a toString by traversing through the 
	 * tree in pre order
	 * @return pre order String representation of the tree
	 */
	public String preOrderToString() {
		StringBuilder sb = new StringBuilder();
		preOrderToString(sb, root);
		return sb.toString();
	}
	
	/**
	 * Helper method to traverse the tree in pre order
	 * @param sb - StringBuilder to construct toString as it
	 * reaches each node
	 * @param root - root to traverse from
	 */
	private void preOrderToString(StringBuilder sb, Node<E> root) {
		if(root.parentNode == null) {
			sb.append(root);
		}
		else {
			sb.append(" ");
			sb.append(root);
		}
		
		if(root.getChildren().size() != 0) {
			sb.append(" ==> ");
			for(int i = 0; i < root.getChildren().size(); i++) {
				preOrderToString(sb,root.getChildren().get(i));
			}
		}
		
	}
	
	/**
	 * Get list of the names for each tree 
	 * @return - list of names
	 */
	public ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
		addNamesPreOrder(names,root);
		return names;
	}
	
	/**
	 * Helper method to add names to list in pre order traversal
	 * @param list - list to add names to
	 * @param root - root of each node to traverse from
	 */
	private void addNamesPreOrder(ArrayList<String> list, Node<E> root) {
		list.add(root.toString());
		if(root.getChildren().size() != 0) {
			for(int i = 0; i < root.getChildren().size(); i++) {
				addNamesPreOrder(list,root.getChildren().get(i));
			}
		}
	}
	
	

	

	
	

}
