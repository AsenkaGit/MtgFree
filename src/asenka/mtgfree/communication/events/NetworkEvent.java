package asenka.mtgfree.communication.events;

import java.io.Serializable;
import java.util.Arrays;
import asenka.mtgfree.model.events.AbstractEvent;
import asenka.mtgfree.model.game.Player;

/**
 * Event triggered when a player perform an action. This eventType is meant to be routed trough the network
 * 
 * @author romain.bourreau
 * @see AbstractEvent
 */
public class NetworkEvent implements Serializable {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -6878254723594306988L;

	/**
	 * The type of event. Usually it is the controller's method name that triggered the network event
	 */
	private String eventType;

	/**
	 * The player performing the eventType
	 */
	private Player player;

	/**
	 * The parameters of the event
	 */
	private Serializable[] data;

	/**
	 * Build a network eventType without any data
	 * 
	 * @param eventType the type of event, for network event use the method name
	 * @param player the player performing the action
	 */
	public NetworkEvent(String eventType, Player player) {

		this(eventType, player, new Serializable[] {});
	}

	/**
	 * Build a network eventType with a client eventType
	 * 
	 * @param eventType the type of event, for network event use the method name
	 * @param player the player performing the action
	 * @param data the param of the event
	 */
	public NetworkEvent(String eventType, Player player, Serializable... data) {

		this.eventType = eventType;
		this.player = player;
		this.data = data;
	}

	/**
	 * @return the type of event
	 * @see NetworkEvent#eventType
	 */
	public String getEventType() {

		return this.eventType;
	}

	/**
	 * @return the data, an array Serializable objects
	 */
	public Serializable[] getData() {

		return this.data;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {

		return player;
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + "[" + this.eventType + ", " + this.player.getName() + ", " + Arrays.toString(this.data)
			+ "]";
	}
}
