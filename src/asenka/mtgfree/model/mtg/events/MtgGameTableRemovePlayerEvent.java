package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.MtgPlayer;

/**
 * Event triggered when a player leaves a game table
 * 
 * @author asenka
 *
 */
public class MtgGameTableRemovePlayerEvent extends AbstractMtgGameTableEvent {

		
	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -3229001473070040114L;
	
	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgGameTableRemovePlayerEvent.class);

	/**
	 * Constructor
	 * @param card the card added
	 */
	public MtgGameTableRemovePlayerEvent(final MtgPlayer card) {
		super(buildEventMessage(card), card);
		LOGGER.trace(super.eventMessage);
	}
	
	/**
	 * Create a event message adapted to this event type.
	 * 
	 * @param card the card updated
	 * @return 
	 */
	private static final String buildEventMessage(final MtgPlayer player) {
		
		return "The player " + player.getName() + " has left the table";
	}
}
