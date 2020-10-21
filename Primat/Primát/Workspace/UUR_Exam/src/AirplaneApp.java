import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


public class AirplaneApp extends Application {

	private TableView<Airplane> tableView = new TableView<Airplane>();
	
	private TableColumn<Airplane,String> nameColumn = new TableColumn<Airplane,String>();
	private TableColumn<Airplane,AirplaneType> typeColumn = new TableColumn<Airplane,AirplaneType>();
	private TableColumn<Airplane,Double> weightColumn = new TableColumn<Airplane,Double>();
	private TableColumn<Airplane,Double> rangeColumn = new TableColumn<Airplane,Double>();
	private TableColumn<Airplane,LocalDate> firstFlightColumn = new TableColumn<Airplane,LocalDate>();
	
	private TextArea textArea = new TextArea();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Airplane Application");
		stage.setScene(getScene());
		stage.setMinHeight(500);
		stage.setMinWidth(500);
		stage.show();
		stage.setHeight(stage.getMinHeight());
		stage.setWidth(stage.getMinWidth());
	}

	private Scene getScene() {
		Scene scene = new Scene(initRootLayout());
		initTableView();
		return scene;
	}

	private Parent initRootLayout() {
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: #660BAB;");
		
		borderPane.setTop(getTopPane());
		borderPane.setLeft(getLeftPane());
		borderPane.setCenter(getCenterPane());
		//borderPane.setRight(getRightPane());
		//borderPane.setBottom(getBottomPane());
		
		return borderPane;
	}

	private void initTableView() {
		tableView.getColumns().addAll(nameColumn, typeColumn, weightColumn, rangeColumn, firstFlightColumn);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.setEditable(true);
		tableView.setItems(Airplane.getDefaultData());
		tableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Airplane>() {
			 @Override
			 public void onChanged(Change<? extends Airplane> change) {
				 setTextAreaContent();
			 }
		 });
		initNameColumn();
		initTypeColumn();
		initWeightColumn();
		initRangeColumn();
		initFirstFlightColumn();
	}
	
	private void initNameColumn() {
		nameColumn.setText("Name");
		nameColumn.setEditable(true);
		nameColumn.setMinWidth(50D);
		nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setOnEditCommit(commit -> {
			Airplane airplane = commit.getRowValue();
			airplane.setName(commit.getNewValue());
		});
	}
	
	private void initTypeColumn() {
		typeColumn.setText("Type");
		typeColumn.setEditable(true);
		typeColumn.setMinWidth(50D);
		typeColumn.setCellValueFactory(cell -> new SimpleObjectProperty(cell.getValue().getType()));
		typeColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(AirplaneType.values()));
		typeColumn.setOnEditCommit(commit -> {
			Airplane airplane = commit.getRowValue();
			airplane.setType(commit.getNewValue());
		});
	}
	
	private void initWeightColumn() {
		weightColumn.setText("Weight (kg)");
		weightColumn.setEditable(true);
		weightColumn.setMinWidth(50D);
		weightColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getWeight()).asObject());
		weightColumn.setCellFactory(cell -> {
			
			return new TableCell<Airplane, Double>() {
				private TextField textField;
				
				@Override
				public void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setAlignment(Pos.CENTER_LEFT);
					if(empty) {
						setText(null);
						setGraphic(null);
					} else {
						setText(getformattedDouble(item));
						setFont(Font.font("Verdana", FontWeight.BOLD, 12));
						if(item >= 100000) {
							setStyle("-fx-text-fill: #ED1C23;");
						} else if(item < 10000) {
							setStyle("-fx-text-fill: #0F6F00;");
						} else {
							setStyle(null);
						}
						setGraphic(null);
					}
				}
				
				@Override
				public void startEdit() {
					super.startEdit();		
					if (textField == null) {
						createTextField();
					}
					setText(null);		
					setGraphic(textField);
				}
				
				@Override
				public void cancelEdit() {
					super.cancelEdit();
					setText(getformattedDouble(getItem()));
					setGraphic(null);
				}
				
				private String getformattedDouble(Double item) {
					NumberStringConverter converter = new NumberStringConverter(Locale.GERMAN);
					return converter.toString(item) + " kg";
				}
				
				private void createTextField() {
					textField = new TextField(String.valueOf(getItem()));
					textField.setOnAction(event -> {	
						try {
							commitEdit(Double.valueOf(textField.getText()));
						} catch(Exception e) {
							cancelEdit();
						}
					});
				}
				
			};
			
		});
		weightColumn.setOnEditCommit(commit -> {
			Airplane airplane = commit.getRowValue();
			airplane.setWeight(commit.getNewValue());
		});
	}
	
	private void initRangeColumn() {
		rangeColumn.setText("Range (km)");
		rangeColumn.setEditable(true);
		rangeColumn.setMinWidth(50D);
		rangeColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getRange()).asObject());
		rangeColumn.setCellFactory(cell -> {
			
			return new TableCell<Airplane, Double>() {
				private TextField textField;
				
				@Override
				public void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setAlignment(Pos.CENTER_LEFT);
					if(empty) {
						setText(null);
						setGraphic(null);
					} else {
						setText(getformattedDouble(item));
						setFont(Font.font("Verdana", FontWeight.BOLD, 12));
						setGraphic(null);
					}
				}
				
				@Override
				public void startEdit() {
					super.startEdit();		
					if (textField == null) {
						createTextField();
					}
					setText(null);		
					setGraphic(textField);
				}
				
				@Override
				public void cancelEdit() {
					super.cancelEdit();
					setText(getformattedDouble(getItem()));
					setGraphic(null);
				}
				
				private String getformattedDouble(Double item) {
					NumberStringConverter converter = new NumberStringConverter(Locale.GERMAN);
					return converter.toString(item) + " km";
				}
				
				private void createTextField() {
					textField = new TextField(String.valueOf(getItem()));
					textField.setOnAction(event -> {	
						try {
							commitEdit(Double.valueOf(textField.getText()));
						} catch(Exception e) {
							cancelEdit();
						}
					});
				}
				
			};
		});
		rangeColumn.setOnEditCommit(commit -> {
			Airplane airplane = commit.getRowValue();
			airplane.setRange(commit.getNewValue());
		});
	}
	
	private void initFirstFlightColumn() {
		firstFlightColumn.setText("First flight");
		firstFlightColumn.setEditable(true);
		firstFlightColumn.setMinWidth(50D);
		firstFlightColumn.setCellValueFactory(cell -> new SimpleObjectProperty(cell.getValue().getFirstFlight()));
		firstFlightColumn.setCellFactory(cell -> {
			
			return new TableCell<Airplane, LocalDate>() {
				private DatePicker datePicker;
				
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					setAlignment(Pos.CENTER_LEFT);
					if(empty) {
						setText(null);
						setGraphic(null);
					} else {
						if(item != null) {
							setText(getformattedDate(item));
						} else {
							setText("not set");
						}
						setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
						setGraphic(null);
					}
				}
				
				@Override
				public void startEdit() {
					super.startEdit();		
					if (datePicker == null) {
						createDatePicker();
					}
					setText(null);		
					setGraphic(datePicker);
					datePicker.show();
				}
				
				@Override
				public void cancelEdit() {
					super.cancelEdit();
					if(getItem() != null) {
						setText(getformattedDate(getItem()));
					} else {
						setText("not set");
					}
					setGraphic(null);
				}
				
				private String getformattedDate(LocalDate item) {
					DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
					formatter = formatter.withLocale(Locale.GERMAN);
					return formatter.format(item);
				}
				
				private void createDatePicker() {
					datePicker = new DatePicker(getItem());
					datePicker.setEditable(false); 
					datePicker.setOnAction(event -> {	
						if (datePicker.getValue() != null) {
							commitEdit(datePicker.getValue());
						} else {
							cancelEdit();
						}
					});
				}
				
			};
		});
		firstFlightColumn.setOnEditCommit(commit -> {
			Airplane airplane = commit.getRowValue();
			airplane.setFirstFlight(commit.getNewValue());
		});
	}
	
	private Node getTopPane() {
		AnchorPane anchorPane = new AnchorPane();
		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-background-color: #FFCC00;");
		menuBar.getMenus().add(getAircraftMenu());
		anchorPane.getChildren().add(menuBar);
		AnchorPane.setTopAnchor(menuBar, 0D);
	    AnchorPane.setLeftAnchor(menuBar, 0D);
	    AnchorPane.setRightAnchor(menuBar, 0D);
	    AnchorPane.setBottomAnchor(menuBar, 0D);
	    return anchorPane;
	}
	
	private Menu getAircraftMenu() {
		Menu menu = new Menu("_Aircraft");
		MenuItem addAirplaneMI = new MenuItem("_Add airplane");
		addAirplaneMI.setAccelerator(new KeyCodeCombination(KeyCode.PLUS, KeyCombination.CONTROL_DOWN));
		addAirplaneMI.setOnAction(action -> addItem());
		MenuItem removeAirplaneMI = new MenuItem("_Remove airplane");
		removeAirplaneMI.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN));
		removeAirplaneMI.setOnAction(action -> removeItems());
		menu.getItems().addAll(addAirplaneMI, removeAirplaneMI);
		return menu;
	}
	
	private Node getLeftPane() {
		AnchorPane anchorPane = new AnchorPane();

		StackPane stackPane = new StackPane();
		stackPane.setStyle("-fx-background-color: #FFCC00;");
		
		VBox vBox = new VBox();
		vBox.setSpacing(5D);
		
		Button addButton = new Button("Add plane");
		addButton.setMaxWidth(Double.MAX_VALUE);
		addButton.setStyle("-fx-base: #ED1C23;");
		addButton.setOnAction(action -> addItem());
		Button removeButton = new Button("Remove plane");
		removeButton.setMaxWidth(Double.MAX_VALUE);
		removeButton.setStyle("-fx-base: #ED1C23;");
		removeButton.setOnAction(action -> removeItems());
		Button exitButton = new Button("Exit");
		exitButton.setMaxWidth(Double.MAX_VALUE);
		exitButton.setStyle("-fx-base: #ED1C23;");
		exitButton.setOnAction(action -> closeApp());
		
		vBox.getChildren().addAll(addButton, removeButton, exitButton);
		vBox.setAlignment(Pos.CENTER);
		
		stackPane.getChildren().add(vBox);
		StackPane.setAlignment(vBox, Pos.CENTER);
		
		anchorPane.getChildren().add(stackPane);
		AnchorPane.setTopAnchor(stackPane, 5D);
	    AnchorPane.setLeftAnchor(stackPane, 5D);
	    AnchorPane.setRightAnchor(stackPane, 5D);
	    AnchorPane.setBottomAnchor(stackPane, 5D);
		return anchorPane;
	}

	private Node getCenterPane() {
		AnchorPane anchorPane = new AnchorPane();

		SplitPane splitPane = new SplitPane();
		splitPane.setOrientation(Orientation.HORIZONTAL);
		splitPane.setStyle("-fx-background-color: #FFCC00;");

		splitPane.getItems().add(getLeftNode());
		splitPane.getItems().add(getRightNode());
		
		anchorPane.getChildren().add(splitPane);
		AnchorPane.setTopAnchor(splitPane, 5D);
	    AnchorPane.setLeftAnchor(splitPane, 5D);
	    AnchorPane.setRightAnchor(splitPane, 5D);
	    AnchorPane.setBottomAnchor(splitPane, 5D);
		return anchorPane;
	}
	
	/** levy node zobrazeny ve SplitPane */
	private Node getLeftNode() {
		this.textArea.setText("");
		this.textArea.setWrapText(false);			// zalamovani textu
		this.textArea.setPrefWidth(280D);
		this.textArea.setEditable(false);
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().add(this.textArea);
		AnchorPane.setTopAnchor(this.textArea, 5D);
	    AnchorPane.setLeftAnchor(this.textArea, 5D);
	    AnchorPane.setRightAnchor(this.textArea, 5D);
	    AnchorPane.setBottomAnchor(this.textArea, 5D);
	    return anchorPane;
	}
	
	/** pravy node zobrazeny ve SplitPane */
	private Node getRightNode() {
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().add(this.tableView);
		AnchorPane.setTopAnchor(this.tableView, 5D);
	    AnchorPane.setLeftAnchor(this.tableView, 5D);
	    AnchorPane.setRightAnchor(this.tableView, 5D);
	    AnchorPane.setBottomAnchor(this.tableView, 5D);
	    return anchorPane;
	}
	
	private void setTextAreaContent() {
		ObservableList<Airplane> aircrafts = tableView.getSelectionModel().getSelectedItems();
		StringBuilder stringBuilder = new StringBuilder("Selected airplanes: \n");
		for(Airplane airplane : aircrafts) {
			stringBuilder.append(airplane.getName() + " (" +
					airplane.getWeight() + " kg, " +
					airplane.getRange() + " km)\n");
		}
		textArea.setText(stringBuilder.toString());
	}
	
	private void addItem() {
		Airplane aircraft = new Airplane("-- Set name --",AirplaneType.UNKNOWN,0,0,null);
		tableView.getItems().add(aircraft);
	}
	
	private void removeItems() {
		ObservableList<Airplane> aircrafts = tableView.getSelectionModel().getSelectedItems();
		
		if(aircrafts != null && !aircrafts.isEmpty()) {
			ObservableList<Airplane> observableList = FXCollections.observableArrayList(aircrafts);
			
			ListView<Airplane> listView = new ListView<Airplane>(observableList);
			listView.setCellFactory(list -> {
				return new ListCell<Airplane>() {
					@Override
					protected void updateItem(Airplane item, boolean empty) {
						super.updateItem(item, empty);
						setText(null);
						if (empty) {
							setText(null);
						} else {
							setText(item.getName());
						}
					}
				};
			});
			
			String title = "Deleting selection";
			String header = "Do you want to delete selected elements?";
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setGraphic(listView);
			ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
			ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(buttonTypeNo, buttonTypeYes);
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == buttonTypeYes){
				for(Airplane airplane : observableList) {
					removeItem(airplane);
				}
			}
		}
	}
	
	private void removeItem(Airplane item) {
		if(tableView.getItems().contains(item)) {
			tableView.getItems().remove(item);
		}
	}
	
	/** Ukonceni */
	public void closeApp() {
		Platform.exit();
	}
}
