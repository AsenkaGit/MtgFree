package asenka.mtgfree.views.javafx;

import java.util.Observable;
import java.util.Observer;

import asenka.mtgfree.controllers.MtgCardController;
import asenka.mtgfree.model.mtg.events.MtgCardLocationUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgCardTapUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgCardVisibilityUpdatedEvent;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SimpleBattlefieldCardView extends AbstractMtgCardView implements Observer {

	/**
	 * The CSS effect to add on the component when it is selected
	 */
	private static final String CSS_SELECTED_STYLE = "-fx-effect: dropshadow(  gaussian  , blue , 15 , 0.0 , 0 , 0 );";

	private ContextMenu contextMenu;

	private MenuItem tapMenuItem;

	private MenuItem untapMenuItem;

	private MenuItem hideCardMenuItem;

	private MenuItem showCardMenuItem;

	private Point2D previousCursorLocation;

	private double newPositionX;

	private double newPositionY;

	private final MtgCardController cardController;

	/**
	 * 
	 * @param cardController
	 */
	public SimpleBattlefieldCardView(MtgCardController cardController) {

		super(MEDIUM_CARD_SIZES);

		this.cardController = cardController;
		this.cardController.getCard().addObserver(this);
		this.setCardDisplayed(this.cardController.getCard());
		this.setPickOnBounds(true);

		initContextMenu();
		initMovementActions();
	}

	/**
	 * Change the style of the card if the component is selected
	 * @param selected
	 */
	public void setSelected(boolean selected) {

		super.setStyle(selected ? CSS_SELECTED_STYLE : "");
	}

	/**
	 * When the card view is tapped, it means the card is rotated with and angle of 90°
	 * around its center. 
	 * @param tapped if <code>true</code>, the card is rotated
	 */
	private void setTapped(boolean tapped) {

		super.setRotate(tapped ? 90.0 : 0.0);

		this.tapMenuItem.setDisable(tapped);
		this.untapMenuItem.setDisable(!tapped);
	}

	/**
	 * Initialize the context menu
	 */
	private void initContextMenu() {

		final boolean visible = this.cardController.getCard().isVisible();
		final boolean tapped = this.cardController.getCard().isTapped();

		// Create the context menu components
		this.contextMenu = new ContextMenu();
		this.tapMenuItem = new MenuItem("Tap");
		this.untapMenuItem = new MenuItem("Untap");
		this.tapMenuItem.setDisable(tapped);
		this.untapMenuItem.setDisable(!tapped);

		this.showCardMenuItem = new MenuItem("Show");
		this.hideCardMenuItem = new MenuItem("Hide");
		this.showCardMenuItem.setDisable(visible);
		this.hideCardMenuItem.setDisable(!visible);

		// Add the behaviors associated to each context menu item
		initContextMenuActions();

		// Add the created components to the context menu
		this.contextMenu.getItems().addAll(this.tapMenuItem, this.untapMenuItem);
		this.contextMenu.getItems().add(new SeparatorMenuItem());
		this.contextMenu.getItems().addAll(this.showCardMenuItem, this.hideCardMenuItem);
	}

	/**
	 * 
	 */
	private void initContextMenuActions() {

		this.tapMenuItem.setOnAction((event) -> this.cardController.setTapped(true));
		this.untapMenuItem.setOnAction((event) -> this.cardController.setTapped(false));
		this.showCardMenuItem.setOnAction((event) -> this.cardController.setVisible(true));
		this.hideCardMenuItem.setOnAction((event) -> this.cardController.setVisible(false));
	}

	/**
	 * 
	 */
	private void initMovementActions() {

		// When the use press a mouse button on the card view
		this.setOnMousePressed((event) -> {

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
				this.newPositionX = newPositionX;
				updateX = true;
			}

			if (newPositionY >= 0 && (newPositionY + cardHeight) <= parentHeight) {
				this.setTranslateY(newPositionY);
				this.newPositionY = newPositionY;
				updateY = true;
			}

			// Update the previous location of the cursor
			this.previousCursorLocation = new Point2D(
					updateX ? event.getSceneX() : this.previousCursorLocation.getX(),
							updateY ? event.getSceneY() : this.previousCursorLocation.getY());
		});

		//On mouse release the new location of the card is updated
		this.setOnMouseReleased((event) -> this.cardController.setLocation(newPositionX, newPositionY));
	}

	@Override
	public void setCardDisplayed(final MtgCard card) {

		// The back image must have been initialized in the constructor of the super class
		
		super.getChildren().remove(this.frontView);
			
		super.frontView = new ImageView(new Image(FILES_MANAGER.getCardImageInputStream(card)));
		super.frontView.setSmooth(true);
		super.frontView.setFitHeight(this.defaultDimensions.getHeight());
		super.frontView.setFitWidth(this.defaultDimensions.getWidth());

		super.setVisibleCardSide(card.isVisible());
		super.getChildren().add(this.frontView);
	}

	/**
	 * 
	 * @return
	 */
	public MtgCard getCard() {
		return this.cardController.getCard();
	}
	
	@Override
	public void setVisibleCardSide(boolean displayFront) {
		// Apply the default behavior of this method defined in the super class
		super.setVisibleCardSide(displayFront);
	
		// Change the availability of the context menu items
		if(this.showCardMenuItem != null && this.hideCardMenuItem != null) {
			this.showCardMenuItem.setDisable(displayFront);
			this.hideCardMenuItem.setDisable(!displayFront);
		}
	}

	@Override
	public void update(Observable observedObject, Object event) {
		
		// The observed object must be an MtgCard here. If not, well... Huston, we have a problem !
		final MtgCard card = (MtgCard) observedObject;

		if (event instanceof MtgCardTapUpdatedEvent) {
			this.setTapped(card.isTapped());
		} else if (event instanceof MtgCardLocationUpdatedEvent) {
			this.setTranslateX(card.getLocation().getX());
			this.setTranslateY(card.getLocation().getY());
		} else if (event instanceof MtgCardVisibilityUpdatedEvent) {
			this.setVisibleCardSide(card.isVisible());
		} 
	}
}
