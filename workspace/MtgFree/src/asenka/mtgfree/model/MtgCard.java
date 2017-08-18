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
	
	// ##########################################################
	// #														#
	// #				Enumerations							#
	// #														#
	// ##########################################################
	
	/**
	 * The rarity of a card.
	 */
	public enum MtgRarity { COMMON, UNCOMMON, RARE, MYHTIC }

	// ##########################################################
	// #														#
	// #				Static members							#
	// #														#
	// ##########################################################
	
	/**
	 * A static field storing the default back image. Like this, the back image is loaded only once.
	 */
	public static final Image DEFAULT_BACK_IMAGE = null; // TODO create an image loader for the back

	// ##########################################################
	// #														#
	// #				Class parameters						#
	// #														#
	// ##########################################################


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
	 * The loyalty value  for the planeswalkers (only). 
	 * {k >= 0 if and only if card type is Planeswalker}
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
	private MtgCardState state;
	

	/**
	 * 
	 */
	private MtgRarity rarity;


	/**
	 * 
	 */
	private List<MtgFormat> formats;
	
	/**
	 * 
	 */
	private List<MtgAbility> abilities;
	
	// ##########################################################
	// #														#
	// #				Constructors							#
	// #														#
	// ##########################################################

	/**
	 * Default constructor
	 */
	public MtgCard() {
		super();
	}

	

	/**
	 * 
	 * @param id
	 * @param language
	 */
	public MtgCard(int id, String language) {
		super();
		this.id = id;
		this.language = language;
	}



	/**
	 * @param id
	 * @param name
	 * @param rulesText
	 * @param backgroundText
	 * @param power
	 * @param toughness
	 * @param imageFront
	 * @param imageBack
	 * @param comments
	 * @param loyalty
	 * @param language
	 * @param type
	 * @param collection
	 * @param state
	 * @param rarity
	 */
	public MtgCard(int id, String name, String rulesText, String backgroundText, String power, String toughness,
			Image imageFront, Image imageBack, String comments, int loyalty, String language, MtgType type,
			MtgCollection collection, MtgCardState state, MtgRarity rarity) {
		super();
		this.id = id;
		this.name = name;
		this.rulesText = rulesText;
		this.backgroundText = backgroundText;
		this.power = power;
		this.toughness = toughness;
		this.imageFront = imageFront;
		this.imageBack = imageBack;
		this.comments = comments;
		this.loyalty = loyalty;
		this.language = language;
		this.type = type;
		this.collection = collection;
		this.state = state;
		this.rarity = rarity;
	}



	// ##########################################################
	// #														#
	// #				Methods									#
	// #														#
	// ##########################################################
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the rulesText
	 */
	public String getRulesText() {
		return rulesText;
	}

	/**
	 * @param rulesText the rulesText to set
	 */
	public void setRulesText(String rulesText) {
		this.rulesText = rulesText;
	}

	/**
	 * @return the backgroundText
	 */
	public String getBackgroundText() {
		return backgroundText;
	}

	/**
	 * @param backgroundText the backgroundText to set
	 */
	public void setBackgroundText(String backgroundText) {
		this.backgroundText = backgroundText;
	}

	/**
	 * @return the power
	 */
	public String getPower() {
		return power;
	}

	/**
	 * @param power the power to set
	 */
	public void setPower(String power) {
		this.power = power;
	}

	/**
	 * @return the toughness
	 */
	public String getToughness() {
		return toughness;
	}

	/**
	 * @param toughness the toughness to set
	 */
	public void setToughness(String toughness) {
		this.toughness = toughness;
	}

	/**
	 * @return the imageFront
	 */
	public Image getImageFront() {
		return imageFront;
	}

	/**
	 * @param imageFront the imageFront to set
	 */
	public void setImageFront(Image imageFront) {
		this.imageFront = imageFront;
	}

	/**
	 * @return the imageBack
	 */
	public Image getImageBack() {
		return imageBack;
	}

	/**
	 * @param imageBack the imageBack to set
	 */
	public void setImageBack(Image imageBack) {
		this.imageBack = imageBack;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the loyalty
	 */
	public int getLoyalty() {
		return loyalty;
	}

	/**
	 * @param loyalty the loyalty to set
	 */
	public void setLoyalty(int loyalty) {
		this.loyalty = loyalty;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the type
	 */
	public MtgType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MtgType type) {
		this.type = type;
	}

	/**
	 * @return the collection
	 */
	public MtgCollection getCollection() {
		return collection;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollection(MtgCollection collection) {
		this.collection = collection;
	}

	/**
	 * @return the state
	 */
	public MtgCardState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(MtgCardState state) {
		this.state = state;
	}

	/**
	 * @return the rarity
	 */
	public MtgRarity getRarity() {
		return rarity;
	}

	/**
	 * @param rarity the rarity to set
	 */
	public void setRarity(MtgRarity rarity) {
		this.rarity = rarity;
	}

	/**
	 * @return the formats
	 */
	public List<MtgFormat> getFormats() {
		return formats;
	}

	/**
	 * @param formats the formats to set
	 */
	public void setFormats(List<MtgFormat> formats) {
		this.formats = formats;
	}

	/**
	 * @return the abilities
	 */
	public List<MtgAbility> getAbilities() {
		return abilities;
	}

	/**
	 * @param abilities the abilities to set
	 */
	public void setAbilities(List<MtgAbility> abilities) {
		this.abilities = abilities;
	}

	
	

}