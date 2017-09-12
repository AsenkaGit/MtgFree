package asenka.mtgfree.model.mtg;

import java.text.Collator;
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
public class MtgCollection implements Comparable<MtgCollection>, Localized {

	/**
	 * The average number of cards in a MTG collection. Used to initiate the set of cards collection
	 */
	private static final int INITIAL_COLLECTION_SIZE = 250;

	/**
	 * The id of the collection (from the database primary key)
	 */
	private int id;

	/**
	 * The collection name;
	 */
	private String name;

	/**
	 * The description of the collection
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
	 * The collator use to compare the collection names
	 */
	private Collator collator;

	/**
	 * Constructor to create exact copy of collection
	 * @param copyFrom
	 */
	MtgCollection(MtgCollection copyFrom) {

		this.id = copyFrom.id;
		this.name = new String(copyFrom.name);
		this.description = new String(copyFrom.description);
		this.releaseDate = copyFrom.releaseDate;
		this.cards = new HashSet<MtgCard>(copyFrom.cards);
		this.locale = copyFrom.locale;
		this.collator = copyFrom.collator;
	}

	/**
	 * Simpler constructor
	 * @param id
	 * @param name
	 */
	public MtgCollection(int id, String name, Locale locale) {

		this(id, name, "", new Date(), locale);
	}

	/**
	 * Full constructor
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param releaseDate
	 * @param locale
	 */
	public MtgCollection(int id, String name, String description, Date releaseDate, Locale locale) {

		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.releaseDate = releaseDate;
		this.locale = locale;
		this.collator = Collator.getInstance(this.locale);
		this.cards = new HashSet<MtgCard>(INITIAL_COLLECTION_SIZE);
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
	public String getDescription() {

		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {

		this.description = description;
	}

	/**
	 * @return
	 */
	public Date getReleaseDate() {

		return releaseDate;
	}

	/**
	 * @param releaseDate
	 */
	public void setReleaseDate(Date releaseDate) {

		this.releaseDate = releaseDate;
	}

	/**
	 * @return
	 */
	public Set<MtgCard> getCards() {

		return cards;
	}

	/**
	 * @param cards
	 */
	public void setCards(Set<MtgCard> cards) {

		this.cards = cards;
	}

	/**
	 * Add card(s) to the set of cards in the collection
	 * 
	 * @param cards the card(s) to add
	 */
	public void addCards(MtgCard... cards) {

		for (MtgCard card : cards) {
			this.cards.add(card);
		}
	}

	/**
	 * Remove card(s) from the set of cards in the collection
	 * 
	 * @param cards the card(s) to remove
	 * @return <code>false</code> if at least one card was not in the set
	 */
	public boolean removeCards(MtgCard... cards) {

		boolean result = true;

		for (MtgCard card : cards) {
			result &= this.cards.remove(card);
		}
		return result;
	}

	@Override
	public Locale getLocale() {

		return this.locale;
	}

	@Override
	public String toString() {

		return "[" + id + ", " + name + ", " + description + ", " + releaseDate + ", " + cards.size() + " cards]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
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
		return true;
	}

	@Override
	public int compareTo(MtgCollection collection) {

		return this.collator.compare(this.name, collection.name);
	}
}
