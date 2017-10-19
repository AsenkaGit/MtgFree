package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.model.events.BattlefieldEvent;

/**
 * 
 * 
 * @author asenka
 *
 */
public class Battlefield extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4503726991451727009L;

	/**
	 * 
	 */
	private Set<Card> cards;

	/**
	 * 
	 */
	public Battlefield() {

		this.cards = Collections.synchronizedSet(new HashSet<Card>());
	}

	/**
	 * @return the cards
	 */
	public Set<Card> getCards() {

		return cards;

	}
	
	/**
	 * 
	 * @param card
	 * @return
	 */
	public boolean contains(Card card) {
		return this.cards.contains(card);
	}

	/**
	 * 
	 * @param card
	 */
	public void add(Card card) {

		this.cards.add(card);

		super.setChanged();
		super.notifyObservers(new BattlefieldEvent("add", "cards", card));
	}

	/**
	 * 
	 * @param card
	 * @return
	 */
	public boolean remove(Card card) {

		if (this.cards.remove(card)) {
			super.setChanged();
			super.notifyObservers(new BattlefieldEvent("remove", "cards", card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	public void clear() {

		this.cards.clear();

		super.setChanged();
		super.notifyObservers(new BattlefieldEvent("clear", "cards", null));
	}
}
