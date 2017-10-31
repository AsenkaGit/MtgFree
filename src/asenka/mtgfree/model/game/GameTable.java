package asenka.mtgfree.model.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import asenka.mtgfree.controlers.game.PlayerController;

/**
 * Game object storing the game table with the players on the table
 * 
 * @author asenka
 * @see Player
 * @see PlayerController
 */
public class GameTable {

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
	 * 
	 * @param localPlayer
	 * @param opponent
	 */
	public GameTable(Player localPlayer, Player opponent) {

		this.localPlayer = localPlayer;
		this.localPlayerController = new PlayerController(localPlayer, true);
		this.otherPlayers = new HashMap<Player, PlayerController>();
		this.otherPlayers.put(opponent, new PlayerController(opponent, false));
	}

	/**
	 * 
	 * @param player
	 * @return
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
}
