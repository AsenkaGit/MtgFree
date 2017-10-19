package asenka.mtgfree.controlers.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author asenka
 *
 */
public abstract class Controller<T extends Observable> {

	/**
	 * 
	 */
	protected final List<Observer> views;

	/**
	 * 
	 */
	protected final T data;

	/**
	 * @param observedData
	 */
	protected Controller(T observedData) {
		this.views = new ArrayList<Observer>(2);
		this.data = observedData;
	}

	/**
	 * 
	 * @return
	 */
	public T getData() {

		return this.data;
	}

	/**
	 * @param view
	 */
	public void addView(Observer view) {

		this.views.add(view);
		this.data.addObserver(view);
	}

	/**
	 * @param view
	 */
	public void removeView(Observer view) {

		this.views.remove(view);
		this.data.deleteObserver(view);
	}

	/**
	 * 
	 */
	public void clearViews() {

		this.data.deleteObservers();
		this.views.clear();
	}
}
