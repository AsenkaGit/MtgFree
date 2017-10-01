package asenka.mtgfree.view;

import java.util.Locale;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;
import asenka.mtgfree.model.mtg.mtgcard.MtgRarity;
import asenka.mtgfree.model.mtg.mtgcard.MtgType;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainPaneController {
	
	@FXML
	private Pane battlefield;
	
	
	@FXML
	private void initialize() {
		
		MtgCard card = new MtgCard(1, "Black Lotus", "Alpha", "0", new MtgType(1, "Artefact", Locale.ENGLISH), MtgRarity.MYTHIC, Locale.ENGLISH);
		card.setVisible(true);
		card.setContext(MtgContext.BATTLEFIELD);
		
		MtgCardController cardController = new MtgCardController(card);
		MtgCardController cardController2 = new MtgCardController(card);
		MtgCardView cardView = new MtgCardView(cardController);
		MtgCardView cardView2 = new MtgCardView(cardController2);
		
		
		battlefield.getChildren().addAll(cardView, cardView2);


	}


}
