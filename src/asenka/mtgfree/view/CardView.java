package asenka.mtgfree.view;

import javafx.util.Duration;
import jdk.net.NetworkPermission;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

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

	private Point2D previousCursorLocation;
	
	private boolean tapped = false;

	public CardView() {

		this.backView = new ImageView(new Image("file:resources/images/mtg/cards/card_mtg_back.jpg"));
		this.frontView = new ImageView(new Image("file:resources/images/mtg/cards/card_mtg_en_Alpha_Black Lotus.png"));
		this.frontView.setPreserveRatio(true);
		this.backView.setPreserveRatio(true);
		this.frontView.setSmooth(true);
		this.backView.setSmooth(true);
		this.frontView.setFitHeight(SMALL_CARD_HEIGHT);
		this.backView.setFitHeight(SMALL_CARD_HEIGHT);

		this.backView.setScaleX(0);

		this.getChildren().addAll(this.backView, this.frontView);
		this.setCursor(Cursor.HAND);

		this.setOnMousePressed((event) -> {
			
			// Initialize the location of the cursor
			this.previousCursorLocation = new Point2D(event.getSceneX(), event.getSceneY());
			
			if (event.isAltDown()) {
				
				if(!tapped) {
					this.frontView.getTransforms().add(new Rotate(90, event.getX(), event.getY()));
					tapped = true;
				} else {
					this.frontView.getTransforms().clear();
					tapped = false;
				}
			} 
		});

		this.setOnMouseDragged((event) -> {

			// Calculate the new card location. deltaX/Y is the v
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
}
