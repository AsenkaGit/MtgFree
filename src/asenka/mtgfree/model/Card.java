package asenka.mtgfree.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.utilities.MtgDataUtility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

/**
 * This class represents a card played during a game.
 * 
 * @author asenka
 * @see Serializable
 */
public class Card implements Serializable {
	
	/**
	 * The serialized ID of the class. It must be updated each time the class is updated.
	 */
	private static final long serialVersionUID = -3454805330637228059L;

	/**
	 * The ID of the card. During a game, each card must have a unique ID. If a card is used several times by one or several players
	 * each instance of this card have a single battle ID. The ID is normally based on the player ID.
	 * @see Player#idProperty() 
	 */
	private IntegerProperty battleId;

	/**
	 * The boolean property indicating whether or not the card is tapped.
	 */
	private BooleanProperty tapped;

	/**
	 * The boolean property indicating whether or not the card is visible. 
	 */
	private BooleanProperty visible;

	/**
	 * The boolean property indicating whether or not the card is selected.
	 */
	private BooleanProperty selected;

	/**
	 * The primary card data. It is basically the main information about the card displayed (name, text, mana cost, colors, etc...). Each Card must 
	 * have a primary card data.
	 * @see MtgCard
	 */
	private ReadOnlyObjectWrapper<MtgCard> primaryCardData;

	/**
	 * The secondary card data. Most of the cards do not need this data and this field will be <code>null</code> most of the time. It can be used for
	 * special layouts cards such as "double-faced" cards.
	 * @see MtgCard
	 */
	private ReadOnlyObjectWrapper<MtgCard> secondaryCardData;

	/**
	 * The location of the card. This value is used on the battlefield to move the card.
	 * @see Point2D
	 */
	private ObjectProperty<Point2D> location;

	/**
	 * The list of counters on the card.
	 * @see Counter
	 */
	private ReadOnlyListWrapper<Counter> counters;

	/**
	 * Build a new card.
	 * 
	 * @param battleId {@link Card#battleId}
	 * @param primaryCardData {@link Card#primaryCardData}
	 */
	public Card(final int battleId, final MtgCard primaryCardData) {

		this.battleId = new SimpleIntegerProperty(this, "battleId", battleId);
		this.tapped = new SimpleBooleanProperty(this, "tapped", false);
		this.visible = new SimpleBooleanProperty(this, "visible", false);
		this.selected = new SimpleBooleanProperty(this, "selected", false);
		this.primaryCardData = new ReadOnlyObjectWrapper<MtgCard>(this, "primaryCardData", primaryCardData);
		this.location = new SimpleObjectProperty<Point2D>(this, "location", new Point2D(0d, 0d));
		this.counters = new ReadOnlyListWrapper<Counter>(this, "counters", FXCollections.<Counter> observableArrayList());

		// Initialize the secondary card data if necessary (most of the time this value will be null)
		MtgCard secondaryCardData = null;
		final String[] names = primaryCardData.getNames();

		if (names != null && names.length > 1) {
			secondaryCardData = MtgDataUtility.getInstance().getMtgCard(names[1]);
		}
		this.secondaryCardData = new ReadOnlyObjectWrapper<MtgCard>(this, "secondaryCardData", secondaryCardData);
	}

	public final IntegerProperty battleIdProperty() {

		return this.battleId;
	}

	public final int getBattleId() {

		return this.battleId.get();
	}
	
	public final void setBattleId(final int battleId) {
		
		this.battleId.set(battleId);
	}

	public final BooleanProperty tappedProperty() {

		return this.tapped;
	}

	public final boolean isTapped() {

		return this.tapped.get();
	}

	public final void setTapped(final boolean tapped) {

		this.tapped.set(tapped);
	}

	public final BooleanProperty visibleProperty() {

		return this.visible;
	}

	public final boolean isVisible() {

		return this.visible.get();
	}

	public final void setVisible(final boolean visible) {

		this.visible.set(visible);
	}

	public final BooleanProperty selectedProperty() {

		return this.selected;
	}

	public final boolean isSelected() {

		return this.selected.get();
	}

