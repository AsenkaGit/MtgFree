package asenka.mtgfree.communication.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TopicSubscriber;

import asenka.mtgfree.communication.NetworkEventManager;
import asenka.mtgfree.communication.events.NetworkEvent;

/**
 * 
 * @author asenka
 * @see ActiveMQTopicCommunicator
 */
public class TopicReader extends ActiveMQTopicCommunicator {

	/**
	 * 
	 */
	private TopicSubscriber subscriber = null;

	/**
	 * 
	 */
	private boolean stopListening;

	/**
	 * 
	 * @param brokerUrl
	 * @param topicId
	 * @throws Exception
	 */
	protected TopicReader(String brokerUrl, String topicId) throws Exception {

		super(brokerUrl, topicId);

		try {
			// Create the topic publisher
			this.subscriber = super.session.createSubscriber(super.topic);

			this.subscriber.setMessageListener(message -> {
				try {

					if (message instanceof ObjectMessage) {
						ObjectMessage objectMessage = (ObjectMessage) message;
						Serializable data = objectMessage.getObject();

						if (data instanceof NetworkEvent) {
							NetworkEventManager.getInstance().manageEvent((NetworkEvent) data);
						} else {
							throw new RuntimeException("Unexpected data type " + data);
						}
					} else {
						throw new RuntimeException("Unexpected message type " + message);
					}
				} catch (JMSException ex) {
					throw new RuntimeException(ex);
				}
			});

		} catch (JMSException e) {
			this.close();
			throw new Exception(e);
		}
	}

	/**
	 * Create a thread listening and read the messages from the broker. Each time a message arrives, it is managed by the thread
	 * created inside this method.
	 */
	void listen() {

		Thread listeningBrokerThread = new Thread(() -> {

			this.stopListening = false;

			// Associate the message listener to the subscription to manage the received messages from the broker

			try {
				// Prevent the thread to stop until the close() method set 'stopListening' to true
				while (!stopListening) {
					Thread.sleep(1000);
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});

//		// Equip the thread to manage the exceptions
//		listeningBrokerThread.setUncaughtExceptionHandler((thread, exception) -> {
//			Logger.getLogger(this.getClass()).error("Error while listening the broker", exception);
//			this.close();
//		});
		listeningBrokerThread.start();
	}

	/**
	 * Close the broker objects
	 */
	@Override
	public void close() {

		try {
			this.stopListening = true;

			if (connection != null) {
				connection.close();
			}
			if (session != null) {
				session.close();
			}
			if (subscriber != null) {
				subscriber.close();
			}
		} catch (JMSException ex) {
			throw new RuntimeException(ex);
		}
	}
}
