package asenka.mtgfree.model.mtg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;

/**
 * This class represents a game table
 * 
 * @author asenka
 */
public class MtgGameTable extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5283884575139799352L;

	/**
	 * The table name
	 */
	private String name;

	/**
	 * The maximum number on player on the table
	 */
	private int maxPlayers;

	/**
	 * The players on the table
	 */
	private List<MtgPlayer> players;

	/**
	 * The list of cards on the battlefield
	 */
	private List<MtgCard> cardsOnBattlefield;


	/**
	 * Constructor
	 * 
	 * @param name
	 * @param player1
	 * @param player2
	 */
	public MtgGameTable(String name, MtgPlayer player1, MtgPlayer player2) {

		super();
		this.name = name;
		this.players = new ArrayList<MtgPlayer>(2);
		this.players.add(player1);
		this.players.add(player2);
		this.maxPlayers = 2;
		this.cardsOnBattlefield = new ArrayList<MtgCard>();
	}

	/**
	 * @return
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return the maxPlayers
	 */
	public int getMaxPlayers() {

		return maxPlayers;
	}

	/**
	 * @param maxPlayers the maxPlayers to set
	 */
	public void setMaxPlayers(final int maxPlayers) {

		this.maxPlayers = maxPlayers;
	}

	/**
	 * @return the players on the table
	 */
	public List<MtgPlayer> getPlayers() {

		return players;
	}

	/**
	 * Set the list of players
	 * @param players
	 */
	public void setPlayers(List<MtgPlayer> players) {

		this.players = players;
	}
	
	/**
	 * Add a new player on the table
	 * @param player
	 */
	public void addPlayer(MtgPlayer player) {
		
		this.players.add(player);
	}
	
	/**
	 * Remove a player from the table
	 * @param player
	 */
	public void removePlayer(MtgPlayer player) {
		
		this.players.remove(player);
	}
 
	/**
	 * Returns the cards that are on the battlefield
	 * @return an unmodifiable list of cards
	 */
	public List<MtgCard> getCardsOnBattlefield() {

		return Collections.unmodifiableList(this.cardsOnBattlefield);
	}

	/**
	 * Add card to the battle field and notify the observers
	 * 
	 * @param card
	 */
	public void addCardToBattleField(MtgCard card) {

		card.setContext(MtgContext.BATTLEFIELD);
		this.cardsOnBattlefield.add(card);
	}

	/**
	 * Remove a card from the battlefield and notify the observers
	 * 
	 * @param card the card to remove
	 */
	public void removeCardFromBattlefield(MtgCard card) {

		this.cardsOnBattlefield.remove(card);
	}

	@Override
	public String toString() {

		return "[" + name + ", " + maxPlayers + ", " + players + ", " + cardsOnBattlefield + "]";
	}
}
