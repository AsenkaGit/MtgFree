package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.model.Card;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class JFXLibraryView extends ListView<Card> {
	
	private final GameController gameController;

	private final ObservableList<Card> libraryCards;
	
	public JFXLibraryView(final GameController controller) {
		
		this.gameController = controller;
		this.libraryCards = controller.getGameTable().getLocalPlayer().getLibrary();
		super.setCellFactory(cellData -> new CardCell());
		super.getItems().addAll(this.libraryCards);
	}
	
	
	
	private class CardCell extends ListCell<Card> {

		public CardCell() {
			
			setAlignment(Pos.CENTER);
		}
		
		@Override
		protected void updateItem(final Card item, boolean empty) {
			
			super.updateItem(item, empty);
			super.setPickOnBounds(false);
			if (empty || item == null) {
				setGraphic(null);
			} else {
				setGraphic(new JFXCardView(item, CardImageSize.MEDIUM));
			}
		}
	}
}
