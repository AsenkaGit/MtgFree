package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of eventType is triggered when a Card is updated
 * 
 * @author asenka
 * @see Card
 */
public class BattlefieldEvent extends AbstractClientEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 3947866940496054884L;

	/**
	 * Build a BattlefieldEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public BattlefieldEvent(String eventType, String property, Serializable value) {

		super(eventType, property, value);
	}
}