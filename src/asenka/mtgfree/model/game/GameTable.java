package asenka.mtgfree.model.game;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.events.network.NetworkEvent;

/**
 * Game object storing the game table with the players on the table
 * 
 * @author asenka
 * @see Player
 * @see PlayerController
 */
public class GameTable extends Observable {
	
	/**
	 * 
	 */
	private final String tableName;

	/**
	 * the player on this client side
	 */
	private final Player localPlayer;

	/**
	 * The local player controller
	 */
	private final PlayerController localPlayerController;

	/**
	 * A map associating the other players on the table to their controller
	 */
	private final Map<Player, PlayerController> otherPlayers;

	/**
	 * All the events occurring during a game
	 */
	private Deque<String> logs;

	/**
	 * Build a game table with two players
	 * 
	 * @param localPlayer the local player
	 * @param opponent the opponent using another client
	 */
	public GameTable(String tableName, Player localPlayer) {

		this.tableName = tableName;
		this.localPlayer = localPlayer;
		this.localPlayerController = new PlayerController(localPlayer, true);
		this.otherPlayers = new HashMap<Player, PlayerController>();
		this.logs = new LinkedList<String>();
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
	
		return this.tableName;
	}

	/**
	 * @param player the player to check
	 * @return <code>true</code> if <code>player</code> is equals to <code>localPlayer</code>
	 */
	public boolean isLocalPlayer(Player player) {

		return localPlayer.equals(player);
	}

	/**
	 * @return the local player
	 */
	public Player getLocalPlayer() {

		return localPlayer;
	}

	/**
	 * @return the local Player Controller
	 */
	public PlayerController getLocalPlayerController() {

		return localPlayerController;
	}

	/**
	 * @return a set with the other player on the table
	 */
	public Set<Player> getOtherPlayers() {

		return otherPlayers.keySet();
	}
	
	/**
	 * Add a player on the game table
	 * @param player the new player
	 */
	public void addOtherPlayer(final Player player) {
		
		this.otherPlayers.put(player, new PlayerController(player, false));
		super.setChanged();
		super.notifyObservers("addOtherPlayer");
	}
	
	/**
	 * Removes the player from the map of other players
	 * @param player the player to remove
	 * @return <code>true</code> if the player was in the map of players, <code>false</code> if he wasn't
	 */
	public boolean removeOtherPlayer(final Player player) {
		
		PlayerController result = this.otherPlayers.remove(player);
		
		// If 'player' was in the map of players...
		if(result != null) { 
			super.setChanged();
			super.notifyObservers("removeOtherPlayer");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param player the player
	 * @return the controller associated to the player
	 */
	public PlayerController getOtherPlayerController(Player player) {

		return otherPlayers.get(player);
	}

	/**
	 * @return the number of player on the table including the local player
	 */
	public int numberOfPlayers() {

		return (this.otherPlayers.size() + 1);
	}

	/**
	 * 
	 * @param event
	 */
	public void addLog(NetworkEvent event) {

		this.logs.addLast(event.toString());

		super.setChanged();
		super.notifyObservers("addLog");
	}

	/**
	 * 
	 * @return
	 */
	public String getLogs() {

		// TODO mettre en forme les logs
		
		String strLogs = "";
		for (String log : this.logs) {
			strLogs += log + "\n";
		}
		return strLogs;
	}
}
