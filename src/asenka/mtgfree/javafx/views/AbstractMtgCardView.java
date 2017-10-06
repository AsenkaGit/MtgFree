package asenka.mtgfree.javafx.views;


import java.util.Observer;

import asenka.mtgfree.javafx.controllers.MtgCardController;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.utilities.FileManager;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author asenka
 * 
 */
public abstract class AbstractMtgCardView extends Group implements Observer {
	
	protected static final Dimension2D SMALL_CARD_SIZES = new Dimension2D(80.0, 120.0);

	protected static final Dimension2D MEDIUM_CARD_SIZES = new Dimension2D(140.0, 200.0);

	protected static final Dimension2D LARGE_CARD_SIZES = new Dimension2D(180.0, 250.0);
	
	protected static final FileManager FILES_MANAGER = FileManager.getInstance();
	
	protected static final Image BACK_IMAGE = new Image(FILES_MANAGER.getMtgBackInputStream());
	
	protected final MtgCardController cardController;
	
	protected ImageView frontView;
	
	protected ImageView backView;
	
	protected Dimension2D defaultDimensions;

	/**
	 * 
	 * @param cardController
	 * @param dimensions
	 */
	public AbstractMtgCardView(MtgCardController cardController, Dimension2D dimensions) {

		super();
		this.cardController = cardController;
		this.defaultDimensions = dimensions;
		
		initImageViews();
		
		this.cardController.getCard().addObserver(this);
	}
	
	/**
	 * Select the image to display 
	 * @param displayFront if <code>true</code>, the front image is displayed
	 */
	public void setVisibleCardSide(boolean displayFront) {

		this.backView.setVisible(!displayFront);
		this.frontView.setVisible(displayFront);
	}
	
	
	/**
	 * The cards view must implements the way to behave when the get selected
	 * @param selected <code>true</code> if the card is selected
	 */
	public abstract void setSelected(boolean selected);

	/**
	 * Initialize the front and the back image views with the set dimensions
	 */
	private void initImageViews() {
		
		final MtgCard card = this.cardController.getCard();
		
		this.frontView = new ImageView(new Image(FILES_MANAGER.getCardImageInputStream(card)));
		this.frontView.setSmooth(true);
		this.frontView.setFitHeight(this.defaultDimensions.getHeight());
		this.frontView.setFitWidth(this.defaultDimensions.getWidth());
		
		this.backView = new ImageView(BACK_IMAGE);
		this.backView.setSmooth(false);
		this.backView.setFitHeight(this.defaultDimensions.getHeight());
		this.backView.setFitWidth(this.defaultDimensions.getWidth());
		
		setVisibleCardSide(card.isVisible());
		this.getChildren().addAll(this.backView, this.frontView);
	}
}
