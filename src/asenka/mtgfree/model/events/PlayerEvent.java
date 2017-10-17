package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of event is triggered when a Library is updated
 * 
 * @author asenka
 * @see Player
 */
public class PlayerEvent extends AbstractEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -1660693441711974073L;

	/**
	 * Build a LibraryEvent 
	 * @param event the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#event
	 * @see AbstractEvent#property
	 * @see AbstractEvent#value
	 */
	public PlayerEvent(String event, String property, Serializable value) {
		super(event, property, value);
	}

}
