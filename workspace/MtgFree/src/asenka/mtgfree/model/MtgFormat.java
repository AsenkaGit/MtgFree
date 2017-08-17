package asenka.mtgfree.model;


import java.util.*;

/**
 * This class reprents a way of playing magic. Some cards are not legal on some format. Each cards should have a collection of formats upon which it's legal to play with it.
 * @author Asenka
 */
public class MtgFormat {

	/**
	 * Default constructor
	 */
	public MtgFormat() {
	}

	/**
	 * 
	 */
	private int id;

	/**
	 * The name of the format (localized).
	 */
	private String name;

	/**
	 * A description about the format (localized)
	 */
	private String description;

}