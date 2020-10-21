package cz.zcu.kiv.lipka.uur.exercise07;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *	Class representing data in table - basic class of the model
 * 
 * @author Richard Lipka
 *
 */
public class Person {
	private StringProperty name;
	private StringProperty surname;
	private ObjectProperty<LocalDate> birthday;
	private ObjectProperty<Day> day;
	
	public Person(String name, String surname, LocalDate birthday) {
		super();
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
		this.day = new SimpleObjectProperty<Day>(Day.MONDAY);
	}

	public final String getName() {
		return name.get();
	}

	public final void setName(String name) {
		this.name.set(name);;
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public final String getSurname() {
		return surname.get();
	}

	public final void setSurname(String surname) {
		this.surname.set(surname);
	}
	
	public StringProperty surnameProperty() {
		return surname;
	}

	public final LocalDate getBirthday() {
		return birthday.get();
	}

	public final void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}
	
	public ObjectProperty<LocalDate> bithdayProperty() {
		return birthday;
	}
	
	public ObjectProperty<Day> dayProperty() {
		return day;
	}

	@Override
	public String toString() {
		return TableDemo.messages.getString("Person.0") + name.get() + TableDemo.messages.getString("Person.1") + surname.get() + TableDemo.messages.getString("Person.2") 
				+ birthday.get() + TableDemo.messages.getString("Person.3"); 
	}
	
}
