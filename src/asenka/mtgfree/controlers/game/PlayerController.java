package asenka.mtgfree.controlers.game;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observer;

import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;

/**
 * <p>
 * The main controller during a game. This is mainly by this controller that the player can manipulate the cards during the game
 * </p>
 * 
 * @author asenka
 * @see Player
 * @see Controller
 */
public class PlayerController extends Controller<Player> {

	/**
	 * Build the controller for a player.
	 * 
	 * @param player the player controlled
	 * @param playerManaged <code>true</code> if the player controller is used by a human player, <code>false</code> if it is used by the program
	 */
	public PlayerController(Player player, boolean playerManaged) {

		super(player, playerManaged);
	}

	/**
	 * Draw a card. It gets and removes the first card from the library and put it in the player 's hand
	 * @throws Exception 
	 */
	public void draw() throws Exception {

		try {
			Card card = this.data.getLibrary().draw();
			this.data.addCardToHand(card);
		} catch(NoSuchElementException ex) {
			throw new Exception("The player's library is empty, you can not draw card anymore", ex);
		}
		 
	}

	/**
	 * Draw <code>x</code> cards from the player's library. Each card is send to the player's hand.
	 * 
	 * @param x the number of cards to draw
	 */
	public void draw(int x) {

		List<Card> cards = this.data.getLibrary().draw(x);
		cards.forEach(card -> this.data.addCardToHand(card));
	}

	/**
	 * Play a card. It means that you take a card from a place (hand, grave yard, library, ...) and put it on the battlefield.
	 * 
	 * @param card the card to put on the battlefield
	 * @param origin where the card was played from (hand, library, exile area, grave yard)
	 * @param visible do you want to put the card on the battlefield visible or not
	 * @param x where to add the card on the battlefield (horizontal coordinate)
	 * @param y where to add the card on the battlefield (vertical coordinate)
	 */
	public void play(Card card, Origin origin, boolean visible, double x, double y) {

		if (origin == Origin.BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot play a card that is already on the battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setLocation(x, y);
			card.setVisible(visible);
			this.data.getBattlefield().add(card);
		}
	}

	/**
	 * Exile a card. It means that you take a card from a place and put it in the exile area.
	 * 
	 * @param card the card to put on the exile area
	 * @param origin where the card was played from (hand, library, battlefield, grave yard)
	 * @param visible do you want to put the card on the exile area visible or not
	 */
	public void exile(Card card, Origin origin, boolean visible) {

		if (origin == Origin.EXILE) {
			throw new IllegalArgumentException("You cannot exile a card that is already exiled");
		} else {
			checkOriginAndRemove(origin, card);
			card.setVisible(visible);
			this.data.addCardToExile(card);
		}
	}

	/**
	 * Send a card to the grave yard. It means that you take a card from a place and put it in the grave yard.
	 * 
	 * @param card the card to put on the grave yard
	 * @param origin where the card was played from (hand, library, battlefield, exile area)
	 */
	public void destroy(Card card, Origin origin) {

		if (origin == Origin.GRAVEYARD) {
			throw new IllegalArgumentException("You cannot destroy a card that is already in the graveyard");
		} else {
			checkOriginAndRemove(origin, card);
			card.setVisible(true);
			this.data.addCardToGraveyard(card);
		}
	}

	/**
	 * Send a card back to hand. It means that you take a card from a place and put it in your hand.
	 * 
	 * @param card the card to put on the hand
	 * @param origin where the card was played from (grave yard, library, battlefield, exile area)
	 */
	public void backToHand(Card card, Origin origin) {

		if (origin == Origin.HAND) {
			throw new IllegalArgumentException("You cannot send back to hand a card that is already in the player's hand");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(false);
			card.setVisible(true);
			this.data.addCardToHand(card);
		}
	}
	
	/**
	 * Send a card to the library (on the top)
	 * @param card
	 * @param origin
	 */
	public void backToTopOfLibrary(Card card, Origin origin) {
		
		if (origin == Origin.LIBRARY) {
			throw new IllegalArgumentException("You cannot send back to library a card that is already in the player's library");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(false);
			card.setVisible(true);
			this.data.getLibrary().addOnTop(card);
		}
	}
	

