package asenka.mtgfree.model;


import java.awt.Image;
import java.util.*;

/**
 * This class represents a Mtg Card. It stores all the necessary data about the cards :
 * > Images,
 * > Texts,
 * > Type,
 * > Stats,
 * > Abilities,
 * > etc...
 * @author Asenka
 */
public class MtgCard {

	/**
	 * Default constructor
	 */
	public MtgCard() {
	}

	/**
	 * A static field storing the default back image. Like this, the back image is loaded only once.
	 */
	private static Image DEFAULT_BACK_IMAGE;

	/**
	 * The unique id of a card (based on the ID in the database)
	 */
	private int id;

	/**
	 * The card name (localized)
	 */
	private String name;

	/**
	 * The text explaining the effect of the card (localized).
	 * 
	 * NOTE :
	 * These text needs to have some symbols:
	 * > tap : {t}
	 * > untap : {u}
	 * > X not colored mana : {<X>} (where X is an integer between 0 and 99)
	 * > red|blue|black|green|white mana : {r} | {b} | {k} | {g} | {w}
	 * > uncolored mana : {m}
	 * > energy counter : {e}
	 * > poison counter : {p}
	 */
	private String rulesText;

	/**
	 * The text about the Mtg background. Useless for the game but it is part of the Mtg lore (localized).
	 * 
	 * It has to be written with italic font (like on the cards).
	 */
	private String backgroundText;

	/**
	 * If the card is a creature, it has power and toughness. The power is stored as a string because sometimes this stat is  not expressed by a number.
	 */
	private String power;

	/**
	 * If the card is a creature, it has power and toughness. The toughness is stored as a string because sometimes this stat is  not expressed by a number.
	 */
	private String toughness;

	/**
	 * The front image. The one where the data are visible.
	 */
	private Image imageFront;

	/**
	 * The back of the card.
	 */
	private Image imageBack;

	/**
	 * This value is not mandatory. But it could be interesting to have some comments about the card. To add some details about the cards (not localized).
	 */
	private String comments;

	/**
	 * The loyalty value  for the planewalkers (only). 
	 * {k >= 0 if and only if card type is Planewalker}
	 * {k = -1 otherwise}
	 */
	private int loyalty;

	/**
	 * 
	 */
	private String language;

	/**
	 * 
	 */
	private MtgType type;

	/**
	 * 
	 */
	private MtgCollection collection;

	/**
	 * 
	 */
	private Set<MtgAbility> abilities;

	/**
	 * 
	 */
	private MtgCardState state;

	/**
	 * 
	 */
	private Set<MtgFormat> formats;

	/**
	 * 
	 */
	private MtgRarity rarity;



	/**
	 * The rarity of a card.
	 */
	public enum MtgRarity {
		COMMON,
		UNCOMMON,
		RARE,
		MYHTIC
	}

}