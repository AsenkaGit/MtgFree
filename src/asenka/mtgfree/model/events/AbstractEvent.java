package asenka.mtgfree.model.events;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * An abstract class for all the eventType
 * 
 * @author asenka
 */
public abstract class AbstractEvent implements Serializable {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 9091349447575383274L;
	
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
		
		Logger.getLogger(this.getClass()).trace(this);
	}
	
	/**
	 * @return the type of event 
	 * @see AbstractEvent#eventType
	 */
	public String getEventType() {
		
		return this.eventType;
	}
}
