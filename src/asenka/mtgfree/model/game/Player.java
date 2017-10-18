package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.text.Collator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.model.events.PlayerEvent;

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
	private Set<Card> hand;

	/**
	 * The player's grave yard
	 */
	private Set<Card> graveyard;

	/**
	 * The player's exile area
	 */
	private Set<Card> exile;

	/**
	 * The player's library
	 */
	private Library library;

	/**
	 * The battlefield reference (it must be the same for all players)
	 */
	private final Battlefield battlefield;

	/**
	 * Build a player
	 * 
	 * @param name
	 */
	public Player(String name, Battlefield battlefield) {

		super();
		this.name = name;
		this.battlefield = battlefield;
		this.lifeCounters = INITIAL_LIFE_COUNTERS;
		this.poisonCounters = INITIAL_POISON_COUNTERS;
		this.availableDecks = new HashSet<Deck>();
		this.hand = new HashSet<Card>();
		this.graveyard = new HashSet<Card>();
		this.exile = new HashSet<Card>();
		this.selectedDeck = null;
		this.library = null;
	}

	/**
	 * The player draw a card
	 */
	public Card draw() {

		if (this.library != null) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent("draw", "library", new Integer(1)));

			Card card = this.library.draw();
			this.addCardToHand(card);
			return card;
		} else {
			throw new RuntimeException("The player's library is not ready");
		}
	}

	/**
	 * The player draw x cards
	 */
	public List<Card> draw(int x) {

		if (this.library != null) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent("draw", "library", new Integer(x)));

			List<Card> cards = this.library.draw(x);
			cards.forEach(card -> this.addCardToHand(card));
			return cards;
		} else {
			throw new RuntimeException("The player's library is not ready");
		}
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
			super.notifyObservers(new PlayerEvent("set", "name", name));
		}
	}

	/**
	 * 
	 * @return
	 */
	public Battlefield getBattlefield() {

		return this.battlefield;
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
			super.notifyObservers(new PlayerEvent("set", "lifeCounters", lifeCounters));
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
	 * @see PlayerEvent
	 */
	public void setPoisonCounters(int poisonCounters) {

		if (this.poisonCounters != poisonCounters) {

			this.poisonCounters = poisonCounters;
			super.setChanged();
			super.notifyObservers(new PlayerEvent("set", "poisonCounters", poisonCounters));
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
		super.notifyObservers(new PlayerEvent("set", "library", library));
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
		super.notifyObservers(new PlayerEvent("set", "selectedDeck", deck));
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
	public Set<Card> getHand() {

		return hand;
	}

	/**
	 * @return the set of all the cards in the player's grave yard
	 */
	public Set<Card> getGraveyard() {

		return graveyard;
	}

	/**
	 * @return the set of all the cards in the player's exile area
	 */
	public Set<Card> getExile() {

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
			super.notifyObservers(new PlayerEvent("add", "availableDecks", deck));
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
			super.notifyObservers(new PlayerEvent("add", "hand", card));
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
			super.notifyObservers(new PlayerEvent("add", "graveyard", card));
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
			super.notifyObservers(new PlayerEvent("add", "exile", card));
		}
	}

	/**
	 * 
	 * @param origin
	 * @param card
	 * @param x
	 * @param y
	 * @param visible
	 */
	public void addCardToBattlefield(String origin, Card card, double x, double y, boolean visible) {
	
		super.setChanged();
		super.notifyObservers(new PlayerEvent("add", "battlefield", card));
		
		checkAndRemoveFromOrigin(origin, card);
		this.battlefield.addCard(card, x, y, visible);
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
			super.notifyObservers(new PlayerEvent("remove", "availableDecks", deck));
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
			super.notifyObservers(new PlayerEvent("remove", "hand", card));
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
			super.notifyObservers(new PlayerEvent("remove", "graveyard", card));
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
			super.notifyObservers(new PlayerEvent("remove", "exile", card));
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
	public boolean removeCardFromBattlefield(Card card) {

		if (this.battlefield.remove(card)) {
			super.setChanged();
			super.notifyObservers(new PlayerEvent("remove", "battlefield", card));
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
			super.notifyObservers(new PlayerEvent("clear", "hand", null));
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
			super.notifyObservers(new PlayerEvent("clear", "graveyard", null));
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
			super.notifyObservers(new PlayerEvent("clear", "exile", null));
		}
	}

	@Override
	public int compareTo(Player otherPlayer) {

		// Uses the default collator to sort the players by their names
		return Collator.getInstance(Locale.getDefault()).compare(this.name, otherPlayer.name);
	}

	/**
	 * Removes the card from the declared origin. If the card is not found in the origin collection,
	 * an exception is raised
	 * @param origin the origin of the card to play. Legal values are : <code>[hand, graveyard, exile, library]</code>
	 * @param card the card to check and remove
	 * @throws IllegalArgumentException if  <code>code</code> is not a legal value
	 * @throws RuntimeException if the card is not in the origin collection
	 */
	private void checkAndRemoveFromOrigin(String origin, Card card) throws IllegalArgumentException, RuntimeException {
	
		String errorMessage = null;
	
		switch (origin) {
			case "hand":
				if (!this.removeCardFromHand(card)) {
					errorMessage = "The card n°" + card.getBattleId() + " is not in the player's hand";
				} 
				break;
			case "graveyard":
				if (!this.removeCardFromGraveyard(card)) {
					errorMessage = "The card n°" + card.getBattleId() + " is not in the players graveyard area";
				}
				break;
			case "exile":
				if (!this.removeCardFromExile(card)) {
					errorMessage = "The card n°" + card.getBattleId() + " is not in the players exile area";
				}
				break;
			case "library":
				if (!this.library.remove(card)) {
					errorMessage = "The card n°" + card.getBattleId() + " is not in the player's library";
				}
				break;
			default:
				throw new IllegalArgumentException("Origin value (" + origin + ") is unknown. Only legal values are "
						+ "[hand, graveyard, exile, library]");
		}
		if (errorMessage != null) {
			throw new RuntimeException(errorMessage);
		}
	
	}

}
