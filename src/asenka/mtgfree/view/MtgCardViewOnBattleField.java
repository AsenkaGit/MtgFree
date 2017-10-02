package asenka.mtgfree.view;

import java.util.Observable;
import java.util.Observer;

import asenka.mtgfree.model.mtg.events.AbstractMtgCardEvent;
import asenka.mtgfree.model.mtg.events.ChangeMtgCardContextEvent;
import asenka.mtgfree.model.mtg.events.MoveMtgCardEvent;
import asenka.mtgfree.model.mtg.events.MtgCardSelectionEvent;
import asenka.mtgfree.model.mtg.events.TappedMtgCardEvent;
import asenka.mtgfree.model.mtg.events.VisibilityMtgCardEvent;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;
import asenka.mtgfree.utilities.FileManager;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author asenka
 *
 */
public class MtgCardViewOnBattleField extends Group implements Observer {

	// private final static Duration HALF_FLIP_ROTATION = Duration.seconds(0.25);

	public static final double SMALL_CARD_HEIGHT = 120d;

	public static final double MEDIUM_CARD_HEIGHT = 200d;

	public static final double LARGE_CARD_HEIGHT = 255d;

	private static final FileManager FILE_MANAGER = FileManager.getInstance();

	public static final Image MTGCARD_BACK_IMAGE = new Image(FILE_MANAGER.getMtgBackInputStream());

	private ImageView backView;

	private ImageView frontView;

	private ContextMenu contextMenu;

	private MenuItem tapMenuItem;

	private MenuItem untapMenuItem;

	private MenuItem hideCardMenuItem;

	private MenuItem showCardMenuItem;

	private Point2D previousCursorLocation;

	private boolean tapped;

	// private boolean displayFront;
	//
	// private boolean selected;

	private MtgCardController controller;

	private MenuItem graveyardMenuItem;

	private MenuItem exileMenuItem;

	private MenuItem battlefieldMenuItem;

	/**
	 * 
	 * @param cardController
	 */
	public MtgCardViewOnBattleField(MtgCardController cardController) {

		this.controller = cardController;
		final MtgCard card = this.controller.getCard();
		this.tapped = card.isTapped();
		// this.selected = card.isSelected();
		card.addObserver(this);

		initContextMenu();
		initCardsImage();
		
		initCardViewStyle();
		initActions();

		controller.setSelected(true);

	}

	/**
	 * Initialize the context menu
	 */
	private void initContextMenu() {

		final boolean visible = this.controller.getCard().isVisible();

		this.contextMenu = new ContextMenu();
		this.tapMenuItem = new MenuItem("Tap");
		this.untapMenuItem = new MenuItem("Untap");
		this.tapMenuItem.setDisable(this.tapped);
		this.untapMenuItem.setDisable(!this.tapped);
		
		this.showCardMenuItem = new MenuItem("Show");
		this.hideCardMenuItem = new MenuItem("Hide");
		this.showCardMenuItem.setDisable(visible);
		this.hideCardMenuItem.setDisable(!visible);
		
		this.graveyardMenuItem = new MenuItem("to Graveyard");
		this.exileMenuItem = new MenuItem("to Exile");
		this.battlefieldMenuItem = new MenuItem("to Battlefield");
		
		switch(this.controller.getCard().getContext()) {
			case BATTLEFIELD: 
				this.graveyardMenuItem.setDisable(false);
				this.exileMenuItem.setDisable(false);
				this.battlefieldMenuItem.setDisable(true);
				break;
			case GRAVEYARD:
				this.graveyardMenuItem.setDisable(true);
				this.exileMenuItem.setDisable(false);
				this.battlefieldMenuItem.setDisable(false);
				this.tapMenuItem.setDisable(true);
				this.untapMenuItem.setDisable(true);
				this.showCardMenuItem.setDisable(true);
				this.hideCardMenuItem.setDisable(true);
				break;
			case EXILE:
				this.graveyardMenuItem.setDisable(false);
				this.exileMenuItem.setDisable(true);
				this.battlefieldMenuItem.setDisable(false);
				this.tapMenuItem.setDisable(true);
				this.untapMenuItem.setDisable(true);
				break;
			default: 
				break;	
		}
		initContextMenuActions();

		this.contextMenu.getItems().addAll(this.tapMenuItem, this.untapMenuItem);
		this.contextMenu.getItems().add(new SeparatorMenuItem());
		this.contextMenu.getItems().addAll(this.showCardMenuItem, this.hideCardMenuItem);
		this.contextMenu.getItems().add(new SeparatorMenuItem());
		this.contextMenu.getItems().addAll(this.battlefieldMenuItem, this.graveyardMenuItem, this.exileMenuItem);
	}

