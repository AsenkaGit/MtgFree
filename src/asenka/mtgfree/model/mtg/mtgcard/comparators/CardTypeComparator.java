package asenka.mtgfree.model.mtg.mtgcard.comparators;

import java.util.Locale;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Use this comparator to sort the card by type
 * 
 * @author asenka
 *
 */
public final class CardTypeComparator extends CardTextComparator {

	/**
	 * 
	 */
	public CardTypeComparator() {
		super();
	}

	/**
	 * @param locale
	 */
	public CardTypeComparator(Locale locale) {
		super(locale);
	}

	/**
	 * @param optionalComparator
	 */
	public CardTypeComparator(CardComparator optionalComparator) {
		super(optionalComparator);
	}

	/**
	 * @param optionalComparator
	 * @param locale
	 */
	public CardTypeComparator(CardComparator optionalComparator, Locale locale) {
		super(optionalComparator, locale);
	}
	
	/**
	 * Default constructor : no optional comparator, user preferred locale
	 */
	public CardTypeComparator(int order) {
		
		super(order);
	}
	
	/**
	 * Constructor : no optional comparator
	 * 
	 * @param locale the desired locale to build the collator
	 * @param order
	 */
	public CardTypeComparator(Locale locale, int order) {
		
		super(locale, order);
	}
	
	/**
	 * Constructor : user preferred locale
	 * 
	 * @param optionalComparator the optional comparator
	 * @param order
	 */
	public CardTypeComparator(CardComparator optionalComparator, int order) {
		
		super(optionalComparator, order);
	}
	
	/**
	 * Full Constructor 
	 * 
	 * @param optionalComparator
	 * @param locale
	 * @param order
	 */
	public CardTypeComparator(CardComparator optionalComparator, Locale locale, int order) {
		
		super(optionalComparator, locale, order);
	}

	/**
	 * Compare the cards according to their type
	 */
	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		int result = super.orderResult(card1.getType().compareTo(card2.getType()));

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
}
