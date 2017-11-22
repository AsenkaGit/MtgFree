package sandbox;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player {

	private final ListProperty<Card> battlefield;

	private final ListProperty<Card> hand;

	private final ListProperty<Card> library;

	private final ListProperty<Card> graveyard;

	private final ListProperty<Card> exile;

	private final StringProperty name;

	private final IntegerProperty life;

	private final IntegerProperty poison;
	
	/**
	 * 
	 * @param name
	 */
	public Player(String name) {
		
		this.name = new SimpleStringProperty(name);
		this.life = new SimpleIntegerProperty(20);
		this.poison = new SimpleIntegerProperty(0);
		this.battlefield = new SimpleListProperty<Card>(FXCollections.<Card>observableArrayList());
		this.hand = new SimpleListProperty<Card>(FXCollections.<Card>observableArrayList());
		this.library = new SimpleListProperty<Card>(FXCollections.<Card>observableArrayList());
		this.graveyard = new SimpleListProperty<Card>(FXCollections.<Card>observableArrayList());
		this.exile = new SimpleListProperty<Card>(FXCollections.<Card>observableArrayList());
	}

	public final ListProperty<Card> battlefieldProperty() {

		return this.battlefield;
	}

	public final ObservableList<Card> getBattlefield() {

		return this.battlefield.get();
	}

	public final ListProperty<Card> handProperty() {

		return this.hand;
	}

	public final ObservableList<Card> getHand() {

		return this.hand.get();
	}

	public final void setHandProperty(final ObservableList<Card> hand) {

		this.hand.set(hand);
	}

	public final ListProperty<Card> libraryProperty() {

		return this.library;
	}

	public final ObservableList<Card> getLibrary() {

		return this.library.get();
	}

	public final void setLibrary(final ObservableList<Card> library) {

		this.library.set(library);
	}

	public final ListProperty<Card> graveyardProperty() {

		return this.graveyard;
	}

	public final ObservableList<Card> getGraveyard() {

		return this.graveyard.get();
	}

	public final void setGraveyard(final ObservableList<Card> graveyard) {

		this.graveyard.set(graveyard);
	}

	public final ListProperty<Card> exileProperty() {

		return this.exile;
	}

	public final ObservableList<Card> getExile() {

		return this.exile.get();
	}

	public final void setExileProperty(final ObservableList<Card> exile) {

		this.exile.set(exile);
	}

	public final StringProperty namePropertyProperty() {

		return this.name;
	}

	public final String getName() {

		return this.name.get();
	}

	public final void setName(final String name) {

		this.name.set(name);
	}

	public final IntegerProperty lifeProperty() {

		return this.life;
	}

	public final int getLife() {

		return this.life.get();
	}

	public final void setLife(final int life) {

		this.life.set(life);
	}

	public final IntegerProperty poisonProperty() {

		return this.poison;
	}

	public final int getPoison() {

		return this.poison.get();
	}

	public final void setPoison(final int poison) {

		this.poison.set(poison);
	}
}
