package asenka.mtgfree.events;

import java.io.Serializable;

import asenka.mtgfree.model.game.Player;

/**
 * Abstract class for the events
 * 
 * @author asenka
 * @see Serializable
 * @see LocalEvent
 * @see NetworkEvent
 */
public abstract class AbstractEvent implements Serializable {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 2142857006882985412L;

	/**
	 * The player at the origin of the event. This value is not mandatory for all the events
	 */
	final Player player;

	/**
	 * The parameters of the events. It may be empty but not <code>null</code>. Since some events are supposed to be communicated
	 * to other player through a network, they have to be serializable.
	 */
	final Serializable[] parameters;

	/**
	 * Build an abstract event initializing the player and the parameters
	 * 
	 * @param player the player at the origin of the event (null allowed)
	 * @param parameters
	 */
	public AbstractEvent(Player player, Serializable... parameters) {
		this.player = player;
		this.parameters = parameters;
	}

	/**
	 * @return the player at the origin of the event. The returned value may be <code>null</code> depending on the type of event
	 */
	public Player getPlayer() {

		return player;
	}

	/**
	 * @return the event parameters. It should not be <code>null</code>
	 */
	public Serializable[] getParameters() {

		return parameters;
	}

	/**
	 * Most of the time, an event has only one parameter. It would be more convenient to get this parameter directly with this
	 * method rather than getting the parameters array.
	 * 
	 * @return the first parameter if there is at least 1
	 * @throws IllegalStateException if the event has no parameter
	 */
	public Serializable getFirstParameter() {

		if (this.parameters.length > 0) {
			return this.parameters[0];
		} else {
			throw new IllegalStateException(
					"The event " + this + " contains no parameter. You can not call the method getFirstParameter().");
		}
	}
}
