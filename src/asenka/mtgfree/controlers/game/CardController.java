package asenka.mtgfree.controlers.game;

import java.util.Observer;

import asenka.mtgfree.controlers.game.Controller.Origin;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Counter;
import asenka.mtgfree.model.game.Library;

/**
 * The controller of a card.
 * 
 * 
 * @author asenka
 *
 */
public class CardController extends Controller<Card> {

	/**
	 * The player's controller. Having this controller helps you to perform operations that needs data outside the card scope.
	 * Such as send back the card to player hand, destroy the card, etc...
	 */
	private PlayerController playerController;

	/**
	 * Build a card controller
	 * 
	 * @param card the card controlled
	 * @param playerController the player controller
	 * @param playerManaged <code>true</code> if the player controller is used by a human player, <code>false</code> if it is used by the program
	 */
	public CardController(Card card, PlayerController playerController, boolean playerManaged) {

		super(card, playerManaged);
		this.playerController = playerController;
	}

	/**
	 * @return the player Controller
	 */
	public PlayerController getPlayerController() {

		return playerController;
	}

	/**
	 * Change the controller of the card. You can use this
	 * 
	 * @param playerController the playerController to set
	 */
	public void setPlayerController(PlayerController playerController) {

		this.playerController = playerController;
	}

	/**
	 * Tap/untap the card
	 * 
	 * @param tapped true/false
	 */
	public void setTapped(boolean tapped) {

		this.data.setTapped(tapped);
	}

	/**
	 * Hide/show the card
	 * 
	 * @param visible true/false
	 */
	public void setVisible(boolean visible) {

		this.data.setVisible(visible);
	}

	/**
	 * Revealed/hide the card to other players
	 * 
	 * @param revealed true/false
	 */
	public void setRevealed(boolean revealed) {

		this.data.setRevealed(revealed);
	}

	/**
	 * Update the card location
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 */
	public void setLocation(double x, double y) {

		this.data.setLocation(x, y);
	}

	/**
	 * Add a counter on the card
	 * 
	 * @param counter the counter to add
	 * @throws IllegalArgumentException if the counter is null
	 */
	public void addCounter(Counter counter) throws IllegalArgumentException {

		if (counter == null) {
			throw new IllegalArgumentException("null counters are not allowed");
		} else {
			this.data.addCounter(counter);
		}
	}

	/**
	 * Removes a counter from the card
	 * 
	 * @param counter the counter to removes
	 * @throws IllegalArgumentException if counter is null
	 */
	public void removeCounter(Counter counter) throws IllegalArgumentException {

		if (counter == null) {
			throw new IllegalArgumentException("Try to remove a null counter");
		} else {
			this.data.removeCounter(counter);
		}
	}

	/**
	 * Removes all the counters from the card
	 */
	public void clearCounters() {

		this.data.clearCounters();
	}

	/**
	 * Associate another card to this one
	 * 
	 * @param associatedCard the other card to assicate
	 * @throws IllegalArgumentException if <code>associatedCard</code> is <code>null</code> or is the same as this card
	 */
	public void addAssociatedCard(Card associatedCard) throws IllegalArgumentException {

		if (associatedCard != null && this.data != associatedCard) {
			this.data.addAssociatedCard(associatedCard);
		} else {
			throw new IllegalArgumentException("Unable to associate the card " + this.data + " with " + associatedCard);
		}
	}

	/**
	 * Remove an associated card
	 * 
	 * @param associatedCard the card to remove
	 * @throws IllegalArgumentException
	 */
	public void removeAssociatedCard(Card associatedCard) throws IllegalArgumentException {

		if (associatedCard != null && this.data != associatedCard) {
			this.data.removeAssociatedCard(associatedCard);
		} else {
			throw new IllegalArgumentException("Unable to remove from " + this.data + " the associated card  " + associatedCard);
		}
	}

	/**
	 * Removes all the associated cards
	 */
	public void clearAssociatedCards() {

		this.data.clearAssociatedCards();
	}

	/**
	 * Send the card to grave yard
	 * 
	 * @param origin the origin of the card
	 * @see Origin
	 */
	public void destroy(Origin origin) {

		this.playerController.destroy(this.data, origin);
	}

	/**
	 * Exile the card
	 * 
	 * @param origin
	 * @param visible
	 * @see Origin
	 */
	public void exile(Origin origin, boolean visible) {

		this.playerController.exile(this.data, origin, visible);
	}

	/**
	 * Play the card on the battlefield
	 * 
	 * @param origin
	 * @param visible
	 * @param x
	 * @param y
	 * @see Origin
	 */
	public void play(Origin origin, boolean visible, double x, double y) {

		this.playerController.play(this.data, origin, visible, x, y);
	}

	/**
	 * Send back the card to the player's hand
	 * 
	 * @param origin
	 */
	public void backToHand(Origin origin) {

		this.playerController.backToHand(this.data, origin);
	}
	
	/**
	 * Move the controlled card to the top of the library. The controlled card has to be in
	 * the player library.
	 * @throws RuntimeException if the controlled is not in the player library
	 */
	public void moveToTopOfLibrary() {
		
		final Library library = this.playerController.data.getLibrary();
		
		if(!library.changeCardIndex(this.data, 0)) {
			throw new RuntimeException(this.data + " is not in the player's library");
		}
	}
	
	/**
	 * Move the controlled card to the bottom of the library. The controlled card has to be in
	 * the player library.
	 * @throws RuntimeException if the controlled is not in the player library
	 */
	public void moveToBottompOfLibrary() {
		
		final Library library = this.playerController.data.getLibrary();
		
		if(!library.changeCardIndex(this.data, library.size() - 1)) {
			throw new RuntimeException(this.data + " is not in the player's library");
		}
	}

	@Override
	public void addObserver(Observer observer) {

		super.addObserver(observer);
		this.playerController.addObserver(observer);
	}

	@Override
	public void deleteObserver(Observer observer) {

		super.deleteObserver(observer);
		this.playerController.deleteObserver(observer);
	}

	@Override
	public void deleteObservers() {

		super.deleteObservers();
		this.playerController.deleteObservers();
	}

}
