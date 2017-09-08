package asenka.mtgfree.model;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import asenka.mtgfree.model.utilities.Localized;

/**
 * A type of magic cards. They are several types of cards. But a type can be
 * decomposed into global type (creature, instant, artefact, etc...). But it can
 * be detailed (Creature : elf, artefact : vehicule, etc...)
 * 
 * @author Asenka
 */
public class MtgType implements Comparable<MtgType>, Comparator<MtgType>, Localized {

	/**
	 * The unique id of a type (based on the ID in the database)
	 */
	private int id;

	/**
	 * This attribute stores the basic type for a card
	 */
	private String type;

	/**
	 * The detailed type contains exactly what taht's written on the card
	 */
	private String subType;

	/**
	 * This field give detailed about a type (localized)
	 */
	private String description;

	/**
	 * 
	 */
	private Locale locale;

	/**
	 * 
	 * @param id
	 * @param type
	 * @param detailedType
	 * @param description
	 * @param locale
	 */
	public MtgType(int id, String type, String detailedType, String description, Locale locale) {

		super();
		this.id = id;
		this.type = type;
		this.subType = detailedType;
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
	 * @return the detailedType
	 */
	public String getSubType() {

		return subType;
	}

	/**
	 * @param subType the detailedType to set
	 */
	public void setSubType(String subType) {

		this.subType = subType;
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

	/**
	 * @return
	 */
	@Override
	public Locale getLocale() {

		return locale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "[" + id + ", " + type + ", " + subType + ", " + description + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((subType == null) ? 0 : subType.hashCode());
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
		MtgType other = (MtgType) obj;
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
		if (subType == null) {
			if (other.subType != null)
				return false;
		} else if (!subType.equals(other.subType))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(MtgType o) {

		return this.compare(this, o);
	}

	/**
	 * 
	 */
	@Override
	public int compare(MtgType type1, MtgType type2) {

		Collator collator = Collator.getInstance(locale);
		int result = collator.compare(type1.type, type2.type);

		if (result == 0) {
			result = collator.compare(type1.subType, type2.subType);

			if (result == 0) {
				result = type1.id - type2.id;
			}
		}
		return result;
	}
}
