package sandbox;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.game.Counter;

import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

public class Card {

	private static int battleIdCounter = 0;

	private final IntegerProperty battleId;

	private final BooleanProperty tapped;

	private final BooleanProperty visible;

	private final BooleanProperty selected;

	private final ObjectProperty<MtgCard> primaryCardData;

	private final ObjectProperty<MtgCard> secondaryCardData;

	private final ObjectProperty<Point2D> location;

	private final ListProperty<Counter> counters;

	/**
	 * 
	 * @param primaryCardData
	 */
	public Card(MtgCard primaryCardData) {

		this.battleId = new ReadOnlyIntegerWrapper(++battleIdCounter);
		this.tapped = new SimpleBooleanProperty(false);
		this.visible = new SimpleBooleanProperty(true);
		this.selected = new SimpleBooleanProperty(false);
		this.primaryCardData = new ReadOnlyObjectWrapper<MtgCard>(primaryCardData);
		this.location = new SimpleObjectProperty<Point2D>(new Point2D(0d, 0d));
		this.counters = new SimpleListProperty<Counter>(FXCollections.<Counter> observableArrayList());

		// Initialize the secondary card data if necessary
		MtgCard secondaryCardData = null;
		String[] names = primaryCardData.getNames();

		if (names != null && names.length > 1) {
			secondaryCardData = MtgDataUtility.getInstance().getMtgCard(names[1]);
		}
		this.secondaryCardData = new ReadOnlyObjectWrapper<MtgCard>(secondaryCardData);
	}

	public static void setBattleIdCounter(final int battleIdCounter) {

		Card.battleIdCounter = battleIdCounter;
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

	public final ObjectProperty<MtgCard> primaryCardDataProperty() {

		return this.primaryCardData;
	}

	public final MtgCard getPrimaryCardData() {

		return this.primaryCardData.get();
	}

	public final void setPrimaryCardData(final MtgCard primaryCardData) {

		this.primaryCardData.set(primaryCardData);
	}

	public final ObjectProperty<MtgCard> secondaryCardDataProperty() {

		return this.secondaryCardData;
	}

	public final MtgCard getSecondaryCardData() {

		return this.secondaryCardData.get();
	}

	public final void setSecondaryCardData(final MtgCard secondaryCardData) {

		this.secondaryCardData.set(secondaryCardData);
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

	public final ListProperty<Counter> countersProperty() {

		return this.counters;
	}

	public final ObservableList<Counter> getCounters() {

		return this.counters.get();
	}

	public final void setCounters(final ObservableList<Counter> counters) {

		this.counters.set(counters);
	}

	// public IntegerProperty battleId() { return this.battleIdProperty; }
	// public int getBattleId() { return this.battleIdProperty.get(); }
	//
	// public BooleanProperty tapped() { return this.tappedProperty; }
	// public boolean getTapped() { return this.tappedProperty.get(); }
	// public void setTapped(boolean tapped) { this.tappedProperty.set(tapped); }
	//
	// public BooleanProperty visible() { return this.visibleProperty; }
	// public boolean isVisible() { return this.visibleProperty.get(); }
	// public void setVisible(boolean visible) { this.visibleProperty.set(visible); }
	//
	// public BooleanProperty selected() { return this.selectedProperty; }
	// public boolean isSelected() { return this.selectedProperty.get(); }
	// public void setSelected(boolean selected) { this.selectedProperty.set(selected); }
	//
	// public ObjectProperty<MtgCard> primaryCardData() { return this.primaryCardDataProperty; }
	// public MtgCard getPrimaryCardData() { return this.primaryCardDataProperty.get(); }
	//
	// public ObjectProperty<MtgCard> secondaryCardData() { return this.secondaryCardDataProperty; }
	// public MtgCard getSecondaryCardData() { return this.secondaryCardDataProperty.get(); }
	//
	// public ObjectProperty<Point2D> location() { return this.locationProperty; }
	// public Point2D getLocation() { return this.locationProperty.get(); }
	// public void setLocation(Point2D newLocation) { this.locationProperty.set(newLocation); }
	// public void setLocation(double x, double y) { this.locationProperty.set(new Point2D(x, y)); }
	//
	// public ListProperty<Counter> counters() { return this.countersProperty; }
	// public ObservableList<Counter> getCounters() { return this.countersProperty.get(); }
}
