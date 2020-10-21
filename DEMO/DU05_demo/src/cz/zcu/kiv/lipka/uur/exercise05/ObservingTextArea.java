package cz.zcu.kiv.lipka.uur.exercise05;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.scene.control.TextArea;

public class ObservingTextArea extends TextArea implements PropertyChangeListener{
	@Override
	public void propertyChange(PropertyChangeEvent evt) {		
		appendText("Property " + evt.getPropertyName() + " changed from " + evt.getOldValue() + " to " + evt.getNewValue() + "\n");
	}
}
