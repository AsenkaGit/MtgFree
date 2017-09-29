package asenka.mtgfree.view;

import javafx.util.Duration;
import jdk.net.NetworkPermission;

import java.util.List;

import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.action.ActionProxy;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 * 
 * @author asenka
 *
 */
public class CardView extends Group {

	private final static Duration HALF_FLIP_ROTATION = Duration.seconds(0.25);

	public final static double SMALL_CARD_HEIGHT = 120d;

	public final static double MEDIUM_CARD_HEIGHT = 200d;

	public final static double LARGE_CARD_HEIGHT = 255d;

	private ImageView backView;

	private ImageView frontView;
	
	private ContextMenu contextMenu;
	
	private MenuItem tapMenuItem;
	
	private MenuItem untapMenuItem;
	
	private MenuItem hideCardMenuItem;
	
	private MenuItem showCardMenuItem;

	private Point2D previousCursorLocation;

	private boolean tapped;
	
	private boolean revealed;
	
	private boolean selected;

	public CardView(boolean revealed) {
		
		this.tapped = false;
		this.selected = false;
		this.revealed = revealed;

		initContextMenu();

		this.backView = new ImageView(new Image("file:resources/images/mtg/cards/card_mtg_back.jpg"));
		this.frontView = new ImageView(new Image("file:resources/images/mtg/cards/card_mtg_en_Alpha_Black Lotus.png"));
		this.frontView.setPreserveRatio(true);
		this.backView.setPreserveRatio(true);
		this.frontView.setSmooth(true);
		this.backView.setSmooth(true);
		this.frontView.setFitHeight(MEDIUM_CARD_HEIGHT);
		this.backView.setFitHeight(MEDIUM_CARD_HEIGHT);

		this.backView.setScaleX(0);

		this.getChildren().addAll(this.backView, this.frontView);
		this.setCursor(Cursor.HAND);

		this.setOnMousePressed((event) -> {
			
			this.setSelected(true);
	
			// Initialize the location of the cursor
			this.previousCursorLocation = new Point2D(event.getSceneX(), event.getSceneY());

			if (event.isSecondaryButtonDown()) {
				contextMenu.show(this, Side.RIGHT, 0,0);
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
			this.previousCursorLocation = new Point2D(
					updateX ? event.getSceneX() : this.previousCursorLocation.getX(),
					updateY ? event.getSceneY() : this.previousCursorLocation.getY());
		});

	}

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
	 * @return the selected
	 */
	public boolean isSelected() {

		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {

		if(selected) {
			this.setStyle("-fx-effect: dropshadow(  gaussian  , blue , 15 , 0.0 , 0 , 0 );");
		} else {
			this.setStyle("");
		}
		
		

		this.selected = selected;
	}

	/**
	 * @return the revealed
	 */
	public boolean isRevealed() {

		return revealed;
	}

	/**
	 * @param revealed the revealed to set
	 */
	public void setRevealed(boolean revealed) {

		this.revealed = revealed;
	}
}
