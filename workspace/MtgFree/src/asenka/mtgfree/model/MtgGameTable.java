package asenka.mtgfree.model;

import java.util.*;

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
	 * Default constructor
	 */
	public MtgGameTable() {
	}

	/**
	 * The maximum number of player on the game table.
	 * 
	 * For the moment one assume it will be only two players. But the model should allow to play with more than 2.
	 */
	private int maxPlayers;

	/**
	 * The cards on the battlefield for this game.
	 */
	private Set<MtgCard> cards;

	/**
	 * 
	 */
	private MtgPlayer players;


	/**
	 * @param player 
	 * @return
	 */
	public void addPlayer(MtgPlayer player) {
		// TODO implement here
	}

	/**
	 * @param player 
	 * @return
	 */
	public void removePlayer(MtgPlayer player) {
		// TODO implement here
	}

	/**
	 * @param card 
	 * @return
	 */
	public void addCardToBattlefield(MtgCard card) {
		// TODO implement here
	}

	/**
	 * @param card 
	 * @return
	 */
	public void removeCardFromBattlefield(MtgCard card) {
		// TODO implement here
	}

}