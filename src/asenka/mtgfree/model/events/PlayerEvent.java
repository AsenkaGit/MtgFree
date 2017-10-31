package asenka.mtgfree.model.events;

/**
 * This kind of eventType is triggered when a Library is updated
 * 
 * @author asenka
 * @see Player
 */
public class PlayerEvent extends AbstractClientEvent {

	/**
	 * Build a LibraryEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public PlayerEvent(String eventType, String property, Object value) {
		super(eventType, property, value);
	}

}
