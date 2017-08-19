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
	// #														
	// #				Enumerations							
	// #														
	// ##########################################################
	
	/**
	 * The rarity of a card.
	 */
	public enum MtgRarity { COMMON, UNCOMMON, RARE, MYHTIC }
	
	/**
	 * 
	 */
	public enum MtgColor {RED, GREEN, BLUE, BLACK, WHITE, NOCOLOR, MULTI}

	// ##########################################################
	// #														
	// #				Static attributes							
	// #														
	// ##########################################################
	
	/**
	 * A static field storing the default back image. Like this, the back image is loaded only once.
	 */
	public static final Image DEFAULT_BACK_IMAGE = null; // TODO create an image loader for the back

	// ##########################################################
	// #														
	// #				Attributes						
	// #														
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
	 * 
	 */
	private MtgColor color;

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
	 * @return the color
	 */
	public MtgColor getColor() {
		return color;
	}



	/**
	 * @param color the color to set
	 */
	public void setColor(MtgColor color) {
		this.color = color;
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





	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + id + ", " + name + ", " + rulesText + ", " + backgroundText + ", " + color + ", " + power + ", "
				+ toughness + ", " + comments + ", " + loyalty + ", " + language + ", " + type + ", " 
				+ ", " + state + ", " + rarity + ", " + formats + ", " + abilities + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abilities == null) ? 0 : abilities.hashCode());
		result = prime * result + ((backgroundText == null) ? 0 : backgroundText.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((formats == null) ? 0 : formats.hashCode());
		result = prime * result + id;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + loyalty;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((power == null) ? 0 : power.hashCode());
		result = prime * result + ((rarity == null) ? 0 : rarity.hashCode());
		result = prime * result + ((rulesText == null) ? 0 : rulesText.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((toughness == null) ? 0 : toughness.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MtgCard other = (MtgCard) obj;
		if (abilities == null) {
			if (other.abilities != null)
				return false;
		} else if (!abilities.equals(other.abilities))
			return false;
		if (backgroundText == null) {
			if (other.backgroundText != null)
				return false;
		} else if (!backgroundText.equals(other.backgroundText))
			return false;
		if (color != other.color)
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (formats == null) {
			if (other.formats != null)
				return false;
		} else if (!formats.equals(other.formats))
			return false;
		if (id != other.id)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (loyalty != other.loyalty)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (power == null) {
			if (other.power != null)
				return false;
		} else if (!power.equals(other.power))
			return false;
		if (rarity != other.rarity)
			return false;
		if (rulesText == null) {
			if (other.rulesText != null)
				return false;
		} else if (!rulesText.equals(other.rulesText))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (toughness == null) {
			if (other.toughness != null)
				return false;
		} else if (!toughness.equals(other.toughness))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}