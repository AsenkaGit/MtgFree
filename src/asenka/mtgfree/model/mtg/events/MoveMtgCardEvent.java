package asenka.mtgfree.model.mtg.events;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * @author asenka
 *
 */
public class MoveMtgCardEvent extends AbstractMtgCardEvent {

	/**
	 * @param updatedCard
	 */
	public MoveMtgCardEvent(MtgCard updatedCard) {
		super("Card moved", updatedCard);
	}

}
