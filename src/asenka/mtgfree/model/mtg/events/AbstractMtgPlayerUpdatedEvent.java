package asenka.mtgfree.model.mtg.events;

import asenka.mtgfree.model.mtg.MtgPlayer;

/**
 * Abstract class for all the events related to a player updated events during a game
 * 
 * @author asenka
 */
public abstract class AbstractMtgPlayerUpdatedEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -488665642519716872L;
	
	/**
	 * The updated player
	 */
	protected final MtgPlayer updatedPlayer;

	/**
	 * Constructor
	 * @param eventMessage
	 * @param updatedPlayer
	 */
	public AbstractMtgPlayerUpdatedEvent(final String eventMessage, final MtgPlayer updatedPlayer) {

		super(eventMessage);
		this.updatedPlayer = updatedPlayer;
	}

	/**
	 * @return the updated Player
	 */
	public MtgPlayer getUpdatedPlayer() {

		return updatedPlayer;
	}
}
