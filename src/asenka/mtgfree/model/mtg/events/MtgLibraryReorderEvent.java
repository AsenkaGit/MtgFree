package asenka.mtgfree.model.mtg.events;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.MtgLibrary;

/**
 * This event is triggered when a library's order is modified (shuffled or cards switched)
 * 
 * @author asenka
 * @see MtgLibrary
 */
public class MtgLibraryReorderEvent extends AbstractMtgLibraryUpdatedEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1525300200564797739L;
	
	/**
	 * Log4j logger use to trace events.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(MtgLibraryReorderEvent.class);

	/**
	 * @param updatedLibrary
	 */
	public MtgLibraryReorderEvent(MtgLibrary updatedLibrary) {

		super("The library has been reordered", updatedLibrary);
		LOGGER.trace(super.eventMessage);
	}

}
