package asenka.mtgfree.communication.activemq;

import java.io.IOException;
import java.io.Serializable;

import asenka.mtgfree.communication.NetworkCommunicator;


public class ActiveMQCommunicator implements NetworkCommunicator {
	
	private TopicWriter writer;
	
	private TopicReader reader;
	
	public ActiveMQCommunicator() throws Exception {
		
		this.writer = new TopicWriter("tcp://192.168.1.20:81616", "MTGTOPIC");
		this.reader = new TopicReader("tcp://192.168.1.20:81616", "MTGTOPIC");
	}

	@Override
	public void send(Serializable data) throws Exception {

		this.writer.publish(data);
	}

	@Override
	public void listen() {

		this.reader.listen();
	}

	@Override
	public void close() throws IOException {
	
		this.reader.close();
		this.writer.close();
	}

}
