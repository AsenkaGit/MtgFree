package asenka.mtgfree.model.mtgcard.comparators;


import java.util.Locale;

import asenka.mtgfree.model.mtgcard.MtgCard;

public final class CardNameComparator extends CardTextComparator {

	public CardNameComparator() {
		super();
	}

	public CardNameComparator(Locale locale) {
		super(locale);
	}

	public CardNameComparator(CardComparator optionalComparator) {
		super(optionalComparator);
	}

	public CardNameComparator(CardComparator optionalComparator, Locale locale) {
		super(optionalComparator, locale);
	}

	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		int result = this.collator.compare(card1.getName(), card2.getName());

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
}
