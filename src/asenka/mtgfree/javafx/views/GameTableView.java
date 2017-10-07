package asenka.mtgfree.javafx.views;

import asenka.mtgfree.controllers.MtgGameTableController;
import javafx.scene.layout.BorderPane;


public class GameTableView extends BorderPane {
	
	/**
	 * 
	 */
	private final MtgGameTableController controller;

	/**
	 * 
	 * @param controller
	 */
	public GameTableView(MtgGameTableController controller) {
		this.controller = controller;
	}

	/**
	 * 
	 * @return
	 */
	public MtgGameTableController getController() {

		return controller;
	}


}
