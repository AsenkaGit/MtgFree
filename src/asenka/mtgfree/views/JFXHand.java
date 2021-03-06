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
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
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
 * JavaFX component displaying the hand of a player. The display changes if the player is the local player or not. The player hand
 * is embedded in an horizontal ScrollPane.
 * 
 * @author asenka
 * @see ScrollPane
 */
public class JFXHand extends ScrollPane {

	/**
	 * The list of handCards in the player hand
	 */
	private ObservableList<Card> handCards;

	/**
	 * The pane display the handCards horizontally
	 */
	private final HBox horizontalLayout;

	/**
	 * The list of card panes
	 */
	private final List<Pane> cardPanes;

	/**
	 * Flag indicating if the hand is from the local player or another player
	 */
	private final boolean forLocalPlayer;

	/**
	 * The game controller used to perform actions on the handCards
	 */
	private final GameController gameController;

	/**
	 * The listener used to update the cards list in the hand when the other player join the game.
	 */
	private InvalidationListener otherPlayerJoiningListener;

	/**
	 * Build a JFX component displaying the handCards in the player's hand
	 * 
	 * @param gameController the controller used to manipulate the handCards
	 * @param player the player
	 */
	public JFXHand(final GameController gameController, final boolean forLocalPlayer) {

		super();
		this.getStyleClass().add("mtg-pane");
		this.getStyleClass().add("JFXHand");
		this.getStyleClass().add(forLocalPlayer ? "localPlayer" : "opponentPlayer");
		this.gameController = gameController;
		this.forLocalPlayer = forLocalPlayer;
		this.handCards = getHandCards();
		this.cardPanes = new ArrayList<Pane>();
		this.horizontalLayout = new HBox();

		buildComponentLayout();
		addListeners();
	}

	private void buildComponentLayout() {

		this.horizontalLayout.setPadding(new Insets(15, 10, 15, 10));
		this.horizontalLayout.setMinHeight(CardImageSize.MEDIUM.getHeigth() + 30d);
		super.setMinHeight((forLocalPlayer ? CardImageSize.MEDIUM.getHeigth() : CardImageSize.SMALL.getHeigth()) + 30d);
		super.setContent(this.horizontalLayout);
		super.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		super.setVbarPolicy(ScrollBarPolicy.NEVER);
	}

	private void addListeners() {

		// If the component display the hand of an opponent that has not join the game yet...
		if (!this.forLocalPlayer && this.handCards == null) {

			final ObjectProperty<Player> otherPlayerProperty = this.gameController.getGameTable().otherPlayerProperty();

			// ... It adds a listeners on the otherPlayer property to update the handCards list
			this.otherPlayerJoiningListener = (observable -> {

				this.handCards = this.gameController.getGameTable().getOtherPlayer().getHand();
				this.addListeners();

				// As soon as the other player has join, we don't need anymore to perform this operation. The listener is removed
				otherPlayerProperty.removeListener(this.otherPlayerJoiningListener);
			});
			otherPlayerProperty.addListener(this.otherPlayerJoiningListener);

		} else {
			
			// TODO Naive implementation : remove all and add all again... Can do better.
			// Even though this component should not contain more than 7 or 8 handCards most of the time
			this.handCards.addListener((ListChangeListener.Change<? extends Card> change) -> refreshHand());
		
			// Draws the hand
			this.refreshHand();
		}
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
		if (this.forLocalPlayer) {

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

			cardViewWithInfoOverlay = null;
			cardView = new JFXCardView(card, CardImageSize.SMALL);
			cardView.selectSide(card.isVisible() ? Side.FRONT : Side.BACK);
			cardView.setOnContextMenuRequested(null);
			card.visibleProperty().addListener(observable -> Platform.runLater(() -> cardView.selectSide(card.isVisible() ? Side.FRONT : Side.BACK)));
		}

		// Create and return the pane to put the card view in
		final Pane cardPane = new Pane(cardViewWithInfoOverlay != null ? cardViewWithInfoOverlay : cardView);
		cardPane.setPadding(new Insets(0, 5, 0, 5));
		return cardPane;
	}

	/**
	 * Add a context menu to a card view. This method is called only of the component displays the handCards of the local player.
	 * 
	 * @param cardView the card view where the context menu will be added
	 * @param card the card related to the node where the context menu is created
	 */
	private void addContextMenuForCardNode(final JFXCardView cardView, final Card card) {

		// Create the context menu
		final ContextMenu contextMenu = cardView.getContextMenu();
		final Menu playMenu = new Menu("Play");
		final MenuItem playVisibleMenuItem = new MenuItem("Visible");
		final MenuItem playHiddenMenuItem = new MenuItem("Hidden");
		final MenuItem destroyMenuItem = new MenuItem("Destroy");
		final MenuItem exileMenuItem = new MenuItem("Exile");
		final MenuItem revealMenuItem = new MenuItem(card.isVisible() ? "Hide" : "Reveal");
		final Menu libraryMenuItem = new Menu("To Library");
		final MenuItem topLibraryMenuItem = new MenuItem("Top");
		final MenuItem bottomLibraryMenuItem = new MenuItem("Bottom");
		playMenu.getItems().addAll(playVisibleMenuItem, playHiddenMenuItem);
		libraryMenuItem.getItems().addAll(topLibraryMenuItem, bottomLibraryMenuItem);
		contextMenu.getItems().addAll(new SeparatorMenuItem(), playMenu, exileMenuItem, destroyMenuItem, libraryMenuItem, revealMenuItem);

		// Set the actions associated with the menu items
		destroyMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.GRAVEYARD, GameController.TOP, false));
		exileMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.EXILE, GameController.TOP, card.isVisible()));
		playVisibleMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.BATTLEFIELD, GameController.TOP, false));
		playHiddenMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.BATTLEFIELD, GameController.TOP, true));
		topLibraryMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.LIBRARY, GameController.TOP, true));
		bottomLibraryMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.LIBRARY, GameController.BOTTOM, true));
		revealMenuItem.setOnAction(event -> {
			this.gameController.setVisible(card, !card.isVisible());
			revealMenuItem.setText(card.isVisible() ? "Hide" : "Reveal");
		});

		// Add the context menu to the card view
		cardView.setOnContextMenuRequested(event -> contextMenu.show(cardView, event.getScreenX(), event.getScreenY()));
	}

	/**
	 * Refresh the whole hand by redrawing all the handCards inside. This behavior may be improved...
	 */
	private void refreshHand() {

		// To update the JavaFX components, you have to perform the operations in the JavaFX Application Thread
		Platform.runLater(() -> {
			this.horizontalLayout.getChildren().clear();
			this.cardPanes.clear();

			this.handCards.forEach(card -> JFXHand.this.cardPanes.add(createCardPane(card)));
			this.horizontalLayout.getChildren().addAll(this.cardPanes);
		});
	}

	/**
	 * @return the observable list of handCards to display on this component. The result depends on the forLocalPlayer flag and the
	 *         result can be <code>null</code>.
	 */
	private ObservableList<Card> getHandCards() throws IllegalStateException {

		// The gameController must be initialized. Also the forLocalPlayer flag must be set !
		if (this.gameController != null) {
			final Player player = this.forLocalPlayer ? 
				this.gameController.getGameTable().getLocalPlayer() : 
				this.gameController.getGameTable().getOtherPlayer();
			return player != null ? player.getHand() : null;
		} else {
			throw new IllegalStateException("The game controller must be initialized before calling this method.");
		}
	}
}
