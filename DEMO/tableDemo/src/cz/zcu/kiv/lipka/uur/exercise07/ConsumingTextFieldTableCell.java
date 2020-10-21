package cz.zcu.kiv.lipka.uur.exercise07;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

// specialized TextFieldTableCell that consumes DELETE events
public class ConsumingTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {
		public ConsumingTextFieldTableCell(final StringConverter<T> converter) {
			// invokes standard constructor of the TextFieldTableCell
			super(converter);
			
			// adds new functionality - pressing DELETE key will not be propagated to 
			// parent components
			setOnKeyReleased(event -> {
				// selecting which events should be consumed
				if (event.getCode() == KeyCode.DELETE) {
					event.consume();
				}
			});
		}
	
	public ConsumingTextFieldTableCell() {
		this((StringConverter<T>) new DefaultStringConverter());
	}
	
	// factories have to be overiden in order to return our new type and not the
	// standard TextFieldTableCell 
	public static <S> Callback<TableColumn<S,String>, TableCell<S,String>> forTableColumn() {
        return forTableColumn(new DefaultStringConverter());
    }
		
	public static <S,T> Callback<TableColumn<S,T>, TableCell<S,T>> forTableColumn(
			final StringConverter<T> converter) {
        return column -> new ConsumingTextFieldTableCell<S,T>(converter);
    }
}
