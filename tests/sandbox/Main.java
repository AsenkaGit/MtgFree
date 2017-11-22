package sandbox;

import java.util.Collections;
import java.util.List;

import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(Main.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Player player = new Player("Asenka");

		player.getLibrary().add(new Card(MtgDataUtility.getInstance().getMtgCard("glorybringer")));
		player.getLibrary().add(new Card(MtgDataUtility.getInstance().getMtgCard("glorybringer")));
		player.getLibrary().add(new Card(MtgDataUtility.getInstance().getMtgCard("glorybringer")));
		player.getLibrary().add(new Card(MtgDataUtility.getInstance().getMtgCard("glorybringer")));
		player.getLibrary().add(new Card(MtgDataUtility.getInstance().getMtgCard("plains")));
		player.getLibrary().add(new Card(MtgDataUtility.getInstance().getMtgCard("plains")));
		player.getLibrary().add(new Card(MtgDataUtility.getInstance().getMtgCard("plains")));
		player.getLibrary().add(new Card(MtgDataUtility.getInstance().getMtgCard("plains")));


		Collections.shuffle(player.getLibrary());
		
		
		TableColumn<Card, String> nameColum = new TableColumn<Card, String>();
		nameColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));
		
		TableColumn<Card, String> costColum = new TableColumn<Card, String>();
		costColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getManaCost()));
		
		TableView<Card> tableView = new TableView<Card>();
		tableView.getColumns().add(nameColum);
		tableView.getColumns().add(costColum);
		
		tableView.setItems(player.getLibrary());
		
		startRemoveItems(player.getLibrary());
		
		Scene scene = new Scene(tableView);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void startRemoveItems(ObservableList<Card> cards) {

		new Thread(() -> {
			
			try {
				
				while(cards.size() > 0) {
					Thread.sleep(1000);
					cards.remove(0);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}).start();
		
	}

}
