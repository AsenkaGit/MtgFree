package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;

/**
 * This components display two JFXBattlefield in a vertical split pane.
 * 
 * @author asenka
 * @see SplitPane
 * @see JFXBattlefield
 */
public class JFXTwoPlayersBattlefields extends SplitPane {
	
	/**
	 * The battlefield displaying the cards of the local player
	 */
	private final JFXBattlefield localPlayerBattlefield;
	
	/**
	 * The battlefield displaying the cards of the opponent
	 */
	private final JFXBattlefield otherPlayerBattlefield;
	
	/**
	 * Build a vertical split pane with two JFXBattlefield
	 * @param controller the game controller
	 * @see JFXBattlefield
	 */
	public JFXTwoPlayersBattlefields(final GameController controller) {
		
		super();
		this.getStyleClass().add("mtg-pane");
		this.localPlayerBattlefield = new JFXBattlefield(controller, true);
		this.otherPlayerBattlefield = new JFXBattlefield(controller, false);
		
		super.setOrientation(Orientation.VERTICAL);
		super.getItems().addAll(this.otherPlayerBattlefield, this.localPlayerBattlefield);
	}
}
