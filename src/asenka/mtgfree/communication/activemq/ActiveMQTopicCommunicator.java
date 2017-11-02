package asenka.mtgfree.communication.activemq;

import java.io.Closeable;
import java.util.Arrays;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Abstract class that mutualized the connection, session and topic data.
 * 
 * @author asenka
 * @see TopicReader
 * @see TopicWriter
 */
abstract class ActiveMQTopicCommunicator implements Closeable {

	/**
	 * The authorized packages. ActiveMQ requires to defines the authorized packages when using serialized object in messages.
	 * 
	 * @see ActiveMQConnectionFactory#setTrustedPackages(java.util.List)
	 */
	private static final String[] TRUSTED_PACKAGES = new String[] { "asenka.mtgfree.communication.events" };

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
	 * Initializes the connection, the session and the topic.
	 * 
	 * @param brokerUrl the broker URL
	 * @param topicId the ID of the topic
	 * @throws JMSException when a problem occurs when connecting with the broker
	 */
	protected ActiveMQTopicCommunicator(String brokerUrl, String topicId) throws Exception {

		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
		factory.setTrustedPackages(Arrays.asList(TRUSTED_PACKAGES));

		try {
			this.connection = factory.createTopicConnection();
			this.connection.start();
			this.session = this.connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			this.topic = this.session.createTopic(topicId);

		} catch (JMSException e) {
			if (this.connection != null) {
				this.connection.close();
			}
			if (this.session != null) {
				this.session.close();
			}
			throw new Exception(e);
		}
	}
}
