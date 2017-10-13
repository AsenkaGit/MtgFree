package asenka.mtgfree.model.pojo;

import java.io.Serializable;

/**
 * <p>
 * Store some details about how to play the card. Each ruling contains a text and a date
 * </p>
 * 
 * <p>
 * This implementation is fully based on the specifications described in the <a href="https://mtgjson.com/documentation.html">MTG
 * Json documentation</a>
 * </p>
 * 
 * @author asenka
 * @see Card
 */
public class Ruling implements Serializable {

	/**
	 * The generated ID for the serialization
	 */
	private static final long serialVersionUID = -5483652095898702285L;

	/**
	 * The date when the rule has been added. Format: <code>"YYYY-mm-dd"</code>
	 */
	private String date;

	/**
	 * The text with the ruling data
	 */
	private String text;

	/**
	 * @param date
	 * @param text
	 */
	public Ruling(String date, String text) {
		super();
		this.date = date;
		this.text = text;
	}

	/**
	 * @return
	 */
	public String getDate() {

		return date;
	}

	/**
	 * @param date
	 */
	public void setDate(String date) {

		this.date = date;
	}

	/**
	 * @return
	 */
	public String getText() {

		return text;
	}

	/**
	 * @param text
	 */
	public void setText(String text) {

		this.text = text;
	}

	@Override
	public String toString() {

		return "Ruling [" + date + ", " + text + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Ruling other = (Ruling) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
