package asenka.mtgfree.views.javafx;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {


		ScrollPane pane = new ScrollPane(new JFXMagicText("Tempting offer — Return a creature card from your graveyard to the battlefield. Each opponent may return a creature card from his or her graveyard to the battlefield. For each player who does, return a creature card from your graveyard to the battlefield."));
		

		Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
		primaryStage.show();
    }

	
	
	public static void main(String[] args) {

        Application.launch(MainApplication.class);
	}

}
