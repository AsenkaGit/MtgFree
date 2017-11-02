package asenka.mtgfree.communication;

import java.io.Closeable;
import java.io.Serializable;


public interface NetworkCommunicator extends Closeable {
	
	public void send(Serializable data) throws Exception;
	
	public void listen();

}
