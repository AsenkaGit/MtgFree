package asenka.mtgfree.views.javafx;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {


//		RichCardText rct = new RichCardText(MtgDataUtility.getInstance().getMtgCard("Ezuri, Renegade Leader"));
		RichCardText rct = new RichCardText(MtgDataUtility.getInstance().getMtgCard("glorybringer"));

		Scene scene = new Scene(rct);
		primaryStage.setScene(scene);
		primaryStage.show();
    }

	
	
	public static void main(String[] args) {

        Application.launch(MainApplication.class);
	}

}
