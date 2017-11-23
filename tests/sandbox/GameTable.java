package sandbox;

import java.util.EventObject;

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

	private final ReadOnlyListWrapper<EventObject> gameEvents;

	public GameTable(String name, final Player localPlayer) {

		this.name = new ReadOnlyStringWrapper(this, "name", name);
		this.localPlayer = new ReadOnlyObjectWrapper<Player>(this, "localPlayer", localPlayer);
		this.otherPlayer = new SimpleObjectProperty<Player>(this, "otherPlayer");
		this.gameEvents = new ReadOnlyListWrapper<EventObject>(this, "gameEvents", FXCollections.observableArrayList());
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

	public final ReadOnlyListProperty<EventObject> gameEventsProperty() {

		return this.gameEvents.getReadOnlyProperty();
	}

	public final ObservableList<EventObject> getGameEvents() {

		return this.gameEvents.get();
	}
}
