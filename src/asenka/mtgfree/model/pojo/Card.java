package asenka.mtgfree.model.pojo;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>
 * Store all the data related to a card
 * </p>
 * 
 * <p>
 * This implementation is fully based on the specifications described in the <a href="https://mtgjson.com/documentation.html">MTG
 * Json documentation</a>
 * </p>
 * 
 * @author asenka
 */
public class Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -631051945429138079L;

	/**
	 * A unique id for this card. It is made up by doing an SHA1 hash of setCode + cardName + cardImageName
	 */
	private String id;

	/**
	 * The card layout. Possible values: normal, split, flip, double-faced, token, plane, scheme, phenomenon, leveler, vanguard,
	 * meld
	 */
	private String layout;

	/**
	 * The card name. For split, double-faced and flip cards, just the name of one side of the card. Basically each 'sub-card' has
	 * its own record.
	 */
	private String name;

	/**
	 * Only used for split, flip, double-faced, and meld cards. Will contain all the names on this card, front or back. For meld
	 * cards, the first name is the card with the meld ability, which has the top half on its back, the second name is the card
	 * with the reminder text, and the third name is the melded back face.
	 */
	private String[] names;

	/**
	 * 
	 */
	private String manaCost;

	/**
	 * 
	 */
	private float cmc;

	/**
	 * 
	 */
	private String[] colors;

	/**
	 * 
	 */
	private String[] colorIdentity;

	/**
	 * 
	 */
	private String type;

	/**
	 * 
	 */
	private String[] supertypes;

	/**
	 * 
	 */
	private String[] types;

	/**
	 * 
	 */
	private String[] subtypes;

	/**
	 * 
	 */
	private String rarity;

	/**
	 * 
	 */
	private String text;

	/**
	 * 
	 */
	private String flavor;

	/**
	 * 
	 */
	private String artist;

	/**
	 * 
	 */
	private String number;

	/**
	 * 
	 */
	private String power;

	/**
	 * 
	 */
	private String toughness;

	/**
	 * 
	 */
	private int loyalty;

	/**
	 * 
	 */
	private int multiverseid;

	/**
	 * 
	 */
	private int[] variations;

	/**
	 * 
	 */
	private String imageName;

	/**
	 * 
	 */
	private String watermark;

	/**
	 * 
	 */
	private String border;

	/**
	 * 
	 */
	private boolean timeshifted;

	/**
	 * 
	 */
	private int hand;

	/**
	 * 
	 */
	private int life;

	/**
	 * 
	 */
	private boolean reserved;

	/**
	 * 
	 */
	private String releaseDate;

	/**
	 * 
	 */
	private boolean starter;

	/**
	 * 
	 */
	private int mciNumber;

	// From here this is extra data about the card that are not available with all JSON files from MTG JSON

	/**
	 * The rulings for the card. An array of objects, each object having 'date' and 'text' keys.
	 */
	private Ruling[] rulings;

	/**
	 * Foreign language names for the card, if this card in this set was printed in another language. An array of objects, each
	 * object having 'language', 'name' and 'multiverseid' keys. Not available for all sets.
	 */
	private ForeignName[] foreignNames;

	/**
	 * The sets that this card was printed in, expressed as an array of set codes.
	 */
	private String[] printings;

	/**
	 * The original text on the card at the time it was printed. This field is not available for promo cards.
	 */
	private String originalText;

	/**
	 * The original type on the card at the time it was printed. This field is not available for promo cards.
	 */
	private String originalType;

	/**
	 * Which formats this card is legal, restricted or banned in. An array of objects, each object having 'format' and 'legality'.
	 */
	private Legality[] legalities;

	/**
	 * For promo cards, this is where this card was originally obtained. For box sets that are theme decks, this is which theme
	 * deck the card is from. For clash packs, this is which deck it is from.
	 */
	private String source;

	/**
	 * 
	 * @param id
	 * @param layout
	 * @param name
	 * @param names
	 * @param manaCost
	 * @param cmc
	 * @param colors
	 * @param colorIdentity
	 * @param type
	 * @param supertypes
	 * @param types
	 * @param subtypes
	 * @param rarity
	 * @param text
	 * @param flavor
	 * @param artist
	 * @param number
	 * @param power
	 * @param toughness
	 * @param loyalty
	 * @param multiverseid
	 * @param variations
	 * @param imageName
	 * @param watermark
	 * @param border
	 * @param timeshifted
	 * @param hand
	 * @param life
	 * @param reserved
	 * @param releaseDate
	 * @param starter
	 * @param mciNumber
	 * @param rulings
	 * @param foreignNames
	 * @param printings
	 * @param originalText
	 * @param originalType
	 * @param legalities
	 * @param source
	 */
	public Card(String id, String layout, String name, String[] names, String manaCost, float cmc, String[] colors, String[] colorIdentity,
			String type, String[] supertypes, String[] types, String[] subtypes, String rarity, String text, String flavor, String artist,
			String number, String power, String toughness, int loyalty, int multiverseid, int[] variations, String imageName,
			String watermark, String border, boolean timeshifted, int hand, int life, boolean reserved, String releaseDate, boolean starter,
			int mciNumber, Ruling[] rulings, ForeignName[] foreignNames, String[] printings, String originalText, String originalType,
			Legality[] legalities, String source) {
		super();
		this.id = id;
		this.layout = layout;
		this.name = name;
		this.names = names;
		this.manaCost = manaCost;
		this.cmc = cmc;
		this.colors = colors;
		this.colorIdentity = colorIdentity;
		this.type = type;
		this.supertypes = supertypes;
		this.types = types;
		this.subtypes = subtypes;
		this.rarity = rarity;
		this.text = text;
		this.flavor = flavor;
		this.artist = artist;
		this.number = number;
		this.power = power;
		this.toughness = toughness;
		this.loyalty = loyalty;
		this.multiverseid = multiverseid;
		this.variations = variations;
		this.imageName = imageName;
		this.watermark = watermark;
		this.border = border;
		this.timeshifted = timeshifted;
		this.hand = hand;
		this.life = life;
		this.reserved = reserved;
		this.releaseDate = releaseDate;
		this.starter = starter;
		this.mciNumber = mciNumber;
		this.rulings = rulings;
		this.foreignNames = foreignNames;
		this.printings = printings;
		this.originalText = originalText;
		this.originalType = originalType;
		this.legalities = legalities;
		this.source = source;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getLayout() {

		return layout;
	}

	public void setLayout(String layout) {

		this.layout = layout;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String[] getNames() {

		return names;
	}

	public void setNames(String[] names) {

		this.names = names;
	}

	public String getManaCost() {

		return manaCost;
	}

	public void setManaCost(String manaCost) {

		this.manaCost = manaCost;
	}

	public float getCmc() {

		return cmc;
	}

	public void setCmc(float cmc) {

		this.cmc = cmc;
	}

	public String[] getColors() {

		return colors;
	}

	public void setColors(String[] colors) {

		this.colors = colors;
	}

	public String[] getColorIdentity() {

		return colorIdentity;
	}

	public void setColorIdentity(String[] colorIdentity) {

		this.colorIdentity = colorIdentity;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String[] getSupertypes() {

		return supertypes;
	}

	public void setSupertypes(String[] supertypes) {

		this.supertypes = supertypes;
	}

	public String[] getTypes() {

		return types;
	}

	public void setTypes(String[] types) {

		this.types = types;
	}

	public String[] getSubtypes() {

		return subtypes;
	}

	public void setSubtypes(String[] subtypes) {

		this.subtypes = subtypes;
	}

	public String getRarity() {

		return rarity;
	}

	public void setRarity(String rarity) {

		this.rarity = rarity;
	}

	public String getText() {

		return text;
	}

	public void setText(String text) {

		this.text = text;
	}

	public String getFlavor() {

		return flavor;
	}

	public void setFlavor(String flavor) {

		this.flavor = flavor;
	}

	public String getArtist() {

		return artist;
	}

	public void setArtist(String artist) {

		this.artist = artist;
	}

	public String getNumber() {

		return number;
	}

	public void setNumber(String number) {

		this.number = number;
	}

	public String getPower() {

		return power;
	}

	public void setPower(String power) {

		this.power = power;
	}

	public String getToughness() {

		return toughness;
	}

	public void setToughness(String toughness) {

		this.toughness = toughness;
	}

	public int getLoyalty() {

		return loyalty;
	}

	public void setLoyalty(int loyalty) {

		this.loyalty = loyalty;
	}

	public int getMultiverseid() {

		return multiverseid;
	}

	public void setMultiverseid(int multiverseid) {

		this.multiverseid = multiverseid;
	}

	public int[] getVariations() {

		return variations;
	}

	public void setVariations(int[] variations) {

		this.variations = variations;
	}

	public String getImageName() {

		return imageName;
	}

	public void setImageName(String imageName) {

		this.imageName = imageName;
	}

	public String getWatermark() {

		return watermark;
	}

	public void setWatermark(String watermark) {

		this.watermark = watermark;
	}

	public String getBorder() {

		return border;
	}

	public void setBorder(String border) {

		this.border = border;
	}

	public boolean isTimeshifted() {

		return timeshifted;
	}

	public void setTimeshifted(boolean timeshifted) {

		this.timeshifted = timeshifted;
	}

	public int getHand() {

		return hand;
	}

	public void setHand(int hand) {

		this.hand = hand;
	}

	public int getLife() {

		return life;
	}

	public void setLife(int life) {

		this.life = life;
	}

	public boolean isReserved() {

		return reserved;
	}

	public void setReserved(boolean reserved) {

		this.reserved = reserved;
	}

	public String getReleaseDate() {

		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {

		this.releaseDate = releaseDate;
	}

	public boolean isStarter() {

		return starter;
	}

	public void setStarter(boolean starter) {

		this.starter = starter;
	}

	public int getMciNumber() {

		return mciNumber;
	}

	public void setMciNumber(int mciNumber) {

		this.mciNumber = mciNumber;
	}

	public Ruling[] getRulings() {

		return rulings;
	}

	public void setRulings(Ruling[] rulings) {

		this.rulings = rulings;
	}

	public ForeignName[] getForeignNames() {

		return foreignNames;
	}

	public void setForeignNames(ForeignName[] foreignNames) {

		this.foreignNames = foreignNames;
	}

	public String[] getPrintings() {

		return printings;
	}

	public void setPrintings(String[] printings) {

		this.printings = printings;
	}

	public String getOriginalText() {

		return originalText;
	}

	public void setOriginalText(String originalText) {

		this.originalText = originalText;
	}

	public String getOriginalType() {

		return originalType;
	}

	public void setOriginalType(String originalType) {

		this.originalType = originalType;
	}

	public Legality[] getLegalities() {

		return legalities;
	}

	public void setLegalities(Legality[] legalities) {

		this.legalities = legalities;
	}

	public String getSource() {

		return source;
	}

	public void setSource(String source) {

		this.source = source;
	}

	@Override
	public String toString() {

		return "Card [" + name + ", " + Arrays.toString(colorIdentity) + ", " + type + ", " + rarity + ", " + multiverseid + ", "
				+ mciNumber + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + ((border == null) ? 0 : border.hashCode());
		result = prime * result + Float.floatToIntBits(cmc);
		result = prime * result + Arrays.hashCode(colorIdentity);
		result = prime * result + Arrays.hashCode(colors);
		result = prime * result + ((flavor == null) ? 0 : flavor.hashCode());
		result = prime * result + Arrays.hashCode(foreignNames);
		result = prime * result + hand;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((layout == null) ? 0 : layout.hashCode());
		result = prime * result + Arrays.hashCode(legalities);
		result = prime * result + life;
		result = prime * result + loyalty;
		result = prime * result + ((manaCost == null) ? 0 : manaCost.hashCode());
		result = prime * result + mciNumber;
		result = prime * result + multiverseid;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(names);
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((originalText == null) ? 0 : originalText.hashCode());
		result = prime * result + ((originalType == null) ? 0 : originalType.hashCode());
		result = prime * result + ((power == null) ? 0 : power.hashCode());
		result = prime * result + Arrays.hashCode(printings);
		result = prime * result + ((rarity == null) ? 0 : rarity.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result + (reserved ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(rulings);
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + (starter ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(subtypes);
		result = prime * result + Arrays.hashCode(supertypes);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + (timeshifted ? 1231 : 1237);
		result = prime * result + ((toughness == null) ? 0 : toughness.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + Arrays.hashCode(types);
		result = prime * result + Arrays.hashCode(variations);
		result = prime * result + ((watermark == null) ? 0 : watermark.hashCode());
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
		Card other = (Card) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mciNumber != other.mciNumber)
			return false;
		if (multiverseid != other.multiverseid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (artist == null) {
			if (other.artist != null)
				return false;
		} else if (!artist.equals(other.artist))
			return false;
		if (border == null) {
			if (other.border != null)
				return false;
		} else if (!border.equals(other.border))
			return false;
		if (Float.floatToIntBits(cmc) != Float.floatToIntBits(other.cmc))
			return false;
		if (!Arrays.equals(colorIdentity, other.colorIdentity))
			return false;
		if (!Arrays.equals(colors, other.colors))
			return false;
		if (flavor == null) {
			if (other.flavor != null)
				return false;
		} else if (!flavor.equals(other.flavor))
			return false;
		if (!Arrays.equals(foreignNames, other.foreignNames))
			return false;
		if (hand != other.hand)
			return false;
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;
		if (layout == null) {
			if (other.layout != null)
				return false;
		} else if (!layout.equals(other.layout))
			return false;
		if (!Arrays.equals(legalities, other.legalities))
			return false;
		if (life != other.life)
			return false;
		if (loyalty != other.loyalty)
			return false;
		if (manaCost == null) {
			if (other.manaCost != null)
				return false;
		} else if (!manaCost.equals(other.manaCost))
			return false;
		if (!Arrays.equals(names, other.names))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (originalText == null) {
			if (other.originalText != null)
				return false;
		} else if (!originalText.equals(other.originalText))
			return false;
		if (originalType == null) {
			if (other.originalType != null)
				return false;
		} else if (!originalType.equals(other.originalType))
			return false;
		if (power == null) {
			if (other.power != null)
				return false;
		} else if (!power.equals(other.power))
			return false;
		if (!Arrays.equals(printings, other.printings))
			return false;
		if (rarity == null) {
			if (other.rarity != null)
				return false;
		} else if (!rarity.equals(other.rarity))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (reserved != other.reserved)
			return false;
		if (!Arrays.equals(rulings, other.rulings))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (starter != other.starter)
			return false;
		if (!Arrays.equals(subtypes, other.subtypes))
			return false;
		if (!Arrays.equals(supertypes, other.supertypes))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (timeshifted != other.timeshifted)
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
		if (!Arrays.equals(types, other.types))
			return false;
		if (!Arrays.equals(variations, other.variations))
			return false;
		if (watermark == null) {
			if (other.watermark != null)
				return false;
		} else if (!watermark.equals(other.watermark))
			return false;
		return true;
	}

}
