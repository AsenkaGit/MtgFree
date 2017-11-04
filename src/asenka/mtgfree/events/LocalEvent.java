package asenka.mtgfree.events;

import asenka.mtgfree.model.game.Player;

/**
 * 
 * @author asenka
 *
 */
public class LocalEvent {

	/**
	 * The type of eventType: <code>"add", "remove", "set", "clear", "shuffle", ...</code>
	 */
	private final EventType eventType;

	/**
	 * The player at the origin of the event
	 */
	private final Player player;

	/**
	 * The new value. On some events, it can be <code>null</code>.
	 */
	private final Object[] params;

	/**
	 * Build a local event without the player information. Use this constructor only if you are
	 * sure that it is useless to know who is at the origin of the event (for the events outside a game
	 * for example)
	 * @param eventType
	 * @param params
	 */
	public LocalEvent(EventType eventType, Object... params) {

		this(null, eventType, params);
	}

	/**
	 * 
	 * @param player
	 * @param eventType
	 * @param params
	 */
	public LocalEvent(Player player, EventType eventType, Object... params) {

		this.player = player;
		this.eventType = eventType;
		this.params = params;
	}

	/**
	 * @return the player at the origin of the event
	 */
	public Player getPlayer() {

		return this.player;
	}

	/**
	 * @return the type of event
	 * @see AbstractLocalEvent#eventType
	 */
	public EventType getEventType() {

		return this.eventType;
	}

	/**
	 * @return the new value. It can be <code>null</code>
	 */
	public Object[] getParams() {

		return params;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getFirstParam() {
		
		if(this.params.length > 0) {
			return this.params[0];
		} else {
			return null;
		}
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [" + eventType + ", " + player + ", " + params + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
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
		LocalEvent other = (LocalEvent) obj;
		if (eventType != other.eventType)
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}
}
