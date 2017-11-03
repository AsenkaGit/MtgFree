package asenka.mtgfree.events.local;

/**
 * This kind of eventType is triggered when a deck is updated
 * 
 * @author asenka
 * @see Deck
 */
public class DeckEvent extends AbstractLocalEvent {

	/**
	 * Build a DeckEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractLocalEvent#property
	 * @see AbstractLocalEvent#value
	 */
	public DeckEvent( String eventType, String property, Object value) {

		super(null, eventType, property, value);
	}

}
