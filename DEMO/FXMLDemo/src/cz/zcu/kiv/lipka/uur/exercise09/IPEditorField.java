package cz.zcu.kiv.lipka.uur.exercise09;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.scene.control.TextField;

/**
 * Component that provides input of IP address from user
 * It provides control of validity of IP address, its value
 * is either valid address or null when no address is provided.
 * 
 *  When address is changed to an invalid one, old value (the
 *  last known valid address) is used. 
 *  
 *  Validity of IP address is checked when focus of this
 *  editor is lost - when user try to move to another
 *  element of the window
 *  
 *  This component is used also as a basic of the TableCell for 
 *  editation of IP address in the TableView
 * 
 * @author Richard Lipka
 *
 */
public class IPEditorField extends TextField {
	// last known valid IP adress
	private String oldValue;	
	
	// creates empty address field
	public IPEditorField() {
		setText("");
		setHandlers();
	}
	
	// creates address field from provided IP address
	// InetAddress is a basic representation of Internet addresses
	// in Java 
	public IPEditorField(InetAddress address) {
		if (address != null) {
			// address is converted to its string representation
			setText(address.getHostAddress());
		} else {
			// when no address is provided, empty text field is displayed 
			setText("");
		}
		setHandlers();
	}
	
	// setting new value from InetAddress
	public boolean setValue(InetAddress adress) {
		if (adress != null) {
			setText(adress.getHostAddress());
			return true;
		} else {
			return false;
		}
	}
	
	// obtaining InetAddress from String written to the text field
	public InetAddress getValue() {
		if (getText() == null) {
			return null;
		}
		// when no text is provided, null is returned
		// this allows to delete content of the field - when user deletes all
		// content, last known valid IP address is not used
		if (getText().trim().length() == 0) {
			return null;
		} else {
			try {				
				// if provided address is valid
				// this check is neccessary, when this value is valid
				// IP address, it is just converted to InetAddress 
				// by method getByName. When text is not valid address,
				// getByName tries to use DNS to resolve provided string
				// as domain name and thus slowing program significantly 
				if (validateString(getText())) {
					// appropriate instance of InetAdress is provided					 
					return InetAddress.getByName(getText());
				} else {
					// else return is null
					return null;
				}
			// when text cannot be converted to IP address for some other 
			// reason, null is also returned			
			} catch (UnknownHostException e) {				
				return null;
			}
		}
	}
	
	// setting handler for check of validity of IP address
	private void setHandlers() {	
		// listener is set on focusedProperty - it is invoked
		// whenever focus of this element is changed - when it
		// gains or lost focus
		focusedProperty().addListener(event -> {
			// if this element lost its focused
			if (!focusedProperty().get()) {
				// obtaining text provided by user 
				String newValue = getText();
				
				// when no text is provided, text field stays empty 
				if ((newValue == null) || (newValue.trim().length() == 0)) {
					setText("");
				} else 
				// validating input 	
				if (validateString(newValue)) {
					// when input is valid IP address, it is allowed to stay in text field 
					setText(newValue);
				} else {
					// when input is not valid IP address, last known valid IP address is loaded
					// and replaces input
					setText(oldValue);
				}
				// current address (or empty field) is stored
				oldValue = getText();
			} 
		});		
	}	
	
	// validation of IP address
	private boolean validateString(String value) {
		// validation is based on regular expression - note that expression contains 
		// four repeated subexpressions - ([01]?\\d\\d?|2[0-4]\\d|25[0-5]) divided
		// by \\. 
		return value.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"); 
	}
}
