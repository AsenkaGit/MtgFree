package asenka.mtgfree.communication.activemq;

import org.apache.activemq.Closeable;

import asenka.mtgfree.communication.NetworkEventManager;
import asenka.mtgfree.communication.events.NetworkEvent;

/**
 * Object used by the NetworkEventManager to used the ActiveMQ broker to send and receive messages
 * 
 * @author asenka
 * @see TopicWriter
 * @see TopicReader
 * @see NetworkEventManager
 */
public class ActiveMQManager implements Closeable {
	
	/**
	 * The writer used to publish message in the ActiveMQ broker
	 */
	private TopicWriter writer;
	
	/**
	 * The reader used to receive messages from the ActiveMQ broker. It is launched in a specific thread
	 */
	private TopicReader reader;
	
	/**
	 * Initializes the reader and the writer. You still need to call {@link ActiveMQManager#listen()} 
	 * to start receiving the messages
	 * @param gameName
	 */
	public ActiveMQManager(String gameName) {
		
		// TODO Use a preference file to load the broker url
		String brokerUrl = "tcp://192.168.1.20:81616";
		
		this.writer = new TopicWriter(brokerUrl, "MTGFREE:Topic:" + gameName);
		this.reader = new TopicReader(brokerUrl, "MTGFREE:Topic:" + gameName);
	}

	/**
	 * Send a message to the broker
	 * @param data the network event to send to the broker
	 * @throws Exception
	 */
	public void send(NetworkEvent data) throws Exception {

		this.writer.publish(data);
	}

	/**
	 * Start to read the data from the broker
	 */
	public void listen() {

		this.reader.listen();
	}

	/**
	 * Close the reader and writer JMS objects
	 */
	@Override
	public void close() {
	
		this.reader.close();
		this.writer.close();
	}
}
