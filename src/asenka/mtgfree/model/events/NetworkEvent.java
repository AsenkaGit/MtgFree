package asenka.mtgfree.model.events;

import asenka.mtgfree.model.game.Player;

/**
 * Event triggered when a player perform an action. This event is meant to be routed trough the network
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
	 * The player performing the event
	 */
	private final Player player;
	
	/**
	 * The client event to transport on the other client
	 */
	private final AbstractClientEvent clientEvent;

	/**
	 * Build a network event without any client event (when the player draws a card)
	 * @param event the event name
	 * @param player the player performing the action
	 */
	public NetworkEvent(String event, Player player) {

		this(event, player, null);
	}
	
	/**
	 * Build a network event with a client event
	 * @param event the event name
	 * @param player the player performing the action
	 * @param clientEvent the client event used to transport data about the event
	 */
	public NetworkEvent(String event, Player player, AbstractClientEvent clientEvent) {

		super(event);
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
