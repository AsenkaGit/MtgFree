package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * This kind of event is triggered when a Card is updated
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
	 * @param event the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#event
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public BattlefieldEvent(String event, String property, Serializable value) {

		super(event, property, value);
	}
}