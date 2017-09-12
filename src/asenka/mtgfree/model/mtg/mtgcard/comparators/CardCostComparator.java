package asenka.mtgfree.model.mtg.mtgcard.comparators;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.utilities.ManaManager;

/**
 * Use this comparator to sort the card according to the CCM (mana cost)
 * 
 * @author asenka
 */
public final class CardCostComparator extends CardComparator {

	/**
	 * Manager used to manipulate the mana cost string of a card
	 */
	private static final ManaManager MANA_MANAGER = ManaManager.getInstance();

	/**
	 * Constructor
	 */
	public CardCostComparator() {

		super(null);
	}

	/**
	 * Constructor with optional comparator
	 * 
	 * @param optionalComparator
	 */
	public CardCostComparator(CardComparator optionalComparator) {

		super(optionalComparator);
	}

	/**
	 * Constructor
	 * 
	 * @param order
	 */
	public CardCostComparator(int order) {

		this(null, order);
	}

	/**
	 * Constructor with optional comparator
	 * 
	 * @param optionalComparator
	 * @param order
	 */
	public CardCostComparator(CardComparator optionalComparator, int order) {

		super(optionalComparator, order);
	}

	/**
	 * Compare two card accord to the value of the CCM
	 */
	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		// Get the CCM delta
		int result = super.orderResult(MANA_MANAGER.getConvertedCostMana(card1) - MANA_MANAGER.getConvertedCostMana(card2));

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
}
