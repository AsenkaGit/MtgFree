package asenka.mtgfree.communication;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Observer;
import java.util.Set;

import org.apache.log4j.Logger;

import asenka.mtgfree.communication.activemq.ActiveMQManager;
import asenka.mtgfree.controllers.game.PlayerController;
import asenka.mtgfree.events.NetworkEvent;
import asenka.mtgfree.model.game.AbstractGameObject;
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
public final class GameManager {

	/**
	 * The unique instance of GameManager
	 */
	private static GameManager singleton = null;

	/**
	 * The local game table
	 */
	private GameTable localGameTable;

	/**
	 * The local player controller
	 */
	private PlayerController localPlayerController;

	/**
	 * The opponent player controller
	 */
	private PlayerController opponentPlayerController;

	/**
	 * The set of observers where the opponent game data will be displayed. The game manager needs to know what are the observers
	 * used for the opponent to assign them when the opponent model objects arrive
	 */
	private Set<Observer> opponentObservers;

	/**
	 * The broker manager used to communicate with the other player(s)
	 */
	private ActiveMQManager brokerManager;

	/**
	 * Private constructor. The only required data to create a GameManager is the local player. By default, the local game table
	 * is <code>null</code>.
	 * 
	 * @param tableName the name of the table to create or to join
	 * @param localPlayer the local player
	 */
	private GameManager(String tableName, Player localPlayer) {

		this.localGameTable = new GameTable(tableName, localPlayer);
		this.localPlayerController = new PlayerController(localPlayer, true);
		this.opponentObservers = new HashSet<Observer>();
		this.opponentPlayerController = null;
	}

