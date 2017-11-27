package sandbox;

import java.io.Serializable;


public class SynchronizationEvent implements Serializable {

	private static final long serialVersionUID = 591871669087955237L;

	private final EventType eventType;
	
	private final Serializable[] args;

	public SynchronizationEvent(EventType eventType, final Serializable... args) {

		this.eventType = eventType;
		this.args = args;
	}
	
	public EventType getEventType() {
		
		return this.eventType;
	}
	
	public Serializable[] getMethodArguments() {
		
		return this.args;
	}
}
