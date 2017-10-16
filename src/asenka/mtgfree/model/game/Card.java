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
	private static final long serialVersionUID = -6222320505854153936L;

	/**
	 * The counter used to generated card battle id
	 */
	private static long battleIdCounter = 0;
	
	/**
	 * The ID of the card on the battlefield. It is automativally generated when
	 * the card is created and does not change
	 */
	private final long battleId;

	/**
	 * The data of the card (name, text, color(s), type(s), etc...
	 */
	private final MtgCard cardData;

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
		this.cardData = cardData;
		this.visible = true;
		this.revealed = false;
		this.tapped = false;
		this.location = new Point2D.Double(-1.0, -1.0);
		this.associatedCards = new LinkedList<Card>();
		this.counters = new HashSet<Counter>(2);
	}

	/**
	 * @return the battleId
	 */
	public long getBattleId() {
	
		return battleId;
	}

	public MtgCard getCardData() {

		return cardData;
	}

	public boolean isTapped() {

		return tapped;
	}

	public void setTapped(boolean tapped) {

		if (this.tapped != tapped) {
			this.tapped = tapped;
			super.setChanged();
			super.notifyObservers(new CardEvent("set", "tapped", new Boolean(tapped)));
		}
	}

	public boolean isVisible() {

		return visible;
	}

	public void setVisible(boolean visible) {

		if (this.visible != visible) {
			this.visible = visible;
			super.setChanged();
			super.notifyObservers(new CardEvent("set", "visible", new Boolean(visible)));
		}
	}

	public boolean isRevealed() {

		return revealed;
	}

	public void setRevealed(boolean revealed) {

		if (this.revealed != revealed) {
			this.revealed = revealed;
			super.setChanged();
			super.notifyObservers(new CardEvent("set", "revealed", new Boolean(revealed)));
		}
	}

	public Point2D.Double getLocation() {

		return location;
	}

	public void setLocation(final double x, final double y) {

		// If one of the value has changed
		if (x != this.location.getX() || y != this.location.getY()) {
			this.location.setLocation(x, y);
			super.setChanged();
			super.notifyObservers(new CardEvent("set", "location", this.location));
		}

	}

	public List<Card> getAssociatedCards() {

		return associatedCards;
	}

	public void addAssociatedCard(Card card) {

		if (card != null) {
			this.associatedCards.add(card);
			super.setChanged();
			super.notifyObservers(new CardEvent("add", "associatedCards", card));
		}
	}

	public void removeAssociatedCard(Card card) {

		if (this.associatedCards.remove(card)) {
			super.setChanged();
			super.notifyObservers(new CardEvent("remove", "associatedCards", card));
		}
	}

	public Set<Counter> getCounters() {

		return counters;
	}

	public void addCounter(Counter counter) {

		if (counter != null) {
			this.counters.add(counter);
			super.setChanged();
			super.notifyObservers(new CardEvent("add", "counter", counter));
		}
	}

	public void removeCounter(Counter counter) {

		if (this.counters.remove(counter)) {
			super.setChanged();
			super.notifyObservers(new CardEvent("remove", "counter", counter));
		}
	}

	@Override
	public String toString() {

		return "Card [" + battleId + ", " +  (cardData != null ? cardData.getName() : "[no card data]") + ", " + tapped + ", " + visible + ", " + revealed + ", " + location + "]";
	}

	
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((associatedCards == null) ? 0 : associatedCards.hashCode());
		result = prime * result + (int) (battleId ^ (battleId >>> 32));
		result = prime * result + ((cardData == null) ? 0 : cardData.hashCode());
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
		if (cardData == null) {
			if (other.cardData != null)
				return false;
		} else if (!cardData.equals(other.cardData))
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
	 * Set the value of the battle id counter used to
	 * generate the card battle id 
	 * @param value a long value >= 0
	 * @throws IllegalArgumentException if value < 0
	 */
	public static final void setBattleIdCounter(long value) {
		
		if(value < 0) {
			throw new IllegalArgumentException("The battle id counter must be >= 0");
		} else {
			Card.battleIdCounter = value;
		}
	}
}
