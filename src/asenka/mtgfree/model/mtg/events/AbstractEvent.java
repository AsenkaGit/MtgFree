package asenka.mtgfree.model.mtg.events;

import java.io.Serializable;

/**
 * Mother class of all the events happening on the MtgFree application
 * 
 * @author asenka
 */
public abstract class AbstractEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -506793224243983347L;
	
	/**
	 * The message associated to the event.
	 */
	protected final String eventMessage;

	/**
	 * Build a new event with an empty message
	 */
	public AbstractEvent() {

		super();
		this.eventMessage = "";
	}

	/**
	 * Build a new event with its message
	 * 
	 * @param eventMessage
	 */
	public AbstractEvent(final String eventMessage) {

		super();
		this.eventMessage = eventMessage;
	}

	/**
	 * @return
	 */
	public String getEventMessage() {

		return eventMessage;
	}
}
