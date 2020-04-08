import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

public class Main extends Application {

    private RGBDataModel model = new RGBDataModel(0,0,0);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(getRoot()));
        stage.setTitle("RGB čtverec");
        stage.show();
    }

    private Parent getRoot() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));

        /* VBox aby byli všechny 3 HBoxy pod sebou*/
        VBox verticalBox = new VBox();
        verticalBox.setPadding(new Insets(10));

        /* První HBox pro R*/
        HBox box1 = new HBox();
        box1.setSpacing(5);

        Label l1 = new Label("R");
        int initR = 0;
        Slider s1 = new Slider(RGBDataModel.MIN, RGBDataModel.MAX, initR);
        s1.setShowTickLabels(true);
        s1.setMajorTickUnit(1);
        s1.valueProperty().bindBidirectional(model.rProperty());

        TextField tf1 = new TextField();
        tf1.textProperty().bindBidirectional(model.rProperty(), new NumberStringConverter());
        box1.getChildren().addAll(l1,s1, tf1);

        /* Druhý HBox pro G*/
        HBox box2 = new HBox();
        box2.setSpacing(5);

        Label l2 = new Label("G");
        int initG = 0;
        Slider s2 = new Slider(RGBDataModel.MIN, RGBDataModel.MAX, initG);
        s2.setShowTickLabels(true);
        s2.setMajorTickUnit(1);
        s2.valueProperty().bindBidirectional(model.gProperty());

        TextField tf2 = new TextField();
        tf2.textProperty().bindBidirectional(model.gProperty(), new NumberStringConverter());
        box2.getChildren().addAll(l2,s2, tf2);

        HBox box3 = new HBox();
        box3.setSpacing(5);

        /* Třetí HBox pro B*/
        Label l3 = new Label("B");
        int initB = 0;
        Slider s3 = new Slider(RGBDataModel.MIN, RGBDataModel.MAX, initB);
        s3.setShowTickLabels(true);
        s3.setMajorTickUnit(1);
        s3.valueProperty().bindBidirectional(model.bProperty());

        TextField tf3 = new TextField();
        tf3.textProperty().bindBidirectional(model.bProperty(), new NumberStringConverter());
        box3.getChildren().addAll(l3,s3, tf3);

        verticalBox.getChildren().addAll(box1,box2, box3);

        /* Čtverec na barvičky*/
        Rectangle rect = new Rectangle();
        rect.setWidth(100);
        rect.setHeight(100);
        rect.fillProperty().bind(model.colorProperty());

        grid.add(verticalBox,0,0);
        grid.add(rect, 1, 0);
        return grid;
    }
}

/** Třída pro datový model*/
class RGBDataModel
{
    public static double MIN = 0;
    public static double MAX = 255;

    private IntegerProperty r;
    private IntegerProperty g;
    private IntegerProperty b;

    private ObjectProperty<Color> color;

    public RGBDataModel(int initR, int initG, int initB) {
        r = new SimpleIntegerProperty();
        g = new SimpleIntegerProperty();
        b = new SimpleIntegerProperty();
        color = new SimpleObjectProperty<>();

        r.setValue(initR);
        g.setValue(initG);
        b.setValue(initB);
        color.setValue(new Color(r.get() / MAX, g.get() / MAX, b.get() / MAX, 1));

        /* Listener na červenou, pomocí lambda výrazu*/
        r.addListener((observableValue, number, t1) -> color.setValue(new Color((int) t1 / MAX, color.get().getGreen(), color.get().getBlue(), 1)));

        /* Listener na zelenou, pomocí lambda výrazu*/
        g.addListener((observableValue, number, t1) -> color.setValue(new Color(color.get().getRed(), (int) t1 / MAX, color.get().getBlue(), 1)));

        /* Listener na modrou, pomocí anonymní třídy*/
        b.addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                color.setValue(new Color(color.get().getRed(), color.get().getGreen(), (int) t1 / MAX, 1));
            }
        });
    }

    public IntegerProperty rProperty()
    {
        return r;
    }

    public IntegerProperty gProperty()
    {
        return g;
    }

    public IntegerProperty bProperty()
    {
        return b;
    }

    public ObjectProperty<Color> colorProperty()
    {
        return color;
    }
}
