package asenka.mtgfree.model.mtg.events;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * The event triggered when a card has a new context.
 * 
 * @author asenka
 */
public class ChangeMtgCardContextEvent extends AbstractMtgCardEvent {

	/**
	 * All the related events happening when a card changes context. The set 
	 * of related events depends on the new context
	 */
	private Set<AbstractMtgCardEvent> relatedEvents;
	
	/**
	 * Constructor
	 * @param updatedCard
	 */
	public ChangeMtgCardContextEvent(MtgCard updatedCard) {
		super("Context updated", updatedCard);
		this.relatedEvents = new HashSet<AbstractMtgCardEvent>(4);
	}

	/**
	 * Add a new related event to this event
	 * @param event a related event
	 */
	public void add(AbstractMtgCardEvent event) {
		this.relatedEvents.add(event);
	}
	
	/**
	 * @return an unmodifiable list of related events 
	 */
	public Set<AbstractMtgCardEvent> getRelatedEvents() {
		return Collections.unmodifiableSet(this.relatedEvents);
	}
}
