package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.events.DeckEvent;

/**
 * The class represent a deck. The deck is devised in two zones : 
 * <ul>
 * <li>The main zone : the cards to play</li>
 * <li>The sideboard zone : the cards you keep in reserve</li>
 * </ul>
 * <p>Each zone is represented by a map where the key is an MtgCard and the value is an Integer for the
 * number of time this cards is in the zone</p>
 * 
 * @author asenka
 * @see Map
 * @see Library
 */
public class Deck extends Observable implements Comparable<Deck>, Serializable {

	/**
	 * The generated ID from serializable
	 */
	private static final long serialVersionUID = 8902051166224902061L;

	/**
	 * The minimum amount of cards in the main zone of the deck
	 */
	public static final int MINIMUM_MAIN_SIZE = 60;

	/**
	 * The maximum amount of cards in the sideboard zone of the deck
	 */
	public static final int MAXIMUM_SIDEBOARD_SIZE = 15;

	/**
	 * The name of the deck. It can not be empty.
	 */
	private String name;

	/**
	 * The description of the deck. It can be empty.
	 */
	private String description;

	/**
	 * The main zone of the deck. It contains the cards that are meant to be played. The cards in the main zone goes into the
	 * player library when the game starts. The map associated a card (MtgCard) to an Integer for the number of instance of this
	 * card in the deck
	 */
	private Map<MtgCard, Integer> main;

	/**
	 * The sideboard zone of the deck. It contains the cards in reserve. The map associated a card (MtgCard) to an Integer for the
	 * number of instance of this card in the deck
	 */
	private Map<MtgCard, Integer> sideboard;

	/**
	 * Build a new deck
	 * 
	 * @param name the name of the deck
	 * @param description the description of the deck
	 */
	public Deck(String name, String decription) {

		this.main = new HashMap<MtgCard, Integer>(MINIMUM_MAIN_SIZE);
		this.sideboard = new HashMap<MtgCard, Integer>(0);
		this.description = decription != null ? decription : "";
		this.name = name != null ? name : "[No Name]";
	}

	/**
	 * @return the name of the deck
	 */
	public String getName() {

		return name;
	}

	/**
	 * Set the name of the deck
	 * 
	 * @param name the name to set
	 * @see DeckEvent
	 */
	public void setName(String name) {

		if (!this.name.equals(name)) {
			this.name = name;
			super.setChanged();
			super.notifyObservers(new DeckEvent("set", "name", name));
		}
	}

	/**
	 * @return the description of the deck
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * Set the description of the deck
	 * 
	 * @param description the description to set
	 * @see DeckEvent
	 */
	public void setDescription(String description) {

		if (!this.description.equals(description)) {
			this.description = description;
			super.setChanged();
			super.notifyObservers(new DeckEvent("set", "description", description));
		}
	}

	/**
	 * @return the map with the main cards
	 */
	public Map<MtgCard, Integer> getMain() {

		return main;
	}

	/**
	 * @return the map with the cards from the sideboard
	 */
	public Map<MtgCard, Integer> getSideboard() {

		return sideboard;
	}

	/**
	 * Build a new library from this deck
	 * 
	 * @return a new Library built with the cards from the main zone
	 * @throws Exception if the cards number in the main zone are inferior to the minimum main size
	 * @see Library
	 */
	public Library getLibrary() throws Exception {

		if (numberOfCards(this.main) >= MINIMUM_MAIN_SIZE) {

			List<Card> libraryCards = new ArrayList<Card>(this.main.size());
			Iterator<Entry<MtgCard, Integer>> it = this.main.entrySet().iterator();

			while (it.hasNext()) {

				Entry<MtgCard, Integer> entry = it.next();
				MtgCard mtgCard = entry.getKey();
				int number = entry.getValue().intValue();

				do {
					libraryCards.add(new Card(mtgCard));
				} while (--number > 0);
			}
			return new Library(libraryCards);

		} else {
			throw new Exception("The library can not be initialized, it does not contains enough cards: " + numberOfCards(this.main) + " ("
					+ MINIMUM_MAIN_SIZE + " are expected)");
		}
	}

