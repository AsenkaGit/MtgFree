package asenka.mtgfree.model;

import asenka.mtgfree.controllers.communication.SynchronizationEvent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameTable {

	private final ReadOnlyStringWrapper name;

	private final ReadOnlyObjectWrapper<Player> localPlayer;

	private final ObjectProperty<Player> otherPlayer;

	private final ReadOnlyListWrapper<SynchronizationEvent> gameEvents;
	
	private final ReadOnlyListWrapper<Card> selectedCards;

	public GameTable(String name, final Player localPlayer) {

		this.name = new ReadOnlyStringWrapper(this, "name", name);
		this.localPlayer = new ReadOnlyObjectWrapper<Player>(this, "localPlayer", localPlayer);
		this.otherPlayer = new SimpleObjectProperty<Player>(this, "otherPlayer");
		this.gameEvents = new ReadOnlyListWrapper<SynchronizationEvent>(this, "gameEvents", FXCollections.<SynchronizationEvent>observableArrayList());
		this.selectedCards = new ReadOnlyListWrapper<Card>(this, "selectedCards", FXCollections.<Card>observableArrayList());
	}

	public final ReadOnlyStringProperty nameProperty() {

		return this.name.getReadOnlyProperty();
	}

	public final String getName() {

		return this.name.get();
	}

	public final ReadOnlyObjectProperty<Player> localPlayerProperty() {

		return this.localPlayer.getReadOnlyProperty();
	}

	public final Player getLocalPlayer() {

		return this.localPlayer.get();
	}

	public final ObjectProperty<Player> otherPlayerProperty() {

		return this.otherPlayer;
	}

	public final Player getOtherPlayer() {

		return this.otherPlayer.get();
	}

	public final void setOtherPlayer(final Player otherPlayer) {

		this.otherPlayer.set(otherPlayer);
	}

	public final ReadOnlyListProperty<SynchronizationEvent> gameEventsProperty() {

		return this.gameEvents.getReadOnlyProperty();
	}

	public final ObservableList<SynchronizationEvent> getGameEvents() {

		return this.gameEvents.get();
	}
	
	public final ReadOnlyListProperty<Card> selectedCardsProperty() {
		
		return this.selectedCards.getReadOnlyProperty();
	}
	
	public final ObservableList<Card> getSelectedCards() {
		
		return this.selectedCards.get();
	}
	
	@Override
	public String toString() {
		
		return "GameTable [" + this.getName() + ", " + this.getLocalPlayer() + ", " + this.getOtherPlayer() + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameEvents.get() == null) ? 0 : gameEvents.get().hashCode());
		result = prime * result + ((selectedCards.get() == null) ? 0 : selectedCards.get().hashCode());
		result = prime * result + ((localPlayer.get() == null) ? 0 : localPlayer.get().hashCode());
		result = prime * result + ((name.get() == null) ? 0 : name.get().hashCode());
		result = prime * result + ((otherPlayer.get() == null) ? 0 : otherPlayer.get().hashCode());
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
		GameTable other = (GameTable) obj;
		if(name.isNotEqualTo(other.name).get())
			return false;
		if(localPlayer.isNotEqualTo(other.localPlayer).get())
			return false;
		if(otherPlayer.isNotEqualTo(other.otherPlayer).get())
			return false;
		if(gameEvents.isNotEqualTo(other.gameEvents).get())
			return false;
		if(selectedCards.isNotEqualTo(other.selectedCards).get())
			return false;
		return true;
	}	
}