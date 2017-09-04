package asenka.mtgfree.model.exceptions;

import asenka.mtgfree.model.MtgCardState;

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
