package asenka.mtgfree.model;

/**
 * This class represent a magic format (e.g. Standard, Modern, Legacy, etc...).
 * 
 * @author Asenka
 */
public class MtgFormat implements Comparable<MtgFormat> {

	// This class could have been a enumeration. But I want to anticipate the creation
	// of another format and I want this data stored in the database.

	/**
	 * 
	 */
	private int id;

	/**
	 * The name of the format (localized).
	 */
	private String name;

	/**
	 * A description about the format (localized)
	 */
	private String description;

	/**
	 * The language used to initialize this object's values.
	 */
	private String language;

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public MtgFormat(int id, String name, String language) {
		this(id, name, "", language);
	}

	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public MtgFormat(int id, String name, String description, String language) {
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

	/**
	 * @return the language used to initialize this format values
	 */
	public String getLanguage() {

		return language;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {

		return "[" + id + ", " + name + ", " + description + "]";
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
		MtgFormat other = (MtgFormat) obj;
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
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(MtgFormat o) {

		return this.name.compareTo(o.name);
	}
}
