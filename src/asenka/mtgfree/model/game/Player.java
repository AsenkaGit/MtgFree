package asenka.mtgfree.model.game;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import asenka.mtgfree.events.LocalEvent;
import static asenka.mtgfree.events.LocalEvent.Type.*;

/**
 * A player
 * 
 * @author asenka
 */
public class Player extends AbstractGameObject implements Comparable<Player> {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -8547754100855888457L;

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
	 * @see LocalEvent
	 */
	public void setName(String name) {

		if (!this.name.equals(name)) {
			this.name = name;
			super.setChanged();
			super.notifyObservers(new LocalEvent(PLAYER_SET_SELECTED_DECK, name));
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
			super.notifyObservers(new LocalEvent(PLAYER_UPDATE_LIFE, new Integer(lifeCounters)));
		}
	}

	/**
	 * @return the number of poison counters on the player
	 */
	public int getPoisonCounters() {

		return this.poisonCounters;
	}

	/**
	 * Set the poison counters on the player
	 * 
	 * @param poisonCounters the number of poison counters
	 * @see LocalEvent
	 */
	public void setPoisonCounters(int poisonCounters) {

		if (this.poisonCounters != poisonCounters) {

			this.poisonCounters = poisonCounters;
			super.setChanged();
			super.notifyObservers(new LocalEvent(PLAYER_UPDATE_POISON, new Integer(poisonCounters)));
		}
	}

	/**
	 * @return the library of the player during a game
	 */
	public Library getLibrary() {

		return this.library;
	}

	/**
	 * Set the current library of the player. if the player has already a library, the library reference remains the same to keep
	 * the observers but the cards are updated
	 * 
	 * @param library
	 */
	public void setLibrary(Library library) {

		if (this.library == null) {
			this.library = library;
		} else {
			this.library.setCards(library.getCards());
		}
	}

	/**
	 * @return the deck used by the player to play
	 */
	public Deck getSelectedDeck() {

		return this.selectedDeck;
	}

	/**
	 * Set the selected deck used by the player to play. The player's library is automatically updated
	 * 
	 * @param selectedDeck the deck to use
	 * @throws RuntimeException if the library cannot be built from the new selected deck
	 * @see LocalEvent
	 */
	public void setSelectedDeck(Deck selectedDeck) throws RuntimeException {

		this.selectedDeck = selectedDeck;

		if (this.selectedDeck != null && !this.selectedDeck.equals(selectedDeck)) {
			this.setLibrary(selectedDeck.buildLibrary());
			super.setChanged();
			super.notifyObservers(new LocalEvent(PLAYER_SET_SELECTED_DECK, selectedDeck));
		}
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
			super.notifyObservers(new LocalEvent(PLAYER_ADD_DECK, deck));
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
			super.notifyObservers(new LocalEvent(ADD_CARD_TO_HAND, card));
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
			super.notifyObservers(new LocalEvent(ADD_CARD_TO_GRAVEYARD, card));
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
			super.notifyObservers(new LocalEvent(ADD_CARD_TO_EXILE, card));
		}
	}

	/**
	 * Removes a card from the hand area of the player
	 * 
	 * @param deck the deck to remove
	 * @return <code>true</code> if the deck was in the player list of available decks
	 * @see LocalEvent
	 */
	public boolean removeAvailableDeck(Deck deck) {

		if (this.availableDecks.remove(deck)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(PLAYER_REMOVE_DECK, deck));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the hand area of the player
	 * 
	 * @param card the card to remove
	 * @return <code>true</code> if the card was in the player's hand
	 * @see LocalEvent
	 */
	public boolean removeCardFromHand(Card card) {

		if (this.hand.remove(card)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(REMOVE_CARD_FROM_HAND, card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the graveyard area of the player
	 * 
	 * @param card the card to remove
	 * @return <code>true</code> if the card was in the graveyard
	 * @see LocalEvent
	 */
	public boolean removeCardFromGraveyard(Card card) {

		if (this.graveyard.remove(card)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(REMOVE_CARD_FROM_GRAVEYARD, card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the exile area of the player
	 * 
	 * @param card the card to remove
	 * @return <code>true</code> if the card was in the exile area
	 * @see LocalEvent
	 */
	public boolean removeCardFromExile(Card card) {

		if (this.exile.remove(card)) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(REMOVE_CARD_FROM_EXILE, card));
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
			super.notifyObservers(new LocalEvent(CLEAR_HAND));
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
			super.notifyObservers(new LocalEvent(CLEAR_GRAVEYARD));
		}
	}

	/**
	 * Clear the cards in the exile area of the player
	 * 
	 * @see LocalEvent
	 */
	public void clearExile() {

		if (!this.exile.isEmpty()) {
			super.setChanged();
			super.notifyObservers(new LocalEvent(CLEAR_EXILE));
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
