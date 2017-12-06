package asenka.mtgfree.views.javafx;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		initProxy(true);

		CardsManager cm = CardsManager.getInstance();
		
		
		final Player player1 = new Player(1, "Player_1");
		final GameController gameController = new GameController(new GameTable("Main", player1));
		gameController.createGame();
		
		player1.getHand().addAll(
			cm.createCard(player1, "glorybringer"),
			cm.createCard(player1, "forest"),
			cm.createCard(player1, "plains"),
			cm.createCard(player1, "black lotus"),
			cm.createCard(player1, "Gaea's Liege"),
			cm.createCard(player1, "Sage of Ancient Lore"),
			cm.createCard(player1, "Sage of Ancient Lore"),
			cm.createCard(player1, "Sage of Ancient Lore"),
			cm.createCard(player1, "Sage of Ancient Lore"),
			cm.createCard(player1, "Sylvan Yeti"),
			cm.createCard(player1, "Shapeshifter"),
			cm.createCard(player1, "Sarkhan Unbroken"),
			cm.createCard(player1, "Legion's Landing"),
			cm.createCard(player1, "always watching"));
		
		JFXPlayerHand hand = new JFXPlayerHand(gameController, player1);
		
		TableView<Card> battlefield = new TableView<Card>();
		TableColumn<Card, String> cardNameColumn = new TableColumn<Card, String>("Battlefield");
		cardNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
		battlefield.getColumns().add(cardNameColumn);
		battlefield.itemsProperty().bind(gameController.getGameTable().getLocalPlayer().battlefieldProperty());
		
		TableView<Card> graveyard = new TableView<Card>();
		TableColumn<Card, String> cardNameColumn2 = new TableColumn<Card, String>("Graveyard");
		cardNameColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
		graveyard.getColumns().add(cardNameColumn2);
		graveyard.itemsProperty().bind(gameController.getGameTable().getLocalPlayer().graveyardProperty());
		
		TableView<Card> exile = new TableView<Card>();
		TableColumn<Card, String> cardNameColumn3 = new TableColumn<Card, String>("Exile");
		cardNameColumn3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
		exile.getColumns().add(cardNameColumn3);
		exile.itemsProperty().bind(gameController.getGameTable().getLocalPlayer().exileProperty());
		
		Scene scene = new Scene(new VBox(new HBox(battlefield, graveyard, exile), new ScrollPane(hand)));
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Close the connection with the broker on exit
		primaryStage.setOnCloseRequest(event -> gameController.exitGame());
		
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
