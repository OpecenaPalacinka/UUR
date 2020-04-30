import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class DataModel {
    private StringProperty name;
    private ObjectProperty<Barvy> color;
    private ObjectProperty<Rez> rez;
    private IntegerProperty size;
    private BooleanProperty visibility;



    public DataModel() {
        this.name = new SimpleStringProperty("Name");
        this.color = new SimpleObjectProperty<>(Barvy.BLACK);
        this.rez = new SimpleObjectProperty<>(Rez.normal);
        this.size = new SimpleIntegerProperty(12);
        this.visibility = new SimpleBooleanProperty(true);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<Barvy> colorProperty() {
        return color;
    }

    public ObjectProperty<Rez> rezProperty() {
        return rez;
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    public String getName() {
        return name.get();
    }

    public Barvy getColor() {
        return color.get();
    }

    public void setColor(Barvy barva) {this.color.set(barva);}

    public Rez getRez() {
        return rez.get();
    }

    public int getSize() {
        return size.get();
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public boolean isVisibility() {
        return visibility.get();
    }

    @Override
    public String toString() {
        return  "Jmeno fontu = " + name.get() +
                ", barva = " + color.get() +
                ", rez = " + rez.get() +
                ", velikost = " + size.get() +
                ", viditelnost = " + visibility.get();
    }
}
