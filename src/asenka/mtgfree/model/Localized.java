package asenka.mtgfree.model;

import java.util.Locale;

/**
 * All classes that needs to have localized content
 * should implements this interface
 * 
 * @author asenka
 */
public interface Localized {

	/**
	 * Returns the locale used on the instance
	 * @return a locale
	 */
	public Locale getLocale();
}