	/**
	 * Add a card to the main zone of the deck
	 * 
	 * @param mtgCard the card data
	 * @param number the number of instance of the card to add in the deck
	 * @see DeckEvent
	 */
	public void addCardToMain(final MtgCard mtgCard, int number) {

		if (this.main.containsKey(mtgCard)) {
			Integer currentNumber = this.main.get(mtgCard);
			this.main.put(mtgCard, new Integer(currentNumber + number));
		} else {
			this.main.put(mtgCard, new Integer(number));
		}
		super.setChanged();
		super.notifyObservers(new DeckEvent("add(" + number + ")", "main", mtgCard));
	}

	/**
	 * Add a card to the sideboard zone of the deck
	 * 
	 * @param mtgCard the card data
	 * @param number the number of instance of the card to add in the deck
	 * @throws Exception if the size of the sideboard will exceed the maximum value after performing this method
	 * @see DeckEvent
	 */
	public void addCardToSideboard(final MtgCard mtgCard, int number) throws Exception {

		if ((numberOfCards(this.sideboard) + number) <= MAXIMUM_SIDEBOARD_SIZE) {

			if (this.sideboard.containsKey(mtgCard)) {
				Integer currentNumber = this.sideboard.get(mtgCard);
				this.sideboard.replace(mtgCard, new Integer(currentNumber + number));
			} else {
				this.sideboard.put(mtgCard, new Integer(number));
			}
			super.setChanged();
			super.notifyObservers(new DeckEvent("add(" + number + ")", "sideboard", mtgCard));
		} else {
			throw new Exception("The number of cards in the sideboard cannot exceed " + MAXIMUM_SIDEBOARD_SIZE + ". The current size is "
					+ numberOfCards(sideboard));
		}
	}

	/**
	 * Removes 1 instance of the card in the main zone of the deck
	 * 
	 * @param mtgCard the card to remove from the deck
	 * @see DeckEvent
	 */
	public void removeCardFromMain(final MtgCard mtgCard) {

		if (this.main.containsKey(mtgCard)) {

			Integer currentNumber = this.main.get(mtgCard);

			if (currentNumber > 1) {
				this.main.replace(mtgCard, new Integer(currentNumber - 1));
			} else {
				this.main.remove(mtgCard);
			}
			super.setChanged();
			super.notifyObservers(new DeckEvent("remove(1)", "main", mtgCard));
		}
	}

	/**
	 * Removes 1 instance of the card in the main zone of the deck
	 * 
	 * @param mtgCard the card to remove from the deck
	 * @see DeckEvent
	 */
	public void removeCardFromSideboard(final MtgCard mtgCard) {

		if (this.sideboard.containsKey(mtgCard)) {

			Integer currentNumber = this.sideboard.get(mtgCard);

			if (currentNumber > 1) {
				this.sideboard.replace(mtgCard, new Integer(currentNumber - 1));
			} else {
				this.sideboard.remove(mtgCard);
			}
			super.setChanged();
			super.notifyObservers(new DeckEvent("remove(1)", "sideboard", mtgCard));
		}
	}

	@Override
	public String toString() {

		return "Deck [" + name + ", " + description + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((main == null) ? 0 : main.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sideboard == null) ? 0 : sideboard.hashCode());
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
		Deck other = (Deck) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (main == null) {
			if (other.main != null)
				return false;
		} else if (!main.equals(other.main))
			return false;
		if (sideboard == null) {
			if (other.sideboard != null)
				return false;
		} else if (!sideboard.equals(other.sideboard))
			return false;
		return true;
	}

	@Override
	public int compareTo(Deck otherDeck) {

		// Uses the default collator to sort the deck by the name
		return Collator.getInstance(Locale.getDefault()).compare(this.name, otherDeck.name);
	}

	/**
	 * Calculate the number of cards in a map<MtgCard, Integer>
	 * 
	 * @param map a map with integer values
	 * @return the sum of all the integer values in the map
	 */
	public static int numberOfCards(Map<MtgCard, Integer> map) {

		Collection<Integer> values = map.values();
		int result = 0;

		for (Integer number : values) {
			result += number.intValue();
		}
		return result;
	}
}
