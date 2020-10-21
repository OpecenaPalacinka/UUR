package cz.zcu.kiv.lipka.uur.exercise09;

import java.net.InetAddress;

import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;

/**
 * TableCell based on IP editor
 * 
 * @author Richard Lipka
 *
 */
public class IPEditorTableCell extends TableCell<Connection, InetAddress> {
	// editor of IP adress
	private IPEditorField editIP;

	// start of editation
	public void startEdit() {
		super.startEdit();		
		
		if (editIP == null) {
			createIPEditor();
		}
		setText(null);	
		// setting up editor
		setGraphic(editIP);	
		// setting value - IP address from the model
		editIP.setValue(getItem());
		// edited component gets focus immediately 
		editIP.requestFocus();		
	}	
	
	// stopping editation
	public void cancelEdit() {
		super.cancelEdit();
		
		// obtaining text representation of IP address for label
		setText(getItem().getHostAddress());
		// removing editor
		setGraphic(null);
	}
		
	// creating editor
	private void createIPEditor() {
		// creating IPEdiorField with current IP address
		editIP = new IPEditorField(getItem());
		
		// adding reaction on commit of new data
		editIP.setOnKeyReleased(event -> {
			// only when enter is pressed data are stored to the model
			if (event.getCode() == KeyCode.ENTER) {
				// test if there is IP address provided
				if (editIP.getValue() == null) {
					// if no address or invalid address is given, old value is used
					editIP.setValue(getItem());
					// data are not commited to the model
					cancelEdit();
				} else {
					// if address is provided, it is commited to the model
					commitEdit(editIP.getValue());
				}
			// when ESC is pressed, editation is canceled	
			} else if (event.getCode() == KeyCode.ESCAPE) {
				cancelEdit();
			}
		});
		
	}
	
	// updating renderer or editor from data model
	public void updateItem(InetAddress item, boolean empty) {
		super.updateItem(item, empty);
		
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			// in editation state
			if (isEditing()) {
				if (editIP != null) {
					// address from the model is loaded to editor
					editIP.setValue(item);
				} 
				setText(null);
				setGraphic(editIP);
			} else {
				// otherwise current address from model is displayed 
				setText(item.getHostAddress());
				setGraphic(null);
			}
		}
	}	
}
