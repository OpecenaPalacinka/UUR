import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.util.*;


public class ProPoradatele extends Application {
    private static Timer timer;
    public TreeView<String> treeView;
    ObservableList<Hrac1> hrac1ObservableList = FXCollections.observableArrayList();
    ObservableList<Hrac2> hrac2ObservableList = FXCollections.observableArrayList();
    TableView<Hrac1> table1 = new TableView<>();
    TableView<Hrac2> table2 = new TableView<>();
    static TableView<Hrac1> tableNaVylouceni21 = new TableView<>();
    static TableView<Hrac1> tableNaVylouceni51 = new TableView<>();
    static TableView<Hrac2> tableNaVylouceni22 = new TableView<>();
    static TableView<Hrac2> tableNaVylouceni52 = new TableView<>();
    public static Tym1 tym1 = new Tym1();
    public static Tym2 tym2 = new Tym2();
    public static CasATretina casATretina = new CasATretina(0,20);
    int minCislo = 1;
    int maxCislo = 99;
    boolean pomocna = true;
    static boolean pauza = true;
    Scene scene2;
    int pocitadloProHistorii = 0;
    public static int pocitadloProDivaky = 0;
    Hrac1[] kopie = new Hrac1[0];
    List<Hrac1> list= new ArrayList<>(Arrays.asList(kopie));
    Hrac2[] kopie2 = new Hrac2[0];
    List<Hrac2> list2 = new ArrayList<>(Arrays.asList(kopie2));
    Image image = new Image("micek.jpg");
    ImageView imageView = new ImageView(image);

