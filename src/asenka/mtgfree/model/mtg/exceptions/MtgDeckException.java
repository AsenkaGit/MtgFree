package asenka.mtgfree.model.mtg.exceptions;

/**
 * 
 * @author asenka
 */
@SuppressWarnings("serial")
public class MtgDeckException extends Exception {

	/**
	 * @param message
	 */
	public MtgDeckException(String message) {

		super(message);
	}

	/**
	 * @param cause
	 */
	public MtgDeckException(Throwable cause) {

		super(cause);
	}
}
