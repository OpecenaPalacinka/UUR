import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.awt.*;

public class ProPoradatele extends Application {
    TableView<Hrac1> table1 = new TableView<>();
    TableView<Hrac2> table2 = new TableView<>();
    private Tym1 tym1 = new Tym1();
    private Tym2 tym2 = new Tym2();
    private Hrac1 hrac1 = new Hrac1(0,"Nekdo Nekdo");
    private Hrac2 hrac2 = new Hrac2(0, "Nekdo Nekdo");
    private CasATretina casATretina = new CasATretina();
    int minCislo = 1;
    int maxCislo = 99;
    public int tretina = 0;
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
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setMinWidth(dimension.getWidth()-200);
        primaryStage.setMinHeight(dimension.getHeight()-100);
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
        pocetGolu.setFont(new Font("Calibri",20));

        TextField tymSkore2 = new TextField();
        tymSkore2.setFont(new Font(16));
        tymSkore2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    tymSkore2.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
        tymSkore2.textProperty().bindBidirectional(tym2.pocetGoluProperty(),new NumberStringConverter());
        tymSkore2.setMaxSize(50,100);

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
        buttonyPlusMinus.setSpacing(15);
        skoreMinus2.setFont(new Font(14));

        buttonyPlusMinus.setPadding(new Insets(15));
        skoreAButtony.setPadding(new Insets(25,0,25,125));
        buttonyPlusMinus.getChildren().addAll(skorePlus2,skoreMinus2);
        skoreAButtony.getChildren().addAll(tymSkore2,buttonyPlusMinus);

        TextField tym2TF = new TextField();
        tym2TF.setText("Jmeno tymu2");
        tym2TF.textProperty().bindBidirectional(tym2.jmenoTymuProperty());
        tym2TF.setOnAction(actionEvent -> tym2.setJmenoTymu(tym2TF.toString()));
        tym2TF.setMinSize(200,30);
        tym2TF.setMaxSize(200,30);

        table2.setEditable(false);
        table2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table2.setMinSize(300,500);
        table2.setMaxSize(300,500);

        TableColumn<Hrac2,Integer> cisloCol = new TableColumn<>("Číslo hráče");
        cisloCol.setCellValueFactory(new PropertyValueFactory<>("cisloHrace"));

        TableColumn<Hrac2,String> jmenoCol = new TableColumn<>("Jméno hráče");
        jmenoCol.setCellValueFactory(new PropertyValueFactory<>("jmenoHrace"));

