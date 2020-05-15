import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProDivaky {

   // public static void main(String[] args) {launch(args);}

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

        TextArea jmenoTymu2 = new TextArea("Jmeno tymu 2");
        jmenoTymu2.setEditable(false);
        jmenoTymu2.setMinSize(300,50);
        jmenoTymu2.setMaxSize(300,50);

        TextArea tymSoupiska2 = new TextArea("Hráč + doba vylouceni");
        tymSoupiska2.setEditable(false);
        tymSoupiska2.setMinSize(250,300);
        tymSoupiska2.setMaxSize(250,300);

        pravaLista.setSpacing(25);
        pravaLista.setPadding(new Insets(25,45,0,0));
        pravaLista.setAlignment(Pos.TOP_CENTER);
        pravaLista.getChildren().addAll(jmenoTymu2, tymSoupiska2);
        return pravaLista;
    }

    public Node createMid(){
        VBox prostredek = new VBox();
        FlowPane tretina = new FlowPane();
        FlowPane horejsek = new FlowPane();

        Label tretinaLabel = new Label("Třetina:");
        tretinaLabel.setFont(new Font(25));
        TextField tretinaField = new TextField();
        tretinaField.setMinSize(30,50);
        tretinaField.setMaxSize(30,50);
        tretina.setAlignment(Pos.TOP_CENTER);
        tretina.setHgap(15);
        tretina.getChildren().addAll(tretinaLabel,tretinaField);


        TextArea skore1 = new TextArea("0");
        Label dvojtecka = new Label(":");
        dvojtecka.setFont(new Font(65));
        TextArea skore2 = new TextArea("0");
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

        prostredek.setPadding(new Insets(30,30,0,30));
        prostredek.setSpacing(50);
        prostredek.setAlignment(Pos.TOP_CENTER);
        prostredek.getChildren().addAll(horejsek,tretina,cas);
        return prostredek;
    }

    public Node createLeft(){
        VBox levaLista = new VBox();

        TextArea tym1 = new TextArea("Jmeno tymu 1");
        tym1.setEditable(false);
        tym1.setMinSize(300,50);
        tym1.setMaxSize(300,50);

        TextArea tymSoupiska1 = new TextArea("Hráč + doba vylouceni");
        tymSoupiska1.setEditable(false);
        tymSoupiska1.setMinSize(250,300);
        tymSoupiska1.setMaxSize(250,300);

        levaLista.setSpacing(25);
        levaLista.setPadding(new Insets(25,0,0,45));
        levaLista.setAlignment(Pos.TOP_CENTER);
        levaLista.getChildren().addAll(tym1, tymSoupiska1);
        return levaLista;
    }
}
