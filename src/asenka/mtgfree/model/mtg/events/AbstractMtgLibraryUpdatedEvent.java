package asenka.mtgfree.model.mtg.events;

import java.io.Serializable;

import asenka.mtgfree.model.mtg.MtgLibrary;

/**
 * Abtract event for the events related to the MtgLibrary.
 * 
 * @author asenka
 * @see MtgLibrary
 */
public abstract class AbstractMtgLibraryUpdatedEvent extends AbstractEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6920796680876727814L;

	/**
	 * The updated library
	 */
	protected final MtgLibrary updatedLibrary;

	/**
	 * Constructor without event message
	 * 
	 * @param updatedLibrary
	 */
	public AbstractMtgLibraryUpdatedEvent(MtgLibrary updatedLibrary) {

		super();
		this.updatedLibrary = updatedLibrary;
	}

	/**
	 * Constructor with event message
	 * 
	 * @param eventMessage the message associated with this event
	 * @param updatedLibrary the updated library
	 */
	public AbstractMtgLibraryUpdatedEvent(final String eventMessage, MtgLibrary updatedLibrary) {

		super(eventMessage);
		this.updatedLibrary = updatedLibrary;
	}

	/**
	 * @return the updated library related to this event
	 */
	public MtgLibrary getUpdatedLibrary() {

		return this.updatedLibrary;
	}
}
