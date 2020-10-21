package cz.zcu.kiv.lipka.uur.exercise11.test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.loadui.testfx.Assertions.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.matcher.base.NodeMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import cz.zcu.kiv.lipka.uur.exercise11.Person;
import cz.zcu.kiv.lipka.uur.exercise11.TestFXMLDemo;

public class TestFXMLDemoTest extends ApplicationTest{

	// setup of all tests - creation of the JavaFX application with content
	// loaded from FXML file
	@Override
	public void start(Stage primaryStage) throws Exception {
    	try {
    		Parent root = FXMLLoader.load(this.getClass().getResource(TestFXMLDemo.MAIN_WINDOW_XML));
    		primaryStage.setScene(new Scene(root));
    		primaryStage.show();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}	
	}	
	
	// test if the name of the person will be changed when one person is selected
	// from the list and the name is changed in the editors on the right side 
	@Test 
	public void changePersonTest() {
		// selecting person - click on field with text "Karas, Jan" 
		clickOn("Karas, Jan");
		// double click on TextField with id "#nameTFE" - doubleclick ensures
		// that the old text will be selected and replaced with a new one
		// writing a new name
		doubleClickOn("#nameTFE").write("Honza");
		doubleClickOn("#surnameTFE").write("Karásek");
		// confirming changes
		clickOn("#confirmBT");
		
		// verifying that list witch id #personList contains at least one child with
		// visible text "Karásek, Honza" - newly added name
		// fails when changed name was not propagated to the list
		verifyThat(lookup("#personList").queryFirst(), hasChild("Karásek, Honza"));
	}
	
	// test if delete button is working
	@Test
	public void deletePersonTest() {
		// selecting one existing person from the list
		clickOn("Karas, Jan");
		// removing selected person by clicking on button with id removeBT
		clickOn("#removeBT");
				
		// checking if the person is really missing
		// "Predicate" used - checking that the list with id "#personList" is not 
		// containing any item with name Jan Karas
		// Predicate contains one method, that returns true if the condition is fulfilled
		// and false if the condition is not fulfilled (i.e. list contain one person with 
		// name Jan and surename Karas)
		// Real parameter of the predicate is the node that was found by query looking for
		// #personList (first parameter of verifyThat)
		// test fails when predicat will return false - condition is not fulfilled
		verifyThat("#personList", (ListView<Person> personTV) -> {
			// find if in the list is person with name Jan Karas
			// using filters from stream API 
			Optional<Person> tested = personTV.getItems().stream()
			 				   .filter(person -> {
			 					   if ((person.getName().compareTo("Jan") == 0) && ((person.getSurname().compareTo("Karas") == 0))) {
			 						   return true;
			 					   } else {
			 						   return false;
			 					   }
			 				   }).findAny();
			
			// if Jan Karas was found, test have to fail --> it was not removed from the list			
			if (tested.isPresent()) {
				return false;
			} else {
				return true;
			}
		});
	}
 
	// testing if adding new person is working
	@Test
	public void addNewPersonTest() {
		clickOn("#nameTFA").write("Jan");
		clickOn("#surnameTFA").write("Novák");
		clickOn("#addBT");		
		
		// when new person was added, personList have to contain a child with name
		// of new person - it have a child element (ListCell) with this text
		verifyThat(lookup("#personList").queryFirst(), hasChild("Novák, Jan"));
	}
	
	// testing if incorrect date is highlighted by red color
	@Test 
	public void insertWrongDateTest() {		
		clickOn("Karas, Jan");
		// writing string that is not date to date field
		clickOn("#birthdateTFE").write("abcd");		
		
		// using predicate to verify that the textField has a style that colors it 
		// to red color
		verifyThat(lookup("#birthdateTFE").queryFirst(), (TextField field) -> {
			// if TextField is styled to have red content, predicate is fulfilled
			if (field.getStyle().contains("-fx-text-inner-color: red")) return true;
			else return false;
		});
	}
	
	// testing if correct date is displayed properly, with black letters
	@Test 
	public void insertCorrectDateTest() {
		clickOn("Karas, Jan");
		clickOn("#birthdateTFE").write("1.1.2010");		
		
		// using predicate to verify that the textField has a style that colors it 
		// to black color		
		verifyThat(lookup("#birthdateTFE").queryFirst(), (TextField field) -> {	
			// if TextField is styled to have black content, predicate is fulfilled
			if (field.getStyle().contains("-fx-text-inner-color: black")) return true;
			else return false;
		});
	}
	
	// testing if the age of person is correctly computed and diplayed
	@Test
	public void computeAgeTest() {
		clickOn("Karas, Jan");
		// writing birthdate to appropriate textField 
		clickOn("#birthdateTFE").write("1.1.2010");	
		// confirming the birthdate
		clickOn("#confirmBT");
		
		// computing expected age from the provided date
		long age = ChronoUnit.YEARS.between(LocalDate.of(2010, 1, 1), LocalDate.now());
		
		// checking that the field with age (id ageTFE) contains expected age
		verifyThat(lookup("#ageTFE").queryFirst(), hasText(age + ""));
	}
	
	// testing if the age is not editable
	@Test
	public void ageNotEditableTest() {
		// using predicate to verify that the node with id ageTFE is not editable
		verifyThat(lookup("#ageTFE").queryFirst(), (TextField field) -> {			
			if (field.isEditable()) return false;
			else return true;
		});		
	}
	
	// testing if the editors are updated correctly
	@Test
	public void updateEditorsTest() {
		// selecting one person
		clickOn("Karas, Jan");
		
		// checking that name and surename appears in the corresponding editors
		verifyThat(lookup("#nameTFE").queryFirst(), hasText("Jan"));
		verifyThat(lookup("#surnameTFE").queryFirst(), hasText("Karas"));
	}
	
	// testing that the data from editors are properly stored in data model
	// not really a good example of unit test - should be separated to several tests
	// for each TextField
	@Test
	public void completeUpdateTest() {
		// selecting one person
		clickOn("Karas, Jan");
		// editing all properties - name, date and child count
		doubleClickOn("#nameTFE").write("Honza");
		doubleClickOn("#surnameTFE").write("Karásek");
		doubleClickOn("#birthdateTFE").write("1.1.2010");
		doubleClickOn("#childNumTFE").write("3");
		// confirming changes
		clickOn("#confirmBT");		
		
		// selecting another person
		clickOn("Fialová, Eva");
		// selecting edited person - using the newly changed name
		clickOn("Karásek, Honza");
		
		// verifying that the new name is displayed properly 
		verifyThat(lookup("#nameTFE").queryFirst(), hasText("Honza"));
		verifyThat(lookup("#surnameTFE").queryFirst(), hasText("Karásek"));
		// verifying that the birthdate is displayed properly 
		verifyThat(lookup("#birthdateTFE").queryFirst(), hasText("1.1.2010"));
		
		// verifying that the age is calculated and displayed properly
		long age = ChronoUnit.YEARS.between(LocalDate.of(2010, 1, 1), LocalDate.now());		
		verifyThat(lookup("#ageTFE").queryFirst(), hasText(age + ""));
		// verifiyng that the number of kids is displayed properly
		verifyThat(lookup("#childNumTFE").queryFirst(), hasText(3 + ""));		
	}
}
