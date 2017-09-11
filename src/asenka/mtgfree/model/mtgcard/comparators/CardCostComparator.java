package asenka.mtgfree.model.mtgcard.comparators;

import asenka.mtgfree.model.mtgcard.MtgCard;
import asenka.mtgfree.model.utilities.ManaManager;

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
	 * @param optionalComparator
	 */
	public CardCostComparator(CardComparator optionalComparator) {
		super(optionalComparator);
	}

	/**
	 * Compare two card accord to the value of the CCM
	 */
	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		// Get the CCM delta
		int result = MANA_MANAGER.getConvertedCostMana(card1) - MANA_MANAGER.getConvertedCostMana(card2);
		
		if(super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}		
		return result;
	}
}
