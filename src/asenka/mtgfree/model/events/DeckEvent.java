package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of eventType is triggered when a deck is updated
 * 
 * @author asenka
 * @see Deck
 */
public class DeckEvent extends AbstractClientEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -4679013335240247037L;

	/**
	 * Build a DeckEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public DeckEvent(String eventType, String property, Serializable value) {

		super(eventType, property, value);
	}

}
