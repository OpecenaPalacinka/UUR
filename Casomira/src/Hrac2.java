import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Hrac2 {
    private IntegerProperty cisloHrace;
    private StringProperty jmenoHrace;

    public Hrac2(Integer cislo, String jmeno){
        this.cisloHrace = new SimpleIntegerProperty(cislo);
        this.jmenoHrace = new SimpleStringProperty(jmeno);
    }

    public int getCisloHrace() {
        return cisloHrace.get();
    }

    public IntegerProperty cisloHraceProperty() {
        return cisloHrace;
    }

    public void setCisloHrace(int cisloHrace) {
        this.cisloHrace.set(cisloHrace);
    }


    public String getJmenoHrace() {
        return jmenoHrace.get();
    }

    public StringProperty jmenoHraceProperty() {
        return jmenoHrace;
    }

    public void setJmenoHrace(String jmenoHrace) {
        this.jmenoHrace.set(jmenoHrace);
    }
}