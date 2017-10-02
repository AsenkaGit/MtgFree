package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;
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
	
	private static final Logger LOGGER = Logger.getLogger(AbstractEvent.class);

	/**
	 * 
	 * @param eventMessage
	 */
	public AbstractEvent(String eventMessage) {
		super();
		this.eventMessage = eventMessage;
		
		LOGGER.info(this);
	}

	/**
	 * @return
	 */
	public String getEventMessage() {

		return eventMessage;
	}

}
