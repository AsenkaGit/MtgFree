package asenka.mtgfree.model;


import java.util.*;

/**
 * A type of magic cards. They are several types of cards. But a type can be decomposed into global type (creature, instant, artefact, etc...). But it can be detailed (Creature : elf, artefact : vehicule, etc...)
 * @author Asenka
 */
public class MtgType {

	/**
	 * Default constructor
	 */
	public MtgType() {
	}

	/**
	 * The unique id of a type (based on the ID in the database)
	 */
	private int id;

	/**
	 * This attribute stores the basic type for a card (localized):
	 * > Creature
	 * > Enchantment
	 * > Instant
	 * > Sorcery
	 * > Land
	 * > Artefact
	 * > Creature-Artefact
	 * > Planeswalker
	 * > Tribal
	 * > ...
	 */
	private String type;

	/**
	 * The detailed type contains exactly what taht's written on the card (localized):
	 * > Creature : human and pilot
	 * > Artefact : Vehicule
	 * > ...
	 * 
	 * It may be the same as the general type (for the Sorcery and Instant for example).
	 */
	private String detailedType;

	/**
	 * This field give detailed about a type (localized)
	 */
	private String description;

}