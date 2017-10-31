package asenka.mtgfree.model.events;

import asenka.mtgfree.model.game.Player;

/**
 * Event triggered when a player perform an action. This eventType is meant to be routed trough the network
 * 
 * @author romain.bourreau
 * @see AbstractEvent
 */
public class NetworkEvent extends AbstractEvent {
	
	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -5787227409423452235L;

	/**
	 * The player performing the eventType
	 */
	private final Player player;
	
	/**
	 * The client eventType to transport on the other client
	 */
	private final AbstractClientEvent clientEvent;

	/**
	 * Build a network eventType without any client eventType (when the player draws a card)
	 * @param eventType the eventType name
	 * @param player the player performing the action
	 */
	public NetworkEvent(String event, Player player) {

		this(event, player, null);
	}
	
	/**
	 * Build a network eventType with a client eventType
	 * @param eventType the type of event
	 * @param player the player performing the action
	 * @param clientEvent the client eventType used to transport data about the eventType
	 */
	public NetworkEvent(String eventType, Player player, AbstractClientEvent clientEvent) {

		super(eventType);
		this.player = player;
		this.clientEvent = clientEvent;
	}

	/**
	 * @return the clientEvent
	 */
	public AbstractClientEvent getClientEvent() {

		return clientEvent;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {

		return player;
	}
}
