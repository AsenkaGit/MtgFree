package asenka.mtgfree.model.game;

import java.awt.Color;
import java.io.Serializable;

/**
 * 
 * @author asenka
 *
 */
public class Counter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6713400592234603589L;

	/**
	 * The value display by the counter (it could be empty)
	 */
	private final String value;

	/**
	 * The color of the counter
	 */
	private final Color color;

	/**
	 * 
	 * @param value
	 * @param color
	 */
	public Counter(String value, Color color) {
		super();
		this.value = value;
		this.color = color;
	}

	/**
	 * @return
	 */
	public String getValue() {

		return value;
	}

	/**
	 * @return
	 */
	public Color getColor() {

		return color;
	}

	@Override
	public String toString() {

		return "MtgCounter [" + value + ", " + color + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Counter other = (Counter) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
