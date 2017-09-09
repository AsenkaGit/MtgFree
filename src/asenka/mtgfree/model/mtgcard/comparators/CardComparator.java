package asenka.mtgfree.model.mtgcard.comparators;

import java.util.Comparator;

import asenka.mtgfree.model.mtgcard.MtgCard;

/**
 * 
 * @author asenka
 */
public abstract class CardComparator implements Comparator<MtgCard> {

	/**
	 * 
	 */
	protected CardComparator optionalComparator;

	/**
	 * 
	 * @param optionalComparator (null value authorized)
	 */
	public CardComparator(CardComparator optionalComparator) {
		super();

		if (optionalComparator != null && this.getClass().equals(optionalComparator.getClass())) {
			throw new IllegalArgumentException("The optional comparator is the same as the main comparator");
		} else {
			this.optionalComparator = optionalComparator;
		}
	}
}
