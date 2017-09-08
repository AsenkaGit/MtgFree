package asenka.mtgfree.model.mtgcard.comparators;

import java.util.Comparator;

import asenka.mtgfree.model.mtgcard.MtgCard;
import asenka.mtgfree.model.utilities.ManaManager;


/**
 * Use this comparator to sort the card according to the CCM (mana cost)
 * 
 * @author asenka
 */
public class CardCostComparator implements Comparator<MtgCard> {

	@Override
	public int compare(MtgCard card1, MtgCard card2) {
		
		ManaManager manaManager = ManaManager.getInstance();
		return manaManager.getConvertedCostMana(card1) - manaManager.getConvertedCostMana(card2);
	}
}
