

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class DU04 extends Application {

    public static void main(String[] args) {
	    launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createRootPane());
        stage.setScene(scene);
        stage.setTitle("Program na ovládání robota");
        stage.show();
    }

    private Parent createRootPane(){
        BorderPane rootPane = new BorderPane();

        rootPane.setTop(createMenu());
        rootPane.setLeft(createOptionPane());
        rootPane.setRight(createOtherOptionPane());
        rootPane.setCenter(createMainButtons());
        rootPane.setBottom(createBottom());

        return rootPane;
    }

    private Node createBottom(){
        GridPane napisy = new GridPane();
        Label popisek1 = new Label("Stav baterie");
        Label popisek2 = new Label("Ujete metry");
        Label popisek3 = new Label("Doba od aktivace");
        Label popisek4 = new Label("Zapnutý nebo vypnutý");
        popisek1.setPadding(new Insets(7));
        popisek2.setPadding(new Insets(7));
        popisek3.setPadding(new Insets(7));
        popisek4.setPadding(new Insets(7));

        TextArea text1 = new TextArea();
        TextArea text2 = new TextArea();
        TextArea text3 = new TextArea();
        TextArea text4 = new TextArea();
        text1.setText("Procenta baterie%");
        text2.setText("Ujeté metry");
        text3.setText("Doba zapnutí");
        text4.setText("Zapnutý/Vypnutý");


        napisy.add(popisek1,0,0);
        napisy.add(popisek2,1,0);
        napisy.add(popisek3,2,0);
        napisy.add(popisek4,3,0);
        napisy.add(text1,0,1);
        napisy.add(text2,1,1);
        napisy.add(text3,2,1);
        napisy.add(text4,3,1);
        napisy.setAlignment(Pos.CENTER);


        return napisy;
    }

    private Node createMainButtons(){
        VBox controlPane = new VBox();
        FlowPane buttons = new FlowPane();
        VBox textArea = new VBox();
        FlowPane infoASipky = new FlowPane();
        VBox psanicko = new VBox();
        GridPane sipky = new GridPane();

        RadioButton onBtn = new RadioButton("Zapnuti zarizeni");
        RadioButton offBtn = new RadioButton("Vypnuti zarizeni");
        RadioButton resBtn = new RadioButton("Restart zarizeni");
        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(onBtn,offBtn,resBtn);
        buttons.getChildren().addAll(onBtn,offBtn,resBtn);

        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setHgap(25);
        buttons.setPadding(new Insets(15));


        TextArea textArea1 = new TextArea();
        textArea1.setText("Tady se vypisuje aktualni stav robota...");
        textArea.getChildren().addAll(textArea1);
        textArea.setAlignment(Pos.CENTER);
        textArea.setPrefHeight(300);

        TextArea info = new TextArea();
        Label popisek = new Label("Vypis pohybu");
        info.setText("Sem se vypisuje natoceni a rychlost...");
        info.setPrefSize(250,100);
        popisek.setPadding(new Insets(5));
        psanicko.getChildren().addAll(popisek,info);
        psanicko.setAlignment(Pos.BOTTOM_LEFT);


        Button nahoru = new Button("↑");
        Button dolu = new Button("↓");
        Button doleva = new Button("�?");
        Button doprava = new Button("→");

        sipky.add(nahoru,1,0);
        sipky.add(doleva,0,1);
        sipky.add(dolu,1,1);
        sipky.add(doprava,2,1);
        sipky.setAlignment(Pos.BOTTOM_RIGHT);
        sipky.setPadding(new Insets(20));
        nahoru.setPrefSize(50,50);
        dolu.setPrefSize(50,50);
        doleva.setPrefSize(50,50);
        doprava.setPrefSize(50,50);

        infoASipky.getChildren().addAll(psanicko,sipky);
        infoASipky.setAlignment(Pos.BOTTOM_CENTER);
        infoASipky.setHgap(250);

       controlPane.getChildren().addAll(buttons,textArea,infoASipky);
        return controlPane;
    }

    private Node createOtherOptionPane(){
        VBox otherOptionPane = new VBox();

        Label napajeni = new Label("Napajeni:");
        RadioButton btn1 = new RadioButton("Solarni");
        RadioButton btn2 = new RadioButton("Baterie");
        RadioButton btn3 = new RadioButton("RTG");

        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(btn1,btn2,btn3);

        otherOptionPane.getChildren().addAll(napajeni,btn1,btn2,btn3);
        otherOptionPane.setPadding(new Insets(25));
        otherOptionPane.setSpacing(25);
        otherOptionPane.setAlignment(Pos.CENTER);

        return otherOptionPane;
    }

    private Node createOptionPane(){
        VBox optionPane = new VBox();

        Label senzory = new Label("Senzory:");
        RadioButton btn1 = new RadioButton("Akusticky");
        RadioButton btn2 = new RadioButton("Opticky");
        RadioButton btn3 = new RadioButton("Tepelny");
        RadioButton btn4 = new RadioButton("Tlakovy");
        RadioButton btn5 = new RadioButton("Radarovy");

        optionPane.getChildren().addAll(senzory,btn1,btn2,btn3,btn4,btn5);
        optionPane.setPadding(new Insets(19));
        optionPane.setSpacing(19);
        optionPane.setAlignment(Pos.CENTER);

        return optionPane;
    }

    private MenuBar createMenu(){
        MenuBar menuBar = new MenuBar();

        Menu onOffMenu = new Menu("_ON/OFF");

        MenuItem onItem = new MenuItem("_Zapnuti programu");
        MenuItem offItem = new MenuItem("_Vypnuti programu");
        onOffMenu.getItems().addAll(onItem,offItem);

        Menu programMenu = new Menu("_Program");
        MenuItem downItem = new MenuItem("_Stazeni dat");
        MenuItem updateItem = new MenuItem("_Aktualizace programu");
        MenuItem uploadItem = new MenuItem("_Nahrani souboru do robota");
        programMenu.getItems().addAll(downItem,updateItem,uploadItem);

        menuBar.getMenus().addAll(onOffMenu,programMenu);
        return menuBar;
    }
}
