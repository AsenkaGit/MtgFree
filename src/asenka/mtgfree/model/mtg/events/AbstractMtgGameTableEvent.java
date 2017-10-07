package asenka.mtgfree.model.mtg.events;

import java.io.Serializable;

import asenka.mtgfree.model.mtg.MtgPlayer;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Abstract class for the events occurring on the MtgGameTable
 * 
 * @author asenka
 *
 */
public abstract class AbstractMtgGameTableEvent extends AbstractEvent implements Serializable {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 1506769365315646388L;
	
	/**
	 * The event related to the game table involve an mtg card to add or remove.
	 */
	private final MtgCard card;
	
	/**
	 * The events can also be related to player (add/remove)
	 */
	private final MtgPlayer player;

	/**
	 * Constructor
	 * @param eventMessage
	 * @param card
	 */
	protected AbstractMtgGameTableEvent(final String eventMessage, final MtgCard card) {
		super(eventMessage);
		this.card = card;
		this.player = null;
	}
	

	/**
	 * Constructor
	 * @param eventMessage
	 * @param card
	 */
	protected AbstractMtgGameTableEvent(final String eventMessage, final MtgPlayer player) {
		super(eventMessage);
		this.player = player;
		this.card = null;
	}

	/**
	 * Returns an MtgCard
	 * @return the card added or removed (according to the type of event)
	 */
	public MtgCard getCard() {

		return card;
	}
	
	/**
	 * Returns a MtgPlayer
	 * @return the player added or removed (according to the type of event)
	 */
	public MtgPlayer getPlayer() {
		
		return player;
	}
}
