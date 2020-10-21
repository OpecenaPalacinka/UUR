package cz.zcu.kiv.lipka.uur.exercise08;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Representation of one ancestor in the tree of ancestors 
 * 
 * Derived as an offspring of TreeItem<T> so it can be directly displayed
 * in the TreeView
 * 
 * Type T is not used, it allows to insert another object to the TreeItem
 * and thus to the ancestor, but we will not use this functionality.
 * 
 * @author Richard Lipka
 *
 */
public class Ancestor implements Comparable<Ancestor> {
	private StringProperty name;
	private ObjectProperty<Sex> sex;
	
	/**
	 * Cretaes representation of one person in the tree of ancestors
	 * 
	 * @param name Name of the person
	 * @param sex sex of the person
	 */
	public Ancestor(String name, Sex sex) {
		super();
		this.name = new SimpleStringProperty(name);
		this.sex = new SimpleObjectProperty<Ancestor.Sex>(sex);
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

	public Sex getSex() {
		return sex.get();
	}

	public void setSex(Sex sex) {
		this.sex.set(sex);
	}	
	
	public ObjectProperty<Sex> sexProperty() {
		return sex;
	}
	
	// ------------------------------------------------------
	// ----------  ToString only for display ----------------	

	@Override
	public String toString() {
		return "Ancestor [name=" + name.get() + ", sex=" + sex.get() + "]";
	}
	
	// ------------------------------------------------------
	// ----------  ToString only for display ----------------
	
	public enum Sex {
		MALE, FEMALE, NEUTRAL;
		
		public String toString() {
			switch (this) {
			case MALE:
				return "Male";
			case FEMALE:
				return "Female";
			case NEUTRAL:
				return "Neuter";
			}
			return "Undefined";
		}		
		
		public String getPronoun() {
			switch (this) {
			case MALE:
				return "he";
			case FEMALE:
				return "she";
			case NEUTRAL:
				return "ze";				
			}
			return "it";	
		}
		
		public String getPossesive() {
			switch (this) {
			case MALE:
				return "his";
			case FEMALE:
				return "her";
			case NEUTRAL:
				return "hir";
			}			
			return "its";	
		}
		
		public String getSymbol() {
			switch (this) {
			case MALE:
				return "\u2642";
			case FEMALE:
				return "\u2640";				
			case NEUTRAL:
				return "\u26B2";	
			}
			return "\u2422";	
		}

		public String getParentWord() {
			switch (this) {
			case MALE:
				return "father";
			case FEMALE:
				return "mother";				
			case NEUTRAL:
				return "parent";	
			}
			return "\u2422";	
		}	
		
	}

	@Override
	public int compareTo(Ancestor o) {		
		return this.getName().compareTo(o.getName());
	}
}
