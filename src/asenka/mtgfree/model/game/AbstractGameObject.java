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
 * All the object observable during a MTG game extends this class
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
	 * The name of the private <code>"obs"</code> field of the Observable class
	 */
	private static final String OBS_FIELD_NAME = "obs";
	
	/**
	 * The private field from Observable : <code>obs</code>. It contains the observers of the game object.
	 * @see Observable#obs 
	 */
	private static Field obsField;
	
	static {
		
		try {
			obsField = Observable.class.getDeclaredField(OBS_FIELD_NAME);
			obsField.setAccessible(true);
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
			Vector<Observer> obs = (Vector<Observer>) obsField.get(this);
			return Collections.unmodifiableList(new ArrayList<Observer>(obs));
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeErrorException(new Error(e));
		}
	}
}
