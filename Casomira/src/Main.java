import javafx.application.Application;
import javafx.application.Platform;
import javafx.css.Style;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRootPane());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Časomíra");
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
        GridPane  tymASkore = new GridPane();
        tymASkore.setGridLinesVisible(true);

        TextArea tymSkore2 = new TextArea();
        tymSkore2.setText("0");
        Button skorePlus2 = new Button("+");
        Button skoreMinus2 = new Button("-");

        tymSkore2.setPrefSize(75,150);
        tymASkore.add(tymSkore2,0,0,1,4);
        tymASkore.add(skorePlus2,2,1);
        tymASkore.add(skoreMinus2,2,3);
        tymASkore.setAlignment(Pos.TOP_CENTER);
        tymASkore.setPadding(new Insets(15));

        TextArea tym2 = new TextArea();
        tym2.setText("Jmeno tymu2");
        tym2.setPrefSize(100,30);

        TextArea tymSoupiska2 = new TextArea();
        tymSoupiska2.setText("Soupiska tymu 2");
        tymSoupiska2.setPrefSize(200,600);

        pravaLista.getChildren().addAll();
        return pravaLista;
    }

    public Node createMid(){
        VBox prostredek = new VBox();

        TextArea cas = new TextArea();
        cas.setText("Aktualni cas");

        Button startStop = new Button("Start");

        CheckBox timeoutTym1 = new CheckBox();
        Label timeout = new Label();
        CheckBox timeoutTym2 = new CheckBox();

        TextArea vylouceni21 = new TextArea();
        Label vylouceni2 = new Label();
        TextArea vyloceni22 = new TextArea();

        TextArea vylouceni51 = new TextArea();
        Label vylouceni5 = new Label();
        Text vylouceni52 = new Text();


        prostredek.getChildren().addAll();
        return prostredek;
    }

    public Node createLeft(){
        VBox levaLista = new VBox();
        GridPane  tymASkore = new GridPane();
        tymASkore.setGridLinesVisible(true);

        TextArea tymSkore1 = new TextArea();
        tymSkore1.setText("0");
        Button skorePlus1 = new Button("+");
        Button skoreMinus1 = new Button("-");

        tymSkore1.setPrefSize(75,150);
        tymASkore.add(tymSkore1,0,0,1,4);
        tymASkore.add(skorePlus1,2,1);
        tymASkore.add(skoreMinus1,2,3);
        tymASkore.setAlignment(Pos.TOP_CENTER);
        tymASkore.setPadding(new Insets(15));

        TextArea tym1 = new TextArea();
        tym1.setText("Jmeno tymu1");
        tym1.setPrefSize(100,30);

        TextArea tymSoupiska1 = new TextArea();
        tymSoupiska1.setText("Soupiska tymu 1");
        tymSoupiska1.setPrefSize(200,600);

        levaLista.getChildren().addAll(tymASkore,tym1, tymSoupiska1);
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
        MenuItem napoveda = new MenuItem("Nápověda");
        MenuItem ukoncit = new MenuItem("Ukončení aplikace");
        //Pridani funkci tlacitkum
        ukoncit.setOnAction(actionEvent -> Platform.exit());

        nastroje.getItems().addAll(napoveda,ukoncit);

        menuBar.getMenus().addAll(nastroje,hraMenu);
        return menuBar;
    }
}
