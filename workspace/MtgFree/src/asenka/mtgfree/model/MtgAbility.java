package asenka.mtgfree.model;


import java.util.*;

/**
 * This class represents a card ability (fly, deathtouch, hexproof, etc...)
 * @author Asenka
 */
public class MtgAbility {

	/**
	 * Default constructor
	 */
	public MtgAbility() {
	}

	/**
	 * 
	 */
	private int id;

	/**
	 * The ability name (localized).
	 */
	private String name;

	/**
	 * A description of the ability (localized)
	 */
	private String description;

}