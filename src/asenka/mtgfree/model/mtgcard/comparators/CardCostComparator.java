package asenka.mtgfree.model.mtgcard.comparators;

import asenka.mtgfree.model.mtgcard.MtgCard;
import asenka.mtgfree.model.utilities.ManaManager;

/**
 * Use this comparator to sort the card according to the CCM (mana cost)
 * 
 * @author asenka
 */
public final class CardCostComparator extends CardComparator {
	
	private static final ManaManager MANA_MANAGER = ManaManager.getInstance();
	
	public CardCostComparator() {
		super(null);
	}
	
	public CardCostComparator(CardComparator optionalComparator) {
		super(optionalComparator);
	}

	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		int result = MANA_MANAGER.getConvertedCostMana(card1) - MANA_MANAGER.getConvertedCostMana(card2);
		
		if(super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}		
		return result;
	}
}
