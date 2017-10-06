package asenka.mtgfree.javafx.views;


import java.util.Locale;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;
import asenka.mtgfree.model.mtg.mtgcard.MtgRarity;
import asenka.mtgfree.model.mtg.mtgcard.MtgType;
import asenka.mtgfree.javafx.controllers.MtgCardController;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class BattlefieldView extends Pane {
	
//	private MtgGameTableController gameTableController;
//	
//	private MtgPlayerController playerController;
	
	public BattlefieldView() {
		
		MtgCard card = new MtgCard(1, "Black Lotus", "Alpha", "0", new MtgType(1, "Artefact", Locale.ENGLISH), MtgRarity.MYTHIC, Locale.ENGLISH);
		card.setVisible(true);
		card.setContext(MtgContext.BATTLEFIELD);
		
		MtgCardController cardController = new MtgCardController(card);
		SimpleBattlefieldMtgCardView battleCardView = new SimpleBattlefieldMtgCardView(cardController);
		
		getChildren().add(battleCardView);
	}

}
