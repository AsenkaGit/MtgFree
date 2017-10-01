package asenka.mtgfree.model.mtg.events;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * 
 * 
 * @author asenka
 *
 */
public class ChangeMtgCardContextEvent extends AbstractMtgCardEvent {

	/**
	 * 
	 */
	private Set<AbstractMtgCardEvent> relatedEvents;
	
	/**
	 * @param updatedCard
	 */
	public ChangeMtgCardContextEvent(MtgCard updatedCard) {
		super("Context updated", updatedCard);
		this.relatedEvents = new HashSet<AbstractMtgCardEvent>(4);
	}

	/**
	 * @param event
	 */
	public void add(AbstractMtgCardEvent event) {
		this.relatedEvents.add(event);
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<AbstractMtgCardEvent> getRelatedEvents() {
		return Collections.unmodifiableSet(this.relatedEvents);
	}
}
