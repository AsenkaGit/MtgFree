package asenka.mtgfree.views.javafx;

import asenka.mtgfree.controllers.MtgGameTableController;
import asenka.mtgfree.model.mtg.MtgGameTable;
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
		
		MtgGameTable gameTable = null;
		MtgGameTableController gameTableController = new MtgGameTableController(gameTable);
		return new GameTableView(gameTableController);
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
