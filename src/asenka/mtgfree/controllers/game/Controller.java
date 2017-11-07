package asenka.mtgfree.controllers.game;

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
	 * generated ID for serialization.
	 */
	private static final long serialVersionUID = -1250928326307690607L;



	/**
	 * The controlled data. It could be of any sub type of {@link Observable}
	 */
	protected final Type controlledData;

	/**
	 * This flag indicates whether or not the controller notify the actions performed to the network. If <code>true</code>, when
	 * an action is performed, then a NetworkEvent will be send to the other player. If <code>false</code>, then the action will
	 * remains local.
	 * @see NetworkEvent
	 */
	protected final boolean createNetworkEvents;

	/**
	 * 
	 * @param data the data controlled by this controller
	 * @param createNetworkEvent
	 */
	protected Controller(Type data, boolean createNetworkEvent) {

		this.controlledData = data;
		this.createNetworkEvents = createNetworkEvent;
	}

	/**
	 * @return the controlled data
	 */
	public Type getData() {

		return this.controlledData;
	}

	/**
	 * @return <code>true</code> if the controller is managed by a human player
	 * @see Controller#createNetworkEvents
	 */
	public boolean isPlayerManaged() {

		return this.createNetworkEvents;
	}

	/**
	 * Add a view to the controller and to the observers of the data
	 * 
	 * @param observer the view
	 */
	public void addObserver(Observer observer) {

		this.controlledData.addObserver(observer);
	}

	/**
	 * Remove a view from the controller and from the observers of the data
	 * 
	 * @param observer the view
	 */
	public void deleteObserver(Observer observer) {

		this.controlledData.deleteObserver(observer);
	}

	/**
	 * Removes all the observers of the data
	 */
	public void deleteObservers() {

		this.controlledData.deleteObservers();
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [ data = " + controlledData + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((controlledData == null) ? 0 : controlledData.hashCode());
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
		if (controlledData == null) {
			if (other.controlledData != null)
				return false;
		} else if (!controlledData.equals(other.controlledData))
			return false;
		return true;
	}
}
