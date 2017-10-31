package asenka.mtgfree.model.events;

/**
 * An abstract class for all the eventType
 * 
 * @author asenka
 */
public abstract class AbstractEvent {

	/**
	 * The type of eventType: <code>"add", "remove", "set", "clear", "shuffle", ...</code>
	 */
	protected final String eventType;
	
	/**
	 * Initializes the abstract eventType with the eventType value
	 * @param eventType the type of event
	 */
	public AbstractEvent(String eventType) {

		this.eventType = eventType;
	}
	
	/**
	 * @return the type of event 
	 * @see AbstractEvent#eventType
	 */
	public String getEventType() {
		
		return this.eventType;
	}
}
