package cz.zcu.kiv.lipka.uur.exercise11;

import java.text.Collator;
import java.time.LocalDate;
import java.util.Locale;

import sun.text.resources.cs.CollationData_cs;

public class Person implements Comparable<Person>{
	private String name;
	private String surname;
	private LocalDate birthDate;
	private int childCount;
	
	public Person() {
		
	}
		
	public Person(String name, String surname, LocalDate birthDate,	int childCount) {
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.childCount = childCount;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public int getChildCount() {
		return childCount;
	}
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	
	public String toString() {
		return surname + ", " + name;
	}

	@Override
	public int compareTo(Person o) {
		Collator comparator = Collator.getInstance(new Locale("cs"));
		return comparator.compare(surname + " " + name, o.getSurname() + " " + getName());
	}
	
}
