package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This class represents an update event on a card related to its <strong>context</strong> value.
 * 
 * @author asenka
 * @see MtgCard
 */
public final class MtgCardContextUpdatedEvent extends AbstractMtgCardUpdatedEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 191676314612213066L;

	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgCardContextUpdatedEvent.class);

	/**
	 * Constructor
	 * 
	 * @param updatedCard
	 */
	public MtgCardContextUpdatedEvent(final MtgCard updatedCard) {

		super(buildEventMessage(updatedCard), updatedCard);
		LOGGER.trace(super.eventMessage);
	}

	/**
	 * Create a event message adapted to this event type.
	 * 
	 * @param card the card updated
	 * @return <code>"The card &lt;name&gt;(&lt;id&gt;) has been updated with a new context: &lt;new_context&gt;"</code>
	 */
	private static final String buildEventMessage(final MtgCard card) {

		return "The card " + card.getName() + "(" + card.getId() + ") has been updated with a new context: " + card.getContext();
	}

	// /**
	// * All the related events happening when a card changes context. The set
	// * of related events depends on the new context
	// */
	// private Set<AbstractMtgCardUpdatedEvent> relatedEvents;
	//
	// /**
	// * Add a new related event to this event
	// * @param event a related event
	// */
	// public void add(AbstractMtgCardUpdatedEvent event) {
	// this.relatedEvents.add(event);
	// }
	//
	// /**
	// * @return an unmodifiable list of related events
	// */
	// public Set<AbstractMtgCardUpdatedEvent> getRelatedEvents() {
	// return Collections.unmodifiableSet(this.relatedEvents);
	// }
}
