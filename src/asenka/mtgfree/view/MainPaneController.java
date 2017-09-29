package asenka.mtgfree.view;

import java.util.Locale;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
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

		battlefield.getChildren().add(new CardView(card));
		
		this.battlefield.setOnMousePressed((event) -> {

			
		});

	}


}
