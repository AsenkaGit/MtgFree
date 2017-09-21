package asenka.mtgfree.view;


import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class BattlefieldView extends Group {
	
	public BattlefieldView() {

		
		
		
		Image image = new Image("file:resources/images/mtg/cards/card_mtg_back.jpg");
		
		
		ImageView cardView = new ImageView(image);
		
		this.getChildren().add(cardView);
	}


}
