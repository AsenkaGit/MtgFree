package asenka.mtgfree.model.mtg.events;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * @author asenka
 *
 */
public class RevealedMtgCardEvent extends AbstractMtgCardEvent {

	/**
	 * @param updatedCard
	 */
	public RevealedMtgCardEvent(MtgCard updatedCard) {
		super("Card revealed or hidden from hand", updatedCard);
	}

}
