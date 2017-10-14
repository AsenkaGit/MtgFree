package asenka.mtgfree.model.game;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.model.pojo.MtgCard;

/**
 * 
 * 
 * @author asenka
 *
 */
public class Card extends Observable {

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
		this.cardData = cardData;
		this.visible = true;
		this.revealed = false;
		this.tapped = false;
		this.location = new Point2D.Double(-1.0, -1.0);
		this.associatedCards = new LinkedList<Card>();
		this.counters = new HashSet<Counter>(2);
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
			super.notifyObservers();
		}
	}

	public boolean isVisible() {

		return visible;
	}

	public void setVisible(boolean visible) {

		if (this.visible != visible) {
			this.visible = visible;
			super.setChanged();
			super.notifyObservers();
		}
	}

	public boolean isRevealed() {

		return revealed;
	}

	public void setRevealed(boolean revealed) {

		if (this.revealed != revealed) {
			this.revealed = revealed;
			super.setChanged();
			super.notifyObservers();
		}
	}

	public Point2D.Double getLocation() {

		return location;
	}

	public void setLocation(final double x, final double y) {

		// If one of the value has changed...
		if (x != this.location.getX() || y != this.location.getY()) {
			this.location.setLocation(x, y);
			super.setChanged();
			super.notifyObservers();
		}

	}

	public List<Card> getAssociatedCards() {

		return associatedCards;
	}

	public void addAssociatedCard(Card card) {

		if (card != null) {
			this.associatedCards.add(card);
			super.setChanged();
			super.notifyObservers();
		}
	}

	public void removeAssociatedCard(Card card) {

		if (this.associatedCards.remove(card)) {
			super.setChanged();
			super.notifyObservers();
		}
	}

	public Set<Counter> getCounters() {

		return counters;
	}

	public void addCounter(Counter counter) {

		if (counter != null) {
			this.counters.add(counter);
			super.setChanged();
			super.notifyObservers();
		}
	}

	public void removeCounter(Counter counter) {

		if (this.counters.remove(counter)) {
			super.setChanged();
			super.notifyObservers();
		}
	}

}
