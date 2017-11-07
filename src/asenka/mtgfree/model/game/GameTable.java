package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

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
public class GameTable extends Observable implements Serializable {
	
	/**
	 * The generated id for serialization
	 */
	private static final long serialVersionUID = -3191823675635881810L;

	/**
	 * The name of this table
	 */
	private final String name;

	/**
	 * the player on this client side
	 */
	private Player localPlayer;

	/**
	 * The local player controller
	 */
	private PlayerController localPlayerController;

	/**
	 * A map associating the other players on the table to their controller
	 */
	private Map<Player, PlayerController> otherPlayers;

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
		this.localPlayerController = new PlayerController(localPlayer, true);
		this.otherPlayers = new HashMap<Player, PlayerController>();
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

		if(player == null) {
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
	 * @param player the player that should be the local player
	 */
	public void setLocalPlayer(Player player) {
		
		this.localPlayer = player;
	}

	/**
	 * @return the local Player Controller
	 */
	public PlayerController getLocalPlayerController() {

		return this.localPlayerController;
	}
	
	/**
	 * Update the player controller and the local player
	 * @param playerController
	 */
	public void setLocalPlayerController(PlayerController playerController) {
		
		this.localPlayerController = playerController;
		this.setLocalPlayer(playerController.getData());
	}

	/**
	 * @return a set with the other player on the table
	 */
	public Set<Player> getOtherPlayers() {

		return this.otherPlayers.keySet();
	}
	
	/**
	 * Add a player on the game table
	 * @param newPlayer the new player
	 */
	public void addOtherPlayer(final Player newPlayer) {
		
		this.otherPlayers.put(newPlayer, new PlayerController(newPlayer, false));
	}
	
	/**
	 * Removes the player from the map of other players
	 * @param playerLeaving the player to remove
	 * @return <code>true</code> if the player leaving the table was in the map of players, <code>false</code> if he wasn't
	 */
	public boolean removeOtherPlayer(final Player playerLeaving) {
		
		return this.otherPlayers.remove(playerLeaving) != null;
	}

	/**
	 * @param otherPlayer the player
	 * @return the controller associated to the player
	 */
	public PlayerController getOtherPlayerController(Player otherPlayer) {

		return otherPlayers.get(otherPlayer);
	}

	/**
	 * @return the number of player on the table including the local player
	 */
	public int numberOfPlayers() {

		return (this.otherPlayers.size() + 1);
	}

	/**
	 * Add a log to this game table
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
			strLogs += log.toString() + "\n";
		}
		return strLogs;
	}
}
