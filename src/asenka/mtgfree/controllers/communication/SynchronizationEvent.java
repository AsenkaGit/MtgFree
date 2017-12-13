package asenka.mtgfree.controllers.communication;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;


public class SynchronizationEvent implements Serializable {

	private static final long serialVersionUID = -8650464715987942583L;

	private final EventType eventType;
	
	private final Object[] parameters;
	
	private final Date time;

	public SynchronizationEvent(EventType eventType, final Serializable... parameters) {

		this.eventType = eventType;
		this.parameters = parameters;
		this.time = new Date();
	}
	
	public EventType getEventType() {
		
		return this.eventType;
	}
	
	public Object[] getParameters() {
		
		return this.parameters;
	}
	
	public Date getTime() {
		
		return this.time;
	}
	
	@Override
	public String toString() {
		
		return "SynchronizationEvent [" + this.eventType + ", " + Arrays.toString(this.parameters) + "]";
	}
}
