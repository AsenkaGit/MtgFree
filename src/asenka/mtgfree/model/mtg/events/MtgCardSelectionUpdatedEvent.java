package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This class represents an update event on a card related to its <strong>selected</strong> value.
 * 
 * @author asenka
 *
 */
public class MtgCardSelectionUpdatedEvent extends AbstractMtgCardUpdatedEvent {

	/**
	 * Log4j logger use to trace events.
	 */
	private static final Logger LOGGER = Logger.getLogger(MtgCardSelectionUpdatedEvent.class);

	/**
	 * Constructor
	 * 
	 * @param updatedCard
	 */
	public MtgCardSelectionUpdatedEvent(final MtgCard updatedCard) {

		super(buildEventMessage(updatedCard), updatedCard);
		LOGGER.trace(super.eventMessage);
	}

	/**
	 * Create a event message adapted to this event type.
	 * 
	 * @param card the card updated
	 * @return <code>"The card &lt;name&gt;(&lt;id&gt;) has been updated with selected set to: &lt;isRevealed&gt;"</code>
	 */
	private static final String buildEventMessage(final MtgCard card) {

		return "The card " + card.getName() + "(" + card.getId() + ") has been updated with selected set to: " + card.isSelected();
	}
}
