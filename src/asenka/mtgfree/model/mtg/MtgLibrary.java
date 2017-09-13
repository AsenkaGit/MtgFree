package asenka.mtgfree.model.mtg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This class represents the player's library used during a game
 * 
 * @author asenka
 */
public class MtgLibrary {

	/**
	 * The cards in the library
	 */
	// The LinkedList is the best fit for the library needs. Check javadoc
	private LinkedList<MtgCard> cards;

	/**
	 * The initial number of cards in the list
	 */
	private final int initialAmountOfCards;

	/**
	 * Constructor
	 * 
	 * @param cards any collection if cards 
	 * @throws IllegalArgumentException if cards is <code>null</code> or if cards does not contains the minimum number of cards
	 *         required (usually it is 60)
	 */
	public MtgLibrary(Collection<MtgCard> cards) throws IllegalArgumentException {

		if (cards == null) {
			throw new IllegalArgumentException("Try to initialize a library with a null deck");
		} else if (cards.size() < MtgDeck.MINIMUM_DECK_SIZE) {
			throw new IllegalArgumentException("Try to initialize a library with an not complete deck");
		}
		this.initialAmountOfCards = cards.size();
		this.cards = new LinkedList<MtgCard>(cards);
	}

	/**
	 * Removes and returns the first card in the stack
	 * 
	 * @return the top card from the library
	 */
	public MtgCard draw() {

		return this.cards.removeFirst();
	}

	/**
	 * Returns the cards from the library
	 * 
	 * @return an unmodifiable list of cards
	 */
	public List<MtgCard> getCards() {

		return Collections.unmodifiableList(cards);
	}

	/**
	 * @return the initial number of cards in the list.
	 */
	public int getInitialAmountOfCards() {

		return initialAmountOfCards;
	}

	/**
	 * @return the number of remaining cards in the library
	 */
	public int getRemainingAmountOfCards() {

		return this.cards.size();
	}

	/**
	 * Returns the x first cards from the library
	 * 
	 * @param xFirst
	 * @return the x first cards from the library
	 */
	public List<MtgCard> getFirstCards(int xFirst) {

		if (xFirst > this.cards.size()) {
			xFirst = this.cards.size();
		}
		List<MtgCard> xFirstList = new ArrayList<MtgCard>(xFirst);

		for (int i = 0; i < xFirst; i++) {
			xFirstList.add(this.cards.get(i));
		}
		return xFirstList;
	}

	/**
	 * Get the card at the specified index
	 * 
	 * @param index the index
	 * @return a mtg card from the library
	 */
	public MtgCard getCardAt(int index) {

		return this.cards.get(index);
	}

	/**
	 * Add one card at the top of the library
	 * 
	 * @param card
	 */
	public void addFirst(MtgCard card) {

		this.cards.addFirst(card);
	}

	/**
	 * add one card at the bottom of the library
	 * 
	 * @param card
	 */
	public void addLast(MtgCard card) {

		this.cards.addLast(card);
	}

	/**
	 * Remove and returns the card at the specified index
	 * 
	 * @param index the index
	 * @return the card removed
	 */
	public MtgCard removeCardAt(int index) {

		return this.cards.remove(index);
	}

	/**
	 * Returns and removes the x first cards from the library
	 * 
	 * @param xFirst
	 * @return the x first cards from the library
	 */
	public List<MtgCard> removeFirstCards(int xFirst) {

		if (xFirst > this.cards.size()) {
			xFirst = this.cards.size();
		}
		List<MtgCard> xFirstList = new ArrayList<MtgCard>(xFirst);

		for (int i = 0; i < xFirst; i++) {
			xFirstList.add(this.cards.remove(i));
		}
		return xFirstList;
	}

	/**
	 * @return <code>true</code> if this library contains no cards
	 */
	public boolean isEmpty() {

		return this.cards.isEmpty();
	}

	/**
	 * Randomize the order of the card in the stack
	 */
	public void shuffle() {

		Collections.shuffle(this.cards);
	}

	@Override
	public String toString() {

		StringBuffer buf = new StringBuffer();
		ListIterator<MtgCard> it = this.cards.listIterator(0);

		buf.append(this.getClass().getSimpleName() + " (" + cards.size() + "/" + initialAmountOfCards + ")\n");

		while (it.hasNext()) {
			buf.append("[" + it.nextIndex() + "] \t--> " + it.next());
		}
		return buf.toString();
	}

}