        GridPane pridani = new GridPane();
        Label pridaniLabelCislo = new Label("Číslo:");
        Label pridaniLabelJmeno = new Label("Jméno hráče:");
        TextField pridaniCislo = new TextField();
        pridaniCislo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pridaniCislo.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        pridaniCislo.setMinSize(35,30);
        pridaniCislo.setMaxSize(35,30);
        TextField pridaniJmeno = new TextField();
        pridaniJmeno.setMinSize(150,30);
        pridaniJmeno.setMaxSize(150,30);
        Button addBT = new Button("Přidat");
        addBT.setOnAction(actionEvent -> {
            Hrac2 novyHrac = new Hrac2(Integer.parseInt(pridaniCislo.getText()),pridaniJmeno.getText());
            table2.getItems().add(novyHrac);
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
        pravaLista.setPadding(new Insets(25,85,0,0));
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
        tretinaField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tretinaField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        tretinaField.setMinSize(75,100);
        tretinaField.setMaxSize(75,100);
        FlowPane tretinaButtony = new FlowPane();
        Button plus = new Button("+");
        plus.setOnAction(actionEvent -> casATretina.setTretina(casATretina.getTretina()+1));
        Button minus = new Button("-");
        minus.setOnAction(actionEvent -> {
            if(casATretina.getTretina()-1<1){
                casATretina.setTretina(1);
            } else {
                casATretina.setTretina(casATretina.getTretina()-1);
            }
        });
        minus.setFont(new Font(12));
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
        cas.setMinSize(400,150);
        cas.setMaxSize(400,150);
        cas.setAlignment(Pos.TOP_CENTER);
        cas.setText("Aktualni cas");

        FlowPane startStop = new FlowPane();
        Button start = new Button("Start");
        start.setMinSize(150,50);
        start.setMaxSize(150,50);

        Button stop = new Button("Stop");
        stop.setMinSize(150,50);
        stop.setMaxSize(150,50);
        startStop.setHgap(25);
        startStop.setAlignment(Pos.CENTER);
        startStop.getChildren().addAll(start,stop);

        casBox.setSpacing(35);
        casBox.setAlignment(Pos.CENTER);
        casBox.getChildren().addAll(casLabel,cas,startStop);
        horniBox.setAlignment(Pos.TOP_CENTER);
        horniBox.getChildren().addAll(tretina,casBox);

        CheckBox timeoutTym1 = new CheckBox();
        Label timeout = new Label("Timeout");
        CheckBox timeoutTym2 = new CheckBox();
        timeouty.setAlignment(Pos.CENTER);
        timeouty.setSpacing(150);
        timeouty.getChildren().addAll(timeoutTym1,timeout,timeoutTym2);

        TextArea vylouceni21 = new TextArea();
        Label vylouceni2 = new Label("Vyloučení na 2 minuty");
        TextArea vyloceni22 = new TextArea();
        vylouceniNa2.setAlignment(Pos.CENTER);
        vylouceniNa2.setSpacing(50);
        vylouceni21.setMinSize(300,100);
        vylouceni21.setMaxSize(300,100);
        vyloceni22.setMinSize(300,100);
        vyloceni22.setMaxSize(300,100);
        vylouceniNa2.getChildren().addAll(vylouceni21,vylouceni2,vyloceni22);

        TextArea vylouceni51 = new TextArea();
        Label vylouceni5 = new Label("Vyloučení na 5 minut");
        TextArea vylouceni52 = new TextArea();
        vylouceniNa5.setAlignment(Pos.CENTER);
        vylouceniNa5.setSpacing(50);
        vylouceni51.setMinSize(300,100);
        vylouceni51.setMaxSize(300,100);
        vylouceni52.setMinSize(300,100);
        vylouceni52.setMaxSize(300,100);
        vylouceniNa5.getChildren().addAll(vylouceni51,vylouceni5,vylouceni52);

        GridPane pridani = new GridPane();
        Label pridaniLabelTym = new Label("Tým:");
        Label pridaniLabelCislo = new Label("Číslo hráče:");
        Label pridaniLabelVylouceni = new Label("Doba vylouceni:");
        TextField pridaniTym = new TextField("ChoiceBox");
        pridaniTym.setMinSize(35,30);
        pridaniTym.setMaxSize(35,30);
        TextField pridaniCislo = new TextField();
        pridaniCislo.setMinSize(150,30);
        pridaniCislo.setMaxSize(150,30);
        ChoiceBox<TypVylouceni> vylouceniTyp = new ChoiceBox<>(FXCollections.observableArrayList(TypVylouceni.values()));
        vylouceniTyp.setMinSize(80,30);
        vylouceniTyp.setMaxSize(80,30);
        Button addBT = new Button("Přidat");
        addBT.setMinSize(100,30);
        addBT.setMaxSize(100,30);
        pridani.add(pridaniLabelTym, 0, 0);
        pridani.add(pridaniTym,0,1);
        pridani.add(pridaniLabelCislo,1,0);
        pridani.add(pridaniCislo,1,1);
        pridani.add(pridaniLabelVylouceni,2,0);
        pridani.add(vylouceniTyp,2,1);
        pridani.add(addBT,4,1);

        pridani.setHgap(10);
        pridani.setVgap(5);
        pridani.setPadding(new Insets(5));
        pridani.setAlignment(Pos.CENTER);

        prostredek.setPadding(new Insets(30,50,0,50));
        prostredek.setSpacing(50);
        prostredek.setAlignment(Pos.TOP_CENTER);
        prostredek.getChildren().addAll(horniBox,timeouty,vylouceniNa2,vylouceniNa5,pridani);
        return prostredek;
    }

    public Node createLeft(){
        VBox levaLista = new VBox();
        HBox skoreAButtony = new HBox();
        VBox buttony = new VBox();

        Label pocetGolu = new Label("Počet gólů týmu 1");
        pocetGolu.setFont(new Font("Calibri",20));

        TextField tymSkore1 = new TextField();
        tymSkore1.setFont(new Font(16));
        tymSkore1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    tymSkore1.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
        tymSkore1.textProperty().bindBidirectional(tym1.pocetGoluProperty(),new NumberStringConverter());
        tymSkore1.setMaxSize(50,100);

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
        buttony.setSpacing(15);
        skoreMinus1.setFont(new Font(14));

        buttony.setPadding(new Insets(15));
        skoreAButtony.setPadding(new Insets(25,0,25,125));
        buttony.getChildren().addAll(skorePlus1,skoreMinus1);
        skoreAButtony.getChildren().addAll(tymSkore1,buttony);

        TextField tym1TF = new TextField();
        tym1TF.setText("Jmeno tymu1");
        tym1TF.textProperty().bindBidirectional(tym1.jmenoTymuProperty());
        tym1TF.setOnAction(actionEvent -> tym1.setJmenoTymu(tym1TF.toString()));
        tym1TF.setMinSize(200,30);
        tym1TF.setMaxSize(200,30);

        table1.setEditable(false);
        table1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn<Tym1,Integer> cisloCol = new TableColumn<>("Číslo hráče");
        TableColumn<Tym1,String> jmenoCol = new TableColumn<>("Jméno hráče");
        table1.setMinSize(300,500);
        table1.setMaxSize(300,500);

        GridPane pridani = new GridPane();
        Label pridaniLabelCislo = new Label("Číslo:");
        Label pridaniLabelJmeno = new Label("Jméno hráče:");
        TextField pridaniCislo = new TextField();
        pridaniCislo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pridaniCislo.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        pridaniCislo.setMinSize(35,30);
        pridaniCislo.setMaxSize(35,30);
        TextField pridaniJmeno = new TextField();
        pridaniJmeno.setMinSize(150,30);
        pridaniJmeno.setMaxSize(150,30);
        Button addBT = new Button("Přidat");
        addBT.setMinSize(100,30);
        addBT.setMaxSize(100,30);
        Button removeBT = new Button("Odstranit");
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
        levaLista.setPadding(new Insets(25,0,0,85));
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

}
