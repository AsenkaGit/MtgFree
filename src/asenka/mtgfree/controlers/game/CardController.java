package asenka.mtgfree.controlers.game;

import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Counter;

public class CardController extends Controller<Card> {

	public CardController(Card card) {
		super(card);
	}

	public void setTapped(boolean tapped) {

		this.data.setTapped(tapped);
	}

	public void setVisible(boolean visible) {

		this.data.setVisible(visible);
	}

	public void setRevealed(boolean revealed) {

		this.data.setRevealed(revealed);
	}

	public void setLocation(double x, double y) {

		this.data.setLocation(x, y);
	}

	public void addCounter(Counter counter) throws IllegalArgumentException {

		if (counter == null) {
			throw new IllegalArgumentException("null counters are not allowed");
		} else {
			this.data.addCounter(counter);
		}
	}

	public void removeCounter(Counter counter) throws IllegalArgumentException {

		if (counter == null) {
			throw new IllegalArgumentException("Try to remove a null counter");
		} else {
			this.data.removeCounter(counter);
		}
	}

	public void clearCounters() {

		this.data.clearCounters();
	}

	public void addAssociatedCard(Card associatedCard) throws IllegalArgumentException {
		
		if (associatedCard != null && !this.data.equals(associatedCard)) {
			this.data.addAssociatedCard(associatedCard);
		} else {
			throw new IllegalArgumentException("Unable to associate the card " + this.data + " with " + associatedCard);
		}
	}
	
	public void removeAssociatedCard(Card associatedCard) throws IllegalArgumentException {

		if (associatedCard != null && !this.data.equals(associatedCard)) {
			this.data.removeAssociatedCard(associatedCard);
		} else {
			throw new IllegalArgumentException("Unable to remove from " + this.data + " the associated card  " + associatedCard);
		}
	}
}
