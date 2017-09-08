package asenka.mtgfree.model.mtgcard;

import java.text.Collator;
import java.util.Locale;

import asenka.mtgfree.model.utilities.Localized;

/**
 * A type of magic cards. They are several types of cards. But a type can be
 * decomposed into global type (creature, instant, artefact, etc...). But it can
 * be detailed (Creature : elf, artefact : vehicule, etc...)
 * 
 * @author Asenka
 */
public class MtgType implements Comparable<MtgType>, Localized {

	/**
	 * The type id (from the database primary key)
	 */
	private int id;

	/**
	 * Basic type of a card (Creature, Instant, Sort, Artefact, etc.)
	 */
	private String mainType;

	/**
	 * The card sub type (Creature : dwarf, Enchant : aura and curse, Artefact : Vehicle, etc.)
	 */
	private String subType;

	/**
	 * Description of the type
	 */
	private String description;

	/**
	 * The language used to display the type data
	 */
	private Locale locale;
	
	/**
	 * the collator used to compare the type's names
	 */
	private transient Collator collator;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param type
	 * @param locale
	 */
	public MtgType(int id, String type, Locale locale) {
		
		this(id, type, type, "", locale);
	}
	
	/**
	 * Constructor
	 * 
	 * @param id 
	 * @param mainType
	 * @param subType
	 * @param description
	 * @param locale
	 */
	public MtgType(int id, String mainType, String subType, String description, Locale locale) {

		super();
		this.id = id;
		this.mainType = mainType;
		this.subType = subType;
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
	public String getMainType() {

		return mainType;
	}

	/**
	 * @param mainType the type to set
	 */
	public void setMainType(String mainType) {

		this.mainType = mainType;
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

	@Override
	public Locale getLocale() {

		return this.locale;
	}

	@Override
	public String toString() {

		return "[" + id + ", " + mainType + ", " + subType + ", " + description + ", " + locale + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((subType == null) ? 0 : subType.hashCode());
		result = prime * result + ((mainType == null) ? 0 : mainType.hashCode());
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
		if (mainType == null) {
			if (other.mainType != null)
				return false;
		} else if (!mainType.equals(other.mainType))
			return false;
		return true;
	}

	@Override
	public int compareTo(MtgType type) {

		if(this.collator == null) {
			this.collator = Collator.getInstance(this.locale);
		}
		int result = this.collator.compare(this.mainType, type.mainType);

		if (result == 0) {
			result = this.collator.compare(this.subType, type.subType);

			if (result == 0) {
				result = this.id - type.id;
			}
		}
		return result;
	}
}
