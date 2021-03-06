package asenka.mtgfree.views;

import java.util.Scanner;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		initProxy(true);

		final Player player;
		final GameController gameController;

		System.out.println("Launch as table creator (c) ? or join (whatever...) ?");

		@SuppressWarnings("resource")
		String input = new Scanner(System.in).nextLine();

		if ("c".equals(input)) {
			player = new Player(1, "Asenka");
			setLibraryA(player);
			gameController = new GameController(new GameTable("Main", player));
			gameController.createGame();
		} else {
			player = new Player(2, "Gros Bébé");
			setLibraryA(player);
			gameController = new GameController(new GameTable("Main", player));
			gameController.joinGame();
		}
		JFXHand handLocalPlayer = new JFXHand(gameController, true);
		JFXHand handOtherPlayer = new JFXHand(gameController, false);
		JFXTwoPlayersBattlefields bothBattlefields = new JFXTwoPlayersBattlefields(gameController);
//		JFXBattlefield localBattlefield = new JFXBattlefield(gameController, true);
		JFXSelectedCardInfoPane selectedCardInfo = new JFXSelectedCardInfoPane(gameController);
		JFXLibrary library = new JFXLibrary(gameController);
		BorderPane rootPane = new BorderPane(bothBattlefields, handOtherPlayer, selectedCardInfo, handLocalPlayer, new VBox(library, new JFXGameEventLogger(gameController)));
		rootPane.setPrefSize(1200, 800);
		
		
		Scene scene = new Scene(rootPane);
		scene.getStylesheets().add("DarkTheme.css");
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("[MTG FREE] - " + player.getName());
		primaryStage.setOnCloseRequest(event -> gameController.exitGame());
		primaryStage.show();
	}

	public static void main(String[] args) {

		Application.launch(MainApplication.class);
	}

	private static void setLibraryA(final Player player) {

		CardsManager cm = CardsManager.getInstance();

		player.getLibrary().addAll(
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "plains"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "mountain"),
			cm.createCard(player, "glorybringer"), 
			cm.createCard(player, "glorybringer"), 
			cm.createCard(player, "glorybringer"), 
			cm.createCard(player, "glorybringer"),
			cm.createCard(player, "Appeal"),
			cm.createCard(player, "Appeal"),
			cm.createCard(player, "Appeal"),
			cm.createCard(player, "Appeal"),
			cm.createCard(player, "Release the Gremlins"),
			cm.createCard(player, "Release the Gremlins"),
			cm.createCard(player, "Release the Gremlins"),
			cm.createCard(player, "Release the Gremlins"),
			cm.createCard(player, "Harnessed Lightning"),
			cm.createCard(player, "Harnessed Lightning"),
			cm.createCard(player, "Harnessed Lightning"),
			cm.createCard(player, "Harnessed Lightning"),
			cm.createCard(player, "Eddytrail Hawk"),
			cm.createCard(player, "Eddytrail Hawk"),
			cm.createCard(player, "Eddytrail Hawk"),
			cm.createCard(player, "Eddytrail Hawk"),
			cm.createCard(player, "Aviary Mechanic"),
			cm.createCard(player, "Aviary Mechanic"),
			cm.createCard(player, "Aviary Mechanic"),
			cm.createCard(player, "Aviary Mechanic"),
			cm.createCard(player, "In Oketra's Name"),
			cm.createCard(player, "In Oketra's Name"),
			cm.createCard(player, "In Oketra's Name"),
			cm.createCard(player, "In Oketra's Name"),
			cm.createCard(player, "Anointer Priest"),
			cm.createCard(player, "Anointer Priest"),
			cm.createCard(player, "Anointer Priest"),
			cm.createCard(player, "Anointer Priest")
			);

	}

	private void initProxy(boolean needProxy) {

		if (needProxy) {

			// TODO
		}
	}
}
