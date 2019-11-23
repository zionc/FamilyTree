package treeAssignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Tree<E> {
	
	public static class Node<E> {
		private E data;
		private ArrayList<Node<E>> children = new ArrayList<Node<E>>();
		private Node<E> parentNode;
		
		public Node(E data) {
			this.data = data;
			//children = new ArrayList<Node<E>>();
			parentNode = null;
		}
		
		public Node(E data, Node<E> parent) {
			this(data);
			this.parentNode = parent;
		}
		
		public void addChildren(E data) {
			Node<E> temp = new Node<E>(data);
			temp.setParent(this);
			children.add(temp);
		}
		
		public void addChildren(Node<E> node) {
			node.setParent(this);
			children.add(node);
		}
		
		public ArrayList<Node<E>> getChildren() {
			return children;
		}
		
		public Node<E> getParent() {
			return this.parentNode;
		}
		
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
	
	public Tree(E data, Tree<E> children) {
		this.root = new Node<E>(data);
		if(children != null) {
			this.root.addChildren(children.root.data);
		}
		
	}
	
	public Tree(E data, ArrayList<Tree<E>> children) {
		this.root = new Node<E>(data);
		for(Tree<E> tree: children) {
			if(tree !=null) {
				addToTree(tree);
			}
		}
		
	}
	
	
	
	public Node<E> getRoot() {
		return root;
	}
	
	
	
	private void addToTree(Tree<E> e) {
		root.addChildren(e.root);
		
	}
	
	public Tree<E> getSubTree(int index) {
		if(root != null && root.getChildren().get(index) != null) 
			return new Tree<E>(root.getChildren().get(index));
		else
			return null;
		
		
	}
	
	public boolean isLeaf() {
		return (root == null || root.getChildren().size() == 0);
	}
	
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
	
	public static Tree<Person> readBinaryTree(BufferedReader bR)
            throws IOException {
       
        String data = bR.readLine().trim();
        int iterations =  new Integer(data.substring(data.indexOf(" ") +1, data.length()));
        String name = data.substring(0, data.indexOf(" "));
        Person person = new PersonFileInitializer(new File("familyinfo.txt"),name).getPerson();
        
        	
        
        //System.out.println("DATA: " + data);
       
        if (iterations == 0) {
            Tree<Person> tree = new Tree<Person>(person);
           // person.setChildren(tree.root.children);
            return tree;
        } else {
        	ArrayList<Tree<Person>> list = new ArrayList<Tree<Person>>();
        	//Tree<String> temp = new Tree<String>(name);
        	for(int i = 0; i < iterations; i++) {
        		Tree<Person> tree = readBinaryTree(bR);
        		list.add(tree);
        		//temp.addToTree(tree);
        	
        	}
        	Tree<Person> temp = new Tree<Person>(person,list);
        	
        return temp;
        }
       
    }
	
	public String preOrderToString() {
		StringBuilder sb = new StringBuilder();
		preOrderToString(sb, root);
		return sb.toString();
	}
	
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
	
	public ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
		addNamesPreOrder(names,root);
		return names;
	}
	
	private void addNamesPreOrder(ArrayList<String> list, Node<E> root) {
		list.add(root.toString());
		if(root.getChildren().size() != 0) {
			for(int i = 0; i < root.getChildren().size(); i++) {
				addNamesPreOrder(list,root.getChildren().get(i));
			}
		}
	}
	
	

	

	
	

}
