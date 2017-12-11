package asenka.mtgfree.views;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Scanner;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		initProxy(true);
		CardsManager cm = CardsManager.getInstance();
		final Player player;
		final GameController gameController;
		
		System.out.println("Launch as table creator (c) ? or join (whatever...) ?");
		
		@SuppressWarnings("resource")
		String input = new Scanner(System.in).nextLine();
		
		if("c".equals(input)) {
			player = new Player(1, "Player_1");
			player.getLibrary().addAll(cm.createCard(player, "glorybringer"), cm.createCard(player, "Architect of the Untamed"),
				cm.createCard(player, "plains"), cm.createCard(player, "black lotus"), cm.createCard(player, "Gaea's Liege"),
				cm.createCard(player, "Sage of Ancient Lore"), cm.createCard(player, "Sage of Ancient Lore"),
				cm.createCard(player, "Tunneling Geopede"), cm.createCard(player, "Ashenmoor Liege"),
				cm.createCard(player, "Sylvan Yeti"), cm.createCard(player, "Shapeshifter"), cm.createCard(player, "Sarkhan Unbroken"),
				cm.createCard(player, "Legion's Landing"), cm.createCard(player, "always watching"));
			gameController = new GameController(new GameTable("Main", player));
			gameController.createGame();
		} else {
			player = new Player(2, "Player_2");
			player.getLibrary().addAll(cm.createCard(player, "glorybringer"), cm.createCard(player, "Architect of the Untamed"),
				cm.createCard(player, "plains"), cm.createCard(player, "black lotus"), cm.createCard(player, "Gaea's Liege"),
				cm.createCard(player, "Sage of Ancient Lore"), cm.createCard(player, "Sage of Ancient Lore"),
				cm.createCard(player, "Tunneling Geopede"), cm.createCard(player, "Ashenmoor Liege"),
				cm.createCard(player, "Sylvan Yeti"), cm.createCard(player, "Shapeshifter"), cm.createCard(player, "Sarkhan Unbroken"),
				cm.createCard(player, "Legion's Landing"), cm.createCard(player, "always watching"));
			gameController = new GameController(new GameTable("Main", player));
			gameController.joinGame();
		}
		JFXHand handLocalPlayer = new JFXHand(gameController, true);
		JFXHand handOtherPlayer = new JFXHand(gameController, false);
		JFXTwoPlayersBattlefields bothBattlefields = new JFXTwoPlayersBattlefields(gameController);
		JFXSelectedCardInfoPane selectedCardInfo = new JFXSelectedCardInfoPane(gameController);
		JFXLibrary library = new JFXLibrary(gameController);
		BorderPane rootPane = new BorderPane(bothBattlefields, handOtherPlayer, selectedCardInfo, handLocalPlayer, library);		
		
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> gameController.exitGame());
		primaryStage.show();
	}

	public static void main(String[] args) {

		Application.launch(MainApplication.class);
	}

	private void initProxy(boolean needProxy) {

		if (needProxy) {

			// Uses a proxy for Internet connection
			final String authUser = "AESN1\bourrero";
			final String authPassword = "Welcome2019";
			Authenticator.setDefault(new Authenticator() {

				@Override
				public PasswordAuthentication getPasswordAuthentication() {

					return new PasswordAuthentication(authUser, authPassword.toCharArray());
				}
			});
			System.setProperty("http.proxyHost", "140.9.9.249");
			System.setProperty("http.proxyPort", "8080");
			System.setProperty("http.proxyUser", authUser);
			System.setProperty("http.proxyPassword", authPassword);
		}
	}
}
