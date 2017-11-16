package asenka.mtgfree.views.javafx;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Observable;

import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Card;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		initProxy(true);

//		Card glorybringer = new Card(MtgDataUtility.getInstance().getMtgCard("glorybringer"));
		Card chaliceOfLife = new Card(MtgDataUtility.getInstance().getMtgCard("chalice of life"));

		final JFXCardDataPane pane = new JFXCardDataPane(chaliceOfLife);

		Scene scene = new Scene(pane);
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
