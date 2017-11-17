package asenka.mtgfree.views.javafx;

import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

import asenka.mtgfree.controllers.game.PlayerController;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.views.CardImageSize;
import asenka.mtgfree.views.javafx.utilities.ImagesManager;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author asenka
 * @see Card
 */
public class JFXCard extends Group implements Observer {

	
	private Card card;
	
	private PlayerController playerController;
	
	private Image cardImage;
	
	private ImageView cardImageView;

	
	public JFXCard(Card card, PlayerController controller, CardImageSize cardImageSize) {
		
		this.card = card;
		this.playerController = controller;
		
		this.cardImage = ImagesManager.getImage(this.card.getPrimaryCardData());
		this.cardImageView = new ImageView();
		this.cardImageView.setSmooth(true);
		this.cardImageView.setFitHeight(cardImageSize.getHeigth());
		this.cardImageView.setFitWidth(cardImageSize.getWidth());
		this.cardImageView.setImage(this.cardImage);
		
		super.getChildren().add(this.cardImageView);
	}
	
	
	
	

	@Override
	public void update(Observable o, Object arg) {

	}
}
