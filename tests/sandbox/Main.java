package sandbox;

import java.util.Collections;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Main {

	public static void main(String[] args) {
//		launch(Main.class);
		
		EventType e = EventType.ADD_COUNTER;
		
		System.out.println(e.getMethodName());
		System.out.println(e.getParameterTypes());
	}

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//
//		Player player = new Player(1, "Asenka");
//		Player player2 = new Player(2, "Bernard");
//		
//		CardsManager cardsManager = CardsManager.getInstance();
//
//		player.getLibrary().add(cardsManager.createCard(player, "glorybringer"));
//		player.getLibrary().add(cardsManager.createCard(player, "glorybringer"));
//		player.getLibrary().add(cardsManager.createCard(player, "glorybringer"));
//		player.getLibrary().add(cardsManager.createCard(player, "glorybringer"));
//		player.getLibrary().add(cardsManager.createCard(player, "plains"));
//		player.getLibrary().add(cardsManager.createCard(player, "plains"));
//		player.getLibrary().add(cardsManager.createCard(player, "plains"));
//		player.getLibrary().add(cardsManager.createCard(player2, "plains"));
//
//
//		Collections.shuffle(player.getLibrary());
//		
//		
//		TableColumn<Card, String> nameColum = new TableColumn<Card, String>();
//		nameColum.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPrimaryCardData().getName()));
//		
//		TableColumn<Card, String> costColum = new TableColumn<Card, String>();
//		costColum.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPrimaryCardData().getManaCost()));
//		
//		TableColumn<Card, Number> battleIdColum = new TableColumn<Card, Number>();
//		battleIdColum.setCellValueFactory(cellData -> cellData.getValue().battleIdProperty());
//		
//		TableView<Card> tableView = new TableView<Card>();
//		tableView.getColumns().add(nameColum);
//		tableView.getColumns().add(costColum);
//		tableView.getColumns().add(battleIdColum);
//		
//		tableView.setItems(player.getLibrary());
//		
//		startRemoveItems(player.getLibrary());
//		
//		Scene scene = new Scene(tableView);
//		primaryStage.setScene(scene);
//		primaryStage.show();
//		
//	}
//
//	private void startRemoveItems(ObservableList<Card> cards) {
//
//		new Thread(() -> {
//			
//			try {
//				
//				while(cards.size() > 0) {
//					
//					Thread.sleep(1000);
//					cards.remove(0);
//				}
//				
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		}).start();
//		
//	}

}
