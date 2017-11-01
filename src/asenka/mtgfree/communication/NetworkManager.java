package asenka.mtgfree.communication;


import org.apache.log4j.Logger;

import asenka.mtgfree.model.events.NetworkEvent;
import asenka.mtgfree.model.game.GameTable;

public class NetworkManager {

	private static NetworkManager singleton;
	
	private final NetworkNotifier notifier;
	
	private NetworkReceiver receiver;
	
	public static void createInstance(GameTable gameTable) {
		
		singleton = new NetworkManager(gameTable);
	}
	
	public static NetworkManager getInstance() {
		
		if (singleton == null) {
			throw new RuntimeException("NetworkManager was not initialized. Use createInstance(...) before calling getInstance()");
		}
		return singleton;
	}
	
	private NetworkManager(GameTable gameTable) {
		
		notifier = new NetworkNotifier();
		receiver = new NetworkReceiver(gameTable);
	}
	
	public void send(NetworkEvent event) throws Exception {
		
		notifier.sendEvent(event);
	}
	
	public void closeConnections() {
		Logger.getLogger(this.getClass()).info("CLOSE BROKER CONNECTIONS");
		notifier.close();
		receiver.close();
	}
	
	
}
