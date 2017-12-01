package asenka.mtgfree.controllers.communication;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.model.Player;

public enum EventType {

	CHANGE_CARD_CONTEXT("changeCardContext"), 
	SET_VISIBLE("setVisible"), 
	SET_TAPPED("setTapped"), 
	SET_LOCATION("setLocation"), 
	SHUFFLE("updateOpponentLibrary"), 
	ADD_COUNTER("addCounter"), 
	REMOVE_COUNTER("removeCounter"), 
	PLAYER_JOIN("addOpponent"), 
	TABLE_CREATOR_RESPONSE("addOpponent"), 
	PLAYER_EXIT("removeOpponent"), 
	
	;

	private String methodName;

	private Class<?>[] parameterTypes;

	private EventType(String methodName) {

		this.methodName = methodName;
		this.parameterTypes = getGameControllerMethodParameterTypes(methodName);
	}

	public String getMethodName() {

		return this.methodName;
	}

	public Class<?>[] getParameterTypes() {

		return this.parameterTypes;
	}

	private static final Class<?>[] getGameControllerMethodParameterTypes(String methodName) throws RuntimeErrorException {

		final Method[] gameControllerMethods = GameController.class.getDeclaredMethods();

		List<Method> filteredList = Arrays.stream(gameControllerMethods)
			.filter(method -> method.getName().equals(methodName))
			.filter(method -> {
				for(Class<?> parameterType : method.getParameterTypes()) {
					if(parameterType.equals(Player.class)) {
						return true;
					}
				}
				return false;
			})
			.collect(Collectors.toList());
		
		if(filteredList.size() == 1) {
			return filteredList.get(0).getParameterTypes();
		} else {
			throw new RuntimeErrorException(new Error("Unable to build the enumeration EventType"));
		}
	}
}
