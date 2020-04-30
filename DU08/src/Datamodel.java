import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Datamodel implements Comparable<Datamodel> {
	private StringProperty name;
	private ObjectProperty<Typ> typ;
	

	public Datamodel(String name, Typ typ) {
		super();
		this.name = new SimpleStringProperty(name);
		this.typ = new SimpleObjectProperty<Typ>(typ);
	}
	
	// ------------------------------------------------------
	// ------------  Geters and seters ----------------------	

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public Typ getTyp() {
		return typ.get();
	}

	public void setTyp(Typ typ) {
		this.typ.set(typ);
	}	
	
	public ObjectProperty<Typ> typProperty() {
		return typ;
	}
	
	// ------------------------------------------------------
	// ----------  ToString only for display ----------------	

	@Override
	public String toString() {
		return "Object [name = " + name.get() + ", typ = " + typ.get() + "]";
	}

	@Override
	public int compareTo(Datamodel o) {
		return this.getName().compareTo(o.getName());
	}
}
