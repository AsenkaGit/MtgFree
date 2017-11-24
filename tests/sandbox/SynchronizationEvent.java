package sandbox;

import java.io.Serializable;
import java.util.EventObject;


public class SynchronizationEvent extends EventObject {
	
	private static final long serialVersionUID = 4521443940518755566L;

	private final EventType eventType;
	
	private final Serializable[] args;

	public SynchronizationEvent(Object source, EventType eventType, Serializable... args) {

		super(source);
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
