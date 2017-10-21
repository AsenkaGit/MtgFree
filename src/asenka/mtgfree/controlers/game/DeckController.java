package asenka.mtgfree.controlers.game;

import javax.management.RuntimeErrorException;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.game.Deck;

/**
 * The controller of the deck
 * 
 * @author asenka
 * @see Deck
 * @see Controller
 */
public class DeckController extends Controller<Deck> {

	/**
	 * Build a deck controller
	 * 
	 * @param data
	 */
	public DeckController(Deck data) {
		super(data);
	}

	/**
	 * Update the deck name
	 * 
	 * @param name
	 */
	public void setName(String name) {

		this.data.setName(name);
	}

	/**
	 * Update the deck description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {

		this.data.setDescription(description);
	}

	/**
	 * Add a card to the deck in the main list
	 * 
	 * @param mtgCard the card to add
	 * @param number the number of time you want to add the card
	 */
	public void addCard(MtgCard mtgCard, int number) {

		try {
			this.addCard(mtgCard, number, false);
		} catch (Exception e) {
			throw new RuntimeErrorException(new Error("This method should not triggers exception."));
		}
	}

	/**
	 * Add a card to the deck
	 * 
	 * @param mtgCard the card to add
	 * @param number the number of time you want to add the card
	 * @param sideboard if <code>true</code> the card is added to the sideboard
	 * @throws Exception when adding a card to the sideboard ONLY, if the sideboard size exceed a certain limit (it was initially set
	 *         to 15)
	 */
	public void addCard(MtgCard mtgCard, int number, boolean sideboard) throws Exception {

		if (sideboard) {
			this.data.addCardToSideboard(mtgCard, number);
		} else {
			this.data.addCardToMain(mtgCard, number);
		}
	}

	/**
	 * Removes a card from the main list
	 * @param mtgCard the card to remove
	 */
	public void removeCard(MtgCard mtgCard)  {

		this.removeCard(mtgCard, false);
	}

	/**
	 * Removes a card from the deck
	 * @param mtgCard the card to remove
	 * @param sideboard if <code>true</code>, removes the card from the sideboard
	 */
	public void removeCard(MtgCard mtgCard, boolean sideboard) {

		if (sideboard) {
			this.data.removeCardFromSideboard(mtgCard);
		} else {
			this.data.removeCardFromMain(mtgCard);
		}
	}
}