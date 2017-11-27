package sandbox;

import java.io.Serializable;
import java.lang.reflect.Method;

import sandbox.activemq.ActiveMQManager;
import sandbox.activemq.CommunicationException;

public class CommunicationManager {

	private final GameController gameController;

	private ActiveMQManager brokerManager;

	public CommunicationManager(GameController gameController) {

		this.gameController = gameController;
		this.brokerManager = null;
	}

	public void createGame() throws CommunicationException, IllegalStateException {

		if (this.brokerManager == null) {
			this.brokerManager = new ActiveMQManager(this.gameController.getGameTable().getName(), this);
			this.brokerManager.listen();
		} else {
			throw new IllegalStateException("The broker manager is already started.");
		}
	}

	public void joinGame() throws CommunicationException, IllegalStateException {

		if (this.brokerManager == null) {

			final GameTable gameTable = this.gameController.getGameTable();

			this.brokerManager = new ActiveMQManager(gameTable.getName(), this);
			this.brokerManager.listen();

			send(EventType.PLAYER_JOIN, gameTable.getLocalPlayer());
		} else {
			throw new IllegalStateException("The broker manager is already started.");
		}
	}

	public void send(EventType eventType, final Player player, final Serializable... args) throws CommunicationException, IllegalStateException {

		if (this.brokerManager != null) {
			
			Serializable[] argumentsToSend = new Serializable[args.length + 1];
			argumentsToSend[0] = player;
			System.arraycopy(args, 0, argumentsToSend, 1, args.length);
			
			this.brokerManager.send(new SynchronizationEvent(eventType, argumentsToSend));
		} else {
			throw new IllegalStateException("The broker manager is not started yet.");
		}

	}

	public void receive(final SynchronizationEvent event) throws IllegalStateException {

		if (this.brokerManager != null) {
			
			
			

		} else {
			throw new IllegalStateException("The broker manager is not started yet.");
		}
	}

	
	private Method getMethod(EventType eventType) {
		
		String methodName = eventType.getMethodName();
		
//		Method gameControllerMethod = this.gameController.getClass().getDeclaredMethod(methodName);
//		
		return null;
	}
	
}