	public final void setSelected(final boolean selected) {

		this.selected.set(selected);
	}

	public final ReadOnlyObjectProperty<MtgCard> primaryCardDataProperty() {

		return this.primaryCardData.getReadOnlyProperty();
	}

	public final MtgCard getPrimaryCardData() {

		return this.primaryCardData.get();
	}

	public final ReadOnlyObjectProperty<MtgCard> secondaryCardDataProperty() {

		return this.secondaryCardData.getReadOnlyProperty();
	}

	public final MtgCard getSecondaryCardData() {

		return this.secondaryCardData.get();
	}

	public final ObjectProperty<Point2D> locationProperty() {

		return this.location;
	}

	public final Point2D getLocation() {

		return this.location.get();
	}

	public final void setLocation(final Point2D location) {

		this.location.set(location);
	}
	
	public final void setLocation(double x, double y) {
		
		setLocation(new Point2D(x, y));
	}

	public final ReadOnlyListProperty<Counter> countersProperty() {

		return this.counters.getReadOnlyProperty();
	}

	public final ObservableList<Counter> getCounters() {

		return this.counters.get();
	}

	/**
	 * Method called to serializes the Card object.
	 * @param out the output stream used to serialize the card
	 * @throws IOException 
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {

		// out.defaultWriteObject(); Do not use the default write method
		out.writeInt(this.getBattleId()); 			// BattleId
		out.writeBoolean(this.isTapped());			// isTapped
		out.writeBoolean(this.isVisible());			// isVisible
		out.writeBoolean(this.isSelected());		// isSelected
		out.writeObject(this.getPrimaryCardData());	// primaryCardData
		out.writeObject(this.getSecondaryCardData());// secondaryCardData
		out.writeDouble(this.getLocation().getX());	// location.x
		out.writeDouble(this.getLocation().getY());	// location.y
		out.writeObject(new ArrayList<Counter>(this.counters));	// counters
	}

	/**
	 * Method called to create a Card from serialized data.
	 * @param in the input stream used to read the serialized data
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

		// in.defaultReadObject(); Do not use the default read method
		this.battleId = new ReadOnlyIntegerWrapper(this, "battleId", in.readInt());
		this.tapped = new SimpleBooleanProperty(this, "tapped", in.readBoolean());
		this.visible = new SimpleBooleanProperty(this, "visible", in.readBoolean());
		this.selected = new SimpleBooleanProperty(this, "selected", in.readBoolean());
		this.primaryCardData = new ReadOnlyObjectWrapper<MtgCard>(this, "primaryCardData", (MtgCard) in.readObject());
		this.secondaryCardData = new ReadOnlyObjectWrapper<MtgCard>(this, "secondaryCardData", (MtgCard) in.readObject());
		final double x = in.readDouble();
		final double y = in.readDouble();
		this.location = new SimpleObjectProperty<Point2D>(this, "location", new Point2D(x, y));
		@SuppressWarnings("unchecked")
		final ObservableList<Counter> counters = FXCollections.<Counter> observableArrayList((ArrayList<Counter>) in.readObject());
		this.counters = new ReadOnlyListWrapper<Counter>(this, "counters", counters);
	}

	@Override
	public String toString() {

		return "Card [" + this.getBattleId() + ", " + this.isTapped() + ", " + this.isVisible() + ", " + this.isSelected() + ", "
			+ this.getLocation() + ", " + this.getPrimaryCardData().getName() + "]";
	}

	/**
	 * Calculate the hash code of a card. It is only based on the battle ID. Because this
	 * battle id must be unique during a game.
	 * @return the hash code of this card.
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + battleId.get();
		return result;
	}

	/**
	 * Compare two cards. The result is only based on the battle ID.
	 * @param card the card compared
	 * @return <code>true</code> if the two cards have the same battleID
	 */
	@Override
	public boolean equals(Object card) {

		if (this == card)
			return true;
		if (card == null)
			return false;
		if (!(card instanceof Card))
			return false;
		final Card other = (Card) card;
		return this.battleId.get() == other.battleId.get();
	}
}
