package asenka.mtgfree.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 * A counter is an elements used to mark a card during a game. 
 * 
 * @author asenka
 * @see Card
 * @see Serializable
 */
public class Counter implements Serializable {

	/**
	 * The serialized ID of the class. It must be updated each time the class is updated.
	 */
	private static final long serialVersionUID = 4252899014767527491L;

	/**
	 * A text to display (or not) giving a meaning to this counter (not mandatory)
	 */
	private StringProperty value;

	/**
	 * The color of the counter (mandatory)
	 */
	private ObjectProperty<Color> color;

	/**
	 * Build a new counter
	 * @param value {@link Counter#value}
	 * @param color {@link Counter#color}
	 */
	public Counter(String value, final Color color) {

		this.value = new SimpleStringProperty(this, "value", value);
		this.color = new SimpleObjectProperty<Color>(this, "color", color);
	}

	public final StringProperty valueProperty() {

		return this.value;
	}

	public final String getValue() {

		return this.valueProperty().get();
	}

	public final void setValue(final String value) {

		this.valueProperty().set(value);
	}

	public final ObjectProperty<Color> colorProperty() {

		return this.color;
	}

	public final Color getColor() {

		return this.colorProperty().get();
	}

	public final void setColor(final Color color) {

		this.colorProperty().set(color);
	}

	/**
	 * Method called to serializes the Counter object.
	 * @param out the output stream used to serialize the counter
	 * @throws IOException 
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {

		out.writeUTF(this.value.get());
		out.writeDouble(this.color.get().getRed());
		out.writeDouble(this.color.get().getGreen());
		out.writeDouble(this.color.get().getBlue());
		out.writeDouble(this.color.get().getOpacity());
	}

	/**
	 * Method called to create a Counter from serialized data.
	 * @param in the input stream used to read the serialized data
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		
		this.value = new SimpleStringProperty(this, "value", in.readUTF());
		final double red = in.readDouble();
		final double green = in.readDouble();
		final double blue = in.readDouble();
		final double opacity = in.readDouble();
		this.color = new SimpleObjectProperty<Color>(this, "color", new Color(red, green, blue, opacity));
	}
	
	@Override
	public String toString() {
		return "Counter [" + this.getValue() + ", " + this.getColor() + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((color.get() == null) ? 0 : color.get().hashCode());
		result = prime * result + ((value.get() == null) ? 0 : value.get().hashCode());
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
		if (this.value.isNotEqualTo(other.value).get()) 
			return false;
		if (this.color.isNotEqualTo(other.color).get())
			return false;
		return true;
	}
}
