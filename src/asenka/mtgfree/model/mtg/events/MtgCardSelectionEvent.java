package asenka.mtgfree.model.mtg.events;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * @author asenka
 *
 */
public class MtgCardSelectionEvent extends AbstractMtgCardEvent {

	/**
	 * @param updatedCard
	 */
	public MtgCardSelectionEvent(MtgCard updatedCard) {
		super("Card selected", updatedCard);
	}

}
