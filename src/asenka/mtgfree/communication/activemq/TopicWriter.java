package asenka.mtgfree.communication.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TopicPublisher;
import javax.management.RuntimeErrorException;

/**
 * The topic writer sending message to the broker. Don't forget to close the connection with the broker when the application exit.
 * 
 * @author asenka
 * @see Closeable
 */
final class TopicWriter extends AbstractActiveMQCommunicator {

	/**
	 * The publisher used to publish the data on the broker
	 */
	private TopicPublisher publisher = null;

	/**
	 * The message published to the broker
	 */
	private ObjectMessage message = null;

	/**
	 * Build a TopicWriter
	 * 
	 * @param brokerUrl the broker URL
	 * @param topicId the ID of the topic
	 * @throws RuntimeException if the connection with the broker cannot be initialized properly
	 */
	TopicWriter(String brokerUrl, String topicId) throws RuntimeException {

		super(brokerUrl, topicId);

		try {
			// Start the connection with the broker
			super.connection = super.factory.createTopicConnection();
			super.connection.start();

			// Create the session and the topic where the messages will be sent
			super.session = super.connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			super.topic = super.session.createTopic(super.topicId);

			// Create the publisher and the message to send the data
			this.publisher = super.session.createPublisher(super.topic);
			this.message = super.session.createObjectMessage();

		} catch (JMSException e) {
			this.close();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Publish the data to the broker topic
	 * 
	 * @param data serializable object
	 * @throws JMSException if a problem occurs while publishing data to the broker
	 */
	void publish(final Serializable data) throws JMSException {

		this.message.setObject(data);
		this.publisher.publish(this.message);
	}

	/**
	 * Closes all the JMS objects (connection, session, etc...)
	 * @throws RuntimeErrorException if unable to close a JMS object
	 */
	@Override
	public void close() {

		try {
			this.message = null;

			if (super.connection != null) {
				super.connection.close();
			}
			if (super.session != null) {
				super.session.close();
			}
			if (this.publisher != null) {
				this.publisher.close();
			}
		} catch (JMSException ex) {
			throw new RuntimeErrorException(new Error(ex));
		}
	}
}
