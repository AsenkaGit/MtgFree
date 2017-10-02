package asenka.mtgfree.view;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;

/**
 * The controller of the MTG card used by the view to perform actions on the card.
 * 
 * 
 * @author asenka
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

	/**
	 * @param visible
	 */
	public void setVisible(boolean visible) {

		this.card.setVisible(visible);
	}

	/**
	 * @param tapped
	 */
	public void setTapped(boolean tapped) {

		this.card.setTapped(tapped);
	}

	/**
	 * @param revealed
	 */
	public void setRevealed(boolean revealed) {

		this.card.setRevealed(revealed);
	}

	/**
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {

		this.card.setSelected(selected);
	}

	/**
	 * Update the card location (only if the card is on the battlefield)
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(double x, double y) {

		this.card.setLocation(x, y);
	}

	/**
	 * @param context
	 */
	public void setContext(MtgContext context) {

		this.card.setContext(context);
	}

}
