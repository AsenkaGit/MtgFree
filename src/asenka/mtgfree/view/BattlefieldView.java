package asenka.mtgfree.view;



import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;


public class BattlefieldView extends Pane {
	
	public BattlefieldView() {
		this.setBackground(new Background(new BackgroundImage(new Image("file:resources/images/mtg/gui_mtg_background.jpg"), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		this.getChildren().add(new CardView(null, this));
	}


}
