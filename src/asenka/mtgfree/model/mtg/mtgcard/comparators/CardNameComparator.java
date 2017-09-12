package asenka.mtgfree.model.mtg.mtgcard.comparators;

import java.util.Locale;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Compare the card according to the name
 * 
 * @author asenka
 */
public final class CardNameComparator extends CardTextComparator {

	/**
	 * Default constructor : no optional comparator, user preferred locale and ASCEDNING order
	 */
	public CardNameComparator() {

		super();
	}

	/**
	 * Constructor : no optional comparator and ASCENDING order
	 * 
	 * @param locale the desired locale to build the collator
	 */
	public CardNameComparator(Locale locale) {

		super(locale);
	}

	/**
	 * Constructor : user preferred locale and ASCENDING order
	 * 
	 * @param optionalComparator the optional comparator
	 */
	public CardNameComparator(CardComparator optionalComparator) {

		super(optionalComparator);
	}

	/**
	 * Constructor : ASCENDING order
	 * 
	 * @param optionalComparator
	 * @param locale
	 */
	public CardNameComparator(CardComparator optionalComparator, Locale locale) {

		super(optionalComparator, locale);
	}
	
	/**
	 * Default constructor : no optional comparator, user preferred locale
	 */
	public CardNameComparator(int order) {
		
		super(order);
	}
	
	/**
	 * Constructor : no optional comparator
	 * 
	 * @param locale the desired locale to build the collator
	 * @param order
	 */
	public CardNameComparator(Locale locale, int order) {
		
		super(locale, order);
	}
	
	/**
	 * Constructor : user preferred locale
	 * 
	 * @param optionalComparator the optional comparator
	 * @param order
	 */
	public CardNameComparator(CardComparator optionalComparator, int order) {
		
		super(optionalComparator, order);
	}
	
	/**
	 * Full Constructor 
	 * 
	 * @param optionalComparator
	 * @param locale
	 * @param order
	 */
	public CardNameComparator(CardComparator optionalComparator, Locale locale, int order) {
		
		super(optionalComparator, locale, order);
	}

	/**
	 * Compare two cards according to their names
	 */
	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		int result = super.orderResult(this.collator.compare(card1.getName(), card2.getName()));

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
}
