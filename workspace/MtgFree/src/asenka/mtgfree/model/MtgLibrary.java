package asenka.mtgfree.model;


import java.util.List;
import java.util.Random;
import java.util.Collection;
import java.util.LinkedList;

/**
 * The library is a special list of cards used only during a game with specific features such as :
 * > draw a card
 * > change the order of the cards in the list
 * > pick up a specific card (when the player seek in his/her library)
 * @author Asenka
 */
public class MtgLibrary {


	/**
	 * The cards in the player's library.
	 */
	private LinkedList<MtgCard> cards;
	
	public static final int FIRST = 0;
	public static final int LAST = -1;
	public static final int RANDOM = -2;
	
	
	/**
	 * Default constructor
	 */
	public MtgLibrary() {
		this.cards = new LinkedList<MtgCard>();
	}


	/**
	 * @return the cards
	 */
	public List<MtgCard> getCards() {
		return cards;
	}


	/**
	 * @param cards the cards to set
	 */
	public void setCards(Collection<MtgCard> cards) {
		this.cards = new LinkedList<MtgCard>(cards);
	}
	
	/**
	 * Returns and <b>removes</b> the first card in the list
	 * @return the first card in the list or <code>null</code> if the list is empty
	 */
	public MtgCard drawCard() {
		return this.cards.poll();
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.size();
	}
	
	/**
	 * 
	 * @param index
	 * @param card
	 */
	public void addCard(int index, MtgCard card) {

		if(index < RANDOM) {
			throw new IllegalArgumentException("You try to add a card in the player library with a wrong index : " + index);
		} else {

			switch(index) {
			case FIRST : this.cards.addFirst(card); break;
			case LAST  : this.cards.addLast(card); break;
			case RANDOM : this.cards.add(getRandomIndex(), card);
			default:this.cards.add(index, card);
			}
		}
	}
	
	/**
	 * 
	 * @param card
	 */
	public void removeCard(MtgCard card) {
		this.cards.remove(card);
	}
	
	/**
	 * 
	 * @param currentIndex
	 * @param newIndex
	 */
	public void moveCard(int currentIndex, int newIndex) {
		
		int maxIndex = this.cards.size();
		
		if(currentIndex < 0 || newIndex < 0 || currentIndex >= maxIndex || newIndex >= maxIndex) {
			throw new IllegalArgumentException("Wrong indexes");
		} else {
			this.cards.add(newIndex, this.cards.remove(currentIndex));
		}
	}
	
	/**
	 * 
	 */
	public void clear() {
		this.cards.clear();
	}

	/**
	 * 
	 * @return
	 */
	private int getRandomIndex() {
		return (new Random()).nextInt(this.cards.size() + 1);
	}

}