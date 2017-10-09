package asenka.mtgfree.views.javafx;

import asenka.mtgfree.controllers.MtgGameTableController;
import asenka.mtgfree.tests.utilities.TestDataModel_French;
import javafx.application.Application;
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
		TestDataModel_French data = TestDataModel_French.getInstance();
		
		MtgGameTableController gameTableController = new MtgGameTableController(data.getGameTable());
		GameTableView gameTableView = new GameTableView(gameTableController);
		
		data.action_drawCardPlayer1();
		
		return gameTableView;
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
