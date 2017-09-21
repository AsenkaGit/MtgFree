package asenka.mtgfree.view;

import javafx.util.Duration;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * 
 * @author asenka
 *
 */
public class CardView extends Group {

	public static final double SCALE_LARGE = 1d;

	public static final double SCALE_NORMAL = 0.8d;

	public static final double SCALE_SMALL = 0.4d;

	private final static Duration HALF_FLIP_ROTATION = Duration.seconds(0.25);

	private ImageView back;

	private ImageView front;

	private MtgCard card;
	
	private double initX;
	private double initY;
	private Point2D dragAnchor;
	private boolean displayFront = true;
	private double currentScale;
	
	

	private SequentialTransition animation;

	public CardView(MtgCard card, Pane parent) {

		
		
		this.card = card;
		this.back = new ImageView(new Image("file:resources/images/mtg/cards/card_mtg_back.jpg"));
		this.front = new ImageView(new Image("file:resources/images/mtg/cards/card_mtg_en_Alpha_Black Lotus.png"));
		setScale(SCALE_SMALL);
		
		this.back.setScaleX(0);
		
		this.setCursor(Cursor.HAND);
		this.animation = new SequentialTransition(flip(front, back));
		animation.setCycleCount(1);
		
		
		
		this.setOnMouseClicked((event) -> {
			
			if (event.isControlDown()) {
				if(displayFront) {
					animation = new SequentialTransition(flip(front, back));
				} else {
					animation = new SequentialTransition(flip(back, front));
				}
				displayFront = !displayFront;
				animation.play();
			}
		});
		
		this.setOnMouseDragged((event) -> {
			double dragX = event.getSceneX() - dragAnchor.getX();
			double dragY = event.getSceneY() - dragAnchor.getY();
			// calculate new position of the circle
			double newXPosition = initX + dragX;
			double newYPosition = initY + dragY;
			if ((newXPosition >= 0) && (newXPosition <= parent.getWidth() - this.back.getFitWidth())) {
				this.setTranslateX(newXPosition);
			}
			if ((newYPosition >= 0) && (newYPosition <= parent.getHeight() - this.back.getFitHeight())) {
				this.setTranslateY(newYPosition);
			}
		});
		
		this.setOnMousePressed((event) -> {
			initX = this.getTranslateX();
            initY = this.getTranslateY();
            dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
		});

		this.getChildren().addAll(this.back, this.front);
	}



	
	
	public void setScale(double scale) {
		this.currentScale = scale;
		this.back.setScaleX(scale);
		this.back.setScaleY(scale);
		this.front.setScaleX(scale);
		this.front.setScaleY(scale);
	}


	private Transition flip(Node front, Node back) {

		// Nous faisons disparaitre la face avant.
		final ScaleTransition scaleOutFront = new ScaleTransition(HALF_FLIP_ROTATION, front);
		scaleOutFront.setFromX(this.currentScale); 
		scaleOutFront.setToX(0); 

		final ScaleTransition scaleInBack = new ScaleTransition(HALF_FLIP_ROTATION, back);
		scaleInBack.setFromX(0);
		scaleInBack.setToX(this.currentScale);

		// On créé la tansition séquentielle contenant les 2 animations
		return new SequentialTransition(scaleOutFront, scaleInBack);
	}

}
