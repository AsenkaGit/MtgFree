package asenka.mtgfree.model;


/**
 * This card manage the card state :
 * > tapped/untapped (only on battlefield)
 * > visible/hidden (only on battlefield and exile)
 * > secret/revealed (only in the player's hand)
 * > the situation of the card : Library, Hand, Graveyard, etc...)
 * @author Asenka
 */
public class MtgCardState {

	/**
	 * Default constructor
	 */
	public MtgCardState() {
	}

	
	// ##########################################################
	// #														#
	// #				Class parameters						#
	// #														#
	// ##########################################################
	
	
	/**
	 * isTapped = false
	 * isVisible = false
	 * isRevealed = false
	 * location = LIBRARY
	 */
	public static MtgCardState DEFAULT_LIBRARY_STATE;

	/**
	 * isTapped = false
	 * isVisible = false
	 * isRevealed = false
	 * location = HAND
	 */
	public static MtgCardState DEFAULT_HAND_STATE;

	/**
	 * isTapped = false
	 * isVisible = true
	 * isRevealed = false
	 * location = BATTLEFIELD
	 */
	public static MtgCardState DEFAULT_BATTLEFIELD_STATE;

	/**
	 * isTapped = false
	 * isVisible = true
	 * isRevealed = false
	 * location = GRAVEYARD
	 */
	public static MtgCardState DEFAULT_GRAVEYARD_STATE;

	/**
	 * isTapped = false
	 * isVisible = true
	 * isRevealed = false
	 * location = EXILE
	 */
	public static MtgCardState DEFAULT_EXILE_STATE;

	/**
	 * if true => the card is tapped. If the card is not on the battlefield, this value should be false.
	 */
	private boolean isTapped;

	/**
	 * If true => the card is visible. Should be false if the card is neither on the battlefield or the exile area.
	 */
	private boolean isVisible;

	/**
	 * if true => the card is revealed. It means that the card  should be visible for all players/viewers. It should be false if the card is not in the player's hand.
	 */
	private boolean isRevealed;

	/**
	 * 
	 */
	private MtgContext context;

	/**
	 * All the possible locations for the cards :
	 * > player's library
	 * > player's hand
	 * > battlefield
	 * > player's graveyard
	 * > player's exile area
	 */
	public enum MtgContext {
		LIBRARY,
		HAND,
		BATTLEFIELD,
		GRAVEYARD,
		EXIL
	}

}