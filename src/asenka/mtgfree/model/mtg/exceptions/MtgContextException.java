package asenka.mtgfree.model.mtg.exceptions;

import asenka.mtgfree.model.mtg.mtgcard.state.MtgCardState;

/**
 * 
 * @author Asenka
 */
@SuppressWarnings("serial")
public class MtgContextException extends RuntimeException {

	/**
	 * 
	 * @param mtgCardState
	 *            the current state
	 * @param details
	 *            the details about the context issue
	 */
	public MtgContextException(MtgCardState mtgCardState, String details) {

		super(details + " [current state is " + mtgCardState + "]");
	}

}
