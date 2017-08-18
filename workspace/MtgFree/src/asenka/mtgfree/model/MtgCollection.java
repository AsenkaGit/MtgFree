package asenka.mtgfree.model;


import java.util.*;

/**
 * The magic cards are gathered by collection (Kaladesh, Amonketh, Tempest, etc...). 
 * 
 * Every cards belongs to a collection. If a collection is removed, it means all the cards related to this collection are supposed to be removed too.
 * @author Asenka
 */
public class MtgCollection {

	// ##########################################################
	// #
	// #				Attributes
	// #							
	// ##########################################################

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
	private String language;

	/**
	 * 
	 */
	private List<MtgCard> cards;
	
	// ##########################################################
	// #
	// #				Constructors
	// #							
	// ##########################################################
	
	

	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param block
	 */
	public MtgCollection(int id, String name, String description, String block) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.block = block;
		this.cards = new ArrayList<MtgCard>();
	}

	
	// ##########################################################
	// #
	// #				Methods
	// #							
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
	 * @return the block
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * @param block the block to set
	 */
	public void setBlock(String block) {
		this.block = block;
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


	/**
	 * @return an ArrayList of MtgCards
	 */
	public List<MtgCard> getCards() {
		return cards;
	}

	/**
	 * 
	 * @param card
	 */
	public void addCard(MtgCard card) {
		this.cards.add(card);
	}
	
	/**
	 * 
	 * @param card
	 */
	public void removeCard(MtgCard card) {
		this.cards.remove(card);
	}
	
	/**
	 * 
	 */
	public void clear() {
		this.cards.clear();
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.cards.size();
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MtgCollection [" + id + ", " + name + ", " + description + ", " + block + ", " + language + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + id;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MtgCollection other = (MtgCollection) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (id != other.id)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}