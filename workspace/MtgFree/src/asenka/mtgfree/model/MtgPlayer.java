package asenka.mtgfree.model;


import java.util.*;

/**
 * This class stores the data about a player.
 * @author Asenka
 */
public class MtgPlayer {

	/**
	 * Default constructor
	 */
	public MtgPlayer() {
	}

	/**
	 * The username of the player. This username should be unique on the server because it is used as an ID.
	 */
	private String username;

	/**
	 * The user password. This data should never be stored in the model without encryption.
	 */
	private String password;

	/**
	 * The current number of life of the player.
	 */
	private int remainingLife;

	/**
	 * The poison counter on the player.
	 */
	private int poisonCounters;

	/**
	 * The energy counter of the player.
	 */
	private int energyCounters;

	/**
	 * A free text to express any status of the player. It can be used to manage any unusual stats. The player can write here anything he/she wants or needs.
	 */
	private String battlefieldComment;

	/**
	 * The list of deck owned by this player.
	 */
	private Set<MtgDeck> decks;

	/**
	 * 
	 */
	private MtgDeck currentDeck;

	/**
	 * The selected cards of the player. They are some rules about cards selection :
	 * > it can be empty or contains several cards
	 * > if more than one cards selected : they should ALL belong to the same context (hand, battlefield, graveyard or exile)
	 */
	private Set<MtgCard> selectedCards;

	/**
	 * 
	 */
	private MtgLibrary library;

	/**
	 * 
	 */
	private Set<MtgCard> cardsInGraveyard;

	/**
	 * 
	 */
	private MtgSimpleList graveyard;

	/**
	 * 
	 */
	private MtgSimpleList hand;

	/**
	 * 
	 */
	private MtgSimpleList exile;

}