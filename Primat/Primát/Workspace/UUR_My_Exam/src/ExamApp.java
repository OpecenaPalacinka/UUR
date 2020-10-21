import java.util.Locale;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.CurrencyStringConverter;
import javafx.util.converter.NumberStringConverter;

public class ExamApp extends Application {

	private static final String negativeSalary = "Salary cannot be negative number";
	private static final String negativeAdvance = "Advance cannot be negative number";
	private static final String hightAdvance = "Advance cannot be higher than salary";
	private static final String nothingSelectedDeletion = "Nothing selected - cannot delete";
	
	private TableView<Person> tableView = new TableView<Person>();
	
	private TableColumn<Person,String> nameColumn = new TableColumn<Person,String>();
	private TableColumn<Person,Gender> genderColumn = new TableColumn<Person,Gender>();
	private TableColumn<Person,Integer> salaryColumn = new TableColumn<Person,Integer>();
	private TableColumn<Person,Integer> advanceColumn = new TableColumn<Person,Integer>();
	private TableColumn<Person,Person> remainColumn = new TableColumn<Person,Person>();
	
	private TextArea textArea = new TextArea();
	
	private Label message = new Label("");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Table example | A16B0176P");
		stage.setScene(getScene());
		stage.setMinHeight(500);
		stage.setMinWidth(800);
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
		
		borderPane.setTop(getTopPane());
		borderPane.setLeft(getLeftPane());
		borderPane.setCenter(getCenterPane());
		//borderPane.setRight(getRightPane());
		borderPane.setBottom(getBottomPane());
		
