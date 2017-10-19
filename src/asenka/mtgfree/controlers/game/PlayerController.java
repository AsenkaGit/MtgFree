package asenka.mtgfree.controlers.game;

import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;

public class PlayerController extends Controller<Player> {

	protected PlayerController(Player observedPlayer) {
		super(observedPlayer);
	}
	
	public void draw() {
		
		
	}

	public void shuffle() {

		this.data.getLibrary().shuffle();
	}

//	public void addOnTop(Card card) throws IllegalArgumentException {
//
//		if (card != null) {
//			this.data.addOnTop(card);
//		} else {
//			throw new IllegalArgumentException("Try to add a null card on the top of the library");
//		}
//	}
//
//	public void addToBottom(Card card) throws IllegalArgumentException {
//
//		if (card != null) {
//			this.data.addToBottom(card);
//		} else {
//			throw new IllegalArgumentException("Try to add a null card on the top of the library");
//		}
//	}
//
//	public void addToBottom(Card card, int x) throws IllegalArgumentException {
//
//		if (card != null && x >= 0) {
//			this.data.addFromTop(card, x);
//		} else {
//			throw new IllegalArgumentException(
//					card == null ? "Try to add a null card on the top of the library" : x + " should be a positive integer");
//		}
//	}

}
