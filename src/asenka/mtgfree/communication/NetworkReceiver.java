package asenka.mtgfree.communication;

import java.io.Closeable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.management.RuntimeErrorException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import asenka.mtgfree.model.events.NetworkEvent;
import asenka.mtgfree.model.game.GameTable;

public class NetworkReceiver implements Closeable {

	private static final String BROKER_URL = "tcp://192.168.1.20:61616";

	private static final String TOPIC_NAME = "MTGFREE_TOPIC_TEST";

	/**
	 * The connection with the broker. Use the connection to create a session.
	 */
	private TopicConnection connection = null;

	/**
	 * The session with the broker. Use the session to create subscription and messages
	 */
	private TopicSession session = null;

	/**
	 * The subscriber used to read the topic in the broker
	 */
	private TopicSubscriber subscriber = null;

	/**
	 * The game table with the all the data about the current game (players, decks, libraries, cards on battlefield or grave yard,
	 * etc.)
	 */
	private GameTable gameTable;

	/**
	 * Flag used to prevent the reading thread to stop. When the application is closed or when something wrong happens, the value
	 * is set to <code>true</code> to allow the reading thread to close properly.
	 */
	private boolean closed;

	/**
	 * Build a NetworkReceiver with a game table
	 * 
	 * @param gameTable
	 */
	NetworkReceiver(final GameTable gameTable) {

		this.gameTable = gameTable;
		this.closed = false;

		// The reading session is managed in specific thread
		new Thread(() -> {

			try {
				// Create a TOPIC connection with ActiveMQ broker and starts it
				ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
				factory.setTrustAllPackages(true);
				connection = factory.createTopicConnection();
				connection.start();

				// Create a session
				session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

				// Create the topic where the message will be sent
				Topic topic = session.createTopic(TOPIC_NAME);

				// Create the topic subscriber
				subscriber = session.createSubscriber(topic);

				// Associate the message listener to the subscription to manage the received messages from the broker
				subscriber.setMessageListener(new NetworkListener());

				// Prevent the thread to stop until the close() method set 'closed' to true
				while (!closed) {
					Thread.sleep(1000);
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				this.close();
			}
		}).start();
	}

	/**
	 * Close the connection with the broker and all the associated objects (connection, session, subscriber). It also closes the
	 * reading thread created above by setting the <code>closed</code> flag to <code>true</code>
	 */
	@Override
	public void close() {

		try {
			closed = true;
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
			// Something really wrong happened...
			throw new RuntimeErrorException(new Error(ex));
		}
	}

	/**
	 * Class implementing MessageListener and its unique method onMessage(Message) triggered automatically when a message is read
	 * from the broker.
	 * 
	 * @author asenka
	 * @see MessageListener
	 */
	class NetworkListener implements MessageListener {

		/**
		 * Method called when a message is received from the broker
		 */
		@Override
		public void onMessage(Message message) {

			if (message instanceof ObjectMessage) {

				ObjectMessage objectMessage = (ObjectMessage) message;

				try {
					// Update the game table logs
					NetworkEvent event = (NetworkEvent) objectMessage.getObject();
					gameTable.addLog(event);

					// If the event is from the local player, then nothing needs to be done
					if (gameTable.isLocalPlayer(event.getPlayer())) {
						Logger.getLogger(this.getClass()).info("YOU DID SOMETHING !");
					} else {
						// But if the event is from another player, then the local views needs to be updated
						Logger.getLogger(this.getClass()).info("ANOTHER PLAYER DID SOMETHING");
						
//						PlayerController opponentController = gameTable.getOtherPlayerController(event.getPlayer());
						
						
					}

				} catch (JMSException e) {
					throw new RuntimeException("Error while reading network data", e);
				}
			} else {
				throw new RuntimeException("Unexpected type of message from the broker: " + message);
			}
		}
	}
}
