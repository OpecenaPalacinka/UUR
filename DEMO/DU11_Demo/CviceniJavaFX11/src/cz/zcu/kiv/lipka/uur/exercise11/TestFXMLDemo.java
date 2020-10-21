package cz.zcu.kiv.lipka.uur.exercise11;

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
public class TestFXMLDemo extends Application {
	// path to FXML file
	public static final String MAIN_WINDOW_XML = "/FXML/InterWindow.fxml";
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Person editation");
		
		primaryStage.setScene(createScene());
	
		primaryStage.show();		
	}
	
	// method for creating scene - it is public in this case, as it is used by controller
	// to switch languages (load the scene again with different locale)
	public Scene createScene() {	
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_WINDOW_XML));
		
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
		
		Scene scene = new Scene(rootPane);		
	
		return scene;
	}
}
