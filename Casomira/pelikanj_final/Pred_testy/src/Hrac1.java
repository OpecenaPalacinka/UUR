import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Hrac1 {
    private IntegerProperty cisloHrace;
    private StringProperty jmenoHrace;
    private CasATretina vylouceni;


    public Hrac1(Integer cislo, String jmeno, TypVylouceni vylouceni){
        this.cisloHrace = new SimpleIntegerProperty(cislo);
        this.jmenoHrace = new SimpleStringProperty(jmeno);
        this.vylouceni = new CasATretina(0,vylouceni.getValue());
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

    public CasATretina getVylouceni() {
        return vylouceni;
    }

    public void setVylouceni(TypVylouceni vylouceni) {
        this.vylouceni.setMinuty(vylouceni.getValue());
    }
}