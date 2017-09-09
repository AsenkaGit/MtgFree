package asenka.mtgfree.model.mtgcard.comparators;

import java.util.Locale;

import asenka.mtgfree.model.mtgcard.MtgCard;

public final class CardCollectionComparator extends CardTextComparator {

	public CardCollectionComparator() {
		super();
	}

	public CardCollectionComparator(Locale locale) {
		super(locale);
	}

	public CardCollectionComparator(CardComparator optionalComparator) {
		super(optionalComparator);
	}

	public CardCollectionComparator(CardComparator optionalComparator, Locale locale) {
		super(optionalComparator, locale);
	}

	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		int result = this.collator.compare(card1.getCollectionName(), card2.getCollectionName());

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
}
