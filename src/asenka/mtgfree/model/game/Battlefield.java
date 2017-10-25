package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import asenka.mtgfree.model.events.BattlefieldEvent;

/**
 * Model class of the battlefield. It is basically a synchronized set of Card.
 * 
 * @author asenka
 * @see Card
 * @see Collections#synchronizedSet(Set)
 * @see Observable
 */
public class Battlefield extends Observable implements Serializable {

	/**
	 * The generated id for serialization
	 */
	private static final long serialVersionUID = -8273268654036110921L;
	/**
	 * The synchronized set of cards on the battlefield
	 */
	private List<Card> cards;

	/**
	 * Create a battlefield without any cards
	 */
	public Battlefield() {

		// Use a synchronized set because several players may perform actions on the battlefield at the same time
		this.cards = Collections.synchronizedList(new ArrayList<Card>());
	}

	/**
	 * @return the cards on the battlefield
	 */
	public List<Card> getCards() {

		return cards;

	}
	
	/**
	 * @param card the card to check
	 * @return <code>true</code> if <code>card</code> is on the battlefield
	 */
	public boolean contains(Card card) {
		return this.cards.contains(card);
	}

	/**
	 * 
	 * @return
	 */
	public int size() {
	
		return this.cards.size();
	}

	/**
	 * Adds a card on the battlefield and notify the observers
	 * @param card the card to add
	 * @see BattlefieldEvent
	 */
	public void add(Card card) {

		this.cards.add(card);

		super.setChanged();
		super.notifyObservers(new BattlefieldEvent("add", "cards", card));
	}

	/**
	 * Removes a card from the battlefield and notify the observers
	 * @param card the card to remove
	 * @return <code>true</code> if the card has been successfully removed, <code>false</code> if the card wasn't on the battlefield
	 * @see BattlefieldEvent
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
	 * Removes all the cards on the battlefield
	 * @see BattlefieldEvent
	 */
	public void clear() {

		this.cards.clear();

		super.setChanged();
		super.notifyObservers(new BattlefieldEvent("clear", "cards", null));
	}
}
