package asenka.mtgfree.controlers.game;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observer;

import org.apache.log4j.Logger;

import asenka.mtgfree.communication.NetworkEventManager;
import asenka.mtgfree.events.network.NetworkEvent;
import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Counter;
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
	 * 
	 */
	private static final long serialVersionUID = 2172603147829383956L;

	/**
	 * Build the controller for a player.
	 * 
	 * @param player the player controlled
	 * @param playerManaged <code>true</code> if the player controller is used by a human player, <code>false</code> if it is used
	 *        by the program
	 */
	public PlayerController(Player player, boolean playerManaged) {

		super(player, playerManaged);
	}

	/**
	 * Draw a card. It gets and removes the first card from the library and put it in the player 's hand
	 * 
	 * @throws Exception
	 */
	public void draw() throws Exception {

		try {
			Card card = this.data.getLibrary().draw(this.data);
			this.data.addCardToHand(card);

			if (playerManaged) {
				notifyNetworkObserver(new NetworkEvent("draw", this.data, new Integer(1)));
			}

		} catch (NoSuchElementException ex) {
			throw new Exception("The player's library is empty, you can not draw card anymore", ex);
		}

	}

	/**
	 * Draw <code>x</code> cards from the player's library. Each card is send to the player's hand.
	 * 
	 * @param x the number of cards to draw
	 */
	public void draw(int x) {

		List<Card> cards = this.data.getLibrary().draw(this.data, x);
		cards.forEach(card -> this.data.addCardToHand(card));

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("draw", this.data, new Integer(x)));
		}
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
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot play a card from the opponent battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setLocation(this.data, x, y);
			card.setVisible(this.data, visible);
			this.data.getBattlefield().add(this.data, card);

			if (playerManaged) {
				notifyNetworkObserver(new NetworkEvent("play", this.data, new Serializable[] { card, origin }));
			}
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
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot exile a card that belong to your opponent");
		} else {
			checkOriginAndRemove(origin, card);
			card.setVisible(this.data, visible);
			this.data.addCardToExile(card);

			if (playerManaged) {
				notifyNetworkObserver(new NetworkEvent("exile", this.data, new Serializable[] { card, origin }));
			}
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
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot destroy a card that belong to your opponent");
		} else {
			checkOriginAndRemove(origin, card);
			card.setVisible(this.data, true);
			this.data.addCardToGraveyard(card);

			if (playerManaged) {
				notifyNetworkObserver(new NetworkEvent("destroy", this.data, new Serializable[] { card, origin }));
			}
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
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot put in your hand a card on the opponent's battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(this.data, false);
			card.setVisible(this.data, true);
			this.data.addCardToHand(card);

			if (playerManaged) {
				notifyNetworkObserver(new NetworkEvent("backToHand", this.data, new Serializable[] { card, origin }));
			}
		}
	}

	/**
	 * Send a card to the library (on the top)
	 * 
	 * @param card
	 * @param origin
	 */
	public void backToTopOfLibrary(Card card, Origin origin) {

		if (origin == Origin.LIBRARY) {
			throw new IllegalArgumentException("You cannot send back to library a card that is already in the player's library");
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot send back to your library a card on the opponent's battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(this.data, false);
			card.setVisible(this.data, true);
			this.data.getLibrary().addOnTop(this.data, card);

			if (playerManaged) {
				notifyNetworkObserver(new NetworkEvent("backToTopOfLibrary", this.data, new Serializable[] { card, origin }));
			}
		}
	}

	/**
	 * Send a card to the library (on the bottom)
	 * 
	 * @param card
	 * @param origin
	 */
	public void backToBottomOfLibrary(Card card, Origin origin) {

		if (origin == Origin.LIBRARY) {
			throw new IllegalArgumentException("You cannot send back to library a card that is already in the player's library");
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot send back to your library a card on the opponent's battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(this.data, false);
			card.setVisible(this.data, true);
			this.data.getLibrary().addToBottom(this.data, card);

			if (playerManaged) {
				notifyNetworkObserver(new NetworkEvent("backToBottomOfLibrary", this.data, new Serializable[] { card, origin }));
			}
		}
	}

	/**
	 * Tap or untap a card
	 * 
	 * @param tapped true/false
	 * @param card the card to update
	 */
	public void setTapped(boolean tapped, Card card) {

		card.setTapped(this.data, tapped);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setTapped", this.data, card));
		}
	}

	/**
	 * Tap or untap a list of cards
	 * 
	 * @param tapped true/false
	 * @param cards the card to update
	 */
	public void setTapped(boolean tapped, List<Card> cards) {

		cards.forEach(card -> card.setTapped(this.data, tapped));

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setTapped", this.data, cards.toArray()));
		}
	}

	/**
	 * Set the visiblity of a list of cards
	 * 
	 * @param visible true/false
	 * @param card the card to update
	 */
	public void setVisible(boolean visible, Card card) {

		card.setVisible(this.data, visible);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setVisible", this.data, card));
		}
	}

	/**
	 * Set the visiblity of a card
	 * 
	 * @param visible true/false
	 * @param cards the card to update
	 */
	public void setVisible(boolean visible, List<Card> cards) {

		cards.forEach(card -> card.setVisible(this.data, visible));

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setVisible", this.data, cards.toArray()));
		}
	}

	/**
	 * Set the visiblity of a list of cards
	 * 
	 * @param revealed true/false
	 * @param card the card to update
	 */
	public void setRevealed(boolean revealed, Card card) {

		card.setRevealed(this.data, revealed);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setRevealed", this.data, card));
		}
	}

	/**
	 * Set the visiblity of a card
	 * 
	 * @param revealed true/false
	 * @param cards the card to update
	 */
	public void setRevealed(boolean revealed, List<Card> cards) {

		cards.forEach(card -> card.setRevealed(this.data, revealed));

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setRevealed", this.data, cards.toArray()));
		}
	}

	/**
	 * Update the card location
	 * 
	 * @param x new vertical coordinate
	 * @param y new horizontal coordinate
	 * @param card the card to update
	 */
	public void setLocation(double x, double y, Card card) {

		card.setLocation(this.data, x, y);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setLocation", this.data, card));
		}
	}

	/**
	 * Add a new counter on the card
	 * 
	 * @param counter
	 * @param card
	 */
	public void addCounter(Counter counter, Card card) {

		card.addCounter(this.data, counter);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("addCounter", this.data, new Serializable[] { card, counter }));
		}
	}

	/**
	 * Removes a counter from the card
	 * 
	 * @param counter the counter to remove
	 * @param card the card
	 */
	public void removeCounter(Counter counter, Card card) {

		card.removeCounter(this.data, counter);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("removeCounter", this.data, new Serializable[] { card, counter }));
		}
	}

	/**
	 * Shuffles the library of the player
	 */
	public void shuffleLibrary() {

		this.data.getLibrary().shuffle(this.data);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("shuffleLibrary", this.data, this.data.getLibrary()));
		}
	}

	/**
	 * Add any type of card to the battlefield without any checking. This method should be used only for token cards and copy of
	 * existing cards on the battlefield.
	 * 
	 * @param card the card to add
	 */
	public void addCardToBattlefield(Card card) {

		this.data.getBattlefield().add(this.data, card);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("addCardToBattlefield", this.data, card));
		}
	}

	/**
	 * Remove any type of card from the battlefield without any checking. This method should be used only for token cards and copy
	 * of existing cards on the battlefield.
	 * 
	 * @param card the card to remove
	 */
	public void removeCardFromBattlefield(Card card) throws Exception {

		this.data.getBattlefield().remove(this.data, card);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("removeCardFromBattlefield", this.data, card));
		}
	}

	/**
	 * 
	 * @param lifeCounters
	 */
	public void setLifeCounters(int lifeCounters) {

		this.data.setLifeCounters(lifeCounters);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setLifeCounters", this.data));
		}
	}

	/**
	 * 
	 * @param poisonCounters
	 */
	public void setPoisonCounters(int poisonCounters) {

		this.data.setPoisonCounters(poisonCounters);

		if (playerManaged) {
			notifyNetworkObserver(new NetworkEvent("setPoisonCounters", this.data));
		}
	}

	/**
	 * PlayerController overrides the Controller method to add the observer to the library and the battlefield
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
	 * PlayerController overrides the Controller method to remove the observer from the library and the battlefield
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
	 * PlayerController overrides the Controller method to clear the observers from the library and the battlefield
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
				if (!this.data.getBattlefield().remove(this.data, card)) {
					throw new RuntimeException("The " + card + " is not on the battlefield");
				}
				break;
			case LIBRARY:
				if (!this.data.getLibrary().remove(this.data, card)) {
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
			case OPPONENT_BATTLEFIELD:
				throw new IllegalArgumentException("You cannot remove a card from the opponent battlefield");
		}
	}

	/**
	 * Send a netw
	 * 
	 * @param event
	 */
	private void notifyNetworkObserver(NetworkEvent event) {

		try {
			NetworkEventManager.getInstance().send(event);
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e);
			throw new RuntimeException("Network issue", e);
		}
	}
}
