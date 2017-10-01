package asenka.mtgfree.model.mtg.events;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * @author asenka
 *
 */
public class AbstractMtgCardEvent extends AbstractEvent {

	/**
	 * 
	 */
	private MtgCard updatedCard;

	/**
	 * @param eventMessage
	 * @param updatedCard
	 */
	public AbstractMtgCardEvent(String eventMessage, MtgCard updatedCard) {
		super(eventMessage);
		this.updatedCard = updatedCard;
	}

	/**
	 * 
	 * @return
	 */
	public MtgCard getUpdatedCard() {

		return updatedCard;
	}
	
	/**
	 * 
	 * @param updatedCard
	 */
	public void setUpdatedCard(MtgCard updatedCard) {

		this.updatedCard = updatedCard;
	}
}
