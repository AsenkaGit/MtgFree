package asenka.mtgfree.controllers.game;

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
	 * The superclass Controller&lt;T&gt; implements Serializable. The serialization may not be useful here
	 */
	private static final long serialVersionUID = 8232303159047672715L;

	/**
	 * Build a deck controller. This constructor assumes that the deck controller is always 
	 * used by a human player (see {@link Controller#createNetworkEvents}).
	 * 
	 * @param data
	 */
	public DeckController(Deck data) {
		super(data, true);
	}

	/**
	 * Update the deck name
	 * 
	 * @param name
	 */
	public void setName(String name) {

		this.controlledData.setName(name);
	}

	/**
	 * Update the deck description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {

		this.controlledData.setDescription(description);
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
			this.controlledData.addCardToSideboard(mtgCard, number);
		} else {
			this.controlledData.addCardToMain(mtgCard, number);
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
			this.controlledData.removeCardFromSideboard(mtgCard);
		} else {
			this.controlledData.removeCardFromMain(mtgCard);
		}
	}
}
