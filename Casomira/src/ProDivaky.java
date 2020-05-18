import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProDivaky {

    public ProDivaky(){
        Stage stage1 = new Stage();
        Scene scene = new Scene(createRootPane());
        stage1.setScene(scene);
        stage1.setMinHeight(550);
        stage1.setResizable(false);
        stage1.show();
    }

    private Parent createRootPane(){
        BorderPane rootPane = new BorderPane();

        rootPane.setLeft(createLeft());
        rootPane.setCenter(createMid());
        rootPane.setRight(createRight());

        return rootPane;
    }

    public Node createRight(){
        VBox pravaLista = new VBox();

        TextField jmenoTymu2 = new TextField();
        jmenoTymu2.setEditable(false);
        jmenoTymu2.textProperty().bind(ProPoradatele.tym2.jmenoTymuProperty());
        jmenoTymu2.setMinSize(300,50);
        jmenoTymu2.setMaxSize(300,50);

        TableView<Tym2> tymVylouceni22 = new TableView<>();
        tymVylouceni22.setEditable(false);
        tymVylouceni22.setMinSize(250,130);
        tymVylouceni22.setMaxSize(250,130);

        TableColumn<Tym2,String> jmenoCol22 = new TableColumn<>("Jméno hráče");
        jmenoCol22.setPrefWidth(133);
        jmenoCol22.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));
        jmenoCol22.setCellFactory(column -> new ProPoradatele.ConsumingTextFieldTableCell<>());

        TableColumn<Tym2,String> casCol22 = new TableColumn<>("Doba vyloučení");
        casCol22.setPrefWidth(117);

        //noinspection unchecked
        tymVylouceni22.getColumns().addAll(jmenoCol22,casCol22);

        TableView<Tym2> tymVylouceni52 = new TableView<>();
        tymVylouceni52.setEditable(false);
        tymVylouceni52.setMinSize(250,130);
        tymVylouceni52.setMaxSize(250,130);

        TableColumn<Tym2,String> jmenoCol52 = new TableColumn<>("Jméno hráče");
        jmenoCol52.setPrefWidth(133);
        jmenoCol52.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));
        jmenoCol52.setCellFactory(column -> new ProPoradatele.ConsumingTextFieldTableCell<>());

        TableColumn<Tym2,String> casCol52 = new TableColumn<>("Doba vyloučení");
        casCol52.setPrefWidth(117);

        //noinspection unchecked
        tymVylouceni52.getColumns().addAll(jmenoCol52,casCol52);

        pravaLista.setSpacing(25);
        pravaLista.setPadding(new Insets(25,45,0,0));
        pravaLista.setAlignment(Pos.TOP_CENTER);
        pravaLista.getChildren().addAll(jmenoTymu2, tymVylouceni22,tymVylouceni52);
        return pravaLista;
    }

    public Node createMid(){
        VBox prostredek = new VBox();
        FlowPane tretina = new FlowPane();
        FlowPane horejsek = new FlowPane();

        Label tretinaLabel = new Label("Třetina:");
        tretinaLabel.setFont(new Font(25));
        TextField tretinaField = new TextField();
        tretinaField.textProperty().bind(Bindings.convert(ProPoradatele.casATretina.tretinaProperty()));
        tretinaField.setMinSize(30,50);
        tretinaField.setMaxSize(30,50);
        tretina.setAlignment(Pos.TOP_CENTER);
        tretina.setHgap(15);
        tretina.getChildren().addAll(tretinaLabel,tretinaField);


        TextField skore1 = new TextField();
        skore1.textProperty().bind(Bindings.convert(ProPoradatele.tym1.pocetGoluProperty()));
        Label dvojtecka = new Label(":");
        dvojtecka.setFont(new Font(65));

        TextField skore2 = new TextField();
        skore2.textProperty().bind(Bindings.convert(ProPoradatele.tym2.pocetGoluProperty()));

        horejsek.setAlignment(Pos.CENTER);
        horejsek.setHgap(30);
        skore1.setMinSize(60,100);
        skore1.setMaxSize(60,100);
        skore2.setMinSize(60,100);
        skore2.setMaxSize(60,100);
        horejsek.getChildren().addAll(skore1,dvojtecka,skore2);

        TextField cas = new TextField();
        cas.setEditable(false);
        cas.setMinSize(400,150);
        cas.setMaxSize(400,150);
        cas.setAlignment(Pos.TOP_CENTER);
        cas.setText("Aktualni cas");

        FlowPane timeout = new FlowPane();
        CheckBox timeout1 = new CheckBox();
        timeout1.selectedProperty().bind(ProPoradatele.tym1.timeoutProperty());
        Label timeoutLabel = new Label("Timeout");
        CheckBox timeout2 = new CheckBox();
        timeout2.selectedProperty().bind(ProPoradatele.tym2.timeoutProperty());

        timeout.setHgap(25);
        timeout.setAlignment(Pos.CENTER);
        timeout.getChildren().addAll(timeout1,timeoutLabel,timeout2);

        prostredek.setPadding(new Insets(30,30,0,30));
        prostredek.setSpacing(50);
        prostredek.setAlignment(Pos.TOP_CENTER);
        prostredek.getChildren().addAll(horejsek,timeout,tretina,cas);
        return prostredek;
    }

    public Node createLeft(){
        VBox levaLista = new VBox();

        TextField jmenoTymu1 = new TextField();
        jmenoTymu1.setEditable(false);
        jmenoTymu1.textProperty().bind(ProPoradatele.tym1.jmenoTymuProperty());
        jmenoTymu1.setMinSize(300,50);
        jmenoTymu1.setMaxSize(300,50);

        TableView<Tym1> tymVylouceni21 = new TableView<>();
        tymVylouceni21.setEditable(false);
        tymVylouceni21.setMinSize(250,130);
        tymVylouceni21.setMaxSize(250,130);

        TableColumn<Tym1,String> jmenoCol21 = new TableColumn<>("Jméno hráče");
        jmenoCol21.setPrefWidth(133.2);
        jmenoCol21.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));
        jmenoCol21.setCellFactory(column -> new ProPoradatele.ConsumingTextFieldTableCell<>());

        TableColumn<Tym1,String> casCol21 = new TableColumn<>("Doba vyloučení");
        casCol21.setPrefWidth(117);

        //noinspection unchecked
        tymVylouceni21.getColumns().addAll(jmenoCol21,casCol21);

        TableView<Tym1> tymVylouceni51 = new TableView<>();
        tymVylouceni51.setEditable(false);
        tymVylouceni51.setMinSize(250,130);
        tymVylouceni51.setMaxSize(250,130);

        TableColumn<Tym1,String> jmenoCol51 = new TableColumn<>("Jméno hráče");
        jmenoCol51.setPrefWidth(133);
        jmenoCol51.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));
        jmenoCol51.setCellFactory(column -> new ProPoradatele.ConsumingTextFieldTableCell<>());

        TableColumn<Tym1,String> casCol51 = new TableColumn<>("Doba vyloučení");
        casCol51.setPrefWidth(117);

        //noinspection unchecked
        tymVylouceni51.getColumns().addAll(jmenoCol51,casCol51);

        levaLista.setSpacing(25);
        levaLista.setPadding(new Insets(25,0,0,45));
        levaLista.setAlignment(Pos.TOP_CENTER);
        levaLista.getChildren().addAll(jmenoTymu1, tymVylouceni21, tymVylouceni51);
        return levaLista;
    }
}
