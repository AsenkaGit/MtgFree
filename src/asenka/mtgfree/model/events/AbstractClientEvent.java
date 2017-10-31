package asenka.mtgfree.model.events;

import java.io.Serializable;

import org.apache.log4j.Logger;


/**
 * Abstract class for all the events on the client side only. 
 * 
 * @author asenka
 *
 */
public abstract class AbstractClientEvent extends AbstractEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -2156337006502788122L;

	/**
	 * The updated property
	 */
	protected final String property;

	/**
	 * The new value. On some events, it can be <code>null</code>. It must be a implementation
	 * of {@link Serializable} to make sure that the event can be serialized.
	 */
	protected final Serializable value;

	/**
	 * Abstract constructor. Initializes the value and trace the event.
	 * 
	 * @param event the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#event
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	protected AbstractClientEvent(String event, String property, Serializable value) {

		super(event);
		this.property = property;
		this.value = value;
		
		Logger.getLogger(this.getClass()).trace(this);
	}

	/**
	 * @return the type of event
	 */
	public String getEvent() {

		return event;
	}

	/** 
	 * @return the updated property
	 */
	public String getProperty() {

		return property;
	}

	/**
	 * @return the new value. It can be <code>null</code>
	 */
	public Serializable getValue() {

		return value;
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [" + event + ", " + property + ", " + value + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractClientEvent other = (AbstractClientEvent) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
