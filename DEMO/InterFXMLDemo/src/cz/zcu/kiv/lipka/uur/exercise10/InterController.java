package cz.zcu.kiv.lipka.uur.exercise10;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * Controller that is responsible for changing language and transformation of the
 * date to text, it also allows to set value of DatePicker when it is newly created
 * 
 * @author Richard Lipka
 *
 */
public class InterController {
	@FXML
	private DatePicker dateDP;
	
	@FXML
	private Label outputLB;
	
	// changing language - instead of changing all strings, window is recreated with
	// new locale setting
	@FXML
	public void changeLanguage() {
		
		SharedData data = SharedData.getSharedData();
		// storing actual value of date in DatePicker, so it will be possible to
		// set in in new DatePicker after it is created
		data.setStoredDate(dateDP.getValue());
		
		// changing locale - switching languages
		if (Locale.getDefault().getLanguage().compareTo(InterFXMLDemo.CZ_LANGUAGE) == 0) {
			Locale.setDefault(new Locale(InterFXMLDemo.UK_LANGUAGE, InterFXMLDemo.UK_COUNTRY));
		} else {
			Locale.setDefault(new Locale(InterFXMLDemo.CZ_LANGUAGE, InterFXMLDemo.CZ_COUNTRY));
		}		
		
		// creates scene again 	
		data.getPrimaryStage().setScene(data.getApplication().createScene());		
	}
	
	// creates text representation of date according to actual locale 
	@FXML
	public void translateDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		
		LocalDate date = dateDP.getValue();
		
		if (date != null) {
			outputLB.setText(formatter.format(date));
		}
	}	
	
	// setting date to DatePicker - used when scene was created after change of language 
	public void setDate(LocalDate date) {
		if (date != null) {
			dateDP.setValue(date);
		}
	}
}
