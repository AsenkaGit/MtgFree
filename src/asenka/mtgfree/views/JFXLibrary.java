package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.views.utilities.ImagesManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Component to manage the library of the local player.
 * 
 * @author asenka
 *
 */
public class JFXLibrary extends GridPane {
	
	private final GameController gameController;
	
	private final ImageView backCardImageView;
	
	
	public JFXLibrary(final GameController controller) {
		
		super();
		this.gameController = controller;
		this.backCardImageView = new ImageView(ImagesManager.IMAGE_MTG_CARD_BACK);
		this.backCardImageView.setFitHeight(CardImageSize.MEDIUM.getHeigth());
		this.backCardImageView.setFitWidth(CardImageSize.MEDIUM.getWidth());
		
		buildComponentLayout();
		addListeners();
	}
	
	
	
	private void buildComponentLayout() {
		
		super.getChildren().add(this.backCardImageView);
		GridPane.setConstraints(this.backCardImageView, 0, 0);
	}
	
	private void addListeners() {
		
		this.backCardImageView.setOnMouseClicked(event -> this.gameController.draw());
	}
}