    public static void main(String[] args) {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->{
                    if(pauza){
                        casATretina.setVteriny(casATretina.getVteriny());
                    } else {
                        for (Hrac1 hrac: tableNaVylouceni21.getItems()
                             ) {
                            hrac.getVylouceni().odectiVterinu();
                        }
                        for (Hrac1 hrac: tableNaVylouceni51.getItems()
                        ) {
                            hrac.getVylouceni().odectiVterinu();
                        }
                        for (Hrac2 hrac: tableNaVylouceni22.getItems()
                        ) {
                            hrac.getVylouceni().odectiVterinu();
                        }
                        for (Hrac2 hrac: tableNaVylouceni52.getItems()
                        ) {
                            hrac.getVylouceni().odectiVterinu();
                        }

                        casATretina.odectiVterinu();
                    }
                    if(casATretina.getMinuty() == 0 && casATretina.getVteriny()==0 && casATretina.getTretina() >= 3){
                        pauza = true;
                        pause();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("KONEC");
                        alert.setHeaderText("Konec zápasu");
                        alert.setContentText("Konec zápasu.");
                        alert.showAndWait();
                    } else if(casATretina.getMinuty()==0 && casATretina.getVteriny()==0){
                        casATretina.setVteriny(0);
                        casATretina.setMinuty(20);
                        pauza = true;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("KONEC");
                        alert.setHeaderText("Konec třetiny");
                        alert.setContentText("Konec třetiny, přicetl jsem třetinu.");
                        alert.showAndWait();
                        casATretina.setTretina(casATretina.getTretina()+1);
                    }

                });
            }
        },0,1000);
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createRootPane());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Časomíra");
        primaryStage.getIcons().add(image);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        treeView = new TreeView<>();
        treeView.setEditable(false);
        TreeItem<String> root = new TreeItem<>("Historie zápasů");
        treeView.setRoot(root);
        scene2 = new Scene(historie());
    }

    private Parent historie(){
        BorderPane borderPane = new BorderPane();
        FlowPane flowPane = new FlowPane();
        Button odstraneni = new Button("Odstranění");
        flowPane.getChildren().add(odstraneni);
        flowPane.setAlignment(Pos.CENTER);
        odstraneni.setOnAction(actionEvent -> removeTreeItem());
        borderPane.setCenter(treeView);
        borderPane.setBottom(flowPane);
        borderPane.setMaxWidth(270);
        return borderPane;
    }

    private Parent createRootPane(){
        BorderPane rootPane = new BorderPane();

        rootPane.setTop(createMenu());
        rootPane.setLeft(createLeft());
        rootPane.setCenter(createMid());
        rootPane.setRight(createRight());

        return rootPane;
    }

    public Node createRight(){
        VBox pravaLista = new VBox();
        HBox skoreAButtony = new HBox();
        VBox buttonyPlusMinus = new VBox();

        Label pocetGolu = new Label("Počet gólů týmu 2");
        pocetGolu.setFont(new Font("Calibri",18));

        TextField tymSkore2 = new TextField();
        tymSkore2.setFont(new Font(16));
        tymSkore2.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                tymSkore2.setText(t1.replaceAll("[^\\d]", ""));
            }
            try {
                if(Integer.parseInt(t1) > 99){
                    tymSkore2.setText(s);
                }
            } catch (Exception ignored){
            }

        });
        tymSkore2.textProperty().bindBidirectional(tym2.pocetGoluProperty(),new NumberStringConverter());
        tymSkore2.setMaxSize(60,50);

        Button skorePlus2 = new Button("+");
        skorePlus2.setOnAction(actionEvent -> {
            if(pauza) {
                tym2.setPocetGolu(Math.min(tym2.getPocetGolu() + 1, 99));
            }
        });
        Button skoreMinus2 = new Button("-");
        skoreMinus2.setOnAction(actionEvent -> {
            if(pauza) {
                tym2.setPocetGolu(Math.max(tym2.getPocetGolu() - 1, 0));
            }
        });
        buttonyPlusMinus.setSpacing(10);
        skoreMinus2.setFont(new Font(14));
        skoreMinus2.setPadding(new Insets(1,9.05,3,9.05));

        buttonyPlusMinus.setPadding(new Insets(0,0,0,15));
        skoreAButtony.setPadding(new Insets(15,0,15,125));
        buttonyPlusMinus.getChildren().addAll(skorePlus2,skoreMinus2);
        skoreAButtony.getChildren().addAll(tymSkore2,buttonyPlusMinus);

        TextField tym2TF = new TextField();
        tym2TF.setPromptText("Jmeno tymu 2");
        tym2TF.textProperty().bindBidirectional(tym2.jmenoTymuProperty());
        tym2TF.textProperty().addListener((observable, oldValue, newValue) -> tym2.setJmenoTymu(newValue));
        tym2TF.setMinSize(200,30);
        tym2TF.setMaxSize(200,30);

        table2.setEditable(false);
        table2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table2.setMinSize(200,325);
        table2.setMaxSize(200,325);
        table2.itemsProperty().bindBidirectional(new SimpleListProperty<>(hrac2ObservableList));

        TableColumn<Hrac2,Integer> cisloCol = new TableColumn<>("Číslo hráče");
        cisloCol.setCellValueFactory(new PropertyValueFactory<>("cisloHrace"));
        cisloCol.setPrefWidth(65);
        cisloCol.setCellFactory(column -> new ConsumingTextFieldTableCell<>(new IntegerStringConverter()));

        TableColumn<Hrac2,String> jmenoCol = new TableColumn<>("Jméno hráče");
        jmenoCol.setPrefWidth(133.2);
        jmenoCol.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));
        jmenoCol.setCellFactory(column -> new ConsumingTextFieldTableCell<>());

        //noinspection unchecked
        table2.getColumns().addAll(cisloCol,jmenoCol);

        GridPane pridani = new GridPane();
        Label pridaniLabelCislo = new Label("Číslo:");
        Label pridaniLabelJmeno = new Label("Jméno hráče:");
        TextField pridaniCislo = new TextField();
        pridaniCislo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                pridaniCislo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        pridaniCislo.setMinSize(35,30);
        pridaniCislo.setMaxSize(35,30);
        TextField pridaniJmeno = new TextField();
        pridaniJmeno.setMinSize(150,30);
        pridaniJmeno.setMaxSize(150,30);
        Button addBT = new Button("Přidat");
        addBT.setOnAction(actionEvent -> {
            if (pridaniCislo.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Špatné číslo");
                alert.setContentText("Nezadali jste číslo. Zadejte číslo mezi 1 - 99!");
                alert.showAndWait();
            } else if (pridaniJmeno.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Nezadali jste jméno");
                alert.setContentText("Zadejte jméno hráče!");
                pridaniCislo.setText("");
                alert.showAndWait();
            } else if(Integer.parseInt(pridaniCislo.getText())<minCislo || Integer.parseInt(pridaniCislo.getText())>maxCislo){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Špatné číslo");
                alert.setContentText("Zadejte číslo mezi 1 - 99!");
                pridaniCislo.setText("");
                alert.showAndWait();
            } else {
                Hrac2 novyHrac = new Hrac2(Integer.parseInt(pridaniCislo.getText()), pridaniJmeno.getText(),TypVylouceni.NULA);
                hrac2ObservableList.add(novyHrac);
                list2.add(novyHrac);
                kopie2 = list2.toArray(new Hrac2[0]);
                pridaniCislo.setText("");
                pridaniJmeno.setText(null);
            }
        });

        addBT.setMinSize(100,30);
        addBT.setMaxSize(100,30);
        Button removeBT = new Button("Odstranit");
        removeBT.setOnAction(actionEvent -> delete2());

        removeBT.setMinSize(100,30);
        removeBT.setMaxSize(100,30);
        pridani.add(pridaniLabelCislo, 0, 0);
        pridani.add(pridaniCislo,0,1);
        pridani.add(pridaniLabelJmeno,1,0);
        pridani.add(pridaniJmeno,1,1);
        pridani.add(removeBT,4,0);
        pridani.add(addBT,4,1);

        pridani.setHgap(3);
        pridani.setVgap(3);
        pridani.setPadding(new Insets(5));

        pravaLista.setSpacing(25);
        pravaLista.setPadding(new Insets(10,25,0,0));
        pravaLista.setAlignment(Pos.TOP_CENTER);
        pravaLista.getChildren().addAll(pocetGolu,skoreAButtony,tym2TF, table2,pridani);
        return pravaLista;
    }

    public Node createMid() {
        VBox prostredek = new VBox();
        VBox tretina = new VBox();
        VBox casBox = new VBox();
        HBox horniBox = new HBox();
        HBox timeouty = new HBox();
        HBox vylouceniNa2 = new HBox();
        HBox vylouceniNa5 = new HBox();

        Label tretinaLabel = new Label("Třetina:");
        TextField tretinaField = new TextField();
        tretinaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tretinaField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        tretinaField.setFont(new Font(19));
        tretinaField.textProperty().bindBidirectional(casATretina.tretinaProperty(),new NumberStringConverter());
        tretinaField.setMinSize(60,50);
        tretinaField.setMaxSize(60,50);
        FlowPane tretinaButtony = new FlowPane();
        Button plus = new Button("+");
        plus.setOnAction(actionEvent -> {
            if(pauza){
                if(casATretina.getTretina()+1>4){
                     casATretina.setTretina(casATretina.getTretina());
                 } else {
                     casATretina.setTretina(casATretina.getTretina()+1);
                 }
            }
        });
        Button minus = new Button("-");
        minus.setOnAction(actionEvent -> {
            if(pauza) {
                casATretina.setTretina(Math.max(casATretina.getTretina() - 1, 1));
            }
        });
        minus.setFont(new Font(14));
        minus.setPadding(new Insets(1.5,9,3.5,9));

        tretinaButtony.setHgap(15);
        tretinaButtony.setAlignment(Pos.CENTER);
        tretinaButtony.getChildren().addAll(plus,minus);
        tretina.setSpacing(15);
        tretina.setAlignment(Pos.CENTER);
        tretina.getChildren().addAll(tretinaLabel,tretinaField,tretinaButtony);

        Label casLabel = new Label("Čas:");
        casLabel.setFont(new Font(20));
        HBox cas = new HBox();
        Label minuty = new Label();
        Label dvojtecka = new Label(":");
        Label vteriny = new Label();
        minuty.setFont(new Font(30));
        dvojtecka.setFont(new Font(30));
        vteriny.setFont(new Font(30));
        minuty.textProperty().bindBidirectional(casATretina.minutyProperty(), new NumberStringConverter());
        vteriny.textProperty().bindBidirectional(casATretina.vterinyProperty(),new NumberStringConverter());

        cas.setMinSize(200,75);
        cas.setMaxSize(200,75);
        cas.setAlignment(Pos.CENTER);
        cas.getChildren().addAll(minuty,dvojtecka,vteriny);

        FlowPane startStop = new FlowPane();
        Button start = new Button("Start");
        start.setOnAction(actionEvent -> pauza = false);
        start.setFont(new Font(17));
        start.setMinSize(125,50);
        start.setMaxSize(125,50);

        Button stop = new Button("Stop");
        stop.setOnAction(actionEvent -> pauza = true);
        stop.setFont(new Font(17));
        stop.setMinSize(125,50);
        stop.setMaxSize(125,50);
        startStop.setHgap(25);
        startStop.setAlignment(Pos.CENTER);
        startStop.getChildren().addAll(start,stop);

        casBox.setSpacing(15);
        casBox.setAlignment(Pos.CENTER);
        casBox.getChildren().addAll(casLabel,cas,startStop);
        horniBox.setAlignment(Pos.TOP_CENTER);
        horniBox.getChildren().addAll(tretina,casBox);

        CheckBox timeoutTym1 = new CheckBox();
        timeoutTym1.selectedProperty().bindBidirectional(tym1.timeoutProperty());
        timeoutTym1.setOnAction(actionEvent -> {
            if(pauza){
                if(timeoutTym1.isSelected()){
                    tym1.setTimeout(true);
                } else {
                     tym1.setTimeout(false);
                }
            }
        });
        Label timeout = new Label("Timeout");
        CheckBox timeoutTym2 = new CheckBox();
        timeoutTym2.selectedProperty().bindBidirectional(tym2.timeoutProperty());
        timeoutTym2.setOnAction(actionEvent -> {
            if(pauza) {
                if (timeoutTym2.isSelected()) {
                    tym2.setTimeout(true);
                } else {
                    tym2.setTimeout(false);
                }
            }
        });
        timeouty.setAlignment(Pos.CENTER);
        timeouty.setSpacing(150);
        timeouty.getChildren().addAll(timeoutTym1,timeout,timeoutTym2);

        tableNaVylouceni21.setEditable(false);
        tableNaVylouceni21.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn<Hrac1,Integer> cisloCol21 = new TableColumn<>("Číslo hráče");
        cisloCol21.setPrefWidth(65);
        cisloCol21.setCellValueFactory(new PropertyValueFactory<>("cisloHrace"));


        TableColumn<Hrac1,String> casCol21 = new TableColumn<>("Doba vyloučení");
        casCol21.setPrefWidth(133.2);
        casCol21.setCellValueFactory(param -> Bindings.createStringBinding(()-> (param.getValue().getVylouceni().getMinuty() +":"+param.getValue().getVylouceni().getVteriny()),param.getValue().getVylouceni().minutyProperty(),param.getValue().getVylouceni().vterinyProperty()));


        //noinspection unchecked
        tableNaVylouceni21.getColumns().addAll(cisloCol21,casCol21);

        Label vylouceni2 = new Label("Vyloučení na 2 minuty");

        tableNaVylouceni22.setEditable(false);
        tableNaVylouceni22.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn<Hrac2,Integer> cisloCol22 = new TableColumn<>("Číslo hráče");
        cisloCol22.setPrefWidth(65);
        cisloCol22.setCellValueFactory(new PropertyValueFactory<>("cisloHrace"));
        cisloCol22.setCellFactory(column -> new ConsumingTextFieldTableCell<>(new IntegerStringConverter()));

        TableColumn<Hrac2,String> casCol22 = new TableColumn<>("Doba vyloučení");
        casCol22.setPrefWidth(133.2);
        casCol22.setCellValueFactory(param -> Bindings.createStringBinding(()-> (param.getValue().getVylouceni().getMinuty() +":"+param.getValue().getVylouceni().getVteriny()),param.getValue().getVylouceni().minutyProperty(),param.getValue().getVylouceni().vterinyProperty()));


        //noinspection unchecked
        tableNaVylouceni22.getColumns().addAll(cisloCol22,casCol22);


        vylouceniNa2.setAlignment(Pos.CENTER);
        vylouceniNa2.setSpacing(50);
        tableNaVylouceni21.setMinSize(200,85);
        tableNaVylouceni21.setMaxSize(200,85);
        tableNaVylouceni22.setMinSize(200,85);
        tableNaVylouceni22.setMaxSize(200,85);
        vylouceniNa2.getChildren().addAll(tableNaVylouceni21,vylouceni2,tableNaVylouceni22);


        tableNaVylouceni51.setEditable(false);
        tableNaVylouceni51.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn<Hrac1,Integer> cisloCol51 = new TableColumn<>("Číslo hráče");
        cisloCol51.setPrefWidth(65);
        cisloCol51.setCellValueFactory(new PropertyValueFactory<>("cisloHrace"));
        cisloCol51.setCellFactory(column -> new ConsumingTextFieldTableCell<>(new IntegerStringConverter()));

        TableColumn<Hrac1,String> casCol51 = new TableColumn<>("Doba vyloučení");
        casCol51.setPrefWidth(133.2);
        casCol51.setCellValueFactory(param -> Bindings.createStringBinding(()-> (param.getValue().getVylouceni().getMinuty() +":"+param.getValue().getVylouceni().getVteriny()),param.getValue().getVylouceni().minutyProperty(),param.getValue().getVylouceni().vterinyProperty()));

        //noinspection unchecked
        tableNaVylouceni51.getColumns().addAll(cisloCol51,casCol51);

        Label vylouceni5 = new Label("Vyloučení na 5 minut");

        tableNaVylouceni52.setEditable(false);
        tableNaVylouceni52.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn<Hrac2,Integer> cisloCol52 = new TableColumn<>("Číslo hráče");
        cisloCol52.setPrefWidth(65);
        cisloCol52.setCellValueFactory(new PropertyValueFactory<>("cisloHrace"));
        cisloCol52.setCellFactory(column -> new ConsumingTextFieldTableCell<>(new IntegerStringConverter()));

        TableColumn<Hrac2,String> casCol52 = new TableColumn<>("Doba vyloučení");
        casCol52.setPrefWidth(133.2);
        casCol52.setCellValueFactory(param -> Bindings.createStringBinding(()-> (param.getValue().getVylouceni().getMinuty() +":"+param.getValue().getVylouceni().getVteriny()),param.getValue().getVylouceni().minutyProperty(),param.getValue().getVylouceni().vterinyProperty()));

        //noinspection unchecked
        tableNaVylouceni52.getColumns().addAll(cisloCol52,casCol52);

        vylouceniNa5.setAlignment(Pos.CENTER);
        vylouceniNa5.setSpacing(50);
        tableNaVylouceni51.setMinSize(200,85);
        tableNaVylouceni51.setMaxSize(200,85);
        tableNaVylouceni52.setMinSize(200,85);
        tableNaVylouceni52.setMaxSize(200,85);
        vylouceniNa5.getChildren().addAll(tableNaVylouceni51,vylouceni5,tableNaVylouceni52);

        GridPane pridani = new GridPane();
        Label pridaniLabelTym = new Label("Tým:");
        Label pridaniLabelCislo = new Label("Číslo hráče:");
        Label pridaniLabelVylouceni = new Label("Doba vylouceni:");
        ChoiceBox<CisloTymu> tymChoice = new ChoiceBox<>(FXCollections.observableArrayList(CisloTymu.values()));
        tymChoice.setMinSize(65,30);
        tymChoice.setMaxSize(65,30);

        TextField pridaniCislo = new TextField();
        pridaniCislo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                pridaniCislo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        pridaniCislo.setMinSize(100,30);
        pridaniCislo.setMaxSize(100,30);
        ChoiceBox<TypVylouceni> vylouceniTyp = new ChoiceBox<>(FXCollections.observableArrayList(TypVylouceni.DVE,TypVylouceni.PET));
        vylouceniTyp.setMinSize(80,30);
        vylouceniTyp.setMaxSize(80,30);
        Button addBT = new Button("Přidat");
        addBT.setOnAction(actionEvent -> {
            if(pauza){
            try {
                switch (tymChoice.getValue()) {
                    case Tym1:
                        for (Hrac1 hraci:  kopie
                        ) {
                            if (!tableNaVylouceni21.getItems().contains(hraci)
                                    && !tableNaVylouceni51.getItems().contains(hraci)) {
                                if (Integer.parseInt(pridaniCislo.getText()) == hraci.getCisloHrace()) {
                                    if (vylouceniTyp.getValue() == TypVylouceni.DVE) {
                                        hraci.setVylouceni(TypVylouceni.DVE);
                                        tableNaVylouceni21.getItems().addAll(hraci);
                                        list.remove(hraci);
                                        kopie = list.subList(0, list.size()).toArray(new Hrac1[0]);
                                        pomocna = false;
                                        break;
                                    }
                                    if (vylouceniTyp.getValue() == TypVylouceni.PET) {
                                        hraci.setVylouceni(TypVylouceni.PET);
                                        tableNaVylouceni51.getItems().addAll(hraci);
                                        list.remove(hraci);
                                        kopie = list.subList(0, list.size()).toArray(new Hrac1[0]);
                                        pomocna = false;
                                        break;
                                    }
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Varování");
                                alert.setHeaderText("Vyloučení");
                                alert.setContentText("Vybraný hráč je již vyloučený.");
                                alert.showAndWait();
                            }
                        }
                        if(pomocna){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Varování");
                            alert.setHeaderText("Špatné číslo");
                            alert.setContentText("Zadali jste číslo, které není na soupisce \"Týmu 1\"");
                            alert.showAndWait();
                        }

                        break;
                    case Tym2:
                        for (Hrac2 hraci: kopie2
                        ) {
                            if (!tableNaVylouceni22.getItems().contains(hraci)
                                    && !tableNaVylouceni52.getItems().contains(hraci)) {
                                if (hraci.getCisloHrace() == Integer.parseInt(pridaniCislo.getText())) {
                                    if (vylouceniTyp.getValue() == TypVylouceni.DVE) {
                                        hraci.setVylouceni(TypVylouceni.DVE);
                                        tableNaVylouceni22.getItems().addAll(hraci);
                                        list2.remove(hraci);
                                        kopie2 = list2.subList(0, list.size()).toArray(new Hrac2[0]);
                                        pomocna = false;
                                        break;
                                    }
                                    if (vylouceniTyp.getValue() == TypVylouceni.PET) {
                                        hraci.setVylouceni(TypVylouceni.PET);
                                        tableNaVylouceni52.getItems().addAll(hraci);
                                        list2.remove(hraci);
                                        kopie2 = list2.subList(0, list.size()).toArray(new Hrac2[0]);
                                        pomocna = false;
                                        break;
                                    }
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Varování");
                                alert.setHeaderText("Vyloučení");
                                alert.setContentText("Vybraný hráč je již vyloučený.");
                                alert.showAndWait();
                            }
                        }
                        if(pomocna){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Varování");
                            alert.setHeaderText("Špatné číslo");
                            alert.setContentText("Zadali jste číslo, které není na soupisce \"Týmu 2\"");
                            alert.showAndWait();
                        }
                        break;
                }
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Varování");
                alert.setHeaderText("Tým/Doba vyloučení");
                alert.setContentText("Vyberte tým nebo dobu vyloučení!");
                alert.showAndWait();
            }

        } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Varování");
                alert.setHeaderText("Čas");
                alert.setContentText("Pokoušel jste se vyloučit hráče zatímco běžel čas!");
                alert.showAndWait();
            }

        });

        addBT.setMinSize(100,30);
        addBT.setMaxSize(100,30);
        pridani.add(pridaniLabelTym, 0, 0);
        pridani.add(tymChoice,0,1);
        pridani.add(pridaniLabelCislo,1,0);
        pridani.add(pridaniCislo,1,1);
        pridani.add(pridaniLabelVylouceni,2,0);
        pridani.add(vylouceniTyp,2,1);
        pridani.add(addBT,4,1);

        pridani.setHgap(10);
        pridani.setVgap(5);
        pridani.setPadding(new Insets(5));
        pridani.setAlignment(Pos.CENTER);

        prostredek.setPadding(new Insets(25,-10,0,-10));
        prostredek.setSpacing(35);
        prostredek.setAlignment(Pos.TOP_CENTER);
        prostredek.getChildren().addAll(horniBox,timeouty,vylouceniNa2,vylouceniNa5,pridani);
        return prostredek;
    }

    public Node createLeft(){
        VBox levaLista = new VBox();
        HBox skoreAButtony = new HBox();
        VBox buttony = new VBox();

        Label pocetGolu = new Label("Počet gólů týmu 1");
        pocetGolu.setFont(new Font("Calibri",18));

        TextField tymSkore1 = new TextField();
        tymSkore1.setFont(new Font(16));
        tymSkore1.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d*")) {
                tymSkore1.setText(t1.replaceAll("[^\\d]", ""));
            }
            try {
                if(Integer.parseInt(t1) > 99){
                    tymSkore1.setText(s);
                }
            } catch (Exception ignored){
            }
        });
        tymSkore1.textProperty().bindBidirectional(tym1.pocetGoluProperty(),new NumberStringConverter());
        tymSkore1.setMaxSize(60,50);

        Button skorePlus1 = new Button("+");
        skorePlus1.setOnAction(actionEvent -> {
            if(pauza) {
                tym1.setPocetGolu(Math.min(tym1.getPocetGolu() + 1, 99));
            }
        });
        Button skoreMinus1 = new Button("-");
        skoreMinus1.setOnAction(actionEvent -> {
            if(pauza) {
                tym1.setPocetGolu(Math.max(tym1.getPocetGolu() - 1, 0));
            }
        });
        buttony.setSpacing(10);
        skoreMinus1.setFont(new Font(14));
        skoreMinus1.setPadding(new Insets(1,9.05,3,9.05));

        buttony.setPadding(new Insets(0,0,0,15));
        skoreAButtony.setPadding(new Insets(15,0,15,120));
        buttony.getChildren().addAll(skorePlus1,skoreMinus1);
        skoreAButtony.getChildren().addAll(tymSkore1,buttony);

        TextField tym1TF = new TextField();
        tym1TF.setPromptText("Jmeno tymu 1");
        tym1TF.textProperty().bindBidirectional(tym1.jmenoTymuProperty());
        tym1TF.textProperty().addListener((observable, oldValue, newValue) -> tym1.setJmenoTymu(newValue));
        tym1TF.setMinSize(200,30);
        tym1TF.setMaxSize(200,30);

        table1.setEditable(false);
        table1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table1.itemsProperty().bindBidirectional(new SimpleListProperty<>(hrac1ObservableList));

        TableColumn<Hrac1,Integer> cisloCol = new TableColumn<>("Číslo hráče");
        cisloCol.setPrefWidth(65);
        cisloCol.setCellValueFactory(new PropertyValueFactory<>("cisloHrace"));
        cisloCol.setCellFactory(column -> new ConsumingTextFieldTableCell<>(new IntegerStringConverter()));

        TableColumn<Hrac1,String> jmenoCol = new TableColumn<>("Jméno hráče");
        jmenoCol.setPrefWidth(133.2);
        jmenoCol.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));
        jmenoCol.setCellFactory(column -> new ConsumingTextFieldTableCell<>());

        table1.setMinSize(200,325);
        table1.setMaxSize(200,325);

        //noinspection unchecked
        table1.getColumns().addAll(cisloCol,jmenoCol);

        GridPane pridani = new GridPane();
        Label pridaniLabelCislo = new Label("Číslo:");
        Label pridaniLabelJmeno = new Label("Jméno hráče:");
        TextField pridaniCislo = new TextField();
        pridaniCislo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                pridaniCislo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        pridaniCislo.setMinSize(35,30);
        pridaniCislo.setMaxSize(35,30);
        TextField pridaniJmeno = new TextField();
        pridaniJmeno.setMinSize(150,30);
        pridaniJmeno.setMaxSize(150,30);

        Button addBT = new Button("Přidat");
        addBT.setOnAction(actionEvent -> {
            if (pridaniCislo.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Špatné číslo");
                alert.setContentText("Nezadali jste číslo. Zadejte číslo mezi 1 - 99!");
                alert.showAndWait();
            } else if (pridaniJmeno.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Nezadali jste jméno");
                alert.setContentText("Zadejte jméno hráče!");
                pridaniCislo.setText("");
                alert.showAndWait();
            } else if(Integer.parseInt(pridaniCislo.getText())<minCislo || Integer.parseInt(pridaniCislo.getText())>maxCislo){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Špatné číslo");
                alert.setContentText("Zadejte číslo mezi 1 - 99!");
                pridaniCislo.setText("");
                alert.showAndWait();
            } else {
                Hrac1 novyHrac = new Hrac1(Integer.parseInt(pridaniCislo.getText()), pridaniJmeno.getText(),TypVylouceni.NULA);
                hrac1ObservableList.add(novyHrac);
                list.add(novyHrac);
                kopie = list.toArray(new Hrac1[0]);
                pridaniCislo.setText("");
                pridaniJmeno.setText(null);
            }
        });
        addBT.setMinSize(100,30);
        addBT.setMaxSize(100,30);

        Button removeBT = new Button("Odstranit");
        removeBT.setOnAction(actionEvent -> delete1());
        removeBT.setMinSize(100,30);
        removeBT.setMaxSize(100,30);
        pridani.add(pridaniLabelCislo, 0, 0);
        pridani.add(pridaniCislo,0,1);
        pridani.add(pridaniLabelJmeno,1,0);
        pridani.add(pridaniJmeno,1,1);
        pridani.add(removeBT,4,0);
        pridani.add(addBT,4,1);

        pridani.setHgap(3);
        pridani.setVgap(3);
        pridani.setPadding(new Insets(5));

        levaLista.setSpacing(25);
        levaLista.setPadding(new Insets(10,0,0,25));
        levaLista.setAlignment(Pos.TOP_CENTER);
        levaLista.getChildren().addAll(pocetGolu,skoreAButtony,tym1TF, table1,pridani);
        return levaLista;
    }

    public MenuBar createMenu(){
        MenuBar menuBar = new MenuBar();

        Menu hraMenu = new Menu("_Hra");
        Menu insertMenu = new Menu("Zápas");
        MenuItem novejZapas = new MenuItem("Nový zápas");
        MenuItem restartZapas = new MenuItem("Restartovat zápas");
        MenuItem ulozitZapas = new MenuItem("Uložit zápas");
        MenuItem historieZapasu = new MenuItem("Historie zápasů");

        insertMenu.getItems().addAll(novejZapas,restartZapas,ulozitZapas);
        hraMenu.getItems().addAll(insertMenu,historieZapasu);

        Menu nastroje = new Menu("_Nástroje");
        MenuItem proDivaky = new MenuItem("Zobrazení pro diváky");
        MenuItem napoveda = new MenuItem("Nápověda");
        MenuItem ukoncit = new MenuItem("Ukončení aplikace");
        //Pridani funkci tlacitkum
        proDivaky.setOnAction(actionEvent -> {
            if(pocitadloProDivaky == 0){
                new ProDivaky();
            }
        });
        napoveda.setOnAction(actionEvent -> vypsaniNapovedy());
        ukoncit.setOnAction(actionEvent -> System.exit(0));

        novejZapas.setOnAction(actionEvent -> {
            tym1.setPocetGolu(0);
            tym1.setTimeout(false);
            tym1.setJmenoTymu("");
            tym2.setTimeout(false);
            tym2.setPocetGolu(0);
            tym2.setJmenoTymu("");
            hrac1ObservableList.clear();
            hrac2ObservableList.clear();
            tableNaVylouceni21.getItems().clear();
            tableNaVylouceni22.getItems().clear();
            tableNaVylouceni51.getItems().clear();
            tableNaVylouceni52.getItems().clear();
            casATretina.setTretina(1);
            casATretina.setVteriny(0);
            casATretina.setMinuty(20);
            resume();
        });
        restartZapas.setOnAction(actionEvent -> {
            tym1.setPocetGolu(0);
            tym1.setTimeout(false);
            tym2.setTimeout(false);
            tym2.setPocetGolu(0);
            tableNaVylouceni21.getItems().clear();
            tableNaVylouceni22.getItems().clear();
            tableNaVylouceni51.getItems().clear();
            tableNaVylouceni52.getItems().clear();
            casATretina.setTretina(1);
            casATretina.setVteriny(0);
            casATretina.setMinuty(20);
        });
        ulozitZapas.setOnAction(actionEvent -> {
            TreeItem<String> skoreTymu = new TreeItem<>();
            skoreTymu.setValue("Skóre: " + tym1.getPocetGolu() + ":" + tym2.getPocetGolu());
            TreeItem<String> jmenaTymu = new TreeItem<>();
            jmenaTymu.setValue(tym1.getJmenoTymu() + "  vs  " + tym2.getJmenoTymu());
            jmenaTymu.getChildren().add(skoreTymu);
            treeView.getRoot().getChildren().add(jmenaTymu);
        });

        historieZapasu.setOnAction(actionEvent -> {
            if(pocitadloProHistorii == 0){
                Stage stage2 = new Stage();
                stage2.setScene(scene2);
                stage2.setTitle("Historie zápasů");
                stage2.setMinHeight(550);
                stage2.setResizable(false);
                pocitadloProHistorii++;
                stage2.showAndWait();
                pocitadloProHistorii--;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pozor");
                alert.setHeaderText("Historie zápasů je již otevřená");
                alert.setContentText("Historie zápasů je již otevřená");
                alert.showAndWait();
            }


        });

        nastroje.getItems().addAll(proDivaky,napoveda,ukoncit);

        menuBar.getMenus().addAll(nastroje,hraMenu);
        return menuBar;
    }

    private void vypsaniNapovedy(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nápověda");
        alert.setHeaderText("Nápověda na ovládání");
        alert.setContentText("Jako první doporučuji vyplnit jména obou týmů, kolonky najdete po stranách" +
                " uprostřed, dále doporučuji vyplnit soupisky obou týmů a to se dělá přes přidávací lištu" +
                " postranách v dolní části obrazovky, každá lišta je pro jeden tým. Pokud chcete zadat timeout " +
                "stačí zakliknout checkbox na straně daného týmu. K přiřazení vyloučení se používá dolní lišta uprostřed" +
                " nejdřív vyberete tým který bude vyloučený, poté hráče a pak dobu trestu. Okno pro diváky se otvírá " +
                "v menu, kde kliknete na první záložku a poté vyberete možnost \"Zobrazení pro diváky\".");
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

    private void delete1() {
        ObservableList<Hrac1> selection = table1.getSelectionModel().getSelectedItems();
        table1.getItems().removeAll(selection);
        table1.getSelectionModel().clearSelection();
    }
    private void delete2() {
        ObservableList<Hrac2> selection = table2.getSelectionModel().getSelectedItems();
        table2.getItems().removeAll(selection);
        table2.getSelectionModel().clearSelection();
    }
    private void removeTreeItem(){
        TreeItem<String> selected = treeView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pozor");
            alert.setHeaderText("Nic jste nevybrali!");
            alert.setContentText("Prosím, vyberte věc, kterou chcete odstranit.");
            alert.showAndWait();
        } else {
            TreeItem<String> parent = selected.getParent();

            if (parent == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pozor");
                alert.setHeaderText("Vybraný prvek je root, nelze odstranit!");
                alert.setContentText("Není možné odstranit prvek: "+ treeView.getRoot().getValue());
                alert.showAndWait();
            } else if (parent == treeView.getRoot()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mazání");
                alert.setHeaderText("Opravdu chcete smazat tento prvek?");
                alert.setContentText("Vybraný: " + selected.getValue());
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            parent.getChildren().remove(selected);
                            treeView.getSelectionModel().clearSelection();
                        });
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mazání");
                alert.setHeaderText("Chyba, vybrali jste špatný prvek.");
                alert.setContentText("Nemůžete smazat pouze skóre zápasu, musíte smazat celý zápas.");
                alert.showAndWait();
            }
        }
    }
    public static void pause(){
        timer.cancel();
    }
    public void resume(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->{
                    if(pauza){
                        casATretina.setVteriny(casATretina.getVteriny());
                    } else {
                        casATretina.odectiVterinu();
                    }
                    if(casATretina.getMinuty() == 0 && casATretina.getVteriny()==0 && casATretina.getTretina() >= 3){
                        pauza = true;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("KONEC");
                        alert.setHeaderText("Konec zápasu");
                        alert.setContentText("Konec zápasu.");
                        alert.showAndWait();
                    } else if(casATretina.getMinuty()==0 && casATretina.getVteriny()==0){
                        casATretina.setVteriny(0);
                        casATretina.setMinuty(20);
                        pauza = true;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("KONEC");
                        alert.setHeaderText("Konec třetiny");
                        alert.setContentText("Konec třetiny, přicetl jsem třetinu.");
                        alert.showAndWait();
                        casATretina.setTretina(casATretina.getTretina()+1);
                    }

                });
            }
        },0,1000);
    }
    public static class ConsumingTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {
        public ConsumingTextFieldTableCell(StringConverter<T> converter) {
            super(converter);

            setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.DELETE) {
                    event.consume();
                }
            });
        }

        public ConsumingTextFieldTableCell() {
            this((StringConverter<T>) new DefaultStringConverter());
        }
    }

}
