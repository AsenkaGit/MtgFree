package asenka.mtgfree.model.game;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.model.events.AbstractEvent;

/**
 * Game object storing the game table with the players on the table
 * 
 * @author asenka
 * @see Player
 * @see PlayerController
 */
public class GameTable extends Observable {

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
	 * @param localPlayer the local player
	 * @param opponent the opponent using another client
	 */
	public GameTable(Player localPlayer, Player opponent) {

		if (localPlayer == null || opponent == null || localPlayer.equals(opponent)) {
			throw new RuntimeException("Wrong initialization of the game table");
		} else {
			this.localPlayer = localPlayer;
			this.localPlayerController = new PlayerController(localPlayer, true);
			this.otherPlayers = new HashMap<Player, PlayerController>();
			this.otherPlayers.put(opponent, new PlayerController(opponent, false));
			this.logs = new LinkedList<String>();
		}
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
	public void addLog(AbstractEvent event) {
		
		this.logs.addLast(event.toString());
		
		super.setChanged();
		super.notifyObservers();
	}

	/**
	 * 
	 * @return
	 */
	public String getLogs() {

		String strLogs = "";
		for(String log : this.logs) {
			strLogs += log + "\n";
		}
		return strLogs;
	}
}
