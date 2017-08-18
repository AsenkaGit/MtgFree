package asenka.mtgfree.model.exceptions;

import asenka.mtgfree.model.MtgCardState;

/**
 * 
 * @author Asenka
 */
public class MtgContextException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param mtgCardState the current state 
	 * @param details the details about the context issue
	 */
	public MtgContextException(MtgCardState mtgCardState, String details) {
		super(details + " [current state is " + mtgCardState + "]");
	}

}
