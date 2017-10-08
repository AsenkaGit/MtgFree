package asenka.mtgfree.model.mtg;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.model.mtg.events.MtgPlayerEnergyUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgPlayerLifeUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgPlayerPoisonUpdatedEvent;
import asenka.mtgfree.model.mtg.exceptions.MtgDeckException;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;

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
 * 
 * @author asenka
 */
public class MtgPlayer extends Observable implements Comparable<MtgPlayer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1737279371197986879L;

	/**
	 * The collator used to compare the deck's names
	 */
	private static final transient Collator DEFAULT_COLLATOR = Collator.getInstance();

	/**
	 * The default initial life points of a player.
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
	 * Update the number of life points for the player
	 * @param remainingLifePoints
	 */
	public void setRemainingLifePoints(int remainingLifePoints) {

		if(this.remainingLifePoints != remainingLifePoints) {
			super.setChanged(); // if this method is not called, the observers will not be notified
		}	
		this.remainingLifePoints = remainingLifePoints;
		super.notifyObservers(new MtgPlayerLifeUpdatedEvent(this));
	}

	/**
	 * @return the number of poison counter of the player
	 */
	public int getPoisonCounters() {

		return poisonCounters;
	}

	/**
	 * Set the number of poison counters on the player and notify the observers
	 * if necessary
	 * @param poisonCounters  
	 */
	public void setPoisonCounters(final int poisonCounters) {

		if(this.poisonCounters != poisonCounters) {
			super.setChanged(); // if this method is not called, the observers will not be notified
		}	
		this.poisonCounters = poisonCounters;
		super.notifyObservers(new MtgPlayerPoisonUpdatedEvent(this));
	}

	/**
	 * @return the number of energy counters of the player
	 */
	public int getEnergyCounters() {

		return energyCounters;
	}
	
	

	/**
	 * 
	 * @param energyCounters
	 */
	public void setEnergyCounters(final int energyCounters) {
	
		if(this.energyCounters != energyCounters) {
			super.setChanged(); // if this method is not called, the observers will not be notified
		}	
		this.energyCounters = energyCounters;
		super.notifyObservers(new MtgPlayerEnergyUpdatedEvent(this));
	}

	/**
	 * Returns an unmodifiable list of cards
	 * @return the current list of cards in the player's hand
	 */
	public List<MtgCard> getHand() {

		return Collections.unmodifiableList(this.hand);
	}

	/**
	 * Returns an unmodifiable list of cards
	 * @return the current list of cards of the player that are exiled
	 */
	public List<MtgCard> getExile() {

		return Collections.unmodifiableList(this.exile);
	}

	/**
	 * Returns an unmodifiable list of cards
	 * @return the current list of cards of the player that are in the graveyard
	 */
	public List<MtgCard> getGraveyard() {

		return Collections.unmodifiableList(this.graveyard);
	}

	/**
	 * @return The player's library
	 */
	public MtgLibrary getLibrary() {

		return this.library;
	}

	/**
	 * Set the player library
	 * @param library the library to set
	 */
	public void setLibrary(MtgLibrary library) {

		this.library = library;
	}

	/**
	 * @return the set of available decks for this players
	 */
	public Set<MtgDeck> getAvailableDecks() {

		return availableDecks;
	}

	/**
	 * @return the current deck of this players. 
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
	public void addCardToGraveyard(MtgCard card) throws IllegalArgumentException {

		card.setContext(MtgContext.GRAVEYARD);
		this.graveyard.add(card);
	}

	/**
	 * Add the card to the exile list and update the card state
	 * 
	 * @param card the card to add
	 * @throws IllegalArgumentException if the card does not have a state yet
	 */
	public void addCardToExile(MtgCard card) throws IllegalArgumentException {

		card.setContext(MtgContext.EXILE);
		this.exile.add(card);
	}

	/**
	 * Add the card to the player hand and update the card state
	 * 
	 * @param card the card to add
	 * @throws IllegalArgumentException if the card does not have a state yet
	 */
	public void addCardToHand(MtgCard card) throws IllegalArgumentException {

		card.setContext(MtgContext.HAND);
		this.hand.add(card);
	}

	/**
	 * Add another deck to the player list of available decks
	 * @param deck the deck to add
	 * @throws MtgDeckException if the deck is not playable
	 */
	public void addAvailableDeck(MtgDeck deck) throws MtgDeckException {

		if (!deck.isPlayable()) {
			new MtgDeckException("Try to add an unplayable deck to this player");
		}
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
	public String toString() {

		return "[" + name + ", " + remainingLifePoints + ", " + energyCounters + ", " + poisonCounters + ", " + currentDeck + "]";
	}

	@Override
	public int compareTo(MtgPlayer player) {

		return DEFAULT_COLLATOR.compare(this.name, player.name);
	}

//	/**
//	 * Check if the card has a state and update the context in the card state
//	 * 
//	 * @param card the card to update
//	 * @param context the context to set on the card
//	 * @throws IllegalArgumentException if the card state is null
//	 */
//	private static void updateCardState(MtgCard card, MtgContext context) throws IllegalArgumentException {
//
//		card.setContext(context);
//	}
}
