package cz.zcu.kiv.lipka.uur.exercise07;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

/**
 * New type of table cell, based on the DatePicker. Label is used for presenting data in the cell, 
 * in editation state DatePicker is used to select the date by user. 
 *  
 * @author Richard Lipka
 *
 */
public class DatePickerCell extends TableCell<Person, LocalDate> {
	private DatePicker dateDP;
	
	// switching to the editation mode
	public void startEdit() {
		// propagation of mode switching to the parent class
		super.startEdit();		
		
		// creating editor - DatePicker if it is not already prepared
		if (dateDP == null) {
			createDatePicker();
		}
		// disabling label with text
		setText(null);		
		// setting up editor
		setGraphic(dateDP);
		
		// opening editor (fields with calendar)
		dateDP.show();
	}
	
	// switching from editation to presentation mode
	public void cancelEdit() {
		super.cancelEdit();
		
		System.out.println("canceling editation");
		
		// returns value in datepicker to original value
		dateDP.setValue(getItem());
		
		// setting label 
		setText(getItem().toString());
		// removing date picker  
		setGraphic(null);
	}
	
	// creating date picker
	private void createDatePicker() {
		dateDP = new DatePicker(getItem());

		// consuming DELETE key event so it is not invoking row deletion when datePicker is open
		dateDP.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.DELETE) {
				event.consume();
			}
		});
		
		// reaction on commit - when date is selected, date picker have to create commit event 
		dateDP.setOnAction(event -> {			
			// commit when date was selected
			
			
			if (dateDP.getValue() != null) {
				commitEdit(dateDP.getValue());
			// cancel when no date was provided	
			} else {
				event.consume();
				cancelEdit();
			}
		});
	}
	
	// setting value to the cell when model is changed or manipulated
	// this method have to be always overloaded, when new cell type is created
	public void updateItem(LocalDate item, boolean empty) {
		// propagating change to the parent class
		super.updateItem(item, empty);
		
		// setting font for the label
		setFont(new Font("Arial", 15));
		// when no item is provided, cell is showing no information
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			// in editation mode
			if (isEditing()) {
				// setting the value to editor - date picker
				if (dateDP != null) {
					dateDP.setValue(item);
				} 
				// disabling label
				setText(null);
				// setting up editor
				setGraphic(dateDP);
			// in presentation mode	
			} else {
				// setting the date to label
				setText(item.toString());
				// disabling editor
				setGraphic(null);
			}
		}
	}
}
