package asenka.mtgfree.model.events;

import java.io.Serializable;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.game.Card;

/**
 * 
 * @author asenka
 *
 */
public abstract class AbstractEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4640418331069086081L;

	/**
	 * 
	 */
	protected final String event;

	/**
	 * 
	 */
	protected final String property;

	/**
	 * 
	 */
	protected final Serializable value;

	/**
	 * @param event
	 * @param property
	 * @param value
	 */
	public AbstractEvent(String event, String property, Serializable value) {

		super();
		this.event = event;
		this.property = property;
		this.value = value;
		
		Logger.getLogger(this.getClass()).trace(this);
	}

	/**
	 * @return
	 */
	public String getEvent() {

		return event;
	}

	/**
	 * @return
	 */
	public String getProperty() {

		return property;
	}

	/**
	 * @return
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
		AbstractEvent other = (AbstractEvent) obj;
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
