package asenka.mtgfree.communication.activemq;

import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Closeable;

/**
 * Abstract class that gather the connection, session and topic data.
 * 
 * @author asenka
 * @see TopicReader
 * @see TopicWriter
 */
abstract class AbstractActiveMQCommunicator implements Closeable {

//	/**
//	 * The authorized packages. ActiveMQ requires to defines the authorized packages when using serialized object in messages.
//	 * 
//	 * @see ActiveMQConnectionFactory#setTrustedPackages(java.util.List)
//	 */
//	// TODO Update the list of trusted package for serialization
//	private static final String[] TRUSTED_PACKAGES = new String[] { "asenka.mtgfree.events", "asenka.mtgfree.model.game",
//			"asenka.mtgfree.model.data", "asenka.mtgfree.controllers.game", "java.util", "java.awt.geom", "java.lang" };

	/**
	 * The connection with the broker
	 */
	protected TopicConnection connection = null;

	/**
	 * The current session with the broker
	 */
	protected TopicSession session = null;

	/**
	 * The topic where the messages are sent and read
	 */
	protected Topic topic = null;

	/**
	 * Connection factory used to create topic connection with a broker
	 */
	protected TopicConnectionFactory factory;

	/**
	 * The string with the broker URL
	 */
	protected String brokerUrl;

	/**
	 * The string with the name of the topic
	 */
	protected String topicId;

	/**
	 * Initializes the connection parameters and the factory
	 * 
	 * @param brokerUrl the broker URL
	 * @param topicId the ID of the topic
	 */
	protected AbstractActiveMQCommunicator(String brokerUrl, String topicId) {

		this.brokerUrl = brokerUrl;
		this.topicId = topicId;

		// Creates the connection factory and set the trusted package to make sure we can send object message containing
		// NetworkEvent data (ActiveMQ security)
		this.factory = new ActiveMQConnectionFactory(this.brokerUrl);
//		((ActiveMQConnectionFactory) factory).setTrustedPackages(Arrays.asList(TRUSTED_PACKAGES));
		((ActiveMQConnectionFactory) factory).setTrustAllPackages(true); // TODO used a list of trusted package
	}

}
