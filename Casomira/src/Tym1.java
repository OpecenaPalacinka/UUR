import javafx.beans.property.*;

public class Tym1 {
    private StringProperty jmenoTymu;
    private IntegerProperty pocetGolu;
    private BooleanProperty timeout;

    public Tym1(){
        this.jmenoTymu = new SimpleStringProperty("Jmeno tymu");
        this.pocetGolu = new SimpleIntegerProperty(0);
        this.timeout = new SimpleBooleanProperty(false);
    }

    public String getJmenoTymu() {
        return jmenoTymu.get();
    }

    public StringProperty jmenoTymuProperty() {
        return jmenoTymu;
    }

    public void setJmenoTymu(String jmenoTymu) {
        this.jmenoTymu.set(jmenoTymu);
    }

    public int getPocetGolu() {
        return pocetGolu.get();
    }

    public IntegerProperty pocetGoluProperty() {
        return pocetGolu;
    }

    public void setPocetGolu(int pocetGolu) {
        this.pocetGolu.set(pocetGolu);
    }

    public boolean isTimeout() {
        return timeout.get();
    }

    public BooleanProperty timeoutProperty() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout.set(timeout);
    }
}
