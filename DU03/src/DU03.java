import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class DU03 extends Application{

    FlowPane platno;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //vytvoreni buttonu
        Button b1=new Button("Zobrazi ostatni tlacitka");
        Button b2=new Button("Vypise text do konzole");
        Button b3=new Button("Zmeni popisek okna");
        Button b4=new Button("Napise novy text do okna");
        Button b5=new Button("Skryje tlacitka");

        //Nove platno + vytvoreni arraylistu
        platno = new FlowPane(b1);
        ArrayList<Node> list=new ArrayList<>();
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);

        //Pomoci vnitrni anonymni tridy
        b1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                platno.getChildren().addAll(b2,b3,b4,b5);
            }
        });

        //Pomoci lambda vyrazu
        b2.setOnAction(aEvent -> System.out.println("Psanicko v konzoli"));
        b3.setOnAction(aEvent -> primaryStage.setTitle(String.valueOf(Math.random())));
        b4.setOnAction(aEvent -> platno.getChildren().add(new Label("Novej text ")));
        b5.setOnAction(aEvent -> platno.getChildren().removeAll(list));

        //Pridani platna do sceny a sceny do primaryStage + setTitle
        primaryStage.setScene(new Scene(platno,600,500));
        primaryStage.setTitle("Prvni aplikace");

        primaryStage.show();
    }


}