	/**
	 * 
	 */
	private void initContextMenuActions() {

		this.tapMenuItem.setOnAction((event) -> this.controller.setTapped(true));
		this.untapMenuItem.setOnAction((event) -> this.controller.setTapped(false));

		this.showCardMenuItem.setOnAction((event) -> this.controller.setVisible(true));
		this.hideCardMenuItem.setOnAction((event) -> this.controller.setVisible(false));
		
		this.battlefieldMenuItem.setOnAction((event) -> this.controller.setContext(MtgContext.BATTLEFIELD));
		this.graveyardMenuItem.setOnAction((event) -> this.controller.setContext(MtgContext.GRAVEYARD));
		this.exileMenuItem.setOnAction((event) -> this.controller.setContext(MtgContext.EXILE));

	}

	/**
	 * Initialize the cards front & back images (from the MtgCard)
	 */
	private void initCardsImage() {

		final MtgCard card = this.controller.getCard();

		this.frontView = new ImageView(new Image(FILE_MANAGER.getCardImageInputStream(card)));
		this.frontView.setPreserveRatio(true);
		this.frontView.setSmooth(true);

		this.backView = new ImageView(MTGCARD_BACK_IMAGE);
		this.backView.setPreserveRatio(true);
		this.backView.setSmooth(true);

		this.frontView.setFitHeight(MEDIUM_CARD_HEIGHT);
		this.backView.setFitHeight(MEDIUM_CARD_HEIGHT);

		this.setVisibleImage(card.isVisible());

		this.getChildren().addAll(this.backView, this.frontView);
	}

	/**
	 * Initialize some cosmetic features on the card view
	 */
	private void initCardViewStyle() {

		this.setCursor(Cursor.HAND);
	}

	/**
	 * Initialize the actions available on the card view component (on the battlefield)
	 */
	private void initActions() {
		
		// When the use press a mouse button on the card view
		this.setOnMousePressed((event) -> {
			this.controller.setSelected(true);
			this.setSelected(true);

			// Initialize the location of the cursor
			this.previousCursorLocation = new Point2D(event.getSceneX(), event.getSceneY());

			if (event.isSecondaryButtonDown()) {
				contextMenu.show(this, Side.RIGHT, 0, 0);
			}
		});

		// When the use maintain the button pressed and drag the card to move it
		this.setOnMouseDragged((event) -> {
			// When you drag a card view, the context menu is hidden 
			contextMenu.hide();

			// Calculate the new card location.
			final double deltaX = event.getSceneX() - previousCursorLocation.getX();
			final double deltaY = event.getSceneY() - previousCursorLocation.getY();
			final double newPositionX = this.getTranslateX() + deltaX;
			final double newPositionY = this.getTranslateY() + deltaY;

			// Get the necessary values to control the new card location
			final Parent battlefieldPane = this.getParent();
			final double parentWidth = battlefieldPane.getBoundsInParent().getWidth();
			final double parentHeight = battlefieldPane.getBoundsInParent().getHeight();
			final double cardWidth = this.getBoundsInParent().getWidth();
			final double cardHeight = this.getBoundsInParent().getHeight();

			// This values are useful to manage the case when the cursor goes outside
			// the battlefield pane component
			boolean updateX = false;
			boolean updateY = false;

			// Control and update the card location
			if (newPositionX >= 0 && (newPositionX + cardWidth) <= parentWidth) {
				this.setTranslateX(newPositionX);
				updateX = true;
			}

			if (newPositionY >= 0 && (newPositionY + cardHeight) <= parentHeight) {
				this.setTranslateY(newPositionY);
				updateY = true;
			}

			// Update the previous location of the cursor
			this.previousCursorLocation = new Point2D(updateX ? event.getSceneX() : this.previousCursorLocation.getX(),
					updateY ? event.getSceneY() : this.previousCursorLocation.getY());
			
			// Update the location of the card in the model
			controller.setLocation(newPositionX, newPositionY);
		});

	}

