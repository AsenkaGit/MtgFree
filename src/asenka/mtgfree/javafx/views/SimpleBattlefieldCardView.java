package asenka.mtgfree.javafx.views;

import java.util.Observable;

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

public class SimpleBattlefieldCardView extends AbstractMtgCardView {
	
	private static final String CSS_SELECTED_STYLE = "-fx-effect: dropshadow(  gaussian  , blue , 15 , 0.0 , 0 , 0 );";
	
	private ContextMenu contextMenu;

	private MenuItem tapMenuItem;

	private MenuItem untapMenuItem;

	private MenuItem hideCardMenuItem;

	private MenuItem showCardMenuItem;

	private Point2D previousCursorLocation;
	
	private double newPositionX;
	
	private double newPositionY;

	/**
	 * 
	 * @param cardController
	 */
	public SimpleBattlefieldCardView(MtgCardController cardController) {

		super(cardController, MEDIUM_CARD_SIZES);
		
		initContextMenu();
		initMovementActions();
	}
	
	@Override
	public void setSelected(boolean selected) {
	
		super.setStyle(selected ? CSS_SELECTED_STYLE : "");
	}
	
	@Override
	public void setVisibleCardSide(boolean displayFront) {
		super.setVisibleCardSide(displayFront);

		if(this.showCardMenuItem != null && this.hideCardMenuItem != null) {
			this.showCardMenuItem.setDisable(displayFront);
			this.hideCardMenuItem.setDisable(!displayFront);
		}
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

		final boolean visible = super.cardController.getCard().isVisible();
		final boolean tapped = super.cardController.getCard().isTapped();

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

		this.tapMenuItem.setOnAction((event) -> super.cardController.setTapped(true));
		this.untapMenuItem.setOnAction((event) -> super.cardController.setTapped(false));
		this.showCardMenuItem.setOnAction((event) -> super.cardController.setVisible(true));
		this.hideCardMenuItem.setOnAction((event) -> super.cardController.setVisible(false));
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
			this.newPositionX = this.getTranslateX() + deltaX;
			this.newPositionY = this.getTranslateY() + deltaY;

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
			this.previousCursorLocation = new Point2D(
					updateX ? event.getSceneX() : this.previousCursorLocation.getX(),
					updateY ? event.getSceneY() : this.previousCursorLocation.getY());
		});
		
		//On mouse release the new location of the card is updated
		this.setOnMouseReleased((event) -> super.cardController.setLocation(newPositionX, newPositionY));
	}

	@Override
	public void update(Observable observedObject, Object event) {

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