	/**
	 * If the player is the creator of the game, it simply returns the local game table. But if the player is joining the table,
	 * you can call this method AFTER calling <code>joinGame(...)</code>. It will wait 10 secondes until the game table is
	 * available.
	 * 
	 * @return the game table to use during the current game
	 * @throws RuntimeException If the game table is not ready after 10 seconds
	 */
	public GameTable getLocalGameTable() {

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
	 * @return the controller for the local player. This controller can creates NetworkEvent objects. This method should not
	 *         return a <code>null</code> value.
	 */
	public PlayerController getLocalPlayerController() {

		return this.localPlayerController;
	}

	/**
	 * 
	 * @return the controller for the opponent player. This value is <code>null</code> until another player has join the table
	 */
	public PlayerController getOpponentPlayerController() {

		return this.opponentPlayerController;
	}

	/**
	 * @return the set of observers (the views for example) used to display the opponent game object (like the opponent
	 *         battlefield or the opponent graveyard)
	 */
	public Set<Observer> getOpponentObservers() {

		return this.opponentObservers;
	}

	/**
	 * Check the topics on the broker to get the table names.
	 * 
	 * @return the list of game table names available on the network
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
	 * Call this method to start the communication with the broker. After calling this method, other player can join this game.
	 * 
	 * @throws Exception if the connection with the broker cannot be initialized properly
	 */
	public void createGame() throws Exception {

		this.brokerManager = new ActiveMQManager(this.localGameTable.getName());
		this.brokerManager.listen();
	}

	/**
	 * If you are not the player who created the game table, you have to call this method to join the existing game table
	 * (assuming you know the table name)
	 * 
	 * @throws Exception
	 * @see NetworkEvent
	 * @see EventType#REQUEST_JOIN
	 */
	public void joinGame() throws Exception {

		this.brokerManager = new ActiveMQManager(this.localGameTable.getName());
		this.brokerManager.listen();

		send(new NetworkEvent(REQUEST_GAMETABLE_DATA, this.localGameTable.getLocalPlayer()));
	}

	/**
	 * Send an event to the other player(s)
	 * 
	 * @param event the event to send
	 * @throws Exception if the message cannot be sent
	 */
	public void send(NetworkEvent event) throws Exception {

		this.brokerManager.send(event);
		Logger.getLogger(this.getClass()).info(">>> SENT: " + event);
	}

	/**
	 * The method called to manage the received events from the other player(s)
	 * 
	 * @param event the network event to manage
	 * @throws Exception if the event cannot be managed properly
	 * @see NetworkEvent
	 */
	public void manageEvent(NetworkEvent event) throws Exception {

		Logger.getLogger(this.getClass()).info(">>>> RECEIVED: " + event);

		final NetworkEvent.Type eventType = event.getType();
		final Player otherPlayer = event.getPlayer();
		final Serializable[] parameters = event.getParameters();

		// IF the player at the origin of the event is NOT the local player THEN...
		if (!this.localGameTable.isLocalPlayer(otherPlayer)) {
			
			boolean flag = false; // boolean value used for events related to boolean state of a card (e.g. tapped)

			switch (eventType) {
				case REQUEST_GAMETABLE_DATA:
					this.localGameTable.setOpponentPlayer(otherPlayer);
					this.opponentPlayerController = new PlayerController(otherPlayer, false);
					this.opponentObservers.forEach(observer -> {
						otherPlayer.addObserver(observer);
						otherPlayer.getLibrary().addObserver(observer);
						otherPlayer.getBattlefield().addObserver(observer);
					});
					send(new NetworkEvent(SEND_GAMETABLE_DATA, this.localGameTable.getLocalPlayer()));
					break;
				case SEND_GAMETABLE_DATA:
					this.localGameTable.setOpponentPlayer(otherPlayer);
					this.opponentPlayerController = new PlayerController(otherPlayer, false);
					this.opponentObservers.forEach(observer -> {
						otherPlayer.addObserver(observer);
						otherPlayer.getLibrary().addObserver(observer);
						otherPlayer.getBattlefield().addObserver(observer);
					});
					send(new NetworkEvent(PLAYER_JOIN, this.localGameTable.getLocalPlayer()));
					break;
				case PLAYER_JOIN:
					// TODO
					break;	
				case PLAYER_LEAVE:
					this.opponentPlayerController.clearGameData();
					break;
				case DRAW:
					this.opponentPlayerController.draw();
					break;
				case DRAW_X:
					this.opponentPlayerController.draw((Integer) parameters[0]);
					break;
				case SHUFFLE_LIBRARY:
					Library reorderedLibrary = (Library) parameters[0];
					this.opponentPlayerController.getData().getLibrary().setCards(reorderedLibrary.getCards());
					break;
				case PLAY: {
					Card card = (Card) parameters[0];
					Origin origin = (Origin) parameters[1];
					Point2D.Double location = card.getLocation();
					this.opponentPlayerController.play(card, origin, card.isVisible(), location.getX(), location.getY());
					break;
				}
				case DESTROY: {
					Card card = (Card) parameters[0];
					Origin origin = (Origin) parameters[1];
					this.opponentPlayerController.destroy(card, origin);
					break;
				}
				case EXILE: {
					Card card = (Card) parameters[0];
					Origin origin = (Origin) parameters[1];
					Boolean visible = (Boolean) parameters[2];
					this.opponentPlayerController.exile(card, origin, visible.booleanValue());
					break;
				}
				case CARD_MOVE: {
					Card card = (Card) parameters[0];
					Point2D.Double location = card.getLocation();
					this.opponentPlayerController.setLocation(location.getX(), location.getY(), card);
					break;
				}
				case CARD_TAP:
					flag = true;
				case CARD_UNTAP:
					if (parameters.length > 1) {
						this.opponentPlayerController.setTapped(flag, GameManager.<Card> convertDataArrayToList(parameters));
					} else {
						this.opponentPlayerController.setTapped(flag, (Card) parameters[0]);
					}
					break;
				case CARD_SHOW:
					flag = true;
				case CARD_HIDE:
					if (parameters.length > 1) {
						this.opponentPlayerController.setVisible(flag, GameManager.<Card> convertDataArrayToList(parameters));
					} else {
						this.opponentPlayerController.setVisible(flag, (Card) parameters[0]);
					}
					break;
				case CARD_DO_REVEAL:
					flag = true;
				case CARD_UNDO_REVEAL:
					if (parameters.length > 1) {
						this.opponentPlayerController.setRevealed(flag, GameManager.<Card> convertDataArrayToList(parameters));
					} else {
						this.opponentPlayerController.setRevealed(flag, (Card) parameters[0]);
					}
					break;
				case BACK_TO_LIBRARY: {
					Card card = (Card) parameters[0];
					Origin origin = (Origin) parameters[1];
					int index = ((Integer) parameters[2]).intValue();

					switch (index) {
						case Library.TOP:
							this.opponentPlayerController.backToTopOfLibrary(card, origin);
							break;
						case Library.BOTTOM:
							this.opponentPlayerController.backToBottomOfLibrary(card, origin);
							break;
						default:
							throw new RuntimeException("Unmanaged library index");
					}
					break;
				}
				case BACK_TO_HAND: {
					Card card = (Card) parameters[0];
					Origin origin = (Origin) parameters[1];
					this.opponentPlayerController.backToHand(card, origin);
					break;
				}
				default: // TODO Finish implementing events management
					throw new RuntimeException(eventType + " is not managed yet by this implementation");
			}
		}
		this.localGameTable.addLog(event);
	}

	/**
	 * Send a Leave signal to the broker to inform the other players and close the connection with the broker
	 * 
	 * @throws RuntimeException if you try to call this method before the beginning of the game o
	 */
	public void endGame() {

		try {
			send(new NetworkEvent(PLAYER_LEAVE, this.localGameTable.getLocalPlayer()));
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
	 * Use this method first to initialize the game manager
	 * 
	 * @param tableName the name of the table to create or to join
	 * @param localPlayer the local player
	 * @return the unique instance of game manager
	 * @throws IllegalStateException if the game manager has been already initialized
	 */
	public static GameManager initialize(String tableName, Player localPlayer) {

		if (singleton == null) {
			singleton = new GameManager(tableName, localPlayer);
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
			throw new IllegalStateException("The network event manager is not initialized yet. Call initialize() first.");
		}
		return singleton;
	}

	/**
	 * Convert an array of serializable data to a list of serializable data
	 * 
	 * @param <T> The requested type to convert the data. It should be a subclass of {@link AbstractGameObject}
	 * @param data the data array
	 * @return a list of the desired type as soon as it is a subclass of serializable
	 */
	@SuppressWarnings("unchecked")
	private static <T extends AbstractGameObject> List<T> convertDataArrayToList(Serializable[] data) {

		List<T> list = new ArrayList<T>(data.length);
		Arrays.stream(data).forEach(serializedData -> list.add((T) serializedData));

		return list;
	}
}