	/**
	 * @param selected the selected to set
	 */
	private void setSelected(boolean selected) {

		if (selected) {
			this.setStyle("-fx-effect: dropshadow(  gaussian  , blue , 15 , 0.0 , 0 , 0 );");
		} else {
			this.setStyle("");
		}
	}

	/**
	 * Select the image to display 
	 * @param displayFront if <code>true</code>, the front image is displayed
	 */
	private void setVisibleImage(boolean displayFront) {

		this.backView.setVisible(!displayFront);
		this.frontView.setVisible(displayFront);

		this.showCardMenuItem.setDisable(displayFront);
		this.hideCardMenuItem.setDisable(!displayFront);
	}

	/**
	 * When the card view is tapped, it means the card is rotated with and angle of 90°
	 * around its center. 
	 * @param tapped if <code>true</code>, the card is rotated
	 */
	private void setTapped(boolean tapped) {

		if (tapped) {
			this.setRotate(90);
		} else {
			this.setRotate(0);
		}
		this.tapMenuItem.setDisable(tapped);
		this.untapMenuItem.setDisable(!tapped);
	}
	
	/**
	 * Update the context menu items according to the card context
	 * @param context the card context
	 */
	private void setContextMenuItemsEnable(MtgContext context) {
		
		switch(context) {
			case BATTLEFIELD: 
				this.graveyardMenuItem.setDisable(false);
				this.exileMenuItem.setDisable(false);
				this.battlefieldMenuItem.setDisable(true);
				this.setTapped(this.controller.getCard().isTapped());
				this.setVisibleImage(this.controller.getCard().isVisible());
				break;
			case GRAVEYARD:
				this.graveyardMenuItem.setDisable(true);
				this.exileMenuItem.setDisable(false);
				this.battlefieldMenuItem.setDisable(false);
				this.tapMenuItem.setDisable(true);
				this.untapMenuItem.setDisable(true);
				this.showCardMenuItem.setDisable(true);
				this.hideCardMenuItem.setDisable(true);
				break;
			case EXILE:
				this.graveyardMenuItem.setDisable(false);
				this.exileMenuItem.setDisable(true);
				this.battlefieldMenuItem.setDisable(false);
				this.tapMenuItem.setDisable(true);
				this.untapMenuItem.setDisable(true);
				this.setVisibleImage(this.controller.getCard().isVisible());
				break;
			default: 
				break;	
		}
	}

	@Override
	public void update(Observable observedObject, Object event) {

		final MtgCard card = (MtgCard) observedObject;

		if (event instanceof TappedMtgCardEvent) {
			this.setTapped(card.isTapped());
		} else if (event instanceof MoveMtgCardEvent) {
			// TODO Manage movement event
		} else if (event instanceof VisibilityMtgCardEvent) {
			this.setVisibleImage(card.isVisible());
		} else if (event instanceof ChangeMtgCardContextEvent) {

			ChangeMtgCardContextEvent contextEvent = (ChangeMtgCardContextEvent) event;
			
			// When a card change its context, several related events can happen
			for (AbstractMtgCardEvent relatedEvent : contextEvent.getRelatedEvents()) {
				this.update(observedObject, relatedEvent);
			}
			setContextMenuItemsEnable(card.getContext());
		}
	}
}
