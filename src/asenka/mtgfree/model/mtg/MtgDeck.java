package asenka.mtgfree.model.mtg;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import asenka.mtgfree.model.mtg.exceptions.MtgDeckException;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * <p>
 * Represents a MTG Deck : a list of cards selected by the player to play with. A deck contains two lists :
 * </p>
 * <ul>
 * <li>The main list, for the cards that the player will play</li>
 * <li>The sideboard, for the cards he wants to have around to put it in the main deck quickly between two games</li>
 * <p>
 * <b>Note</b> : the values in a deck are not localized since they come from the player.
 * </p>
 * 
 * @author asenka
 */
public class MtgDeck implements Comparable<MtgDeck> {

	/**
	 * The collator used to compare the deck's names
	 */
	// We use a default collator since we cannot know for sure witch language will be used to name the decks
	// A French person, may use English names, or something that does not belong to any language anyway...
	private static final Collator DEFAULT_COLLATOR = Collator.getInstance();

	/**
	 * The minimum size of a deck in the main list of cards
	 */
	public static final int MINIMUM_DECK_SIZE = 60;

	/**
	 * The maximum size of the sideboard
	 */
	public static final int MAXIMUM_SIDEBOARD_SIZE = 15;

	/**
	 * The id of the deck from the data base (primary key)
	 */
	private int id;

	/**
	 * The name of the deck
	 */
	private String name;

	/**
	 * Not mandatory, the player can still add some notes explaining the deck mechanics
	 */
	private String description;

	/**
	 * The card to play with.
	 */
	private List<MtgCard> mainCards;

	/**
	 * The optional cards (it may remain empty)
	 */
	private List<MtgCard> sideBoard;

	/**
	 * Full constructor
	 * 
	 * @param id
	 * @param name
	 * @param description
	 */
	public MtgDeck(int id, String name, String description) {

		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.mainCards = new ArrayList<MtgCard>(MINIMUM_DECK_SIZE);
		this.sideBoard = new ArrayList<MtgCard>();
	}

	/**
	 * @return
	 */
	public int getId() {

		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {

		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {

		this.description = description;
	}

	/**
	 * @return
	 */
	public List<MtgCard> getMainCards() {

		return mainCards;
	}

	/**
	 * @param mainCards
	 */
	public void setMainCards(Collection<MtgCard> mainCards) {

		this.mainCards = new ArrayList<MtgCard>(mainCards);
	}

	/**
	 * @return
	 */
	public List<MtgCard> getSideBoard() {

		return sideBoard;
	}

	/**
	 * @param sideBoard
	 */
	public void setSideBoard(Collection<MtgCard> sideBoard) {

		this.sideBoard = new ArrayList<MtgCard>(sideBoard);
	}

	/**
	 * Adds cards to the specified list of the deck
	 * 
	 * @param addTo MAIN or SIDEBOARD
	 * @param cards the card(s) to add
	 * @throws MtgDeckException 
	 * @see MtgDeckList
	 */
	public void addCards(MtgDeckList addTo, MtgCard... cards) throws MtgDeckException {

		for (MtgCard card : cards) {

			if (addTo == MtgDeckList.MAIN) {
				this.mainCards.add(card);
			} else if (this.sideBoard.size() < MAXIMUM_SIDEBOARD_SIZE) {
				this.sideBoard.add(card);
			} else {
				throw new MtgDeckException("The side board cannot contains more than " + MAXIMUM_SIDEBOARD_SIZE + " cards");
			}
		}
	}

	/**
	 * Removes cards from the specified list of the deck
	 * 
	 * @param removeFrom MAIN or SIDEBOARD
	 * @param cards the card(s) to add
	 * @see MtgDeckList
	 */
	public void removeCards(MtgDeckList removeFrom, MtgCard... cards) {

		for (MtgCard card : cards) {

			if (removeFrom == MtgDeckList.MAIN) {
				this.mainCards.remove(card);
			} else {
				this.sideBoard.remove(card);
			}
		}
	}

	@Override
	public String toString() {

		return "[" + id + ", " + name + ", " + description + ", " + mainCards.size() + ", " + sideBoard.size() + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((mainCards == null) ? 0 : mainCards.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sideBoard == null) ? 0 : sideBoard.hashCode());
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
		MtgDeck other = (MtgDeck) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (mainCards == null) {
			if (other.mainCards != null)
				return false;
		} else if (!mainCards.equals(other.mainCards))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sideBoard == null) {
			if (other.sideBoard != null)
				return false;
		} else if (!sideBoard.equals(other.sideBoard))
			return false;
		return true;
	}

	@Override
	public int compareTo(MtgDeck deck) {

		return DEFAULT_COLLATOR.compare(this.name, deck.name);
	}
}
