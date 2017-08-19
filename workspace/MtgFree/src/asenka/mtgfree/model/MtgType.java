package asenka.mtgfree.model;

/**
 * A type of magic cards. They are several types of cards. But a type can be
 * decomposed into global type (creature, instant, artefact, etc...). But it can
 * be detailed (Creature : elf, artefact : vehicule, etc...)
 * 
 * @author Asenka
 */
public class MtgType {

	/**
	 * The unique id of a type (based on the ID in the database)
	 */
	private int id;

	/**
	 * This attribute stores the basic type for a card (localized): > Creature >
	 * Enchantment > Instant > Sorcery > Land > Artefact > Creature-Artefact >
	 * Planeswalker > Tribal > ...
	 */
	private String type;

	/**
	 * The detailed type contains exactly what taht's written on the card
	 * (localized): > Creature : human and pilot > Artefact : Vehicule > ...
	 * 
	 * It may be the same as the general type (for the Sorcery and Instant for
	 * example).
	 */
	private String subType;

	/**
	 * This field give detailed about a type (localized)
	 */
	private String description;

	/**
	 * 
	 * @param id
	 * @param type
	 * @param detailedType
	 * @param description
	 */
	public MtgType(int id, String type, String detailedType, String description) {
		super();
		this.id = id;
		this.type = type;
		this.subType = detailedType;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param type
	 *            the type to set
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
	 * @param subType
	 *            the detailedType to set
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + id + ", " + type + ", " + subType + ", " + description + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((subType == null) ? 0 : subType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	
	

}