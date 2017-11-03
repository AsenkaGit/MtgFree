package asenka.mtgfree.controlers.game;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * A generic class for all the controllers.
 * 
 * @author asenka
 * @see Observer
 * @see Observable
 */
public abstract class Controller<Type extends Observable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1250928326307690607L;

	/**
	 * <p>
	 * This origin indicates the origin of a card about to be played. For example, when you want to play a card on the battlefield
	 * from your hand, then the origin would be HAND. When you want to exile a card from your library, then the origin would be
	 * LIBRARY.
	 * </p>
	 * <p>
	 * It is important to have this information to be able to remove the card from its original collection.
	 * </p>
	 */
	public enum Origin {
		BATTLEFIELD, HAND, LIBRARY, EXILE, GRAVEYARD, OPPONENT_BATTLEFIELD
	}

	/**
	 * The controlled data. It could be of any sub type of {@link Observable}
	 */
	protected final Type data;

	/**
	 * This boolean indicates if the controller is used by the player. If <code>true</code>, then it means it is a normal
	 * controller used by the player to manipulate the local data. If <code>false</code>, it means it is a controller used by the
	 * network manager to update the opponent data during a game
	 */
	protected final boolean playerManaged;

	/**
	 * Protected controller.
	 * 
	 * @param data the controlled data
	 */
	protected Controller(Type data, boolean playerManaged) {

		this.data = data;
		this.playerManaged = playerManaged;
	}

	/**
	 * @return the controlled data
	 */
	public Type getData() {

		return this.data;
	}

	/**
	 * @return <code>true</code> if the controller is managed by a human player
	 * @see Controller#playerManaged
	 */
	public boolean isPlayerManaged() {

		return this.playerManaged;
	}

	/**
	 * Add a view to the controller and to the observers of the data
	 * 
	 * @param observer the view
	 */
	public void addObserver(Observer observer) {

		this.data.addObserver(observer);
	}

	/**
	 * Remove a view from the controller and from the observers of the data
	 * 
	 * @param observer the view
	 */
	public void deleteObserver(Observer observer) {

		this.data.deleteObserver(observer);
	}

	/**
	 * Removes all the observers of the data
	 */
	public void deleteObservers() {

		this.data.deleteObservers();
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [ data = " + data + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		Controller<?> other = (Controller<?>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
}
