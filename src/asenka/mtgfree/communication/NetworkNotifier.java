package asenka.mtgfree.communication;

import java.io.Closeable;
import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.management.RuntimeErrorException;

import org.apache.activemq.ActiveMQConnectionFactory;

import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.events.NetworkEvent;

public class NetworkNotifier implements Closeable {

	private static final String BROKER_URL = "tcp://localhost:61616";

	private static final String TOPIC_NAME = "MTGFREE_TOPIC_TEST";

	private TopicPublisher publisher = null;

	private TopicConnection connection = null;

	private TopicSession session = null;

	public NetworkNotifier() {

		try {
			// Create a TOPIC connection with ActiveMQ broker and starts it
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
			connection = factory.createTopicConnection();
			connection.start();

			// Create a session
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the topic where the message will be sent
			Topic topic = session.createTopic(TOPIC_NAME);

			// Create the topic publisher
			publisher = session.createPublisher(topic);

		} catch (JMSException e) {
			try {
				if (connection != null) {
					connection.close();
				}
				if (session != null) {
					session.close();
				}
				if (publisher != null) {
					publisher.close();
				}
				throw new RuntimeException("Problem to initialize the connection with the broker", e);
			} catch (JMSException ex) {
				throw new RuntimeErrorException(new Error(ex));
			}
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void sendEvent(NetworkEvent event) throws Exception {
		
		// Create the message
		try {
			ObjectMessage message = session.createObjectMessage();
			message.setObject(event);
			publisher.publish(message);
			
		} catch (JMSException e) {
			throw new Exception("Problem while sending event to broker", e);
		}
	}
	
	

	@Override
	public void close() throws IOException {
		
		try {
			if (connection != null) {
				connection.close();
			}
			if (session != null) {
				session.close();
			}
			if (publisher != null) {
				publisher.close();
			}
		} catch (JMSException ex) {
			throw new RuntimeErrorException(new Error(ex));
		}
	}
}
