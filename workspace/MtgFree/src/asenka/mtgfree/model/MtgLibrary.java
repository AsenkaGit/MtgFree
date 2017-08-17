package asenka.mtgfree.model;


import java.util.*;

/**
 * The library is a special list of cards used only during a game with specific features such as :
 * > draw a card
 * > change the order of the cards in the list
 * > pick up a specific card (when the player seek in his/her library)
 * @author Asenka
 */
public class MtgLibrary {

	/**
	 * Default constructor
	 */
	public MtgLibrary() {
	}

	/**
	 * The cards in the player's library.
	 */
	private Set<MtgCard> cards;

}