package asenka.mtgfree.views;

import java.util.Optional;

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class JFXSelectedCardInfoPane extends StackPane {
	
	private final ObservableList<Card> selectedCards;
	
	private final JFXCardView cardView;
	
	private final JFXMagicText cardCostText;
	
	private final JFXMagicText cardRulesText;
	
	private final Text cardNameText;
	
	private final Text cardTypeText;
	
	private final Text cardFlavorText;
	
	private Side currentSide;
	
	private Card currentCard;
	
	public JFXSelectedCardInfoPane(final ObservableList<Card> selectedCards) {
		
		this.selectedCards = selectedCards;
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
			
			if(this.selectedCards.isEmpty()) {
				setCard(null);	
			} else {
				setCard(this.selectedCards.get(0));	
			}
		});
	}
	
	/**
	 * Set the card to display
	 * @param card
	 */
	private void setCard(final Card card) {
		
		if(card != null) {
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
	 * @param cardData the card data
	 */
	private void setCardData(final MtgCard cardData) {
		
		if(cardData != null) {
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
		
		final Insets gridCellInsets = new Insets(5, 0, 5, 0);
		final Insets paneInsets = new Insets(10, 10, 10, 10);
		
		this.cardNameText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		this.cardTypeText.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
		this.cardRulesText.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));
		this.cardFlavorText.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
		this.cardTypeText.setWrappingWidth(CardImageSize.LARGE.getWidth());
		this.cardFlavorText.setWrappingWidth(CardImageSize.LARGE.getWidth());
		
		this.cardCostText.setHAlignment(Pos.CENTER);
		
		GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(this.cardView, this.cardNameText, this.cardCostText, this.cardTypeText, this.cardRulesText, this.cardFlavorText);
		GridPane.setConstraints(this.cardView, 			0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		GridPane.setConstraints(this.cardNameText, 		0, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(0, 0, 0, 0));
		GridPane.setConstraints(this.cardCostText, 		0, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		GridPane.setConstraints(this.cardTypeText, 		0, 3, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		GridPane.setConstraints(this.cardRulesText, 	0, 4, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		GridPane.setConstraints(this.cardFlavorText, 	0, 5, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, gridCellInsets);
		
		super.getChildren().add(gridPane);
		super.setPadding(paneInsets);
		super.setPrefWidth(CardImageSize.LARGE.getWidth() + paneInsets.getLeft() + paneInsets.getRight());
		super.setMinWidth(CardImageSize.LARGE.getWidth() + paneInsets.getLeft() + paneInsets.getRight());
		super.setMaxWidth(CardImageSize.LARGE.getWidth() + paneInsets.getLeft() + paneInsets.getRight());
	}
	
	/**
	 * If the card view context menu displayed a double-faced card, the "other side" menu item
	 * of the card view is updated by this method: it updates the code of the action ot make sure that
	 * the image is changed but also the card data displayed by the JFXSelectedCardInfo component. Without
	 * this method, only the image is updated
	 */
	private void updateCardViewContextMenu() {
		
		// Find the menu item if it exists (most of the time it will be null)
		final MenuItem otherSideMenuItem = findMenuItemByID(this.cardView.getContextMenu(), JFXCardView.OTHER_SIDE_MENU_ITEM_ID);
		
		if(otherSideMenuItem != null) {
			
			// Get the default action performed 
			final EventHandler<ActionEvent> defaultAction = otherSideMenuItem.getOnAction();
			
			// Update the action performed
			otherSideMenuItem.setOnAction(event -> {
				
				// 1. Perform the default action
				defaultAction.handle(event);
				
				// 2. New behavior : the card data displayed are also updated
				if(this.currentSide == Side.FRONT) {
					setCardData(this.currentCard.getSecondaryCardData());
					this.currentSide = Side.OTHER_SIDE;
				} else {
					setCardData(this.currentCard.getPrimaryCardData());
					this.currentSide = Side.FRONT;
				}	
			});
		}
	}
	
	/**
	 * Look for a menu item with a specific ID in a context menu
	 * @param contextMenu the context menu 
	 * @param id the id of the menu item in the context menu to look for
	 * @return the menu item id with the desired ID or <code>null</code>
	 */
	private static MenuItem findMenuItemByID(final ContextMenu contextMenu, String id) {
		
		// Return one and only one menu item matching the ID
		Optional<MenuItem> result = contextMenu.getItems().stream().filter(item -> id.equals(item.getId())).findFirst();
		return result.isPresent() ? result.get() : null;
	}
	
}
