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
		this.battlefieldView = new BattlefieldView(controller);
		this.playerHandView = new PlayerHandView();
		

		setRight(this.selectedCardView);
		setCenter(this.battlefieldView);
		setBottom(this.playerHandView);
	}

	@Override
	public void update(Observable observedObject, Object event) {

	}
}
