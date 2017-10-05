package asenka.mtgfree.model.mtg.mtgcard.comparators;

import java.util.Locale;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Comparator used to sort the card according to the collection name
 * 
 * @author asenka
 *
 */
public final class CardCollectionComparator extends CardTextComparator {

	/**
	 * Default constructor
	 */
	public CardCollectionComparator() {

		super();
	}

	/**
	 * 
	 * @param locale
	 */
	public CardCollectionComparator(Locale locale) {

		super(locale);
	}

	/**
	 * 
	 * @param optionalComparator
	 */
	public CardCollectionComparator(CardComparator optionalComparator) {

		super(optionalComparator);
	}

	/**
	 * 
	 * @param optionalComparator
	 * @param locale
	 */
	public CardCollectionComparator(CardComparator optionalComparator, Locale locale) {

		super(optionalComparator, locale);
	}

	/**
	 * 
	 * @param order
	 */
	public CardCollectionComparator(int order) {

		super((CardComparator) null, order);
	}

	/**
	 * 
	 * @param locale
	 * @param order
	 */
	public CardCollectionComparator(Locale locale, int order) {

		super(locale, order);
	}

	/**
	 * 
	 * @param optionalComparator
	 * @param order
	 */
	public CardCollectionComparator(CardComparator optionalComparator, int order) {

		super(optionalComparator, order);
	}

	/**
	 * 
	 * @param optionalComparator
	 * @param locale
	 * @param order
	 */
	public CardCollectionComparator(CardComparator optionalComparator, Locale locale, int order) {

		super(optionalComparator, locale, order);
	}

	/**
	 * 
	 */
	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		int result = super.orderResult(super.collator.compare(card1.getCollectionName(), card2.getCollectionName()));

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
}
