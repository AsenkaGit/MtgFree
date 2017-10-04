package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This class represents an update event on a card related to its <strong>visible</strong> value. This event can be triggered only
 * if the card is on the context of a battlefield. Otherwise it does not make sense.
 * 
 * @author asenka
 *
 */
public class MtgCardVisibilityUpdatedEvent extends AbstractMtgCardUpdatedEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9104593069494194840L;
	
	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgCardVisibilityUpdatedEvent.class);

	/**
	 * Constructor
	 * 
	 * @param updatedCard
	 */
	public MtgCardVisibilityUpdatedEvent(final MtgCard updatedCard) {

		super(buildEventMessage(updatedCard), updatedCard);
		LOGGER.trace(super.eventMessage);
	}

	/**
	 * Create a event message adapted to this event type.
	 * 
	 * @param card the card updated
	 * @return <code>"The card &lt;name&gt;(&lt;id&gt;) has been updated with visibility set to: &lt;isVisible&gt;"</code>
	 */
	private static final String buildEventMessage(final MtgCard card) {

		return "The card " + card.getName() + "(" + card.getId() + ") has been updated with visibility set to: " + card.isVisible();
	}
}
