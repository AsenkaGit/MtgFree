package asenka.mtgfree.model.events;

import java.io.Serializable;

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
}
