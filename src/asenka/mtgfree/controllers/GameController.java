package asenka.mtgfree.controllers;

import java.util.List;

import asenka.mtgfree.controllers.communication.EventType;
import asenka.mtgfree.controllers.communication.SynchronizationEvent;
import asenka.mtgfree.controllers.communication.CommunicationException;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.Counter;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The controller used to perform all the actions during a game.
 * 
 * @author asenka
 * @see GameTable
 * @see CommunicationManager
 */
public class GameController {

	/**
	 * The constant used to moves a card at the top of a list (index 0)
	 */
	public static final int TOP = 0;

	/**
	 * The constant used to moves a card at the bottom of a list
	 */
	public static final int BOTTOM = -1;

	/**
	 * The context of a card. It refers to the game area where the card is during the game : the hand, the library, the
	 * battlefield, etc...
	 * 
	 * @author asenka
	 * @see Player
	 */
	public enum Context {
		BATTLEFIELD, HAND, LIBRARY, EXILE, GRAVEYARD;
	}

	/**
	 * The game table managed by this controller.
	 */
	private final GameTable gameTable;

	/**
	 * The communication manager used to synchronize the game event between players during a game.
	 */
	private final CommunicationManager communicationManager;

	/**
	 * Build a game controller with a game table.
	 * 
	 * @param gameTable the game table managed
	 */
	public GameController(final GameTable gameTable) {

		this.gameTable = gameTable;
		this.communicationManager = new CommunicationManager(this);
	}

	/**
	 * @return the table managed by this controller.
	 */
	public GameTable getGameTable() {

		return this.gameTable;
	}

	/**
	 * Create a new game table.
	 * @throws GameException
	 */
	public void createGame() throws GameException {

		try {
			this.communicationManager.createGame();
		} catch (IllegalStateException | CommunicationException e) {
			throw new GameException(e);
		}
	}

	/**
	 * Join a existing game table.
	 * @throws GameException
	 */
	public void joinGame() throws GameException {

		try {
			this.communicationManager.joinGame();
		} catch (IllegalStateException | CommunicationException e) {
			throw new GameException(e);
		}
	}

	/**
	 * Add an opponent to the local game table.
	 * @param opponentPlayer
	 * @throws GameException
	 */
	synchronized void addOpponent(final Player opponentPlayer) throws GameException {

		if (this.gameTable.getOtherPlayer() == null) {
			this.gameTable.setOtherPlayer(opponentPlayer);
		} else {
			throw new GameException("The table " + this.gameTable + " has already an opponent.");
		}
	}

	/**
	 * Exit the game table.
	 * @throws GameException
	 */
	public void exitGame() throws GameException {

		try {
			this.communicationManager.exitGame();
		} catch (IllegalStateException | CommunicationException e) {
			throw new GameException(e);
		}
	}

	/**
	 * Removes the opponent on the local game table.
	 * @param opponentPlayer
	 * @throws GameException
	 */
	synchronized void removeOpponent(final Player opponentPlayer) throws GameException {

		if (this.gameTable.getOtherPlayer() != null) {
			this.gameTable.setOtherPlayer(null);
		} else {
			throw new GameException("The player " + opponentPlayer + " tries to exit the table " + this.gameTable
				+ " but he is not an opponent on this table.");
		}
	}

	/**
	 * Shuffle the library of the local player.
	 * @throws GameException
	 */
	public void shuffle() throws GameException {

		final Player localPlayer = this.gameTable.getLocalPlayer();
		FXCollections.shuffle(localPlayer.getLibrary());

		try {
			this.communicationManager.send(EventType.SHUFFLE, localPlayer);
		} catch (IllegalStateException | CommunicationException e) {
			throw new GameException(e);
		}
	}

	/**
	 * Update the library of the opponent
	 * @param opponent the opponent player
	 * @throws GameException
	 */
	synchronized void updateOpponentLibrary(final Player opponent) throws GameException {

		if (opponent.equals(this.gameTable.getOtherPlayer())) {
			opponent.setLibrary(FXCollections.<Card> observableList(opponent.getLibrary()));
		} else {
			throw new GameException("Unexpected player for library update: " + opponent);
		}
	}

	/**
	 * Make the local player draw a card.
	 * @return the card drawn
	 * @throws GameException
	 */
	public Card draw() throws GameException {

		return draw(this.gameTable.getLocalPlayer());
	}

