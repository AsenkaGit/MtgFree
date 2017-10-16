package asenka.mtgfree.model.game;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.model.events.CardEvent;
import asenka.mtgfree.model.pojo.MtgCard;
import asenka.mtgfree.model.utilities.json.MtgDataUtility;

/**
 * 
 * 
 * @author asenka
 *
 */
public class Card extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6450719588687307909L;

	/**
	 * The counter used to generated card battle id
	 */
	private static long battleIdCounter = 0;

	/**
	 * The ID of the card on the battlefield. It is automativally generated when the card is created and does not change
	 */
	private final long battleId;

	/**
	 * The primary card data.
	 */
	private final MtgCard primaryCardData;

	/**
	 * The secondary card data. It will remain null 99% of the time, but there are some cards with 2 cards on it (double-faced,
	 * split, flip)
	 */
	private final MtgCard secondaryCardData;

	/**
	 * Is the card tapped or not. Only available while a card is on the battlefield. This value is useless in other contexts
	 */
	private boolean tapped;

	/**
	 * Is the card visible. Only available while a card is on the battlefield. This value is useless in other contexts
	 */
	private boolean visible;

	/**
	 * Is the card revealed to other player while it is on the owner's hand. Only available while a card is on a player's hand.
	 * This value is useless in other contexts
	 */
	private boolean revealed;

	/**
	 * The location of the card on the battlefield. Only available while a card is on the battlefield. This value is useless in
	 * other contexts
	 */
	private Point2D.Double location;

	/**
	 * The list of card associated with this one. It could be interesting when you want to regroup cards together. The order in
	 * the list represents how the cards are overlapping.
	 */
	private List<Card> associatedCards;

	/**
	 * The counters on the card
	 */
	private Set<Counter> counters;

	/**
	 * 
	 * @param cardData
	 */
	public Card(MtgCard cardData) {

		super();
		this.battleId = ++Card.battleIdCounter;
		this.primaryCardData = cardData;
		this.visible = true;
		this.revealed = false;
		this.tapped = false;
		this.location = new Point2D.Double(-1.0, -1.0);
		this.associatedCards = new LinkedList<Card>();
		this.counters = new HashSet<Counter>(2);

		// Initialize the secondary card data if necessary
		String[] names = this.primaryCardData.getNames();

		if (names != null && names.length > 1) {
			this.secondaryCardData = MtgDataUtility.getInstance().getMtgCard(names[1]);
		} else {
			this.secondaryCardData = null;
		}
	}

	/**
	 * @return the battleId
	 */
	public long getBattleId() {

		return battleId;
	}

	/**
	 * This is the method you will have to use most of the time
	 * 
	 * @return the main data about the card
	 */
	public MtgCard getPrimaryCardData() {

		return primaryCardData;
	}

	/**
	 * This method is only useful when the card layout is not 'normal'
	 * 
	 * @return the secondary card data
	 */
	public MtgCard getSecondaryCardData() {

		return secondaryCardData;
	}

	/**
	 * @return <code>true</code> if the card is tapped
	 */
	public boolean isTapped() {

		return tapped;
	}

	/**
	 * Set if the card is tapped or not. The method creates a CardEvent to notify the observers.
	 * 
	 * @param tapped
	 */
	public void setTapped(boolean tapped) {

		if (this.tapped != tapped) {
			this.tapped = tapped;
			super.setChanged();
			super.notifyObservers(new CardEvent("set", "tapped", new Boolean(tapped)));
		}
	}

	/**
	 * Check if the card is visible. If not it, means that you should only be able to see the back of the card.
	 * 
	 * @return
	 */
	public boolean isVisible() {

		return visible;
	}

	/**
	 * Set to <code>true</code> if the card is visible on the battlefield. By default the value is <code>true</code>. The method
	 * creates a CardEvent to notify the observers.
	 * 
	 * @param visible true/false
	 */
	public void setVisible(boolean visible) {

		if (this.visible != visible) {
			this.visible = visible;
			super.setChanged();
			super.notifyObservers(new CardEvent("set", "visible", new Boolean(visible)));
		}
	}

	/**
	 * Use this method only in the context of the player hand. Otherwise this value does not make sense
	 * 
	 * @return <code>true</code> if the card is revealed to opponent while on the player hand
	 */
	public boolean isRevealed() {

		return revealed;
	}

	/**
	 * Set to <code>true</code> if the card should be revealed to other player(s) when it is in the player's hand. By default the
	 * value is <code>false</code>. The method creates a CardEvent to notify the observers.
	 * 
	 * @param revealed true/false
	 */
	public void setRevealed(boolean revealed) {

		if (this.revealed != revealed) {
			this.revealed = revealed;
			super.setChanged();
			super.notifyObservers(new CardEvent("set", "revealed", new Boolean(revealed)));
		}
	}

	/**
	 * @return the location of the card : <code>(x, y)</code> where x and y are double values.
	 */
	public Point2D.Double getLocation() {

		return location;
	}

	/**
	 * Set the location of the card. The method creates a CardEvent to notify the observers.
	 * 
	 * @param x the double coordinate for horizontal position
	 * @param y the double coordinate for vertical position
	 * @see CardEvent
	 */
	public void setLocation(final double x, final double y) {

		// If one of the value has changed
		if (x != this.location.getX() || y != this.location.getY()) {
			this.location.setLocation(x, y);
			super.setChanged();
			super.notifyObservers(new CardEvent("set", "location", this.location));
		}

	}

	/**
	 * @return the list of the cards associated with this card
	 */
	public List<Card> getAssociatedCards() {

		return associatedCards;
	}

	/**
	 * Associates a card with another (like when a card enchants another, or when an artifact equips a creature). The method
	 * creates a CardEvent to notify the observers.
	 * 
	 * @param card the associated card to add
	 * @see CardEvent
	 */
	public void addAssociatedCard(Card card) {

		if (card != null) {
			this.associatedCards.add(card);
			super.setChanged();
			super.notifyObservers(new CardEvent("add", "associatedCards", card));
		}
	}

	/**
	 * Removes an associated card. The method creates a CardEvent to notify the observers.
	 * 
	 * @param card the associated card to remove
	 * @see CardEvent
	 */
	public void removeAssociatedCard(Card card) {

		if (this.associatedCards.remove(card)) {
			super.setChanged();
			super.notifyObservers(new CardEvent("remove", "associatedCards", card));
		}
	}

	/**
	 * Removes all the cards associated to this card. The method creates a CardEvent to notify the observers.
	 * 
	 * @see CardEvent
	 */
	public void clearAssociatedCards() {

		if (!this.associatedCards.isEmpty()) {
			this.associatedCards.clear();
			super.setChanged();
			super.notifyObservers(new CardEvent("clear", "associatedCards", null));
		}
	}

	/**
	 * @return the counters on the cards. It can not be null, but it could be empty
	 */
	public Set<Counter> getCounters() {

		return counters;
	}

	/**
	 * Adds a counter on the card. The method creates a CardEvent to notify the observers.
	 * 
	 * @param counter the counter to add
	 * @see CardEvent
	 */
	public void addCounter(Counter counter) {

		if (counter != null) {
			this.counters.add(counter);
			super.setChanged();
			super.notifyObservers(new CardEvent("add", "counter", counter));
		}
	}

	/**
	 * Removes a counter on the card. The method creates a CardEvent to notify the observers.
	 * 
	 * @param counter the counter to remove
	 * @see CardEvent
	 */
	public void removeCounter(Counter counter) {

		if (this.counters.remove(counter)) {
			super.setChanged();
			super.notifyObservers(new CardEvent("remove", "counter", counter));
		}
	}

	/**
	 * Removes all the counters on the card. The method creates a CardEvent to notify the observers.
	 * 
	 * @see CardEvent
	 */
	public void clearCounters() {

		if (!this.counters.isEmpty()) {
			this.counters.clear();
			super.setChanged();
			super.notifyObservers(new CardEvent("clear", "counter", null));
		}
	}

	/**
	 * The card layout. Possible values: normal, split, flip, double-faced, token, plane, scheme, phenomenon, leveler, vanguard,
	 * meld. This method is important to know if a card is normal (only one face) or double-faced.
	 * 
	 * @return a string with the value of the layout.
	 * @see MtgCard
	 */
	public String getLayout() {

		return this.primaryCardData.getLayout();
	}

	@Override
	public String toString() {

		return "Card [" + battleId + ", " + (primaryCardData != null ? primaryCardData.getName() + ", " + primaryCardData.getLayout() : "[no card data]") + ", " + tapped + ", "
				+ visible + ", " + revealed + ", " + location + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((associatedCards == null) ? 0 : associatedCards.hashCode());
		result = prime * result + (int) (battleId ^ (battleId >>> 32));
		result = prime * result + ((primaryCardData == null) ? 0 : primaryCardData.hashCode());
		result = prime * result + ((secondaryCardData == null) ? 0 : secondaryCardData.hashCode());
		result = prime * result + ((counters == null) ? 0 : counters.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + (revealed ? 1231 : 1237);
		result = prime * result + (tapped ? 1231 : 1237);
		result = prime * result + (visible ? 1231 : 1237);
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
		Card other = (Card) obj;
		if (battleId != other.battleId)
			return false;
		if (revealed != other.revealed)
			return false;
		if (tapped != other.tapped)
			return false;
		if (visible != other.visible)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (primaryCardData == null) {
			if (other.primaryCardData != null)
				return false;
		} else if (!primaryCardData.equals(other.primaryCardData))
			return false;
		if (secondaryCardData == null) {
			if (other.secondaryCardData != null)
				return false;
		} else if (!secondaryCardData.equals(other.secondaryCardData))
			return false;
		if (counters == null) {
			if (other.counters != null)
				return false;
		} else if (!counters.equals(other.counters))
			return false;
		if (associatedCards == null) {
			if (other.associatedCards != null)
				return false;
		} else if (!associatedCards.equals(other.associatedCards))
			return false;
		return true;
	}

	/**
	 * Set the value of the battle id counter used to generate the card battle id
	 * 
	 * @param value a long value >= 0
	 * @throws IllegalArgumentException if value < 0
	 */
	public static final void setBattleIdCounter(long value) {

		if (value < 0) {
			throw new IllegalArgumentException("The battle id counter must be >= 0");
		} else {
			Card.battleIdCounter = value;
		}
	}
}