	/**
	 * Send a card to the library (on the bottom)
	 * @param card
	 * @param origin
	 */
	public void backToBottomOfLibrary(Card card, Origin origin) {
		
		if (origin == Origin.LIBRARY) {
			throw new IllegalArgumentException("You cannot send back to library a card that is already in the player's library");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(false);
			card.setVisible(true);
			this.data.getLibrary().addToBottom(card);
		}
	}

	/**
	 * Tap or untap a list of cards
	 * 
	 * @param tapped true/false
	 * @param cards the card to update
	 */
	public void setTapped(boolean tapped, List<Card> cards) {

		cards.forEach(card -> card.setTapped(tapped));
	}

	/**
	 * Tap or untap all the cards on the battlefield
	 * 
	 * @param tapped true/false
	 */
	public void setTappedAll(boolean tapped) {

		this.data.getBattlefield().getCards().forEach(card -> card.setTapped(tapped));
	}

	/**
	 * Shuffles the library of the player
	 */
	public void shuffleLibrary() {

		this.data.getLibrary().shuffle();
	}

	/**
	 * Add any type of card to the battlefield without any checking. This method should be used only for token cards and copy of
	 * existing cards on the battlefield.
	 * 
	 * @param card the card to add
	 */
	public void addCardToBattlefield(Card card) {

		this.data.getBattlefield().add(card);
	}

	/**
	 * Remove any type of card from the battlefield without any checking. This method should be used only for token cards and copy
	 * of existing cards on the battlefield.
	 * 
	 * @param card the card to remove
	 */
	public void removeCardFromBattlefield(Card card) throws Exception {

		this.data.getBattlefield().remove(card);
	}

	/**
	 * 
	 * @param lifeCounters
	 */
	public void setLifeCounters(int lifeCounters) {

		this.data.setLifeCounters(lifeCounters);
	}

	/**
	 * 
	 * @param poisonCounters
	 */
	public void setPoisonCounters(int poisonCounters) {

		this.data.setPoisonCounters(poisonCounters);
	}

	/**
	 * PlayerController overrides the Controller method to add the observer to the
	 * library and the battlefield
	 */
	@Override
	public void addObserver(Observer observer) {
	
		super.addObserver(observer);
	
		Library library = this.data.getLibrary();
		Battlefield battlefield = this.data.getBattlefield();
	
		library.addObserver(observer);
		battlefield.addObserver(observer);
	}

	/**
	 * PlayerController overrides the Controller method to remove the observer from the
	 * library and the battlefield
	 */
	@Override
	public void deleteObserver(Observer observer) {
	
		super.deleteObserver(observer);
	
		Library library = this.data.getLibrary();
		Battlefield battlefield = this.data.getBattlefield();
	
		library.deleteObserver(observer);
		battlefield.deleteObserver(observer);
	}

	/**
	 * PlayerController overrides the Controller method to clear the observers from the
	 * library and the battlefield
	 */
	@Override
	public void deleteObservers() {
	
		super.deleteObservers();
	
		Library library = this.data.getLibrary();
		Battlefield battlefield = this.data.getBattlefield();
	
		library.deleteObservers();
		battlefield.deleteObservers();
	}

	/**
	 * Try to remove a card according to the origin. If the card is really removed from the origin collection, then everything is
	 * okay. But otherwise, it means that the origin is wrong because the card is not is the origin collection.
	 * 
	 * @param origin the origin of the card where it should be removed
	 * @param card the card to remove
	 * @throws RuntimeException if the card was not removed from the origin collection
	 */
	private void checkOriginAndRemove(Origin origin, Card card) throws RuntimeException {

		switch (origin) {
			case BATTLEFIELD:
				if (!this.data.getBattlefield().remove(card)) {
					throw new RuntimeException("The " + card + " is not on the battlefield");
				}
				break;
			case LIBRARY:
				if (!this.data.getLibrary().remove(card)) {
					throw new RuntimeException("The " + card + " is not in the library");
				}
				break;
			case HAND:
				if (!this.data.removeCardFromHand(card)) {
					throw new RuntimeException("The " + card + " is not in the hand");
				}
				break;
			case EXILE:
				if (!this.data.removeCardFromExile(card)) {
					throw new RuntimeException("The " + card + " is not in the exile area");
				}
				break;
			case GRAVEYARD:
				if (!this.data.removeCardFromGraveyard(card)) {
					throw new RuntimeException("The " + card + " is not in the graveyard");
				}
				break;
		}
	}

}
