package asenka.mtgfree.views.javafx;

import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

import asenka.mtgfree.controllers.game.PlayerController;
import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.views.CardImageSize;
import asenka.mtgfree.views.javafx.utilities.ImagesManager;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

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
	
	private ContextMenu cardContextMenu;
	
	private JFXCardTooltip cardTooltip;

	
	public JFXCard(Card card, PlayerController controller, CardImageSize cardImageSize) {
		
		this.card = card;
		this.playerController = controller;
		
		MtgCard cardData = this.card.getPrimaryCardData();
		
		this.cardImage = ImagesManager.getImage(cardData.getMultiverseid());
		this.cardImageView = new ImageView();
		this.cardImageView.setSmooth(true);
		this.cardImageView.setFitHeight(cardImageSize.getHeigth());
		this.cardImageView.setFitWidth(cardImageSize.getWidth());
		this.cardImageView.setImage(this.cardImage);
		this.cardTooltip = new JFXCardTooltip(this.card.getPrimaryCardData());
		
		Tooltip.install(this, this.cardTooltip);
		
		super.getChildren().add(this.cardImageView);
	}
	
	
	private class JFXCardTooltip extends Tooltip {
		
		
		
		public JFXCardTooltip(MtgCard cardData) {

			JFXMagicText cardText = new JFXMagicText(cardData.getText());
			JFXMagicText cardCost = new JFXMagicText(cardData.getManaCost());
			Text cardName = new Text(cardData.getName());
			
			cardText.setStyle("-fx-border-color:black");
			
			GridPane gridPane = new GridPane();
			gridPane.setVgap(10d);
			gridPane.setHgap(10d);
			gridPane.setPadding(new Insets(5d, 5d, 5d, 5d));
			
			gridPane.add(cardName, 0, 0);
			gridPane.add(cardCost, 1, 0);
			gridPane.add(cardText, 0, 1);
			
			GridPane.setColumnSpan(cardText, 2);
			
			super.setGraphic(gridPane);
			super.setStyle(
				"-fx-background-radius: 2 2 2 2;" + 
				"-fx-background-color: linear-gradient(#FFFFFF, #DEDEDE);" + 
				"-fx-font-size:16px;");
			super.sizeToScene();
		}
	}
	
	

	@Override
	public void update(Observable o, Object arg) {

	}
}
