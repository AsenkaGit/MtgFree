package asenka.mtgfree.model.mtgcard.comparators;

import java.util.Locale;

import asenka.mtgfree.model.mtgcard.MtgCard;

/**
 * Use this comparator to sort the card by type
 * 
 * @author asenka
 *
 */
public final class CardTypeComparator extends CardTextComparator {

	public CardTypeComparator() {
		super();
	}

	public CardTypeComparator(Locale locale) {
		super(locale);
	}

	public CardTypeComparator(CardComparator optionalComparator) {
		super(optionalComparator);
	}

	public CardTypeComparator(CardComparator optionalComparator, Locale locale) {
		super(optionalComparator, locale);
	}

	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		int result = card1.getType().compareTo(card2.getType());

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
}
