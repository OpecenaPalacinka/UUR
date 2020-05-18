import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CasATretina {
    private IntegerProperty tretina;
    private IntegerProperty cas;

    public CasATretina(){
        this.tretina = new SimpleIntegerProperty(1);
        this.cas = new SimpleIntegerProperty();
    }

    public int getTretina() {
        return tretina.get();
    }

    public IntegerProperty tretinaProperty() {
        return tretina;
    }

    public void setTretina(int tretina) {
        this.tretina.set(tretina);
    }

    public int getCas() {
        return cas.get();
    }

    public IntegerProperty casProperty() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas.set(cas);
    }
}
