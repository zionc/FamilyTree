

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * TreeGUI represents the graphical representation for the Tree as well
 * as each tree's information
 * @author zionchilagan
 *
 */
public class TreeGUI extends BorderPane {
	/** Group for tree */
	private Group treeGroup;
	/** Container for a person's information */
	private VBox displayPane;
	
	/**
	 * Constructs TreeGUI given a tree, and creates title,
	 * tree pane and information pane
	 * @param tree - Tree to display
	 */
	public TreeGUI(Tree<Person> tree) {
		treeGroup = new Group();
		TreeNode treeNode = new TreeNode(tree);
		
		SubTree fullTree = new SubTree(treeNode);
		fullTree.setStyle("-fx-background-color: #f5e5ae;");
		
		treeGroup.getChildren().add(fullTree);
		
		HBox title = getTitlePane();
		displayPane = getInfoPane(null,"");
		
		
		
		this.setTop(title);
		
		this.setCenter(treeGroup);
		this.setRight(displayPane);
		this.setPadding(new Insets(10,10,10,10));
		this.setStyle("-fx-background-color: #b38b54;");
	
		BorderPane.setAlignment(treeGroup,Pos.TOP_CENTER);
		setMargin(treeGroup,new Insets(10,0,0,0));
		setMargin(displayPane,new Insets(10,10,10,10));

		
	}
	
	/**
	 * Create a title pane for the GUI
	 * @return - Container for title
	 */
	public HBox getTitlePane() {
		
		Label titleLabel = new Label();
		titleLabel.setText("Zion's Family Tree");
		titleLabel.setStyle("-fx-font: 60 arial;");
		titleLabel.setTextFill(Color.web("#05b030"));
		
		HBox titleBox = new HBox();
		titleBox.getChildren().add(titleLabel);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setStyle("-fx-background-color: #e9edbe;");
		titleBox.setPrefHeight(USE_COMPUTED_SIZE + 100);
		
		
		return titleBox;
	}
	
	/**
	 * Constructs a container for a person's
	 * information by displaying a picture of the
	 * person (if one exists) and the person's 
	 * string representation
	 * 
	 * To-Do
	 * - Implement image display
	 * @param image - picture of person
	 * @param description - description of person
	 * @return - Container for the person's info
	 */
	public VBox getInfoPane(Image image, String description) {
		VBox infoBox = new VBox(10);
		infoBox.setStyle("-fx-background-color:#ecedd8;");
		infoBox.setPadding(new Insets(45, 20, 20, 20));
		infoBox.setAlignment(Pos.TOP_CENTER);
		
		Label welcomeText = new Label("Hover over name for info!");
		welcomeText.setStyle("-fx-font: 30 arial");
		welcomeText.setTextFill(Color.web("#05b030"));
		
		infoBox.getChildren().add(welcomeText);
		
		
		
		
			
		TextArea infoTextArea = new TextArea();
		infoTextArea.setWrapText(true);
		infoTextArea.setText(description);
		infoTextArea.setEditable(false);
		infoBox.getChildren().add(infoTextArea);
		
		if(image != null) {
			ImageView imageView = new ImageView();
			imageView.setFitHeight(300); 
			imageView.setFitWidth(250);
			imageView.setImage(image); 
			infoBox.getChildren().add(imageView);
		}
		
		return infoBox;
	}
	
	
	/**
	 * SubTree is a recursive class that recursively creates a graphical
	 * representation of a full tree, by combining it's sub trees
	 * @author zionchilagan
	 *
	 */
	private class SubTree extends StackPane {
		/** TreeNode that holds each root */
		private TreeNode treeRoot;
		/** Number of children of a tree */
		private int numChildren;
		/** Group to hold full tree */
		private Group group;
		/** Tree's branches */
		private Line stem,childLine,childStem;
		/** List of lines, which represent a tree's branch */
		private ArrayList<Line> lines = new ArrayList<Line>();
		private ArrayList<SubTree> subTrees = new ArrayList<SubTree>();
		/**
		 * Constructs a SubTree with a given root
		 * @param treeRoot - Graphical representation of a tree root
		 */
		public SubTree(TreeNode treeRoot) {
			
			this.treeRoot = treeRoot;
			this.group = new Group();
			numChildren = treeRoot.getTree().getRoot().getChildren().size();
			
			setListeners();
			group.getChildren().add(treeRoot);
			drawConnections();
			getChildren().add(group);
			
			
			
		}
		
