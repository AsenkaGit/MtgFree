package asenka.mtgfree.model.mtg.mtgcard.comparators;

import java.text.Collator;
import java.util.Locale;

/**
 * Abstract comparator working with text value. The comparator provides a localized collator to sort properly every text value
 * (string) according the each language Specificities
 * 
 * @author asenka
 *
 */
public abstract class CardTextComparator extends CardComparator {

	/**
	 * The localized collator use to compare two strings
	 */
	protected final Collator collator;

	/**
	 * Constructor. Create a default text comparator with the locale set in the used preferences
	 */
	protected CardTextComparator() {

		this(null, getPreferedLocale());
	}

	/**
	 * Constructor with locale
	 * @param locale 
	 */
	protected CardTextComparator(Locale locale) {

		this(null, locale);
	}

	/**
	 * Constructor with sub comparator
	 * @param optionalComparator 
	 */
	protected CardTextComparator(CardComparator optionalComparator) {

		this(optionalComparator, getPreferedLocale());
	}

	/**
	 * Constructor with sub comparator and locale
	 * @param optionalComparator
	 * @param locale
	 */
	protected CardTextComparator(CardComparator optionalComparator, Locale locale) {

		super(optionalComparator);
		this.collator = Collator.getInstance(locale);
	}

	/**
	 * 
	 * @param order
	 */
	protected CardTextComparator(int order) {

		this(null, getPreferedLocale(), order);
	}

	/**
	 * 
	 * @param locale
	 * @param order
	 */
	protected CardTextComparator(Locale locale, int order) {

		this(null, locale, order);
	}

	/**
	 * Use the prefered locale to build the text comparator
	 * @param optionalComparator
	 * @param order
	 */
	protected CardTextComparator(CardComparator optionalComparator, int order) {

		this(optionalComparator, getPreferedLocale(), order);
	}

	/**
	 * Full constructor with optional comparator, locale and order defined
	 * @param optionalComparator
	 * @param locale
	 * @param order
	 */
	protected CardTextComparator(CardComparator optionalComparator, Locale locale, int order) {

		super(optionalComparator, order);
		this.collator = Collator.getInstance(locale);
	}

	/**
	 * For the moment this method returns the default locale. It would
	 * be nice to use a configuration file instead
	 * @return the default locale
	 * @see Locale#getDefault()
	 */
	private static final Locale getPreferedLocale() {

		return Locale.getDefault();
		// TODO Utiliser un fichier de configuration pour déterminer automatiquement la locale
		// à utiliser ici. En attendant je mets le Français (plus simple pour les tests)
	}
}
