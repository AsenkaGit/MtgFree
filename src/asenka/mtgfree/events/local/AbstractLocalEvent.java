package asenka.mtgfree.events.local;

import org.apache.log4j.Logger;

/**
 * Abstract class for all the events on the client side only.
 * 
 * @author asenka
 *
 */
public abstract class AbstractLocalEvent {

	/**
	 * The type of eventType: <code>"add", "remove", "set", "clear", "shuffle", ...</code>
	 */
	protected final String eventType;

	/**
	 * The updated property
	 */
	protected final String property;

	/**
	 * The new value. On some events, it can be <code>null</code>.
	 */
	protected final Object value;

	/**
	 * Abstract constructor. Initializes the value and trace the eventType.
	 * 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractLocalEvent#eventType
	 * @see AbstractLocalEvent#property
	 * @see AbstractLocalEvent#value
	 */
	protected AbstractLocalEvent(String eventType, String property, Object value) {

		this.eventType = eventType;
		this.property = property;
		this.value = value;

		Logger.getLogger(this.getClass()).trace(this);
	}

	/**
	 * @return the type of event
	 * @see AbstractEvent#eventType
	 */
	public String getEventType() {

		return this.eventType;
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
	public Object getValue() {

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
		AbstractLocalEvent other = (AbstractLocalEvent) obj;
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
