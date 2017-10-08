package asenka.mtgfree.views.javafx;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Basic view of an MtgCard it does not implements any observer. It takes a card and display it with the 
 * desired dimensions
 * 
 * @author asenka
 * @see MtgCard
 *
 */
public class SimpleCardView extends AbstractMtgCardView {
	
	/**
	 * The card to display
	 */
	private MtgCard card;

	/**
	 * Constructor
	 * @param dimensions
	 * @param card
	 */
	public SimpleCardView(Dimension2D dimensions, MtgCard card) {
		super(dimensions);
		
		
		if(card == null) {
			super.backView.setVisible(true);
			super.getChildren().remove(super.frontView);
		} else {
			this.card = card;
			this.setCardDisplayed(this.card);
		}
	}

	@Override
	public void setCardDisplayed(final MtgCard card) {

		super.getChildren().remove(this.frontView);
		
		super.frontView = new ImageView(new Image(FILES_MANAGER.getCardImageInputStream(card)));
		super.frontView.setSmooth(true);
		super.frontView.setFitHeight(this.defaultDimensions.getHeight());
		super.frontView.setFitWidth(this.defaultDimensions.getWidth());

		super.setVisibleCardSide(card.isVisible());
		super.getChildren().add(this.frontView);	
	}

}
