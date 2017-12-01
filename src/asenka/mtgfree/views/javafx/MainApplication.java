package asenka.mtgfree.views.javafx;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		initProxy(true);

		CardsManager cm = CardsManager.getInstance();
		
		final Player player1 = new Player(1, "Player_1");
		
		player1.getLibrary().addAll(
			cm.createCard(player1, "glorybringer"),
			cm.createCard(player1, "glorybringer"),
			cm.createCard(player1, "glorybringer"));
		
		Scene scene = new Scene(new JFXPlayerHand(player1.getLibrary()));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		Application.launch(MainApplication.class);
	}

	private void initProxy(boolean needProxy) {

		if (needProxy) {

			// Uses a proxy for Internet connection
			final String authUser = "AESN1\bourrero";
			final String authPassword = "Welcome2018";
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
