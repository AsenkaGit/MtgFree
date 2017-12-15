package asenka.mtgfree.controllers.communication;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import asenka.mtgfree.controllers.CommunicationManager;
import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Player;


/**
 * The type of a event that may occur during a game.
 * 
 * @author asenka
 * @see GameController
 */
public enum EventType {

	/**
	 * Event occuring when a card is moved from one context to another (draw, play, destroy, etc...). The associated method of
	 * GameController is <strong>changeCardContext(...)</strong>.
	 * 
	 * @see Context
	 */
	CHANGE_CARD_CONTEXT("changeCardContext"),

	/**
	 * Event occuring when a card visibility changes. The associated method of GameController is <strong>setVisible(...)</strong>.
	 */
	SET_VISIBLE("setVisible"),

	/**
	 * Event occuring when a card tapping changes. The associated method of GameController is <strong>setTapped(...)</strong>.
	 */
	SET_TAPPED("setTapped"),

	/**
	 * Event occuring when a card location changes. The associated method of GameController is <strong>setLocation(...)</strong>.
	 */
	SET_LOCATION("setLocation"),

	/**
	 * Event occuring the local player shuffles its library. In that case, the player sends the new library to its opponent so
	 * that the game table on the opponent side is update with a new library order. The associated method of GameController is
	 * <strong>updateOpponentLibrary(...)</strong>.
	 */
	SHUFFLE("updateOpponentLibrary"),

	/**
	 * Event occuring when a player add a counter on a card. The associated method of GameController is
	 * <strong>addCounter(...)</strong>.
	 */
	ADD_COUNTER("addCounter"),

	/**
	 * Event occuring when a player removes a counter on a card. The associated method of GameController is
	 * <strong>removeCounter(...)</strong>.
	 */
	REMOVE_COUNTER("removeCounter"),

	/**
	 * Event occuring when a player join the game table. The associated method of GameController is
	 * <strong>addOpponent(...)</strong>.
	 */
	PLAYER_JOIN("addOpponent"),

	/**
	 * Event occuring in response to to PLAYER_JOIN event to send the data of the other player to the joining player. The
	 * associated method of GameController is <strong>addOpponent(...)</strong>.
	 */
	TABLE_CREATOR_RESPONSE("addOpponent"),

	/**
	 * Event occuring when a player leaves the game table. The associated method of GameController is
	 * <strong>removeOpponent(...)</strong>.
	 */
	PLAYER_EXIT("removeOpponent"),

	/**
	 * Event occuring when a player update its life points. The associated method of GameController is
	 * <strong>setPlayerLife(...)</strong>.
	 */
	SET_LIFE("setPlayerLife"),

	/**
	 * Event occuring when a player update its poison points. The associated method of GameController is
	 * <strong>setPlayerPoison(...)</strong>.
	 */
	SET_POISON("setPlayerPoison"),;

	/**
	 * The name of the method from the {@link GameController}. This attribute is necessary to easily find the method to call when the
	 * communication manager receives an event from an opponent player.
	 * @see CommunicationManager
	 * @see GameController
	 */
	private final String methodName;

	/**
	 * The list of parameters of the method. This attribute is necessary to easily find the method to call when the
	 * communication manager receives an event from an opponent player.
	 * @see CommunicationManager
	 * @see GameController
	 */
	private final Class<?>[] parameterTypes;

	/**
	 * Build a new Event type enum constant.
	 * 
	 * @param methodName {@link EventType#methodName}
	 */
	private EventType(String methodName) {

		this.methodName = methodName;
		this.parameterTypes = getGameControllerMethodParameterTypes(methodName);
	}

	public final String getMethodName() {

		return this.methodName;
	}

	public final Class<?>[] getParameterTypes() {

		return this.parameterTypes;
	}

	/**
	 * Look for the parameters of a method from the {@link GameController} class.
	 * 
	 * @param methodName the name of a method of {@link GameController}
	 * @return an array of parameters for a method
	 * @throws RuntimeErrorException if the parameters list cannot be found. If it happens, it means that the GameController
	 *         method has been updated and do not match anymore the implementation of this method.
	 */
	private static final Class<?>[] getGameControllerMethodParameterTypes(String methodName) throws RuntimeErrorException {

		final Method[] gameControllerMethods = GameController.class.getDeclaredMethods();

		List<Method> filteredList = Arrays.stream(gameControllerMethods).filter(method -> method.getName().equals(methodName))
			.filter(method -> {
				for (Class<?> parameterType : method.getParameterTypes()) {
					if (parameterType.equals(Player.class)) {
						return true;
					}
				}
				return false;
			}).collect(Collectors.toList());

		if (filteredList.size() == 1) {
			return filteredList.get(0).getParameterTypes();
		} else {
			throw new RuntimeErrorException(new Error("Unable to build the enumeration EventType"));
		}
	}
}
