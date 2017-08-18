package asenka.mtgfree.model;


import java.util.*;

/**
 * A deck is a list if cards that  a player use during a Mtg game. The main cards in the deck will be used as the player library. the side cards are just here to be remembered as good candidates for the main deck.
 * @author Asenka
 */
public class MtgDeck {

	// ##########################################################
	// #														
	// #				Enumerations							
	// #														
	// ##########################################################

	/**
	 * This enumeration is useful inside the MtgDeck class to indicate the targeted list to perform an operation :
	 * > the main cards in the deck
	 * > the sideboard
	 */
	public enum MtgDeckList { MAIN, SIDEBOARD }
	
	// ##########################################################
	// #														
	// #				Attributes							
	// #														
	// ##########################################################
	
	/**
	 * The id of the deck (from the database)
	 */
	private int id;

	/**
	 * Each player should give a name to its deck.
	 */
	private String name;

	/**
	 * The description is not mandatory, but it is a small text explaining the deck.
	 */
	private String description;

	/**
	 * The cards meant to go to the player library during a Mtg game.
	 */
	private List<MtgCard> mainCards;

	/**
	 * The cards that will not be played, but it is nice to have them remembered because they may work with this deck.
	 */
	private List<MtgCard> sideCards;

	/**
	 * The owner of the deck.
	 */
	private MtgPlayer owner;
	
	// ##########################################################
	// #														
	// #				Constructors							
	// #														
	// ##########################################################
	
	/**
	 * Default constructor
	 */
	private MtgDeck() {
		super();
		this.mainCards = new ArrayList<MtgCard>();
		this.sideCards = new ArrayList<MtgCard>();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param owner
	 */
	public MtgDeck(int id, String name, String description, MtgPlayer owner) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
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
	 * @return the owner
	 */
	public MtgPlayer getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(MtgPlayer owner) {
		this.owner = owner;
	}

	/**
	 * @return the mainCards
	 */
	public List<MtgCard> getMainCards() {
		return mainCards;
	}

	/**
	 * @return the sideCards
	 */
	public List<MtgCard> getSideCards() {
		return sideCards;
	}

	
	/**
	 * @param list 
	 * @param card 
	 * @return
	 */
	public void addCard(MtgDeckList list, MtgCard card) {
		
		if(list == MtgDeckList.MAIN) { 
			this.mainCards.add(card);
		} else { // SIDEBOARD
			this.sideCards.add(card);
		}
	}

	/**
	 * @param list 
	 * @param card 
	 * @return
	 */
	public void removeCard(MtgDeckList list, MtgCard card) {
		
		if(list == MtgDeckList.MAIN) { 
			this.mainCards.remove(card);
		} else { // SIDEBOARD
			this.sideCards.remove(card);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MtgDeck [" + id + ", " + name + ", " + description + ", " + owner + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		MtgDeck other = (MtgDeck) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	
	

}