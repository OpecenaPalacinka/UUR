import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * Demonstration of ListView and using of dialogs
 *
 * @author Jan Pelikán
 *
 */
public class Main extends Application {
    //novej listView
    ListView<DataModel> listView = new ListView<>();
    //Observable list
    ObservableList<DataModel> dataModels;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Seznam - Jan Pelikán");
        primaryStage.setScene(createScene());
        primaryStage.show();

    }

    private Scene createScene() {
        Scene scene = new Scene(getRoot(), 450, 500);
        return scene;
    }

    private Parent getRoot() {
        // The whole scene is created from BorderPane

        BorderPane mainPane = new BorderPane();

        mainPane.setCenter(getControlPane());

        return mainPane;
    }

    private Node getControlPane() {
        dataModels = createInitData();
        listView.setItems(dataModels);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setPadding(new Insets(15));

        VBox box1 = new VBox();
        box1.setSpacing(15);

        Button showModel = new Button("Vypiš model");
        showModel.setOnAction(p -> processSelection(listView.getSelectionModel().getSelectedItem()));
        box1.getChildren().addAll(listView, showModel);

        grid.add(box1, 0, 0);

        VBox box2 = new VBox();
        box2.setSpacing(15);

        FlowPane f1 = new FlowPane();
        f1.setHgap(15);
        Text t1 = new Text("Jméno");
        TextField tf1 = new TextField();
        f1.getChildren().addAll(t1, tf1);

        FlowPane f2 = new FlowPane();
        f2.setHgap(15);
        Text t2 = new Text("Příjmení");
        TextField tf2 = new TextField();

        f2.getChildren().addAll(t2, tf2);

        FlowPane f3 = new FlowPane();
        f3.setHgap(15);
        Text t3 = new Text("Ulice");
        TextField tf3 = new TextField();

        f3.getChildren().addAll(t3, tf3);

        FlowPane f4 = new FlowPane();
        f4.setHgap(15);
        Text t4 = new Text("Číslo popisné");
        TextField tf4 = new TextField();

        f4.getChildren().addAll(t4, tf4);

        Button akutalizovat = new Button("Akutalizovat");
        akutalizovat.setOnAction(p -> updatePerson(tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText()));

        box2.getChildren().addAll(f1, f2, f3, f4, akutalizovat);
        grid.add(box2, 1,0);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DataModel>() {
            @Override
            public void changed(ObservableValue<? extends DataModel> observableValue, DataModel oldData, DataModel newData) {
                tf1.setText(newData.getJmeno());
                tf2.setText(newData.getPrijemni());
                tf3.setText(newData.getUlice());
                tf4.setText(String.valueOf(newData.getPsc()));
            }
        });

        return grid;
    }

    /*
     * ------------------------------------------------------------------------------------------------
     *
     * Methods used for reaction on buttons - no creating of additional GUI elements
     *
     * ------------------------------------------------------------------------------------------------
     */

    private void updatePerson(String jmeno, String prijmeni, String ulice, String PSC)
    {
        listView.getSelectionModel().getSelectedItem().setJmeno(jmeno);
        listView.getSelectionModel().getSelectedItem().setPrijemni(prijmeni);
        listView.getSelectionModel().getSelectedItem().setUlice(ulice);
        listView.getSelectionModel().getSelectedItem().setPsc(Integer.parseInt(PSC));
    }



    private void processSelection(DataModel dataModel) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(String.format("Jméno: %s Příjmení:  %s  Ulice: %s  PSČ: %s", dataModel.getJmeno(), dataModel.getPrijemni(), dataModel.getUlice(), dataModel.getPsc()));
        alert.showAndWait();
    }


    private ObservableList<DataModel> createInitData() {
        // creating initial data
        ObservableList<DataModel> data = FXCollections.observableArrayList();

        data.add(new DataModel("Jana", "Nováková", "Blatenská", 35));
        data.add(new DataModel("Daniel", "Filip", "Pařížská", 456));
        data.add(new DataModel("Lenka", "Hradilová", "Americká", 34));
        data.add(new DataModel("Adéla", "Bernardová", "Náměstí", 11));
        data.add(new DataModel("Johana", "Malá", "NevimDalsiUlici", 5));

        return data;
    }
}

class DataModel {
    private String jmeno;
    private String prijemni;
    private String ulice;
    private int psc;

    public DataModel(String jmeno, String prijemni,String ulice,int psc){
        this.jmeno = jmeno;
        this.prijemni = prijemni;
        this.ulice = ulice;
        this.psc = psc;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijemni() {
        return prijemni;
    }

    public void setPrijemni(String prijemni) {
        this.prijemni = prijemni;
    }

    public String getUlice() {
        return ulice;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    @Override
    public String toString(){
        return  String.format("%s %s", jmeno, prijemni);
    }
}
