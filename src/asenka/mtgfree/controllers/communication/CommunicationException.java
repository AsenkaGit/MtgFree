package asenka.mtgfree.controllers.communication;

public class CommunicationException extends Exception {

	private static final long serialVersionUID = -347771991029739807L;

	public CommunicationException(Exception e) {

		super(e);
	}

	public CommunicationException(String message) {

		super(message);
	}

}
