package asenka.mtgfree.model.mtgcard;

import java.text.Collator;
import java.util.Locale;

import asenka.mtgfree.model.utilities.Localized;

/**
 * This class represents a card ability (fly, deathtouch, hexproof, etc...)
 * 
 * @author asenka
 */
public class MtgAbility implements Comparable<MtgAbility>, Localized {

	/**
	 * The ability id (from the database primary key)
	 */
	private int id;

	/**
	 * The ability name
	 */
	private String name;

	/**
	 * A description of the ability
	 */
	private String description;

	/**
	 * The language used to display the ability data
	 */
	private Locale locale;

	/**
	 * Collator used to compare an ability's name with another
	 */
	private transient Collator collator;

	/**
	 * Constructor
	 * 
	 * @param id the ability id (> 0)
	 * @param name the ability name (not null)
	 * @param locale the language used (not null)
	 * @see Locale
	 */
	public MtgAbility(int id, String name, Locale locale) {

		this(id, name, "", locale);
	}

	/**
	 * Constructor
	 * 
	 * @param id the ability id (> 0)
	 * @param name the ability name (not null)
	 * @param description the ability description (not null)
	 * @param locale the language used (not null)
	 * @see Locale
	 */
	public MtgAbility(int id, String name, String description, Locale locale) {

		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.locale = locale;
	}

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
	 * @return the description
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {

		this.description = description;
	}

	@Override
	public Locale getLocale() {

		return this.locale;
	}

	@Override
	public String toString() {

		return "[" + id + ", " + name + ", " + description + ", " + locale + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MtgAbility other = (MtgAbility) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
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
		return true;
	}

	@Override
	public int compareTo(MtgAbility o) {
		
		if(this.collator == null) {
			this.collator = Collator.getInstance(this.locale);
		}
		return this.collator.compare(this.name, o.name);
	}
}
