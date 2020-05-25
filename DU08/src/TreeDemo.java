import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

import java.util.Objects;

/**
 * 
 * Demonstration of using TreeView
 * 
 * @author Jan Pelikán
 *
 */
public class TreeDemo extends Application {
	private TreeView<Datamodel> datamodelTRV;
	// text area, updated when selection of the tree view is changed
	private TextArea detailsTA;
	// textField and ChoiceBox for adding new item to the list
	private TextField nameTF;
	private ChoiceBox<Typ> typCHB;

	public static void main(String[] args) {
		launch(args);
	}

	// creates primary stage
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("TreeView");
		primaryStage.setScene(createScene());
		// sets minimal size of the window
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(300);
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
		datamodelTRV = new TreeView<Datamodel>();
		
		// setting up factory that provides cells 
		datamodelTRV.setCellFactory(treeView -> {
			// creates AncestorDisplayCell for each element of the tree
			return new DatamodelDisplayCell();
		});
		// allows editation of the tree
		datamodelTRV.setEditable(true);
		// creates reaction on the commit
		datamodelTRV.setOnEditCommit(event -> {
			// when element is changed, the collection where it belongs (its siblings)
			// is sorted
			// propagation of new value to the model is provided directly in the cell			
			sortItems(event.getTreeItem().getParent());
			// change of node could cause change of selected node without changing selection
			// --> description have to be updated
			detailsTA.setText(createDescription());
		});
		// creates reaction on the change of selection
		// listens on the collection of selected items - when content of this
		// collection is changed, event is fired
		datamodelTRV.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends TreeItem<Datamodel>> change) -> {
			// when selection is changed, text area receives a new text
			detailsTA.setText(createDescription());
		});
		
		datamodelTRV.setMinWidth(300);

		// setting margin around tree view
		BorderPane.setMargin(datamodelTRV, new Insets(5));
		
		// setting root element to the tree
		datamodelTRV.setRoot(new TreeItem<Datamodel>(new Datamodel("C:", Typ.ADRESAR)));
		// creates default list of items
		createDefaultChildren(datamodelTRV.getRoot());
		
		return datamodelTRV;
	}
	
	// creates control panel
	private Node getControlPane() {
		GridPane controlPane = new GridPane();
		
		Label nameLB = new Label("Name:");
		Label typLB = new Label("Typ:");
		
		nameTF = new TextField();
		nameTF.setPrefColumnCount(30);
		// setting both types to choiceBox
		typCHB = new ChoiceBox<Typ>(FXCollections.observableArrayList(Typ.values()));
		
		// creates buttons and reactions - adding and removing of selected item
		Button addBT = new Button("Add");
		addBT.setPrefWidth(100);
		addBT.setOnAction(event -> addAncestor());
		
		Button removeBT = new Button("Remove");
		removeBT.setPrefWidth(100);
		removeBT.setOnAction(event -> removeItem());

		// creates grid in gridPane, setting positions of each element
		controlPane.add(nameLB, 0, 0);
		controlPane.add(nameTF, 0, 1);
		controlPane.add(typLB, 1, 0);
		controlPane.add(typCHB, 1, 1);
		controlPane.add(removeBT, 4, 0);
		controlPane.add(addBT, 4, 1);

		controlPane.setHgap(3);
		controlPane.setVgap(3);

		controlPane.setPadding(new Insets(5));
		controlPane.setAlignment(Pos.CENTER);
		
		return controlPane;
	}

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
	private void createDefaultChildren(TreeItem<Datamodel> parent) {
		TreeItem<Datamodel> usersItem = new TreeItem<>(new Datamodel("Users", Typ.ADRESAR));
		TreeItem<Datamodel> programFilesItem = new TreeItem<>(new Datamodel("ProgramFiles", Typ.ADRESAR));
		TreeItem<Datamodel> myProfileItem = new TreeItem<>(new Datamodel("MyProfile", Typ.ADRESAR));
		TreeItem<Datamodel> desktopItem = new TreeItem<>(new Datamodel("Desktop", Typ.ADRESAR));
		TreeItem<Datamodel> googleItem = new TreeItem<>(new Datamodel("Google", Typ.SOUBOR));


		myProfileItem.getChildren().add(desktopItem);
		usersItem.getChildren().add(myProfileItem);
		programFilesItem.getChildren().add(googleItem);
		
		parent.getChildren().addAll(usersItem, programFilesItem);
		// when elements are added to the collection, collection is sorted
		sortItems(parent);
	}
	
	// creates part of one line of the description
	// combines name and symbol
	private String getDescLine(Datamodel datamodel) {
		return datamodel.getName() + " (" + datamodel.getTyp().getSymbol() + ")";
	}
	
	// creates description for the text area
	private String createDescription() {
		// obtaining current selection
		TreeItem<Datamodel> selected = datamodelTRV.getSelectionModel().getSelectedItem();
		String nad = "Nad";

		// when nothing is selected, nothing is displayed
		if (selected == null) {
			return "Nothing is selected";
		// when something is selected	
		} else {
			// obtaining name of the selected element; this string will be
			// several times longer, we will add new lines for each ancestor
			// in the path, so it is better to use StringBuilder to avoid
			// creating and destroying multiple strings
			StringBuilder result = new StringBuilder(selected.getValue().getTyp().toString()+": " + getDescLine(selected.getValue()) + "\n");
			
			// obtaining parent of the selected element
			TreeItem<Datamodel> parent = selected.getParent();

			// when parent exists 
			if (parent != null) {
				// creates father/mother line 
				result.append( nad+" "+parent.getValue().getTyp().toString() + ": " + getDescLine(parent.getValue()) + "\n");
			// when parent don't exists, selected element is root and creating of text ends
			} else {				
				return result.toString();
			}
			
			// obtaining parent of parent (grandparent)
			parent = parent.getParent();

			// when grandparent exists
			while (parent != null) {
				nad = "Nad-" + nad;
				// creates line for the grandparent or great-grandparent
				result.append(nad+" " + parent.getValue().getTyp().toString() + ": " + getDescLine(parent.getValue()) + "\n");
				// obtaining parent of current parent
				parent = parent.getParent();
			}
			return result.toString();					
		}
	}	
	
	// sorting children of selected parent
	private void sortItems(TreeItem<Datamodel> parent) {
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
	
	// removing selected item
	private void removeItem() {
		TreeItem<Datamodel> selected = datamodelTRV.getSelectionModel().getSelectedItem();
		
		// if nothing is selected, warning is displayed
		if (selected == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Deleting error");
			alert.setHeaderText("Nothing is selected!");
			alert.setContentText("Please select node to delete.");
			alert.showAndWait();
		// when something is selected	
		} else {
			// obtaining parent of selected item
			TreeItem<Datamodel> parent = selected.getParent();
			
			// root cannot be deleted - if parent is null, nothing will be deleted 
			if (parent == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Deleting error");
				alert.setHeaderText("Selected item is root!");
				alert.setContentText("It is not possible to delete " + selected.getValue().getName() + " is the root");
				alert.showAndWait();				
			// when selected element is not root
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION); 
				alert.setTitle("Deleting");
				if (Objects.equals(selected.getValue().getTyp().toString(), "Adresar")){
					alert.setHeaderText("Opravdu chcete smazat tento adresář a všechny jeho podadresáře?");
				} else {
					alert.setHeaderText("Opravdu chcete smazat tento soubor?");
				}
				alert.setContentText("Selected: " + selected.getValue().getName());
				alert.showAndWait()
				 .filter(response -> response == ButtonType.OK)
				 .ifPresent(response -> {
					 parent.getChildren().remove(selected);
					 // clearing selection - after deleting nothing will be selected
					 datamodelTRV.getSelectionModel().clearSelection();
			 });				
			}
		}
	}

	private void addAncestor() {
		// obtaining selected tree node
		TreeItem<Datamodel> selected = datamodelTRV.getSelectionModel().getSelectedItem();
		
		// if nothing is selected
		if (selected == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Insertion error");
			alert.setHeaderText("Nothing is selected!");
			alert.setContentText("Please select node to add new item.");
			alert.showAndWait();
		// if some node is selected as an item
		} else {
			String name = nameTF.getText();
			Typ typ = typCHB.getValue();
		
			// checking if name is provided
			if (name.length() == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Insertion error");
				alert.setHeaderText("No name is provided!");
				alert.setContentText("Please provide a name of a new item.");
				alert.showAndWait();				
			} else
			// checking if typ is provided
			if (typ == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Insertion error");
				alert.setHeaderText("No typ is provided!");
				alert.setContentText("Please provide a typ of a new item.");
				alert.showAndWait();
			//checking if selected item is file, if yes, adding cannot be completed
			} else
			if(Objects.equals(selected.getValue().getTyp().toString(), "Soubor")){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Insertion error");
				alert.setHeaderText("Can not add under file");
				alert.setContentText("Please select proper directory not a file.");
				alert.showAndWait();
			} // when everything is provided
				else {
				Datamodel newAncestor = new Datamodel(name, typ);
				selected.getChildren().add(new TreeItem<Datamodel>(newAncestor));
				sortItems(selected);
			    // ensures that newly added node will be visible 
				selected.setExpanded(true);				
			}
		}
	}
}
