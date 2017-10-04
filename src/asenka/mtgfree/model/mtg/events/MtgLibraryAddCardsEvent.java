package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.MtgLibrary;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This event is triggered when new card(s) are added in the library
 * 
 * @author asenka
 *
 */
public class MtgLibraryAddCardsEvent extends AbstractMtgLibraryUpdatedEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 254369646130903098L;

	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgLibraryAddCardsEvent.class);

	/**
	 * The new cards in the library
	 */
	private final MtgCard[] addedCards;
	
	/**
	 * The indexes where those cards have been added
	 */
	private final int[] indexes;

	/**
	 * @param updatedLibrary
	 * @param addedCards
	 * @param indexes
	 */
	public MtgLibraryAddCardsEvent(final MtgLibrary updatedLibrary, final MtgCard[] addedCards, final int[] indexes) {

		super(buildEventMessage(updatedLibrary, addedCards, indexes), updatedLibrary);
		this.addedCards = addedCards;
		this.indexes = indexes;
		LOGGER.trace(super.eventMessage);
	}

	
	/**
	 * @return the new cards
	 */
	public MtgCard[] getAddedCards() {
	
		return addedCards;
	}

	
	/**
	 * @return the indexes of the new cards in the library
	 */
	public int[] getIndexes() {
	
		return indexes;
	}
	
	/**
	 * 
	 * @param updatedLibrary
	 * @param addedCards
	 * @param indexes
	 * @return
	 */
	private static final String buildEventMessage(final MtgLibrary updatedLibrary, final MtgCard[] addedCards, final int[] indexes) {
		// TODO event Message
		return "";
	}
}
