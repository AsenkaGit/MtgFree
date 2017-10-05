package asenka.mtgfree.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RootPaneController {

	@FXML
	private Button drawButton;
	
	@FXML
	private Button drawPlay;
	
	@FXML
	private BattlefieldView battlefieldView;
	
	private MtgGameTableController gameTableController;
	
	
	/**
	 * Method automatically called when the RootPane is loaded
	 */
	@FXML
	private void initialize() {
		
		this.gameTableController = new MtgGameTableController();
	}
	
	@FXML
	private void drawCard() {
		System.out.println("DRAW");
	}
	
	@FXML
	private void playCard() {
		System.out.println("PLAY");
	}
}

