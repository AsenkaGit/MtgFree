package sandbox.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TopicSubscriber;
import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import sandbox.CommunicationManager;
import sandbox.SynchronizationEvent;

/**
 * The class able to read the data from the ActiveMQ broker. It is run in a specific thread created in the constructor
 * and started with the method {@link TopicReader#listen()}. 
 * 
 * @author asenka
 * @see AbstractActiveMQCommunicator
 */
final class TopicReader extends AbstractActiveMQCommunicator {

	/**
	 * The topic subscription used to read the messages from the topic
	 */
	private TopicSubscriber subscriber = null;

	/**
	 * The thread where the session to the broker is created. 
	 */
	private Thread listeningThread;

	/**
	 * The flag indicating to stop reading the topic. It is initialized to <code>false</code>
	 */
	private boolean stopListening;

	/**
	 * Build a topic reader able to read data from a broker with a subscription to the specified
	 * topic. The constructor create the listening thread but do not start it. Call the method
	 * {@link TopicReader#listen()} to start reading the data from the broker.
	 * 
	 * @param brokerUrl the broker URL
	 * @param topicId the name of the topic to subscribe on
	 * @param communicationManager the manager used to manage the network event
	 */
	TopicReader(String brokerUrl, String topicId, final CommunicationManager communicationManager) {

		// Initialize the borker params and the connection factory
		super(brokerUrl, topicId);
		
		// Prepare the listening thread but it is not started in the constructor
		this.listeningThread = new Thread(() -> {

			this.stopListening = false;

			try {
				// Start the connection with the broker (ActiveMQ)
				super.connection = super.factory.createTopicConnection();
				super.connection.start();

				// Create the session and the topic where the messages will be sent
				super.session = super.connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
				super.topic = super.session.createTopic(super.topicId);

				// Create the subscription to the topic
				this.subscriber = super.session.createSubscriber(topic);

				// Add the message listener to the subscription. It defines what to do
				// with the received messages
				this.subscriber.setMessageListener(message -> {
					try {
						// Get the data from the message and send it to the singleton class NetworkEventManage that is able
						// to deal with those data
						ObjectMessage objectMessage = (ObjectMessage) message;
						Serializable data = objectMessage.getObject();
						communicationManager.receive((SynchronizationEvent) data);

					} catch (Exception e) {
						Logger.getLogger(this.getClass()).error("Problem to read data from broker.", e);
					}
				});

				// Infinite loop to always listen while 'stopListening' flag is false
				while (!stopListening) {
					Thread.sleep(1000);
				}

			} catch (JMSException | InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				this.close();
			}
		});

		// Add a name to the thread and an exception handler to make sure that all the exceptions are at least logged
		this.listeningThread.setName("Read ActiveMQ Thread [" + super.brokerUrl + ", " + super.topicId + "]");
		this.listeningThread.setUncaughtExceptionHandler(
			(thread, exception) -> Logger.getLogger(this.getClass()).error("Error while listening the broker", exception));
	}

	/**
	 * Start the listening thread that read continuously the messages from the broker until
	 * {@link TopicReader#close()} is called.
	 */
	void listen() {

		this.listeningThread.start();
	}

	/**
	 * Stop to listen to the broker and closes all the JMS objects (connection, session, etc...)
	 * @throws RuntimeErrorException if unable to close a JMS object
	 */
	@Override
	public void close() {

		try {
			this.stopListening = true;
			
			if (super.connection != null) {
				super.connection.close();
			}
			if (super.session != null) {
				super.session.close();
			}
			if (this.subscriber != null) {
				this.subscriber.close();
			}
		} catch (JMSException ex) {
			throw new RuntimeErrorException(new Error(ex));
		}
	}
}
