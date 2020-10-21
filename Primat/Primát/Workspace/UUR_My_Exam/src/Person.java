import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Person {
	private StringProperty name;
	private ObjectProperty<Gender> gender;
	private IntegerProperty advance;
	private IntegerProperty salary;	
	
	public Person(String name, Gender gender, int advance, int salary) {
		super();
		this.name = new SimpleStringProperty(name);
		this.gender = new SimpleObjectProperty<Gender>(gender);
		this.advance = new SimpleIntegerProperty(advance);
		this.salary = new SimpleIntegerProperty(salary);
	}
	
	public String toString() {
		return name.get() + " (" + gender.get() + ")";
	}

	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public Gender getGender() {
		return gender.get();
	}
	
	public void setGender(Gender gender) {
		this.gender.set(gender);
	}
	
	public ObjectProperty<Gender> genderProperty() {
		return gender;
	}
	
	public int getAdvance() {
		return advance.get();
	}
	
	public void setAdvance(int advance) {
		this.advance.set(advance);
	}
	
	public IntegerProperty advanceProperty() {
		return advance;
	}
	
	public int getSalary() {
		return salary.get();
	}
	
	public void setSalary(int salary) {
		this.salary.set(salary);
	}	
	
	public IntegerProperty salaryProperty() {
		return salary;
	}
	
	public static ObservableList<Person> getDefaultData() {
		ObservableList<Person> data = FXCollections.observableArrayList();
		
		data.add(new Person("Janos Horvay", Gender.MALE, 15000, 20000));
		data.add(new Person("Pal Szinyei Merse", Gender.MALE, 10000, 18000));
		data.add(new Person("Lajos Vajda", Gender.MALE, 0, 35000));
		data.add(new Person("Csaba Markus", Gender.MALE, 20000, 20000));
		data.add(new Person("Sandor Bortnyik", Gender.MALE, 8000, 17000));
		
		data.add(new Person("Anna Kethly", Gender.FEMALE, 15000, 20000));
		data.add(new Person("Erzsebet Bathory", Gender.FEMALE, 10000, 18000));
		data.add(new Person("Ilona Zrinyi", Gender.FEMALE, 0, 35000));
		data.add(new Person("Flora Sass", Gender.FEMALE, 20000, 20000));
		data.add(new Person("Ilona Tóth", Gender.FEMALE, 8000, 17000));
		
		return data;
	}
}
