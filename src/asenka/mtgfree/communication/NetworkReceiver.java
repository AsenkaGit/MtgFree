package asenka.mtgfree.communication;

import java.io.Closeable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.management.RuntimeErrorException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import asenka.mtgfree.model.game.GameTable;

public class NetworkReceiver implements Closeable {

	private static final String BROKER_URL = "tcp://192.168.1.20:61616";

	private static final String TOPIC_NAME = "MTGFREE_TOPIC_TEST";

	private TopicConnection connection = null;

	private TopicSession session = null;

	private TopicSubscriber subscriber = null;

	private GameTable gameTable;
	
	private boolean closed;

	NetworkReceiver(GameTable gameTable) {

		this.gameTable = gameTable;
		this.closed = false;

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
				
				subscriber.setMessageListener(new NetworkListener());
				
				while(!closed) {
					Thread.sleep(1000);
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
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
					throw new RuntimeErrorException(new Error(ex));
				}
			}
		}).start();
	}

	@Override
	public void close() {

		try {
			closed = true;
			if (connection != null) {
				connection.stop();
				connection.close();
			}
			if (session != null) {
				session.close();
			}
			if (subscriber != null) {
				subscriber.close();
			}
		} catch (JMSException ex) {
			throw new RuntimeErrorException(new Error(ex));
		}
	}

	class NetworkListener implements MessageListener {

		@Override
		public void onMessage(Message message) {

			Logger.getLogger(this.getClass()).info(message);
		}

	}

}
