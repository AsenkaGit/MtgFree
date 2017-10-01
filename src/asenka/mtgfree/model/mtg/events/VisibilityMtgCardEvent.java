package asenka.mtgfree.model.mtg.events;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

public class VisibilityMtgCardEvent extends AbstractMtgCardEvent {

	/**
	 * 
	 */
	private static final String VISIBLE_EVENT_MESSAGE = " is now visible";

	/**
	 * 
	 */
	private static final String HIDE_EVENT_MESSAGE = " is now hidden";

	/**
	 * 
	 * @param updatedCard
	 */
	public VisibilityMtgCardEvent(MtgCard updatedCard) {
		super(updatedCard.getName() + (updatedCard.isVisible() ? VISIBLE_EVENT_MESSAGE : HIDE_EVENT_MESSAGE), updatedCard);

	}
}
