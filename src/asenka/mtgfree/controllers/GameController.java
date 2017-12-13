package asenka.mtgfree.controllers;

import java.util.List;

import asenka.mtgfree.controllers.communication.EventType;
import asenka.mtgfree.controllers.communication.CommunicationException;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.Counter;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameController {

	public static final int TOP = 0;

	public static final int BOTTOM = -1;

	public enum Context {
		BATTLEFIELD, HAND, LIBRARY, EXILE, GRAVEYARD;
	}

	private final GameTable gameTable;

	private final CommunicationManager communicationManager;

	public GameController(final GameTable gameTable) {

		this.gameTable = gameTable;
		this.communicationManager = new CommunicationManager(this);
	}

	public GameTable getGameTable() {

		return this.gameTable;
	}

	public void createGame() throws GameException {

		try {
			this.communicationManager.createGame();
		} catch (IllegalStateException | CommunicationException e) {
			throw new GameException(e);
		}
	}

	public void joinGame() throws GameException {

		try {
			this.communicationManager.joinGame();
		} catch (IllegalStateException | CommunicationException e) {
			throw new GameException(e);
		}
	}

	synchronized void addOpponent(final Player opponentPlayer) throws GameException {

		if (this.gameTable.getOtherPlayer() == null) {
			this.gameTable.setOtherPlayer(opponentPlayer);
		} else {
			throw new GameException("The table " + this.gameTable + " has already an opponent.");
		}
	}

	public void exitGame() throws GameException {

		try {
			this.communicationManager.exitGame();
		} catch (IllegalStateException | CommunicationException e) {
			throw new GameException(e);
		}
	}

	synchronized void removeOpponent(final Player opponentPlayer) throws GameException {

		if (this.gameTable.getOtherPlayer() != null) {
			this.gameTable.setOtherPlayer(null);
		} else {
			throw new GameException("The player " + opponentPlayer + " tries to exit the table " + this.gameTable
				+ " but he is not an opponent on this table.");
		}
	}

	public void shuffle() throws GameException {

		final Player localPlayer = this.gameTable.getLocalPlayer();
		this.gameTable.getLocalPlayer().shuffleLibrary();

		try {
			this.communicationManager.send(EventType.SHUFFLE, localPlayer);
		} catch (IllegalStateException | CommunicationException e) {
			throw new GameException(e);
		}
	}

	synchronized void updateOpponentLibrary(final Player player) throws GameException {

		if (player.equals(this.gameTable.getOtherPlayer())) {
			player.setLibrary(FXCollections.<Card>observableList(player.getLibrary()));
		} else {
			throw new GameException("Unexpected player for library update: " + player);
		}
	}

	public Card draw() throws GameException {

		return draw(this.gameTable.getLocalPlayer());
	}

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

	public void changeCardContext(final Card card, final Context origin, final Context destination, int destinationIndex, boolean hidden)
		throws GameException {

		changeCardContext(this.gameTable.getLocalPlayer(), card, origin, destination, destinationIndex, hidden);
	}

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

	public void setTapped(final Card card, boolean tapped) throws GameException {

		setTapped(this.gameTable.getLocalPlayer(), card, tapped);
	}

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

	public void setVisible(final Card card, boolean visible) throws GameException {

		setVisible(this.gameTable.getLocalPlayer(), card, visible);
	}

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

	// synchronized void setSelected(final Player player, final Card card, boolean selected) throws GameException {
	//
	// card.setSelected(selected);
	//
	// if (player.equals(this.gameTable.getLocalPlayer())) {
	// try {
	// this.communicationManager.send(EventType.SET_SELECTED, player, card, Boolean.valueOf(selected));
	// } catch (IllegalStateException | CommunicationException e) {
	// throw new GameException(e);
	// }
	// }
	// }

	public void setLocation(final Card card, double x, double y) throws GameException {

		setLocation(this.gameTable.getLocalPlayer(), card, x, y);
	}

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

	public void addCounter(final Card card, final Counter counter) throws GameException {

		addCounter(this.gameTable.getLocalPlayer(), card, counter);
	}

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

	public void removeCounter(final Card card, final Counter counter) throws GameException {

		removeCounter(this.gameTable.getLocalPlayer(), card, counter);
	}

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
	
	public void setPlayerLife(final int life) {
		
		setPlayerLife(this.gameTable.getLocalPlayer(), life);
	}
	
	synchronized void setPlayerLife(final Player player, final int life) {
		
		player.setLife(life);
		
		if (player.equals(this.gameTable.getLocalPlayer())) {
			try {
				this.communicationManager.send(EventType.SET_LIFE, player, Integer.valueOf(life));
			} catch (IllegalStateException | CommunicationException e) {
				throw new GameException(e);
			}
		}
	}
	
	public void setPlayerPoison(final int poison) {
		
		setPlayerPoison(this.gameTable.getLocalPlayer(), poison);
	}
	
	synchronized void setPlayerPoison(final Player player, final int poison) {
		
		player.setPoison(poison);
		
		if (player.equals(this.gameTable.getLocalPlayer())) {
			try {
				this.communicationManager.send(EventType.SET_POISON, player, Integer.valueOf(poison));
			} catch (IllegalStateException | CommunicationException e) {
				throw new GameException(e);
			}
		}
	}
	
	public void setSelectedCards(Card... cards) {
		
		this.gameTable.getSelectedCards().forEach(card -> card.setSelected(false));
		this.gameTable.getSelectedCards().clear();
		this.gameTable.getSelectedCards().addAll(cards);
		this.gameTable.getSelectedCards().forEach(card -> card.setSelected(true));
	}

	public static ObservableList<Card> getContextList(final Context origin, final Player player) throws GameException {

		switch (origin) {
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
				throw new GameException("Unmanaged origin: " + origin);
		}
	}
}
