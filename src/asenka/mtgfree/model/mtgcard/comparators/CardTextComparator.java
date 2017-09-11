package asenka.mtgfree.model.mtgcard.comparators;

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
	protected Collator collator;

	/**
	 * Constructor. Create a default text comparator with the locale set in the used preferences
	 */
	public CardTextComparator() {

		this(null, getPreferedLocale());
	}

	/**
	 * 
	 * @param locale
	 */
	public CardTextComparator(Locale locale) {

		this(null, locale);
	}

	/**
	 * @param optionalComparator
	 */
	public CardTextComparator(CardComparator optionalComparator) {

		this(optionalComparator, getPreferedLocale());
	}

	/**
	 * @param optionalComparator
	 * @param locale
	 */
	public CardTextComparator(CardComparator optionalComparator, Locale locale) {

		super(optionalComparator);
		this.collator = Collator.getInstance(locale);
	}

	/**
	 * 
	 * @param order
	 */
	public CardTextComparator(int order) {

		this(null, getPreferedLocale(), order);
	}

	/**
	 * 
	 * @param locale
	 * @param order
	 */
	public CardTextComparator(Locale locale, int order) {

		this(null, locale, order);
	}

	/**
	 * Use the prefered locale to build the text comparator
	 * @param optionalComparator
	 * @param order
	 */
	public CardTextComparator(CardComparator optionalComparator, int order) {

		this(optionalComparator, getPreferedLocale(), order);
	}

	/**
	 * Full constructor with optional comparator, locale and order defined
	 * @param optionalComparator
	 * @param locale
	 * @param order
	 */
	public CardTextComparator(CardComparator optionalComparator, Locale locale, int order) {

		super(optionalComparator, order);
		this.collator = Collator.getInstance(locale);
	}

	/**
	 * 
	 * @return
	 */
	private static final Locale getPreferedLocale() {

		return Locale.FRENCH;
		// TODO Utiliser un fichier de configuration pour déterminer automatiquement la locale
		// à utiliser ici. En attendant je mets le Français (plus simple pour les tests)
	}
}
