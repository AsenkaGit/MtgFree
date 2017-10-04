package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.MtgPlayer;

/**
 * Event triggered when a player has its poison counter is modified
 * 
 * @author asenka
 *
 */
public class MtgPlayerPoisonUpdatedEvent extends AbstractMtgPlayerUpdatedEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3462661816546451813L;

	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgPlayerPoisonUpdatedEvent.class);

	/**
	 * Constructor
	 * @param updatedPlayer
	 */
	public MtgPlayerPoisonUpdatedEvent(MtgPlayer updatedPlayer) {

		super(buildEventMessage(updatedPlayer), updatedPlayer);
		LOGGER.trace(super.eventMessage);
	}

	/**
	 * 
	 * @param player
	 * @return
	 */
	private static final String buildEventMessage(final MtgPlayer player) {

		// TODO event Message
		return "";
	}
}
