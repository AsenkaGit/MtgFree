package asenka.mtgfree.model.mtg;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;

/**
 * This class represents a game table
 * 
 * @author asenka
 */
public class MtgGameTable extends Observable {

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
	
	// TODO add the logs

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
	public void setMaxPlayers(int maxPlayers) {

		this.maxPlayers = maxPlayers;
	}

	/**
	 * @return
	 */
	public List<MtgPlayer> getPlayers() {

		return players;
	}

	/**
	 * @param players
	 */
	public void setPlayers(List<MtgPlayer> players) {

		this.players = players;
	}
	
	/**
	 * 
	 * @param player
	 */
	public void addPlayer(MtgPlayer player) {
		
		this.players.add(player);
		// TODO Add event
	}
	
	/**
	 * 
	 * @param player
	 */
	public void removePlayer(MtgPlayer player) {
		
		this.players.remove(player);
		// TODO add event
	}
 
	/**
	 * @return
	 */
	public List<MtgCard> getCardsOnBattlefield() {

		return cardsOnBattlefield;
	}

	/**
	 * Add card to the battle field
	 * 
	 * @param card
	 */
	public void addCardToBattleField(MtgCard card) {

		card.setContext(MtgContext.BATTLEFIELD);
		this.cardsOnBattlefield.add(card);
		// TODO Add event
	}

	/**
	 * Remove a card from the battlefield
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
