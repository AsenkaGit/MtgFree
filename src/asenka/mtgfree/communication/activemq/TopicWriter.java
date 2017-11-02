package asenka.mtgfree.communication.activemq;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TopicPublisher;

/**
 * The topic writer sending message to the broker. Don't forget to close the connection with the broker when the application exit.
 * 
 * @author asenka
 * @see Closeable
 */
public class TopicWriter extends ActiveMQTopicCommunicator {

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
	 * @throws Exception 
	 * @throws JMSException
	 */
	TopicWriter(String brokerUrl, String topicId) throws Exception {

		super(brokerUrl, topicId);
		
		try {
			// Create the topic publisher
			this.publisher = super.session.createPublisher(super.topic);
			this.message = super.session.createObjectMessage();
			
		} catch (JMSException e) {
			this.close();
			throw new Exception(e);
		}
	}

	/**
	 * Publish the data to the broker topic
	 * 
	 * @param data serializable object
	 * @throws Exception
	 */
	void publish(final Serializable data) throws Exception {

		try {
			this.message.setObject(data);
			this.publisher.publish(this.message);
		} catch (JMSException e) {
			this.close();
			throw new Exception(e);
		}
	}

	@Override
	public void close() throws IOException {

		try {
			this.message = null;

			if (this.connection != null) {
				this.connection.close();
			}
			if (this.session != null) {
				this.session.close();
			}
			if (this.publisher != null) {
				this.publisher.close();
			}
		} catch (JMSException ex) {
			throw new IOException(ex);
		}
	}
}
