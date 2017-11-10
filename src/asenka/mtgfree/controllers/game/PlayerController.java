package asenka.mtgfree.controllers.game;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

import asenka.mtgfree.communication.GameManager;
import asenka.mtgfree.events.NetworkEvent;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Counter;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Origin;
import asenka.mtgfree.model.game.Player;

import static asenka.mtgfree.events.NetworkEvent.Type.*;

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
	 * @param createNetworkEvent use <code>true</code> if you want that this controller create NetworkEvent to notify the other
	 *        player that the local player performed an action
	 */
	public PlayerController(Player player, boolean createNetworkEvent) {

		super(player, createNetworkEvent);
	}

	/**
	 * Find a card inside all the player's cards lists (battlefield, library, exile, hand, etc...) with a battle ID
	 * 
	 * @param battleId a long value for the battle ID
	 * @throws RuntimeException if the card cannot be found
	 * @return a card 
	 */
	public Card findCardByBattleId(final long battleId) {
		
		final Predicate<Card> battleIdFilter = (card -> card.getBattleId() == battleId);
		Optional<Card> result = this.controlledData.getLibrary().getCards().stream().filter(battleIdFilter).findFirst();
		
		if(!result.isPresent()) {
			result = this.controlledData.getBattlefield().getCards().stream().filter(battleIdFilter).findFirst();
			
			if(!result.isPresent()) {
				result = this.controlledData.getHand().stream().filter(battleIdFilter).findFirst();
				
				if(!result.isPresent()) {
					result = this.controlledData.getGraveyard().stream().filter(battleIdFilter).findFirst();
					
					if(!result.isPresent()) {
						result = this.controlledData.getExile().stream().filter(battleIdFilter).findFirst();
					}
				}
			}
		}
		if(result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException("The card with the battle ID : " + battleId + " cannot be found in the data of " + this.controlledData);
		}
	}

	/**
	 * Draw a card. It gets and removes the first card from the library and put it in the player 's hand
	 * 
	 * @throws Exception if the library is empty
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void draw() throws Exception {

		try {
			Card card = this.controlledData.getLibrary().removeFirst(this.controlledData);
			this.controlledData.addCardToHand(card);

			if (this.createNetworkEvents) {
				notifyOtherPlayers(new NetworkEvent(DRAW, this.controlledData));
			}
		} catch (NoSuchElementException ex) {
			throw new Exception("The player's library is empty, you can not draw card anymore", ex);
		}

	}

	/**
	 * Draw <code>x</code> cards from the player's library. Each card is send to the player's hand.
	 * 
	 * @param x the number of cards to draw
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void draw(int x) {

		List<Card> cards = this.controlledData.getLibrary().removeFirst(this.controlledData, x);
		cards.forEach(card -> this.controlledData.addCardToHand(card));

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(DRAW_X, this.controlledData, x));
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
	 * @throws RuntimeException if the origin is {@link Origin#BATTLEFIELD} or {@link Origin#OPPONENT_BATTLEFIELD}
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void play(Card card, Origin origin, boolean visible, double x, double y) throws RuntimeException {

		if (origin == Origin.BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot play a card that is already on the battlefield");
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot play a card from the opponent battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setLocation(this.controlledData, x, y);
			card.setVisible(this.controlledData, visible);
			this.controlledData.getBattlefield().add(this.controlledData, card);

			if (this.createNetworkEvents) {
				notifyOtherPlayers(new NetworkEvent(PLAY, this.controlledData, card, origin));
			}
		}
	}

	/**
	 * Exile a card. It means that you take a card from a place and put it in the exile area.
	 * 
	 * @param card the card to put on the exile area
	 * @param origin where the card was played from (hand, library, battlefield, grave yard)
	 * @param visible do you want to put the card on the exile area visible or not
	 * @throws RuntimeException if the origin is {@link Origin#EXILE} or {@link Origin#OPPONENT_BATTLEFIELD}
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void exile(Card card, Origin origin, boolean visible) throws RuntimeException {

		if (origin == Origin.EXILE) {
			throw new IllegalArgumentException("You cannot exile a card that is already exiled");
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot exile a card that belong to your opponent");
		} else {
			checkOriginAndRemove(origin, card);
			card.setVisible(this.controlledData, visible);
			this.controlledData.addCardToExile(card);

			if (this.createNetworkEvents) {
				notifyOtherPlayers(new NetworkEvent(EXILE, this.controlledData, card, origin, visible));
			}
		}
	}

	/**
	 * Send a card to the grave yard. It means that you take a card from a place and put it in the grave yard. The card is
	 * automatically updated with the following values:
	 * <ul>
	 * <li>visible = <code>true</code></li>
	 * </ul>
	 * 
	 * @throws RuntimeException if the origin is {@link Origin#GRAVEYARD} or {@link Origin#OPPONENT_BATTLEFIELD}
	 * @param card the card to put on the grave yard
	 * @param origin where the card was played from (hand, library, battlefield, exile area)
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void destroy(Card card, Origin origin) throws RuntimeException {

		if (origin == Origin.GRAVEYARD) {
			throw new IllegalArgumentException("You cannot destroy a card that is already in the graveyard");
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot destroy a card that belong to your opponent");
		} else {
			checkOriginAndRemove(origin, card);
			card.setVisible(this.controlledData, true);
			this.controlledData.addCardToGraveyard(card);

			if (this.createNetworkEvents) {
				notifyOtherPlayers(new NetworkEvent(DESTROY, this.controlledData, card, origin));
			}
		}
	}

	/**
	 * Send a card back to hand. It means that you take a card from a place and put it in your hand. The card is automatically
	 * updated with the following values:
	 * <ul>
	 * <li>revealed = <code>false</code></li>
	 * <li>visible = <code>true</code></li>
	 * </ul>
	 * 
	 * @param card the card to put on the hand
	 * @param origin where the card was played from (grave yard, library, battlefield, exile area)
	 * @throws RuntimeException if the origin is {@link Origin#HAND} or {@link Origin#OPPONENT_BATTLEFIELD}
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void backToHand(Card card, Origin origin) throws RuntimeException {

		if (origin == Origin.HAND) {
			throw new IllegalArgumentException("You cannot send back to hand a card that is already in the player's hand");
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot put in your hand a card on the opponent's battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(this.controlledData, false);
			card.setVisible(this.controlledData, true);
			this.controlledData.addCardToHand(card);

			if (this.createNetworkEvents) {
				notifyOtherPlayers(new NetworkEvent(BACK_TO_HAND, this.controlledData, card, origin));
			}
		}
	}

	/**
	 * Send a card to the library (on the top). The card is automatically updated with the following values:
	 * <ul>
	 * <li>revealed = <code>false</code></li>
	 * <li>visible = <code>true</code></li>
	 * </ul>
	 * 
	 * @param card the card to send back to library
	 * @param origin where the card was before
	 * @throws RuntimeException if the origin is {@link Origin#LIBRARY} or {@link Origin#OPPONENT_BATTLEFIELD}
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void backToTopOfLibrary(Card card, Origin origin) throws RuntimeException {

		if (origin == Origin.LIBRARY) {
			throw new IllegalArgumentException("You cannot send back to library a card that is already in the player's library");
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot send back to your library a card on the opponent's battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(this.controlledData, false);
			card.setVisible(this.controlledData, true);
			this.controlledData.getLibrary().addOnTop(this.controlledData, card);

			if (this.createNetworkEvents) {
				notifyOtherPlayers(new NetworkEvent(BACK_TO_LIBRARY, this.controlledData, card, origin, Library.TOP));
			}
		}
	}

	/**
	 * Send a card to the library (to the bottom). The card is automatically updated with the following values:
	 * <ul>
	 * <li>revealed = <code>false</code></li>
	 * <li>visible = <code>true</code></li>
	 * </ul>
	 * 
	 * @param card the card to send back to library
	 * @param origin where the card was before
	 * @throws RuntimeException if the origin is {@link Origin#LIBRARY} or {@link Origin#OPPONENT_BATTLEFIELD}
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void backToBottomOfLibrary(Card card, Origin origin) throws RuntimeException {

		if (origin == Origin.LIBRARY) {
			throw new IllegalArgumentException("You cannot send back to library a card that is already in the player's library");
		} else if (origin == Origin.OPPONENT_BATTLEFIELD) {
			throw new IllegalArgumentException("You cannot send back to your library a card on the opponent's battlefield");
		} else {
			checkOriginAndRemove(origin, card);
			card.setRevealed(this.controlledData, false);
			card.setVisible(this.controlledData, true);
			this.controlledData.getLibrary().addToBottom(this.controlledData, card);

			if (this.createNetworkEvents) {
				notifyOtherPlayers(new NetworkEvent(BACK_TO_LIBRARY, this.controlledData, card, origin, Library.BOTTOM));
			}
		}
	}

	/**
	 * Tap or untap a card
	 * 
	 * @param tapped true/false
	 * @param card the card to update
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setTapped(boolean tapped, Card card) {
		
		card.setTapped(this.controlledData, tapped);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent((tapped ? CARD_TAP : CARD_UNTAP), this.controlledData, card));
		}
	}
	
	/**
	 * Tap or untap a list of cards
	 * 
	 * @param tapped true/false
	 * @param cards the card to update
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setTapped(boolean tapped, List<Card> cards) {

		cards.forEach(card -> card.setTapped(this.controlledData, tapped));

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent((tapped ? CARD_TAP : CARD_UNTAP), this.controlledData, cards.toArray()));
		}
	}

	/**
	 * Set the visiblity of a list of cards
	 * 
	 * @param visible true/false
	 * @param card the card to update
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setVisible(boolean visible, Card card) {

		card.setVisible(this.controlledData, visible);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent((visible ? CARD_SHOW : CARD_HIDE), this.controlledData, card));
		}
	}

	/**
	 * Set the visiblity of a card
	 * 
	 * @param visible true/false
	 * @param cards the card to update
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setVisible(boolean visible, List<Card> cards) {

		cards.forEach(card -> card.setVisible(this.controlledData, visible));

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent((visible ? CARD_SHOW : CARD_HIDE), this.controlledData, cards.toArray()));
		}
	}

	/**
	 * Set the visiblity of a list of cards
	 * 
	 * @param revealed true/false
	 * @param card the card to update
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setRevealed(boolean revealed, Card card) {

		card.setRevealed(this.controlledData, revealed);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent((revealed ? CARD_DO_REVEAL : CARD_UNDO_REVEAL), this.controlledData, card));
		}
	}

	/**
	 * Set if a card is revealed from the player's hand or not
	 * 
	 * @param revealed true/false
	 * @param cards the card to update
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setRevealed(boolean revealed, List<Card> cards) {

		cards.forEach(card -> card.setRevealed(this.controlledData, revealed));

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent((revealed ? CARD_DO_REVEAL : CARD_UNDO_REVEAL), this.controlledData, cards.toArray()));
		}
	}

	/**
	 * Update the card location
	 * 
	 * @param x new horizontal coordinate
	 * @param y new vertical coordinate
	 * @param card the card to update
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setLocation(double x, double y, Card card) {

		card.setLocation(this.controlledData, x, y);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(CARD_MOVE, this.controlledData, card));
		}
	}

	/**
	 * Add a counter on the card
	 * 
	 * @param counter the new counter
	 * @param card the card where the counter should be added
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void addCounter(Counter counter, Card card) {

		card.addCounter(this.controlledData, counter);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(CARD_ADD_COUNTER, this.controlledData, counter, card));
		}
	}

	/**
	 * Removes a counter from the card. If the card did not contains the counter, nothing happens
	 * 
	 * @param counter the counter to remove
	 * @param card the card where the counter should be removed
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void removeCounter(Counter counter, Card card) {

		if (card.removeCounter(this.controlledData, counter) && this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(CARD_REMOVE_COUNTER, this.controlledData, counter, card));
		}
	}

	/**
	 * Add <code>associatedCard</code> as a card associated with <code>card</code>.
	 * 
	 * @param associatedCard the card to associate with <code>card</code>
	 * @param card the card where the associated card should be added
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void addAssociatedCard(Card associatedCard, Card card) {

		card.addAssociatedCard(this.controlledData, associatedCard);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(CARD_ADD_COUNTER, this.controlledData, associatedCard, card));
		}
	}

	/**
	 * Removes an associated card from <code>card</code>. If <code>card</code> did not contains <code>associatedCard</code>,
	 * nothing happens
	 * 
	 * @param associatedCard the associated card to remove
	 * @param card the card where the associated card should be removed
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void removeAssociatedCard(Card associatedCard, Card card) {

		if (card.removeAssociatedCard(this.controlledData, associatedCard) && this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(CARD_REMOVE_COUNTER, this.controlledData, associatedCard, card));
		}
	}

	/**
	 * Shuffles the library of the controlled player
	 * 
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void shuffleLibrary() {

		Library library = this.controlledData.getLibrary();
		library.shuffle(this.controlledData);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(SHUFFLE_LIBRARY, this.controlledData, library));
		}
	}

	/**
	 * Clear all the data of the controlled player
	 * 
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void clearGameData() {
		
		// Delete the observers of all the cards
		this.controlledData.getLibrary().getCards().forEach(card -> card.deleteObservers());
		this.controlledData.getHand().forEach(card -> card.deleteObservers());
		this.controlledData.getBattlefield().getCards().forEach(card -> card.deleteObservers());
		this.controlledData.getGraveyard().forEach(card -> card.deleteObservers());
		this.controlledData.getExile().forEach(card -> card.deleteObservers());

		// clear the cards from the player
		this.controlledData.getLibrary().clear(this.controlledData);
		this.controlledData.getBattlefield().clear(this.controlledData);
		this.controlledData.clearExile();
		this.controlledData.clearGraveyard();
		this.controlledData.clearHand();

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(CLEAR_GAME_DATA, this.controlledData));
		}
	}

	/**
	 * Set the number of life counters on the controlled player
	 * 
	 * @param lifeCounters the number of life counters
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setLifeCounters(int lifeCounters) {

		this.controlledData.setLifeCounters(lifeCounters);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(PLAYER_UPDATE_LIFE, this.controlledData, new Integer(lifeCounters)));
		}
	}

	/**
	 * Set the number of poison counters on the controlled player
	 * 
	 * @param poisonCounters the number of poison counters
	 * @see LocalEvent
	 * @see NetworkEvent
	 */
	public void setPoisonCounters(int poisonCounters) {

		this.controlledData.setPoisonCounters(poisonCounters);

		if (this.createNetworkEvents) {
			notifyOtherPlayers(new NetworkEvent(PLAYER_UPDATE_POISON, this.controlledData, new Integer(poisonCounters)));
		}
	}

	/**
	 * Try to remove a card according to the origin. If the card is really removed from the origin collection, then everything is
	 * okay. But otherwise, it means that the origin is wrong because the card is not is the origin collection.
	 * 
	 * @param origin the origin of the card where it should be removed
	 * @param card the card to remove
	 * @throws RuntimeException if the card was not removed from the origin collection
	 * @throws IllegalArgumentException if the origin is {@link Origin#OPPONENT_BATTLEFIELD}, because you can not remove card
	 *         controlled by your opponent !
	 * @see LocalEvent
	 */
	private void checkOriginAndRemove(Origin origin, Card card) throws RuntimeException {

		switch (origin) {
			case BATTLEFIELD:
				if (!this.controlledData.getBattlefield().remove(this.controlledData, card)) {
					throw new RuntimeException("The " + card + " is not on the battlefield");
				}
				break;
			case LIBRARY:
				if (!this.controlledData.getLibrary().remove(this.controlledData, card)) {
					throw new RuntimeException("The " + card + " is not in the library");
				}
				break;
			case HAND:
				if (!this.controlledData.removeCardFromHand(card)) {
					throw new RuntimeException("The " + card + " is not in the hand");
				}
				break;
			case EXILE:
				if (!this.controlledData.removeCardFromExile(card)) {
					throw new RuntimeException("The " + card + " is not in the exile area");
				}
				break;
			case GRAVEYARD:
				if (!this.controlledData.removeCardFromGraveyard(card)) {
					throw new RuntimeException("The " + card + " is not in the graveyard");
				}
				break;
			case OPPONENT_BATTLEFIELD:
			case OPPONENT_EXILE:
			case OPPONENT_GRAVEYARD:
				throw new IllegalArgumentException("You cannot remove a card from the opponent.");
		}
	}

	/**
	 * Send the network event to the network to notify the other player(s) that the local player had perform an action they should
	 * be aware of.
	 * 
	 * @param event a network event
	 * @throws RuntimeException if the game manager is not able to send the event to other player(s)
	 * @see NetworkEvent
	 * @see GameManager
	 */
	private void notifyOtherPlayers(NetworkEvent event) {

		try {
			GameManager.getInstance().send(event);
		} catch (Exception e) {
			throw new RuntimeException("Network issue", e);
		}
	}
}
