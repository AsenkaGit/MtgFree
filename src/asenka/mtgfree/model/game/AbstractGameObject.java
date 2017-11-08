package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.management.RuntimeErrorException;

/**
 * The a
 * 
 * 
 * @author asenka
 * @see Observable
 * @see Serializable
 */
public abstract class AbstractGameObject extends Observable implements Serializable {
	
	/**
	 * The generated ID for serialization
	 */
	private static final long serialVersionUID = -1652738694744426771L;
	
	/**
	 * The private field from Observable : <code>obs</code>. It contains the observers of the game object.
	 * @see Observable#obs 
	 */
	private static Field OBSERVERS_FIELD;
	
	static {
		
		try {
			OBSERVERS_FIELD = Observable.class.getDeclaredField("obs");
			OBSERVERS_FIELD.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeErrorException(new Error(e));
		}
	}
	
	/**
	 * Use java reflection to get the private data from Observable class where the observers are stored.
	 * @return the unmodifiable list of observers of the game data
	 */
	public List<Observer> getObservers() {
		
		try {
			@SuppressWarnings("unchecked")
			Vector<Observer> obs = (Vector<Observer>) OBSERVERS_FIELD.get(this);
			return Collections.unmodifiableList(new ArrayList<Observer>(obs));
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeErrorException(new Error(e));
		}
	}
	
}
