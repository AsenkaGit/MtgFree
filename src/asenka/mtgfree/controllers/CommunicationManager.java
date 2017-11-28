package asenka.mtgfree.controllers;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import asenka.mtgfree.controllers.communication.CommunicationException;
import asenka.mtgfree.controllers.communication.EventType;
import asenka.mtgfree.controllers.communication.SynchronizationEvent;
import asenka.mtgfree.controllers.communication.activemq.ActiveMQManager;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;



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

	public void send(EventType eventType, final Player player, final Serializable... parameters)
		throws CommunicationException, IllegalStateException {

		if (this.brokerManager != null) {

			// Create a new table merging the player with the parameters
			final Serializable[] parametersToSend = new Serializable[parameters.length + 1];
			parametersToSend[0] = player;
			System.arraycopy(parameters, 0, parametersToSend, 1, parameters.length);

			this.brokerManager.send(new SynchronizationEvent(eventType, parametersToSend));
		} else {
			throw new IllegalStateException("The broker manager is not started yet.");
		}

	}

	public void receive(final SynchronizationEvent event) throws CommunicationException {

		Object[] parameters = event.getParameters();

		if (parameters.length > 0 && parameters[0] instanceof Player) {

			final Player localPlayer = this.gameController.getGameTable().getLocalPlayer();

			// The events from the local player are not managed
			if (!localPlayer.equals(parameters[0])) {

				try {
					EventType eventType = event.getEventType();
				
					if (eventType == EventType.PLAYER_JOIN) {
						CardsManager.getInstance().addCardsFromPlayer((Player) parameters[0]);
					} else {
						replaceSerializedObjectsByLocalObjects(parameters);
					}
					final Method method = getGameControllerMethod(eventType);
					
					method.invoke(this.gameController, parameters);

				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
					throw new CommunicationException(e);
				}
			}
		} else {
			throw new CommunicationException(parameters.length > 0 ? 
				("Unexpected first parameter type: " + parameters[0].getClass() + " instead of " + Player.class) :
				("The parameters array is empty."));
		}
	}

	private void replaceSerializedObjectsByLocalObjects(Object[] parameters) throws CommunicationException {

		for (int i = 0; i < parameters.length; i++) {

			if (parameters[i] instanceof Card) {
				parameters[i] = CardsManager.getInstance().getLocalCard((Card) parameters[i]);
			} else if (parameters[i] instanceof Player) {
				final GameTable gameTable = this.gameController.getGameTable();

				if (gameTable.getOtherPlayer().equals((Player) parameters[i])) {
					parameters[i] = gameTable.getOtherPlayer();
				} else {
					throw new CommunicationException("Unexpected player: " + parameters[i]);
				}
			}
		}
	}

	private static Method getGameControllerMethod(EventType eventType) throws NoSuchMethodException, SecurityException {

		String methodName = eventType.getMethodName();
		Class<?>[] parameterTypes = eventType.getParameterTypes();

		return GameController.class.getDeclaredMethod(methodName, parameterTypes);
	}
}
