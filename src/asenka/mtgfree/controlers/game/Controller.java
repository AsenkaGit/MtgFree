package asenka.mtgfree.controlers.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A generic class for all the controllers. 
 * 
 * @author asenka
 * @see Observer
 * @see Observable
 */
public abstract class Controller<Type extends Observable> {

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
		BATTLEFIELD, HAND, LIBRARY, EXILE, GRAVEYARD
	}

	/**
	 * All the observers controlled by the controller. All the observers should implements {@link Observer} interface
	 */
	protected final List<Observer> observers;

	/**
	 * The controlled data. It could be of any sub type of {@link Observable}
	 */
	protected final Type data;

	/**
	 * Protected controller. 
	 * @param data the controlled data
	 */
	protected Controller(Type data) {
		this.observers = new ArrayList<Observer>(2);
		this.data = data;
	}

	/**
	 * @return the controlled data
	 */
	public Type getData() {

		return this.data;
	}
	
	/**
	 * @return an unmodifiable list of the observers managed by this controller
	 * @see Collections#unmodifiableList(List)
	 */
	public List<Observer> getObservers() {
		
		return Collections.unmodifiableList(this.observers);
	}

	/**
	 * Add a view to the controller and to the observers of the data
	 * @param observer the view
	 */
	public void addObserver(Observer observer) {

		this.observers.add(observer);
		this.data.addObserver(observer);
	}

	/**
	 * Remove a view from the controller and from the observers of the data
	 * @param observer the view
	 */
	public void deleteObserver(Observer observer) {

		this.observers.remove(observer);
		this.data.deleteObserver(observer);
	}

	/**
	 * Removes all the observers of the data
	 */
	public void deleteObservers() {

		this.observers.clear();
		this.data.deleteObservers();
	}
	
	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [ #Views = " + observers.size() + ", " + data + "]";
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
