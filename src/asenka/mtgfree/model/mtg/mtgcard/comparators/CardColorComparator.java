package asenka.mtgfree.model.mtg.mtgcard.comparators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgColor;

/**
 * Comparator used to sort a list of cards according to the colors. It works with weights associated to each color :
 * <ul>
 * <li>RED => 1</li>
 * <li>BLACK => 2</li>
 * <li>WHITE => 4</li>
 * <li>BLUE => 8</li>
 * <li>GREEN => 16</li>
 * </ul>
 * 
 * @author asenka
 * @see CardComparator
 */
public class CardColorComparator extends CardComparator {

	/**
	 * The map of integer weights associated to each color.
	 */
	private static final Map<MtgColor, Integer> COLORS_WEIGHTS = getColorWeigths();

	/**
	 * Constructor
	 */
	public CardColorComparator() {

		super(null);
	}

	/**
	 * Constructor with <code>optionalComparator</code>
	 * 
	 * @param optionalComparator the comparator used if the result of this compare is 0.
	 */
	public CardColorComparator(CardComparator optionalComparator) {

		super(optionalComparator);
	}

	/**
	 * 
	 * @param order ASCENDING or DESCENDING
	 * @see CardComparator
	 */
	public CardColorComparator(int order) {

		super(null, order);
	}

	/**
	 * 
	 * @param optionalComparator
	 * @param order ASCENDING or DESCENDING
	 */
	public CardColorComparator(CardComparator optionalComparator, int order) {

		super(optionalComparator, order);
	}

	/**
	 * 
	 */
	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {

		int result = super.orderResult(getColorsWeight(card1.getColors()) - getColorsWeight(card2.getColors()));

		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}

	/**
	 * Creates the sum of color weights from a set of colors
	 * 
	 * @param colors the set of colors
	 * @return 0 if colors == null or the sum of each color weigths in the colors set
	 */
	private static final int getColorsWeight(Set<MtgColor> colors) {

		if (colors == null) {
			return 0;
		} else {
			int result = 0;
			for (MtgColor color : colors) {
				result += COLORS_WEIGHTS.get(color);
			}
			return result;
		}
	}

	/**
	 * Creates the map with the weight associated to each colors.<br />
	 * <p>
	 * The value used for the weight works like the value used for the access rights
	 * in Linux system : 1, 2, 4, 8, 16. Using the a geometric serie like that is interesting
	 * because you can easily get combinaison of colors by additionning the weight together.
	 * </p>
	 * <p>
	 * For example, if a card as a weight of 3, it can only means that it is a RED (1) and BLACK (2) card.
	 * </p>
	 * 
	 * @return a map with the weight of each color
	 */
	private static final Map<MtgColor, Integer> getColorWeigths() {

		Map<MtgColor, Integer> colorWeights = new HashMap<>(5);
		colorWeights.put(MtgColor.RED, 1);
		colorWeights.put(MtgColor.BLACK, 2);
		colorWeights.put(MtgColor.WHITE, 4);
		colorWeights.put(MtgColor.BLUE, 8);
		colorWeights.put(MtgColor.GREEN, 16);
		return colorWeights;
	}
}
