package asenka.mtgfree.model.data;

import java.io.Serializable;

/**
 * <p>
 * Stores the names translations and the multiverse id of the translated card
 * </p>
 * 
 * <p>
 * This implementation is fully based on the specifications described in the <a href="https://mtgjson.com/documentation.html">MTG
 * Json documentation</a>
 * </p>
 * 
 * @author asenka
 * @see MtgCard
 */
public class MtgForeignName implements Serializable {

	/**
	 * The generated ID for the serialization
	 */
	private static final long serialVersionUID = 1919032153475142659L;

	/**
	 * The translation language
	 */
	private String language;

	/**
	 * The card's name in the instance language
	 */
	private String name;

	/**
	 * The multiverse id of the translated card. Useful to get the translated image with the following URL:<br />
	 * <code>http://gatherer.wizards.com/Handlers/ImagesManager.ashx?multiverseid=[MULTIVERSEID]&type=card</code>
	 */
	private int multiverseid;

	/**
	 * @param language
	 * @param name
	 * @param multiverseid
	 */
	public MtgForeignName(String language, String name, int multiverseid) {
		super();
		this.language = language;
		this.name = name;
		this.multiverseid = multiverseid;
	}

	/**
	 * @return
	 */
	public String getLanguage() {

		return language;
	}

	/**
	 * @param language
	 */
	public void setLanguage(String language) {

		this.language = language;
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
	public int getMultiverseid() {

		return multiverseid;
	}

	/**
	 * @param multiverseid
	 */
	public void setMultiverseid(int multiverseid) {

		this.multiverseid = multiverseid;
	}

	@Override
	public String toString() {

		return "MtgForeignName [" + language + ", " + name + ", " + multiverseid + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + multiverseid;
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
		MtgForeignName other = (MtgForeignName) obj;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (multiverseid != other.multiverseid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
