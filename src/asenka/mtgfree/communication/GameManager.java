package asenka.mtgfree.communication;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import asenka.mtgfree.communication.activemq.ActiveMQManager;
import asenka.mtgfree.controllers.game.PlayerController;
import asenka.mtgfree.events.NetworkEvent;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.GameTable;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Origin;
import asenka.mtgfree.model.game.Player;

import static asenka.mtgfree.events.NetworkEvent.Type.*;

/**
 * A singleton class running on each client when to manage the game. It is this object that make possible the communication of
 * events with the other players.
 * 
 * @author asenka
 * @see GameTable
 * @see Player
 * @see ActiveMQManager
 */
public class GameManager {

	/**
	 * The unique instance of GameManager
	 */
	private static GameManager singleton = null;

	/**
	 * The local game table
	 */
	private GameTable localGameTable;

	/**
	 * The local player
	 */
	private Player localPlayer;

	/**
	 * The broker manager used to communicate with the other player(s)
	 */
	private ActiveMQManager brokerManager;

	/**
	 * Private constructor. The only required data to create a GameManager is the local player. By default, the local game table
	 * is <code>null</code>.
	 * 
	 * @param localPlayer the local player
	 */
	private GameManager(Player localPlayer) {

		this.localPlayer = localPlayer;
		this.localGameTable = null;
	}

	/**
	 * Use this method first to initialize the game manager
	 * 
	 * @param localPlayer
	 * @return the unique instance of game manager
	 * @throws IllegalStateException if the game manager has been already initialized
	 */
	public static GameManager initialize(Player localPlayer) {

		if (singleton == null) {
			singleton = new GameManager(localPlayer);
			return singleton;
		} else {
			throw new IllegalStateException("You can call this method only once");
		}

	}

	/**
	 * The method grant access to the GameManager everywhere and make sure only one instance of this object exist on a JVM
	 * 
	 * @return the unique instance of GameManager
	 * @throws IllegalStateException if the game manager is not initialized (see {@link GameManager#initialize(Player)})
	 */
	public static GameManager getInstance() {

		if (singleton == null) {
			throw new IllegalStateException("The network event manager is not initialized yet");
		}
		return singleton;
	}

