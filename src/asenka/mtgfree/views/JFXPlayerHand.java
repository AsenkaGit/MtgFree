package asenka.mtgfree.views;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.InfoOverlay;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.views.JFXCardView.Side;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * JavaFX component displaying the hand of a player. The display changes if the player is the local player or not.
 * 
 * 
 * @author asenka
 * 
 */
public class JFXPlayerHand extends ScrollPane {

	/**
	 * The list of cards in the player hand
	 */
	private final ObservableList<Card> cards;

	/**
	 * The pane display the cards horizontally
	 */
	private final HBox horizontalLayout;

	/**
	 * The list of card panes
	 */
	private final List<Pane> cardPanes;

	/**
	 * Flag indicating if the hand is from the local player or another player
	 */
	private final boolean isLocalPlayerHand;

	/**
	 * The game controller used to perform actions on the cards
	 */
	private final GameController gameController;

	/**
	 * Build a JFX component displaying the cards in the player's hand
	 * 
	 * @param gameController the controller used to manipulate the cards
	 * @param player the player
	 */
	public JFXPlayerHand(final GameController gameController, final Player player) {

		super();
		this.cards = player.getHand();
		this.gameController = gameController;
		this.isLocalPlayerHand = player.equals(gameController.getGameTable().getLocalPlayer());
		this.cardPanes = new ArrayList<Pane>();
		this.horizontalLayout = new HBox();
		this.horizontalLayout.setPadding(new Insets(15, 10, 15, 10));

		// Add the cards from the player hand.
		this.cards.forEach(card -> this.cardPanes.add(createCardPane(card)));
		this.horizontalLayout.getChildren().addAll(this.cardPanes);

		// TODO Naive implementation : remove all and add all again... Can do better.
		// Even though this component should not contain more than 7 or 8 cards most of the time
		this.cards.addListener((ListChangeListener.Change<? extends Card> change) -> refreshHand());

		// If the player is not the local player, the cards are equipped with another listener
		// It refresh the player hand whenever a card from its hand change visibility.
		if (!this.isLocalPlayerHand) {
			this.cards.forEach(card -> card.visibleProperty().addListener(observable -> refreshHand()));
		}

		super.setContent(this.horizontalLayout);
		super.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		super.setVbarPolicy(ScrollBarPolicy.NEVER);
	}

	/**
	 * Create a pane displaying a card
	 * 
	 * @param card the card to display
	 * @return a pane with all the components necessary for the card
	 */
	private Pane createCardPane(final Card card) {

		final JFXCardView cardView;
		final InfoOverlay cardViewWithInfoOverlay;

		// If the hand displayed is the one of the local player...
		if (this.isLocalPlayerHand) {

			cardView = new JFXCardView(card, CardImageSize.MEDIUM);

			// Add the context menu on the card
			addContextMenuForCardNode(cardView, card);

			// Add action to set the selected card
			cardView.setOnMouseClicked(event -> this.gameController.setSelectedCards(card));

			// The card is added in an info overlay (see controlfx library) that display the card name
			cardViewWithInfoOverlay = new InfoOverlay(cardView, card.getPrimaryCardData().getName());

			// When the mouse is over the card view, the card is zoomed 5% bigger
			cardViewWithInfoOverlay.setOnMouseEntered(event -> {
				cardViewWithInfoOverlay.setScaleX(1.05);
				cardViewWithInfoOverlay.setScaleY(1.05);
			});

			// When the mouse leaves the image, it returns to its normal size
			cardViewWithInfoOverlay.setOnMouseExited(event -> {
				cardViewWithInfoOverlay.setScaleX(1);
				cardViewWithInfoOverlay.setScaleY(1);
			});
		} else {

			cardView = new JFXCardView(card, CardImageSize.SMALL);

			if (card.isVisible()) {

				// The card is added in an info overlay (see controlfx library) that display the card name
				cardViewWithInfoOverlay = new InfoOverlay(cardView, card.getPrimaryCardData().getName());
				cardViewWithInfoOverlay.setStyle("-fx-font-size:9px;");
				cardView.selectSide(Side.FRONT);
			} else {
				cardViewWithInfoOverlay = null;
				cardView.setOnContextMenuRequested(null);
				cardView.selectSide(Side.BACK);
			}
		}

		// Create and return the pane to put the card view in
		final Pane cardPane = new Pane(cardViewWithInfoOverlay != null ? cardViewWithInfoOverlay : cardView);
		cardPane.setPadding(new Insets(0, 5, 0, 5));
		return cardPane;
	}

	/**
	 * Add a context menu to a card view
	 * 
	 * @param cardView the card view where the context menu will be added
	 * @param card the card related to the node where the context menu is created
	 */
	private void addContextMenuForCardNode(final JFXCardView cardView, final Card card) {

		// Create the context menu
		final ContextMenu contextMenu = cardView.createContextMenu();
		final Menu playMenu = new Menu("Play");
		final MenuItem playVisibleMenuItem = new MenuItem("Visible");
		final MenuItem playHiddenMenuItem = new MenuItem("Hidden");
		final MenuItem destroyMenuItem = new MenuItem("Destroy");
		final MenuItem exileMenuItem = new MenuItem("Exile");
		final Menu libraryMenuItem = new Menu("To Library");
		final MenuItem topLibraryMenuItem = new MenuItem("Top");
		final MenuItem bottomLibraryMenuItem = new MenuItem("Bottom");
		playMenu.getItems().addAll(playVisibleMenuItem, playHiddenMenuItem);
		libraryMenuItem.getItems().addAll(topLibraryMenuItem, bottomLibraryMenuItem);
		contextMenu.getItems().addAll(new SeparatorMenuItem(), playMenu, exileMenuItem, libraryMenuItem);

		// Set the actions associated with the menu items
		destroyMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.GRAVEYARD, GameController.TOP, false));
		exileMenuItem.setOnAction(
			event -> this.gameController.changeCardContext(card, Context.HAND, Context.EXILE, GameController.TOP, card.isVisible()));
		playVisibleMenuItem.setOnAction(
			event -> this.gameController.changeCardContext(card, Context.HAND, Context.BATTLEFIELD, GameController.TOP, false));
		playHiddenMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.BATTLEFIELD, GameController.TOP, true));
		topLibraryMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.LIBRARY, GameController.TOP, true));
		bottomLibraryMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.LIBRARY, GameController.BOTTOM, true));


		// Add the context menu to the card view
		cardView.setOnContextMenuRequested(event -> contextMenu.show(cardView, event.getScreenX(), event.getScreenY()));
	}

	/**
	 * Refresh the whole hand by redrawing all the cards inside. This behavior may be improved...
	 */
	private void refreshHand() {

		// To update the JavaFX components, you have to perform the operations in the JavaFX Application Thread
		Platform.runLater(() -> {
			this.horizontalLayout.getChildren().clear();
			this.cardPanes.clear();

			this.cards.forEach(card -> JFXPlayerHand.this.cardPanes.add(createCardPane(card)));
			this.horizontalLayout.getChildren().addAll(this.cardPanes);
		});
	}
}
