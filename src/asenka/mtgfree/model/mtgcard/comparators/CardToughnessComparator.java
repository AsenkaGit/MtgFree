package asenka.mtgfree.model.mtgcard.comparators;

import org.apache.commons.lang3.math.NumberUtils;

import asenka.mtgfree.model.mtgcard.MtgCard;

/**
 * Comparator based on the card toughness (for creatures or vehicles)
 * 
 * @author asenka
 */
public class CardToughnessComparator extends CardComparator {

	private static final int MAX_TOUGHNESS_VALUE = 99;

	/**
	 * Constructor
	 */
	public CardToughnessComparator() {

		super(null);
	}

	/**
	 * 
	 * @param optionalComparator
	 */
	public CardToughnessComparator(CardComparator optionalComparator) {

		super(optionalComparator);
	}
	
	
	/**
	 * Constructor : no optional comparator
	 * @param order
	 */
	public CardToughnessComparator(int order) {
		
		super(null, order);
	}
	
	/**
	 * Full Constructor 
	 * @param optionalComparator
	 * @param order
	 */
	public CardToughnessComparator(CardComparator optionalComparator, int order) {
		
		super(optionalComparator, order);
	}

	/**
	 * Compare the cards according to their toughness stat
	 */
	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		final int toughness1 = getIntegerStatValue(card1.getToughness());
		final int toughness2 = getIntegerStatValue(card2.getToughness());

		int result = super.orderResult(toughness1 - toughness2);

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
	

	/**
	 * Calculate the integer value from a string for the power stat of a card
	 * @param power a string representing the power of a card. 
	 * @return an integer. 
	 */
	// This method has default access modifier (package) to make it usable with JUnit test
	static final int getIntegerStatValue(String power) {

		// First, it checks if the power value can be used to create a number
		// If it is possible, the number is created, otherwise we use the
		// max power value + 1 (= 100 since the strongest Mtg card has 99/99)

		if (NumberUtils.isCreatable(power)) {
			return NumberUtils.createInteger(power);
		} else if (power.isEmpty()) {
			return MAX_TOUGHNESS_VALUE + 2; // In that case, it means the card is not a creature
		} else {
			return MAX_TOUGHNESS_VALUE + 1; // In that case, it means the card has a power value variable (X, Y, Z or *)
		}
	}
}