		/** Helper method to set listeners for TreeNode */
		private void setListeners() {
			treeRoot.setOnMouseEntered(entered);
			treeRoot.setOnMouseExited(exited);
		}
		
		/**
		 * Draws the lines (branches) for the tree
		 * 
		 * To-Do
		 * - Compute better algorithm to compute the branch length of a sub tree
		 */
		private void drawConnections() {
			
			int xCoord = ((int) treeRoot.getLayoutX() + ((int)treeRoot.getLayoutX() +(int)treeRoot.getTreeRectangle().getWidth()))/2;
			int yCoord = ((int) treeRoot.getLayoutY()  + (int)treeRoot.getTreeRectangle().getHeight()) + 3;
			int rectHeight = (int)treeRoot.getTreeRectangle().getHeight();
			
			
			
			int childScale = calculateChildScale();
			stem = new Line(xCoord, yCoord, xCoord ,yCoord + rectHeight );
			childLine = new Line(stem.getEndX() - childScale/3, stem.getEndY(), 
					stem.getEndX() + childScale/3, stem.getEndY());
		
			group.getChildren().addAll(stem,childLine);
			
		
			
			drawChildStems(childLine);
			lines.add(stem);
			lines.add(childLine);
			for(Line l: lines) {
				l.setStrokeWidth(5);
				l.setStroke(Color.web("#d67104"));
			}
		
		}
		
		/**
		 * Helper method to calculate how long a branch line should be
		 * based on a the amount of grandchildren a root has
		 * @return - Scale for branch line
		 */
		private int calculateChildScale() {
			int spacing = 10;
			
			int initial = ((int) treeRoot.getTreeRectangle().getWidth() + spacing);
			if(treeRoot.getTree().isLeaf()) {
				return 0;
			}
			else { 
				for(int i = 0; i < numChildren; i++) {
					if(!(treeRoot.getTree().getSubTree(i).isLeaf())) {
						return calculateTotalGrandChild() * initial + 90;
					}
				}
				return numChildren * ((int) treeRoot.getTreeRectangle().getWidth() );
			}
			
		}
		
		/**
		 * Helper method to traverse a tree and count how many 
		 * grand kids a person has
		 * @return number of grand kids
		 */
		private int calculateTotalGrandChild() {
			int grandChildCount = 0;
				
				for(int i = 0; i < numChildren; i++) {
			
					grandChildCount+=treeRoot.getTree().getSubTree(i).getRoot().getChildren().size();
					
				}	
				return grandChildCount;
		}
		
		
		/**
		 * Helper method to draw stems for each child, this is the method
		 * that creates new SubTree objects for each child stem
		 * @param line - Child line to draw child stem and new sub tree from
		 */
		private void drawChildStems(Line line) {
			int length = (int)(line.getEndX() - line.getStartX());
			int spacing = 0;
			if(numChildren > 1)
				spacing = length/(numChildren-1);
			int startX = (int)line.getStartX();
			for(int i = 0; i < numChildren; i++) {
				childStem = new Line(startX,line.getStartY(),startX,line.getStartY()+40);
				int childX = startX - (int)(treeRoot.getTreeRectangle().getWidth()/2);
				int childY = (int)childStem.getEndY();
				
				TreeNode extra = new TreeNode(treeRoot.getTree().getSubTree(i),childX,childY);
				SubTree extraTree = new SubTree(extra);
				subTrees.add(extraTree);
				group.getChildren().addAll(childStem,extraTree.getGroup());
				
				
				
				lines.add(childStem);
				startX+=spacing;
				
				
				
			}
			
		}
		
		/**
		 * Get group that contains sub tree
		 * @return
		 */
		public Group getGroup() {
			return group;
		}
		
		/**
		 * Method to highlight current tree root as well as it's children
		 */
		private void highlight() {
			treeRoot.getTreeRectangle().setStroke(Color.YELLOW);
			treeRoot.setEffect(new InnerShadow(15,Color.YELLOW));
			for(Line l: lines) {
				l.setStroke(Color.YELLOW);
			}
			
		}
		
