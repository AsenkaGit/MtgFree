package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of event is triggered when a deck is updated
 * 
 * @author asenka
 * @see Deck
 */
public class DeckEvent extends AbstractEvent {

	/**
	 * The generated id for serialization
	 */
	private static final long serialVersionUID = -1325290049137592105L;

	/**
	 * Build a DeckEvent 
	 * @param event the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#event
	 * @see AbstractEvent#property
	 * @see AbstractEvent#value
	 */
	public DeckEvent(String event, String property, Serializable value) {

		super(event, property, value);
	}

}
