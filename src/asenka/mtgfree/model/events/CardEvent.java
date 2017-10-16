package asenka.mtgfree.model.events;

import java.io.Serializable;

public class CardEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1325290049137592105L;

	/**
	 * 
	 * @param event
	 * @param property
	 * @param value
	 */
	public CardEvent(String event, String property, Serializable value) {

		super(event, property, value);
	}

}
