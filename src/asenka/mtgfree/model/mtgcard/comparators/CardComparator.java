package asenka.mtgfree.model.mtgcard.comparators;

import java.util.Comparator;

import asenka.mtgfree.model.mtgcard.MtgCard;

/**
 * Abstract class used to compare MtgCards together. From it, you can define any comparison behavior you want : compare on the
 * name, the cost, the color, the type, etc...
 * 
 * @author asenka
 * @see Comparator
 * @see MtgCard
 */
public abstract class CardComparator implements Comparator<MtgCard> {

	/**
	 * Use this to sort from the lowest to the highest (default)
	 */
	public static final int ASCENDING = 1;

	/**
	 * Use this to sort from the highest to the lowest
	 */
	public static final int DESCENDING = -1;

	/**
	 * The order to classify in a list. Use the constants from CardComparator : ASCENDING or DESCENDING
	 */
	protected int order;

	/**
	 * The optional comparator used when the primary comparison returns 0. It allows you to have a several criteria of sorting
	 * (first on the color, then on the name, then on the descending cost for example)
	 */
	protected CardComparator optionalComparator;

	/**
	 * Build a default comparator without any optional comparator and ordered with ASCENDING
	 */
	protected CardComparator() {

		this(null);
	}

	/**
	 * Build a comparator with another optional comparator ordered with ASCENDING
	 * 
	 * @param optionalComparator (null value authorized)
	 */
	protected CardComparator(CardComparator optionalComparator) {

		// By default the order is ASCENDING (= 1)
		this(optionalComparator, ASCENDING);
	}

	/**
	 * Build a comparator with optional comparator and a defined order.
	 * 
	 * @param optionalComparator the optional comparator used when the primary comparison's result is 0
	 * @param order ASCENDING or DESCENDING
	 */
	protected CardComparator(CardComparator optionalComparator, int order) {

		// If the optional comparator is not null, then it should not be the same as
		// the primary comparator (represented by the keyword 'this')
		if (optionalComparator != null && this.getClass().equals(optionalComparator.getClass())) {
			throw new IllegalArgumentException("The optional comparator is the same as the primary comparator");
		} else {
			this.optionalComparator = optionalComparator;
			this.order = order;
		}
	}

	/**
	 * Manage the ordering of the comparison
	 * @param result
	 * @return if <code>order >= 0</code>, returns the same result, else returns the negation of the result
	 */
	protected int orderResult(int result) {

		return this.order >= 0 ? result : -result;
	}
}
