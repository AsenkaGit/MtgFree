package asenka.mtgfree.view;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * 
 * @author asenka
 *
 */
public class MtgCardController {

	/**
	 * 
	 */
	private MtgCard card;

	/**
	 * @param card
	 */
	public MtgCardController(MtgCard card) {
		super();
		this.card = card;
	}

	/**
	 * @return
	 */
	public MtgCard getCard() {

		return card;
	}

	/**
	 * @param card
	 */
	public void setCard(MtgCard card) {

		this.card = card;
	}

}
