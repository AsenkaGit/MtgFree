package asenka.mtgfree.model.mtgcard.comparators;

import org.apache.commons.lang3.math.NumberUtils;

import asenka.mtgfree.model.mtgcard.MtgCard;

/**
 * Comparator based on the card power (for creatures or vehicles)
 * 
 * @author asenka
 */
public class CardPowerComparator extends CardComparator {

	/**
	 * The maximum value of a power in an mtg card
	 */
	private static final int MAX_POWER_VALUE = 99;

	/**
	 * Default Constructor : no optional comparator and ASCENDING order
	 */
	public CardPowerComparator() {

		super(null);
	}

	/**
	 * Constructor : ASCENDING order
	 * @param optionalComparator
	 */
	public CardPowerComparator(CardComparator optionalComparator) {

		super(optionalComparator);
	}
	
	/**
	 * Constructor : no optional comparator
	 * @param order
	 */
	public CardPowerComparator(int order) {
		
		super(null, order);
	}
	
	/**
	 * Full Constructor 
	 * @param optionalComparator
	 * @param order
	 */
	public CardPowerComparator(CardComparator optionalComparator, int order) {
		
		super(optionalComparator, order);
	}

	/**
	 * Compare the cards according to their power stat
	 */
	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		final int power1 = getIntegerStatValue(card1.getPower());
		final int power2 = getIntegerStatValue(card2.getPower());

		int result = super.orderResult(power1 - power2);

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
	private static final int getIntegerStatValue(String power) {

		// First, it checks if the power value can be used to create a number
		// If it is possible, the number is created, otherwise we use the
		// max power value + 1 (= 100 since the strongest Mtg card has 99/99)

		if (NumberUtils.isCreatable(power)) {
			return NumberUtils.createInteger(power);
		} else if (power.isEmpty()) {
			return MAX_POWER_VALUE + 2; // In that case, it means the card is not a creature
		} else {
			return MAX_POWER_VALUE + 1; // In that case, it means the card has a power value variable (X, Y, Z or *)
		}
	}
}
