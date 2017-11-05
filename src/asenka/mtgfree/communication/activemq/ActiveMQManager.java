package asenka.mtgfree.communication.activemq;

import java.util.Set;
import java.util.stream.Collectors;

import javax.jms.JMSException;
import javax.management.RuntimeErrorException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Closeable;
import org.apache.activemq.command.ActiveMQTopic;

import asenka.mtgfree.communication.GameManager;
import asenka.mtgfree.events.NetworkEvent;

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
	 * Initializes the reader and the writer. You still need to call {@link ActiveMQManager#listen()} 
	 * to start receiving the messages
	 * @param gameName
	 */
	public ActiveMQManager(String gameName) {
		
		// TODO Use a preference file to load the broker url
		this.brokerUrl = "tcp://192.168.1.20:61616"; // Adapt this value to your ActiveMQ URL
		
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
	 * @return the game topics in the broker
	 */
	public Set<ActiveMQTopic> getGameTopics() {
		
		Set<ActiveMQTopic> topics = getAvailableTopics(this.brokerUrl);

		Set<ActiveMQTopic> filteredTopics = topics.stream().filter(topic -> {
				try {
					return topic.getTopicName().startsWith("MTGFREE:");
				} catch (JMSException e) {
					return false;
				}
			}).collect(Collectors.toSet());
		return filteredTopics;
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
	 * @return the set of topics in the broker at the specified URL
	 * @see ActiveMQTopic
	 */
	private static Set<ActiveMQTopic> getAvailableTopics(String brokerUrl) {
		
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
		ActiveMQConnection connection = null;
		Set<ActiveMQTopic> topics = null;
		
		try {
			connection = (ActiveMQConnection) factory.createConnection();
			topics = connection.getDestinationSource().getTopics();
			
		} catch(JMSException ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (JMSException e) {
				throw new RuntimeErrorException(new Error(e));
			}
		}
		return topics;
	}
}
