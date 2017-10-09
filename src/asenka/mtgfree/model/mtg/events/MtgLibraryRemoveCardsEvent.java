package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.MtgLibrary;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This event is triggered when one or more cards are removed from a library
 * 
 * @author asenka
 * @see MtgLibrary
 */
public class MtgLibraryRemoveCardsEvent extends AbstractMtgLibraryUpdatedEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 5994593090307173403L;

	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgLibraryRemoveCardsEvent.class);

	/**
	 * The array of removed cards.
	 */
	private final MtgCard[] removedCards;

	/**
	 * 
	 * @param updatedLibrary
	 * @param cards the array of cards removed from the library
	 */
	public MtgLibraryRemoveCardsEvent(final MtgLibrary updatedLibrary, final MtgCard... cards) {

		super(buildEventMessage(updatedLibrary, cards), updatedLibrary);
		this.removedCards = cards;
		LOGGER.trace(super.eventMessage);
	}

	/**
	 * @return the removed cards from the library
	 */
	public MtgCard[] getRemovedCards() {

		return this.removedCards;
	}

	/**
	 * Create the event message explaining the event
	 * @param updatedLibrary the library updated
	 * @param removedCards the cards removed
	 * @return a string explaining the event
	 */
	private static final String buildEventMessage(final MtgLibrary updatedLibrary, final MtgCard... removedCards) {
		
		StringBuffer buf = new StringBuffer();
		buf.append('[');
		for(MtgCard card : removedCards) {
			buf.append(card.getName() + ", ");
		}
		buf.append(']');
		
		return "The cards " + buf.toString() + " have been removed from library";
	}
}
