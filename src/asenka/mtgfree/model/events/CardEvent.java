package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of event is triggered when a Card is updated
 * 
 * @author asenka
 * @see Card
 */
public class CardEvent extends AbstractClientEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -1325290049137592105L;

	/**
	 * Build a CardEvent 
	 * @param event the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractClientEvent#event
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public CardEvent(String event, String property, Serializable value) {

		super(event, property, value);
	}
}
