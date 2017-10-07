package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Event triggered when a card arrives on the battlefield
 * 
 * @author asenka
 *
 */
public class MtgGameTableAddCardEvent extends AbstractMtgGameTableEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -7761433506933504588L;
	
	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgGameTableAddCardEvent.class);

	/**
	 * Constructor
	 * @param card the card added
	 */
	public MtgGameTableAddCardEvent(final MtgCard card) {
		super(buildEventMessage(card), card);
		LOGGER.trace(super.eventMessage);
	}
	
	/**
	 * Create a event message adapted to this event type.
	 * 
	 * @param card the card updated
	 * @return <code>"The card &lt;name&gt;(&lt;id&gt;) is now on the battlefield"</code>
	 */
	private static final String buildEventMessage(final MtgCard card) {
		
		return "The card " + card.getName() + "(" + card.getId() + ") is now on the battlefield";
	}
}
