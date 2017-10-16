package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import asenka.mtgfree.model.events.LibraryEvent;

public class Library extends Observable implements Serializable {

	private final int initialSize;

	private final LinkedList<Card> cards;

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
	 * Returns an unmodifiable list of Cards
	 * 
	 * @return the list of cards
	 * @see Collections#unmodifiableList(List)
	 */
	public List<Card> getCards() {

		return Collections.unmodifiableList(cards);
	}
	
	/**
	 * Returns the x first cards from the library. The returned cards remain inside the library 
	 * (no need to notify the observers)
	 * 
	 * @param xFirst the number of cards to get
	 * @return the x first cards from the library
	 */
	public List<Card> getFirstCards(int xFirst) {
	
		// if xFirst is superior at the current number of cards in the library, the parameter value is reduced
		if (xFirst > this.cards.size()) {
			xFirst = this.cards.size();
		}
		List<Card> xFirstList = new ArrayList<Card>(xFirst);
	
		for (int i = 0; i < xFirst; i++) {
			xFirstList.add(this.cards.get(i));
		}
		return xFirstList;
	}

	/**
	 * Get the card at the specified index
	 * 
	 * @param index the index
	 * @return a card from the library at a specific location
	 */
	public Card getCardAt(final int index) {
	
		return this.cards.get(index);
	}

	/**
	 * 
	 */
	public void shuffle() {
		
		Collections.shuffle(this.cards);
		super.setChanged();
		super.notifyObservers(new LibraryEvent("shuffle", "cards", null));	
	}
	
	/**
	 * 
	 * @return
	 */
	public Card draw() {
		
		Card card = this.cards.removeFirst();

		if (card != null) {
			super.setChanged();
			super.notifyObservers(new LibraryEvent("remove", "cards", card));
		}
		return card;
	}

}
