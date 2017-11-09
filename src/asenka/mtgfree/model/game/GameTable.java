package asenka.mtgfree.model.game;

import java.util.Deque;
import java.util.LinkedList;

import asenka.mtgfree.controllers.game.PlayerController;
import asenka.mtgfree.events.AbstractEvent;
import asenka.mtgfree.events.LocalEvent;

import static asenka.mtgfree.events.LocalEvent.Type.*;

/**
 * Game object storing the game table with the players on the table
 * 
 * @author asenka
 * @see Player
 * @see PlayerController
 */
public class GameTable extends AbstractGameObject {

	/**
	 * The generated id for serialization
	 */
	private static final long serialVersionUID = -2427880626586409302L;

	/**
	 * The name of this table
	 */
	private final String name;

	/**
	 * the player on this client side
	 */
	private Player localPlayer;

	/**
	 * The opponent player
	 */
	private Player opponentPlayer;

	/**
	 * List of events logged on this game table
	 */
	private Deque<AbstractEvent> logs;

	/**
	 * Build a game table with two players
	 * 
	 * @param localPlayer the local player
	 * @param opponent the opponent using another client
	 */
	public GameTable(String tableName, Player localPlayer) {

		this.name = tableName;
		this.localPlayer = localPlayer;
		this.logs = new LinkedList<AbstractEvent>();
	}

	/**
	 * @return the name of this game table
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * @param player the player to check
	 * @return <code>true</code> if <code>player</code> is equals to <code>localPlayer</code>
	 */
	public boolean isLocalPlayer(Player player) {

		if (player == null) {
			throw new IllegalArgumentException("null value is not allowed for this method.");
		} else {
			return this.localPlayer.equals(player);
		}
	}

	/**
	 * @return the local player
	 */
	public Player getLocalPlayer() {

		return this.localPlayer;
	}

	/**
	 * Set the local player.
	 * 
	 * @param player the player that should be the local player
	 */
	public void setLocalPlayer(Player player) {

		this.localPlayer = player;
	}


	/**
	 * @return a set with the other player on the table
	 */
	public Player getOpponentPlayer() {

		return this.opponentPlayer;
	}
	
	/**
	 * 
	 * @param player
	 */
	public void setOpponentPlayer(Player player) {
		
		this.opponentPlayer = player;
	}

	/**
	 * @return the number of player on the table including the local player
	 */
	public int numberOfPlayers() {

		int numberOfPlayer = 0;
		if (this.localPlayer != null) numberOfPlayer++;
		if (this.opponentPlayer != null) numberOfPlayer++;
		return numberOfPlayer;
	}

	/**
	 * Add a log to this game table
	 * 
	 * @param event an event
	 */
	public void addLog(AbstractEvent event) {

		// TODO revoir cette implementation non terminée
		this.logs.addLast(event);

		super.setChanged();
		super.notifyObservers(new LocalEvent(NEW_GAMETABLE_LOG, event));
	}

	/**
	 * 
	 * @return
	 */
	public String getStringLogs() {

		// TODO mettre en forme les logs

		String strLogs = "";
		for (AbstractEvent log : this.logs) {
			strLogs += log.toString().replace("NetworkEvent", "") + "\n";
		}
		return strLogs;
	}
}
