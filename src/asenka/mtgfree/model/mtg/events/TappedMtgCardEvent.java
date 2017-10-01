package asenka.mtgfree.model.mtg.events;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * @author asenka
 *
 */
public class TappedMtgCardEvent extends AbstractMtgCardEvent {
	
	/**
	 * 
	 */
	private static final String TAP_EVENT_MESSAGE = " has been tapped";
	
	/**
	 * 
	 */
	private static final String UNTAP_EVENT_MESSAGE = " has been untapped";

	/**
	 * @param updatedCard
	 */
	public TappedMtgCardEvent(MtgCard updatedCard) {
		super(updatedCard.getName() + (updatedCard.isTapped() ? TAP_EVENT_MESSAGE : UNTAP_EVENT_MESSAGE), updatedCard);
	}
}
