package asenka.mtgfree.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import asenka.mtgfree.model.utilities.CardsManager;
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

/**
 * This class contains the player data used during a game :
 * <ul>
 * <li>life points,</li>
 * <li>poison points,</li>
 * <li>cards lists:
 * <ul>
 * <li>library</li>
 * <li>hand</li>
 * <li>graveyard</li>
 * <li>exile</li>
 * <li>battlefield</li>
 * </ul>
 * </li>
 * <li>...</li>
 * <ul>
 * 
 * @author asenka
 * @see Serializable
 */
public class Player implements Serializable {

	/**
	 * The serialized ID of the class. It must be updated each time the class is updated.
	 */
	private static final long serialVersionUID = -6085712796619122606L;

	/**
	 * The list of cards in the player's library. This list can be fully updated when the opponent shuffles its library.
	 * @see Card
	 */
	private ListProperty<Card> library;

	/**
	 * The list of cards in the player's battlefield area. This property is read-only because the reference cannot be changed.
	 * @see Card
	 */
	private ReadOnlyListWrapper<Card> battlefield;

	/**
	 * The list of cards in the player's hand. This property is read-only because the reference cannot be changed.
	 * @see Card
	 */
	private ReadOnlyListWrapper<Card> hand;

	/**
	 * The list of cards in the player's graveyard area. This property is read-only because the reference cannot be changed.
	 * @see Card
	 */
	private ReadOnlyListWrapper<Card> graveyard;

	/**
	 * The list of cards in the player's exile area. This property is read-only because the reference cannot be changed.
	 * @see Card
	 */
	private ReadOnlyListWrapper<Card> exile;

	/**
	 * The player ID. This ID is used by the {@link CardsManager} to build the {@link Card} and their battleID.
	 */
	private IntegerProperty id;

	/**
	 * The player name.
	 */
	private StringProperty name;

	/**
	 * The number of remaining life points.
	 */
	private IntegerProperty life;

	/**
	 * The number of poison counters on the players.
	 */
	private IntegerProperty poison;

	/**
	 * Build a player
	 * @param id {@link Player#id}
	 * @param name {@link Player#name}
	 */
	public Player(int id, String name) {

		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.life = new SimpleIntegerProperty(20);
		this.poison = new SimpleIntegerProperty(0);
		this.library = new SimpleListProperty<Card>(FXCollections.<Card> observableArrayList());
		this.battlefield = new ReadOnlyListWrapper<Card>(FXCollections.<Card> observableArrayList());
		this.hand = new ReadOnlyListWrapper<Card>(FXCollections.<Card> observableArrayList());
		this.graveyard = new ReadOnlyListWrapper<Card>(FXCollections.<Card> observableArrayList());
		this.exile = new ReadOnlyListWrapper<Card>(FXCollections.<Card> observableArrayList());
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

	/**
	 * Method called to serializes the Player object.
	 * @param out the output stream used to serialize the player
	 * @throws IOException 
	 */
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

	/**
	 * Method called to create a Player from serialized data.
	 * @param in the input stream used to read the serialized data
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
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
	public String toString() {

		return "Player [" + this.getId() + ", " + this.getName() + " ]";
	}

	/**
	 * Calculate the hash code of a player based on the player ID.
	 * @return the hash code of the player 
	 */
	@Override
	public int hashCode() {
	
		final int prime = 31;
		int result = 1;
		result = prime * result + id.get();
		return result;
	}

	/**
	 * Compare two players. The result is only based on the player ID.
	 * @param player the player compared
	 * @return <code>true</code> if the two players have the same ID
	 */
	@Override
	public boolean equals(Object player) {

		if (this == player)
			return true;
		if (player == null)
			return false;
		if (getClass() != player.getClass())
			return false;
		Player other = (Player) player;
		return this.id.get() == other.id.get();
	}
}
