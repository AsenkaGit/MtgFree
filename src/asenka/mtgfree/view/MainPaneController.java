package asenka.mtgfree.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainPaneController {
	
	@FXML
	private Pane battlefield;
	
	
	@FXML
	private void initialize() {

		battlefield.getChildren().addAll(new CardView());
		
		this.battlefield.setOnMousePressed((event) -> {

			
		});

	}


}
