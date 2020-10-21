package cz.zcu.kiv.lipka.uur.exercise12;

import java.util.List;

import cz.zcu.kiv.lipka.uur.exercise12.data.Port;
import cz.zcu.kiv.lipka.uur.exercise12.data.Ship;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class GraphDemo extends Application {
	private DataSource data = new DataSource();
	private FlowPane graphPane;
	private TextArea text;
	private PieChart generalChart;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(createScene());
		primaryStage.show();
	}
	
	protected Scene createScene() {
		Scene scene = new Scene(getRoot());
		
		return scene;
	}
	
	protected Parent getRoot() {
		BorderPane rootPane = new BorderPane();
		
		rootPane.setCenter(getGraphPane());
		rootPane.setRight(getDescPane());
		
		return rootPane;
	}
	
	protected Node getGraphPane() {
		graphPane = new FlowPane();
		graphPane.setMinWidth(400);
		graphPane.setMinHeight(450);		
		
		graphPane.getChildren().add(getGeneralChart());
		
		return graphPane;
	}
	
	protected Node getDescPane() {
		text = new TextArea();
		text.setPrefColumnCount(20);
		text.setEditable(false);
		
		return text;
	}
	
	
	// Method for creating a pie chart, with general data about ports and 
	// total tonnage of all ships in the ports
	protected Chart getGeneralChart(){
		// preparation of data for Piechart (1D, but each value have a name assigned)
		ObservableList<PieChart.Data> generalData = FXCollections.observableArrayList();
		List<Port> ports = data.getPorts();
		
		// generating data for all ports - for each port a new data item is created, 
		// from name and tonnage
		ports.stream()
			 .forEach(port -> generalData.add(new PieChart.Data(port.getName(), data.getPortTonnage(port))));
		
		// creating pie chart
		generalChart = new PieChart(generalData);
		// setting a layout of pie chart
		// length of lines from elements to labels 
		generalChart.setLabelLineLength(20);
		// position of legenda
		generalChart.setLegendSide(Side.RIGHT);
		// title of chart
		generalChart.setTitle("Actual port tonnage:");		
	
		// setting reaction for each element of the pie chart 
		// (not for the whole graph - each element should react in a different way)
		for (final PieChart.Data graphData : generalChart.getData()) {			
			// when mouse enters the element, description of the appropriate port is created
			graphData.getNode().setOnMouseEntered(event -> {				
		    	createText(data.getPortByName(graphData.getName()), null);
		    });
			// when mouse leaves the element, description of the port is removed 
			graphData.getNode().setOnMouseExited(event -> {
		    	createText(null, null);
			});			
			// when user clicks on element, detail graph with information
			// about all ships in the port  is displayed
			graphData.getNode().setOnMouseClicked(event -> {
		    	insertDetailedGraph(ports.get(data.getPortID(graphData.getName())));
		    });		    
		}		
		
		return generalChart;		
	}
	
	// replacing general graph with detailed graph of selected port
	protected void insertDetailedGraph(Port port) {
		graphPane.getChildren().remove(0);
		graphPane.getChildren().add(getDetailedChart(port));	
	}
	
	// replacing detailed graph with general graph
	protected void insertGeneralGraph() {
		graphPane.getChildren().remove(0);
		graphPane.getChildren().add(generalChart);		
	}
	
	// creates detailed graph as a bar chart
	protected Node getDetailedChart(Port port) {
		BorderPane detailedGraphPane = new BorderPane();
		
		// bar chart use 2 axis - x axis deals with categories (ships in our case)
		CategoryAxis xAxis = new CategoryAxis();
		// 	                    - y axis deals with values (ship tonnage)
        NumberAxis yAxis = new NumberAxis();	
        
        // setting labels for both axes
        xAxis.setLabel("Ship");
        yAxis.setLabel("Tonnage [t]");      

        // creates bar chart with provided axes
		BarChart<String, Integer> detailChart = new BarChart(xAxis, yAxis);
		// creates one data serie that will be displayed in bar chart
		// bar chart can display several groups of data, each serie is considered
		// to be one group (in Line chart each serie would be a separate line)
		XYChart.Series<String, Integer> serie = new XYChart.Series<String, Integer>();
		
		// creates data for each ship, each data element is composed from name and ship tonnage
		List<Ship> ships = data.getShipsByPort(port);
		ships.stream()
			 .forEach(ship -> serie.getData().add(new XYChart.Data<String, Integer>(ship.getName(), ship.getTonnage())));
		
		// adding data to the chart
		detailChart.getData().addAll(serie);
		// disabling lengend - only one group is used, so there is no need for legend,
		// only data for one port are displayed
		detailChart.setLegendVisible(false);
		
		// adding reaction of each graph element to mouse movement - very similar to 
		// pie chart
		for (final XYChart.Series<String, Integer> graphSerie : detailChart.getData()) {
			for (final XYChart.Data<String, Integer> graphData : graphSerie.getData()) {
				graphData.getNode().setOnMouseEntered(event -> {
					createText(port, data.getShipByName(graphData.getXValue()));
			    });				
				graphData.getNode().setOnMouseExited(event -> {
					createText(port, null);
				});
			}
		}			 
		
		// adding chart to the pane
		detailedGraphPane.setCenter(detailChart);
		
		// adding control button that allows to return to general chart
		Button returnButton = new Button("Back");
		returnButton.setOnAction(event -> insertGeneralGraph());
		BorderPane.setAlignment(returnButton, Pos.TOP_LEFT);
		BorderPane.setMargin(returnButton, new Insets(3));
		detailedGraphPane.setTop(returnButton);		
		
		return detailedGraphPane;
	}
	
	// creates description of port or port and ship
	private void createText(Port port, Ship ship) {
		text.setText("");
		if (port != null) {
			text.appendText("Port: " + port.getName() + "\nTotal tonnage: " + data.getPortTonnage(port) + " t.");
		}
		if (ship != null) {
			text.appendText("\nShip: " + ship.getName() + "\nTonnage: " + ship.getTonnage() + " t.");
		}
	}
}
