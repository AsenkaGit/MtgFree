package asenka.mtgfree.controllers.communication.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TopicSubscriber;
import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import asenka.mtgfree.controllers.CommunicationManager;
import asenka.mtgfree.controllers.communication.SynchronizationEvent;


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
	 * If an exception occurs while reading the message, the exception is stored in this object. It value is <code>null</code> by default.
	 */
	private Throwable errorReadingMessage;
	
	/**
	 * The reader needs the communication manager to manage the data from the broker
	 */
	private CommunicationManager communicationManager;

	/**
	 * The flag indicating to stop reading the topic. It is initialized to <b><code>false</code></b> in the listening thread.
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
		this.communicationManager = communicationManager;
		
		// Prepare the listening thread but it is not started in the constructor
		this.listeningThread = initListeningThread();

		// Add a name to the thread and an exception handler to make sure that all the exceptions are at least logged
		this.listeningThread.setName("Read ActiveMQ Thread [" + super.brokerUrl + ", " + super.topicId + "]");
		this.listeningThread.setUncaughtExceptionHandler((thread, exception) -> {
			Logger.getLogger(TopicReader.class).error("Error while reading message from broker", exception);
		});
	}

	/**
	 * Start the listening thread that read continuously the messages from the broker until
	 * {@link TopicReader#close()} is called.
	 */
	void listen() {

		this.listeningThread.start();
	}
	
	/**
	 * @return <code>true</code> if the reader is currently listening the messages from the broker
	 */
	boolean isListening() {
		
		return this.listeningThread.isAlive() && !this.stopListening;
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
	
	/**
	 * @return The thread that listen to the broker messages.
	 */
	private Thread initListeningThread() {
		
		return new Thread(() -> {

			this.stopListening = false;
			this.errorReadingMessage = null;

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
						this.communicationManager.receive((SynchronizationEvent) data);

					} catch (Throwable e) {
						this.errorReadingMessage = e;
					}
				});

				
				// Infinite loop to always listen while 'stopListening' flag is false
				while (!this.stopListening) {
					Thread.sleep(1000);
					
					// If an exception has been raised by the message listener, the thread is stop by throwing the exception
					if(this.errorReadingMessage != null) {
						throw this.errorReadingMessage;
					}
				}

			} catch (Throwable e) {
				throw new RuntimeException(e);
			} finally {
				this.close();
			}
		});
	}
}
