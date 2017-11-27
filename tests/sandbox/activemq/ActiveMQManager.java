package sandbox.activemq;

import java.util.Set;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.Closeable;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;

import asenka.mtgfree.communication.GameManager;
import asenka.mtgfree.events.NetworkEvent;
import sandbox.CommunicationManager;
import sandbox.SynchronizationEvent;

/**
 * Object used by the NetworkEventManager to used the ActiveMQ broker to send and receive messages
 * 
 * @author asenka
 * @see TopicWriter
 * @see TopicReader
 * @see GameManager
 */
public class ActiveMQManager implements Closeable {

	/**
	 * 
	 */
	public static final String TABLE_NAME_PREFIX = "MTGFREE:Topic:";

	/**
	 * The writer used to publish message in the ActiveMQ broker
	 */
	private TopicWriter writer;

	/**
	 * The reader used to receive messages from the ActiveMQ broker. It is launched in a specific thread
	 */
	private TopicReader reader;

	/**
	 * The url to the broker of messages
	 */
	private String brokerUrl;

	/**
	 * Initializes the reader and the writer. You still need to call {@link ActiveMQManager#listen()} to start receiving the
	 * messages
	 * 
	 * @param tableName the name of the game table
	 * @param communicationManager
	 * @throws Exception if the connection with the broker cannot be initialized properly
	 */
	public ActiveMQManager(String tableName, final CommunicationManager communicationManager) throws CommunicationException {

		// TODO Use a preference file to load the broker url
		// this.brokerUrl = "tcp://192.168.1.20:61616"; // Adapt this value to your ActiveMQ URL
		this.brokerUrl = "tcp://localhost:61616"; // Adapt this value to your ActiveMQ URL

		this.writer = new TopicWriter(this.brokerUrl, TABLE_NAME_PREFIX + tableName);
		this.reader = new TopicReader(this.brokerUrl, TABLE_NAME_PREFIX + tableName, communicationManager);
	}

	/**
	 * Send a message to the broker
	 * 
	 * @param data the network event to send to the broker
	 * @throws CommunicationException if a problem occurs while publishing data to the broker
	 */
	public void send(SynchronizationEvent data) throws CommunicationException {

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

	/**
	 * Returns the topics used for each game table where the players exchange data (not the technical topics automatically created
	 * by ActiveMQ)
	 * 
	 * @return the topics from the broker where the name start with the game table prefix
	 * @throws CommunicationException 
	 * @see ActiveMQManager#TABLE_NAME_PREFIX
	 */
	public Set<ActiveMQTopic> getGameTopics() throws CommunicationException {

		Set<ActiveMQTopic> topics = getAvailableTopics();

		// Filter the topics to get only the topics related to a game table
		Set<ActiveMQTopic> filteredTopics = topics.stream().filter(topic -> {
			try {
				return topic.getTopicName().startsWith(TABLE_NAME_PREFIX);
			} catch (JMSException e) {
				Logger.getLogger(this.getClass()).error("Error when filtering the topics from broker : ", e);
				return false;
			}
		}).collect(Collectors.toSet());

		return filteredTopics;
	}

	/**
	 * Find the current topics on the broker
	 * 
	 * @return a set of topics currently available from the current connection with the broker. <code>null</code> if the
	 *         connection is not ready yet
	 * @throws CommunicationException
	 */
	private Set<ActiveMQTopic> getAvailableTopics() throws CommunicationException {

		Set<ActiveMQTopic> topics = null;
		ActiveMQConnection currentConnection = (ActiveMQConnection) this.writer.connection;

		if (currentConnection != null) {

			try {
				topics = currentConnection.getDestinationSource().getTopics();
			} catch (JMSException ex) {
				throw new CommunicationException(ex);
			}
		}
		return topics;
	}
}
