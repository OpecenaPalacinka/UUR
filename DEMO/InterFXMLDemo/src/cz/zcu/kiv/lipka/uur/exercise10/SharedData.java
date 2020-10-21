package cz.zcu.kiv.lipka.uur.exercise10;

import java.time.LocalDate;

import javafx.stage.Stage;

/**
 * Class for sharing data between application and controller
 * created as singleton to facilitate obtaining from any part of the application
 * 
 * @author Richard Lipka
 *
 */
public class SharedData {
	private Stage primaryStage;
	private InterFXMLDemo application;
	private LocalDate storedDate;
	
	private static SharedData singleton;
	
	private SharedData() {		
	}
	
	public static SharedData getSharedData() {
		if (singleton == null) {
			singleton = new SharedData();
		}
		
		return singleton;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public InterFXMLDemo getApplication() {
		return application;
	}
	
	public void setApplication(InterFXMLDemo application) {
		this.application = application;
	}

	public LocalDate getStoredDate() {
		return storedDate;
	}

	public void setStoredDate(LocalDate storedDate) {
		this.storedDate = storedDate;
	}	
}
