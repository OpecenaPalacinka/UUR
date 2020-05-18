import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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


public class ProPoradatele extends Application {
    TableView<Hrac1> table1 = new TableView<>();
    TableView<Hrac2> table2 = new TableView<>();
    TableView<Hrac1> tableNaVylouceni21 = new TableView<>();
    TableView<Hrac1> tableNaVylouceni51 = new TableView<>();
    TableView<Hrac2> tableNaVylouceni22 = new TableView<>();
    TableView<Hrac2> tableNaVylouceni52 = new TableView<>();
    public static Tym1 tym1 = new Tym1();
    public static Tym2 tym2 = new Tym2();
    public static CasATretina casATretina = new CasATretina();
    int minCislo = 1;
    int maxCislo = 99;
    public int tretina = 0;
    boolean pomocna = true;
    Image image = new Image("micek.jpg");
    ImageView imageView = new ImageView(image);

    public static void main(String[] args) {
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
        });
        tymSkore2.textProperty().bindBidirectional(tym2.pocetGoluProperty(),new NumberStringConverter());
        tymSkore2.setMaxSize(60,50);

        Button skorePlus2 = new Button("+");
        skorePlus2.setOnAction(actionEvent -> tym2.setPocetGolu(tym2.getPocetGolu()+1));
        Button skoreMinus2 = new Button("-");
        skoreMinus2.setOnAction(actionEvent -> {
            if(tym2.getPocetGolu()-1<0){
                tym2.setPocetGolu(0);
            } else {
                tym2.setPocetGolu(tym2.getPocetGolu()-1);
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
        tym2TF.textProperty().addListener((observable, oldValue, newValue) -> {
            tym2.setJmenoTymu(newValue);
        });
        tym2TF.setMinSize(200,30);
        tym2TF.setMaxSize(200,30);

        table2.setEditable(false);
        table2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table2.setMinSize(200,325);
        table2.setMaxSize(200,325);

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
                Hrac2 novyHrac = new Hrac2(Integer.parseInt(pridaniCislo.getText()), pridaniJmeno.getText());
                table2.getItems().add(novyHrac);
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
            if(casATretina.getTretina()+1>4){
                casATretina.setTretina(casATretina.getTretina());
            } else {
                casATretina.setTretina(casATretina.getTretina()+1);
            }
        });
        Button minus = new Button("-");
        minus.setOnAction(actionEvent -> {
            if(casATretina.getTretina()-1<1){
                casATretina.setTretina(1);
            } else {
                casATretina.setTretina(casATretina.getTretina()-1);
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
        TextField cas = new TextField();
        cas.setEditable(false);
        cas.setMinSize(200,75);
        cas.setMaxSize(200,75);
        cas.setAlignment(Pos.TOP_CENTER);
        cas.setText("Aktualni cas");

        FlowPane startStop = new FlowPane();
        Button start = new Button("Start");
        start.setFont(new Font(17));
        start.setMinSize(125,50);
        start.setMaxSize(125,50);

        Button stop = new Button("Stop");
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
        timeoutTym1.setOnAction(actionEvent -> {
            if(timeoutTym1.isSelected()){
                tym1.setTimeout(true);
            } else {
                tym1.setTimeout(false);
            }
        });
        Label timeout = new Label("Timeout");
        CheckBox timeoutTym2 = new CheckBox();
        timeoutTym2.setOnAction(actionEvent -> {
            if(timeoutTym2.isSelected()){
                tym2.setTimeout(true);
            } else {
                tym2.setTimeout(false);
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
        cisloCol21.setCellFactory(column -> new ConsumingTextFieldTableCell<>(new IntegerStringConverter()));

        TableColumn<Hrac1,String> casCol21 = new TableColumn<>("Doba vyloučení");
        casCol21.setPrefWidth(133.2);

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
        ChoiceBox<TypVylouceni> vylouceniTyp = new ChoiceBox<>(FXCollections.observableArrayList(TypVylouceni.values()));
        vylouceniTyp.setMinSize(80,30);
        vylouceniTyp.setMaxSize(80,30);
        Button addBT = new Button("Přidat");
        addBT.setOnAction(actionEvent -> {
            try {
                switch (tymChoice.getValue()) {
                    case Tym1:
                        for (Hrac1 hraci: table1.getItems()
                        ) {
                            if(hraci.getCisloHrace() == Integer.parseInt(pridaniCislo.getText())){
                                if (vylouceniTyp.getValue() == TypVylouceni.DVE) {
                                    tableNaVylouceni21.getItems().addAll(hraci);
                                    pomocna = false;
                                    break;
                                }
                                if(vylouceniTyp.getValue() == TypVylouceni.PET){
                                    tableNaVylouceni51.getItems().addAll(hraci);
                                    pomocna = false;
                                    break;
                                }
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
                        for (Hrac2 hraci: table2.getItems()
                        ) {
                            if (hraci.getCisloHrace() == Integer.parseInt(pridaniCislo.getText())) {
                                 if (vylouceniTyp.getValue() == TypVylouceni.DVE) {
                                    tableNaVylouceni22.getItems().addAll(hraci);
                                    pomocna = false;
                                    break;
                                 }
                                 if (vylouceniTyp.getValue() == TypVylouceni.PET){
                                     tableNaVylouceni52.getItems().addAll(hraci);
                                     pomocna = false;
                                     break;
                                 }
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
        });
        tymSkore1.textProperty().bindBidirectional(tym1.pocetGoluProperty(),new NumberStringConverter());
        tymSkore1.setMaxSize(60,50);

        Button skorePlus1 = new Button("+");
        skorePlus1.setOnAction(actionEvent -> tym1.setPocetGolu(tym1.getPocetGolu()+1));
        Button skoreMinus1 = new Button("-");
        skoreMinus1.setOnAction(actionEvent -> {
            if(tym1.getPocetGolu()-1<0){
                tym1.setPocetGolu(0);
            } else {
                tym1.setPocetGolu(tym1.getPocetGolu()-1);
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
        tym1TF.textProperty().addListener((observable, oldValue, newValue) -> {
            tym1.setJmenoTymu(newValue);
        });
        tym1TF.setMinSize(200,30);
        tym1TF.setMaxSize(200,30);

        table1.setEditable(false);
        table1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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
                Hrac1 novyHrac = new Hrac1(Integer.parseInt(pridaniCislo.getText()), pridaniJmeno.getText());
                table1.getItems().add(novyHrac);
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
        MenuItem historie = new MenuItem("Historie zápasů");

        insertMenu.getItems().addAll(novejZapas,restartZapas,ulozitZapas);
        hraMenu.getItems().addAll(insertMenu,historie);

        Menu nastroje = new Menu("_Nástroje");
        MenuItem proDivaky = new MenuItem("Zobrazení pro diváky");
        MenuItem napoveda = new MenuItem("Nápověda");
        MenuItem ukoncit = new MenuItem("Ukončení aplikace");
        //Pridani funkci tlacitkum
        proDivaky.setOnAction(actionEvent -> new ProDivaky());
        napoveda.setOnAction(actionEvent -> vypsaniNapovedy());
        ukoncit.setOnAction(actionEvent -> Platform.exit());

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
