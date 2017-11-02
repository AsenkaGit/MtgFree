package asenka.mtgfree.communication;

import java.io.IOException;

import org.apache.log4j.Logger;

import asenka.mtgfree.communication.activemq.ActiveMQCommunicator;
import asenka.mtgfree.communication.events.NetworkEvent;
import asenka.mtgfree.model.game.GameTable;

/**
 * 
 * @author asenka
 */
public class NetworkEventManager {

	/**
	 * 
	 */
	private static NetworkEventManager singleton;

	/**
	 * 
	 */
	private GameTable gameTable;

	/**
	 * 
	 */
	private NetworkCommunicator communicator;

	/**
	 * 
	 * @return
	 */
	public static NetworkEventManager getInstance() {

		if (singleton == null) {
			singleton = new NetworkEventManager();
		}
		return singleton;
	}

	/**
	 * 
	 */
	private NetworkEventManager() {

		try {
			this.communicator = new ActiveMQCommunicator();
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void send(NetworkEvent event) throws Exception {

		this.communicator.send(event);
	}

	/**
	 * 
	 */
	public void listen() {

		this.communicator.listen();
	}

	/**
	 * 
	 * @param data
	 */
	public void manageEvent(NetworkEvent data) {

		// TODO implement event manager

		System.out.println(data);
	}

	/**
	 * @throws IOException
	 */
	public void closeConnections() throws IOException {

		this.communicator.close();
	}

	/**
	 * @return the gameTable
	 */
	public GameTable getGameTable() {

		return this.gameTable;
	}

	/**
	 * @param gameTable the gameTable to set
	 */
	public void setGameTable(GameTable gameTable) {

		this.gameTable = gameTable;
	}
}
