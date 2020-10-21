package cz.zcu.kiv.lipka.uur.exercise10;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * Demonstration of internationalization, based on API calls
 * 
 * Application allows change of language on the runtime, it is
 * able to keep it state (the selected date)
 * 
 * @author Richard Lipka
 *
 */
public class InterDemo extends Application {
	// definition of used language and country codes
	public static final String DEF_LANGUAGE = "cs";
	public static final String DEF_COUNTRY = "CZ";
	
	public static final String CZ_LANGUAGE = "cs";
	public static final String CZ_COUNTRY = "CZ";

	public static final String UK_LANGUAGE = "en";
	public static final String UK_COUNTRY = "UK";

	// paths to files with flag images
	public static final String CZ_FLAG = "/IMG/cz.gif";
	public static final String UK_FLAG = "/IMG/uk.gif";
	
	// default height and width of the window
	public static final int WIDTH = 300;
	public static final int HEIGHT = 250;
	
	// reference on all labeled elements, that have to be changed
	// when language is changed
	// it is necessary in order to allow change of language on runtime, 
	// without restarting the application 
	// when language is set only during start of the application, the
	// labels are set during construction of nodes in the tree
	private MenuItem newMI, openMI, storeMI, closeMI, copyMI, insertMI, helpMI;
	private Menu fileM, editM, helpM;
	private Label controlLB, errorLB, outputLB;
	private Button controlBT, translateBT;
	private DatePicker dateDP;
	private Stage primaryStage;
	
	// images with Czech and British flag, reference on actual flag
	private Image ukFlag, czFlag, actualFlag;
	
	// reference on actual set of texts
	// the texts are loaded every time when language is changed
	// if frequent language changes are expected, it is better to 
	// keep all used texts loaded in a collection
	private Messages messages;

	public static void main(String[] args) {
		launch(args);
	}
	
	// initialization of application
	public void init() {
		// setting default locale that will be used by application
		// for example DatePicker is created with texts according to 
		// actual locale
		Locale.setDefault(new Locale(DEF_LANGUAGE, DEF_COUNTRY));
		
		// loading texts for actual locale 
		messages = new Messages(Locale.getDefault());
		
		// loading images (not creating ImageView - it is not possible		
		// in this moment)
		loadImages();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		primaryStage.setTitle(messages.getString("frameCaption"));
		primaryStage.setScene(createScene());
		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		
		primaryStage.show();		
	}
	
	private Scene createScene() {
		Scene scene = new Scene(getRoot());		
		return scene;
	}
	
	private Parent getRoot() {
		BorderPane rootPane = new BorderPane();
		
		rootPane.setTop(getMenu());
		rootPane.setCenter(getContent());
		
		return rootPane;
	}
	
	private MenuBar getMenu() {
		MenuBar menuBar = new MenuBar();
		
		// all descriptions are taken from actual set of texts, 
		// not directly from texts in source code
		fileM = new Menu(messages.getString("menuFileCaption"));
		editM = new Menu(messages.getString("menuEditCaption"));
		helpM = new Menu(messages.getString("menuHelpCaption"));
		
		newMI = new MenuItem(messages.getString("menuNewCaption"));
		openMI = new MenuItem(messages.getString("menuOpenCaption"));
		storeMI = new MenuItem(messages.getString("menuSaveCaption"));
		closeMI = new MenuItem(messages.getString("menuExitCaption"));
		
		fileM.getItems().addAll(newMI, openMI, storeMI, closeMI);
		
		copyMI = new MenuItem(messages.getString("menuCopyCaption"));
		insertMI = new MenuItem(messages.getString("menuPasteCaption"));
		
		editM.getItems().addAll(copyMI, insertMI);
		
		helpMI = new MenuItem(messages.getString("menuAboutCaption"));
		
		helpM.getItems().addAll(helpMI);		
		
		menuBar.getMenus().addAll(fileM, editM, helpM);
		
		return menuBar;
	}
	
