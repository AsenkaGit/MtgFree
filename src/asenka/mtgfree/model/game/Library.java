package asenka.mtgfree.model.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import asenka.mtgfree.events.LocalEvent;

import static asenka.mtgfree.events.LocalEvent.Type.*;

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
public class Library extends AbstractGameObject {

	/**
	 * The generated id for serialization
	 */
	private static final long serialVersionUID = 3841596534929597985L;

	/**
	 * Constant used to indicate the top of the library
	 */
	public static final int TOP = 0;

	/**
	 * Constant used to indicate the bottom of the library
	 */
	public static final int BOTTOM = Integer.MAX_VALUE;

	/**
	 * The initial size of the list
	 */
	private final int initialSize;

	/**
	 * The linked list of cards in the library.
	 */
	private LinkedList<Card> cards;

	/**
	 * Build a library object. A convenient alternative way to create a library is to use the
	 * method {@link Deck#buildLibrary()}. 
	 * 
	 * @param cards a collection of cards
	 */
	public Library(Collection<Card> cards) {

		this.cards = new LinkedList<>(cards);
		this.initialSize = cards.size();
	}

	/**
	 * @return the initial amount of cards in the library
	 */
	public int getInitialSize() {

		return this.initialSize;
	}

	/**
	 * @return the current size (number of cards) in the library
	 */
	public int size() {

		return this.cards.size();
	}

	/**
	 * @param card the card to check
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
	 * Update the cards in the library with the cards from another library
	 * 
	 * @param cards a list of cards
	 */
	public void setCards(List<Card> cards) {

		this.cards = new LinkedList<Card>(cards);
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
	 * @param index the index of the desired card in the library
	 * @return a card from the library at a specific location
	 */
	public Card get(int index) {

		return this.cards.get(index);
	}

	/**
	 * Add a card on the top of the library. The method creates a LocalEvent to notify the observers of the update on the library.
	 * 
	 * @param player the player performing the action. Used to create the event
	 * @param card the card to add
	 * @see LocalEvent
	 */
	public void addOnTop(Player player, final Card card) {

		this.cards.addFirst(card);
		super.setChanged();
		super.notifyObservers(new LocalEvent(ADD_CARD_TO_LIBRARY, player, card, Library.TOP));
	}

	/**
	 * Add a card at the bottom of the library. The method creates a LocalEvent to notify the observers of the update on the
	 * library.
	 * 
	 * @param player the player performing the action. Used to create the event
	 * @param card the card to add
	 * @see LocalEvent
	 */
	public void addToBottom(Player player, final Card card) {

		this.cards.addLast(card);
		super.setChanged();
		super.notifyObservers(new LocalEvent(ADD_CARD_TO_LIBRARY, player, card, Library.BOTTOM));
	}

	/**
	 * Add a card at the specified index location 
	 * 
	 * @param player the player performing the action. Used to create the event
	 * @param card the card to add
	 * @param index the index to insert the card. If <code>x >= cards.size()</code>,the card is inserted
	 *        at the bottom of the library : {@link Library#addToBottom(Card)}
	 * @see LocalEvent
	 */
	public void addAt(Player player, final Card card, int index) {

		if (index >= this.cards.size()) {
			this.addToBottom(player, card);
		} else {
			this.cards.add(index, card);
			super.setChanged();
			super.notifyObservers(new LocalEvent(ADD_CARD_TO_LIBRARY, player, card, index));
		}
	}

	/**
	 * Returns and removes the first card on the library. The method creates a LocalEvent to notify the observers of the update on
	 * the library.
	 * 
	 * @param player the player performing the action. Used to create the event
	 * @return a Card
	 * @see LocalEvent
	 */
	public Card removeFirst(Player player) {

		Card card = this.cards.removeFirst();

		if (card != null) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(REMOVE_CARD_FROM_LIBRARY, player, card));
		}
		return card;
	}

	/**
	 * Removes and returns the x first cards from the library
	 * 
	 * @param player the player performing the action. Used to create the event
	 * @param x the number of cards to remvoes from the library and return as a list
	 * @return the list of cards removed from the library
	 * @see LocalEvent
	 */
	public List<Card> removeFirst(Player player, int x) {

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
			super.notifyObservers(new LocalEvent(REMOVE_CARD_FROM_LIBRARY, player, xFirstCards.toArray()));
		}
		return xFirstCards;
	}

	/**
	 * Remove a card from the library. The method creates a LocalEvent to notify the observers of the update on the library.
	 * 
	 * @param player the player performing the action. Used to create the event
	 * @param card the card to remove
	 * @return <code>true</code> if card was in the player's library
	 * @see LocalEvent
	 */
	public boolean remove(Player player, Card card) {

		if (this.cards.remove(card)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(REMOVE_CARD_FROM_LIBRARY, player, card));
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
		super.notifyObservers(new LocalEvent(SHUFFLE_LIBRARY, player));
	}

	// /**
	// * Update the index of a card in the library
	// *
	// * @param card the card from the library
	// * @param newIndex the new index of the card in the library
	// * @return <code>true</code> if the card's index has been updated, if <code>false</code> the card was not in the library
	// */
	// public boolean changeCardIndex(Player player, Card card, int newIndex) {
	//
	// if (this.cards.remove(card)) {
	// this.cards.add(newIndex, card);
	// super.setChanged();
	// super.notifyObservers(new LocalEvent(CHANGE_CARD_INDEX, player, card, new Integer(newIndex)));
	// return true;
	// } else {
	// return false;
	// }
	// }

	/**
	 * Clear the library. The method creates a LocalEvent to notify the observers of the update on the library.
	 * 
	 * @see LocalEvent
	 */
	public void clear(Player player) {

		if (!this.cards.isEmpty()) {
			this.cards.clear();
			super.setChanged();
			super.notifyObservers(new LocalEvent(CLEAR_LIBRARY, player));
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
