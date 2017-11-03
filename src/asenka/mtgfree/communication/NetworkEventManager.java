package asenka.mtgfree.communication;

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
	 * The broker manager.
	 */
	private ActiveMQManager brokerManager;

	/**
	 * The method grant access to the NetworkEventManager everywhere and make sure only one instance of this object exist on a JVM
	 * 
	 * @return the unique instance of NetworkEventManager
	 */
	public static NetworkEventManager getInstance() {

		if (singleton == null) {
			singleton = new NetworkEventManager();
		}
		return singleton;
	}

	/**
	 * When the game start you should call this method to start the dialog with the other player(s)
	 * 
	 * @param gameTable a game table with at least the local player data ready
	 */
	public void startGame(GameTable gameTable) {

		try {
			this.gameTable = gameTable;
			this.brokerManager = new ActiveMQManager(gameTable.getTableName());
			this.brokerManager.listen();
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
	}

	/**
	 * The method called to manage the received events from the other player(s)
	 * 
	 * @param event
	 */
	public void manageEvent(NetworkEvent event) {

		// TODO implement event manager

		String eventType = event.getEventType();
		Player player = event.getPlayer();

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

	// The default constructor is privatized to prevent its usage and make sure we have a single instance of NetworkEventManager
	private NetworkEventManager() {

		// EMPTY
	}
}
