package cz.zcu.kiv.lipka.uur.exercise08;

import cz.zcu.kiv.lipka.uur.exercise08.Ancestor.Sex;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * Demonstration of using TreeView, showing the ancestors lineage
 * 
 * @author Richard Lipka
 *
 */
public class TreeDemo extends Application {
	// tree view, provide acces to the view and to selected items
	private TreeView<Ancestor> ancestorTRV;
	// text area, updated when selection of the tree view is changed
	private TextArea detailsTA;
	// textField and ChoiceBox for adding new ancestor to the list
	private TextField nameTF;
	private ChoiceBox<Ancestor.Sex> sexCHB;

	public static void main(String[] args) {
		launch(args);
	}

	// creates primary stage
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("TreeView demonstration");
		primaryStage.setScene(createScene());
		// sets minimal size of the window
		primaryStage.setMinWidth(590);
		primaryStage.setMinHeight(200);
		primaryStage.show();
	}
	
	// creates scene
	private Scene createScene() {
		Scene scene = new Scene(getRoot(), 600, 500);		
		return scene;
	}
	
	// creating root panel, based on BorderPane
	private Parent getRoot() {
		BorderPane rootPane = new BorderPane();
		
		// visualization of the tree will be in the center
		rootPane.setCenter(getTree());
		// control panel will be at the bottom
		rootPane.setBottom(getControlPane());
		// text area with information about selected node is on the right side
		rootPane.setRight(getDetailsPane());
		
		return rootPane;
	}
	
	// creates tree
	private Node getTree() {
		ancestorTRV = new TreeView<Ancestor>();
		
		// setting up factory that provides cells 
		ancestorTRV.setCellFactory(treeView -> {
			// creates AncestorDisplayCell for each element of the tree
			return new AncestorDisplayCell();
		});
		// allows editation of the tree
		ancestorTRV.setEditable(true);
		// creates reaction on the commit
		ancestorTRV.setOnEditCommit(event -> {
			// when element is changed, the collection where it belongs (its siblings)
			// is sorted
			// propagation of new value to the model is provided directly in the cell			
			sortChildren(event.getTreeItem().getParent());
			// change of node could cause change of selected node without changing selection
			// --> description have to be updated
			detailsTA.setText(createDescription());
		});
		// creates reaction on the change of selection
		// listens on the collection of selected items - when content of this
		// collection is changed, event is fired
		ancestorTRV.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends TreeItem<Ancestor>> change) -> {
			// when selection is changed, text area receives a new text
			detailsTA.setText(createDescription());
		});
		
		ancestorTRV.setMinWidth(300);
		
		
		// setting margin around tree view
		BorderPane.setMargin(ancestorTRV, new Insets(5));		
		
		// setting root element to the tree
		ancestorTRV.setRoot(new TreeItem<Ancestor>(new Ancestor("Eve", Sex.FEMALE)));
		// creates default list of the ancestors
		createDefaultChildren(ancestorTRV.getRoot());
		
		return ancestorTRV;
	}
	
	// creates control panel
	private Node getControlPane() {
		GridPane controlPane = new GridPane();
		
		Label nameLB = new Label("Name:");
		Label sexLB = new Label("Sex:");
		
		nameTF = new TextField();
		nameTF.setPrefColumnCount(30);
		// setting both sexes to choiceBox
		sexCHB = new ChoiceBox<Ancestor.Sex>(FXCollections.observableArrayList(Ancestor.Sex.values()));
		
		// creates buttons and reactions - adding and removing of selected ancestor
		Button addBT = new Button("Add");
		addBT.setPrefWidth(100);
		addBT.setOnAction(event -> addAncestor());
		
		Button removeBT = new Button("Remove");
		removeBT.setPrefWidth(100);
		removeBT.setOnAction(event -> removeAncestor());

		// creates grid in gridPane, setting positions of each element
		controlPane.add(nameLB, 0, 0);
		controlPane.add(nameTF, 0, 1);
		controlPane.add(sexLB, 1, 0);
		controlPane.add(sexCHB, 1, 1);
		controlPane.add(removeBT, 4, 0);
		controlPane.add(addBT, 4, 1);
		
		// setting gaps bewteen elements in the grid
		controlPane.setHgap(3);
		controlPane.setVgap(3);
		
		// setting position and padding in the parent element
		controlPane.setPadding(new Insets(5));
		controlPane.setAlignment(Pos.CENTER);
		
		return controlPane;
	}
	
	// creates panel with textArea containing description of selected element	
	private Node getDetailsPane() {		
		detailsTA = new TextArea();
		
		// sets dimension of the text area
		detailsTA.setPrefColumnCount(20);
		// disabling editation of the text area - text si changed only automatically
		detailsTA.setEditable(false);
		
		BorderPane.setMargin(detailsTA, new Insets(5));
		
		return detailsTA;
	}
	
	/*
	 * -----------------------------------------------------------------------------
	 * 
	 * End of GUI definition, methods for event processing follows
	 * 
	 * -----------------------------------------------------------------------------
	 */
	
	// cretaes default values for the tree, on several levels
	private void createDefaultChildren(TreeItem<Ancestor> parent) {
		TreeItem<Ancestor> sethItem = new TreeItem<Ancestor>(new Ancestor("Seth", Sex.MALE));
		TreeItem<Ancestor> cainItem = new TreeItem<Ancestor>(new Ancestor("Enoch", Sex.MALE));
		TreeItem<Ancestor> abelItem = new TreeItem<Ancestor>(new Ancestor("Abel", Sex.MALE));
		
		// I know it is not correct, but for the sake of showing enum :-)
		TreeItem<Ancestor> enochItem = new TreeItem<Ancestor>(new Ancestor("Enoch", Sex.NEUTRAL));
		TreeItem<Ancestor> enosItem = new TreeItem<Ancestor>(new Ancestor("Enos", Sex.MALE));		

		
		cainItem.getChildren().add(enochItem);
		sethItem.getChildren().add(enosItem);
		
		parent.getChildren().addAll(abelItem, cainItem, sethItem);
		// when elements are added to the collection, collection is sorted
		sortChildren(parent);		
	}
	
	// creates part of one line of the description
	// combines name and gender symbol
	private String getDescLine(Ancestor ancestor) {
		return ancestor.getName() + " (" + ancestor.getSex().getSymbol() + ")";
	}
	
	// creates description for the text area
	private String createDescription() {
		// obtaining current selection
		TreeItem<Ancestor> selected = ancestorTRV.getSelectionModel().getSelectedItem();
		
		// when nothing is selected, nothing is displayed
		if (selected == null) {
			return "Nothing is selected";
		// when something is selected	
		} else {
			// obtaining name of the selected element; this string will be
			// several times longer, we will add new lines for each ancestor
			// in the path, so it is better to use StringBuilder to avoid
			// creating and destroying multiple strings
			StringBuilder result = new StringBuilder("Name: " + getDescLine(selected.getValue()) + "\n");
			
			// obtaining parent of the selected element
			TreeItem<Ancestor> parent = selected.getParent();			
			
			// when parent exists 
			if (parent != null) {
				// creates father/mother line 
				result.append(parent.getValue().getSex().getParentWord() + ": " + getDescLine(parent.getValue()) + "\n");
			// when parent don't exists, selected element is root and creating of text ends
			} else {				
				return result.toString();
			}
			
			// obtaining parent of parent (grandparent)
			parent = parent.getParent();
			String grand = "grand";
			// when grandparent exists
			while (parent != null) {	
				// creates line for the grandparent or great-grandparent
				result.append(grand + parent.getValue().getSex().getParentWord() + ": " + getDescLine(parent.getValue()) + "\n");
				// accumluator for great- - allows to create line for inlimited ammount of ancestors
				grand = "great-" + grand;
				// obtaining parent of current parent
				parent = parent.getParent();
			}
			return result.toString();					
		}
	}	
	
	// sorting children of selected parent
	private void sortChildren(TreeItem<Ancestor> parent) {
		// when parent is really provided
		if (parent != null) {
			// cretates comparator of two children
			parent.getChildren().sort((item1, item2) -> {
				// comparator delegates the comparing on children
				// children are Ancestors, so we know that they implements
				// Comparable interface
				return item1.getValue().compareTo(item2.getValue());
			});
		}
	}
	
	// removing selected ancestor
	private void removeAncestor() {
		TreeItem<Ancestor> selected = ancestorTRV.getSelectionModel().getSelectedItem();
		
		// if nothing is selected, warning is displayed
		if (selected == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Deleting error");
			alert.setHeaderText("Nothing is selected!");
			alert.setContentText("Please select node to delete.");
			// shows warning
			alert.showAndWait();
		// when something is selected	
		} else {
			// obtaining parent of selected item
			TreeItem<Ancestor> parent = selected.getParent();
			
			// root cannot be deleted - if parent is null, nothing will be deleted 
			if (parent == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Deleting error");
				alert.setHeaderText("Selected item is Root!");				
				alert.setContentText("It is not possible to delete " + selected.getValue().getName() + ", " + selected.getValue().getSex().getPronoun() + " is the first human");
				alert.showAndWait();				
			// when selected element is not root
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION); 
				alert.setTitle("Deleting");
				alert.setHeaderText("Do you want to delete selected ancestor and all " + selected.getValue().getSex().getPossesive() + " offsprings?");				
				alert.setContentText("Selected: " + selected.getValue().getName());
				// asking if the selected element should be really removed, along with its offsprings
				alert.showAndWait()
				 .filter(response -> response == ButtonType.OK)
				 .ifPresent(response -> {
					 // removing selected element from its parent
					 parent.getChildren().remove(selected);
					 // clearing selection - after deleting nothing will be selected
					 // this causes change of selection, so there is no need to manually update text area
					 // removing don't change the order of remaining children, so no additional sorting is needed 
					 ancestorTRV.getSelectionModel().clearSelection();
			 });				
			}
		}
	}

	// adding new ancestor to the tree, as a children of selected node
	private void addAncestor() {
		// obtaining selected tree node
		TreeItem<Ancestor> selected = ancestorTRV.getSelectionModel().getSelectedItem();
		
		// if nothing is selected
		if (selected == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Insertion error");
			alert.setHeaderText("Nothing is selected!");
			alert.setContentText("Please select node to add new offspring.");
			// and alert is displayed
			alert.showAndWait();
		// if some node is selected as an ancestor	
		} else {
			String name = nameTF.getText();
			Sex sex = sexCHB.getValue();
		
			// checking if name is provided
			if (name.length() == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Insertion error");
				alert.setHeaderText("No name is provided!");
				alert.setContentText("Please provide a name for a new offspring.");
				// when no name is provided, adding cannot continue
				alert.showAndWait();				
			} else
			// checking if sex is provided	
			if (sex == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Insertion error");
				alert.setHeaderText("No sex is provided!");
				alert.setContentText("Please provide a sex of a new offspring.");
				// when sex is not provided, adding cannot continue
				alert.showAndWait();
			// when everything is provided	
			} else {			
				// new ancestor is created
				Ancestor newAncestor = new Ancestor(name, sex);
				// ancestor is added as a child to selected node
				selected.getChildren().add(new TreeItem<Ancestor>(newAncestor));
				// all siblings of added element are sorted
				sortChildren(selected);
				// selected node (parent of the new Ancestor) is expanded
			    // ensures that newly added node will be visible 
				selected.setExpanded(true);				
			}
		}
	}
}
