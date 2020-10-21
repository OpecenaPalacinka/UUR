package cz.zcu.kiv.lipka.uur.exercise05;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class ObserverDemo extends Application {
	private static final int MIN = -10;
	private static final int MAX = 10;
	private static final int INIT = 0;
	
	DataModel myModel = new DataModel(INIT, "Initial Data", MIN, MAX);
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ObserverDemo");		
		
		Scene mainScene = new Scene(createMainPane(), 500, 500);
		primaryStage.setScene(mainScene);
		
		primaryStage.show();
	}

	private Parent createMainPane() {
		BorderPane rootPane = new BorderPane();
		
		rootPane.setLeft(getControlPane());
		rootPane.setTop(getMenu());
		rootPane.setCenter(getLogPane());
		
		return rootPane;
	}
	
	private Node getLogPane() {
		// creating text area with 15 columns and infinite height
		ObservingTextArea logTA = new ObservingTextArea();
		logTA.setPrefColumnCount(25);
		logTA.setPrefHeight(Double.MAX_VALUE);		
		BorderPane.setMargin(logTA, new Insets(3));
		// adding text area as observer
		myModel.addPropertyChangeListener(logTA);
		
		return logTA;
	}
	
	private Node getControlPane() {
		VBox controlPane = new VBox();
		
		// creating observing label with width 70 pixel and centered text
		HBox labelPane = new HBox();
		Label valueLB = new Label();		
		valueLB.setPrefWidth(70);
		valueLB.setAlignment(Pos.CENTER);
		// adding label as observer
		valueLB.textProperty().bindBidirectional(myModel.integerDataProperty(), new NumberStringConverter());			
		
		// creating control buttons
		// incrementation
		Button incBT = new Button("+");
		incBT.setPrefWidth(30);
		incBT.setOnAction(event -> myModel.inc());
				
		// decrementation
		Button decBT = new Button("-");
		decBT.setPrefWidth(30);
		decBT.setOnAction(event -> myModel.dec());
		
		labelPane.getChildren().addAll(decBT, valueLB, incBT);
		labelPane.setSpacing(5);
		labelPane.setPadding(new Insets(3));
		
		// creating slider
		Slider valueSL = new Slider(MIN, MAX, INIT);
		valueSL.setShowTickLabels(true);
		valueSL.setShowTickMarks(true);
		valueSL.setMajorTickUnit(1);
		valueSL.valueProperty().bindBidirectional(myModel.integerDataProperty());
		
		// creting textField
		TextField fieldTF = new TextField();
		TextField mirrorLB = new TextField();
		
		fieldTF.textProperty().bindBidirectional(myModel.stringDataProperty());
		mirrorLB.textProperty().bind(myModel.stringDataProperty());
		
		controlPane.getChildren().addAll(labelPane, valueSL, fieldTF, mirrorLB);
		controlPane.setAlignment(Pos.CENTER);
		
		return controlPane;
	}
	
	private MenuBar getMenu() {
		MenuBar bar = new MenuBar();
		
		// creating first menu
		// _ is there as preparation for Mnemonics 
		// letter after _ will be used as mnemonics
		Menu fileMenu = new Menu("_File");			
		MenuItem exitMI = new MenuItem("E_xit");	
		// exiting application
		exitMI.setOnAction(event -> Platform.exit());		
		fileMenu.getItems().add(exitMI);
		
		// creating second menu, inc and dec actions
		Menu actionMenu = new Menu("_Actions");
				
		// creating incrementation menu item
		MenuItem incMI = new MenuItem("_Increment");
		incMI.setOnAction(event -> myModel.inc());
		// adding accelerator key (+ and CTRL) to this menu item
		incMI.setAccelerator(new KeyCodeCombination(KeyCode.ADD, KeyCombination.CONTROL_DOWN));
		
		MenuItem decMI = new MenuItem("_Decrement");
		decMI.setOnAction(event -> myModel.dec());
		decMI.setAccelerator(new KeyCodeCombination(KeyCode.SUBTRACT, KeyCombination.CONTROL_DOWN));
		
		actionMenu.getItems().addAll(incMI, decMI);
		
		bar.getMenus().addAll(fileMenu, actionMenu);
		
		return bar;
	}	
}
