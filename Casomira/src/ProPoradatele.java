import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;

public class ProPoradatele extends Application {

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createRootPane());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Časomíra");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setMinWidth(dimension.getWidth());
        primaryStage.setMinHeight(dimension.getHeight());
        primaryStage.setMaximized(true);
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
        tymSkore2.setText("0");
        tymSkore2.setEditable(false);
        tymSkore2.setMaxSize(50,100);

        Button skorePlus2 = new Button("+");
        Button skoreMinus2 = new Button("-");
        buttonyPlusMinus.setSpacing(15);
        skoreMinus2.setFont(new Font(14));

        buttonyPlusMinus.setPadding(new Insets(15));
        skoreAButtony.setPadding(new Insets(25,0,25,125));
        buttonyPlusMinus.getChildren().addAll(skorePlus2,skoreMinus2);
        skoreAButtony.getChildren().addAll(tymSkore2,buttonyPlusMinus);

        TextArea tym2 = new TextArea();
        tym2.setText("Jmeno tymu2");
        tym2.setMinSize(200,30);
        tym2.setMaxSize(200,30);

        TextArea tymSoupiska2 = new TextArea();
        tymSoupiska2.setText("Soupiska tymu 2");
        tymSoupiska2.setMinSize(300,500);
        tymSoupiska2.setMaxSize(300,500);

        GridPane pridani = new GridPane();
        Label pridaniLabelCislo = new Label("Číslo:");
        Label pridaniLabelJmeno = new Label("Jméno hráče:");
        TextField pridaniCislo = new TextField();
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

        pravaLista.setSpacing(25);
        pravaLista.setPadding(new Insets(25,85,0,0));
        pravaLista.setAlignment(Pos.TOP_CENTER);
        pravaLista.getChildren().addAll(pocetGolu,skoreAButtony,tym2, tymSoupiska2,pridani);
        return pravaLista;
    }

    public Node createMid(){
        VBox prostredek = new VBox();
        VBox tretina = new VBox();
        VBox casBox = new VBox();
        HBox horniBox = new HBox();
        HBox timeouty = new HBox();
        HBox vylouceniNa2 = new HBox();
        HBox vylouceniNa5 = new HBox();

        Label tretinaLabel = new Label("Třetina:");
        TextField tretinaField = new TextField();
        tretinaField.setEditable(false);
        tretinaField.setMinSize(75,100);
        tretinaField.setMaxSize(75,100);
        FlowPane tretinaButtony = new FlowPane();
        Button plus = new Button("+");
        Button minus = new Button("-");
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

        Button startStop = new Button("Start");
        startStop.setMinSize(200,50);
        startStop.setMaxSize(200,50);

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
        tymSkore1.setText("0");
        tymSkore1.setEditable(false);
        tymSkore1.setMaxSize(50,100);
        Button skorePlus1 = new Button("+");
        Button skoreMinus1 = new Button("-");
        buttony.setSpacing(15);
        skoreMinus1.setFont(new Font(14));

        buttony.setPadding(new Insets(15));
        skoreAButtony.setPadding(new Insets(25,0,25,125));
        buttony.getChildren().addAll(skorePlus1,skoreMinus1);
        skoreAButtony.getChildren().addAll(tymSkore1,buttony);

        TextArea tym1 = new TextArea();
        tym1.setText("Jmeno tymu1");
        tym1.setMinSize(200,30);
        tym1.setMaxSize(200,30);

        TextArea tymSoupiska1 = new TextArea();
        tymSoupiska1.setText("Soupiska tymu 1");
        tymSoupiska1.setMinSize(300,500);
        tymSoupiska1.setMaxSize(300,500);

        GridPane pridani = new GridPane();
        Label pridaniLabelCislo = new Label("Číslo:");
        Label pridaniLabelJmeno = new Label("Jméno hráče:");
        TextField pridaniCislo = new TextField();
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
        levaLista.getChildren().addAll(pocetGolu,skoreAButtony,tym1, tymSoupiska1,pridani);
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
        //Pridani funkci tlacitkum

        insertMenu.getItems().addAll(novejZapas,restartZapas,ulozitZapas);
        hraMenu.getItems().addAll(insertMenu,historie);

        Menu nastroje = new Menu("_Nástroje");
        MenuItem proDivaky = new MenuItem("Zobrazení pro diváky");
        MenuItem napoveda = new MenuItem("Nápověda");
        MenuItem ukoncit = new MenuItem("Ukončení aplikace");
        //Pridani funkci tlacitkum
        ukoncit.setOnAction(actionEvent -> Platform.exit());
        proDivaky.setOnAction(actionEvent -> new ProDivaky());

        nastroje.getItems().addAll(proDivaky,napoveda,ukoncit);

        menuBar.getMenus().addAll(nastroje,hraMenu);
        return menuBar;
    }
}
