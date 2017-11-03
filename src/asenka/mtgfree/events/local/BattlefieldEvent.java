package asenka.mtgfree.events.local;

import asenka.mtgfree.model.game.Player;

/**
 * This kind of eventType is triggered when a Card is updated
 * 
 * @author asenka
 * @see Card
 */
public class BattlefieldEvent extends AbstractLocalEvent {

	/**
	 * Build a BattlefieldEvent 
	 * @param eventType the type of event
	 * @param property the updated property
	 * @param value the new value
	 * @see AbstractEvent#eventType
	 * @see AbstractLocalEvent#property
	 * @see AbstractLocalEvent#value
	 */
	public BattlefieldEvent(Player player, String eventType, String property, Object value) {

		super(player, eventType, property, value);
	}
}