package asenka.mtgfree.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player implements Serializable {

	private static final long serialVersionUID = -6085712796619122606L;

	private ListProperty<Card> library;

	private ReadOnlyListWrapper<Card> battlefield;

	private ReadOnlyListWrapper<Card> hand;

	private ReadOnlyListWrapper<Card> graveyard;

	private ReadOnlyListWrapper<Card> exile;
	
	private IntegerProperty id;

	private StringProperty name;

	private IntegerProperty life;

	private IntegerProperty poison;
	
	public Player(int id, String name) {
		
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.life = new SimpleIntegerProperty(20);
		this.poison = new SimpleIntegerProperty(0);
		this.library = new SimpleListProperty<Card>(FXCollections.<Card>observableArrayList());
		this.battlefield = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
		this.hand = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
		this.graveyard = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
		this.exile = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
	}

	public final ListProperty<Card> libraryProperty() {
	
		return this.library;
	}

	public final ObservableList<Card> getLibrary() {
	
		return this.library.get();
	}

	public final void setLibrary(ObservableList<Card> library) {
		
		this.library.set(library);
	}

	public final ReadOnlyListProperty<Card> battlefieldProperty() {

		return this.battlefield.getReadOnlyProperty();
	}

	public final ObservableList<Card> getBattlefield() {

		return this.battlefield.get();
	}

	public final ReadOnlyListProperty<Card> handProperty() {

		return this.hand.getReadOnlyProperty();
	}

	public final ObservableList<Card> getHand() {

		return this.hand.get();
	}

	public final ReadOnlyListProperty<Card> graveyardProperty() {

		return this.graveyard.getReadOnlyProperty();
	}

	public final ObservableList<Card> getGraveyard() {

		return this.graveyard.get();
	}

	public final ReadOnlyListProperty<Card> exileProperty() {

		return this.exile.getReadOnlyProperty();
	}

	public final ObservableList<Card> getExile() {

		return this.exile.get();
	}
	
	public final IntegerProperty idProperty() {
		
		return this.id;
	}
	
	public final int getId() {
		
		return this.id.get();
	}
	
	public final void setId(final int id) {
		
		this.id.set(id);
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
	
	public final void shuffleLibrary() {
		
		FXCollections.shuffle(this.library);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		
		out.writeInt(this.id.get());
		out.writeUTF(this.name.get());
		out.writeInt(this.life.get());
		out.writeInt(this.poison.get());
		out.writeObject(new ArrayList<Card>(this.library.get()));
		out.writeObject(new ArrayList<Card>(this.hand.get()));
		out.writeObject(new ArrayList<Card>(this.graveyard.get()));
		out.writeObject(new ArrayList<Card>(this.exile.get()));
		out.writeObject(new ArrayList<Card>(this.battlefield.get()));
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

		this.id = new SimpleIntegerProperty(this, "id", in.readInt());
		this.name = new SimpleStringProperty(this, "name", in.readUTF());
		this.life = new SimpleIntegerProperty(this, "life", in.readInt());
		this.poison = new SimpleIntegerProperty(this, "poison", in.readInt());
		final ObservableList<Card> library = FXCollections.<Card> observableArrayList((ArrayList<Card>) in.readObject());
		final ObservableList<Card> hand = FXCollections.<Card> observableArrayList((ArrayList<Card>) in.readObject());
		final ObservableList<Card> graveyard = FXCollections.<Card> observableArrayList((ArrayList<Card>) in.readObject());
		final ObservableList<Card> exile = FXCollections.<Card> observableArrayList((ArrayList<Card>) in.readObject());
		final ObservableList<Card> battlefield = FXCollections.<Card> observableArrayList((ArrayList<Card>) in.readObject());
		this.library = new ReadOnlyListWrapper<Card>(this, "library", library);
		this.hand = new ReadOnlyListWrapper<Card>(this, "hand", hand);
		this.graveyard = new ReadOnlyListWrapper<Card>(this, "graveyard", graveyard);
		this.exile = new ReadOnlyListWrapper<Card>(this, "exile", exile);
		this.battlefield = new ReadOnlyListWrapper<Card>(this, "battlefield", battlefield);
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + id.get();
		return result;
	}
	
	
	@Override
	public String toString() {
		
		return "Player [" + this.getId() + ", " + this.getName() + " ]";
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return this.id.isEqualTo(other.id).get();
	}
	
	
}
