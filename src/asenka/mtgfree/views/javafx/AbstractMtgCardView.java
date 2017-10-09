package asenka.mtgfree.views.javafx;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.utilities.FileManager;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class used to display a card
 * 
 * @author asenka
 * @see Group
 */
public abstract class AbstractMtgCardView extends Group {

	/**
	 * Default small dimensions for cards (80x120)
	 */
	protected static final Dimension2D SMALL_CARD_SIZES = new Dimension2D(80.0, 120.0);

	/**
	 * Default medium dimensions for cards(140x200)
	 */
	protected static final Dimension2D MEDIUM_CARD_SIZES = new Dimension2D(140.0, 200.0);

	/**
	 * Default large dimensions for cards(180x250)
	 */
	protected static final Dimension2D LARGE_CARD_SIZES = new Dimension2D(180.0, 250.0);

	/**
	 * Utility used to get input streams on card image files
	 */
	protected static final FileManager FILES_MANAGER = FileManager.getInstance();

	/**
	 * The JavaFX Image of a back of card stored once and for all to avoid reloading it every time
	 */
	protected static final Image BACK_IMAGE = new Image(FILES_MANAGER.getMtgBackInputStream());

	/**
	 * The front view of a Mtg card
	 */
	protected ImageView frontView;

	/**
	 * The back image of an mtg card
	 */
	protected ImageView backView;

	/**
	 * The default dimensions of the card view
	 */
	protected Dimension2D defaultDimensions;

	/**
	 * Constructor
	 * @param dimensions the dimensions used to display the component
	 */
	public AbstractMtgCardView(Dimension2D dimensions) {

		super();
		this.defaultDimensions = dimensions;
		
		// The front image view must be initialize in the sub classes
		this.frontView = null;
		
		// Initialize the back image
		this.backView = new ImageView(BACK_IMAGE);
		this.backView.setSmooth(false);
		this.backView.setFitHeight(dimensions.getHeight());
		this.backView.setFitWidth(dimensions.getWidth());
		
		super.getChildren().add(this.backView);
	}

	/**
	 * Select the image to display
	 * 
	 * @param displayFront if <code>true</code>, the front image is displayed
	 */
	public void setVisibleCardSide(boolean displayFront) {
		
		// If the image views are initialized
		if (this.backView != null && this.frontView != null) {
			this.backView.setVisible(!displayFront);
			this.frontView.setVisible(displayFront);
		}
	}

	/**
	 * Set the card to display according to the MtgCard
	 * @param card the MtgCard to display
	 * @see MtgCard
	 */
	public abstract void setCardDisplayed(final MtgCard card);
}
