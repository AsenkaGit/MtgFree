package asenka.mtgfree.view;

import java.util.Observable;
import java.util.Observer;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
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
public class MtgCardView extends Group implements Observer {

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
//	private boolean selected;

	private MtgCardController controller;

	/**
	 * 
	 * @param cardController
	 */
	public MtgCardView(MtgCardController cardController) {

		this.controller = cardController;
		final MtgCard card = this.controller.getCard();
		this.tapped = card.isTapped();
//		this.selected = card.isSelected();
		card.addObserver(this);

		initContextMenu();
		initCardsImage();
		initCardViewStyle();
		initActions();

	}

	/**
	 * Initialize the context menu
	 */
	private void initContextMenu() {

		this.contextMenu = new ContextMenu();
		this.tapMenuItem = new MenuItem("Tap");
		this.untapMenuItem = new MenuItem("Untap");
		this.tapMenuItem.setDisable(this.tapped);
		this.untapMenuItem.setDisable(!this.tapped);
		this.showCardMenuItem = new MenuItem("Reveal");
		this.hideCardMenuItem = new MenuItem("Hide");
		this.tapMenuItem.setDisable(this.tapped);
		this.untapMenuItem.setDisable(!this.tapped);
		this.contextMenu.getItems().addAll(this.tapMenuItem, this.untapMenuItem);
		this.contextMenu.getItems().add(new SeparatorMenuItem());
		this.contextMenu.getItems().addAll(this.showCardMenuItem, this.hideCardMenuItem);
	}

	/**
	 * 
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
	 * 
	 */
	private void initCardViewStyle() {

		this.setCursor(Cursor.HAND);
	}

	/**
	 * 
	 */
	private void initActions() {

		this.setOnMousePressed((event) -> {

			this.setSelectedStyle(true);
			this.controller.setSelected(true);

			// Initialize the location of the cursor
			this.previousCursorLocation = new Point2D(event.getSceneX(), event.getSceneY());

			if (event.isSecondaryButtonDown()) {
				contextMenu.show(this, Side.RIGHT, 0, 0);
			}
		});

		this.setOnMouseDragged((event) -> {

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
		});

	}

	/**
	 * @param selected the selected to set
	 */
	private void setSelectedStyle(boolean selected) {

		if (selected) {
			this.setStyle("-fx-effect: dropshadow(  gaussian  , blue , 15 , 0.0 , 0 , 0 );");
		} else {
			this.setStyle("");
		}
	}

	/**
	 * 
	 * @param displayFront
	 */
	private void setVisibleImage(boolean displayFront) {

		this.backView.setVisible(!displayFront);
		this.frontView.setVisible(displayFront);
	}

	@Override
	public void update(Observable card, Object event) {

		System.out.println("CardView " + (MtgCard) card + " has been update : " + event.getClass().getSimpleName());
	}
}
