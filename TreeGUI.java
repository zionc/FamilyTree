package treeAssignment;

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
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TreeGUI extends BorderPane {
	private Group treeGroup;
	private VBox displayPane;
	public TreeGUI(Tree<Person> tree) {
		treeGroup = new Group();
		TreeNode treeNode = new TreeNode(tree);
		
		SubTree fullTree = new SubTree(treeNode);
		fullTree.setStyle("-fx-background-color: #f5e5ae;");
		//fullTree.drawConnections();
		treeGroup.getChildren().add(fullTree);
		
		HBox title = getTitlePane();
		displayPane = getInfoPane(null,"");
		/*TreeNode treeNode2 = new TreeNode(tree);
		SubTree tester2 = new SubTree(treeNode2);
		Group group2 = new Group(tester2.getGroup()); */
		
		//tester.getChildren().add(new TreeNode(tree,90,100));
		
		
		this.setTop(title);
		
		this.setCenter(treeGroup);
		this.setRight(displayPane);
		this.setPadding(new Insets(10,10,10,10));
		this.setStyle("-fx-background-color: #b38b54;");
		//this.setRight(group2);
		BorderPane.setAlignment(treeGroup,Pos.TOP_CENTER);
		setMargin(treeGroup,new Insets(10,0,0,0));
		setMargin(displayPane,new Insets(10,10,10,10));
		//setAlignment(group2,Pos.TOP_CENTER);
		
	}
	
	
	
	public HBox getTitlePane() {
		
		Label titleLabel = new Label();
		titleLabel.setText("Zion's Family");
		titleLabel.setStyle("-fx-font: 60 arial;");
		titleLabel.setTextFill(Color.web("#05b030"));
		
		HBox titleBox = new HBox();
		titleBox.getChildren().add(titleLabel);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setStyle("-fx-background-color: #e9edbe;");
		titleBox.setPrefHeight(USE_COMPUTED_SIZE + 100);
		
		
		return titleBox;
	}
	
	public VBox getInfoPane(Image image, String description) {
		VBox infoBox = new VBox(10);
		infoBox.setStyle("-fx-background-color:#ecedd8;");
		infoBox.setPadding(new Insets(45, 20, 20, 20));
		infoBox.setAlignment(Pos.TOP_CENTER);
		
		Label welcomeText = new Label("Hover over name for info!");
		welcomeText.setStyle("-fx-font: 30 arial");
		welcomeText.setTextFill(Color.web("#05b030"));
		
		infoBox.getChildren().add(welcomeText);
		
		if(image != null) {
			ImageView imageView = new ImageView();
			imageView.setFitHeight(image.getHeight()); 
			imageView.setFitWidth(image.getWidth());
			imageView.setImage(image); 
			infoBox.getChildren().add(imageView);
		}
		
		
			
			TextArea infoTextArea = new TextArea();
			infoTextArea.setWrapText(true);
			infoTextArea.setText(description);
			infoTextArea.setEditable(false);
			infoBox.getChildren().add(infoTextArea);
			
		
		
		
		
		return infoBox;
	}
	
	
	
	private class SubTree extends StackPane {
		private TreeNode treeRoot;
		private int numChildren;
		private Group group;
		private Line stem,childLine,childStem;
		private ArrayList<Line> lines = new ArrayList<Line>();
		public SubTree(TreeNode treeRoot) {
			
			this.treeRoot = treeRoot;
			setListeners();
			numChildren = treeRoot.getTree().getRoot().getChildren().size();
			//System.out.println(numChildren);
			this.group = new Group();
			group.getChildren().add(treeRoot);
			//addEventFilter(MouseEvent.MOUSE_ENTERED, entered);
			//addEventFilter(MouseEvent.MOUSE_EXITED, exited);
			
			drawConnections();
			getChildren().add(group);
			
			
			
		}
		
		private void setListeners() {
			treeRoot.setOnMouseEntered(entered);
			treeRoot.setOnMouseExited(exited);
		}
		
		
		
		
		public TreeNode getTreeRoot() {
			return treeRoot;
		}
		
		private void drawConnections() {
			
			int xCoord = ((int) treeRoot.getLayoutX() + ((int)treeRoot.getLayoutX() +(int)treeRoot.getTreeRectangle().getWidth()))/2;
			int yCoord = ((int) treeRoot.getLayoutY()  + (int)treeRoot.getTreeRectangle().getHeight()) + 3;
			int rectHeight = (int)treeRoot.getTreeRectangle().getHeight();
			
			
			
			int childScale = calculateChildScale();
			System.out.println("Grand children: " +this.calculateTotalGrandChild());
			stem = new Line(xCoord, yCoord, xCoord ,yCoord + rectHeight );
			childLine = new Line(stem.getEndX() - childScale/3, stem.getEndY(), 
					stem.getEndX() + childScale/3, stem.getEndY());
		
			group.getChildren().addAll(stem,childLine);
			
			//getChildren().addAll(stem,childLine);
			
			drawChildStems(childLine);
			lines.add(stem);
			lines.add(childLine);
			for(Line l: lines) {
				l.setStrokeWidth(5);
				l.setStroke(Color.BROWN);
			}
		
		}
		
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
		
		private int calculateTotalGrandChild() {
			int grandChildCount = 0;
				for(int i = 0; i < treeRoot.getTree().getRoot().getChildren().size(); i++) {
			
					grandChildCount+=treeRoot.getTree().getSubTree(i).getRoot().getChildren().size();
					
				}	
				return grandChildCount;
		}
		
		
		
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
				group.getChildren().addAll(childStem,extraTree.getGroup());
				
				
				
				lines.add(childStem);
				startX+=spacing;
				
				
				
			}
			
		}
		
		public Group getGroup() {
			return group;
		}
		
		
		
		private EventHandler<MouseEvent> entered = mouseEvent -> {
			TreeNode treeNode = (TreeNode) mouseEvent.getSource();
			System.out.println(treeNode);
			this.treeRoot = treeNode;
			treeNode.getTreeRectangle().setStroke(Color.YELLOW);
			treeNode.setEffect(new InnerShadow(10,Color.YELLOW));
			for(Line l: lines) {
				l.setStroke(Color.YELLOW);
			}
			displayPane = getInfoPane(null,treeNode.getTree().getRoot().toString());
			TreeGUI.this.setRight(displayPane);
			TreeGUI.setMargin(displayPane,new Insets(10,10,10,10));
			
		};
		
		//Handles event when user drags mouse away from circle
		private EventHandler<MouseEvent> exited = mouseEvent -> {
			TreeNode treeNode = (TreeNode)mouseEvent.getSource();
			treeNode.getTreeRectangle().setStroke(Color.GREEN);
			treeNode.setEffect(null);
			for(Line l: lines) {
				l.setStroke(Color.BROWN);
			}
			
		}; 
		
		
	}
		
		
	
	private class TreeNode extends StackPane {
		private int x;
		private int y;
		private Rectangle treeRectangle;
		private Tree<Person> tree;
		private TreeNode(Tree<Person> tree) {
			this.tree = tree;
			treeRectangle = new Rectangle(80,40);
			treeRectangle.setFill(Color.web("#e1f7da"));
			treeRectangle.setStroke(Color.GREEN);
			String name = this.tree.getRoot().getData().getName();
			Text treeName = new Text(name);
			getChildren().addAll(treeRectangle,treeName);
		}
		
		private TreeNode(Tree<Person> tree, int x, int y) {
			//this.x = x;
			//this.y = y;
			this.setLayoutX(x);
			this.setLayoutY(y);
			this.tree = tree;
			treeRectangle = new Rectangle(80,40);
			treeRectangle.setFill(Color.web("#e1f7da"));
			treeRectangle.setStroke(Color.GREEN);
			treeRectangle.setStrokeWidth(2);
			String name = this.tree.getRoot().getData().getName();
			Text treeName = new Text(name);
			getChildren().addAll(treeRectangle,treeName);
		}
		
		public boolean isLeaf() {
			return tree.isLeaf();
		}
		
		
		public Tree<Person> getTree() {
			return tree;
		}
		
		public Rectangle getTreeRectangle() {
			return treeRectangle;
		}
		
		public String toString() {
			return tree.getRoot().toString();
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
}

