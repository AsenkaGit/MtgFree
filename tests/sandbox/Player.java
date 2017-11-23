package sandbox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player implements Serializable {

	private static final long serialVersionUID = -4119416247136512680L;

	private ReadOnlyListWrapper<Card> battlefield;

	private ReadOnlyListWrapper<Card> hand;

	private ReadOnlyListWrapper<Card> library;

	private ReadOnlyListWrapper<Card> graveyard;

	private ReadOnlyListWrapper<Card> exile;

	private StringProperty name;

	private IntegerProperty life;

	private IntegerProperty poison;
	
	public Player(String name) {
		
		this.name = new SimpleStringProperty(name);
		this.life = new SimpleIntegerProperty(20);
		this.poison = new SimpleIntegerProperty(0);
		this.battlefield = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
		this.hand = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
		this.library = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
		this.graveyard = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
		this.exile = new ReadOnlyListWrapper<Card>(FXCollections.<Card>observableArrayList());
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

	public final ReadOnlyListProperty<Card> libraryProperty() {

		return this.library.getReadOnlyProperty();
	}

	public final ObservableList<Card> getLibrary() {

		return this.library.get();
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
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		
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
		result = prime * result + ((battlefield == null) ? 0 : battlefield.hashCode());
		result = prime * result + ((exile == null) ? 0 : exile.hashCode());
		result = prime * result + ((graveyard == null) ? 0 : graveyard.hashCode());
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		result = prime * result + ((library == null) ? 0 : library.hashCode());
		result = prime * result + life.get();
		result = prime * result + ((name.get() == null) ? 0 : name.hashCode());
		result = prime * result + poison.get();
		return result;
	}
	
	
	@Override
	public String toString() {
		
		return "Player [" + this.getName() + " ]";
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
		if (this.name.isNotEqualTo(other.name).get())
			return false;
		if (this.life.isNotEqualTo(other.life).get())
			return false;
		if (this.poison.isNotEqualTo(other.poison).get())
			return false;
		if (battlefield == null) {
			if (other.battlefield != null)
				return false;
		} else if (!battlefield.equals(other.battlefield))
			return false;
		if (exile == null) {
			if (other.exile != null)
				return false;
		} else if (!exile.equals(other.exile))
			return false;
		if (graveyard == null) {
			if (other.graveyard != null)
				return false;
		} else if (!graveyard.equals(other.graveyard))
			return false;
		if (hand == null) {
			if (other.hand != null)
				return false;
		} else if (!hand.equals(other.hand))
			return false;
		if (library == null) {
			if (other.library != null)
				return false;
		} else if (!library.equals(other.library))
			return false;
		return true;
	}
	
	
}
