package asenka.mtgfree.javafx.views;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import asenka.mtgfree.controllers.MtgGameTableController;
import asenka.mtgfree.model.mtg.events.SelectionUpdateEvent;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.utilities.FileManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class SelectedCardView extends VBox implements Observer {
	
	protected static final FileManager FILES_MANAGER = FileManager.getInstance();
	
	private final MtgGameTableController controller;
	
	private ImageView cardFrontImage;
	
	

	/**
	 * 
	 * @param controller
	 */
	public SelectedCardView(MtgGameTableController controller) {
		super();
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

	/**
	 * 
	 * @param firstSelectedCard
	 */
	private void setSelectedCardViewDisplay(final MtgCard firstSelectedCard) {
		
		this.cardFrontImage = new ImageView(new Image(FILES_MANAGER.getCardImageInputStream(firstSelectedCard)));
	}

	@Override
	public void update(Observable observedGameTable, Object event) {

		if(event instanceof SelectionUpdateEvent) {
			
			List<MtgCard> selectedCards = ((SelectionUpdateEvent) event).getSelectedCards();
			setSelectedCardViewDisplay(selectedCards.get(0));
		}
	}
}
