package asenka.mtgfree.controllers;


@SuppressWarnings("serial")
public class GameException extends RuntimeException {

	public GameException(String message) {
		super(message);
	}

	public GameException(Exception e) {
		super(e);
	}
}
