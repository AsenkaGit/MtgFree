package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;

public class JFXTwoPlayersBattlefields extends SplitPane {
	
	private final JFXBattlefield localPlayerBattlefield;
	
	private final JFXBattlefield otherPlayerBattlefield;
	
	public JFXTwoPlayersBattlefields(final GameController controller) {
		
		super();
		this.getStyleClass().add("mtg-pane");
		this.localPlayerBattlefield = new JFXBattlefield(this, controller, true);
		this.otherPlayerBattlefield = new JFXBattlefield(this, controller, false);
		
		super.setOrientation(Orientation.VERTICAL);
		super.getItems().addAll(this.otherPlayerBattlefield, this.localPlayerBattlefield);
	}
}
