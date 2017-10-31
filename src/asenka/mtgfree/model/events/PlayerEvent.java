package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of eventType is triggered when a Library is updated
 * 
 * @author asenka
 * @see Player
 */
public class PlayerEvent extends AbstractClientEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -1660693441711974073L;

	/**
	 * Build a LibraryEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public PlayerEvent(String eventType, String property, Serializable value) {
		super(eventType, property, value);
	}

}
