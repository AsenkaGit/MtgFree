package sandbox;


public enum EventType {

	ADD_OPPONENT("addOpponent"),
	REMOVE_OPPONENT("removeOpponent"),
	CHANGE_CARD_CONTEXT("changeCardContext"),
	SET_VISIBLE("setVisible"),
	SET_TAPPED("setTapped"),
	SET_SELECTED("setSelected"),
	SET_LOCATION("setLocation"),
	SHUFFLE("updateOpponentLibrary"), 
	ADD_COUNTER("addCounter"),
	REMOVE_COUNTER("removeCounter"),
	;
	
	private String methodName;
	
	private EventType(String methodName) {
		this.methodName = methodName;
	}
	
	public String getMethodName() {
		
		return this.methodName;
	}
}
