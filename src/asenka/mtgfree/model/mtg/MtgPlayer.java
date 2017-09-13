package asenka.mtgfree.model.mtg;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.state.MtgCardState;
import asenka.mtgfree.model.mtg.mtgcard.state.MtgContext;

/**
 * This class represents a player and stores all the data related to the player
 * <ul>
 * <li>name</li>
 * <li>life points</li>
 * <li>energy counters</li>
 * <li>poison counters</li>
 * <li>the cards in the player's hand</li>
 * <li>the cards in the player's exile area</li>
 * <li>the cards in the player's grave yard</li>
 * <li>the cards in the player's library</li>
 * <li>the current deck</li>
 * <li>the available decks</li>
 * </ul>
 * @author asenka
 */
public class MtgPlayer implements Comparable<MtgPlayer> {

	/**
	 * The collator used to compare the deck's names
	 */
	private static final Collator DEFAULT_COLLATOR = Collator.getInstance();

	/**
	 * 
	 */
	public static final int DEFAULT_LIFE_POINTS = 20;

	/**
	 * The player name
	 */
	private String name;

	/**
	 * The remaining life points of the player during the game (by default it starts at 20)
	 */
	private int remainingLifePoints;

	/**
	 * The number of energy counters during a game (0 by default)
	 */
	private int energyCounters;

	/**
	 * The number of poison counters during a game (0 by default)
	 */
	private int poisonCounters;

	/**
	 * The cards in the player hand during a game
	 */
	private List<MtgCard> hand;

	/**
	 * The cards in the grave yard
	 */
	private List<MtgCard> exile;

	/**
	 * The exiled cards during a game
	 */
	private List<MtgCard> graveyard;

	/**
	 * The player library during a game
	 */
	private MtgLibrary library;

	/**
	 * All the decks available for this player
	 */
	private Set<MtgDeck> availableDecks;

	/**
	 * The selected deck to play with
	 */
	private MtgDeck currentDeck;

	/**
	 * Constructor
	 * 
	 * @param name the player's name
	 */
	public MtgPlayer(String name) {

		super();
		this.name = name;
		this.remainingLifePoints = DEFAULT_LIFE_POINTS;
		this.energyCounters = 0;
		this.poisonCounters = 0;
		this.hand = new ArrayList<MtgCard>(7);
		this.graveyard = new ArrayList<MtgCard>();
		this.exile = new ArrayList<MtgCard>();
		this.availableDecks = new HashSet<MtgDeck>();
		this.currentDeck = null;
		this.library = null;
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
	public int getRemainingLifePoints() {

		return remainingLifePoints;
	}

	/**
	 * @param remainingLifePoints
	 */
	public void setRemainingLifePoints(int remainingLifePoints) {

		this.remainingLifePoints = remainingLifePoints;
	}

	/**
	 * @return
	 */
	public int getPoisonCounters() {

		return poisonCounters;
	}

	/**
	 * @param poisonCounters
	 */
	public void setPoisonCounters(int poisonCounters) {

		this.poisonCounters = poisonCounters;
	}

	/**
	 * @return
	 */
	public int getEnergyCounters() {

		return energyCounters;
	}

	/**
	 * @return
	 */
	public List<MtgCard> getHand() {

		return hand;
	}

	/**
	 * @return
	 */
	public List<MtgCard> getExile() {

		return exile;
	}

	/**
	 * @return
	 */
	public List<MtgCard> getGraveyard() {

		return graveyard;
	}

	/**
	 * @return the library
	 */
	public MtgLibrary getLibrary() {

		return library;
	}

	/**
	 * @param library the library to set
	 */
	public void setLibrary(MtgLibrary library) {

		this.library = library;
	}

	/**
	 * @return
	 */
	public Set<MtgDeck> getAvailableDecks() {

		return availableDecks;
	}

	/**
	 * @return
	 */
	public MtgDeck getCurrentDeck() {

		return currentDeck;
	}

	/**
	 * 
	 * @param currentDeck
	 * @throws IllegalArgumentException
	 */
	public void setCurrentDeck(MtgDeck currentDeck) throws IllegalArgumentException {

		if (!this.availableDecks.contains(currentDeck)) {
			throw new IllegalArgumentException("The deck " + currentDeck + "is not in the available list of decks " + availableDecks);
		}
		this.currentDeck = currentDeck;
	}

	/**
	 * Add the card to the grave yard list and update the card state
	 * 
	 * @param card the card to add
	 * @throws IllegalArgumentException if the card does not have a state yet
	 */
	public void addCardToGraveyard(MtgCard card) {

		MtgCardState state = card.getState();

		if (state == null) {
			throw new IllegalArgumentException("The card does not have a state : " + card);
		}
		state.setContext(MtgContext.GRAVEYARD);
		this.graveyard.add(card);
	}

	/**
	 * Add the card to the exile list and update the card state
	 * 
	 * @param card the card to add
	 * @throws IllegalArgumentException if the card does not have a state yet
	 */
	public void addCardToExile(MtgCard card) {

		MtgCardState state = card.getState();

		if (state == null) {
			throw new IllegalArgumentException("The card does not have a state : " + card);
		}
		state.setContext(MtgContext.EXILE);
		this.exile.add(card);
	}

	/**
	 * Add the card to the player hand and update the card state
	 * 
	 * @param card the card to add
	 * @throws IllegalArgumentException if the card does not have a state yet
	 */
	public void addCardToHand(MtgCard card) {

		MtgCardState state = card.getState();

		if (state == null) {
			throw new IllegalArgumentException("The card does not have a state : " + card);
		}
		state.setContext(MtgContext.HAND);
		this.hand.add(card);
	}

	/**
	 * 
	 * @param deck
	 */
	public void addAvailableDeck(MtgDeck deck) {

		this.availableDecks.add(deck);
	}

	/**
	 * @param card the card to remove from the exile list
	 */
	public void removeCardFromExile(MtgCard card) {

		this.exile.remove(card);
	}

	/**
	 * @param card the card to remove from the player's hand
	 */
	public void removeCardFromHand(MtgCard card) {

		this.hand.remove(card);
	}

	/**
	 * @param card the card to remove from the player's grave yard
	 */
	public void removeCardFromGraveyard(MtgCard card) {

		this.graveyard.remove(card);
	}

	/**
	 * @param deck the deck to remove from the available decks
	 */
	public void removeAvailableDeck(MtgDeck deck) {

		this.availableDecks.remove(deck);
	}

	@Override
	public int compareTo(MtgPlayer player) {

		return DEFAULT_COLLATOR.compare(this.name, player.name);
	}
}
