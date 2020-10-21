package cz.zcu.kiv.lipka.uur.exercise11;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller that is responsible for changing language and transformation of the
 * date to text, it also allows to set value of DatePicker when it is newly created
 * 
 * @author Richard Lipka
 *
 */
public class TestController implements Initializable{
	private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(new Locale("cs", "CZ"));
	
	@FXML
	TextField nameTFE, surnameTFE, nameTFA, surnameTFA, birthdateTFE, ageTFE, childNumTFE;
	
	@FXML
	ListView<Person> personList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		personList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
		    @Override
		    public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
		        updateEditor();
		    }
		});
	}
	
	@FXML
	public void confirmChanges() {
		String name, surname, tmpDate, tmpChildNum;
		LocalDate birthDate = null;
		int childNum = 0;
		
		Person selected = personList.getSelectionModel().getSelectedItem();
		
		if (selected == null) {
			return;
		}
		
		name = nameTFE.getText();
		surname = surnameTFE.getText();
		tmpDate = birthdateTFE.getText();
		tmpChildNum = childNumTFE.getText();
		
		if ((name == null) || (name.trim().length() == 0)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Cannot update!");
			alert.setHeaderText("It is not possible to change name of person to empty string!");
			alert.setContentText("You have to fill name of the person that should be updated!");
			alert.showAndWait();
			return;
		}
		if ((surname == null) || (surname.trim().length() == 0)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Cannot update!");
			alert.setHeaderText("It is not possible to change surname of person to empty string!");
			alert.setContentText("You have to fill surname of the person that should be updated!");
			alert.showAndWait();
			return;
		}

		if ((tmpDate == null) || (tmpDate.trim().length() == 0)) {
			birthDate = null; 
		} else {
			try {
				birthDate = LocalDate.parse(tmpDate, formatter);			
			} catch (DateTimeParseException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Cannot update!");
				alert.setHeaderText("Birthdate is not valid!");
				alert.setContentText("You have to provide birthdate in format DD.MM.YYYY!");
				alert.showAndWait();
				return;				
			}
		}
		
		if ((tmpChildNum == null) || (tmpChildNum.trim().length() == 0)) {
			childNum = 0;
		} else {
			try {
				childNum = Integer.parseInt(tmpChildNum);
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Cannot update!");
				alert.setHeaderText("Number of children have to be a number!");				
				alert.showAndWait();
				return;				
			}						
					
			if (childNum < 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Cannot update!");
				alert.setHeaderText("Number of children have to be a positive value or zero!");				
				alert.showAndWait();
				return;			
			}

			if (childNum > 20) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Update possible");
				alert.setHeaderText("It is hard to believe, but OK - if you insist :-)");				
				alert.showAndWait();
			}							
							
			selected.setName(name);
			selected.setSurname(surname);
			selected.setBirthDate(birthDate);
			selected.setChildCount(childNum);			
			
			personList.getItems().set(personList.getItems().indexOf(selected), selected);
			updateEditor();
			personList.getSelectionModel().select(selected);
		}		
	}
	
	@FXML 
	public void checkDate() {
		String tmpDate = birthdateTFE.getText();
		
		try {
			LocalDate date = LocalDate.parse(tmpDate, formatter);
			
			if (date == null) {
				birthdateTFE.setStyle("-fx-text-inner-color: red;");
			} else {
				birthdateTFE.setStyle("-fx-text-inner-color: black;");
			}			
		} catch (DateTimeParseException e) {
			birthdateTFE.setStyle("-fx-text-inner-color: red;");
		}
	}
	
	@FXML
	public void addPerson() {
		String name, surname;
		
		name = nameTFA.getText();
		surname = surnameTFA.getText();
		
		if ((name == null) || (surname == null) || (name.trim().length() == 0) || (surname.trim().length() == 0)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Cannot instert!");
			alert.setHeaderText("It is not possible to insert an empty person!");
			alert.setContentText("You have to fill name and surname of the person that should be added!");
			alert.showAndWait();
		} else {
			Person newPerson = new Person(name, surname, null, 0);
			personList.getItems().add(newPerson);
			personList.getItems().sort(null);
		}
	}
	
	@FXML
	public void deleteSelected() {
		Person selected = personList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			personList.getItems().remove(selected);
			personList.getSelectionModel().clearSelection();
			
			nameTFE.setText("");
			surnameTFE.setText("");
			birthdateTFE.setText("");
			ageTFE.setText("");	
			childNumTFE.setText("");
		}
	}
	
	private void updateEditor() {
		Person selection = personList.getSelectionModel().getSelectedItem();
		
		if (selection != null) {
			nameTFE.setText(selection.getName());
			surnameTFE.setText(selection.getSurname());
			LocalDate birthDate = selection.getBirthDate();
			if (birthDate != null) {				
				birthdateTFE.setText(selection.getBirthDate().format(formatter));
				long age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
				ageTFE.setText(age + "");
			} else {				
				birthdateTFE.setText("");
				ageTFE.setText("");
			}
			childNumTFE.setText(selection.getChildCount() + "");			
		}
	}
}
