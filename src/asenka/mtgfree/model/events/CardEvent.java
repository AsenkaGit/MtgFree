package asenka.mtgfree.model.events;

/**
 * This kind of eventType is triggered when a Card is updated
 * 
 * @author asenka
 * @see Card
 */
public class CardEvent extends AbstractClientEvent {

	/**
	 * Build a CardEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	public CardEvent(String eventType, String property, Object value) {

		super(eventType, property, value);
	}
}
