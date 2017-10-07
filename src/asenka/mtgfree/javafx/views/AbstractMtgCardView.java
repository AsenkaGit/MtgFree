package asenka.mtgfree.javafx.views;


import java.util.Observer;

import asenka.mtgfree.controllers.MtgCardController;
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
	
	protected ImageView frontView;
	
	protected ImageView backView;
	
	protected Dimension2D defaultDimensions;

	/**
	 * 
	 * @param cardController
	 * @param dimensions
	 */
	public AbstractMtgCardView(Dimension2D dimensions) {

		super();
		this.defaultDimensions = dimensions;
		initImageViews();
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
	protected abstract void initImageViews();
}
