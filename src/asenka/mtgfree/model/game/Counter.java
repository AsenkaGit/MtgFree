package asenka.mtgfree.model.game;

import java.awt.Color;

/**
 * 
 * @author asenka
 *
 */
public class Counter {

	/**
	 * The value display by the counter (it could be empty)
	 */
	private String value;

	/**
	 * The color of the counter
	 */
	private Color color;

	public Counter(String value, Color color) {
		super();
		this.value = value;
		this.color = color;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public Color getColor() {

		return color;
	}

	public void setColor(Color color) {

		this.color = color;
	}

	@Override
	public String toString() {

		return "MtgCounter [" + value + ", " + color + "]";
	}
}