		return borderPane;
	}

	private void initTableView() {
		tableView.getColumns().addAll(nameColumn,genderColumn,salaryColumn,advanceColumn,remainColumn);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.setEditable(true);
		tableView.setItems(Person.getDefaultData());
		tableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Person>() {
			 @Override
			 public void onChanged(Change<? extends Person> change) {
				 setTextAreaContent();
			 }
		 });
		initNameColumn();
		initGenderColumn();
		initSalaryColumn();
		initAdvanceColumn();
		initRemainColumn();
	}
	
	private void initNameColumn() {
		nameColumn.setText("Name");
		nameColumn.setEditable(true);
		nameColumn.setMinWidth(150D);
		nameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setOnEditCommit(commit -> {
			Person person = commit.getRowValue();
			person.setName(commit.getNewValue());
			setTextAreaContent();
		});
	}
	
	private void initGenderColumn() {
		genderColumn.setText("Gender");
		genderColumn.setEditable(true);
		genderColumn.setMinWidth(50D);
		genderColumn.setCellValueFactory(cell -> cell.getValue().genderProperty());
		genderColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(Gender.values()));
		genderColumn.setOnEditCommit(commit -> {
			Person person = commit.getRowValue();
			person.setGender(commit.getNewValue());
			setTextAreaContent();
		});
	}
	
	private void initSalaryColumn() {
		salaryColumn.setText("Salary");
		salaryColumn.setEditable(true);
		salaryColumn.setMinWidth(100D);
		salaryColumn.setCellValueFactory(cell -> cell.getValue().salaryProperty().asObject());
		salaryColumn.setCellFactory(cell -> {
			
			return new TableCell<Person, Integer>() {
				private TextField textField;
				
				@Override
				public void updateItem(Integer item, boolean empty) {
					super.updateItem(item, empty);
					setAlignment(Pos.CENTER_LEFT);
					if(empty) {
						setText(null);
						setGraphic(null);
					} else {
						setText(getFormattedMoney(item));
						setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
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
					setText(getFormattedMoney(getItem()));
					setGraphic(null);
				}
				
				private String getFormattedMoney(Integer item) {
					NumberStringConverter converter = new CurrencyStringConverter(new Locale("hu", "HU"));
					return converter.toString(item);
				}
				
				private void createTextField() {
					textField = new TextField(String.valueOf(getItem()));
					textField.setOnAction(event -> {	
						try {
							Integer amount = Integer.valueOf(textField.getText());
							if(amount < 0) {
								textField.setText(String.valueOf(getItem()));
								setMessage(negativeSalary,"-fx-text-fill: #ED1C23;");
								cancelEdit();
							} else {
								commitEdit(amount);
							}
						} catch(Exception e) {
							textField.setText(String.valueOf(getItem()));
							cancelEdit();
						}
					});
				}
				
			};
			
		});
		salaryColumn.setOnEditCommit(commit -> {
			Person person = commit.getRowValue();
			person.setSalary(commit.getNewValue());
			refreshTable();
		});
	}
	
	private void initAdvanceColumn() {
		advanceColumn.setText("Advance");
		advanceColumn.setEditable(true);
		advanceColumn.setMinWidth(100D);
		advanceColumn.setCellValueFactory(cell -> cell.getValue().advanceProperty().asObject());
		advanceColumn.setCellFactory(cell -> {
			
			return new TableCell<Person, Integer>() {
				private TextField textField;
				private Person person;
				
				@Override
				public void updateItem(Integer item, boolean empty) {
					super.updateItem(item, empty);
					setAlignment(Pos.CENTER_LEFT);
					if(empty) {
						setText(null);
						setGraphic(null);
					} else {
						setText(getFormattedMoney(item));
						setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
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
					setText(getFormattedMoney(getItem()));
					setGraphic(null);
				}
				
				private String getFormattedMoney(Integer item) {
					NumberStringConverter converter = new CurrencyStringConverter(new Locale("hu", "HU"));
					return converter.toString(item);
				}
				
				private void createTextField() {
					person = (Person) getTableRow().getItem();
					textField = new TextField(String.valueOf(getItem()));
					textField.setOnAction(event -> {	
						try {
							Integer amount = Integer.valueOf(textField.getText());
							if(amount < 0) {
								textField.setText(String.valueOf(getItem()));
								setMessage(negativeAdvance,"-fx-text-fill: #ED1C23;");
								cancelEdit();
							} else if(amount > person.getSalary()) {
								textField.setText(String.valueOf(getItem()));
								setMessage(hightAdvance,"-fx-text-fill: #ED1C23;");
								cancelEdit();
							} else {
								commitEdit(amount);
							}
						} catch(Exception e) {
							textField.setText(String.valueOf(getItem()));
							cancelEdit();
						}
					});
				}
				
			};
			
		});
		advanceColumn.setOnEditCommit(commit -> {
			Person person = commit.getRowValue();
			person.setAdvance(commit.getNewValue());
			refreshTable();
		});
	}
	
	private void initRemainColumn() {
		remainColumn.setText("Remain");
		remainColumn.setMinWidth(100D);
		remainColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Person>(cellData.getValue()));
		remainColumn.setCellFactory(column -> {
			return new TableCell<Person, Person>() {
				
				@Override
				protected void updateItem(Person item, boolean empty) {
					super.updateItem(item, empty);
					if(item != null) {
						Integer remain = item.salaryProperty().get() - item.advanceProperty().get();
						setText(getFormattedMoney(remain));
						setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
					} else {
						setText("");
						setGraphic(null);
					}
				}
				
				private String getFormattedMoney(Integer item) {
					NumberStringConverter converter = new CurrencyStringConverter(new Locale("hu", "HU"));
					return converter.toString(item);
				}
				
			};
		});
	}
	
	private Node getTopPane() {
		AnchorPane anchorPane = new AnchorPane();
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(getFileMenu());
		menuBar.getMenus().add(getTableMenu());
		anchorPane.getChildren().add(menuBar);
		AnchorPane.setTopAnchor(menuBar, 0D);
	    AnchorPane.setLeftAnchor(menuBar, 0D);
	    AnchorPane.setRightAnchor(menuBar, 0D);
	    AnchorPane.setBottomAnchor(menuBar, 0D);
	    return anchorPane;
	}
	
	private Menu getFileMenu() {
		Menu menu = new Menu("_File");
		MenuItem exitMI = new MenuItem("_Exit");
		exitMI.setOnAction(action -> closeApp());
		menu.getItems().addAll(exitMI);
		return menu;
	}
	
	private Menu getTableMenu() {
		Menu menu = new Menu("_Table");
		MenuItem addMI = new MenuItem("_Add person");
		addMI.setAccelerator(new KeyCodeCombination(KeyCode.PLUS, KeyCombination.CONTROL_DOWN));
		addMI.setOnAction(action -> addItem());
		MenuItem removeMI = new MenuItem("_Remove person");
		removeMI.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN));
		removeMI.setOnAction(action -> removeItems());
		menu.getItems().addAll(addMI, removeMI);
		return menu;
	}
	
	private Node getLeftPane() {
		this.textArea.setText("");
		this.textArea.setWrapText(false);
		this.textArea.setPrefWidth(280D);
		this.textArea.setEditable(false);
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().add(this.textArea);
		AnchorPane.setTopAnchor(this.textArea, 0D);
	    AnchorPane.setLeftAnchor(this.textArea, 0D);
	    AnchorPane.setRightAnchor(this.textArea, 0D);
	    AnchorPane.setBottomAnchor(this.textArea, 0D);
	    return anchorPane;
	}
	
	private Node getCenterPane() {
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().add(this.tableView);
		AnchorPane.setTopAnchor(this.tableView, 0D);
	    AnchorPane.setLeftAnchor(this.tableView, 0D);
	    AnchorPane.setRightAnchor(this.tableView, 0D);
	    AnchorPane.setBottomAnchor(this.tableView, 0D);
	    return anchorPane;
	}
	
	private Node getBottomPane() {
		AnchorPane anchorPane = new AnchorPane();
		
		HBox hBox = new HBox();
		Label messageTitle = new Label("Massage: ");
		hBox.getChildren().addAll(messageTitle, this.message);
		
		anchorPane.getChildren().add(hBox);
		AnchorPane.setTopAnchor(hBox, 2D);
	    AnchorPane.setLeftAnchor(hBox, 2D);
	    AnchorPane.setRightAnchor(hBox, 2D);
	    AnchorPane.setBottomAnchor(hBox, 2D);
	    return anchorPane;
	}
	
	private void setMessage(String msg, String style) {
		this.message.setText(msg);
		this.message.setStyle(style);
	}
	
	private void setTextAreaContent() {
		ObservableList<Person> persons = tableView.getSelectionModel().getSelectedItems();
		StringBuilder stringBuilder = new StringBuilder("Selected people: \n");
		for(Person person : persons) {
			stringBuilder.append(person.getName() + " (" +
					person.getGender() + ")\n");
		}
		textArea.setText(stringBuilder.toString());
	}
	
	private void refreshTable() {
		tableView.getColumns().get(0).setVisible(false);
		tableView.getColumns().get(0).setVisible(true);
	}
	
	private void addItem() {
		Person person = new Person("-- Set name --",Gender.FEMALE,0,0);
		tableView.getItems().add(person);
	}
	
	private void removeItems() {
		ObservableList<Person> persons = tableView.getSelectionModel().getSelectedItems();
		
		if(persons != null && !persons.isEmpty()) {
			ObservableList<Person> observableList = FXCollections.observableArrayList(persons);
			
			ListView<Person> listView = new ListView<Person>(observableList);
			listView.setCellFactory(list -> {
				return new ListCell<Person>() {
					@Override
					protected void updateItem(Person item, boolean empty) {
						super.updateItem(item, empty);
						setText(null);
						if (empty) {
							setText(null);
						} else {
							setText(item.getName() + " (" + item.getGender() + ")");
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
				for(Person person : observableList) {
					removeItem(person);
				}
			}
		} else {
			setMessage(nothingSelectedDeletion,"-fx-text-fill: #ED1C23;");
		}
	}
	
	private void removeItem(Person item) {
		if(tableView.getItems().contains(item)) {
			tableView.getItems().remove(item);
		}
	}
	
	/** Ukonceni */
	public void closeApp() {
		Platform.exit();
	}
}
