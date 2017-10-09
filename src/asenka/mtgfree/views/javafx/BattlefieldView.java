package asenka.mtgfree.views.javafx;


import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import asenka.mtgfree.controllers.MtgCardController;
import asenka.mtgfree.controllers.MtgGameTableController;
import asenka.mtgfree.model.mtg.events.MtgGameTableAddCardEvent;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BattlefieldView extends Pane implements Observer {
	
	private MtgGameTableController gameTableController;
//	
//	private MtgPlayerController playerController;
	
	/**
	 * 
	 * @param gameTableController
	 */
	public BattlefieldView(MtgGameTableController gameTableController) {
		this.gameTableController = gameTableController;
		this.gameTableController.getGameTable().addObserver(this);
		
		this.setOnMouseClicked((event) -> {
			Node pick = event.getPickResult().getIntersectedNode();
			
			if (pick instanceof ImageView) {
				
				SimpleBattlefieldCardView cardView = (SimpleBattlefieldCardView) pick.getParent();
				MtgCard card = cardView.getCard();
				
				this.gameTableController.setSelectedCards(Collections.singletonList(card));
			}
		});
		
	}

	@Override
	public void update(Observable observedObject, Object event) {
		
		if(event instanceof MtgGameTableAddCardEvent) {
			
			MtgCard card = ((MtgGameTableAddCardEvent) event).getCard();
			
			SimpleBattlefieldCardView cardView = new SimpleBattlefieldCardView(new MtgCardController(card));
			
			this.getChildren().add(cardView);
		}
		
	}

}
