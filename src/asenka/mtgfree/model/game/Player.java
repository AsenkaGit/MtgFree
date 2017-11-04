package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.events.EventType;
import asenka.mtgfree.events.LocalEvent;

/**
 * A player
 * 
 * @author asenka
 */
public class Player extends Observable implements Serializable, Comparable<Player> {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -6984024632036631899L;

	/**
	 * The initial value of the player life points
	 */
	private static final int INITIAL_LIFE_COUNTERS = 20;

	/**
	 * The initial value for the player poison counters
	 */
	private static final int INITIAL_POISON_COUNTERS = 0;

	/**
	 * The player's name
	 */
	private String name;

	/**
	 * The player's life counters
	 */
	private int lifeCounters;

	/**
	 * The player's poison counters
	 */
	private int poisonCounters;

	/**
	 * The player's selected deck
	 */
	private Deck selectedDeck;

	/**
	 * The player's available decks
	 */
	private Set<Deck> availableDecks;

	/**
	 * The player's hand
	 */
	private List<Card> hand;

	/**
	 * The player's grave yard
	 */
	private List<Card> graveyard;

	/**
	 * The player's exile area
	 */
	private List<Card> exile;

	/**
	 * The player's library
	 */
	private Library library;

	/**
	 * The battlefield reference 
	 */
	private final Battlefield battlefield;

	/**
	 * Build a player with a new Battlefield
	 * 
	 * @param name
	 */
	public Player(String name) {

		this(name, new Battlefield());
	}
	
	/**
	 * Build a player with a specified battlefield
	 * 
	 * @param name
	 * @param battlefield
	 */
	public Player(String name, Battlefield battlefield) {

		super();
		this.name = name;
		this.battlefield = battlefield;
		this.lifeCounters = INITIAL_LIFE_COUNTERS;
		this.poisonCounters = INITIAL_POISON_COUNTERS;
		this.availableDecks = new HashSet<Deck>();
		this.hand = new ArrayList<Card>();
		this.graveyard = new ArrayList<Card>();
		this.exile = new ArrayList<Card>();
		this.selectedDeck = null;
		this.library = null;
	}

