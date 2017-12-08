package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Card;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;

public class JFXBattlefield extends Pane {

	private final ObservableList<Card> battlefieldCards;

	private final ObservableMap<Card, JFXBattlefieldCardView> battlefieldCardViews;

	private final GameController gameController;
	
	private final Group cardsArea;

	public JFXBattlefield(final GameController gameController, final ObservableList<Card> battlefield) {

		this.battlefieldCards = battlefield;
		this.battlefieldCardViews = FXCollections.<Card, JFXBattlefieldCardView> observableHashMap();
		this.gameController = gameController;
		this.cardsArea = new Group();

		buildComponentLayout();

		this.battlefieldCards.addListener((ListChangeListener.Change<? extends Card> change) -> {

			while (change.next()) {

				if (change.wasAdded()) {
					change.getAddedSubList().forEach(card -> {
						JFXBattlefieldCardView battlefieldCardView = new JFXBattlefieldCardView(card, CardImageSize.MEDIUM);
						this.battlefieldCardViews.put(card, battlefieldCardView);
						this.cardsArea.getChildren().add(battlefieldCardView);
					});
				} else if (change.wasRemoved()) {
					change.getRemoved().forEach(card -> {
						JFXBattlefieldCardView battlefieldCardView = this.battlefieldCardViews.remove(card);
						this.cardsArea.getChildren().remove(battlefieldCardView);
					});
				}
			}
		});
	}

	private void buildComponentLayout() {

		super.getChildren().add(this.cardsArea);
		super.setStyle("-fx-border-color: blue");
	}

	private class JFXBattlefieldCardView extends JFXCardView {

		private double deltaX;

		private double deltaY;

		private JFXBattlefieldCardView(final Card card, CardImageSize size) {

			super(card, size);
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
			final MenuItem otherSideMenuItem = JFXCardView.findMenuItemByID(getContextMenu(), JFXCardView.OTHER_SIDE_MENU_ITEM_ID);
			
			if(otherSideMenuItem != null) {
				otherSideMenuItem.setDisable(!card.isVisible());
			}
			
			
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

			
			final MenuItem visibleMenuItem = new MenuItem(card.isVisible() ? "Hide" : "Show");
			visibleMenuItem.setOnAction(event -> {

				if ("Hide".equals(visibleMenuItem.getText())) {
					gameController.setVisible(card, false);
					
					if(otherSideMenuItem != null) {
						otherSideMenuItem.setDisable(true);
					}
					visibleMenuItem.setText("Show");
				} else {
					gameController.setVisible(card, true);
					visibleMenuItem.setText("Hide");
					
					if(otherSideMenuItem != null) {
						otherSideMenuItem.setDisable(false);
					}
				}

			});

			final MenuItem destroyMenuItem = new MenuItem("Destroy");
			destroyMenuItem.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.GRAVEYARD, 0, false));

			final MenuItem exileMenuItem = new MenuItem("Exile");
			exileMenuItem.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.EXILE, 0, false));
			
			final MenuItem handMenuItem = new MenuItem("Hand");
			handMenuItem.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.HAND, 0, true));
			
			final MenuItem aboveMenuItem = new MenuItem("Above");
			aboveMenuItem.setOnAction(event -> this.toFront());
			
			final MenuItem underMenuItem = new MenuItem("Under");
			underMenuItem.setOnAction(event -> this.toBack());

			final Menu libraryMenuItem = new Menu("To Library");
			final MenuItem topLibraryMenuItem = new MenuItem("Top");
			final MenuItem bottomLibraryMenuItem = new MenuItem("Bottom");
			libraryMenuItem.getItems().addAll(topLibraryMenuItem, bottomLibraryMenuItem);
			topLibraryMenuItem
				.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.LIBRARY, GameController.TOP, true));
			bottomLibraryMenuItem
				.setOnAction(event -> gameController.changeCardContext(card, Context.BATTLEFIELD, Context.LIBRARY, GameController.BOTTOM, true));

			super.getContextMenu().getItems().addAll(tapMenuItem, visibleMenuItem, destroyMenuItem, exileMenuItem, handMenuItem, libraryMenuItem,
				aboveMenuItem, underMenuItem);
		}

		/**
		 * Add the listeners on the card displayed to update the card view when the card properties change
		 */
		private void initializeCardListeners() {

			final Card card = super.getCard();

			// Rotate the card view when the tap property is updated on the card
			card.tappedProperty().addListener(observable -> {

				BooleanProperty tappedProperty = (BooleanProperty) observable;
				if (tappedProperty.get()) {
					this.setRotate(90d);
				} else {
					this.setRotate(0d);
				}
			});

			// Show/hide the card when the visible property of the card is updated
			card.visibleProperty().addListener(observable -> {

				BooleanProperty visibleProperty = (BooleanProperty) observable;
				if (visibleProperty.get()) {
					this.selectSide(Side.FRONT);
				} else {
					this.selectSide(Side.BACK);
				}
			});

			// Moves the card view when the card coordinates are updated
			card.locationProperty().addListener(observable -> {

				@SuppressWarnings("unchecked")
				ObjectProperty<Point2D> locationProperty = (ObjectProperty<Point2D>) observable;

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

				JFXBattlefield.this.gameController.setSelectedCards(card);
				this.deltaX = event.getX();
				this.deltaY = event.getY();
			});

			// When the mouse moves while the button is pressed, the card view component position follow the
			// the mouse coordinates
			super.setOnMouseDragged(event -> {

				final double newLayoutX = event.getSceneX() - this.deltaX;
				final double newLayoutY = event.getSceneY() - this.deltaY;

				if (newLayoutX > 0) {
					super.setLayoutX(newLayoutX - super.getLayoutBounds().getMinX());
				}

				if (newLayoutY > 0) {
					super.setLayoutY(newLayoutY - super.getLayoutBounds().getMaxY());
				}
			});

			// When the mouse is released, the game controller update the location of the card
			super.setOnMouseReleased(event -> {
				gameController.setLocation(card, getLayoutX(), getLayoutY());
			});
		}
	}
}
