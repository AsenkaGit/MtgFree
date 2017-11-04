package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;

import asenka.mtgfree.events.EventType;
import asenka.mtgfree.events.LocalEvent;

/**
 * <p>
 * A representation of the library during an Mtg game. It contains a linked list of cards.
 * </p>
 * <p>
 * The class extends {@link Observable} to notify the observers when a value is updated.
 * </p>
 * 
 * @author asenka
 * @see Card
 * @see Observable
 * @see LinkedList
 */
public class Library extends Observable implements Serializable {

	/**
	 * The generated id for serialization
	 */
	private static final long serialVersionUID = -3520577402050132447L;

	/**
	 * The initial size of the list
	 */
	private final int initialSize;

	/**
	 * The linked list of cards in the library.
	 */
	private final LinkedList<Card> cards;

	/**
	 * @param cards
	 */
	public Library(Collection<Card> cards) {

		this.cards = new LinkedList<>(cards);
		this.initialSize = cards.size();
	}

	/**
	 * 
	 * @return the initial amount of cards in the library
	 */
	public int getInitialSize() {

		return initialSize;
	}

	/**
	 * @return the current size (number of cards) in the library
	 */
	public int size() {

		return this.cards.size();
	}

	/**
	 * @param card
	 * @return <code>true</code> if card is in the library
	 */
	public boolean contains(Card card) {

		return this.cards.contains(card);
	}

	/**
	 * Returns an unmodifiable list of Cards
	 * 
	 * @return the list of cards
	 * @see Collections#unmodifiableList(List)
	 */
	public List<Card> getCards() {

		return Collections.unmodifiableList(cards);
	}

	/**
	 * Returns the x first cards from the library. The returned cards remain inside the library (no need to notify the observers)
	 * 
	 * @param x the number of cards to get
	 * @return the x first cards from the library
	 */
	public List<Card> getFirstCards(int x) {

		// if xFirst is superior at the current number of cards in the library, the parameter value is reduced
		if (x > this.cards.size()) {
			x = this.cards.size();
		}
		List<Card> xFirstCards = new ArrayList<Card>(x);

		for (int i = 0; i < x; i++) {
			xFirstCards.add(this.cards.get(i));
		}
		return xFirstCards;
	}

	/**
	 * Get the card at the specified index
	 * 
	 * @param index the index
	 * @return a card from the library at a specific location
	 * @see LocalEvent
	 */
	public Card get(final int index) {

		return this.cards.get(index);
	}

	/**
	 * Add a card on the top of the library. The method creates a LocalEvent to notify the observers of the update on the
	 * library.
	 * 
	 * @param card the card to add
	 * @see LocalEvent
	 */
	public void addOnTop(Player player, final Card card) {

		this.cards.addFirst(card);
		super.setChanged();
		super.notifyObservers(new LocalEvent(player, EventType.ADD_TO_LIBRARY_TOP, card));
	}

	/**
	 * Add a card at the bottom of the library. The method creates a LocalEvent to notify the observers of the update on the
	 * library.
	 * 
	 * @param card the card to add
	 * @see LocalEvent
	 */
	public void addToBottom(Player player, final Card card) {

		this.cards.addLast(card);
		super.setChanged();
		super.notifyObservers(new LocalEvent(player, EventType.ADD_TO_LIBRARY_BOTTOM, card));
	}

	/**
	 * Add a card under the x first cards of the library. The method creates a LocalEvent to notify the observers of the update
	 * on the library.
	 * 
	 * @param card the cards to add
	 * @param x the number of cards. If <code>x >= cards.size()</code>,the card is inserted at the bottom of the library :
	 *        {@link Library#addToBottom(Card)}
	 * @see LocalEvent
	 */
	public void addFromTop(Player player, final Card card, int x) {

		if (x >= this.cards.size()) {
			this.addToBottom(player, card);
		} else {
			this.cards.add(x, card);
			super.setChanged();
			super.notifyObservers(new LocalEvent(player, EventType.ADD_TO_LIBRARY_FROM_TOP, card, new Integer(x)));
		}
	}

	/**
	 * Returns and removes the first card on the library. The method creates a LocalEvent to notify the observers of the update
	 * on the library.
	 * 
	 * @return a Card
	 * @see LocalEvent
	 */
	public Card draw(Player player) {

		Card card = this.cards.removeFirst();

		if (card != null) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(player, EventType.DRAW, card));
		}
		return card;
	}

	/**
	 * Removes and returns the x first cards from the library
	 * 
	 * @param x the number of cards to draw
	 * @return the list of card removed from the library
	 * @see LocalEvent
	 */
	public List<Card> draw(Player player, int x) {

		// if xFirst is superior at the current number of cards in the library, the parameter value is reduced
		if (x > this.cards.size()) {
			x = this.cards.size();
			Logger.getLogger(this.getClass()).warn("Try to remove more cards than the library have");
		}
		List<Card> xFirstCards = new ArrayList<Card>(x);

		int i = x;
		do {
			xFirstCards.add(this.cards.removeFirst());
		} while (--i > 0);

		if (!xFirstCards.isEmpty()) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(player, EventType.DRAW_X, xFirstCards));
		}
		return xFirstCards;
	}

	/**
	 * Remove a card from the library. The method creates a LocalEvent to notify the observers of the update on the library.
	 * 
	 * @param card the card to remove
	 * @return
	 * @see LocalEvent
	 */
	public boolean remove(Player player, Card card) {

		if (this.cards.remove(card)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(player, EventType.REMOVE_FROM_LIBRARY, card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Shuffle the cards in the library. The method creates a LocalEvent to notify the observers of the update on the library.
	 * 
	 * @see Collections#shuffle(List)
	 * @see LocalEvent
	 */
	public void shuffle(Player player) {

		Collections.shuffle(this.cards);
		super.setChanged();
		super.notifyObservers(new LocalEvent(player, EventType.SHUFFLE));
	}

	/**
	 * Update the index of a card in the library
	 * 
	 * @param card the card from the library
	 * @param newIndex the new index of the card in the library
	 * @return <code>true</code> if the card's index has been updated, if <code>false</code> the card was not in the library
	 */
	public boolean changeCardIndex(Player player, Card card, int newIndex) {

		if (this.cards.remove(card)) {
			this.cards.add(newIndex, card);
			super.setChanged();
			super.notifyObservers(new LocalEvent(player, EventType.CHANGE_CARD_INDEX, card, new Integer(newIndex)));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Clear the library. The method creates a LocalEvent to notify the observers of the update on the library.
	 * 
	 * @see LocalEvent
	 */
	public void clear(Player player) {

		if (!this.cards.isEmpty()) {
			this.cards.clear();
			super.setChanged();
			super.notifyObservers(new LocalEvent(player, EventType.CLEAR_LIBRARY));
		}
	}

	@Override
	public String toString() {

		return "Library [" + cards.size() + " / " + initialSize + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + initialSize;
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
		Library other = (Library) obj;
		if (initialSize != other.initialSize)
			return false;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		return true;
	}
}
