package asenka.mtgfree.model;

import java.util.*;

import asenka.mtgfree.model.exceptions.MtgGameException;

/**
 * This class represents a game table. i.e. the virtual place where the game is played.
 * 
 * This is the entry point for all the data related to a Mtg game :
 * > Players (leading to players data)
 * > The cards on the battlefield,
 * > the game logs,
 * > ...
 * @author Asenka
 */
public class MtgGameTable {


	/**
	 * The maximum number of player on the game table.
	*/
	private int maxPlayers;

	/**
	 * The cards on the battlefield for this game.
	 */
	private ArrayList<MtgCard> cards;

	/**
	 * 
	 */
	private ArrayList<MtgPlayer> players;
	
	
	

	/**
	 * 
	 */
	public MtgGameTable() {
		super();
		this.maxPlayers = 2; // TODO this value should be managed by a configuration tool
	}
	
	

	public ArrayList<MtgPlayer> getPlayers() {
		return players;
	}

	/**
	 * @param player 
	 * @return
	 * @throws MtgGameException 
	 */
	public void addPlayer(MtgPlayer player) throws MtgGameException {
		
		if(this.players.size() < maxPlayers) {
			this.players.add(player);
		} else {
			throw new MtgGameException("This table has already reached the maximum number of players");
		}
	}

	/**
	 * @param player 
	 * @return
	 */
	public boolean removePlayer(MtgPlayer player) {
		return this.players.remove(player);
	}

	/**
	 * @param card 
	 * @return
	 */
	public void addCardToBattlefield(MtgCard card) {
		this.cards.add(card);
	}

	/**
	 * @param card 
	 * @return
	 */
	public boolean removeCardFromBattlefield(MtgCard card) {
		return this.cards.remove(card);
	}

}