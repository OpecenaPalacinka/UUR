package cz.zcu.kiv.lipka.uur.exercise07;

import javafx.scene.control.TableCell;

/**
 * 
 * Cell that is only displaying its number + 1 - number of the line in the collection
 * 
 * @author Richard Lipka
 *
 */
public class OrderCell extends TableCell<Person, Integer> {
	
	// reaction on cell update
	// value of Integer is irrelevant - no value is provided by value factory, index is 
	// determined from the cell position
	public void updateItem(Integer value, boolean empty) {
		super.updateItem(value, empty);
		
		// empty cells are created to fill space of the table 
		if (empty) {
			// the empty cells will not be numbered
			setText(null);
		} else {
			// if cell corresponds to a record in table, a number will be assigned according to the 
			// position of record in the table
			setText((getIndex() + 1) + " : ");
		}
	}
}
