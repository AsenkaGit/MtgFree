package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.views.JFXCardView.Side;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

/**
 * This component displays the first card in the list of selected cards.
 * 
 * @author asenka
 * @see StackPane
 */
public class JFXSelectedCardInfoPane extends StackPane { // The StackPane reacts better to the size limitations than the basic
															// Pane

	/**
	 * This component adds a listener on this list to update itself each time the list is changed.
	 */
	private final ObservableList<Card> selectedCards;

	/**
	 * The card view used to display an image of the card.
	 */
	private final JFXCardView cardView;

	/**
	 * The special text component used to display the mana cost of the card (with symbols)
	 */
	private final JFXMagicText cardCostText;

	/**
	 * The special text component used to display the card rules text (with symbols)
	 */
	private final JFXMagicText cardRulesText;

	/**
	 * The regular JavaFX text component for the card name
	 */
	private final Text cardNameText;

	/**
	 * The regular JavaFX text component for the card type
	 */
	private final Text cardTypeText;

	/**
	 * The regular JavaFX text component for the card flavor text
	 */
	private final Text cardFlavorText;

	/**
	 * The current side displayed
	 * 
	 * @see JFXCardView
	 * @see Side
	 */
	private Side currentSide;

	/**
	 * The current card displayed by the component (may be <code>null</code>)
	 */
	private Card currentCard;

	/**
	 * Build a component able to display the selected card
	 * 
	 * @param controller the observable list of cards to "listen"
	 */
	public JFXSelectedCardInfoPane(final GameController controller) {

		super();
		this.getStyleClass().add("mtg-pane");
		this.getStyleClass().add("JFXSelectedCardInfoPane");
		this.selectedCards = controller.getGameTable().getSelectedCards();
		this.cardView = new JFXCardView(CardImageSize.LARGE);
		this.cardCostText = new JFXMagicText();
		this.cardRulesText = new JFXMagicText();
		this.cardNameText = new Text();
		this.cardTypeText = new Text();
		this.cardFlavorText = new Text();
		this.currentCard = null;

		buildComponentLayout();

		// Add a listener on the selected cards list to update the card displayed by this component each time the liste is updated
		this.selectedCards.addListener((ListChangeListener.Change<? extends Card> change) -> {

			if (this.selectedCards.isEmpty()) {
				setCard(null);
			} else {
				setCard(this.selectedCards.get(0));
			}
		});
	}

	/**
	 * Set the card to display. By default, the front side of the card is displayed. If the card is <code>null</code>, then the
	 * back of a magic card is displayed.
	 * 
	 * @param card the card (<code>null</code> allowed)
	 */
	private void setCard(final Card card) {

		if (card != null) {
			this.currentSide = Side.FRONT;
			this.currentCard = card;
			this.cardView.setCard(card);
			setCardData(card.getPrimaryCardData());
			updateCardViewContextMenu();
		} else {
			this.currentSide = Side.BACK;
			this.cardView.setCard(null);
			setCardData(null);
		}
	}

	/**
	 * Set the card data to display (a card can have more than one card data with the special layout)
	 * 
	 * @param cardData the card data (<code>null</code> allowed)
	 */
	private void setCardData(final MtgCard cardData) {

		if (cardData != null) {
			this.cardCostText.setText(cardData.getManaCost());
			this.cardRulesText.setText(cardData.getText());
			this.cardNameText.setText(cardData.getName());
			this.cardTypeText.setText(cardData.getType());
			this.cardFlavorText.setText(cardData.getFlavor());
		} else {
			this.cardCostText.setText("");
			this.cardRulesText.setText("");
			this.cardNameText.setText("");
			this.cardTypeText.setText("");
			this.cardFlavorText.setText("");
		}
	}

	/**
	 * Create the layout
	 */
	private void buildComponentLayout() {

		// Prepare the insets
		final Insets gridCellInsets = new Insets(5, 0, 5, 0);
		final Insets paneInsets = new Insets(10, 10, 10, 10);

		// Set the properties of the text components
		this.cardTypeText.setWrappingWidth(CardImageSize.LARGE.getWidth());
		this.cardFlavorText.setWrappingWidth(CardImageSize.LARGE.getWidth());
		this.cardCostText.setHAlignment(Pos.CENTER);
		
		this.cardView.getStyleClass().add("mtg-text");
		this.cardCostText.getStyleClass().add("mtg-text");
		this.cardRulesText.getStyleClass().add("mtg-text");
		this.cardNameText.getStyleClass().add("mtg-text");
		this.cardNameText.getStyleClass().add("strong");
		this.cardTypeText.getStyleClass().add("mtg-text");
		this.cardTypeText.getStyleClass().add("type");
		this.cardFlavorText.getStyleClass().add("mtg-text");
		this.cardFlavorText.getStyleClass().add("light");

		// Prepare the grid pane that contains and organizes the components
		final GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(this.cardView, this.cardNameText, this.cardCostText, this.cardTypeText, this.cardRulesText,
			this.cardFlavorText);
		GridPane.setConstraints(this.cardView, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		GridPane.setConstraints(this.cardNameText, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER,
			new Insets(0, 0, 0, 0));
		GridPane.setConstraints(this.cardCostText, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		GridPane.setConstraints(this.cardTypeText, 0, 3, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		GridPane.setConstraints(this.cardRulesText, 0, 4, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		GridPane.setConstraints(this.cardFlavorText, 0, 5, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);

		// Add the grid pand to the StackPane and adds the sizes constraints
		super.getChildren().add(gridPane);
		super.setPadding(paneInsets);
		super.setPrefWidth(CardImageSize.LARGE.getWidth() + paneInsets.getLeft() + paneInsets.getRight());
		super.setMinWidth(CardImageSize.LARGE.getWidth() + paneInsets.getLeft() + paneInsets.getRight());
		super.setMaxWidth(CardImageSize.LARGE.getWidth() + paneInsets.getLeft() + paneInsets.getRight());
	}

	/**
	 * If the card view context menu displayed a double-faced card, the "other side" menu item of the card view is updated by this
	 * method: it updates the code of the action and make sure that the image is changed but also the card data displayed by the
	 * JFXSelectedCardInfo component. Without this method, only the image is updated.
	 */
	private void updateCardViewContextMenu() {

		// Find the menu item if it exists (most of the time it will be null)
		final MenuItem otherSideMenuItem = JFXCardView.findMenuItemByID(this.cardView.getContextMenu(), JFXCardView.OTHER_SIDE_MENU_ITEM_ID);

		if (otherSideMenuItem != null) {

			// Get the default action performed
			final EventHandler<ActionEvent> defaultAction = otherSideMenuItem.getOnAction();

			// Update the action performed
			otherSideMenuItem.setOnAction(event -> {

				// 1. Perform the default action if available
				if(defaultAction != null) {
					defaultAction.handle(event);
				}

				// 2. New behavior : the card data displayed are also updated
				if (this.currentSide == Side.FRONT) {
					setCardData(this.currentCard.getSecondaryCardData());
					this.currentSide = Side.OTHER_SIDE;
				} else {
					setCardData(this.currentCard.getPrimaryCardData());
					this.currentSide = Side.FRONT;
				}
			});
		}
	}

	
}
