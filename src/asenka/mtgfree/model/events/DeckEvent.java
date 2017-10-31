package asenka.mtgfree.model.events;

/**
 * This kind of eventType is triggered when a deck is updated
 * 
 * @author asenka
 * @see Deck
 */
public class DeckEvent extends AbstractClientEvent {

	/**
	 * Build a DeckEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public DeckEvent(String eventType, String property, Object value) {

		super(eventType, property, value);
	}

}
