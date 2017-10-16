package asenka.mtgfree.model.events;

import java.io.Serializable;

public class LibraryEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3725354157603997948L;

	public LibraryEvent(String event, String property, Serializable value) {
		super(event, property, value);
	}

}
