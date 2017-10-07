package asenka.mtgfree.model.mtg.events;

import java.io.Serializable;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Abstract class used to manage all the events happening on an MtgCard
 * 
 * @author asenka
 * @see MtgCard
 */
public abstract class AbstractMtgCardUpdatedEvent extends AbstractEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2109871609821360816L;

	/**
	 * The card updated. This object must contains the new values
	 */
	protected final MtgCard updatedCard;

	/**
	 * Constructor
	 * 
	 * @param eventMessage a specific message describing the event
	 * @param updatedCard the card updated with the new value(s)
	 */
	public AbstractMtgCardUpdatedEvent(final String eventMessage, final MtgCard updatedCard) {

		super(eventMessage);
		this.updatedCard = updatedCard;
	}

	/**
	 * With this method you can get the card updated and, like that, you can get all the <strong>new values</strong>
	 * 
	 * @return the updated card related to the event.
	 */
	public MtgCard getUpdatedCard() {

		return this.updatedCard;
	}
}
