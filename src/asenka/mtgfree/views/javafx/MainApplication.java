package asenka.mtgfree.views.javafx;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.views.CardImageSize;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		initProxy(true);
		
		JFXCard card = new JFXCard(new Card(MtgDataUtility.getInstance().getMtgCard("chalice of life")), null, CardImageSize.LARGE);
		JFXCard card2 = new JFXCard(new Card(MtgDataUtility.getInstance().getMtgCard("glorybringer")), null, CardImageSize.MEDIUM);
		JFXCard card3 = new JFXCard(new Card(MtgDataUtility.getInstance().getMtgCard("always watching")), null, CardImageSize.SMALL);
		
		Scene scene = new Scene(new FlowPane(card, card2, card3));
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
