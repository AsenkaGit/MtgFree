package sandbox;

import java.util.EventObject;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameTable {

	private final StringProperty name;

	private final ObjectProperty<Player> localPlayer;

	private final ObjectProperty<Player> otherPlayer;

	private final ListProperty<EventObject> gameEvents;
	
	
	public GameTable(String name, Player localPlayer) {
		
		this.name = new SimpleStringProperty(name);
		this.localPlayer = new SimpleObjectProperty<Player>(localPlayer);
		this.otherPlayer = new SimpleObjectProperty<Player>(null);
		this.gameEvents = new SimpleListProperty<EventObject>(FXCollections.observableArrayList());
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

	public final ObjectProperty<Player> localPlayerProperty() {

		return this.localPlayer;
	}

	public final Player getLocalPlayer() {

		return this.localPlayer.get();
	}

	public final void setLocalPlayer(final Player localPlayer) {

		this.localPlayer.set(localPlayer);
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

	public final ListProperty<EventObject> gameEventsProperty() {

		return this.gameEvents;
	}

	public final ObservableList<EventObject> getGameEvents() {

		return this.gameEvents.get();
	}

	public final void setGameEvents(final ObservableList<EventObject> gameEvents) {

		this.gameEvents.set(gameEvents);
	}

}
