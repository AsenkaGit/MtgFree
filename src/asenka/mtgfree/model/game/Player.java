package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.events.local.PlayerEvent;

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

		return name;
	}

	/**
	 * Set the name of the player
	 * 
	 * @param name the player's name
	 */
	public void setName(String name) {

		if (this.name.equals(name)) {
			this.name = name;
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "set", "name", name));
		}
	}

	/**
	 * @return the number of life points of the player
	 */
	public int getLifeCounters() {

		return lifeCounters;
	}

	/**
	 * Set the number of life points of the player
	 * 
	 * @param lifeCounters the number of life points
	 * @see PlayerEvent
	 */
	public void setLifeCounters(int lifeCounters) {
		
		if (this.lifeCounters != lifeCounters) {

			this.lifeCounters = lifeCounters;
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "set", "lifeCounters", new Integer(lifeCounters)));
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
	 * @see PlayerEvent
	 */
	public void setPoisonCounters(int poisonCounters) {

		if (this.poisonCounters != poisonCounters) {

			this.poisonCounters = poisonCounters;
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "set", "poisonCounters", new Integer(poisonCounters)));
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
		super.notifyObservers(new PlayerEvent(this, "set", "library", library));
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
	 * @param deck the deck to use
	 * @see PlayerEvent
	 */
	public void setSelectedDeck(Deck deck) {

		this.selectedDeck = deck;
		super.setChanged();
		super.notifyObservers(new PlayerEvent(this, "set", "selectedDeck", deck));
	}

	/**
	 * 
	 * @return
	 */
	public Battlefield getBattlefield() {
	
		return this.battlefield;
	}

	/**
	 * @return the set of available decks of the player
	 */
	public Set<Deck> getAvailableDecks() {

		return availableDecks;
	}

	/**
	 * @return the set of all the cards in the player's hand
	 */
	public List<Card> getHand() {

		return hand;
	}

	/**
	 * @return the set of all the cards in the player's grave yard
	 */
	public List<Card> getGraveyard() {

		return graveyard;
	}

	/**
	 * @return the set of all the cards in the player's exile area
	 */
	public List<Card> getExile() {

		return exile;
	}

	/**
	 * Add a deck to the available decks of this player
	 * 
	 * @param deck the deck to add
	 * @see PlayerEvent
	 */
	public void addAvailableDeck(Deck deck) {

		if (deck != null) {

			this.availableDecks.add(deck);
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "add", "availableDecks", deck));
		}
	}

	/**
	 * Adds a card in the hand area of the player
	 * 
	 * @param card the card to exile
	 * @see PlayerEvent
	 */
	public void addCardToHand(Card card) {

		if (card != null) {

			this.hand.add(card);
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "add", "hand", card));
		}
	}

	/**
	 * Adds a card in the graveyard area of the player
	 * 
	 * @param card the card to exile
	 * @see PlayerEvent
	 */
	public void addCardToGraveyard(Card card) {

		if (card != null) {

			this.graveyard.add(card);
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "add", "graveyard", card));
		}
	}

	/**
	 * Adds a card in the exile area of the player
	 * 
	 * @param card the card to exile
	 * @see PlayerEvent
	 */
	public void addCardToExile(Card card) {

		if (card != null) {

			this.exile.add(card);
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "add", "exile", card));
		}
	}

	/**
	 * Removes a card from the hand area of the player
	 * 
	 * @param deck the deck to remove
	 * @see PlayerEvent
	 */
	public boolean removeAvailableDeck(Deck deck) {

		if (this.availableDecks.remove(deck)) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "remove", "availableDecks", deck));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the hand area of the player
	 * 
	 * @param card the card to remove
	 * @see PlayerEvent
	 */
	public boolean removeCardFromHand(Card card) {

		if (this.hand.remove(card)) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "remove", "hand", card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the graveyard area of the player
	 * 
	 * @param card the card to remove
	 * @see PlayerEvent
	 */
	public boolean removeCardFromGraveyard(Card card) {

		if (this.graveyard.remove(card)) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "remove", "graveyard", card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a card from the exile area of the player
	 * 
	 * @param card the card to remove
	 * @see PlayerEvent
	 */
	public boolean removeCardFromExile(Card card) {

		if (this.exile.remove(card)) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "remove", "exile", card));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Clear the cards in the hand area of the player
	 * 
	 * @see PlayerEvent
	 */
	public void clearHand() {

		if (!this.hand.isEmpty()) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "clear", "hand", null));
		}
	}

	/**
	 * Clear the cards in the graveyard area of the player
	 * 
	 * @see PlayerEvent
	 */
	public void clearGraveyard() {

		if (!this.graveyard.isEmpty()) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "clear", "graveyard", null));
		}
	}

	/**
	 * Clear the cards in the exile area of the player
	 * 
	 * @see PlayerEvent
	 */
	public void clearExile() {

		if (!this.exile.isEmpty()) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent(this, "clear", "exile", null));
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
