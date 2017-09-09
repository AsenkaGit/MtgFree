package asenka.mtgfree.model.mtgcard.comparators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import asenka.mtgfree.model.mtgcard.MtgCard;
import asenka.mtgfree.model.mtgcard.MtgColor;

public class CardColorComparator extends CardComparator {

	private static final Map<MtgColor, Integer> COLORS_WEIGHTS = getColorWeigths();

	public CardColorComparator() {
		super(null);
	}

	public CardColorComparator(CardComparator optionalComparator) {
		super(optionalComparator);
	}

	@Override
	public int compare(final MtgCard card1, final MtgCard card2) {


		int result = getColorsWeight(card1.getColors()) - getColorsWeight(card2.getColors());
		
		if (super.optionalComparator != null && result == 0) {
			result = super.optionalComparator.compare(card1, card2);
		}
		return result;
	}
	
	private int getColorsWeight(Set<MtgColor> colors) {
		
		if(colors == null) {
			return 0;
		} else {
			int result = 0;
			for(MtgColor color : colors) {
				result += COLORS_WEIGHTS.get(color);
			}
			return result;
		}
	}

	/**
	 * 
	 * @return
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
