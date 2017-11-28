package asenka.mtgfree.controllers.communication;

import java.io.Serializable;


public class SynchronizationEvent implements Serializable {

	private static final long serialVersionUID = 591871669087955237L;

	private final EventType eventType;
	
	private final Object[] parameters;

	public SynchronizationEvent(EventType eventType, final Serializable... parameters) {

		this.eventType = eventType;
		this.parameters = parameters;
	}
	
	public EventType getEventType() {
		
		return this.eventType;
	}
	
	public Object[] getParameters() {
		
		return this.parameters;
	}
}
