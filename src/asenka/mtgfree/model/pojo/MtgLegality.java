package asenka.mtgfree.model.pojo;

import java.io.Serializable;

/**
 * <p>
 * Associate a legality to a format. It is used by a card in a list or an array to say if that card it legal, restricted or banned
 * in a specific format.
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
public class MtgLegality implements Serializable {

	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = 2214702778952665931L;

	/**
	 * The name of the format : <code>"Legacy", "Vintage", "Modern"</code>, etc.
	 */
	private String format;

	/**
	 * The possible values are <code>"Restricted", "Banned" or "Legal"</code>
	 */
	private String legality;

	/**
	 * @param format
	 * @param legality
	 */
	public MtgLegality(String format, String legality) {
		super();
		this.format = format;
		this.legality = legality;
	}

	/**
	 * @return
	 */
	public String getFormat() {

		return format;
	}

	/**
	 * @param format
	 */
	public void setFormat(String format) {

		this.format = format;
	}

	/**
	 * @return
	 */
	public String getLegality() {

		return legality;
	}

	/**
	 * @param legality
	 */
	public void setLegality(String legality) {

		this.legality = legality;
	}

	@Override
	public String toString() {

		return "MtgLegality [" + format + ", " + legality + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((legality == null) ? 0 : legality.hashCode());
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
		MtgLegality other = (MtgLegality) obj;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (legality == null) {
			if (other.legality != null)
				return false;
		} else if (!legality.equals(other.legality))
			return false;
		return true;
	}
}
