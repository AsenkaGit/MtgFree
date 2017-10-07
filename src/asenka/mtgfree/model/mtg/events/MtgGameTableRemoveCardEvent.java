package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Event triggered when a card is removed from the game table. For example:
 * <ul>
 * <li>Delete an useless token</li>
 * <li>A card goes back to player's hand</li>
 * <li>A card goes back to player's library</li>
 * <li>...</li>
 * <ul>
 * 
 * 
 * @author asenka
 *
 */
public class MtgGameTableRemoveCardEvent extends AbstractMtgGameTableEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -8885238245629596081L;
	
	
	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgGameTableRemoveCardEvent.class);

	/**
	 * Constructor
	 * @param card the card removed
	 */
	public MtgGameTableRemoveCardEvent(final MtgCard card) {
		super(buildEventMessage(card), card);
		LOGGER.trace(super.eventMessage);
	}
	
	/**
	 * Create a event message adapted to this event type.
	 * 
	 * @param card the card updated
	 * @return <code>"The card &lt;name&gt;(&lt;id&gt;) has been removed from the battlefield"</code>
	 */
	private static final String buildEventMessage(final MtgCard card) {
		
		return "The card " + card.getName() + "(" + card.getId() + ") has been removed from the battlefield";
	}
}
