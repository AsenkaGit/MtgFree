package asenka.mtgfree.model.mtg;

import java.io.Serializable;
import java.text.Collator;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.utilities.Localized;

/**
 * Represents a collection of Mtg cards (like Amonket, Tempest, Alpha, etc...)
 * 
 * @author asenka
 */
public class MtgCollection implements Comparable<MtgCollection>, Localized, Serializable {

	/**
	 * Generated ID for serialization
	 */
	private static final long serialVersionUID = 5002172898577941900L;

	/**
	 * The average number of cards in a MTG collection. Used to initiate the set of cards collection
	 */
	private static final int INITIAL_COLLECTION_SIZE = 250;

	/**
	 * The code of the collection
	 */
	private String code;

	/**
	 * The collection name;
	 */
	private String name;

	/**
	 * The type of collection (extension, commander, reprint, starter, etc...). <br />
	 * This value is not mandatory to have the application working, but it can not be null (used empty string instead)
	 */
	private String type;

	/**
	 * The description of the collection. <br />
	 * This value is not mandatory to have the application working, but it can not be null (used empty string instead)
	 */
	private String description;

	/**
	 * Collection's release year (no need to be more specific)
	 */
	private Date releaseDate;

	/**
	 * The cards in this collection
	 */
	private Set<MtgCard> cards;

	/**
	 * The language used to display the collection data
	 */
	private Locale locale;

	/**
	 * The collator use to sort the collection based on the collection NAME
	 */
	private final transient Collator collator; // The collator does not need to be serialized

	/**
	 * Constructor to create exact copy of collection
	 * 
	 * @param copyFrom the collection to copy
	 */
	MtgCollection(MtgCollection copyFrom) {

		this.code = copyFrom.code;
		this.name = new String(copyFrom.name);
		this.description = new String(copyFrom.description);
		this.releaseDate = copyFrom.releaseDate;
		this.cards = new HashSet<MtgCard>(copyFrom.cards);
		this.type = copyFrom.type;
		this.locale = copyFrom.locale;
		this.collator = copyFrom.collator;
	}

	/**
	 * Simpler constructor (for the release date, it uses the current date)
	 * 
	 * @param code
	 * @param name
	 * @param locale
	 */
	public MtgCollection(String code, String name, Locale locale) {

		this(code, name, "", "", new Date(), locale);
	}

	/**
	 * Full constructor
	 * 
	 * @param code the unique code of the collection
	 * @param name the localized name of the collection
	 * @param type the localized type of the collection
	 * @param description the localized description of the collection (if no, use empty string)
	 * @param releaseDate the released date (if unknow, used a random date)
	 * @param locale the locale used to display the collection data
	 */
	public MtgCollection(String code, String name, String type, String description, Date releaseDate, Locale locale) {

		super();
		this.code = code;
		this.name = name;
		this.type = type;
		this.description = description;
		this.releaseDate = releaseDate;
		this.locale = locale;
		this.collator = Collator.getInstance(this.locale);
		this.cards = new HashSet<MtgCard>(INITIAL_COLLECTION_SIZE);
	}

	/**
	 * e.g. for Ixalan the code is "XLN". This value does not depends on the locale
	 * 
	 * @return the collection code
	 */
	public String getCode() {

		return code;
	}

	/**
	 * Set the code of the collection
	 * 
	 * @param code the collection code (unique value)
	 */
	public void setCode(String code) {

		this.code = code;
	}

	/**
	 * @return the full localized name of the collection
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name the localized name of the collection
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {

		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {

		this.type = type;
	}

	/**
	 * @return a description of the collection (localized)
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * Set the description of the collection (localized)
	 * 
	 * @param description the text describing the collection (used an empty string instead of null if you don't want any
	 *        description)
	 */
	public void setDescription(String description) {

		this.description = description;
	}

	/**
	 * @return the release date of the collection
	 */
	public Date getReleaseDate() {

		return releaseDate;
	}

	/**
	 * Set the release date of the collection
	 * 
	 * @param releaseDate
	 */
	public void setReleaseDate(Date releaseDate) {

		this.releaseDate = releaseDate;
	}

	/**
	 * Returns an unmodifiable set of cards
	 * 
	 * @return the cards in the collection
	 * @see Collections#unmodifiableSet(Set)
	 */
	public Set<MtgCard> getCards() {

		return Collections.unmodifiableSet(cards);
	}

	/**
	 * Set the cards in the collection by creating a new instance of HashSet that is a copy of the parameter cards set.
	 * 
	 * @param cards the cards that should be in the collection
	 */
	public void setCards(Set<MtgCard> cards) {

		this.cards = new HashSet<MtgCard>(cards);
	}

	/**
	 * Add card(s) to the set of cards in the collection. It also update the card collection name attribute.
	 * 
	 * @param cards the card(s) to add (an array)
	 */
	public void addCards(MtgCard... cards) {

		for (MtgCard card : cards) {
			this.cards.add(card);
			card.setCollectionCode(this.code);
		}
	}

	/**
	 * Remove card(s) from the set of cards in the collection
	 * 
	 * @param cards the card(s) to remove
	 */
	public void removeCards(MtgCard... cards) {

		for (MtgCard card : cards) {
			this.cards.remove(card);
		}
	}

	/**
	 * @param card
	 * @return <code>true</code> if the card belongs to the collection
	 */
	public boolean contains(MtgCard card) {

		return this.cards.contains(card);
	}

	@Override
	public Locale getLocale() {

		return this.locale;
	}

	@Override
	public String toString() {

		return "[" + code + ", " + name + ", " + type + ", " + releaseDate + ", " + cards.size() + " cards inside]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((collator == null) ? 0 : collator.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MtgCollection other = (MtgCollection) obj;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (collator == null) {
			if (other.collator != null)
				return false;
		} else if (!collator.equals(other.collator))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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

	@Override
	public int compareTo(MtgCollection collection) {

		// Uses a localized collator to compare the collection names.
		return this.collator.compare(this.name, collection.name);
	}
}
