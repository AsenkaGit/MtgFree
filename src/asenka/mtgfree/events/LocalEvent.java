package asenka.mtgfree.events;

import java.io.Serializable;
import java.util.Arrays;

import asenka.mtgfree.model.game.Player;

/**
 * A Local event. These class of event is not supposed to be routed to other players.
 * They are here to implement the local MVC pattern and send the necessary data
 * to the views so that they can be updated properly.
 * 
 * @author asenka
 * @see AbstractEvent
 */
public class LocalEvent extends AbstractEvent {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 6856250468773765127L;

	/**
	 * The type of local events
	 */
	public enum Type {
		ADD_CARD_TO_HAND,
		ADD_CARD_TO_BATTLEFIELD,
		ADD_CARD_TO_GRAVEYARD,
		ADD_CARD_TO_EXILE,
		ADD_CARD_TO_LIBRARY,
		ADD_CARD_TO_DECK_MAIN,
		ADD_CARD_TO_DECK_SIDEBOARD,
		REMOVE_CARD_FROM_HAND,
		REMOVE_CARD_FROM_BATTLEFIELD,
		REMOVE_CARD_FROM_GRAVEYARD,
		REMOVE_CARD_FROM_EXILE,
		REMOVE_CARD_FROM_LIBRARY,
		REMOVE_CARD_FROM_DECK_MAIN,
		REMOVE_CARD_FROM_DECK_SIDEBOARD,
		CLEAR_HAND,
		CLEAR_BATTLEFIELD,
		CLEAR_GRAVEYARD,
		CLEAR_EXILE,
		CLEAR_LIBRARY,
		CLEAR_DECK_MAIN,
		CLEAR_DECK_SIDEBOARD,
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
		PLAYER_UPDATE_LIFE,
		PLAYER_UPDATE_POISON,
		PLAYER_SET_SELECTED_DECK,
		PLAYER_ADD_DECK,
		PLAYER_REMOVE_DECK,
		NEW_GAMETABLE_LOG
	}

	/**
	 * The type of local event
	 */
	private final LocalEvent.Type type;

	/**
	 * Build a local event without player data (the player is <code>null</code>)
	 * @param type the type of event (mandatory)
	 * @param parameters the parameters of this event (not mandatory)
	 */
	public LocalEvent(LocalEvent.Type type, Serializable... parameters) {

		this(type, null, parameters);
	}

	/**
	 * Build a local event with the data about the player at the origin of the event
	 * @param type the type of event (mandatory)
	 * @param player the player at the origin of the event (null allowed)
	 * @param parameters the parameters of this event (not mandatory)
	 */
	public LocalEvent(LocalEvent.Type type, Player player, Serializable... parameters) {

		super(player, parameters);
		this.type = type;
	}

	/**
	 * 
	 * @return the type of event
	 * @see LocalEvent.Type
	 */
	public LocalEvent.Type getType() {

		return this.type;
	}

	@Override
	public String toString() {

		return LocalEvent.class.getSimpleName() + 
				" [" + (player != null ? player.getName() + ", " : "") + type + ", " + Arrays.toString(parameters) + "]";
	}
}
