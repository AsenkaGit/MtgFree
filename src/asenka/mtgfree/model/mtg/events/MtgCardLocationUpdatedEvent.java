package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This class represents an update event on a card related to its <strong>location</strong> value. It is triggered each time a
 * card on a battlefield is moved. On other context, this event does not make sense.
 * 
 * @author asenka
 */
public class MtgCardLocationUpdatedEvent extends AbstractMtgCardUpdatedEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2525052048324109222L;

	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgCardLocationUpdatedEvent.class);

	/**
	 * Constructor
	 * 
	 * @param updatedCard
	 */
	public MtgCardLocationUpdatedEvent(final MtgCard updatedCard) {

		super(buildEventMessage(updatedCard), updatedCard);
		LOGGER.trace(super.eventMessage);
	}

	/**
	 * Create a event message adapted to this event type.
	 * 
	 * @param card the card updated
	 * @return <code>"The card &lt;name&gt;(&lt;id&gt;) has been moved to: &lt;new_location&gt;"</code>
	 */
	private static final String buildEventMessage(final MtgCard card) {

		return "The card " + card.getName() + "(" + card.getId() + ") has been moved to: " + card.getLocation();
	}
}
