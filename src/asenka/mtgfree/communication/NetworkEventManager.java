package asenka.mtgfree.communication;

import java.lang.IllegalStateException;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import asenka.mtgfree.communication.activemq.ActiveMQManager;
import asenka.mtgfree.controlers.game.Controller.Origin;
import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.events.network.NetworkEvent;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.GameTable;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;

/**
 * 
 * @author asenka
 */
public class NetworkEventManager {

	/**
	 * The unique instance of NetworkEventManager
	 */
	private static NetworkEventManager singleton = null;

	/**
	 * The game table
	 */
	private GameTable gameTable;

	/**
	 * 
	 */
	private Player localPlayer;

	/**
	 * The broker manager.
	 */
	private ActiveMQManager brokerManager;

	/**
	 * 
	 * @param localPlayer
	 */
	private NetworkEventManager(Player localPlayer) {

		this.localPlayer = localPlayer;
	}

	public static NetworkEventManager createInstance(Player localPlayer) {

		if (singleton == null) {
			singleton = new NetworkEventManager(localPlayer);
			return singleton;
		} else {
			throw new IllegalStateException("You can call this method only once");
		}

	}

	/**
	 * The method grant access to the NetworkEventManager everywhere and make sure only one instance of this object exist on a JVM
	 * 
	 * @return the unique instance of NetworkEventManager
	 */
	public static NetworkEventManager getInstance() {

		if (singleton == null) {
			throw new IllegalStateException("The network event manager is not initialized yet");
		}
		return singleton;
	}

	/**
	 * When the game start you should call this method to start the dialog with the other player(s)
	 * 
	 * @param gameTable a game table with at least the local player data ready
	 */
	public void createGame(GameTable gameTable) {

		try {
			this.gameTable = gameTable;
			this.brokerManager = new ActiveMQManager(gameTable.getTableName());
			this.brokerManager.listen();
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public void joinGame(String tableName, Player player) {

		try {
			this.brokerManager = new ActiveMQManager(tableName);
			this.brokerManager.listen();

			send(new NetworkEvent("join", player));

		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Send an event to the other player(s)
	 * 
	 * @param event the event to send
	 * @throws Exception if the message cannot be sent
	 */
	public void send(NetworkEvent event) throws Exception {

		this.brokerManager.send(event);
		Logger.getLogger(this.getClass()).info(">>>> SENT: " + event);
	}

	/**
	 * The method called to manage the received events from the other player(s)
	 * 
	 * @param event
	 */
	public void manageEvent(NetworkEvent event) {

		String eventType = event.getEventType();
		Player player = event.getPlayer();

		Logger.getLogger(this.getClass()).info(">>> RECEIVED: " + event);

		try {

			if ("join".equals(eventType)) {
				managePlayerJoin(event);
				
			} else if (this.gameTable != null) {
				this.gameTable.addLog(event);
				
				if (!this.gameTable.isLocalPlayer(player)) {

					final PlayerController otherPlayerController = this.gameTable.getOtherPlayerController(player);

					switch (eventType) {
						case "draw":
							otherPlayerController.draw((Integer) event.getData()[0]);
							break;
						case "shuffleLibrary":
							otherPlayerController.getData().setLibrary((Library) event.getData()[0]);
							break;
						case "play":
							Card card = (Card) event.getData()[0];
							Origin origin = (Origin) event.getData()[1];
							otherPlayerController.play(card, origin, card.isVisible(), card.getLocation().getX(), card.getLocation().getY());
							break;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(this.getClass()).error(ex.getMessage(), ex);
		}	
	}

	/**
	 * Close the connection with the broker
	 * 
	 * @throws RuntimeException if you try to call this method before the beginning of the game
	 */
	public void endGame() {

		if (this.brokerManager != null) {
			this.brokerManager.close();
		} else {
			throw new RuntimeException("Try to close a broker connection that is not initialized yet");
		}
	}
	
	
	public GameTable getGameTable() {
		
		if(this.gameTable != null) {
			
			return this.gameTable;
		} else {
			
			int timeout = 0;
			
			while(this.gameTable == null || timeout == 10) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeErrorException(new Error(e));
				} finally {
					timeout++;
				}
			}
			
			if(this.gameTable == null && timeout == 10) {
				throw new RuntimeException("Unable to get the Game table from table creator");
			} else {
				return this.gameTable;
			}
		}
	}

	/**
	 * 
	 * @param event an event triggered when a new player wants to join a table
	 * @throws Exception
	 */
	private void managePlayerJoin(NetworkEvent event) throws Exception {

		if ("join".equals(event.getEventType())) {

			// Get the event data
			Player player = event.getPlayer();
			GameTable receivedGameTable = event.getData().length > 0 ? ((GameTable) (event.getData()[0])) : null;

			if (!this.localPlayer.equals(player) && receivedGameTable == null) {
				
				// FIRST CASE : the table creator receive a join request
				
				this.gameTable.addOtherPlayer(player);
				
				// Build a mirror game table to send to the new player
				GameTable newPlayerGameTable = new GameTable(this.gameTable.getTableName(), player);

				// The table create is added as an other player on the game table
				newPlayerGameTable.addOtherPlayer(localPlayer);

				// Send the game table to the broker in another join event
				send(new NetworkEvent("join", player, newPlayerGameTable));

				
			} else if (this.gameTable == null && receivedGameTable != null) {

				// SECOND CASE : the player taht made the request to join the table
				// receives the game table from the creator
				
				this.gameTable = receivedGameTable;
			}
		} else {
			throw new IllegalArgumentException(event + " is not a join event.");
		}
	}
}
