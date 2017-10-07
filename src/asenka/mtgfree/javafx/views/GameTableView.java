package asenka.mtgfree.javafx.views;

import java.util.Observable;
import java.util.Observer;

import asenka.mtgfree.controllers.MtgGameTableController;
import javafx.scene.layout.BorderPane;


public class GameTableView extends BorderPane implements Observer {
	
	/**
	 * 
	 */
	private final MtgGameTableController controller;
	
	/**
	 * 
	 */
	private BattlefieldView battlefieldView;
	
	/**
	 * 
	 */
	private PlayerHandView playerHandView;
	
	/**
	 * 
	 */
	private SelectedCardView selectedCardView;
	
	

	/**
	 * 
	 * @param controller
	 */
	public GameTableView(MtgGameTableController controller) {
		this.controller = controller;
		this.controller.getGameTable().addObserver(this);
	}

	/**
	 * 
	 * @return
	 */
	public MtgGameTableController getController() {

		return controller;
	}

	@Override
	public void update(Observable o, Object arg) {

		// TODO Auto-generated method stub
		
	}
}
