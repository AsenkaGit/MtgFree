package sandbox;

import java.util.List;

import javafx.collections.FXCollections;

public class GameController {

	public static final int TOP = 0;

	public static final int BOTTOM = -1;

	public enum Context {
		BATTLEFIELD, HAND, LIBRARY, EXILE, GRAVEYARD;
	}

	private final GameTable gameTable;

	public GameController(final GameTable gameTable) {

		this.gameTable = gameTable;
	}

	public void addOpponent(final Player opponentPlayer) throws GameException {

		if (this.gameTable.getOtherPlayer() == null) {
			this.gameTable.setOtherPlayer(opponentPlayer);
		} else {
			throw new GameException("The table " + this.gameTable + " has already an opponent.");
		}
	}

	public void removeOpponent() {

		this.gameTable.setOtherPlayer(null);
	}

	public void shuffle() {

		this.gameTable.getLocalPlayer().shuffleLibrary();

		// TODO create network event to notify to update the opponent
	}

	public void updateOpponentLibrary(final List<Card> library) {

		final Player opponent = this.gameTable.getOtherPlayer();
		opponent.setLibrary(FXCollections.observableList(library));
	}

	public void draw() throws GameException {

		draw(this.gameTable.getLocalPlayer());
	}

	public void draw(final Player player) {

		final List<Card> library = getContextList(Context.LIBRARY, player);
		final Card card = library.remove(0);

		if (card != null) {
			changeCardContext(player, card, Context.LIBRARY, Context.HAND, TOP);
		} else {
			throw new GameException("Unable to draw a card.");
		}
	}

	public void changeCardContext(final Card card, final Context origin, final Context destination, int destinationIndex)
		throws GameException {

		changeCardContext(this.gameTable.getLocalPlayer(), card, origin, destination, destinationIndex);
	}

	public void changeCardContext(final Player player, final Card card, final Context origin, final Context destination,
		int destinationIndex) throws GameException {

		final List<Card> originList = getContextList(origin, player);
		final List<Card> destinationList = getContextList(destination, player);

		if (originList.remove(card)) {

			if (destinationIndex == BOTTOM) {
				destinationList.add(card);
			} else {
				destinationList.add(destinationIndex, card);
			}

			if (player.equals(this.gameTable.getLocalPlayer())) {
				// TODO Send the Network message
				new SynchronizationEvent(EventType.CHANGE_CARD_CONTEXT, player, card, origin, destination,
					Integer.valueOf(destinationIndex));

			}
		} else {
			throw new GameException(
				"Unable to move the " + card + " from  " + origin + " to " + destination + " (" + destinationIndex + ")");
		}
	}

	public void setTapped(final Card card, boolean tapped) {

		setTapped(this.gameTable.getLocalPlayer(), card, tapped);
	}

	public void setTapped(final Player player, final Card card, boolean tapped) {

		card.setTapped(tapped);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			// TODO Send the Network message
			new SynchronizationEvent(EventType.SET_TAPPED, player, card, Boolean.valueOf(tapped));
		}
	}

	public void setVisible(final Card card, boolean visible) {

		setVisible(this.gameTable.getLocalPlayer(), card, visible);
	}

	public void setVisible(final Player player, final Card card, boolean visible) {

		if (visible != card.isVisible()) {

			card.setVisible(visible);

			if (player.equals(this.gameTable.getLocalPlayer())) {
				// TODO Send the Network message
				new SynchronizationEvent(EventType.SET_VISIBLE, player, card, Boolean.valueOf(visible));
			}
		}
	}

	public void setSelected(final Card card, boolean selected) {

		setSelected(this.gameTable.getLocalPlayer(), card, selected);
	}

	public void setSelected(final Player player, final Card card, boolean selected) {

		card.setSelected(selected);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			// TODO Send the Network message
			new SynchronizationEvent(EventType.SET_SELECTED, player, card, Boolean.valueOf(selected));
		}
	}

	public void setLocation(final Card card, double x, double y) {

		setLocation(this.gameTable.getLocalPlayer(), card, x, y);
	}

	public void setLocation(final Player player, final Card card, double x, double y) {

		card.setLocation(x, y);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			// TODO Send the Network message
			new SynchronizationEvent(EventType.SET_LOCATION, player, card, Double.valueOf(x), Double.valueOf(y));
		}
	}

	public void addCounter(final Card card, final Counter counter) {

		addCounter(this.gameTable.getLocalPlayer(), card, counter);
	}

	public void addCounter(final Player player, final Card card, final Counter counter) {

		card.getCounters().add(counter);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			// TODO Send the Network message
			new SynchronizationEvent(EventType.ADD_COUNTER, player, card, counter);
		}
	}

	public void removeCounter(final Card card, final Counter counter) {

		removeCounter(this.gameTable.getLocalPlayer(), card, counter);
	}

	public void removeCounter(final Player player, final Card card, final Counter counter) {

		card.getCounters().remove(counter);

		if (player.equals(this.gameTable.getLocalPlayer())) {
			// TODO Send the Network message
			new SynchronizationEvent(EventType.REMOVE_COUNTER, player, card, counter);
		}
	}

	private static List<Card> getContextList(final Context origin, final Player player) throws GameException {

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
