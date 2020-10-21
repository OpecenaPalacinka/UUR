package cz.zcu.kiv.lipka.uur.exercise07;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	// name of the file with text resources - name of country and language is
	// added automatically when ResourceBundle is loaded according to given
	// locale
	public static final String TEXT_FILE = "LANG/messages";
	private ResourceBundle texts;

	// constructor loads text from file
	public Messages(Locale locale) {
		texts = ResourceBundle.getBundle(TEXT_FILE, locale);		
	}

	// getter provides texts according to given key
	// wraps getString() method
	// when no text to given key is found, the key itself is returned - this
	// allows application to work even when file with texts is corrupted or missing
	public String getString(String key) {
		try {
			return texts.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
