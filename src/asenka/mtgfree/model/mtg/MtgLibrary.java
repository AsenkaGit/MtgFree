package asenka.mtgfree.model.mtg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

import asenka.mtgfree.model.mtg.events.MtgLibraryAddCardsEvent;
import asenka.mtgfree.model.mtg.events.MtgLibraryRemoveCardsEvent;
import asenka.mtgfree.model.mtg.events.MtgLibraryReorderEvent;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This class represents the player's library used during a game
 * 
 * @author asenka
 */
public class MtgLibrary extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 464786817994595291L;

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
	 * Removes and returns the first card in the stack. The observers are notified that the library is updated (with one card
	 * less)
	 * 
	 * @return the top card from the library (removed from the library)
	 */
	public MtgCard draw() {

		MtgCard card = this.cards.removeFirst();

		if (card != null) {
			super.setChanged();
			super.notifyObservers(new MtgLibraryRemoveCardsEvent(this, card));
		}
		return card;
	}

	/**
	 * Returns the cards from the library
	 * 
	 * @return an unmodifiable list of cards
	 * @see Collections#unmodifiableList(List)
	 */
	public List<MtgCard> getCards() {

		// We don't want to have the cards from the library updated outside of the library.
		// That's why we return an unmodifiable list of cards instead of a reference to the library list
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
	 * Returns the x first cards from the library. The returned cards remain inside the library 
	 * (no need to notify the observers)
	 * 
	 * @param xFirst the number of cards to get
	 * @return the x first cards from the library
	 */
	public List<MtgCard> getFirstCards(int xFirst) {

		// if xFirst is superior at the current number of cards in the library, the parameter value is reduced
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
	public MtgCard getCardAt(final int index) {

		return this.cards.get(index);
	}

	/**
	 * Add one card at the top of the library and notify the observers
	 * 
	 * @param card
	 * @see MtgLibraryAddCardsEvent
	 */
	public void addFirst(final MtgCard card) {

		this.cards.addFirst(card);
		super.setChanged();
		super.notifyObservers(new MtgLibraryAddCardsEvent(this, new MtgCard[] { card }, new int[] {0}));
	}

	/**
	 * Add one card at the bottom of the library and notify the observers
	 * 
	 * @param card
	 * @see MtgLibraryAddCardsEvent
	 */
	public void addLast(final MtgCard card) {

		this.cards.addLast(card);
		super.setChanged();
		super.notifyObservers(new MtgLibraryAddCardsEvent(this, new MtgCard[] { card }, new int[] {this.cards.size() - 1}));
	}

	/**
	 * Remove and returns the card at the specified index and notify the observers
	 * 
	 * @param index the index to remove the card
	 * @return the card removed
	 * @throws IndexOutOfBoundsException
	 * @see MtgLibraryRemoveCardsEvent
	 */
	public MtgCard removeCardAt(final int index) {

		MtgCard card = this.cards.remove(index);

		if (card != null) {
			super.setChanged();
			super.notifyObservers(new MtgLibraryRemoveCardsEvent(this, card));
		}
		return card;

	}

	/**
	 * Returns and removes the x first cards from the library. It notify the observers
	 * that new cards are removed
	 * 
	 * @param xFirst the number of cards to remove from the library
	 * @return the x first cards from the library
	 * @see MtgLibraryRemoveCardsEvent
	 */
	public List<MtgCard> removeFirstCards(int xFirst) {

		if (xFirst > this.cards.size()) {
			xFirst = this.cards.size();
		}
		MtgCard[] xFirstCards = new MtgCard[xFirst];

		for (int i = 0; i < xFirst; i++) {
			xFirstCards[i] = this.cards.remove(i);
		}
		super.setChanged();
		super.notifyObservers(new MtgLibraryRemoveCardsEvent(this, xFirstCards));
		
		return Arrays.asList(xFirstCards);
	}

	/**
	 * @return <code>true</code> if this library contains no cards
	 */
	public boolean isEmpty() {

		return this.cards.isEmpty();
	}

	/**
	 * Randomize the order of the card in the stack. 
	 * It notify the observers that the library has been updated
	 */
	public void shuffle() {

		Collections.shuffle(this.cards);
		
		super.setChanged();
		super.notifyObservers(new MtgLibraryReorderEvent(this));
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

	// /**
	// * Check if the card has a state and update the context in the card state
	// *
	// * @param card the card to update
	// * @param context the context to set on the card
	// */
	// private static void updateCardState(MtgCard card, MtgContext context) {
	//
	// card.setContext(context);
	// }
}
