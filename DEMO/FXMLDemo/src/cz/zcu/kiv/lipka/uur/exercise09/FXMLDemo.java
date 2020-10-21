package cz.zcu.kiv.lipka.uur.exercise09;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main class of the FXML demo application
 * 
 * Loads FXML and CSS file from given address and 
 * displays them
 * 
 * @author Richard Lipka
 *
 */
public class FXMLDemo extends Application {
	// paths to FXML and CSS files - path relative to classpath
	// FXML in FXML subdir - this have to be in the same folder as package "cz"
	// works in .jar file 
	private static final String MAIN_WINDOW_XML = "/FXML/TableWindow.fxml";
	// CSS in CSS subdir - this have to be in the same folder as package "cz"
	// works in .jar file 
	private static final String MAIN_WINDOW_CSS = "/CSS/basicStyle.css";
	
	private FXMLLoader loader;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Table demo with FXML");
		// obtains scene generated from FXML file
		primaryStage.setScene(createScene());
		primaryStage.show();		
	}
	
	private Scene createScene() {
		// loading FXML file
		loader = new FXMLLoader(getClass().getResource(MAIN_WINDOW_XML));
		// loading CSS file
		String css = getClass().getResource(MAIN_WINDOW_CSS).toExternalForm();
						
		Pane rootPane = null;
		
		try {
			// obtaining root pane from loaded FXML
			rootPane = (Pane)loader.load();			
		// when FXML is not found or cannot be parsed, an exception occurs
		// this will display dialog with information about exception
		// when debugging, it is better to replace this with
		// e.printStackTrace to have a better information about exception 
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Window cannot be created");
			alert.setHeaderText("Cannot load XML file with definition of main window");
			alert.setContentText("file: " + MAIN_WINDOW_XML + "\n" + 
					             "Error: " + e.getMessage() + "\n" +
					             "Exception: " + e.getClass().getName());			
			alert.showAndWait();
			System.exit(1);
		}
		
		// creating scene from loaded panel
		Scene scene = new Scene(rootPane);
		// removing all CSS from the scene - only default look is used
		scene.getStylesheets().clear();
		// adding loaded CSS
		scene.getStylesheets().add(css);
		
		return scene;
	}
}
