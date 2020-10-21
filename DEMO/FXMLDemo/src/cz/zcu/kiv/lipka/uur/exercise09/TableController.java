package cz.zcu.kiv.lipka.uur.exercise09;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller that handles events defined in FXML
 * 
 * @author Richard Lipka
 *
 */
public class TableController implements Initializable{
	/*---------------------------------------------------------------
	 * 
	 * References on nodes of the scene that will be necessary to
	 * provide all required services (adding data to table, removing 
	 * them, updating text area)  
	 * 
	 * FXML loader automatically provides references according to 
	 * fx:id values in XML file - they have match the names of these
	 * attributes
	 * 
	 *--------------------------------------------------------------- */
	@FXML
	private TableView<Connection> connectionTable;
	
	@FXML 
	private IPEditorField sourceIP, targetIP;
	
	@FXML 
	private TextField programTF;
	
	@FXML
	private TextArea logTA;
	/*--------------------------------------------------------------- */	
	
	// initialization - all content of this method will be invoked
	// before controller is created - useful when some properties
	// cannot be set in FXML and have to be set when FXML is loaded
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}	
	
	// updating text area with data from selected row
	@FXML
	public void displayRow(ActionEvent event) {
		Connection selected = connectionTable.getSelectionModel().getSelectedItem();
		
		if (selected != null) {
			logTA.setText("Connection details\n"
					    + "Program name: " + selected.getName() + "\n"
					    + "Source: " + selected.getSource().getHostAddress()  + "\n"
					    + "Target: " + selected.getTarget().getHostAddress()  + "\n");
		}
	}
	
	// adding row, with tests if inputs are all right
	@FXML
	public void addRow(ActionEvent event) {
				
		String name = programTF.getText();
		InetAddress source = sourceIP.getValue();
		InetAddress target = targetIP.getValue();
		
		if (name.trim().length() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Insert error");
			alert.setHeaderText("No name is provided!");
			alert.setContentText("Name of the program cannot be empty, you have to provide a name");
			alert.showAndWait();
			
			return;
		}
		
		if (source == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Insert error");
			alert.setHeaderText("No source IP adress was provided!");
			alert.setContentText("Source adress of the connection cannot be empty, you have to provide some source adress");
			alert.showAndWait();
			
			return;
		}
		
		if (name.trim().length() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Insert error");
			alert.setHeaderText("No target IP adress was provided!");
			alert.setContentText("Target adress of the connection cannot be empty, you have to provide some target adress");
			alert.showAndWait();
			
			return;
		}
		
		Connection newConnection = new Connection(name, source, target);
		// data model is obtained directly from TableView
		connectionTable.getItems().add(newConnection);		
	}
	
	// updating name in the data model
	@FXML
	public void nameCommit(CellEditEvent<Connection, String> event) {
		if (event.getNewValue().trim().length() > 0) {
			event.getRowValue().setName(event.getNewValue());
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Insert error");
			alert.setHeaderText("No IP adress was provided!");
			alert.setContentText("Name of the program cannot be empty, old value was preserved in the table");
			alert.showAndWait();
			event.getRowValue().setName(event.getOldValue());
		}
	}
	
	// updating source address in data model	
	@FXML
	public void sourceCommit(CellEditEvent<Connection, InetAddress> event) {
		if (event.getNewValue() != null) {
			event.getRowValue().setSource(event.getNewValue());			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Editation error");
			alert.setHeaderText("No IP adress was provided!");
			alert.setContentText("Source adress of the connection cannot be empty, old value was preserved in the table");
			alert.showAndWait();
			event.getRowValue().setSource(event.getOldValue());
		}
	}
	
	// updating target address in data model	
	@FXML
	public void targetCommit(CellEditEvent<Connection, InetAddress> event) {
		if (event.getNewValue() != null) {
			event.getRowValue().setTarget(event.getNewValue());
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Editation error");
			alert.setHeaderText("No IP adress was provided!");
			alert.setContentText("Target adress of the connection cannot be empty, old value was preserved in the table");
			alert.showAndWait();
			event.getRowValue().setTarget(event.getOldValue());
		}
	}	
	
	// deleting selected row, with test if something is selected
	@FXML
	public void removeRow() {
		Connection selected = connectionTable.getSelectionModel().getSelectedItem();
		
		if (selected == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Deleting error");
			alert.setHeaderText("No connection is selected!");
			alert.setContentText("You have to selecta line to delete");
			alert.showAndWait();
			return;
		}
		
		connectionTable.getItems().remove(selected);
	}
	
	// terminating application
	@FXML
	public void terminate() {
		Platform.exit();
	}
}