	/**
	 * Make the specified player draw a card.
	 * @param player
	 * @return
	 * @throws GameException
	 */
	synchronized Card draw(final Player player) throws GameException {

		final List<Card> library = getContextList(Context.LIBRARY, player);
		final Card card = library.size() > 0 ? library.get(TOP) : null;

		if (card != null) {
			changeCardContext(player, card, Context.LIBRARY, Context.HAND, TOP, true);
			return card;
		} else {
			throw new GameException("Unable to draw a card.");
		}
	}

	/**
	 * Change the context of a card or move a card inside the same context for the local player.
	 * @param card the card to moves
	 * @param origin the origin context
	 * @param destination the destination context
	 * @param destinationIndex the destination index in the destination context
	 * @param hidden is the card visible or not in the destination context
	 * @throws GameException
	 */
	public void changeCardContext(final Card card, final Context origin, final Context destination, int destinationIndex, boolean hidden)
		throws GameException {

		changeCardContext(this.gameTable.getLocalPlayer(), card, origin, destination, destinationIndex, hidden);
	}

	/**
	 * Change the context of a card or move a card inside the same context for the specified player. if the player is the local player,
	 * an event is sent to the opponent to synchronize the game tables.
	 * @param player the player 
	 * @param card the card to moves
	 * @param origin the origin context
	 * @param destination the destination context
	 * @param destinationIndex the destination index in the destination context
	 * @param hidden is the card visible or not in the destination context
	 * @throws GameException
	 */
	synchronized void changeCardContext(final Player player, final Card card, final Context origin, final Context destination,
		int destinationIndex, boolean hidden) throws GameException {

		final List<Card> originList = getContextList(origin, player);
		final List<Card> destinationList = getContextList(destination, player);

		if (originList.remove(card)) {

			card.setVisible(!hidden);

			if (destinationIndex == BOTTOM) {
				destinationList.add(card);
			} else {
				destinationList.add(destinationIndex, card);
			}

			if (player.equals(this.gameTable.getLocalPlayer())) {
				try {
					this.communicationManager.send(EventType.CHANGE_CARD_CONTEXT, player, card, origin, destination,
						Integer.valueOf(destinationIndex), Boolean.valueOf(hidden));
				} catch (IllegalStateException | CommunicationException e) {
					throw new GameException(e);
				}
			}
		} else {
			throw new GameException(
				"Unable to move the " + card + " from  " + origin + " to " + destination + " (" + destinationIndex + ")");
		}
	}

	/**
	 * 
	 * @param card
	 * @param tapped
	 * @throws GameException
	 */
	public void setTapped(final Card card, boolean tapped) throws GameException {

		setTapped(this.gameTable.getLocalPlayer(), card, tapped);
	}

