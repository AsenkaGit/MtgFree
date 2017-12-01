package asenka.mtgfree.views.javafx;

import java.util.ArrayList;
import java.util.List;

import asenka.mtgfree.model.Card;
import asenka.mtgfree.views.CardImageSize;
import asenka.mtgfree.views.javafx.utilities.ImagesManager;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class JFXPlayerHand extends ScrollPane {
	
	private ObservableList<Card> cards;
	
	private List<ImageView> cardViews;
	
	private HBox horizontalLayout;
	
	public JFXPlayerHand(final ObservableList<Card> cards) {
		
		super();
		this.cards = cards;
		this.horizontalLayout = new HBox();
		this.cardViews = new ArrayList<ImageView>(cards.size()); 
		
		for(Card card : this.cards) {
			
			ImageView cardView = new ImageView(ImagesManager.getImage(card.getPrimaryCardData()));
			cardView.setFitHeight(CardImageSize.MEDIUM.getHeigth());
			cardView.setFitWidth(CardImageSize.MEDIUM.getWidth());
			this.cardViews.add(cardView);
			this.horizontalLayout.getChildren().add(cardView);
		}
		super.setContent(this.horizontalLayout);
		
	}
}
