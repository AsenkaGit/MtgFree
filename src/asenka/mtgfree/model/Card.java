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

public class Card implements Serializable {
	
	private static final long serialVersionUID = -3454805330637228059L;

	private IntegerProperty battleId;

	private BooleanProperty tapped;

	private BooleanProperty visible;

	private BooleanProperty selected;

	private ReadOnlyObjectWrapper<MtgCard> primaryCardData;

	private ReadOnlyObjectWrapper<MtgCard> secondaryCardData;

	private ObjectProperty<Point2D> location;

	private ReadOnlyListWrapper<Counter> counters;

	/**
	 * @param battleId
	 * @param primaryCardData
	 */
	public Card(final int battleId, final MtgCard primaryCardData) {

		this.battleId = new SimpleIntegerProperty(this, "battleId", battleId);
		this.tapped = new SimpleBooleanProperty(this, "tapped", false);
		this.visible = new SimpleBooleanProperty(this, "visible", true);
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

	private void writeObject(ObjectOutputStream out) throws IOException {

		out.writeInt(this.getBattleId());
		out.writeBoolean(this.isTapped());
		out.writeBoolean(this.isVisible());
		out.writeBoolean(this.isSelected());
		out.writeObject(this.getPrimaryCardData());
		out.writeObject(this.getSecondaryCardData());
		out.writeDouble(this.getLocation().getX());
		out.writeDouble(this.getLocation().getY());
		out.writeObject(new ArrayList<Counter>(this.counters));
	}

	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

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

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + battleId.get();
		result = prime * result + ((location.get() == null) ? 0 : location.get().hashCode());
		result = prime * result + ((primaryCardData.get() == null) ? 0 : primaryCardData.get().hashCode());
		result = prime * result + ((secondaryCardData.get() == null) ? 0 : secondaryCardData.get().hashCode());
		result = prime * result + ((counters.get() == null) ? 0 : counters.get().hashCode());
		result = prime * result + (selected.get() ? 1231 : 1237);
		result = prime * result + (tapped.get() ? 1231 : 1237);
		result = prime * result + (visible.get() ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Card))
			return false;
		final Card other = (Card) obj;
		if (this.battleId.isNotEqualTo(other.battleId).get())
			return false;
		if (this.tapped.isNotEqualTo(other.tapped).get())
			return false;
		if (this.visible.isNotEqualTo(other.visible).get())
			return false;
		if (this.selected.isNotEqualTo(other.selected).get())
			return false;
		if (this.primaryCardData.isNotEqualTo(other.primaryCardData).get())
			return false;
		if (this.secondaryCardData.isNotEqualTo(other.secondaryCardData).get())
			return false;
		if (this.counters.isNotEqualTo(other.counters).get())
			return false;
		return true;
	}
}
