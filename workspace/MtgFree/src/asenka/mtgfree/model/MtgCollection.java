package asenka.mtgfree.model;


import java.util.*;

/**
 * The magic cards are gathered by collection (Kaladesh, Amonketh, Tempest, etc...). 
 * 
 * Every cards belongs to a collection. If a collection is removed, it means all the cards related to this collection are supposed to be removed too.
 * @author Asenka
 */
public class MtgCollection {

	/**
	 * Default constructor
	 */
	public MtgCollection() {
	}

	/**
	 * The id of the collection in the database.
	 */
	private int id;

	/**
	 * The name of the collection (localized).
	 */
	private String name;

	/**
	 * The description of the collection (localized).
	 */
	private String description;

	/**
	 * A collection can be regroup into a block that usually contains 2 or 3 collections (localized).
	 */
	private String block;

	/**
	 * 
	 */
	private Set<MtgCard> cards;

}