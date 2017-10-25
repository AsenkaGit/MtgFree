package asenka.mtgfree.model.data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>This class represents a set of Mtg cards</p>
 * 
 * <p>
 * This implementation is fully based on the specifications described in the <a href="https://mtgjson.com/documentation.html">MTG
 * Json documentation</a>
 * </p>
 * 
 * @author asenka
 * @see MtgCard
 */
public class MtgSet implements Serializable {

	/**
	 * The generated ID used for serialization
	 */
	private static final long serialVersionUID = 6709762497313663339L;

	/**
	 * The name of the set
	 */
	private String name;

	/**
	 * The set's abbreviated code
	 */
	private String code;

	/**
	 * The code that Gatherer uses for the set. Only present if different than 'code'
	 */
	private String gathererCode;

	/**
	 * An old style code used by some Magic software. Only present if different than 'gathererCode' and 'code'
	 */
	private String oldCode;

	/**
	 * The code that magiccards.info uses for the set. Only present if magiccards.info has this set
	 */
	private String magicCardsInfoCode;

	/**
	 * When the set was released (YYYY-MM-DD). For promo sets, the date the first card was released.
	 */
	private String releaseDate;

	/**
	 * The type of border on the cards, either "white", "black" or "silver"
	 */
	private String border;

	/**
	 * Type of set. One of: "core", "expansion", "reprint", "box", "un", "from the vault", "premium deck", "duel deck", "starter",
	 * "commander", "planechase", "archenemy","promo", "vanguard", "masters", "conspiracy", "masterpiece"
	 */
	private String type;

	/**
	 * The block this set is in
	 */
	private String block;

	/**
	 * Present and set to true if the set was only released online
	 */
	private boolean onlineOnly;

//	/**
//	 * The 'booster' key is present for each set that has physical boosters (so not present for box sets, duel decks, digital
//	 * masters editions, etc.). It is an array containing one item per card in the booster. Thus the array length is how many
//	 * cards are in a booster. Each item in the array is either a string representing the type of booster card or an array of
//	 * strings representing possible types for that booster card.
//	 */
//	private String[] booster;

	/**
	 * The cards in the set
	 */
	private MtgCard[] cards;

	/**
	 * Constructor with all values 
	 * @param name
	 * @param code
	 * @param gathererCode
	 * @param oldCode
	 * @param magicCardsInfoCode
	 * @param releaseDate
	 * @param border
	 * @param type
	 * @param block
	 * @param onlineOnly
	 * @param booster
	 * @param cards
	 */
	public MtgSet(String name, String code, String gathererCode, String oldCode, String magicCardsInfoCode, String releaseDate, String border,
			String type, String block, boolean onlineOnly/*, String[] booster*/, MtgCard[] cards) {
		super();
		this.name = name;
		this.code = code;
		this.gathererCode = gathererCode;
		this.oldCode = oldCode;
		this.magicCardsInfoCode = magicCardsInfoCode;
		this.releaseDate = releaseDate;
		this.border = border;
		this.type = type;
		this.block = block;
		this.onlineOnly = onlineOnly;
//		this.booster = booster;
		this.cards = cards;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	public String getGathererCode() {

		return gathererCode;
	}

	public void setGathererCode(String gathererCode) {

		this.gathererCode = gathererCode;
	}

	public String getOldCode() {

		return oldCode;
	}

	public void setOldCode(String oldCode) {

		this.oldCode = oldCode;
	}

	public String getMagicCardsInfoCode() {

		return magicCardsInfoCode;
	}

	public void setMagicCardsInfoCode(String magicCardsInfoCode) {

		this.magicCardsInfoCode = magicCardsInfoCode;
	}

	public String getReleaseDate() {

		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {

		this.releaseDate = releaseDate;
	}

	public String getBorder() {

		return border;
	}

	public void setBorder(String border) {

		this.border = border;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getBlock() {

		return block;
	}

	public void setBlock(String block) {

		this.block = block;
	}

	public boolean isOnlineOnly() {

		return onlineOnly;
	}

	public void setOnlineOnly(boolean onlineOnly) {

		this.onlineOnly = onlineOnly;
	}

//	public String[] getBooster() {
//
//		return booster;
//	}
//
//	public void setBooster(String[] booster) {
//
//		this.booster = booster;
//	}

	public MtgCard[] getCards() {

		return cards;
	}

	public void setCards(MtgCard[] cards) {

		this.cards = cards;
	}

	@Override
	public String toString() {

		return "MtgSet [" + name + ", " + code + ", " + releaseDate + ", " + type + ", " + block + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
//		result = prime * result + Arrays.hashCode(booster);
		result = prime * result + ((border == null) ? 0 : border.hashCode());
		result = prime * result + Arrays.hashCode(cards);
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((gathererCode == null) ? 0 : gathererCode.hashCode());
		result = prime * result + ((magicCardsInfoCode == null) ? 0 : magicCardsInfoCode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((oldCode == null) ? 0 : oldCode.hashCode());
		result = prime * result + (onlineOnly ? 1231 : 1237);
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
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
		MtgSet other = (MtgSet) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
//		if (!Arrays.equals(booster, other.booster))
//			return false;
		if (border == null) {
			if (other.border != null)
				return false;
		} else if (!border.equals(other.border))
			return false;
		if (!Arrays.equals(cards, other.cards))
			return false;
		if (gathererCode == null) {
			if (other.gathererCode != null)
				return false;
		} else if (!gathererCode.equals(other.gathererCode))
			return false;
		if (magicCardsInfoCode == null) {
			if (other.magicCardsInfoCode != null)
				return false;
		} else if (!magicCardsInfoCode.equals(other.magicCardsInfoCode))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (oldCode == null) {
			if (other.oldCode != null)
				return false;
		} else if (!oldCode.equals(other.oldCode))
			return false;
		if (onlineOnly != other.onlineOnly)
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
