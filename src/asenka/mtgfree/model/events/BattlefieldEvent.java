package asenka.mtgfree.model.events;

/**
 * This kind of eventType is triggered when a Card is updated
 * 
 * @author asenka
 * @see Card
 */
public class BattlefieldEvent extends AbstractClientEvent {

	/**
	 * Build a BattlefieldEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public BattlefieldEvent(String eventType, String property, Object value) {

		super(eventType, property, value);
	}
}