	/**
	 * 
	 * @param player
	 * @param card
	 * @param tapped
	 * @throws GameException
	 */
	synchronized void setTapped(final Player player, final Card card, boolean tapped) throws GameException {

		card.setTapped(tapped);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			try {
				this.communicationManager.send(EventType.SET_TAPPED, player, card, Boolean.valueOf(tapped));
			} catch (IllegalStateException | CommunicationException e) {
				throw new GameException(e);
			}
		}
	}

	/**
	 * 
	 * @param card
	 * @param visible
	 * @throws GameException
	 */
	public void setVisible(final Card card, boolean visible) throws GameException {

		setVisible(this.gameTable.getLocalPlayer(), card, visible);
	}

	/**
	 * 
	 * @param player
	 * @param card
	 * @param visible
	 * @throws GameException
	 */
	synchronized void setVisible(final Player player, final Card card, boolean visible) throws GameException {

		if (visible != card.isVisible()) {

			card.setVisible(visible);

			if (player.equals(this.gameTable.getLocalPlayer())) {
				try {
					this.communicationManager.send(EventType.SET_VISIBLE, player, card, Boolean.valueOf(visible));
				} catch (IllegalStateException | CommunicationException e) {
					throw new GameException(e);
				}
			}
		}
	}

	/**
	 * 
	 * @param card
	 * @param x
	 * @param y
	 * @throws GameException
	 */
	public void setLocation(final Card card, double x, double y) throws GameException {

		setLocation(this.gameTable.getLocalPlayer(), card, x, y);
	}

	/**
	 * 
	 * @param player
	 * @param card
	 * @param x
	 * @param y
	 * @throws GameException
	 */
	synchronized void setLocation(final Player player, final Card card, double x, double y) throws GameException {

		card.setLocation(x, y);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			try {
				this.communicationManager.send(EventType.SET_LOCATION, player, card, Double.valueOf(x), Double.valueOf(y));
			} catch (IllegalStateException | CommunicationException e) {
				throw new GameException(e);
			}
		}
	}

	/**
	 * 
	 * @param card
	 * @param counter
	 * @throws GameException
	 */
	public void addCounter(final Card card, final Counter counter) throws GameException {

		addCounter(this.gameTable.getLocalPlayer(), card, counter);
	}

	/**
	 * 
	 * @param player
	 * @param card
	 * @param counter
	 * @throws GameException
	 */
	synchronized void addCounter(final Player player, final Card card, final Counter counter) throws GameException {

		card.getCounters().add(counter);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			try {
				this.communicationManager.send(EventType.ADD_COUNTER, player, card, counter);
			} catch (IllegalStateException | CommunicationException e) {
				throw new GameException(e);
			}
		}
	}

	/**
	 * Removes a counter on a card belonging to the local player.
	 * 
	 * @param card
	 * @param counter
	 * @throws GameException
	 */
	public void removeCounter(final Card card, final Counter counter) throws GameException {

		removeCounter(this.gameTable.getLocalPlayer(), card, counter);
	}

	/**
	 * Removes a counter on a card belonging to the specified player. f the player is the local player, then a
	 * {@link SynchronizationEvent} is sent to the opponent.
	 * 
	 * @param player
	 * @param card
	 * @param counter
	 * @throws GameException
	 */
	synchronized void removeCounter(final Player player, final Card card, final Counter counter) throws GameException {

		card.getCounters().remove(counter);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			try {
				this.communicationManager.send(EventType.REMOVE_COUNTER, player, card, counter);
			} catch (IllegalStateException | CommunicationException e) {
				throw new GameException(e);
			}
		}
	}

	/**
	 * Set the life points of the local player
	 * 
	 * @param life
	 * @throws GameException
	 */
	public void setPlayerLife(final int life) throws GameException {

		setPlayerLife(this.gameTable.getLocalPlayer(), life);
	}

	/**
	 * Set the life points of a player. If the player is the local player, then a {@link SynchronizationEvent} is sent to the
	 * opponent.
	 * 
	 * @param player
	 * @param life
	 * @throws GameException
	 */
	synchronized void setPlayerLife(final Player player, final int life) throws GameException {

		player.setLife(life);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			try {
				this.communicationManager.send(EventType.SET_LIFE, player, Integer.valueOf(life));
			} catch (IllegalStateException | CommunicationException e) {
				throw new GameException(e);
			}
		}
	}

	/**
	 * Set the local player poison counters.
	 * 
	 * @param poison the number of poison counters
	 * @throws GameException
	 */
	public void setPlayerPoison(final int poison) throws GameException {

		setPlayerPoison(this.gameTable.getLocalPlayer(), poison);
	}

	/**
	 * Set the poison counter of the specified player. If the player is the local player, then a {@link SynchronizationEvent} is
	 * sent to the opponent.
	 * 
	 * @param player the player to update
	 * @param poison the number of poison counters of the player
	 * @throws GameException
	 */
	synchronized void setPlayerPoison(final Player player, final int poison) throws GameException {

		player.setPoison(poison);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			try {
				this.communicationManager.send(EventType.SET_POISON, player, Integer.valueOf(poison));
			} catch (IllegalStateException | CommunicationException e) {
				throw new GameException(e);
			}
		}
	}

	/**
	 * Set the selected cards.
	 * 
	 * @param cards an array of cards (varargs)
	 */
	public void setSelectedCards(Card... cards) {

		this.gameTable.getSelectedCards().clear();
		this.gameTable.getSelectedCards().addAll(cards);
	}

	/**
	 * Find the list of cards from a player according to the specified context
	 * 
	 * @param context the required context
	 * @param player the player
	 * @return an observable list of cards
	 * @throws GameException
	 * @see Context
	 */
	public static ObservableList<Card> getContextList(final Context context, final Player player) throws GameException {

		switch (context) {
			case LIBRARY:
				return player.getLibrary();
			case HAND:
				return player.getHand();
			case BATTLEFIELD:
				return player.getBattlefield();
			case GRAVEYARD:
				return player.getGraveyard();
			case EXILE:
				return player.getExile();
			default:
				throw new GameException("Unmanaged origin: " + context);
		}
	}
}
