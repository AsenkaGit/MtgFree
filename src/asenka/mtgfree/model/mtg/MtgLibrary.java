package asenka.mtgfree.model.mtg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * 
 * @author asenka
 */
public class MtgLibrary {

	/**
	 * 
	 */
	private final LinkedList<MtgCard> stackOfCards;

	/**
	 * 
	 */
	private final int initialAmountOfCards;

	/**
	 * 
	 * @param cards
	 * @throws IllegalArgumentException
	 */
	public MtgLibrary(Collection<MtgCard> cards) throws IllegalArgumentException {

		if (cards == null) {
			throw new IllegalArgumentException("Try to initialize a library with a null deck");
		} else if (cards.size() < MtgDeck.MINIMUM_DECK_SIZE) {
			throw new IllegalArgumentException("Try to initialize a library with an not complete deck");
		}
		this.initialAmountOfCards = cards.size();
		this.stackOfCards = new LinkedList<MtgCard>(cards);
	}

	/**
	 * 
	 * @return
	 */
	public List<MtgCard> getStackOfCards() {

		return stackOfCards;
	}

	/**
	 * 
	 * @return
	 */
	public int getInitialAmountOfCards() {

		return initialAmountOfCards;
	}

	/**
	 * 
	 * @return
	 */
	public int getRemainingAmountOfCards() {

		return this.stackOfCards.size();
	}

	/**
	 * 
	 * @param xFirst
	 * @return
	 */
	public List<MtgCard> getFirstCards(int xFirst) {

		if (xFirst > this.stackOfCards.size()) {
			xFirst = this.stackOfCards.size();
		}
		List<MtgCard> xFirstList = new ArrayList<MtgCard>(xFirst);

		for (int i = 0; i < xFirst; i++) {
			xFirstList.add(this.stackOfCards.get(i));
		}
		return xFirstList;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public MtgCard getCardAt(int index) {

		return this.stackOfCards.get(index);
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public MtgCard removeCardAt(int index) {

		return this.stackOfCards.remove(index);
	}

	/**
	 * 
	 * @param xFirst the number of card to get
	 * @return an array list with the x first cards in the library
	 */
	public List<MtgCard> removeFirstCards(int xFirst) {

		if (xFirst > this.stackOfCards.size()) {
			xFirst = this.stackOfCards.size();
		}
		List<MtgCard> xFirstList = new ArrayList<MtgCard>(xFirst);

		for (int i = 0; i < xFirst; i++) {
			xFirstList.add(this.stackOfCards.remove(i));
		}
		return xFirstList;
	}

	/**
	 * @return <code>true</code> if this library contains no cards
	 */
	public boolean isEmpty() {

		return this.stackOfCards.isEmpty();
	}

	/**
	 * Removes and returns the first card in the stack
	 * 
	 * @return a card
	 */
	public MtgCard draw() {

		return this.stackOfCards.removeFirst();
	}

	/**
	 * Randomize the order of the card in the stack
	 */
	public void shuffle() {

		Collections.shuffle(this.stackOfCards);
	}
	
	/**
	 * 
	 * @param card
	 */
	public void addFirst(MtgCard card) {
		this.stackOfCards.addFirst(card);
	}
	
	
	/**
	 * 
	 * @param card
	 */
	public void addLast(MtgCard card) {
		this.stackOfCards.addLast(card);
	}

	@Override
	public String toString() {

		StringBuffer buf = new StringBuffer();
		ListIterator<MtgCard> it = this.stackOfCards.listIterator(0);
		
		buf.append(this.getClass().getSimpleName() + " (" + stackOfCards.size() + "/" + initialAmountOfCards + ")\n");
		
		while (it.hasNext()) {
			buf.append("[" + it.nextIndex() + "] \t--> " + it.next());
		}
		return buf.toString();
	}

}
