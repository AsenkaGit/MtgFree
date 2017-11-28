package asenka.mtgfree.model;

import asenka.mtgfree.model.data.MtgCard;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Deck {

	private final StringProperty name;

	private final StringProperty description;

	private final ReadOnlyListWrapper<MtgCard> main;

	private final ReadOnlyListWrapper<MtgCard> sideboard;

	public Deck(String name, String description) {

		this.name = new SimpleStringProperty(this, "name", name);
		this.description = new SimpleStringProperty(this, "description", description);
		this.main = new ReadOnlyListWrapper<MtgCard>(this, "main", FXCollections.<MtgCard> observableArrayList());
		this.sideboard = new ReadOnlyListWrapper<MtgCard>(this, "sideboard", FXCollections.<MtgCard> observableArrayList());
	}

	public final StringProperty nameProperty() {

		return this.name;
	}

	public final String getName() {

		return this.name.get();
	}

	public final void setName(final String name) {

		this.name.set(name);
	}

	public final StringProperty descriptionProperty() {

		return this.description;
	}

	public final String getDescription() {

		return this.description.get();
	}

	public final void setDescription(final String description) {

		this.description.set(description);
	}

	public final ReadOnlyListProperty<asenka.mtgfree.model.data.MtgCard> mainProperty() {

		return this.main.getReadOnlyProperty();
	}

	public final ObservableList<MtgCard> getMain() {

		return this.main.get();
	}

	public final ReadOnlyListProperty<asenka.mtgfree.model.data.MtgCard> sideboardProperty() {

		return this.sideboard.getReadOnlyProperty();
	}

	public final ObservableList<MtgCard> getSideboard() {

		return this.sideboard.get();
	}
	
	@Override
	public String toString() {
		
		return "Deck [" + this.getName() + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((description.get() == null) ? 0 : description.get().hashCode());
		result = prime * result + ((main.get() == null) ? 0 : main.get().hashCode());
		result = prime * result + ((name.get() == null) ? 0 : name.get().hashCode());
		result = prime * result + ((sideboard.get() == null) ? 0 : sideboard.get().hashCode());
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
		Deck other = (Deck) obj;
		if (this.name.isNotEqualTo(other.name).get())
			return false;
		if (this.description.isNotEqualTo(other.description).get())
			return false;
		if (main == null) {
			if (other.main != null)
				return false;
		} else if (!main.equals(other.main))
			return false;
		if (sideboard == null) {
			if (other.sideboard != null)
				return false;
		} else if (!sideboard.equals(other.sideboard))
			return false;
		return true;
	}
}
