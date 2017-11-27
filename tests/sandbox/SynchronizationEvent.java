package sandbox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.EventObject;


public class SynchronizationEvent extends EventObject {
	
	private static final long serialVersionUID = 4521443940518755566L;

	private final EventType eventType;
	
	private final Serializable[] args;

	public SynchronizationEvent(EventType eventType, final Player player, final Serializable... args) {

		super(player);
		this.eventType = eventType;
		this.args = args;
	}
	
	public EventType getEventType() {
		
		return this.eventType;
	}
	
	public Serializable[] getMethodArguments() {
		
		return this.args;
	}
	
	public void writeObject(ObjectOutputStream out) throws IOException {
		
		out.writeObject((Player) super.source);
		out.defaultWriteObject();
	}
	
	public void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		
		super.source = (Player) in.readObject();
		in.defaultReadObject();
	}
}
