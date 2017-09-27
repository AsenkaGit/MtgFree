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

	private Point2D dragAnchor;

	private boolean displayFront = true;

	private double currentScale;

	private SequentialTransition animation;

	public CardView(MtgCard card, Pane parent) {
		

		this.card = card;
		this.back = new ImageView(new Image("file:resources/images/mtg/cards/card_mtg_back.jpg"));
		this.front = new ImageView(new Image("file:resources/images/mtg/cards/card_mtg_en_Alpha_Black Lotus.png"));
		this.setScale(SCALE_SMALL);
		this.getChildren().addAll(this.back, this.front);

		// Hide the back
		this.back.setScaleX(0);
		this.setCursor(Cursor.HAND);
		
//		this.setTranslateX(100);
		

		this.setOnMouseDragged((event) -> {
			
		});

		this.setOnMousePressed((event) -> {
			
			System.out.println("LayoutX/Y :");
			System.out.println(this.getLayoutX());
			System.out.println(this.getLayoutY());
			System.out.println("Translate X/Y :");
			System.out.println(this.getTranslateX());
			System.out.println(this.getTranslateY());
			
			
		});

		
	}

	/**
	 * Update the scale of the displayed card
	 * @param scale a double value >= 0
	 */
	public void setScale(double scale) {

		this.currentScale = scale;
		this.back.setScaleX(scale);
		this.back.setScaleY(scale);
		this.front.setScaleX(scale);
		this.front.setScaleY(scale);
	}

}
