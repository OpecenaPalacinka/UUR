import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;

public class Main extends Application {

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRootPane());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Časomíra");
        primaryStage.show();
    }

    private Parent createRootPane(){
        BorderPane rootPane = new BorderPane();

        rootPane.setTop(createMenu());

        return rootPane;
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
