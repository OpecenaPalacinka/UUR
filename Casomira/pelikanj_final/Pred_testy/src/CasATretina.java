import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CasATretina {
    private IntegerProperty tretina;
    private IntegerProperty vteriny;
    private IntegerProperty minuty;

    public CasATretina(int vteriny, int minuty){
        this.tretina = new SimpleIntegerProperty(1);
        this.vteriny = new SimpleIntegerProperty(vteriny);
        this.minuty = new SimpleIntegerProperty(minuty);
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

    public int getVteriny() {
        return vteriny.get();
    }

    public IntegerProperty vterinyProperty() {
        return vteriny;
    }

    public void setVteriny(int vteriny) {
        this.vteriny.set(vteriny);
    }

    public int getMinuty() {
        return minuty.get();
    }

    public IntegerProperty minutyProperty() {
        return minuty;
    }

    public void setMinuty(int minuty) {
        this.minuty.set(minuty);
    }

    public void odectiVterinu(){
        if(getVteriny()%60==0){
            setMinuty(getMinuty()-1);
            setVteriny(60);
        }
        setVteriny(getVteriny()-1);
    }
/*
    @Override
    public String toString(){
        return this.getMinuty()+":"+this.getVteriny();
    }
    
 */

}
