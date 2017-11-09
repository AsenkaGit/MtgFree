package asenka.mtgfree.model.game;

/**
 * <p>
 * This origin indicates the origin of a card about to be played. For example, when you want to play a card on the battlefield
 * from your hand, then the origin would be HAND. When you want to exile a card from your library, then the origin would be
 * LIBRARY.
 * </p>
 * <p>
 * It is important to have this information to be able to remove the card from its original collection.
 * </p>
 */
public enum Origin {
	BATTLEFIELD, HAND, LIBRARY, EXILE, GRAVEYARD, OPPONENT_BATTLEFIELD, OPPONENT_GRAVEYARD, OPPONENT_EXILE
}
