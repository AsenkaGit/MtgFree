package asenka.mtgfree.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainPaneController {
	
	@FXML
	private Pane battlefield;
	
	
	@FXML
	private void initialize() {
		
		System.out.println("Coucou");
		
		battlefield.getChildren().addAll(new CardView(null, battlefield));
		
		
		
		battlefield.setOnMouseClicked((event) -> {
			System.out.println(event.getX() + " : " + event.getY());
			System.out.println(event.getSceneX() + " : " + event.getSceneY());
			
		});
		
	}


}