	private Node getContent() {
		VBox contentPane = new VBox();
		
		controlLB = new Label(messages.getString("fullText"));
		errorLB = new Label(messages.getString("errorText"));
		
		controlBT = new Button(messages.getString("buttonDesc"));
		controlBT.setGraphic(new ImageView(actualFlag));
		controlBT.setMinWidth(150);
		controlBT.setOnAction(event -> changeLanguage());
		
		dateDP = new DatePicker();
		
		translateBT = new Button(messages.getString("translateButton"));
		translateBT.setPrefWidth(150);
		translateBT.setOnAction(event -> createText());
		
		outputLB = new Label("");		
		
		contentPane.getChildren().addAll(controlLB, errorLB, controlBT, dateDP, translateBT, outputLB);
		contentPane.setAlignment(Pos.CENTER);
		contentPane.setSpacing(5);
		
		return contentPane;
	}	
	
	// Handling change of languages
	private void changeLanguage() {		
		
		// only two languages are switched
		if (Locale.getDefault().getLanguage().compareTo("cs") == 0) {
			// when language is switched, new locale is created
			Locale.setDefault(new Locale(UK_LANGUAGE, UK_COUNTRY));
			// reference on actual flag is set
			actualFlag = ukFlag;
		} else {
			Locale.setDefault(new Locale(CZ_LANGUAGE, CZ_COUNTRY));
			actualFlag = czFlag;			
		}
		// texts for components are loaded according to actual 
		// (already changed) locale
		messages = new Messages(Locale.getDefault());
		// actualization of all texts and other elements
		actualizeLocale();
	}
	
	// Transforamtion of date from DatePicker to label
	private void createText() {
		// creates formatter according to current locale
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		
		LocalDate date = dateDP.getValue();
		
		if (date != null) {
			// using formatter to convert date to String according to provided pattern
			outputLB.setText(formatter.format(date));
		}
	}	
	
	// loading images and setting reference on actual flag
	private void loadImages() {
		czFlag = new Image(getClass().getResourceAsStream(CZ_FLAG));
		ukFlag = new Image(getClass().getResourceAsStream(UK_FLAG));
		
		if (Locale.getDefault().getLanguage().compareTo(CZ_LANGUAGE) == 0) {
			actualFlag = czFlag;
		}
		if (Locale.getDefault().getLanguage().compareTo(UK_LANGUAGE) == 0) {
			actualFlag = ukFlag;
		}		
	}	
	
	// actualization of texts
	private void actualizeLocale() {
		// setting texts on all localized components	
		primaryStage.setTitle(messages.getString("frameCaption"));
		
		fileM.setText(messages.getString("menuFileCaption"));
		editM.setText(messages.getString("menuEditCaption"));
		helpM.setText(messages.getString("menuHelpCaption"));
		
		newMI.setText(messages.getString("menuNewCaption"));
		openMI.setText(messages.getString("menuOpenCaption"));
		storeMI.setText(messages.getString("menuSaveCaption"));
		closeMI.setText(messages.getString("menuExitCaption"));
		
		copyMI.setText(messages.getString("menuCopyCaption"));
		insertMI.setText(messages.getString("menuPasteCaption"));		
		
		helpMI.setText(messages.getString("menuAboutCaption"));
		
		controlLB.setText(messages.getString("fullText"));
		// this is just demonstration of reaction on non-existing
		// key in set of texts
		errorLB.setText(messages.getString("errorText"));
		controlBT.setText(messages.getString("buttonDesc"));
		
		translateBT.setText(messages.getString("translateButton"));		
		
		// setting actual flag on the button
		controlBT.setGraphic(new ImageView(actualFlag));		
		
		// creates and changes DatePicker
		// DatePicker contains texts obtained from actual Locale
		// but when locale is changed, not all texts are really actualized
		// the only safe way is now to create new datePiceker after locale
		// is changed, remove previous DatePicker and insert a new one
		LocalDate date = dateDP.getValue();
		Pane parent = (Pane)dateDP.getParent();
		
		int position = parent.getChildren().indexOf(dateDP);
		
		// removing current DatePicker
		parent.getChildren().remove(dateDP);
		
		// crates and adding new DatePicker with actual texts
		dateDP = new DatePicker(date);
		parent.getChildren().add(position, dateDP);		
		
		// removes old text from label
		outputLB.setText("");
	}	
}
