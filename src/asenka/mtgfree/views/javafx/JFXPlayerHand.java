package asenka.mtgfree.views.javafx;

import java.util.ArrayList;
import java.util.List;

import asenka.mtgfree.model.Card;
import asenka.mtgfree.views.CardImageSize;
import asenka.mtgfree.views.javafx.utilities.ImagesManager;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class JFXPlayerHand extends ScrollPane {
	
	private ObservableList<Card> cards;
	
	private HBox horizontalLayout;
	
	public JFXPlayerHand(final ObservableList<Card> cards) {
		
		super();
		this.cards = cards;
		this.horizontalLayout = new HBox();
		this.horizontalLayout.setPadding(new Insets(10, 10, 10, 10));
		
		
		for(Card card : this.cards) {
			
			ImageView cardView = new ImageView(ImagesManager.getImage(card.getPrimaryCardData()));
			cardView.setFitHeight(CardImageSize.MEDIUM.getHeigth());
			cardView.setFitWidth(CardImageSize.MEDIUM.getWidth());
			
			cardView.setOnMouseEntered(event -> {
				cardView.setScaleX(1.1);
				cardView.setScaleY(1.1);
			});
			
			cardView.setOnMouseExited(event -> {
				cardView.setScaleX(1);
				cardView.setScaleY(1);
			});
			
			Pane imagePane = new Pane(cardView);
			imagePane.setPadding(new Insets(0, 5, 0, 5));
			
			this.horizontalLayout.getChildren().add(imagePane);
		}
		super.setContent(this.horizontalLayout);
		
		this.cards.addListener(new ListChangeListener<Card>(){

			@Override
			public void onChanged(ListChangeListener.Change<? extends Card> change) {

				Platform.runLater( () -> { 
					
					// TODO
				});
			}
		});
		
	}
}
