package asenka.mtgfree.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class stores the data about a player.
 * @author Asenka
 */
public class MtgPlayer {
	

	/**
	 * The username of the player. This username should be unique on the server because it is used as an ID.
	 */
	private String username;

	/**
	 * The user password. This data should never be stored in the model without encryption.
	 */
	private String password;

	/**
	 * The current number of life of the player.
	 */
	private int remainingLife;

	/**
	 * The poison counter on the player.
	 */
	private int poisonCounters;

	/**
	 * The energy counter of the player.
	 */
	private int energyCounters;

	/**
	 * A free text to express any status of the player. It can be used to manage any unusual stats. The player can write here anything he/she wants or needs.
	 */
	private String battlefieldComment;
	
	/**
	 * 
	 */
	private MtgDeck currentDeck;

	/**
	 * The list of deck owned by this player.
	 */
	private ArrayList<MtgDeck> decks;

	/**
	 * The selected cards of the player. They are some rules about cards selection :
	 * > it can be empty or contains several cards
	 * > if more than one cards selected : they should ALL belong to the same context (hand, battlefield, graveyard or exile)
	 */
	private ArrayList<MtgCard> selectedCards;

	/**
	 * 
	 */
	private ArrayList<MtgCard> graveyard;

	/**
	 * 
	 */
	private ArrayList<MtgCard> hand;

	/**
	 * 
	 */
	private ArrayList<MtgCard> exile;
	
	/**
	 * 
	 */
	private MtgLibrary library;
	
	
	/**
	 * 
	 * @param username
	 * @param password
	 */
	public MtgPlayer(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.remainingLife = 20;
		this.poisonCounters = 0;
		this.energyCounters = 0;
		this.battlefieldComment = "";
		this.decks = null;
		this.currentDeck = null;
		this.library = null;
		this.selectedCards = new ArrayList<MtgCard>(3);
		this.graveyard = null;
		this.exile = null;
		this.hand = null;
	}


	/**
	 * @return the user name
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the user name to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the remainingLife
	 */
	public int getRemainingLife() {
		return remainingLife;
	}


	/**
	 * @param remainingLife the remainingLife to set
	 */
	public void setRemainingLife(int remainingLife) {
		this.remainingLife = remainingLife;
	}


	/**
	 * @return the poisonCounters
	 */
	public int getPoisonCounters() {
		return poisonCounters;
	}


	/**
	 * @param poisonCounters the poisonCounters to set
	 */
	public void setPoisonCounters(int poisonCounters) {
		this.poisonCounters = poisonCounters;
	}


	/**
	 * @return the energyCounters
	 */
	public int getEnergyCounters() {
		return energyCounters;
	}


	/**
	 * @param energyCounters the energyCounters to set
	 */
	public void setEnergyCounters(int energyCounters) {
		this.energyCounters = energyCounters;
	}


	/**
	 * @return the battlefieldComment
	 */
	public String getBattlefieldComment() {
		return battlefieldComment;
	}


	/**
	 * @param battlefieldComment the battlefieldComment to set
	 */
	public void setBattlefieldComment(String battlefieldComment) {
		this.battlefieldComment = battlefieldComment;
	}


	/**
	 * @return the library
	 */
	public MtgLibrary getLibrary() {
		return library;
	}


	/**
	 * @param library the library to set
	 */
	public void setLibrary(MtgLibrary library) {
		this.library = library;
	}


	/**
	 * @return the currentDeck
	 */
	public MtgDeck getCurrentDeck() {
		return currentDeck;
	}


	/**
	 * @param currentDeck the currentDeck to set
	 */
	public void setCurrentDeck(MtgDeck currentDeck) {
		this.currentDeck = currentDeck;
	}


	/**
	 * @return the decks
	 */
	public ArrayList<MtgDeck> getDecks() {
		return decks;
	}


	/**
	 * @param decks the decks to set
	 */
	public void setDecks(Collection<MtgDeck> decks) {
		this.decks = new ArrayList<MtgDeck>(decks);
	}


	/**
	 * @return the selectedCards
	 */
	public ArrayList<MtgCard> getSelectedCards() {
		return selectedCards;
	}


	/**
	 * @param selectedCards the selectedCards to set
	 */
	public void setSelectedCards(Collection<MtgCard> selectedCards) {
		this.selectedCards = new ArrayList<MtgCard>(selectedCards);
	}


	/**
	 * @return the graveyard
	 */
	public ArrayList<MtgCard> getGraveyard() {
		return graveyard;
	}



	/**
	 * @return the exile
	 */
	public ArrayList<MtgCard> getExile() {
		return exile;
	}


	/**
	 * @return the hand
	 */
	public ArrayList<MtgCard> getHand() {
		return hand;
	}


	/**
	 * 
	 * @param context
	 * @param card
	 */
	public void addCard(MtgContext context, MtgCard card) {
		
		if(card == null) {
			throw new IllegalArgumentException("Try to add null value into a list of cards");
		} else {
			
			switch(context) {
			case EXILE: this.exile.add(card);
				break;
			case GRAVEYARD: this.graveyard.add(card);
				break; 
			case HAND: this.hand.add(card);
				break;
			default: 
				throw new IllegalArgumentException(context.name() + " is not allowed by this method");
			}
		}
	}

	/**
	 * 
	 * @param context
	 * @param card
	 */
	public void removeCard(MtgContext context, MtgCard card) {

		switch(context) {
		case EXILE: this.exile.remove(card);
		break;
		case GRAVEYARD: this.graveyard.remove(card);
		break; 
		case HAND: this.hand.remove(card);
		break;
		default: 
			throw new IllegalArgumentException(context.name() + " is not allowed by this method");
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + username + ", Life=" + remainingLife
				+ ", Poison=" + poisonCounters + ", Energy=" + energyCounters + ", "
				+ battlefieldComment + ", " + currentDeck + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((battlefieldComment == null) ? 0 : battlefieldComment.hashCode());
		result = prime * result + ((currentDeck == null) ? 0 : currentDeck.hashCode());
		result = prime * result + energyCounters;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + poisonCounters;
		result = prime * result + remainingLife;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		MtgPlayer other = (MtgPlayer) obj;
		if (battlefieldComment == null) {
			if (other.battlefieldComment != null)
				return false;
		} else if (!battlefieldComment.equals(other.battlefieldComment))
			return false;
		if (currentDeck == null) {
			if (other.currentDeck != null)
				return false;
		} else if (!currentDeck.equals(other.currentDeck))
			return false;
		if (energyCounters != other.energyCounters)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (poisonCounters != other.poisonCounters)
			return false;
		if (remainingLife != other.remainingLife)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}