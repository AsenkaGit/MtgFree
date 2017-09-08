package asenka.mtgfree.model;

/**
 * This class represents a card ability (fly, deathtouch, hexproof, etc...)
 * 
 * 
 * @author Asenka
 */
public class MtgAbility implements Comparable<MtgAbility> {

	/**
	 * 
	 */
	private int id;

	/**
	 * The ability name (localized).
	 */
	private String name;

	/**
	 * A description of the ability (localized)
	 */
	private String description;

	/**
	 * 
	 */
	private String language;

	/**
	 * 
	 * @param id
	 * @param name
	 * @param language
	 */
	public MtgAbility(int id, String name, String language) {
		this(id, name, "", language);

	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 */
	public MtgAbility(int id, String name, String description, String language) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.language = language;
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

	/**
	 * @return the language
	 */
	public String getLanguage() {

		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {

		this.language = language;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "[" + id + ", " + name + ", " + description + ", " + language + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		MtgAbility other = (MtgAbility) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(MtgAbility o) {

		return this.name.compareTo(o.name);
	}
}
