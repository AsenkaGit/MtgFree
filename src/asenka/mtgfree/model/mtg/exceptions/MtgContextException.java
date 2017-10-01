package asenka.mtgfree.model.mtg.exceptions;

import asenka.mtgfree.model.mtg.mtgcard.MtgCardState;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;

/**
 * Error raised when the state of a card is not valid according to the context where
 * the card id 
 * @author asenka
 * @see MtgContext
 * @see MtgCardState
 */
@SuppressWarnings("serial")
public class MtgContextException extends RuntimeException {

	
	/**
	 * @param cause root cause
	 */
	public MtgContextException(Throwable cause) {

		super(cause);
	}

	/**
	 * @param mtgCardState the current state
	 * @param details the details about the context issue
	 */
	public MtgContextException(MtgCardState mtgCardState, String details) {

		super(details + " [current state is " + mtgCardState + "]");
	}
}
