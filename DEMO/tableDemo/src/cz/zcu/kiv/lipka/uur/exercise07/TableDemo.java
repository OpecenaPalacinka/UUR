package cz.zcu.kiv.lipka.uur.exercise07;

import java.awt.Event;
import java.time.LocalDate;
import java.util.Locale;

import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 
 * Demonstration of using the table, with new cell editor / renderer
 * 
 * @author Richard Lipka
 *
 */
public class TableDemo extends Application {
	public static final String DEF_LANGUAGE = "cs";
	public static final String DEF_COUNTRY = "CZ";
	
	// reference on stage
	private Stage primaryStage;
	// reference on table, neccessary to manipulate with selected elements
	private TableView<Person> tableTV;
	// references on textfields and date picker for adding new people
	private TextField nameTF, surnameTF;
	private DatePicker birthdayDP;
	private Label logLabel;
	
	public static Messages messages = new Messages(Locale.getDefault());

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		Locale.setDefault(new Locale(DEF_LANGUAGE, DEF_COUNTRY));
		
		this.primaryStage.setTitle("Table example");
		this.primaryStage.setScene(createScene());
		this.primaryStage.show();		
	}
	
	private Scene createScene() {
		Scene scene = new Scene(getRoot(), 750, 500);
		return scene;
	}
	
	// scene is based on border pane, with table in center and control on bottom
	private Parent getRoot() {
		BorderPane rootPane = new BorderPane();
		
		rootPane.setCenter(getTablePane());
		rootPane.setTop(getControlPane());
		rootPane.setBottom(getLoggingPane());
		
		return rootPane;
	}
	
	// creating table and setting up its properties
	private Node getTablePane() {
		tableTV = new TableView<Person>();		
		
		// creating columns, with definition of data type displayed in column
		TableColumn<Person, Integer> numberColumn = new TableColumn<Person, Integer>("Order");
		TableColumn<Person, String> nameColumn = new TableColumn<Person, String>("Name");
		TableColumn<Person, String> surnameColumn = new TableColumn<Person, String>("Surname");
		TableColumn<Person, LocalDate> birthdayColumn = new TableColumn<Person, LocalDate>("Birthday");
		TableColumn<Person, String> connectedNameColumn = new TableColumn<Person, String>("Full name");
		TableColumn<Person, Day> dayColumn = new TableColumn<Person, Day>("Day");
		
		// creating column with number of line
		// no value factory is needed - the number will be determined by index of cell
		numberColumn.setMinWidth(40);
		numberColumn.setMaxWidth(40);
		// naturally, column will not be editable and sortable
		numberColumn.setEditable(false);
		numberColumn.setSortable(false);
		// creating a custom cell factory (as a lambda expression) that provides cell with its index
		numberColumn.setCellFactory(column -> new OrderCell());
		
		// creating factory for obtaining value that will be displayed in the cell
		nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		nameColumn.setMinWidth(150);
		// creating cell factory that will allow editation of name			
		// creating factory using lambda expression
		nameColumn.setCellFactory(column -> new ConsumingTextFieldTableCell<Person, String>());
		//nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	
		// creating factory for obtaining value that will be displayed in the cell
		surnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
		surnameColumn.setMinWidth(150);
		// creating cell factory using the one defined in ConsumingTextFieldTableCell
		surnameColumn.setCellFactory(ConsumingTextFieldTableCell.forTableColumn());
		
		// creating factory for obtaining value that will be displayed in the cell		
		birthdayColumn.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("birthday"));
		birthdayColumn.setMinWidth(150);
		birthdayColumn.setMaxWidth(200);
		// creating custom factory that will provided instances of DatePickerCell, used for displaying and 
		// editation of date
		birthdayColumn.setCellFactory(column -> new DatePickerCell());
		// creating a reaction on committing new value in the cell - propagating the change in the model
		// Necessary - the cell itself is not providing binding (it is not implemented in it)
		birthdayColumn.setOnEditCommit(event -> commitBirthday(event));		
		
		// demonstration of calculated column - value is computed when name and surname columns are changed
		// values in columns are observable --> no need for explicit checks and redraws of table		
		// factory is defined in this method
		//   the factory returns StringBinsing object - an object composed of several
		//                                              strings, that is changed when any
		//                                              component of it is changed
		connectedNameColumn.setCellValueFactory(value -> new StringBinding() {
			{
				// list of binded string properties (have to be observable)
				super.bind(value.getValue().nameProperty(), value.getValue().surnameProperty());
			}
			
			// computes value when any of theobserved components is changed
			@Override 
			protected String computeValue() {				
				return value.getValue().nameProperty().getValue() + " " + value.getValue().surnameProperty().getValue();
			}
		});
		connectedNameColumn.setEditable(false);		
		
		// demonstration of column with ComboBox for data selection
		dayColumn.setCellValueFactory(new PropertyValueFactory<Person, Day>("day"));
		dayColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Day.values()));
		
		// adding columns to the table
		tableTV.getColumns().addAll(numberColumn, nameColumn, surnameColumn, birthdayColumn, connectedNameColumn, dayColumn);
		// creating initial dataset
		tableTV.setItems(getInitData());
		// allowing editation of the table and all its columns
		tableTV.setEditable(true);
		// allowing selection of multiple rows
		tableTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// allowing columns to resize in order to fill the whole table
		tableTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		// adding reaction on key pressed in the table - deleting rows when DELETE is pressed
		tableTV.setOnKeyReleased(event -> {
			// deleting only when DELETE is pressed, other keys are not interesting 
			if (event.getCode() == KeyCode.DELETE) 
				deleteSelected();
		});

		BorderPane.setMargin(tableTV, new Insets(5));
		
		return tableTV;
	}
	
	// creating control form, GridPane is used in order to keep labels and fields in line	
	private Node getControlPane() {
		GridPane controlPane = new GridPane();
		
		Label nameLB = new Label("Name:");
		Label surnameLB = new Label("Surname:");
		Label birthdayLB = new Label("Birthday:");
		
		nameTF = new TextField();
		surnameTF = new TextField();
		birthdayDP = new DatePicker();		
		
		Button addBT = new Button("Add");
		addBT.setPrefWidth(60);
		addBT.setPrefHeight(60);
		addBT.setOnAction(event -> addPerson());

		controlPane.add(nameLB, 0, 0);
		controlPane.add(nameTF, 0, 1);
		controlPane.add(surnameLB, 1, 0);
		controlPane.add(surnameTF, 1, 1);
		controlPane.add(birthdayLB, 2, 0);
		controlPane.add(birthdayDP, 2, 1);
		
		controlPane.add(addBT, 4, 0, 1, 2);
	
		controlPane.setHgap(3);
		controlPane.setVgap(3);
		
		controlPane.setAlignment(Pos.CENTER);
		controlPane.setPadding(new Insets(5));
		
		return controlPane;
	}
	
	private Node getLoggingPane() {
		HBox loggingPane = new HBox();
		
		logLabel = new Label("Place for actual state of date model");
		Button updateBT = new Button("Update");
		HBox.setMargin(updateBT, new Insets(5));
		updateBT.setOnAction(event -> updateLog());
		
		loggingPane.getChildren().addAll(updateBT, logLabel);
		
		return loggingPane;
	}
	
	/*
	 * ------------------------------------------------------------------------------------------
	 * 
	 * Control methods - reaction on deleting and adding elements into table
	 * 
	 * ------------------------------------------------------------------------------------------
	 * 
	 */
	
	private void updateLog() {
		ObservableList<Person> selection = tableTV.getSelectionModel().getSelectedItems();
		
		if (selection.size() == 0) {
			logLabel.setText("Nothing is selected");
		} else {
			logLabel.setText(selection.size() + " selected; First: " + selection.get(0).toString());
		}
	}
	
	// deleting selected elements, similar like in ListView
	private void deleteSelected() {		
		// obtaining the selected elements
		ObservableList<Person> selection = FXCollections.observableArrayList(tableTV.getSelectionModel().getSelectedItems());
		
		// when nothing is selected
		if (selection.size() == 0) {
			// a warning is displayed
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Deleting items");
			alert.setHeaderText("No items for deleting were selected!");
			alert.setContentText("Please select items for deleting.");
			alert.show();
		// when at least one element in table is selected	
		} else {			
			// user is asked to confirm deleting elements
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Deleting selection");
			alert.setHeaderText("Do you want to delete selected elements?");
			
			// configuring listView of items selected for deleting
			ListView<Person> selectionLW = new ListView<Person>(selection);
			selectionLW.setPrefHeight(100);
			selectionLW.setPrefWidth(400);
			
			// dialog contain a list of elements that should be deleted, displayed as toString()				
			alert.setGraphic(selectionLW);
			// stream API used to determine which button was pressed in the dialog
			alert.showAndWait()
				 // when the dialog is confirmed
				 .filter(response -> response == ButtonType.OK)
				 // removing all selected elements and clearing the selection
				 .ifPresent(response -> {
					 tableTV.getItems().removeAll(selection);
					 tableTV.getSelectionModel().clearSelection();
			 });
		}
	}	
	
	// commiting new birthday to the model	
	private void commitBirthday(CellEditEvent<Person, LocalDate> event) {
		// obtaining row where birthdate is edited   // setting birthdate to the model
		event.getRowValue().setBirthday(event.getNewValue());
	}
	
	// adding new person
	private void addPerson() {
		String name = nameTF.getText();
		String surname = surnameTF.getText();
		LocalDate birthday = birthdayDP.getValue();
		
		// tests and warnings checking if all fields are filled by the user
		if (name.length() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Insert error");
			alert.setHeaderText("No name is provided!");
			alert.setContentText("Please provide a first name in corresponding filed!");
			alert.showAndWait();
			return;
		} 
		if (surname.length() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Insert error");
			alert.setHeaderText("No surname is provided!");
			alert.setContentText("Please provide a surname in corresponding filed!");
			alert.showAndWait();
			return;			
		}
		if (birthday == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Insert error");
			alert.setHeaderText("No birthday date is provided!");
			alert.setContentText("Please provide a birthday date in corresponding filed!");
			alert.showAndWait();
			return;			
		}
		
		// when all data are set, new person is created and added to the table
		Person newPerson = new Person(name, surname, birthday);
		tableTV.getItems().add(newPerson);
		// it is useful to sort table after adding, so the person is not at the end, but on the right position
		tableTV.sort();		
	}
	
	// creating initial data
	private ObservableList<Person> getInitData() {
		ObservableList<Person> data = FXCollections.observableArrayList();
		
		data.add(new Person("Jan", "Novak", LocalDate.of(1983, 3, 17)));
		data.add(new Person("Petr", "Lilov", LocalDate.of(1955, 2, 23)));
		data.add(new Person("Ales", "Cerha", LocalDate.of(1992, 4, 8)));
		data.add(new Person("Kamila", "Zrzava", LocalDate.of(1963, 7, 22)));
		data.add(new Person("Zdena", "Danhelova", LocalDate.of(1984, 1, 30)));
		data.add(new Person("Ivan", "Holy", LocalDate.of(1999, 5, 11)));
		data.add(new Person("Tomas", "Lavicka", LocalDate.of(1972, 10, 2)));
		data.add(new Person("Simon", "Vesely", LocalDate.of(1956, 2, 7)));
		
		return data;
	}
}
