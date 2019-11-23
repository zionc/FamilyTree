package treeAssignment;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TreeGUI extends BorderPane {
	private Group treeGroup;
	public TreeGUI(Tree<Person> tree) {
		treeGroup = new Group();
		TreeNode treeNode = new TreeNode(tree);
		
		SubTree fullTree = new SubTree(treeNode);
		
		//fullTree.drawConnections();
		treeGroup.getChildren().add(fullTree);
		
		HBox title = getTitlePane();
		/*TreeNode treeNode2 = new TreeNode(tree);
		SubTree tester2 = new SubTree(treeNode2);
		Group group2 = new Group(tester2.getGroup()); */
		
		//tester.getChildren().add(new TreeNode(tree,90,100));
		
		
		this.setTop(title);
		
		this.setCenter(treeGroup);
		this.setPadding(new Insets(10,10,10,10));
		this.setStyle("-fx-background-color: #b38b54;");
		//this.setRight(group2);
		BorderPane.setAlignment(treeGroup,Pos.TOP_CENTER);
		setMargin(treeGroup,new Insets(10,0,0,0));
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
	
	
	
	
	private class SubTree extends Pane {
		private TreeNode treeRoot;
		private int numChildren;
		private Group group;
		public SubTree(TreeNode treeRoot) {
			
			this.treeRoot = treeRoot;
			numChildren = treeRoot.getTree().getRoot().getChildren().size();
			//System.out.println(numChildren);
			this.group = new Group();
			//group.getChildren().add(treeRoot);
			//getChildren().add(this.group);
			
			getChildren().add(group);
			
			drawConnections();
			//this.setBackground(value);
			
			//drawConnections();
			
			
		}
		public SubTree(TreeNode treeRoot, int x, int y) {
			
			this.treeRoot = treeRoot;
			setLayoutX(x);
			setLayoutY(y);
			numChildren = treeRoot.getTree().getRoot().getChildren().size();
			//System.out.println(numChildren);
			this.group = new Group();
			//group.getChildren().add(treeRoot);
			getChildren().add(group);
			
			
			
			drawConnections();
			//this.setBackground(value);
			
			//drawConnections();
			
			
		}
		
		
		
		public TreeNode getTreeRoot() {
			return treeRoot;
		}
		
		private void drawConnections() {
			ArrayList<Line> lines = new ArrayList<Line>();
			int xCoord = ((int) treeRoot.getLayoutX() + ((int)treeRoot.getLayoutX() +(int)treeRoot.getTreeRectangle().getWidth()))/2;
			int yCoord = ((int) treeRoot.getLayoutY()  + (int)treeRoot.getTreeRectangle().getHeight()) + 3;
			
			int spacing = 10;
			
			//int childScale = (numChildren * ((int) treeRoot.getTreeRectangle().getWidth() + spacing));
			int childScale = calculateChildScale();
			System.out.println("Grand children: " +this.calculateTotalGrandChild());
			Line stem = new Line(xCoord, yCoord, xCoord ,yCoord+40);
			Line childLine = new Line(stem.getEndX() - childScale/3, stem.getEndY(), 
					stem.getEndX() + childScale/3, stem.getEndY());
			
			group.getChildren().addAll(treeRoot,stem,childLine);
			//getChildren().addAll(stem,childLine);
			
			drawChildStems(childLine,lines);
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
		
		
		
		private void drawChildStems(Line line,ArrayList<Line> lines) {
			int length = (int)(line.getEndX() - line.getStartX());
			int spacing = 0;
			if(numChildren > 1)
				spacing = length/(numChildren-1);
			int startX = (int)line.getStartX();
			for(int i = 0; i < numChildren; i++) {
				Line childStem = new Line(startX,line.getStartY(),startX,line.getStartY()+40);
				int childX = startX - (int)(treeRoot.getTreeRectangle().getWidth()/2);
				int childY = (int)childStem.getEndY();
				
				TreeNode extra = new TreeNode(treeRoot.getTree().getSubTree(i));
				SubTree extraTree = new SubTree(extra,childX,childY);
				group.getChildren().addAll(childStem,extraTree);
				//getChildren().addAll(childStem, extraTree);
				//extraTree.drawConnections();
				//extraTree.drawConnections();
				//getChildren().add(childStem);
				//getChildren().add(new SubTree(extra));
				
				
				lines.add(childStem);
				startX+=spacing;
				//group.getChildren().addAll(childStem,tree);
				//getChildren().addAll(childStem,tree);
				
				
			}
			
		}
		
		
		
	}
		
		
	
	private class TreeNode extends StackPane {
		private int x;
		private int y;
		private Rectangle treeRectangle;
		private Tree<Person> tree;
		private TreeNode(Tree<Person> tree) {
			this.tree = tree;
			treeRectangle = new Rectangle(80,40);
			treeRectangle.setFill(Color.WHITE);
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
			treeRectangle.setFill(Color.WHITE);
			treeRectangle.setStroke(Color.GREEN);
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

