package asenka.mtgfree.views;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		initProxy(true);

		CardsManager cm = CardsManager.getInstance();

		final Player player1 = new Player(1, "Player_1");
		final Player player2 = new Player(2, "Player_2");
		final GameController gameController = new GameController(new GameTable("Main", player1));
		gameController.createGame();

		Card cardVisible = cm.createCard(player1, "Legion's Landing");
		cardVisible.setVisible(true);

		player1.getHand().addAll(cardVisible, cm.createCard(player1, "glorybringer"), cm.createCard(player1, "Architect of the Untamed"),
			cm.createCard(player1, "plains"), cm.createCard(player1, "black lotus"), cm.createCard(player1, "Gaea's Liege"),
			cm.createCard(player1, "Sage of Ancient Lore"), cm.createCard(player1, "Sage of Ancient Lore"),
			cm.createCard(player1, "Tunneling Geopede"), cm.createCard(player1, "Ashenmoor Liege"),
			cm.createCard(player1, "Sylvan Yeti"), cm.createCard(player1, "Shapeshifter"), cm.createCard(player1, "Sarkhan Unbroken"),
			cm.createCard(player1, "Legion's Landing"), cm.createCard(player1, "always watching"));
		
		player2.getHand().addAll(cardVisible, cm.createCard(player2, "glorybringer"), cm.createCard(player2, "Architect of the Untamed"),
			cm.createCard(player2, "plains"), cm.createCard(player2, "black lotus"));

		JFXHand hand = new JFXHand(gameController, player1);
		JFXHand hand2 = new JFXHand(gameController, player2);
		JFXBattlefield battlefield = new JFXBattlefield(gameController, player1.getBattlefield());
		JFXSelectedCardInfoPane selectionPane = new JFXSelectedCardInfoPane(gameController.getGameTable().getSelectedCards());
		JFXSelectedCardInfoPane selectionPane2 = new JFXSelectedCardInfoPane(gameController.getGameTable().getSelectedCards());
		
		BorderPane rootPane = new BorderPane(battlefield, hand2, selectionPane, hand, selectionPane2);		
		
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> gameController.exitGame());
		primaryStage.show();

		new Thread(() -> {
			try {
				Thread.sleep(2000);
				
				Platform.runLater(() -> {
					gameController.changeCardContext(cardVisible, Context.HAND, Context.BATTLEFIELD, 0, true);
				});

				Thread.sleep(2000);

				Platform.runLater(() -> {
					cardVisible.setVisible(true);
				});
				
				Thread.sleep(2000);

				Platform.runLater(() -> {
					cardVisible.setLocation(50, 50);
				});

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
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
