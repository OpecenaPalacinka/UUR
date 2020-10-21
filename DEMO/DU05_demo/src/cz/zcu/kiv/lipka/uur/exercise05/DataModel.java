package cz.zcu.kiv.lipka.uur.exercise05;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Date model for this example, encapsulates one integer and one string value. 
 * 
 * @author Richard
 *
 */
public class DataModel {
	private IntegerProperty integerData = new SimpleIntegerProperty();
	private StringProperty stringData = new SimpleStringProperty();
	
	private int max, min;
	
	// Class that allows to manage registered listeners and dispatch the notification 
	// to them
	// Used to distribute information about change of any attribute of this class 
	private final PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);
	
	// Constructor setting the initial values for the properties
	public DataModel(int initialInt, String initialString, int min, int max) {
		if (max <= min) {
			throw new IllegalArgumentException("Minimum has to be lower than maximum! You have provided min: " + min + ", max: " + max);
		}
		
		if ((initialInt < min) || (initialInt > max)) {
			throw new IllegalArgumentException("Initial data has to be between minimum and maximum! You have provided min: " + min + ", max: " + max + " and data: " + initialInt);
		}
		
		this.max = max;
		this.min = min;
		
		integerData.set(initialInt);
		setStringData(initialString);
		
		// overcomes problems with the exposition of the property - can be changed without calling
		// setter methods from this class
		// can be done in inc() and dec() methods, but then changes made directly on the property will
		// not lead to firing event
		stringData.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("Int value changed from " + oldValue + " to " + newValue);
				listenerManager.firePropertyChange("stringProperty", oldValue, newValue);
			}			
		});
		integerData.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("String value changed from " + oldValue + " to " + newValue);
				listenerManager.firePropertyChange("integerData", oldValue, newValue);
			}
		});		
	}
	
	// ----------------------------------------------------------------
	// Methods for the management of the listeners
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    	System.out.println("Registering " + listener);
        listenerManager.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listenerManager.removePropertyChangeListener(listener);
    }	

	// ----------------------------------------------------------------
	// methods for the incrementation and decrementation
	// Operation is performed only if the result is between expected maximum and minimum, otherwise
	// no change is done. 
	// After change, all the listeners are notified about the change due to listeners from constructor. 
	public int inc() {		
		if (integerData.get() < max) {
			System.out.println("Incrementing");
			integerData.set(integerData.get() + 1);
			return integerData.get();
		} else {
			System.out.println(integerData.get() + " >= " + max);
			return integerData.get();
		}
	}
	
	public int dec() {		
		if (integerData.get() > min) {
			System.out.println("Decrementing");
			integerData.set(integerData.get() - 1);
			return integerData.get();
		} else {
			return integerData.get();
		}
	}
	
	// obtain actual value
	public int getIntegerData() {
		return integerData.get();
	}
		
	public void setStringData(String newStringData) {
		stringData.set(newStringData);
	}

	public String getStringData() {
		return stringData.get();
	}

	// ----------------------------------------------------------------
	// Methods to gain access to the properties, in order to listen to their changes	
	public IntegerProperty integerDataProperty() {
		return integerData;
	}

	
	public StringProperty stringDataProperty() {
		return stringData;
	}

}