	/**
	 * @return the player's name
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * Set the name of the player
	 * 
	 * @param name the player's name
	 */
	public void setName(String name) {

		if (!this.name.equals(name)) {
			this.name = name;
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.SET_PLAYER_NAME, name));
		}
	}

	/**
	 * @return the number of life points of the player
	 */
	public int getLifeCounters() {

		return this.lifeCounters;
	}

	/**
	 * Set the number of life points of the player
	 * 
	 * @param lifeCounters the number of life points
	 * @see LocalEvent
	 */
	public void setLifeCounters(int lifeCounters) {
		
		if (this.lifeCounters != lifeCounters) {

			this.lifeCounters = lifeCounters;
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.SET_PLAYER_LIFE, new Integer(lifeCounters)));
		}
	}

	/**
	 * @return the number of poison counters on the player
	 */
	public int getPoisonCounters() {

		return poisonCounters;
	}

	/**
	 * Set the poison counters on the player
	 * 
	 * @param poisonCounters the number of poison counters
	 * @return 
	 * @see LocalEvent
	 */
	public void setPoisonCounters(int poisonCounters) {

		if (this.poisonCounters != poisonCounters) {

			this.poisonCounters = poisonCounters;
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.SET_PLAYER_POISON, new Integer(poisonCounters)));
		}
	}

	/**
	 * @return the library of the player during a game
	 */
	public Library getLibrary() {

		return library;
	}

	/**
	 * Set the current library of the player
	 * 
	 * @param library
	 */
	public void setLibrary(Library library) {

		this.library = library;
		super.setChanged();
		super.notifyObservers(new LocalEvent(EventType.SET_PLAYER_LIBRARY, library));
	}

	/**
	 * @return the deck used by the player to play
	 */
	public Deck getSelectedDeck() {

		return selectedDeck;
	}

	/**
	 * Set the selected deck used by the player to play
	 * 
	 * @param selectedDeck the deck to use
	 * @see LocalEvent
	 */
	public void setSelectedDeck(Deck selectedDeck) {

		this.selectedDeck = selectedDeck;
		super.setChanged();
		super.notifyObservers(new LocalEvent(EventType.SET_PLAYER_SELECTED_DECK, selectedDeck));
	}

	/**
	 * @return the battlefield and all the cards on it
	 */
	public Battlefield getBattlefield() {
	
		return this.battlefield;
	}

	/**
	 * @return the set of available decks of the player
	 */
	public Set<Deck> getAvailableDecks() {

		return Collections.unmodifiableSet(this.availableDecks);
	}

	/**
	 * @return the set of all the cards in the player's hand
	 */
	public List<Card> getHand() {

		return Collections.unmodifiableList(this.hand);
	}

	/**
	 * @return the set of all the cards in the player's grave yard
	 */
	public List<Card> getGraveyard() {

		return Collections.unmodifiableList(this.graveyard);
	}

	/**
	 * @return the set of all the cards in the player's exile area
	 */
	public List<Card> getExile() {

		return Collections.unmodifiableList(this.exile);
	}

	/**
	 * Add a deck to the available decks of this player
	 * 
	 * @param deck the deck to add
	 * @see LocalEvent
	 */
	public void addAvailableDeck(Deck deck) {

		if (deck != null) {

			this.availableDecks.add(deck);
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.ADD_AVAILABLE_DECK, deck));
		}
	}

	/**
	 * Adds a card in the hand area of the player
	 * 
	 * @param card the card to exile
	 * @see LocalEvent
	 */
	public void addCardToHand(Card card) {

		if (card != null) {

			this.hand.add(card);
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.ADD_TO_HAND, card));
		}
	}

	/**
	 * Adds a card in the graveyard area of the player
	 * 
	 * @param card the card to exile
	 * @see LocalEvent
	 */
	public void addCardToGraveyard(Card card) {

		if (card != null) {

			this.graveyard.add(card);
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.ADD_TO_GRAVEYARD, card));
		}
	}

	/**
	 * Adds a card in the exile area of the player
	 * 
	 * @param card the card to exile
	 * @see LocalEvent
	 */
	public void addCardToExile(Card card) {

		if (card != null) {

			this.exile.add(card);
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.ADD_TO_EXILE, card));
		}
	}

	/**
	 * Removes a card from the hand area of the player
	 * 
	 * @param deck the deck to remove
	 * @see LocalEvent
	 */
	public boolean removeAvailableDeck(Deck deck) {

		if (this.availableDecks.remove(deck)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.REMOVE_AVAILABLE_DECK, deck));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the hand area of the player
	 * 
	 * @param card the card to remove
	 * @see LocalEvent
	 */
	public boolean removeCardFromHand(Card card) {

		if (this.hand.remove(card)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.REMOVE_FROM_HAND, card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the graveyard area of the player
	 * 
	 * @param card the card to remove
	 * @see LocalEvent
	 */
	public boolean removeCardFromGraveyard(Card card) {

		if (this.graveyard.remove(card)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.REMOVE_FROM_GRAVEYARD, card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the exile area of the player
	 * 
	 * @param card the card to remove
	 * @see LocalEvent
	 */
	public boolean removeCardFromExile(Card card) {

		if (this.exile.remove(card)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.REMOVE_FROM_GRAVEYARD, card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Clear the cards in the hand area of the player
	 * 
	 * @see LocalEvent
	 */
	public void clearHand() {

		if (!this.hand.isEmpty()) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.CLEAR_HAND));
		}
	}

	/**
	 * Clear the cards in the graveyard area of the player
	 * 
	 * @see LocalEvent
	 */
	public void clearGraveyard() {

		if (!this.graveyard.isEmpty()) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.CLEAR_GRAVEYARD));
		}
	}

	/**
	 * Clear the cards in the exile area of the player
	 * 
	 * @see LocalEvent
	 * @see EventType#CLEAR_EXILE
	 */
	public void clearExile() {

		if (!this.exile.isEmpty()) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.CLEAR_EXILE));
		}
	}
	
	

	@Override
	public String toString() {

		return "Player [" + name + ", " + lifeCounters + ", " + poisonCounters + "]";
	}
	
	

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((selectedDeck == null) ? 0 : selectedDeck.hashCode());
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
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (selectedDeck == null) {
			if (other.selectedDeck != null)
				return false;
		} else if (!selectedDeck.equals(other.selectedDeck))
			return false;
		return true;
	}

	@Override
	public int compareTo(Player otherPlayer) {

		// Uses the default collator to sort the players by their names
		return Collator.getInstance(Locale.getDefault()).compare(this.name, otherPlayer.name);
	}
}
