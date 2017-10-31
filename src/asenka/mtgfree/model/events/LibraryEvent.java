package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of event is triggered when a Library is updated
 * 
 * @author asenka
 * @see Library
 */
public class LibraryEvent extends AbstractClientEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -3725354157603997948L;

	/**
	 * Build a LibraryEvent 
	 * @param event the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractClientEvent#event
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public LibraryEvent(String event, String property, Serializable value) {
		super(event, property, value);
	}

}
