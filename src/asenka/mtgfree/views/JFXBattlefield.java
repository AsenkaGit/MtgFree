package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Card;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;

public class JFXBattlefield extends ScrollPane {

	private ObservableList<Card> battlefieldCards;

	private final ObservableMap<Card, JFXBattlefieldCardView> battlefieldCardViews;

	private final GameController gameController;

	private final Pane battlefieldPane;

	private final Group cardsGroup;

	private final boolean forLocalPlayer;
	
	private InvalidationListener otherPlayerJoiningListener;
	
	private final JFXTwoPlayersBattlefields parentBattlefield;

	
	public JFXBattlefield(final GameController gameController, final boolean forLocalPlayer) {
		
		this(null, gameController, forLocalPlayer);
	}
	
	JFXBattlefield(final JFXTwoPlayersBattlefields bothBattlefield, final GameController gameController, final boolean forLocalPlayer) {

		this.parentBattlefield = bothBattlefield;
		this.forLocalPlayer = forLocalPlayer;
		this.gameController = gameController;
		this.battlefieldCards = getBattlefieldCards();
		this.battlefieldCardViews = FXCollections.<Card, JFXBattlefieldCardView> observableHashMap();
		this.battlefieldPane = new Pane();
		this.cardsGroup = new Group();

		buildComponentLayout();
		addListeners();
	}

	private void buildComponentLayout() {

		this.battlefieldPane.getChildren().add(this.cardsGroup);
		this.battlefieldPane.setStyle("-fx-border-color: blue");
		super.setContent(this.battlefieldPane);
	}

	private void addListeners() {
		
		
		// If the component display the battlefield of an opponent that has not join the game yet...
		if (!this.forLocalPlayer && this.battlefieldCards == null) {
			
			final ObjectProperty<Player> otherPlayerProperty = this.gameController.getGameTable().otherPlayerProperty();
			
			// ... It adds a listeners on the otherPlayer property to update the battlefield cards list
			this.otherPlayerJoiningListener = (observable -> {

				this.battlefieldCards = this.gameController.getGameTable().getOtherPlayer().getBattlefield();
				this.addListeners();
				
				// As soon as the other player has join, we don't need anymore to perform this operation. The listener is removed
				otherPlayerProperty.removeListener(this.otherPlayerJoiningListener);
			});
			otherPlayerProperty.addListener(this.otherPlayerJoiningListener);

		} else {
		
			// Listener updating the battlefield view when a card enters or leaves the battlefield
			this.battlefieldCards.addListener((ListChangeListener.Change<? extends Card> change) -> {

				while (change.next()) {

					// A card enters the battlefield component...
					if (change.wasAdded()) {
						
						change.getAddedSubList().forEach(card -> {
							JFXBattlefieldCardView battlefieldCardView = new JFXBattlefieldCardView(card, CardImageSize.MEDIUM);
							this.battlefieldCardViews.put(card, battlefieldCardView);
							Platform.runLater(() -> this.cardsGroup.getChildren().add(battlefieldCardView));
						});
						
					// A card leaves the battlefield component...
					} else if (change.wasRemoved()) {
						
						change.getRemoved().forEach(card -> {
							JFXBattlefieldCardView battlefieldCardView = this.battlefieldCardViews.remove(card);
							Platform.runLater(() -> this.cardsGroup.getChildren().remove(battlefieldCardView));
						});
					}
				}
			});
		}
	}

	/**
	 * @return the observable list of cards to display on this component. The result depends on the forLocalPlayer flag and the
	 *         result can be <code>null</code>.
	 */
	private ObservableList<Card> getBattlefieldCards() throws IllegalStateException {

		// The gameController must be initialized. Also the forLocalPlayer flag must be set !
		if (this.gameController != null) {
			final Player player = this.forLocalPlayer ? 
				this.gameController.getGameTable().getLocalPlayer() : 
				this.gameController.getGameTable().getOtherPlayer();
			return player != null ? player.getBattlefield() : null;
		} else {
			throw new IllegalStateException("The game controller must be initialized before calling this method.");
		}
	}
	
	private class JFXBattlefieldCardView extends JFXCardView {

		private double deltaX;

		private double deltaY;

		private JFXBattlefieldCardView(final Card card, CardImageSize size) {

			super(card, size);
			selectSide(card.isVisible() ? Side.FRONT : Side.BACK);

			initializeMouvementListeners();
			initializeCardListeners();
			
			if(JFXBattlefield.this.forLocalPlayer) {
				initializeContextMenu();
			}
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
			destroyMenuItem.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.GRAVEYARD, 0, false));

			// Exile menu item
			final MenuItem exileMenuItem = new MenuItem("Exile");
			exileMenuItem.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.EXILE, 0, false));

			// Back to hand menu item
			final MenuItem handMenuItem = new MenuItem("Hand");
			handMenuItem.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.HAND, 0, true));

			// Send to the front the selected card (only local operation)
			final MenuItem aboveMenuItem = new MenuItem("Above");
			aboveMenuItem.setOnAction(event -> this.toFront());

			// Send to back the selected card (only local operation)
			final MenuItem underMenuItem = new MenuItem("Under");
			underMenuItem.setOnAction(event -> this.toBack());

			// 
			final Menu libraryMenuItem = new Menu("To Library");
			final MenuItem topLibraryMenuItem = new MenuItem("Top");
			final MenuItem bottomLibraryMenuItem = new MenuItem("Bottom");
			libraryMenuItem.getItems().addAll(topLibraryMenuItem, bottomLibraryMenuItem);
			topLibraryMenuItem.setOnAction(
				event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.LIBRARY, GameController.TOP, true));
			bottomLibraryMenuItem.setOnAction(
				event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.LIBRARY, GameController.BOTTOM, true));

			defaultContextMenu.getItems().addAll(tapMenuItem, visibleMenuItem, destroyMenuItem, exileMenuItem, handMenuItem,
				libraryMenuItem, aboveMenuItem, underMenuItem);
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

				if(JFXBattlefield.this.forLocalPlayer || card.isVisible()) {
					JFXBattlefield.this.gameController.setSelectedCards(card);
				} else {
					JFXBattlefield.this.gameController.setSelectedCards();
				}
				this.deltaX = event.getX();
				this.deltaY = event.getY();
			});

			// When the mouse moves while the button is pressed, the card view component position follow the
			// the mouse coordinates
			super.setOnMouseDragged(event -> {

				// The way to calculate the new coordinates depends on the way the battlefield is included in the
				// application scene. 
				final double newLayoutX = parentBattlefield == null ?
					event.getSceneX() - this.deltaX - JFXBattlefield.this.getBoundsInParent().getMinX() :
					event.getSceneX() - this.deltaX - JFXBattlefield.this.getParent().getBoundsInParent().getMinX() - parentBattlefield.getBoundsInParent().getMinX();
				final double newLayoutY = parentBattlefield == null ?
					event.getSceneY() - this.deltaY - JFXBattlefield.this.getBoundsInParent().getMinY() :
					event.getSceneY() - this.deltaY - JFXBattlefield.this.getParent().getBoundsInParent().getMinY() - parentBattlefield.getBoundsInParent().getMinY();
				
				if (newLayoutX > 0) {
					super.setLayoutX(newLayoutX);
				}

				if (newLayoutY > 0) {
					super.setLayoutY(newLayoutY);
				}
			});

			// When the mouse is released, the game controller update the location of the card
			super.setOnMouseReleased(event -> {
				JFXBattlefield.this.gameController.setLocation(card, super.getLayoutX(), super.getLayoutY());
			});
		}
	}
}
