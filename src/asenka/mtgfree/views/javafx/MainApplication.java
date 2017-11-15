package asenka.mtgfree.views.javafx;


import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Card;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Stage;


public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

//		JFXMagicText text = new JFXMagicText("{2}{R}{U}{G} Fly\nTempting offer — Return a creature card from your graveyard to the battlefield.\n{2}{R}{U}{G}Each opponent may return a creature card from his or her graveyard to the battlefield. For each player who does, return a creature card from your graveyard to the battlefield.");
//		ScrollPane pane = new ScrollPane(text);
//		pane.setHbarPolicy(ScrollBarPolicy.NEVER);
//		pane.setFitToWidth(true);
		
		JFXSelectedCardPane pane = new JFXSelectedCardPane(new Card(MtgDataUtility.getInstance().getMtgCard("glorybringer")));
		
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
	
	public static void main(String[] args) {

        Application.launch(MainApplication.class);
	}

}