	/**
	 * When the game start you should call this method to start the dialog with the other player(s)
	 * 
	 * @param gameTable a game table with at least the local player data ready
	 * @throws Exception
	 */
	public void createGame(GameTable gameTable) throws Exception {

		try {
			this.localGameTable = gameTable;
			this.brokerManager = new ActiveMQManager(gameTable.getName());
			this.brokerManager.listen();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * If you are not the player who created the game table, you have to call this method to join the existing game table
	 * (assuming you know the table name)
	 * 
	 * @param tableName the name of the game table you want to join
	 * @param joiningPlayer the player who wants to join the table
	 * @throws Exception
	 * @see NetworkEvent
	 * @see EventType#REQUEST_JOIN
	 */
	public void joinGame(String tableName, Player joiningPlayer) throws Exception {

		this.brokerManager = new ActiveMQManager(tableName);
		this.brokerManager.listen();

		send(new NetworkEvent(REQUEST_GAMETABLE_DATA, joiningPlayer));
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAvailableTables() {

		List<String> availableTables = new ArrayList<String>();

		// This part get all the topics from the broker,
		this.brokerManager.getGameTopics().forEach(topic -> {
			try {
				availableTables.add(topic.getTopicName().replace(ActiveMQManager.TABLE_NAME_PREFIX, ""));
			} catch (Exception e) {
				Logger.getLogger(this.getClass()).error(e);
			}
		});
		return availableTables;
	}

	/**
	 * Send an event to the other player(s)
	 * 
	 * @param event the event to send
	 * @throws Exception if the message cannot be sent
	 */
	public void send(NetworkEvent event) throws Exception {

		this.brokerManager.send(event);
		Logger.getLogger(this.getClass()).trace(">>>> SENT: " + event);
	}

	/**
	 * The method called to manage the received events from the other player(s)
	 * 
	 * @param event
	 */
	public void manageEvent(NetworkEvent event) {

		Logger.getLogger(this.getClass()).trace(">>> RECEIVED: " + event);

		final NetworkEvent.Type eventType = event.getType();
		final Player otherPlayer = event.getPlayer();
		final Serializable[] parameters = event.getParameters();

		try {

			// If the game table is ready
			if (this.localGameTable != null) {

				// If the player receiving the event is not the one at the origin of the event...
				if (!this.localGameTable.isLocalPlayer(otherPlayer)) {

					// If the table creator receives a join request from another player, it builds and send a
					// mirrored game table
					if (eventType == REQUEST_GAMETABLE_DATA) {

						// Build a mirror game table to send to the new player
						GameTable otherPlayerGameTable = new GameTable(this.localGameTable.getName(), otherPlayer);

						// Add the 'other' player on the local game table and on the game table to send to the otherPlayer
						this.localGameTable.addOtherPlayer(otherPlayer);
						otherPlayerGameTable.addOtherPlayer(this.localPlayer);

						// Send the game table to the broker in another join event
						send(new NetworkEvent(SEND_GAMETABLE_DATA, this.localPlayer, otherPlayerGameTable));

					} else { // For normal type of events (DRAW, PLAY, etc.)

						final PlayerController otherPlayerController = this.localGameTable.getOtherPlayerController(otherPlayer);
						boolean flag = false; // boolean value used for events related to boolean state of a card (e.g. tapped)

						switch (eventType) {
							case DRAW:
								otherPlayerController.draw();
								break;
							case DRAW_X:
								otherPlayerController.draw((Integer) parameters[0]);
								break;
							case SHUFFLE_LIBRARY:
								otherPlayerController.getData().getLibrary().setCards(convertDataArrayToList(parameters));
								break;
							case PLAY: {
								Card card = (Card) parameters[0];
								Origin origin = (Origin) parameters[1];
								Point2D.Double location = card.getLocation();
								otherPlayerController.play(card, origin, card.isVisible(), location.getX(), location.getY());
								break;
							}
							case DESTROY: {
								Card card = (Card) parameters[0];
								Origin origin = (Origin) parameters[1];
								otherPlayerController.destroy(card, origin);
								break;
							}
							case CARD_MOVE: {
								Card card = (Card) parameters[0];
								Point2D.Double location = card.getLocation();
								otherPlayerController.setLocation(location.getX(), location.getY(), card);
								break;
							}
							case CARD_TAP:
								flag = true;
							case CARD_UNTAP:
								if (parameters.length > 1) {
									otherPlayerController.setTapped(flag, GameManager.<Card> convertDataArrayToList(parameters));
								} else {
									otherPlayerController.setTapped(flag, (Card) parameters[0]);
								}
								break;
							case CARD_SHOW:
								flag = true;
							case CARD_HIDE:
								if (parameters.length > 1) {
									otherPlayerController.setVisible(flag, GameManager.<Card> convertDataArrayToList(parameters));
								} else {
									otherPlayerController.setVisible(flag, (Card) parameters[0]);
								}
								break;
							case CARD_DO_REVEAL:
								flag = true;
							case CARD_UNDO_REVEAL:
								if (parameters.length > 1) {
									otherPlayerController.setRevealed(flag, GameManager.<Card> convertDataArrayToList(parameters));
								} else {
									otherPlayerController.setRevealed(flag, (Card) parameters[0]);
								}
								break;
							case PLAYER_LEAVE:
								// TODO 
								break;
							case PLAYER_JOIN:
								// TODO 
								break;
							default: // TODO Finish implementing events management
								throw new RuntimeException(eventType + " is not managed yet by this implementation");
						}
					}
				}
				this.localGameTable.addLog(event);

			} else if (eventType == SEND_GAMETABLE_DATA) {

				// If the game table is not ready and if the event is a data synchronization from
				// table creator, then we can initialize the game table for the joining player.
				this.localGameTable = (GameTable) parameters[0];
				this.localGameTable.setLocalPlayerController(new PlayerController(this.localPlayer, true));

				send(new NetworkEvent(PLAYER_JOIN, this.localPlayer));

			}
		} catch (Exception ex) {
			Logger.getLogger(this.getClass()).error(ex.getMessage(), ex);
		}
	}

	/**
	 * Send a Leave signal to the broker to inform the other players and close the connection with the broker
	 * 
	 * @throws RuntimeException if you try to call this method before the beginning of the game o
	 */
	public void endGame() {

		try {
			send(new NetworkEvent(PLAYER_LEAVE, this.localPlayer));
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error("Unable to send the exit game table signal to broker", e);
			throw new RuntimeException(e);
		} finally {

			if (this.brokerManager != null) {
				this.brokerManager.close();
			} else {
				throw new RuntimeException("Try to close a broker connection that is not initialized yet");
			}
		}
	}

	/**
	 * If the player is the creator of the game, it simply returns the local game table. But if the player is joining the table,
	 * you can call this method AFTER calling <code>joinGame(...)</code>. It will wait 10 secondes until the game table is
	 * available.
	 * 
	 * @return the game table to use during the current game
	 * @throws RuntimeException If the game table is not ready after 10 seconds
	 */
	public GameTable getGameTable() {

		// If the game is already available, it is directly returned
		if (this.localGameTable != null) {
			return this.localGameTable;
		} else {
			// If not ready yet, and if the method joinGame(...) has been called before,
			// we came assume that it will be initialized soon. So we wait up to 10 seconds
			// to have a game table
			int timeout = 0;

			while (this.localGameTable == null && timeout < 10) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				} finally {
					timeout++;
				}
			}

			// If the gameTable is still not available, check if you have call the method joinGame(...)
			// Otherwise, it means a network issue
			if (this.localGameTable == null && timeout == 10) {
				throw new RuntimeException("Unable to get the Game table from table creator");
			} else {
				return this.localGameTable;
			}
		}
	}

	/**
	 * Convert an array of serializable data to a list of serializable data
	 * 
	 * @param <T> The requested type to convert the data. It should be a subclass of {@link Serializable}
	 * @param data the data array
	 * @return a list of the desired type as soon as it is a subclass of serializable
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Serializable> List<T> convertDataArrayToList(Serializable[] data) {

		List<T> list = new ArrayList<T>(data.length);
		Arrays.stream(data).forEach(serializedData -> list.add((T) serializedData));

		return list;
	}
}
