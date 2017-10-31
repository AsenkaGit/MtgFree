package asenka.mtgfree.model.events;

import java.io.Serializable;
import java.util.Arrays;

import asenka.mtgfree.model.game.Player;

/**
 * Event triggered when a player perform an action. This eventType is meant to be routed trough the network
 * 
 * @author romain.bourreau
 * @see AbstractEvent
 */
public class NetworkEvent extends AbstractEvent implements Serializable {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 4918156440560685706L;

	/**
	 * The player performing the eventType
	 */
	private final Player player;
	
	/**
	 * The parameters of the event
	 */
	private final Serializable data;

	/**
	 * Build a network eventType without any data
	 * @param eventType the type of event, for network event use the method name
	 * @param player the player performing the action
	 */
	public NetworkEvent(String eventType, Player player) {

		this(eventType, player, null);
	}
	
	/**
	 * Build a network eventType with a client eventType
	 * @param eventType the type of event, for network event use the method name
	 * @param player the player performing the action
	 * @param data the client eventType used to transport data about the eventType
	 */
	public NetworkEvent(String eventType, Player player, Serializable data) {

		super(eventType);
		this.player = player;
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public Serializable getData() {

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
		String strEvent = this.data instanceof Serializable[] ?
			this.getClass().getSimpleName() + "[" + super.eventType + ", " + this.player.getName() + ", " + Arrays.toString((Serializable[]) this.data) + "]":
			this.getClass().getSimpleName() + "[" + super.eventType + ", " + this.player.getName() + ", " + this.data + "]";

		return strEvent;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NetworkEvent other = (NetworkEvent) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}
}
