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
	 * of {@link Serializable} to make sure that the eventType can be serialized.
	 */
	protected final Serializable value;

	/**
	 * Abstract constructor. Initializes the value and trace the eventType.
	 * 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractClientEvent#property
	 * @see AbstractClientEvent#value
	 */
	protected AbstractClientEvent(String eventType, String property, Serializable value) {

		super(eventType);
		this.property = property;
		this.value = value;
		
		Logger.getLogger(this.getClass()).trace(this);
	}

	/**
	 * @return the type of eventType
	 */
	public String getEvent() {

		return eventType;
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

		return this.getClass().getSimpleName() + " [" + eventType + ", " + property + ", " + value + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
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
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equals(other.eventType))
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