		private void unhighlight() {
			
			treeRoot.getTreeRectangle().setStroke(Color.GREEN);
			treeRoot.setEffect(null);
			for(Line l: lines) {
				l.setStroke(Color.web("#d67104"));
			}
		}
		
		/**
		 * Method that defines when a user hovers mouse over
		 * a tree node, the tree node is highlighted yellow, as
		 * well as it's branches
		 */
		private EventHandler<MouseEvent> entered = mouseEvent -> {
			TreeNode treeNode = (TreeNode)mouseEvent.getSource();
			SubTree tree = this;
			tree.highlight();
			for(SubTree t: subTrees) {
				t.highlight();
			}
			displayPane = getInfoPane(treeNode.getTree().getRoot().getData().getImage(),treeNode.toString());
			TreeGUI.this.setRight(displayPane);
			TreeGUI.setMargin(displayPane,new Insets(10,10,10,10));
		};
		
		/**
		 * Handles event when user exits the tree node, which turns
		 * the Tree Node's color back to original as well as it's 
		 * branches
		 */
		private EventHandler<MouseEvent> exited = mouseEvent -> {
			SubTree tree = this;
			tree.unhighlight();
			for(SubTree t: subTrees) {
				t.unhighlight();
			}
			
		}; 
		
		
		/**
		 * Overridden method to display tree as well
		 * as it's sub trees
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Root: " + treeRoot.getTree().getRoot().getData().getName());
			sb.append("Children: ");
			for(SubTree t: subTrees) {
				sb.append(t);
				sb.append("\n");
			}
			return sb.toString();
		}
		
		/**
		 * Get the sub trees for parent
		 * @return - Parent tree's sub tree(s)
		 */
		public ArrayList<SubTree> getSubTrees() {
			return subTrees;
		}
		
		
	}
		
		
	/**
	 * Inner class that turns a tree's root
	 * into a graphical representation using a rectangle and
	 * label
	 * @author zionchilagan
	 *
	 */
	private class TreeNode extends StackPane {
		/** Rectangle to contain the name */
		private Rectangle treeRectangle;
		/** Tree to represent */
		private Tree<Person> tree;
		
		/**
		 * Constructs a Node with a root
		 * @param tree - Tree whose root will be represented
		 */
		private TreeNode(Tree<Person> tree) {
			this.tree = tree;
			treeRectangle = new Rectangle(80,40);
			treeRectangle.setFill(Color.web("#e1f7da"));
			treeRectangle.setStroke(Color.GREEN);
			treeRectangle.setEffect(new InnerShadow(15,Color.web("#e1f7da")));
			treeRectangle.setStrokeWidth(2);
			String name = this.tree.getRoot().getData().getName();
			Text treeName = new Text(name);
			getChildren().addAll(treeRectangle,treeName);
		}
		
		/**
		 * Constructs a Node with a root, in specific x,y coordinate
		 * @param tree - Tree whose root will be represented
		 * @param x - x coordinate for the tree node
		 * @param y - y coordinate for the tree node
		 */
		private TreeNode(Tree<Person> tree, int x, int y) {
			
			this.setLayoutX(x);
			this.setLayoutY(y);
			this.tree = tree;
			treeRectangle = new Rectangle(80,40);
			treeRectangle.setFill(Color.web("#e1f7da"));
			treeRectangle.setStroke(Color.GREEN);
			treeRectangle.setEffect(new InnerShadow(15,Color.web("#e1f7da")));
			treeRectangle.setStrokeWidth(2);
			String name = this.tree.getRoot().getData().getName();
			Text treeName = new Text(name);
			getChildren().addAll(treeRectangle,treeName);
		}
		
		/**
		 * Get the tree to represent 
		 * @return - tree that is being represented
		 */
		public Tree<Person> getTree() {
			return tree;
		}
		
		/**
		 * Get the rectangle containing the root
		 * @return - Rectangle for the tree root
		 */
		public Rectangle getTreeRectangle() {
			return treeRectangle;
		}
		
		/**
		 * Get the string representation for the root
		 */
		public String toString() {
			return tree.getRoot().toString();
		}

		
	}
}

