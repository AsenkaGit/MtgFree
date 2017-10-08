package asenka.mtgfree.views.javafx;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import asenka.mtgfree.controllers.MtgGameTableController;
import asenka.mtgfree.model.mtg.events.SelectionUpdateEvent;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The JavaFX component used to display the selected card image and data
 * 
 * @author asenka
 *
 */
public class SelectedCardView extends VBox implements Observer {
	
	/**
	 * The game table controller that helps this to know what card is selected
	 */
	private final MtgGameTableController controller;
	
	/**
	 * The component displaying the selected card
	 */
	private SimpleCardView cardView;
	
	/**
	 * Text label with the selected card name
	 */
	private Label cardNameLabel;
	

	/**
	 * Constructor
	 * @param controller
	 */
	public SelectedCardView(MtgGameTableController controller) {
		super();
		this.controller = controller;
		this.controller.getGameTable().addObserver(this);
		
		// Initialize the card without any card
		this.cardView = new SimpleCardView(AbstractMtgCardView.LARGE_CARD_SIZES, null);
		this.cardNameLabel = new Label("No card selected");
		
		// Add the image view to the component children
		super.getChildren().addAll(this.cardView, this.cardNameLabel);
	}

	@Override
	public void update(Observable observedGameTable, Object event) {

		// This component only reacts to a Selection update event.
		if(event instanceof SelectionUpdateEvent) {
			
			// Get the first selected card from the list
			List<MtgCard> selectedCards = ((SelectionUpdateEvent) event).getSelectedCards();
			this.cardView.setCardDisplayed(selectedCards.get(0));
			this.cardNameLabel.setText(selectedCards.get(0).getName());
		}
	}
}
