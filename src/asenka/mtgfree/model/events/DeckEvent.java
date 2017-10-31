package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of event is triggered when a deck is updated
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
	 * @param event the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractClientEvent#event
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public DeckEvent(String event, String property, Serializable value) {

		super(event, property, value);
	}

}
