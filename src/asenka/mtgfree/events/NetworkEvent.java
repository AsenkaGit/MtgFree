package asenka.mtgfree.events;

import java.io.Serializable;
import java.util.Arrays;

import asenka.mtgfree.model.game.Player;

/**
 * Event triggered when a player perform an action. This eventType is meant to be routed trough the network
 * 
 * @author asenka
 * @see AbstractEvent
 */
public class NetworkEvent extends AbstractEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -1857530867085344584L;

	/**
	 * The type of network events
	 */
	public enum Type {
		REQUEST_GAMETABLE_DATA,
		SEND_GAMETABLE_DATA,
		PLAYER_JOIN,
		PLAYER_LEAVE,
		PLAYER_UPDATE_LIFE,
		PLAYER_UPDATE_POISON,
		DRAW,
		DRAW_X,
		PLAY,
		DESTROY,
		EXILE,
		BACK_TO_HAND,
		BACK_TO_LIBRARY,
		CARD_TAP,
		CARD_UNTAP,
		CARD_SHOW,
		CARD_HIDE,
		CARD_DO_REVEAL,
		CARD_UNDO_REVEAL,
		CARD_MOVE,
		CARD_ADD_COUNTER,
		CARD_REMOVE_COUNTER,
		CARD_CLEAR_COUNTERS,
		CARD_ADD_ASSOCIATED_CARD,
		CARD_REMOVE_ASSOCIATED_CARD,
		CARD_CLEAR_ASSOCIATED_CARDS,
		SHUFFLE_LIBRARY,
		CLEAR_GAME_DATA,
		GAME_TABLE_FULL
	}

	/**
	 * The type of network event
	 */
	private final NetworkEvent.Type type;

	/**
	 * Build a local event with the data about the player at the origin of the event
	 * @param player the player at the origin of the event (null not allowed)
	 * @param type the type of event (mandatory)
	 * @param parameters the parameters of this event (not mandatory)
	 */
	public NetworkEvent(NetworkEvent.Type type, Player player, Serializable... parameters) {

		super(player, parameters);
		this.type = type;
	}

	/**
	 * @return the type of event
	 * @see NetworkEvent.Type
	 */
	public NetworkEvent.Type getType() {

		return this.type;
	}

	@Override
	public String toString() {

		return NetworkEvent.class.getSimpleName() + " [" + player.getName() + ", " + type + ", " + Arrays.toString(parameters) + "]";
	}
}
