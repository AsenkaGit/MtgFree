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

		synchronized (this.cards) {
			return cards;
		}
	}

	/**
	 * 
	 * @param card
	 */
	public void addCard(Card card) {

		synchronized (this.cards) {
			this.cards.add(card);
		}
		super.setChanged();
		super.notifyObservers(new BattlefieldEvent("add", "cards", card));
	}

	/**
	 * 
	 * @param card
	 * @param x
	 * @param y
	 * @param visible
	 */
	public void addCard(Card card, double x, double y, boolean visible) {

		card.setLocation(x, y);
		card.setVisible(visible);
		this.addCard(card);
	}

	/**
	 * 
	 * @param card
	 * @return
	 */
	public boolean remove(Card card) {

		synchronized (this.cards) {
			if (this.cards.remove(card)) {
				super.setChanged();
				super.notifyObservers(new BattlefieldEvent("remove", "cards", card));
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 
	 */
	public void clear() {

		synchronized (this.cards) {
			this.cards.clear();
		}
		super.setChanged();
		super.notifyObservers(new BattlefieldEvent("clear", "cards", null));
	}
}
