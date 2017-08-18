package asenka.mtgfree.model;


/**
 * This class represents a card ability (fly, deathtouch, hexproof, etc...)
 * 
 * 
 * @author Asenka
 */
public class MtgAbility {

	// ##########################################################
	// #														#
	// #				Class parameters						#
	// #														#
	// ##########################################################
	
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
	
	/**
	 * 
	 */
	private String language;
	
	
	// ##########################################################
	// #														#
	// #				Constructors							#
	// #														#
	// ##########################################################
	
	/**
	 * Default constructor
	 */
	public MtgAbility() {
		
	}
	
	

	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 */
	public MtgAbility(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}


	// ##########################################################
	// #														#
	// #					Methods	(public)					#
	// #														#
	// ##########################################################

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	} 


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}



	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
}