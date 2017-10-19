package asenka.mtgfree.controlers.game;

import java.util.ArrayList;
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
	 * All the views controlled by the controller. All the views should implements {@link Observer} interface
	 */
	protected final List<Observer> views;

	/**
	 * The controlled data. It could be of any sub type of {@link Observable}
	 */
	protected final Type data;

	/**
	 * Protected controller. 
	 * @param data the controlled data
	 */
	protected Controller(Type data) {
		this.views = new ArrayList<Observer>(2);
		this.data = data;
	}

	/**
	 * @return the controlled data
	 */
	public Type getData() {

		return this.data;
	}

	/**
	 * Add a view to the controller and to the observers of the data
	 * @param view the view
	 */
	public void addView(Observer view) {

		this.views.add(view);
		this.data.addObserver(view);
	}

	/**
	 * Remove a view from the controller and from the observers of the data
	 * @param view the view
	 */
	public void removeView(Observer view) {

		this.views.remove(view);
		this.data.deleteObserver(view);
	}

	/**
	 * Removes all the observers of the data
	 */
	public void clearViews() {

		this.data.deleteObservers();
		this.views.clear();
	}
	
	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [ #Views = " + views.size() + ", " + data + "]";
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
