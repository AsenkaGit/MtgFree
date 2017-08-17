package asenka.mtgfree.model;


import java.util.*;

/**
 * A deck is a list if cards that  a player use during a Mtg game. The main cards in the deck will be used as the player library. the side cards are just here to be remembered as good candidates for the main deck.
 * @author Asenka
 */
public class MtgDeck {

	/**
	 * Default constructor
	 */
	public MtgDeck() {
	}

	/**
	 * The id of the deck (from the database)
	 */
	private int id;

	/**
	 * Each player should give a name to its deck.
	 */
	private String name;

	/**
	 * The description is not mandatory, but it is a small text explaining the deck.
	 */
	private String description;

	/**
	 * The cards meant to go to the player library during a Mtg game.
	 */
	private MtgCard mainCards;

	/**
	 * The cards that will not be played, but it is nice to have them remembered because they may work with this deck.
	 */
	private Set<MtgCard> sideCards;

	/**
	 * The owner of the deck.
	 */
	private MtgPlayer owner;

	/**
	 * @param list 
	 * @param card 
	 * @return
	 */
	public void addCard(MtgDeckList list, MtgCard card) {
		// TODO implement here
	}

	/**
	 * @param list 
	 * @param card 
	 * @return
	 */
	public void removeCard(MtgDeckList list, MtgCard card) {
		// TODO implement here
	}

	/**
	 * This enumeration is useful inside the MtgDeck class to indicate the targeted list to perform an operation :
	 * > the main cards in the deck
	 * > the sideboard
	 */
	public enum MtgDeckList {
		MAIN,
		SIDEBOARD
	}

}