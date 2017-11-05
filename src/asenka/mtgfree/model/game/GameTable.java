package asenka.mtgfree.model.game;

import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.events.EventType;
import asenka.mtgfree.events.LocalEvent;
import asenka.mtgfree.events.NetworkEvent;

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

		this.name = tableName;
		this.localPlayer = localPlayer;
		this.localPlayerController = new PlayerController(localPlayer, true);
		this.otherPlayers = new HashMap<Player, PlayerController>();
		this.logs = new LinkedList<String>();
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

		return this.localPlayer.equals(player);
	}

	/**
	 * @return the local player
	 */
	public Player getLocalPlayer() {

		return this.localPlayer;
	}

	/**
	 * @return the local Player Controller
	 */
	public PlayerController getLocalPlayerController() {

		return this.localPlayerController;
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
		super.setChanged();
		super.notifyObservers(new LocalEvent(EventType.PLAYER_JOIN, newPlayer));
	}
	
	/**
	 * Removes the player from the map of other players
	 * @param playerLeaving the player to remove
	 * @return <code>true</code> if the player leaving the table was in the map of players, <code>false</code> if he wasn't
	 */
	public boolean removeOtherPlayer(final Player playerLeaving) {
		
		PlayerController result = this.otherPlayers.remove(playerLeaving);
		
		// If 'playerLeaving' was in the map of players...
		if(result != null) { 
			super.setChanged();
			super.notifyObservers(new LocalEvent(EventType.PLAYER_LEAVE, playerLeaving));
			return true;
		} else {
			return false;
		}
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
	 * 
	 * @param event
	 */
	public void addLog(NetworkEvent event) {

		this.logs.addLast(event.toString());

		super.setChanged();
		super.notifyObservers(new LocalEvent(EventType.UPDATE_GAME_LOGS, event));
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
