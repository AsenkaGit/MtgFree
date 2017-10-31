package asenka.mtgfree.model.events;

import java.io.Serializable;

/**
 * An abstract class for all the event
 * 
 * @author asenka
 */
public abstract class AbstractEvent implements Serializable {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 6912265769794571101L;
	
	/**
	 * The type of event: <code>"add", "remove", "set", "clear", "shuffle", ...</code>
	 */
	protected final String event;
	
	/**
	 * Initializes the abstract event with the event value
	 * @param event the type of event
	 */
	public AbstractEvent(String event) {

		this.event = event;
	}
}
