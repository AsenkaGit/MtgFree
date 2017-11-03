package asenka.mtgfree.view.tests;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import asenka.mtgfree.communication.NetworkEventManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestUIApplication extends Application {

	private Stage primaryStage;

	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Mtg Free [TEST GAME]");
		
		initRootLayout();

	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		NetworkEventManager.getInstance().endGame();
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {

		return primaryStage;
	}

	/**
	 * Initializes the root layout.
	 */
	private void initRootLayout() {

		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TestUIApplication.class.getResource("TestGameApplication.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

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

		// Launch the JavaFX application
		launch(args);
	}
}
