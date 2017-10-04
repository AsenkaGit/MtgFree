package asenka.mtgfree.model.mtg.mtgcard;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

import asenka.mtgfree.model.utilities.Localized;

/**
 * This class represent a magic format (e.g. Standard, Modern, Legacy, etc...).
 * 
 * @author Asenka
 */
public class MtgFormat implements Comparable<MtgFormat>, Localized, Serializable {

	// This class could have been a enumeration. But I want to anticipate the creation
	// of another format and I want this data stored in the database.

	/**
	 * 
	 */
	private static final long serialVersionUID = 7823825497400458563L;

	/**
	 * The format id (same as the primary key in the database)
	 */
	private int id;

	/**
	 * The name of the format
	 */
	private String name;

	/**
	 * A description about the format
	 */
	private String description;

	/**
	 * The language used to initialize this object's values.
	 */
	private Locale locale;

	/**
	 * The collator used to compare the format's name
	 */
	private transient Collator collator;

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public MtgFormat(int id, String name, Locale locale) {

		this(id, name, "", locale);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param name
	 * @param description
	 */
	public MtgFormat(int id, String name, String description, Locale locale) {

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
	 * @param id the id of the format (it should reflect the database id)
	 */
	public void setId(int id) {

		this.id = id;
	}

	/**
	 * @return the name of the format
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
	 * @return the description the format description
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

	/**
	 * 
	 */
	@Override
	public String toString() {

		return "[" + id + ", " + name + ", " + description + "]";
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
		MtgFormat other = (MtgFormat) obj;
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
	public int compareTo(MtgFormat format) {

		if (this.collator == null) {
			this.collator = Collator.getInstance(this.locale);
		}
		return this.collator.compare(this.name, format.name);
	}
}
