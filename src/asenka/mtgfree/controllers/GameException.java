package asenka.mtgfree.controllers;


public class GameException extends RuntimeException {

	private static final long serialVersionUID = 4110259207836802415L;

	public GameException(String message) {
		super(message);
	}

	public GameException(Exception e) {
		super(e);
	}

	public GameException(Throwable exception) {

		// TODO Auto-generated constructor stub
	}

}
