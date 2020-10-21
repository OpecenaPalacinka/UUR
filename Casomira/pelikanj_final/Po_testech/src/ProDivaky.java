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
        ProPoradatele.pocitadloProDivaky++;
        stage1.showAndWait();
        ProPoradatele.pocitadloProDivaky--;
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

        Label jmenoTymu2 = new Label();
        jmenoTymu2.setFont(new Font(25));
        jmenoTymu2.textProperty().bind(ProPoradatele.tym2.jmenoTymuProperty());
        jmenoTymu2.setMinSize(300,50);
        jmenoTymu2.setMaxSize(300,50);

        TableView<Hrac2> tymVylouceni22 = new TableView<>();
        tymVylouceni22.setEditable(false);
        tymVylouceni22.setMinSize(250,130);
        tymVylouceni22.setMaxSize(250,130);
        tymVylouceni22.itemsProperty().bind(ProPoradatele.tableNaVylouceni22.itemsProperty());
        tymVylouceni22.setPlaceholder(new Label("Žádné vyloučení"));

        TableColumn<Hrac2,String> jmenoCol22 = new TableColumn<>("Jméno hráče");
        jmenoCol22.setPrefWidth(133);
        jmenoCol22.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));

        TableColumn<Hrac2,String> casCol22 = new TableColumn<>("Doba vyloučení");
        casCol22.setPrefWidth(117);
        casCol22.setCellValueFactory(param -> Bindings.createStringBinding(()-> (param.getValue().getVylouceni().getMinuty() +":"+param.getValue().getVylouceni().getVteriny()),param.getValue().getVylouceni().minutyProperty(),param.getValue().getVylouceni().vterinyProperty()));


        //noinspection unchecked
        tymVylouceni22.getColumns().addAll(jmenoCol22,casCol22);

        TableView<Hrac2> tymVylouceni52 = new TableView<>();
        tymVylouceni52.setEditable(false);
        tymVylouceni52.setMinSize(250,130);
        tymVylouceni52.setMaxSize(250,130);
        tymVylouceni52.itemsProperty().bind(ProPoradatele.tableNaVylouceni52.itemsProperty());
        tymVylouceni52.setPlaceholder(new Label("Žádné vyloučení"));

        TableColumn<Hrac2,String> jmenoCol52 = new TableColumn<>("Jméno hráče");
        jmenoCol52.setPrefWidth(133);
        jmenoCol52.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));

        TableColumn<Hrac2,String> casCol52 = new TableColumn<>("Doba vyloučení");
        casCol52.setPrefWidth(117);
        casCol52.setCellValueFactory(param -> Bindings.createStringBinding(()-> (param.getValue().getVylouceni().getMinuty() +":"+param.getValue().getVylouceni().getVteriny()),param.getValue().getVylouceni().minutyProperty(),param.getValue().getVylouceni().vterinyProperty()));


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
        Label tretinaField = new Label();
        tretinaField.setFont(new Font(25));
        tretinaField.textProperty().bind(Bindings.convert(ProPoradatele.casATretina.tretinaProperty()));
        tretinaField.setMinSize(30,50);
        tretinaField.setMaxSize(30,50);
        tretina.setAlignment(Pos.TOP_CENTER);
        tretina.setHgap(15);
        tretina.getChildren().addAll(tretinaLabel,tretinaField);


        Label skore1 = new Label();
        skore1.setFont(new Font(50));
        skore1.textProperty().bind(Bindings.convert(ProPoradatele.tym1.pocetGoluProperty()));
        Label dvojtecka = new Label(":");
        dvojtecka.setPadding(new Insets(-15,0,0,0));
        dvojtecka.setFont(new Font(50));

        Label skore2 = new Label();
        skore2.setFont(new Font(50));
        skore2.textProperty().bind(Bindings.convert(ProPoradatele.tym2.pocetGoluProperty()));

        horejsek.setAlignment(Pos.CENTER);
        horejsek.setHgap(30);

        horejsek.getChildren().addAll(skore1,dvojtecka,skore2);

        HBox cas = new HBox();
        Label minuty = new Label();
        Label dvojteckaCas = new Label(":");
        Label vteriny = new Label();
        minuty.setFont(new Font(70));
        dvojteckaCas.setFont(new Font(75));
        vteriny.setFont(new Font(70));
        dvojteckaCas.setPadding(new Insets(-15,0,0,0));
        minuty.textProperty().bind(Bindings.convert(ProPoradatele.casATretina.minutyProperty().asString("%02d")));
        vteriny.textProperty().bind(Bindings.convert(ProPoradatele.casATretina.vterinyProperty().asString("%02d")));

        cas.setMinSize(400,150);
        cas.setMaxSize(400,150);
        cas.setAlignment(Pos.CENTER);
        cas.getChildren().addAll(minuty,dvojteckaCas,vteriny);
        FlowPane timeout = new FlowPane();

        CheckBox timeout1 = new CheckBox();
        Label timeoutCas1 = new Label();
        timeoutCas1.textProperty().bind(Bindings.createStringBinding(()-> (ProPoradatele.param.getMinuty() +":"+ProPoradatele.param.getVteriny())
                ,ProPoradatele.param.minutyProperty(),ProPoradatele.param.vterinyProperty()));
        timeout1.setDisable(true);
        timeout1.selectedProperty().bind(ProPoradatele.tym1.timeoutProperty());
        Label timeoutLabel = new Label("Timeout");
        CheckBox timeout2 = new CheckBox();
        Label timeoutCas2 = new Label();
        timeoutCas2.textProperty().bind(Bindings.createStringBinding(()-> (ProPoradatele.param2.getMinuty() +":"+ProPoradatele.param2.getVteriny())
                ,ProPoradatele.param2.minutyProperty(),ProPoradatele.param2.vterinyProperty()));
        timeout2.selectedProperty().bind(ProPoradatele.tym2.timeoutProperty());
        timeout2.setDisable(true);
        timeout.setHgap(25);
        timeout.setAlignment(Pos.CENTER);
        timeout.getChildren().addAll(timeoutCas1,timeout1,timeoutLabel,timeout2,timeoutCas2);

        prostredek.setPadding(new Insets(30,30,0,30));
        prostredek.setSpacing(50);
        prostredek.setAlignment(Pos.TOP_CENTER);
        prostredek.getChildren().addAll(horejsek,timeout,tretina,cas);
        return prostredek;
    }

    public Node createLeft(){
        VBox levaLista = new VBox();

        Label jmenoTymu1 = new Label();
        jmenoTymu1.setFont(new Font(25));
        jmenoTymu1.textProperty().bind(ProPoradatele.tym1.jmenoTymuProperty());
        jmenoTymu1.setMinSize(300,50);
        jmenoTymu1.setMaxSize(300,50);

        TableView<Hrac1> tymVylouceni21 = new TableView<>();
        tymVylouceni21.setEditable(false);
        tymVylouceni21.setMinSize(250,130);
        tymVylouceni21.setMaxSize(250,130);
        tymVylouceni21.itemsProperty().bind(ProPoradatele.tableNaVylouceni21.itemsProperty());
        tymVylouceni21.setPlaceholder(new Label("Žádné vyloučení"));

        TableColumn<Hrac1,String> jmenoCol21 = new TableColumn<>("Jméno hráče");
        jmenoCol21.setPrefWidth(133.2);
        jmenoCol21.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));

        TableColumn<Hrac1,String> casCol21 = new TableColumn<>("Doba vyloučení");
        casCol21.setPrefWidth(117);
        casCol21.setCellValueFactory(param -> Bindings.createStringBinding(()-> (param.getValue().getVylouceni().getMinuty() +":"+param.getValue().getVylouceni().getVteriny()),param.getValue().getVylouceni().minutyProperty(),param.getValue().getVylouceni().vterinyProperty()));


        //noinspection unchecked
        tymVylouceni21.getColumns().addAll(jmenoCol21,casCol21);

        TableView<Hrac1> tymVylouceni51 = new TableView<>();
        tymVylouceni51.setEditable(false);
        tymVylouceni51.setMinSize(250,130);
        tymVylouceni51.setMaxSize(250,130);
        tymVylouceni51.itemsProperty().bind(ProPoradatele.tableNaVylouceni51.itemsProperty());
        tymVylouceni51.setPlaceholder(new Label("Žádné vyloučení"));

        TableColumn<Hrac1,String> jmenoCol51 = new TableColumn<>("Jméno hráče");
        jmenoCol51.setPrefWidth(133);
        jmenoCol51.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));

        TableColumn<Hrac1,String> casCol51 = new TableColumn<>("Doba vyloučení");
        casCol51.setPrefWidth(117);
        casCol51.setCellValueFactory(param -> Bindings.createStringBinding(()-> (param.getValue().getVylouceni().getMinuty() +":"+param.getValue().getVylouceni().getVteriny()),param.getValue().getVylouceni().minutyProperty(),param.getValue().getVylouceni().vterinyProperty()));


        //noinspection unchecked
        tymVylouceni51.getColumns().addAll(jmenoCol51,casCol51);

        levaLista.setSpacing(25);
        levaLista.setPadding(new Insets(25,0,0,45));
        levaLista.setAlignment(Pos.TOP_CENTER);
        levaLista.getChildren().addAll(jmenoTymu1, tymVylouceni21, tymVylouceni51);
        return levaLista;
    }
}
