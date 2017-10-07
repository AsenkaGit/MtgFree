package asenka.mtgfree.model.mtg.events;

import java.util.List;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Event triggered when the selection changes
 * 
 * @author asenka
 *
 */
public class SelectionUpdateEvent extends AbstractEvent {

	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgGameTableAddCardEvent.class);

	/**
	 * The selected cards
	 */
	private final List<MtgCard> selectedCards;

	/**
	 * 
	 * @param selectedCards
	 */
	public SelectionUpdateEvent(List<MtgCard> selectedCards) {

		super(buildEventMessage(selectedCards));
		this.selectedCards = selectedCards;
		LOGGER.trace(super.eventMessage);
	}

	/**
	 * @return the list of selected cards
	 */
	public List<MtgCard> getSelectedCards() {

		return selectedCards;
	}

	/**
	 * 
	 * @param selectedCards
	 * @return
	 */
	private static String buildEventMessage(List<MtgCard> selectedCards) {

		StringBuffer buf = new StringBuffer();
		buf.append("Update the selected cards: ");
		
		for (MtgCard card : selectedCards) {
			buf.append(card.getName());
			buf.append("(");
			buf.append(card.getId());
			buf.append("), ");
		}
		return buf.toString();
	}

}
