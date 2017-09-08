package asenka.mtgfree.model.mtgcard;

import java.text.Collator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import asenka.mtgfree.model.mtgcard.state.MtgCardState;
import asenka.mtgfree.model.utilities.Localized;
import asenka.mtgfree.model.utilities.ManaManager;

/**
 * This class represents a Mtg Card. It stores all the necessary data about the cards
 * 
 * @author aAsenka
 */
public class MtgCard implements Comparable<MtgCard>, Localized {

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
	 * The name of the card collection.
	 */
	private String collectionName; // I don't to use an MtgCollection here to avoid circular references

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
	 * The type of a card
	 */
	private MtgType type;

	/**
	 * The state of a card. Only used during a game, it stores all the necessary data to manipulate the card while playing
	 * (coordinates, context, is the card tapped or not, etc.)
	 */
	private MtgCardState state;

	/**
	 * The rarity level of a card
	 */
	private MtgRarity rarity;

	/**
	 * The legal formats for this cards
	 */
	private Set<MtgFormat> formats;

	/**
	 * The abilities related to this card
	 */
	private Set<MtgAbility> abilities;

	/**
	 * This value is not mandatory. But it could be interesting to have some comments about the card. To add some details about
	 * the cards (not localized).
	 */
	private String comments;

	/**
	 * The language used for the card data
	 */
	private Locale locale;

	/**
	 * The collator use to compare card's names
	 */
	private transient Collator collator;

	// /**
	// *
	// */
	// private static ManaManager manaManager = ManaManager.getInstance();

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param name
	 * @param color
	 * @param type
	 * @param rarity
	 * @param locale
	 */
	public MtgCard(int id, String name, MtgColor color, MtgType type, MtgRarity rarity, Locale locale) {

		this(id, name, null, "", "", null, "", "", "", -1, type, null, rarity, new HashSet<MtgFormat>(), new HashSet<MtgAbility>(), "",
				locale);
		this.setColors(color);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param name
	 * @param cost
	 * @param rulesText
	 * @param backgroundText
	 * @param colors
	 * @param collectionName
	 * @param power a string with a number from 0 to 99 or X or * or a combination of the previous
	 * @param toughness a string with a number from 0 to 99 or X or * or a combination of the previous
	 * @param loyalty
	 * @param type
	 * @param state
	 * @param rarity
	 * @param formats
	 * @param abilities
	 * @param comments
	 * @param locale
	 */
	public MtgCard(int id, String name, String cost, String rulesText, String backgroundText, Set<MtgColor> colors, String collectionName,
			String power, String toughness, int loyalty, MtgType type, MtgCardState state, MtgRarity rarity, Set<MtgFormat> formats,
			Set<MtgAbility> abilities, String comments, Locale locale) {

		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.rulesText = rulesText;
		this.backgroundText = backgroundText;
		this.colors = colors;
		this.collectionName = collectionName;
		this.power = power;
		this.toughness = toughness;
		this.loyalty = loyalty;
		this.type = type;
		this.state = state;
		this.rarity = rarity;
		this.formats = formats;
		this.abilities = abilities;
		this.comments = comments;
		this.locale = locale;
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
	 * @return the localized card name
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name the card name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return a string representing the mana cost of the card
	 */
	public String getCost() {

		return cost;
	}

	/**
	 * Update the cost value. Updating the cost, also update the colors,
	 * 
	 * @param cost a string representing the mana cost of a card
	 * @throws IllegalArgumentException if cost is null or if it contains some illegal values
	 */
	public void setCost(String cost) throws IllegalArgumentException {

		ManaManager manaManager = ManaManager.getInstance();

		// If cost is not empty, update the colors of the card
		if (cost != null && !cost.isEmpty()) {
			this.colors = manaManager.getColors(cost);
		}
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
	 * Set the card's color and check if they matches with the card's cost.
	 * 
	 * @param colors a set of MtgColor
	 * @throws IllegalArgumentException if the new colors and the cost are not compatible
	 */
	public void setColors(Set<MtgColor> colors) throws IllegalArgumentException {

		// Even though, an exception has been raised due to cost and
		// color incompatibility the value is still set. That's why
		// the setting is done before the checking.
		this.colors = colors;

		if (colors != null && colors.equals(ManaManager.getInstance().getColors(this.cost))) {
			throw new IllegalArgumentException("The new color(s) " + colors + " do not match the card cost : " + cost);
		}
	}

	/**
	 * Set the card's color and check if they matches with the card's cost.
	 * 
	 * @param colors an array of MtgColor
	 * @throws IllegalArgumentException if the new colors and the cost are not compatible
	 */
	public void setColors(MtgColor... colors) throws IllegalArgumentException {

		// Even though, an exception has been raised due to cost and
		// color incompatibility the value is still set. That's why
		// the setting is done before the checking.
		this.colors = new HashSet<MtgColor>(colors.length);

		for (MtgColor color : colors) {
			this.colors.add(color);
		}

		// Check if the cost and the new card's colors are still matching.
		if (this.colors.equals(ManaManager.getInstance().getColors(this.cost))) {
			throw new IllegalArgumentException("The new color(s) " + colors + " do not match the card cost : " + cost);
		}
	}

	/**
	 * @return the localized name of the card's collection
	 */
	public String getCollectionName() {

		return collectionName;
	}

	/**
	 * Set the collectionName
	 * @param collectionName the localized name of the card's collection
	 */
	public void setCollectionName(String collectionName) {

		this.collectionName = collectionName;
	}

	/**
	 * Get the card's power. It the card is not a creature or a vehicle it returns an empty string
	 * @return the card power.
	 */
	public String getPower() {

		return power;
	}

	/**
	 * set the card's power
	 * @param power a string with a number from 0 to 99 or X or * or a combination of the previous
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

	@Override
	public Locale getLocale() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {

		return "[" + id + ", " + (name != null ? name + ", " : "") + (cost != null ? cost + ", " : "")
				+ (rulesText != null ? rulesText + ", " : "") + (backgroundText != null ? backgroundText + ", " : "")
				+ (colors != null ? colors + ", " : "") + (collectionName != null ? collectionName + ", " : "")
				+ (power != null ? power + ", " : "") + (toughness != null ? toughness + ", " : "") + loyalty + ", "
				+ (type != null ? type + ", " : "") + (state != null ? state + ", " : "") + (rarity != null ? rarity + ", " : "")
				+ (formats != null ? formats + ", " : "") + (abilities != null ? abilities + ", " : "")
				+ (comments != null ? comments + ", " : "") + (locale != null ? locale : "") + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((abilities == null) ? 0 : abilities.hashCode());
		result = prime * result + ((backgroundText == null) ? 0 : backgroundText.hashCode());
		result = prime * result + ((collectionName == null) ? 0 : collectionName.hashCode());
		result = prime * result + ((colors == null) ? 0 : colors.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((formats == null) ? 0 : formats.hashCode());
		result = prime * result + id;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
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
		if (collectionName == null) {
			if (other.collectionName != null)
				return false;
		} else if (!collectionName.equals(other.collectionName))
			return false;
		if (colors == null) {
			if (other.colors != null)
				return false;
		} else if (!colors.equals(other.colors))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (formats == null) {
			if (other.formats != null)
				return false;
		} else if (!formats.equals(other.formats))
			return false;
		if (id != other.id)
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
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

	@Override
	public int compareTo(MtgCard card) {

		if (this.collator == null) {
			this.collator = Collator.getInstance(this.locale);
		}
		int result = this.collator.compare(this.name, card.name);

		if (result == 0) {
			result = this.collator.compare(this.collectionName, card.collectionName);
		}
		return result;
	}

}
