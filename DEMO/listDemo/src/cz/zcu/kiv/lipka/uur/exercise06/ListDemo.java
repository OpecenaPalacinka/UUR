package cz.zcu.kiv.lipka.uur.exercise06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * Demonstration of ListView and using of dialogs
 * 
 * @author Richard Lipka
 *
 */
public class ListDemo extends Application {
	// reference on the list viewer - needed for manipulation with selection
	private ListView<String> listLV;
	// reference on primary stage - used for setting the reference on parent window in dialogs  
	private Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("List demonstration");
		
		this.primaryStage.setScene(createScene());
		this.primaryStage.show();
	}
	
	private Scene createScene() {
		Scene scene = new Scene(getRoot(), 400, 500);
		return scene;
	}
	
	private Parent getRoot() {
		// The whole scene is created from BorderPane - center contains list and bottom contains control of the 
		// application
		BorderPane mainPane = new BorderPane();
		
		mainPane.setCenter(getList());
		mainPane.setBottom(getControlPane());
		
		
		return mainPane;
	}
	
	private Node getList() {
		listLV = new ListView<String>();

		// filing list with initial data
		listLV.setItems(createInitData(50));
		
		// setting maximum spreading to sides - in order to fill whole center
		listLV.setPrefWidth(Double.MAX_VALUE);
		// allowing selection of multiple items
		listLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// allowing editation of cells
		listLV.setEditable(true);
		// setting a cell factory that creates editable string cells, based on TextField
		// basic cells are not able to edit their content
		listLV.setCellFactory(TextFieldListCell.forListView());		
		
		// setting margin around the whole list view - this will be used by layout in the parent panel
		BorderPane.setMargin(listLV, new Insets(5));
		
		return listLV;
	}
	
	private Node getControlPane() {
		// buttons will be placed in FlowPane
		FlowPane controlPane = new FlowPane();
		
		// creating buttons
		Button readBTN = new Button("Read file");
		Button selectBTN = new Button("Select");
		Button deleteBTN = new Button("Delete");
		Button resetBTN = new Button("Reset");
		
		// adding reaction on button pressing
		readBTN.setOnAction(event -> readNewData());
		selectBTN.setOnAction(event -> processSelection());
		deleteBTN.setOnAction(event -> deleteSelection());
		resetBTN.setOnAction(event -> resetSelection());		
		
		controlPane.getChildren().addAll(readBTN, selectBTN, deleteBTN, resetBTN);
		
		// setting margin around all children of this panel - all buttons
		controlPane.getChildren().forEach(node -> FlowPane.setMargin(node, new Insets(3)));
		// setting the same width for all children of this panel - buttons
		controlPane.getChildren().forEach(node -> {((Button)node).setPrefWidth(80);});
		
		// moving all buttons to center in parent layout
		controlPane.setAlignment(Pos.CENTER);
		controlPane.setPadding(new Insets(5));
		
		return controlPane;
	}
	
/*
 * ------------------------------------------------------------------------------------------------
 * 
 * Methods used for reaction on buttons - no creating of additional GUI elements
 * 
 * ------------------------------------------------------------------------------------------------	
 */
	

	private void resetSelection() {
		// removing selection from list
		listLV.getSelectionModel().clearSelection();
	}
	
	private void deleteSelection() {
		// creating copy of selected elements - collection containing reference on all selected strings
		ObservableList<String> selection = FXCollections.observableArrayList(listLV.getSelectionModel().getSelectedItems());
		
		// when nothing is selected, warning is displayed
		if (selection.size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Please select some lines before deleting them!");
			alert.setTitle("Selection error");
			alert.setHeaderText("Nothing is selected!");
			alert.showAndWait();
		// when at least one item is selected, it is removed from list view (from the underlying collection)	
		} else {		
			listLV.getItems().removeAll(selection);
			// after deleting, selection is placed on next item after the last deleted one, so usually 
			// it is better to remove it
			listLV.getSelectionModel().clearSelection();
		}
	}
	
	private void processSelection() {
		// creating copy of selected elements - collection containing reference on all selected strings
		ObservableList<String> selection = FXCollections.observableArrayList(listLV.getSelectionModel().getSelectedItems());
		
		// when nothing is selected, warning is displayed
		if (selection.size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Please select some lines before processing them!");
			alert.setTitle("Selection error");
			alert.setHeaderText("Nothing is selected!");
			alert.showAndWait();
		// when at least one item is selected, all selected items are displayed in the dialog
		} else {		
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Selected items");
			alert.setHeaderText("These items were selected");
			// setGraphics allow to place one node to the dialog 
			// usually it is used to place an icon there, but in our case it can be used to place
			// another list view there, containing all selected items
			alert.setGraphic(new ListView<String>(selection));
			alert.showAndWait();
		}		
	}	
	
	private void readNewData() {
		FileChooser chooser = new FileChooser();
	
		// showing file chooser dialog and waiting for selection
		File file = chooser.showOpenDialog(primaryStage);
		// when dialog is canceled, dialog returns null
		if (file != null) {
			// reading data from the selected file
			ObservableList<String> data = readFile(file);
			// when some data were read, they are assigned to the list view  
			if ((data != null) && (data.size() > 0)) {
				listLV.setItems(data);
			// when no data are loaded, alert is displayed	
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("No data were readed from file!");
				alert.setTitle("Loading error");
				alert.setHeaderText("No data in the file!");
				alert.showAndWait();
			}
		}
	}
	
	private ObservableList<String> readFile(File source) {
		// reading file line by line, each line will be one element in the list
		ObservableList<String> newData = FXCollections.observableArrayList();
		
		if (source == null) {
			return null;
		} else {
			FileReader reader;
			BufferedReader input;
			try {
				reader = new FileReader(source);
				input = new BufferedReader(reader);				
			
				for (String line = input.readLine(); line != null; line = input.readLine()) {
					newData.add(line);
				}
				
				input.close();
				reader.close();				
			} catch (IOException e) {
				return null;
			} 	
			
			return newData;
		}		
	}	
	
	private ObservableList<String> createInitData(int count) {
		// creating initial data
		ObservableList<String> data = FXCollections.observableArrayList();
		
		for (int i = 0; i < count; i++) {
			data.add("Line " + i);
		}
		
		return data;
	}		
}
