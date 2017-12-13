package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;

/**
 * The JFX component able to display the cards currently on the battlefield and manipulate them. It can be included into a
 * {@link JFXTwoPlayersBattlefields}
 * 
 * @author asenka
 * @see JFXBattlefieldCardView
 * @see JFXTwoPlayersBattlefields
 */
public class JFXBattlefield extends ScrollPane {

	/**
	 * The controller used to perform actions on the game data
	 */
	private final GameController gameController;

	/**
	 * This flag indicates if the current battlefield is the one of the local player or not. Depending of this values, the
	 * availables actions will change.
	 */
	private final boolean forLocalPlayer;

	/**
	 * The list of cards on the battlefield. A listener is added on this list to update the component when a card enters or
	 * leaves.
	 */
	private ObservableList<Card> battlefieldCards;

	/**
	 * The map of card views. It is used to easily find and remove the card view when the displayed card leaves the battlefield.
	 */
	private final ObservableMap<Card, JFXBattlefieldCardView> battlefieldCardViews;

	/**
	 * The group containing all the cards. It is interesting to used a group to make the methods {@link Node#toBack()} and
	 * {@link Node#toFront()} working
	 */
	private final Group cardsGroup;

	/**
	 * The pane where the cards group is displayed
	 */
	private final Pane battlefieldPane;

	/**
	 * The listener set on the {@link GameTable#otherPlayerProperty()}. The other player may not exist when the game is created.
	 * We need to have a listener to know when the other player join. When he/she does,
	 */
	private InvalidationListener otherPlayerJoiningListener;

	/**
	 * The parent battlefield. This value is not mandatory and can be <code>null</code>.
	 */
	private final JFXTwoPlayersBattlefields parentBattlefield;

	/**
	 * Build a simple battlefield that is not included into a {@link JFXTwoPlayersBattlefields}
	 * 
	 * @param gameController {@link JFXBattlefield#gameController}
	 * @param forLocalPlayer {@link JFXBattlefield#forLocalPlayer}
	 */
	public JFXBattlefield(final GameController gameController, final boolean forLocalPlayer) {

		this(null, gameController, forLocalPlayer);
	}

	/**
	 * Build a battlefield that may be included into a {@link JFXTwoPlayersBattlefields}
	 * 
	 * @param parentBattlefield {@link JFXBattlefield#parentBattlefield}
	 * @param gameController {@link JFXBattlefield#gameController}
	 * @param forLocalPlayer {@link JFXBattlefield#forLocalPlayer}
	 */
	JFXBattlefield(final JFXTwoPlayersBattlefields parentBattlefield, final GameController gameController, final boolean forLocalPlayer) {

		this.getStyleClass().add("mtg-pane");
		this.getStyleClass().add("JFXBattlefield");
		this.getStyleClass().add(forLocalPlayer ? "localPlayer" : "opponentPlayer");
		this.parentBattlefield = parentBattlefield;
		this.forLocalPlayer = forLocalPlayer;
		this.gameController = gameController;
		this.battlefieldCards = getBattlefieldCards(this.gameController.getGameTable(), forLocalPlayer);
		this.battlefieldCardViews = FXCollections.<Card, JFXBattlefieldCardView> observableHashMap();
		this.battlefieldPane = new Pane();
		this.cardsGroup = new Group();

		buildComponentLayout();
		addListeners();
	}

	/**
	 * Build the battlefield layout
	 */
	private void buildComponentLayout() {

		// If there is already some cards to display, they are directly added. It could happen when a player join a game with
		// already played cards.
		if (this.battlefieldCards != null) {
			this.battlefieldCards.forEach(card -> addCardViewToBattlefield(card));
		}

		this.battlefieldPane.getChildren().add(this.cardsGroup);
		super.setContent(this.battlefieldPane);
	}

	/**
	 * Add the listeners to make this battlefield dynamic. If the battlefield is NOT for the local player data and if the other
	 * player is not already set (<em>i.e.</em> its current value is <code>null</code>), then the
	 * {@link JFXBattlefield#otherPlayerJoiningListener} is initialized to add the listeners later when the other player is ready.
	 */
	private void addListeners() {

		if (!this.forLocalPlayer && this.battlefieldCards == null) {
			installOtherPlayerListener();
		} else {
			installDefaultListener();
		}
	}

	/**
	 * Install the normal listeners to manage the card when they enter or leave.
	 */
	private void installDefaultListener() {
	
		// Listener updating the battlefield view when a card enters or leaves the battlefield
		this.battlefieldCards.addListener((ListChangeListener.Change<? extends Card> change) -> {
	
			while (change.next()) {
	
				// A card enters the battlefield component...
				if (change.wasAdded()) {
					change.getAddedSubList().forEach(card -> addCardViewToBattlefield(card));
	
					// A card leaves the battlefield component...
				} else if (change.wasRemoved()) {
					change.getRemoved().forEach(card -> removeCardViewFromBattlefield(card));
				}
			}
		});
	}

