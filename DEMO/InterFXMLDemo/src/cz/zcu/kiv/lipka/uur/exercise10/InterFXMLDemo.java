package cz.zcu.kiv.lipka.uur.exercise10;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Demonstration of internationalization, based on FXML
 * 
 * Application allows change of language on the runtime, it is
 * able to keep it state (the selected date)
 *   
 * @author Richard Lipka
 *
 */
public class InterFXMLDemo extends Application {
	// path to FXML file
	public static final String MAIN_WINDOW_XML = "/FXML/InterWindow.fxml";
	// path to file with localized strings
	public static final String TEXT_FILE = "LANG/Texts";
	
	// definition of used language and country codes
	public static final String DEF_LANGUAGE = "cs";
	public static final String DEF_COUNTRY = "CZ";
	
	public static final String CZ_LANGUAGE = "cs";
	public static final String CZ_COUNTRY = "CZ";

	public static final String UK_LANGUAGE = "en";
	public static final String UK_COUNTRY = "UK";
	
	// default dimension of the window
	public static final int HEIGHT = 300;
	public static final int WIDTH = 250;

	// reference on loaded localized texts
	private ResourceBundle texts;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// initialization of program - loading of localized strings for
	// default locale
	public void init() {
		// creates locale for default settings 
		Locale.setDefault(new Locale(DEF_LANGUAGE, DEF_COUNTRY));
		// loads localized texts
		texts = ResourceBundle.getBundle(TEXT_FILE, Locale.getDefault());
		
		// creates object for sharing references on stage, application and other
		// usefull data between Application and Controller
		SharedData data = SharedData.getSharedData();
		// setting reference on the application
		data.setApplication(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// storing reference on primary stage - note that SharedData is singleton object, 
		// so it can be easily retrieved on any place in the application
		SharedData data = SharedData.getSharedData();		
		data.setPrimaryStage(primaryStage);
		
		primaryStage.setTitle(texts.getString("frameCaption"));
		
		primaryStage.setScene(createScene());
		primaryStage.setMinWidth(HEIGHT);
		primaryStage.setMinHeight(WIDTH);
		
		primaryStage.show();		
	}
	
	// method for creating scene - it is public in this case, as it is used by controller
	// to switch languages (load the scene again with different locale)
	public Scene createScene() {	
		// obtaining new localized texts with actual locale		
		texts = ResourceBundle.getBundle(InterFXMLDemo.TEXT_FILE, Locale.getDefault());
		// loads FXML file, with localized strings that should be used to replace 
		// variables in FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_WINDOW_XML), texts);
		
		Pane rootPane = null;
		
		try {
			rootPane = (Pane)loader.load();
		} catch (Exception e) {
			// exception here can be caused by missing resource in file with localized strings
			// or by problems in FXML file 
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Window cannot be created");
			alert.setHeaderText("Error while loading XML file with definition of main window");
			alert.setContentText("file: " + MAIN_WINDOW_XML + "\n" + 
					             "Error: " + e.getMessage() + "\n" +
					             "Exception: " + e.getClass().getName());			
			alert.showAndWait();
			System.exit(1);
		}
		
		Scene scene = new Scene(rootPane, HEIGHT, WIDTH);
		
		// when scene is created and a date is stored (probably because language was
		// changed and now Scene is recreated with empty DatePicker), reference on 
		// the controller is obtained and used to set a date to DatePicker
		// It cannot be done in handling of this event in controller - when scene was 
		// loaded from XML file new controller was created and the service of this event
		// was invoked from the old controller - it don't have a reference on the new
		// DatePicker
		SharedData data = SharedData.getSharedData();
		if (data.getStoredDate() != null) {
			// obtaining controller from loader, setting date in DatePicker
			((InterController)loader.getController()).setDate(data.getStoredDate());
		}		
	
		return scene;
	}
}
