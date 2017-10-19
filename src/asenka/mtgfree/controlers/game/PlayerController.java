package asenka.mtgfree.controlers.game;

import java.util.Arrays;
import java.util.List;

import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Player;

public class PlayerController extends Controller<Player> {

	protected PlayerController(Player observedPlayer) {

		super(observedPlayer);
	}

	public void draw() {

		Card card = this.data.getLibrary().draw();
		this.data.addCardToHand(card);
	}

	public void draw(int x) {

		List<Card> cards = this.data.getLibrary().draw(x);
		cards.forEach(card -> this.data.addCardToHand(card));
	}

	public void addCardToBattlefield(Card card, boolean visible, double x, double y) {

		card.setLocation(x, y);
		card.setVisible(visible);
		this.data.getBattlefield().addCard(card);
	}
	
	public void removeCardFromBattlefield(Card card) {
		
		// TODO
	}

	public void playFromHand(Card card, boolean visible, double x, double y) {

		if (this.data.removeCardFromHand(card)) {
			addCardToBattlefield(card, visible, x, y);
		} else {
			throw new RuntimeException("Try to play a card from hand, but it seems the " + card + " is not in the player's hand");
		}
	}

	public void playFromGraveyard(Card card, boolean visible, double x, double y) {

		if (this.data.removeCardFromGraveyard(card)) {
			addCardToBattlefield(card, visible, x, y);
		} else {
			throw new RuntimeException("Try to play a card from graveyard, but it seems the " + card + " is not in the player's graveyard");
		}
	}

	public void playFromExile(Card card, boolean visible, double x, double y) {

		if (this.data.removeCardFromExile(card)) {
			addCardToBattlefield(card, visible, x, y);
		} else {
			throw new RuntimeException(
				"Try to play a card from exile area, but it seems the " + card + " is not in the player's exile area");
		}
	}

	public void playFromLibrary(Card card, boolean visible, double x, double y) {

		if (this.data.getLibrary().remove(card)) {
			addCardToBattlefield(card, visible, x, y);
		} else {
			throw new RuntimeException("Try to play a card from library, but it seems the " + card + " is not in the player's library");
		}
	}

	public void setTapped(boolean tapped, Card card) {
		
		card.setTapped(tapped);
	}

	public void setTapped(boolean tapped, List<Card> cards) {

		cards.forEach(card -> card.setTapped(tapped));
	}

	public void setTappedAll(boolean tapped) {
	
		this.data.getBattlefield().getCards().forEach(card -> card.setTapped(tapped));
	}

	public void shuffleLibrary() {

		this.data.getLibrary().shuffle();
	}
	
	

}
