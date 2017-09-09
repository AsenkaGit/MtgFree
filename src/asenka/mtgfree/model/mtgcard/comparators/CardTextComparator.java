package asenka.mtgfree.model.mtgcard.comparators;

import java.text.Collator;
import java.util.Locale;

public abstract class CardTextComparator extends CardComparator {

	/**
	 * 
	 */
	protected Collator collator;

	/**
	 * 
	 */
	public CardTextComparator() {
		this(null, getPreferedLocale());
	}

	/**
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
	 * @return
	 */
	private static final Locale getPreferedLocale() {

		return Locale.FRENCH;
		// TODO Utiliser un fichier de configuration pour déterminer automatiquement la locale
		// à utiliser ici. En attendant je mets le Français (plus simple pour les tests)
	}
}
