package asenka.mtgfree.model.mtg.events;

/**
 * 
 * 
 * 
 * @author asenka
 *
 */
public class AbstractEvent {

	/**
	 * 
	 */
	protected String eventMessage;

	/**
	 * 
	 * @param eventMessage
	 */
	public AbstractEvent(String eventMessage) {
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
