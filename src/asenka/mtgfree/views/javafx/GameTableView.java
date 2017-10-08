package asenka.mtgfree.views.javafx;

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
		this.selectedCardView = new SelectedCardView(controller);
		
		setRight(this.selectedCardView);
	}

	@Override
	public void update(Observable observedObject, Object event) {

		// TODO Auto-generated method stub
		
	}
}
