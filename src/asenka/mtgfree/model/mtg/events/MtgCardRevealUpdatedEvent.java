package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * 
 * This class represents an update event on a card related to its <strong>revealed</strong> value. This events can only be
 * triggered for a card in the context of a player's hand. Otherwise this event does not make sense.
 * 
 * @author asenka
 *
 */
public class MtgCardRevealUpdatedEvent extends AbstractMtgCardUpdatedEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3797488545165903618L;

	/**
	 * Log4j logger use to trace events.
	 */
	private static final Logger LOGGER = Logger.getLogger(MtgCardRevealUpdatedEvent.class);

	/**
	 * Constructor
	 * 
	 * @param updatedCard
	 */
	public MtgCardRevealUpdatedEvent(final MtgCard updatedCard) {

		super(buildEventMessage(updatedCard), updatedCard);
		LOGGER.trace(super.eventMessage);
	}

	/**
	 * Create a event message adapted to this event type.
	 * 
	 * @param card the card updated
	 * @return <code>"The card &lt;name&gt;(&lt;id&gt;) has been updated with revealed set to: &lt;isRevealed&gt;"</code>
	 */
	private static final String buildEventMessage(final MtgCard card) {

		return "The card " + card.getName() + "(" + card.getId() + ") has been updated with revealed set to: " + card.isRevealed();
	}
}
