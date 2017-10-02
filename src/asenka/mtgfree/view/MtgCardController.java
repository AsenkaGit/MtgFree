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
	 * The card controlled by this controller
	 */
	private MtgCard card;

	/**
	 * Constructor with an MtgCard
	 * @param card
	 */
	public MtgCardController(MtgCard card) {
		super();
		this.card = card;
	}

	/**
	 * @return the card controlled by this controller
	 */
	public MtgCard getCard() {

		return card;
	}

	/**
	 * @param card the card to control
	 */
	public void setCard(MtgCard card) {

		this.card = card;
	}

	/**
	 * Action : set if a card is visible (i.e. display the front side)
	 * @param visible <code>true</code> to display the front side
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
