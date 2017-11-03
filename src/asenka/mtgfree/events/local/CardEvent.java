package asenka.mtgfree.events.local;

/**
 * This kind of eventType is triggered when a Card is updated
 * 
 * @author asenka
 * @see Card
 */
public class CardEvent extends AbstractLocalEvent {

	/**
	 * Build a CardEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractLocalEvent#property
	 * @see AbstractLocalEvent#value
	 */
	public CardEvent(String eventType, String property, Object value) {

		super(eventType, property, value);
	}
}