	/**
	 * Only used for other player battlefield and if the opponent data are not available yet. It creates and add a listener on 
	 * {@link JFXBattlefield#otherPlayerJoiningListener} 
	 */
	private void installOtherPlayerListener() {

		final ObjectProperty<Player> otherPlayerProperty = this.gameController.getGameTable().otherPlayerProperty();

		// ... It adds a listeners on the otherPlayer property to update the battlefield cards list
		this.otherPlayerJoiningListener = observable -> {

			this.battlefieldCards = this.gameController.getGameTable().getOtherPlayer().getBattlefield();
			installDefaultListener();

			// As soon as the other player has join, we don't need anymore to perform this operation. The listener is removed and
			// nullified
			otherPlayerProperty.removeListener(this.otherPlayerJoiningListener);
			this.otherPlayerJoiningListener = null;
		};
		otherPlayerProperty.addListener(this.otherPlayerJoiningListener);
	}

	/**
	 * Create and add a {@link JFXBattlefieldCardView} into the battlefield
	 * @param card the card added
	 * @see JFXBattlefieldCardView
	 */
	private void addCardViewToBattlefield(final Card card) {

		final JFXBattlefieldCardView battlefieldCardView = new JFXBattlefieldCardView(card);
		this.battlefieldCardViews.put(card, battlefieldCardView);
		Platform.runLater(() -> this.cardsGroup.getChildren().add(battlefieldCardView));
	}

	/**
	 * Remove the card view fromt he battlefield.
	 * @param card the card removed
	 * @see JFXBattlefieldCardView
	 */
	private void removeCardViewFromBattlefield(final Card card) {

		final JFXBattlefieldCardView battlefieldCardView = this.battlefieldCardViews.remove(card);
		Platform.runLater(() -> {

			if (!this.cardsGroup.getChildren().remove(battlefieldCardView)) {
				throw new IllegalArgumentException(
					"Try to remove the view of " + card + " that is not currently displayed on this battlefield.");
			}
		});
	}

	/**
	 * Get the battlefield list of a player from a specific game table. The player where the battlefield list is loaded depends on 
	 * the forLocalPlayer flag
	 * @param gameController {@link JFXBattlefield#gameController}
	 * @param forLocalPlayer {@link JFXBattlefield#forLocalPlayer}
	 * @return a list of cards that can be observed representing the battlefield of a player.
	 * @throws IllegalStateException if gameTable is <code>null</code>
	 */
	private static ObservableList<Card> getBattlefieldCards(final GameTable gameTable, final boolean forLocalPlayer)
		throws IllegalStateException {

		if (gameTable != null) {
			final Player player = forLocalPlayer ? gameTable.getLocalPlayer() : gameTable.getOtherPlayer();
			return player != null ? player.getBattlefield() : null;
		} else {
			throw new IllegalStateException("The game table is null.");
		}
	}

	/**
	 * A subclass of {@link JFXCardView} that add a specific context menu and the ability to move the card
	 * by dragging the mouse.
	 * 
	 * @author asenka
	 * @see JFXCardView
	 */
	private class JFXBattlefieldCardView extends JFXCardView {

		/**
		 * When clicking on a card view, <code>deltaX</code> contains the local X coordinate of the point clicked
		 */
		private double deltaX;

		/**
		 * When clicking on a card view, <code>deltaY</code> contains the local Y coordinate of the point clicked
		 */
		private double deltaY;

		/**
		 * Build a card view for the battlefield
		 * @param card the card to display
		 * @param size the size
		 */
		private JFXBattlefieldCardView(final Card card) {

			super(card, CardImageSize.MEDIUM);
			selectSide(card.isVisible() ? Side.FRONT : Side.BACK);

			initializeMouvementListeners();
			initializeCardListeners();
			initializeContextMenu();
		}

		/**
		 * Update the context menu of the card view
		 */
		private void initializeContextMenu() {

			final Card card = super.getCard();
			final ContextMenu defaultContextMenu = getContextMenu();

			// If the card is a double-faced card, the otherSideMenuItem is disabled
			final MenuItem otherSideMenuItem = JFXCardView.findMenuItemByID(defaultContextMenu, JFXCardView.OTHER_SIDE_MENU_ITEM_ID);
			if (otherSideMenuItem != null) {
				otherSideMenuItem.setDisable(!card.isVisible());
			}

			// Some actions are only available for the local player so that the opponent can not mess with the local player cards.
			if (forLocalPlayer) {
				
				// Menu item managing the tapping of the card
				final MenuItem tapMenuItem = new MenuItem("Tap");
				tapMenuItem.setOnAction(event -> {

					if ("Tap".equals(tapMenuItem.getText())) {
						gameController.setTapped(card, true);
						tapMenuItem.setText("Untap");
					} else {
						gameController.setTapped(card, false);
						tapMenuItem.setText("Tap");
					}

				});

				// Menu item managing the visibility of the card
				final MenuItem visibleMenuItem = new MenuItem(card.isVisible() ? "Hide" : "Show");
				visibleMenuItem.setOnAction(event -> {

					if ("Hide".equals(visibleMenuItem.getText())) {
						gameController.setVisible(card, false);

						if (otherSideMenuItem != null) {
							otherSideMenuItem.setDisable(true);
						}
						visibleMenuItem.setText("Show");
					} else {
						gameController.setVisible(card, true);
						visibleMenuItem.setText("Hide");

						if (otherSideMenuItem != null) {
							otherSideMenuItem.setDisable(false);
						}
					}

				});

				// Destroy menu item
				final MenuItem destroyMenuItem = new MenuItem("Destroy");
				destroyMenuItem
					.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.GRAVEYARD, 0, false));

