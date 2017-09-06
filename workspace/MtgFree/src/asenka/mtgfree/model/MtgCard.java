package asenka.mtgfree.model;

import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

import asenka.mtgfree.utilities.ManaManager;

/**
 * This class represents a Mtg Card. It stores all the necessary data about the cards
 * 
 * @author Asenka
 */
public class MtgCard implements Comparable<MtgCard> {

	/**
	 * The unique id of a card (based on the ID in the database)
	 */
	private int id;

	/**
	 * The card name
	 */
	private String name;

	/**
	 * The cost of a card is represented by an array of string because each symbol needs a string representation.
	 * 
	 * @see ManaManager
	 */
	private String cost;

	/**
	 * The text explaining the effect of the card
	 */
	private String rulesText;

	/**
	 * The text about the Mtg background. Useless for the game but it is part of the Mtg lore
	 */
	private String backgroundText;

	/**
	 * A card usually have one color : red, blue, green, black or white. But it can also have no color (like most of the
	 * artifacts) or also like some Eldrazi creatures. Also, it can have more than one color if the mana cost of a card include
	 * more than one color of mana. That is the reason why the color is an EnumSet of MtgColor
	 * 
	 * @see MtgColor
	 */
	private Set<MtgColor> colors;

	/**
	 * If the card is a creature, it has power and toughness. The power is stored as a string because sometimes this stat is not
	 * expressed by a number.
	 */
	private String power;

	/**
	 * If the card is a creature, it has power and toughness. The toughness is stored as a string because sometimes this stat is
	 * not expressed by a number.
	 */
	private String toughness;

	/**
	 * The loyalty value for the planeswalkers (only). <br />
	 * <code>{k > 0 if and only if card type is Planeswalker} {k = -1 otherwise}</code>
	 */
	private int loyalty;

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
	private Set<MtgFormat> formats;

	/**
	 * 
	 */
	private Set<MtgAbility> abilities;

	/**
	 * This value is not mandatory. But it could be interesting to have some comments about the card. To add some details about
	 * the cards (not localized).
	 */
	private String comments;

	/**
	 * The front image. The one where the data are visible.
	 */
	private Image imageFront;

	/**
	 * The back of the card.
	 */
	private Image imageBack;

	/**
	 * 
	 */
	private String language;

//	/**
//	 * 
//	 */
//	private static ManaManager manaManager = ManaManager.getInstance();

	/**
	 * @param id
	 * @param name
	 * @param language
	 */
	public MtgCard(int id, String name, String language) {
		super();
		this.id = id;
		this.name = name;
		this.cost = null;
		this.colors = null;
		this.language = language;
		this.loyalty = -1;
		this.state = null;
		this.rarity = MtgRarity.UNKNOWN;
		this.power = "";
		this.toughness = "";
		this.type = null;
		
	}

	/**
	 * @return
	 */
	public int getId() {

		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {

		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return
	 */
	public String getCost() {

		return cost;
	}

	/**
	 * @param cost
	 */
	public void setCost(String cost) {

		this.cost = cost;
	}

	/**
	 * @return
	 */
	public String getRulesText() {

		return rulesText;
	}

	/**
	 * @param rulesText
	 */
	public void setRulesText(String rulesText) {

		this.rulesText = rulesText;
	}

	/**
	 * @return
	 */
	public String getBackgroundText() {

		return backgroundText;
	}

	/**
	 * @param backgroundText
	 */
	public void setBackgroundText(String backgroundText) {

		this.backgroundText = backgroundText;
	}

	/**
	 * @return
	 */
	public Set<MtgColor> getColors() {

		return colors;
	}

	/**
	 * 
	 * @param colors
	 * @return
	 */
	public boolean isColor(MtgColor... colors) {

		boolean result = true;
		int i = 0;

		while (result && i < colors.length) {

			if (!this.colors.contains(colors[i++])) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * @param colors
	 */
	public void setColors(MtgColor... colors) {

		this.colors = new HashSet<MtgColor>(1);

		for (MtgColor color : colors) {
			this.colors.add(color);
		}
	}

	/**
	 * @return
	 */
	public String getPower() {

		return power;
	}

	/**
	 * @param power
	 */
	public void setPower(String power) {

		this.power = power;
	}

	/**
	 * @return
	 */
	public String getToughness() {

		return toughness;
	}

	/**
	 * @param toughness
	 */
	public void setToughness(String toughness) {

		this.toughness = toughness;
	}

	/**
	 * @return
	 */
	public int getLoyalty() {

		return loyalty;
	}

	/**
	 * @param loyalty
	 */
	public void setLoyalty(int loyalty) {

		this.loyalty = loyalty;
	}

	/**
	 * @return
	 */
	public MtgType getType() {

		return type;
	}

	/**
	 * @param type
	 */
	public void setType(MtgType type) {

		this.type = type;
	}

	/**
	 * @return
	 */
	public MtgCardState getState() {

		return state;
	}

	/**
	 * @param state
	 */
	public void setState(MtgCardState state) {

		this.state = state;
	}

	/**
	 * @return
	 */
	public MtgRarity getRarity() {

		return rarity;
	}

	/**
	 * @param rarity
	 */
	public void setRarity(MtgRarity rarity) {

		this.rarity = rarity;
	}

	/**
	 * @return
	 */
	public Set<MtgFormat> getFormats() {

		return formats;
	}

	/**
	 * @param formats
	 */
	public void setFormats(Set<MtgFormat> formats) {

		this.formats = formats;
	}

	/**
	 * @return
	 */
	public Set<MtgAbility> getAbilities() {

		return abilities;
	}

	/**
	 * @param abilities
	 */
	public void setAbilities(Set<MtgAbility> abilities) {

		this.abilities = abilities;
	}

	/**
	 * @return
	 */
	public String getComments() {

		return comments;
	}

	/**
	 * @param comments
	 */
	public void setComments(String comments) {

		this.comments = comments;
	}

	/**
	 * @return
	 */
	public Image getImageFront() {

		return imageFront;
	}

	/**
	 * @param imageFront
	 */
	public void setImageFront(Image imageFront) {

		this.imageFront = imageFront;
	}

	/**
	 * @return
	 */
	public Image getImageBack() {

		return imageBack;
	}

	/**
	 * @param imageBack
	 */
	public void setImageBack(Image imageBack) {

		this.imageBack = imageBack;
	}

	/**
	 * @return
	 */
	public String getLanguage() {

		return language;
	}

	/**
	 * @param language
	 */
	public void setLanguage(String language) {

		this.language = language;
	}

	/**
	 * 
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MtgCard o) {

		return this.name.compareTo(o.name);
	}

}
