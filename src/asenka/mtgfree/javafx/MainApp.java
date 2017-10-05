package asenka.mtgfree.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This is the entry point of the MtgFree application.
 * 
 * Where everything starts ^^
 * 
 * @author asenka
 *
 */
public class MainApp extends Application {
	
//	/**
//	 * log4j logger to trace application behavior
//	 */
//	private static final Logger LOGGER = Logger.getLogger(MainApp.class);

	/**
	 * The stage where all the javafx components are displayed
	 */
	private Stage primaryStage;
	
	/**
	 * The default pane directly added on the scene
	 */
	private BorderPane rootPane;

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.rootPane = getRootPane();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MTG Free [Under construction]");
		this.primaryStage.setScene(new Scene(this.rootPane));
		this.primaryStage.show();
	}

	/**
	 * Build the root pane from the fxml file
	 * @return the root pane with all the javafx components
	 */
	private BorderPane getRootPane() {

		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("RootPane.fxml"));
			BorderPane rootLayout = (BorderPane) loader.load();

			return rootLayout;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method call when the application starts
	 * 
	 * @param args No argument required
	 */
	public static void main(String[] args) {

		// Calls the start(...) method above
		launch(args);
	}
}