				// Exile menu item
				final MenuItem exileMenuItem = new MenuItem("Exile");
				exileMenuItem.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.EXILE, 0, false));

				// Back to hand menu item
				final MenuItem handMenuItem = new MenuItem("Hand");
				handMenuItem.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.HAND, 0, true));

				// Send to library menu items
				final Menu libraryMenuItem = new Menu("To Library");
				final MenuItem topLibraryMenuItem = new MenuItem("Top");
				final MenuItem bottomLibraryMenuItem = new MenuItem("Bottom");
				libraryMenuItem.getItems().addAll(topLibraryMenuItem, bottomLibraryMenuItem);
				topLibraryMenuItem.setOnAction(
					event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.LIBRARY, GameController.TOP, true));
				bottomLibraryMenuItem.setOnAction(
					event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.LIBRARY, GameController.BOTTOM, true));

				// Build the context menu
				defaultContextMenu.getItems().addAll(tapMenuItem, visibleMenuItem, destroyMenuItem, exileMenuItem, handMenuItem,
					libraryMenuItem);

			}
			// Menu managing the cards overlapping
			// Send to the front the selected card (only local operation)
			final MenuItem aboveMenuItem = new MenuItem("Above");
			aboveMenuItem.setOnAction(event -> this.toFront());

			// Send to back the selected card (only local operation)
			final MenuItem underMenuItem = new MenuItem("Under");
			underMenuItem.setOnAction(event -> this.toBack());
			
			defaultContextMenu.getItems().addAll(aboveMenuItem, underMenuItem);
		}

		/**
		 * Add the listeners on the card displayed to update the card view when the card properties change
		 */
		private void initializeCardListeners() {

			final Card card = super.getCard();

			// Rotate the card view when the tap property is updated on the card
			card.tappedProperty().addListener(observable -> {

				final BooleanProperty tappedProperty = (BooleanProperty) observable;

				if (tappedProperty.get()) {
					this.setRotate(90d);
				} else {
					this.setRotate(0d);
				}
			});

			// Show/hide the card when the visible property of the card is updated
			card.visibleProperty().addListener(observable -> {

				final BooleanProperty visibleProperty = (BooleanProperty) observable;

				if (visibleProperty.get()) {
					this.selectSide(Side.FRONT);
				} else {
					this.selectSide(Side.BACK);
				}
			});

			// Moves the card view when the card coordinates are updated
			card.locationProperty().addListener(observable -> {

				@SuppressWarnings("unchecked")
				final ObjectProperty<Point2D> locationProperty = (ObjectProperty<Point2D>) observable;

				this.setLayoutX(locationProperty.get().getX());
				this.setLayoutY(locationProperty.get().getY());
			});
		}

		/**
		 * Initializes the listeners to make the card views movable on the battlefield
		 */
		private void initializeMouvementListeners() {

			final Card card = super.getCard();

			// When the mouse button is pressed, the selected cards list is updated
			super.setOnMousePressed(event -> {

				if (forLocalPlayer || card.isVisible()) {
					gameController.setSelectedCards(card);
				} else {
					gameController.setSelectedCards(); // clear the selection list.
				}
				this.deltaX = event.getX();
				this.deltaY = event.getY();
			});

			// When the mouse moves while the button is pressed, the card view component position follow the
			// the mouse coordinates
			super.setOnMouseDragged(event -> {

				// Calculate the new layout coordinates of the card view
				final double newLayoutX = parentBattlefield == null
					? event.getSceneX() - this.deltaX - JFXBattlefield.this.getBoundsInParent().getMinX()
					: event.getSceneX() - this.deltaX - JFXBattlefield.this.getParent().getBoundsInParent().getMinX()
						- parentBattlefield.getBoundsInParent().getMinX();
				final double newLayoutY = parentBattlefield == null
					? event.getSceneY() - this.deltaY - JFXBattlefield.this.getBoundsInParent().getMinY()
					: event.getSceneY() - this.deltaY - JFXBattlefield.this.getParent().getBoundsInParent().getMinY()
						- parentBattlefield.getBoundsInParent().getMinY();

				// Update the card view coordinates
				if (newLayoutX > 0) {
					super.setLayoutX(newLayoutX);
				}
				if (newLayoutY > 0) {
					super.setLayoutY(newLayoutY);
				}
				
			});

			// When the mouse is released, the game controller update the location of the card
			super.setOnMouseReleased(event -> gameController.setLocation(card, super.getLayoutX(), super.getLayoutY()));
		}
	}
}
