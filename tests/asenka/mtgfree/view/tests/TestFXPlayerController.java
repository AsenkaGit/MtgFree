package asenka.mtgfree.view.tests;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.controlers.game.Controller.Origin;
import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.events.AbstractEvent;
import asenka.mtgfree.model.events.BattlefieldEvent;
import asenka.mtgfree.model.events.LibraryEvent;
import asenka.mtgfree.model.events.PlayerEvent;
import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TestFXPlayerController implements Observer {

	private static MtgDataUtility dataUtility;

	private static Deck testDeck;

	private static Library library;

	private static Battlefield battlefield;

	private static Player player;

	static {

		Card.setBattleIdCounter(1);

		dataUtility = MtgDataUtility.getInstance();

		testDeck = new Deck("Test Controller deck", "");
		testDeck.addCardToMain(dataUtility.getMtgCard("Plains"), 14);
		testDeck.addCardToMain(dataUtility.getMtgCard("Mountain"), 14);
		testDeck.addCardToMain(dataUtility.getMtgCard("glorybringer"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Ahn-Crop Crasher"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Always watching"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Angel of Sanctions"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Battlefield Scavenger"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Blazing Volley"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Bloodlust Inciter"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Brute Strength"), 4);

		try {
			library = testDeck.getLibrary();
		} catch (Exception e) {
			e.printStackTrace();
		}

		battlefield = new Battlefield();

		player = new Player("Jean Lassalle", battlefield);
		player.addAvailableDeck(testDeck);
		player.setSelectedDeck(testDeck);
		player.setLibrary(library);
	}

	@FXML
	private TableView<Card> battlefieldTableView;

	@FXML
	private TableColumn<Card, String> btNameTableColumn;

	@FXML
	private TableColumn<Card, String> btTypeTableColumn;

	@FXML
	private TableColumn<Card, String> btTextTableColumn;

	@FXML
	private TableColumn<Card, String> btCostTableColumn;

	@FXML
	private TableColumn<Card, String> btPowerTableColumn;

	@FXML
	private TableColumn<Card, String> btToughnessTableColumn;

	@FXML
	private TableView<Card> handTableView;

	@FXML
	private TableColumn<Card, String> hdNameTableColumn;

	@FXML
	private TableColumn<Card, String> hdTypeTableColumn;

	@FXML
	private TableColumn<Card, String> hdTextTableColumn;

	@FXML
	private TableColumn<Card, String> hdCostTableColumn;

	@FXML
	private TableColumn<Card, String> hdColorTableColumn;

	@FXML
	private TableColumn<Card, String> hdPowerTableColumn;

	@FXML
	private TableColumn<Card, String> hdToughnessTableColumn;

	@FXML
	private TextArea selectedCardDataTextArea;

	@FXML
	private TextArea graveyardTextArea;

	@FXML
	private TextArea exileTextArea;

	@FXML
	private TextArea libraryTextArea;

	@FXML
	private TextArea playerDataTextArea;

	@FXML
	private TextArea logsTextArea;

	@FXML
	private ImageView selectedCardImageView;

	@FXML
	private Button drawHandButton;

	@FXML
	private Button shuffleButton;

	@FXML
	private Button playButton;

	@FXML
	private Button revealeHideHandButton;

	@FXML
	private Button tapUntapSelectedButton;

	@FXML
	private Button showHideSelectedButton;

	@FXML
	private Button killSelectedButton;

	@FXML
	private Button exileSelectedButton;

	@FXML
	private Button handSelectedButton;

	@FXML
	private Button libraryTopSelectedButton;

	@FXML
	private Button libraryBottomSelectedButton;

	@FXML
	private Button lifeUpPlayerButton;

	@FXML
	private Button lifeDownPlayerButton;

	@FXML
	private Button poisonUpPlayerButton;

	@FXML
	private Button poisonDownPlayerButton;

	private PlayerController playerController;

	private Queue<AbstractEvent> logs;

	private Card selectedCard;

	private Origin selectedCardOrigin;

	@FXML
	private void initialize() {

		logs = new LinkedList<AbstractEvent>();
		playerController = new PlayerController(player);
		playerController.addObserver(this);
		selectedCard = null;
		selectedCardOrigin = null;

		this.selectedCardDataTextArea.setWrapText(true);
		this.libraryTextArea.setText(buildStringFromCardsCollection(library.getCards()));
		this.playerDataTextArea.setText(buildPlayerDataString(player));

		this.btNameTableColumn
			.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));
		this.hdNameTableColumn
			.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));

		battlefieldTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			Platform.runLater(() -> {

				if (newSelection == null) {
					battlefieldTableView.getSelectionModel().clearSelection();
					selectedCardOrigin = null;
					selectedCard = null;
					this.selectedCardImageView.setImage(null);
				} else {
					selectedCard = newSelection;
					selectedCardOrigin = Origin.BATTLEFIELD;
					int multiverseid = selectedCard.getPrimaryCardData().getMultiverseid();
					
					Thread t = new Thread(() -> this.selectedCardImageView.setImage(new Image("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + multiverseid + "&type=card")));
					t.start();
				}
				displaySelectedCard();
			});
		});

		handTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			Platform.runLater(() -> {

				if (newSelection == null) {
					handTableView.getSelectionModel().clearSelection();
					selectedCardOrigin = null;
					selectedCard = null;
				} else {
					selectedCard = newSelection;
					selectedCardOrigin = Origin.BATTLEFIELD;
					int multiverseid = selectedCard.getPrimaryCardData().getMultiverseid();

					Thread t = new Thread(() -> this.selectedCardImageView.setImage(new Image("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + multiverseid + "&type=card")));
					t.start();
			
				}
				displaySelectedCard();
				
			});
		});
	}

	@FXML
	private void draw() {

		playerController.draw();
	}

	@FXML
	private void shuffleLibrary() {

		playerController.shuffleLibrary();
	}

	@FXML
	private void lifeUp() {

		playerController.setLifeCounters(player.getLifeCounters() + 1);
	}

	@FXML
	private void lifeDown() {

		playerController.setLifeCounters(player.getLifeCounters() - 1);
	}

	@FXML
	private void poisonUp() {

		playerController.setPoisonCounters(player.getPoisonCounters() + 1);
	}

	@FXML
	private void poisonDown() {

		playerController.setPoisonCounters(player.getPoisonCounters() - 1);
	}

	@FXML
	private void play() {

		if (selectedCard != null && selectedCardOrigin != null) {
			playerController.play(selectedCard, selectedCardOrigin, true, 0, 0);
		}
	}

	@FXML
	private void destroy() {

		if (selectedCard != null && selectedCardOrigin != null) {
			playerController.destroy(selectedCard, selectedCardOrigin);
		}
	}

	@FXML
	private void exile() {

		if (selectedCard != null && selectedCardOrigin != null) {
			playerController.exile(selectedCard, selectedCardOrigin, true);
		}
	}

	@Override
	public void update(Observable observedObject, Object event) {

		this.logs.add((AbstractEvent) event);
		this.logsTextArea.setText(buildLogsString(this.logs));

		if (event instanceof LibraryEvent) {
			manageLibraryEvent((Library) observedObject, (LibraryEvent) event);
		} else if (event instanceof PlayerEvent) {
			managePlayerEvent((Player) observedObject, (PlayerEvent) event);
		} else if (event instanceof BattlefieldEvent) {
			manageBattlefieldEvent((Battlefield) observedObject, (BattlefieldEvent) event);
		}
	}

	private void displaySelectedCard() {

		if (selectedCard == null) {

			this.selectedCardDataTextArea.setText("");
		} else {

			MtgCard cardData = selectedCard.getPrimaryCardData();
			String cardDataString = "";
			cardDataString = "Name:\t " + cardData.getName() + "\n";
			cardDataString += "Cost: \t " + cardData.getManaCost() + "\n";
			cardDataString += "Type:\t " + cardData.getType() + "\n";
			cardDataString += "Text:\n " + cardData.getText() + "\n";

			this.selectedCardDataTextArea.setText(cardDataString);
		}

	}

	private void managePlayerEvent(Player observedObject, PlayerEvent event) {

		switch (event.getEvent()) {
			case "set": {
				this.playerDataTextArea.setText(buildPlayerDataString(playerController.getData()));
			}
			case "add":
			case "remove": {
				switch (event.getProperty()) {
					case "hand":
						this.handTableView.setItems(FXCollections.observableList(this.playerController.getData().getHand()));
						break;
					case "exile":
						this.exileTextArea.setText(buildStringFromCardsCollection(playerController.getData().getExile()));
						break;
					case "graveyard":
						this.graveyardTextArea.setText(buildStringFromCardsCollection(playerController.getData().getGraveyard()));
						break;
				}
			}
		}
	}

	private void manageBattlefieldEvent(Battlefield observedObject, BattlefieldEvent event) {

		this.battlefieldTableView.setItems(FXCollections.observableList(this.playerController.getData().getBattlefield().getCards()));
	}

	private void manageLibraryEvent(Library library, LibraryEvent event) {

		this.libraryTextArea.setText(buildStringFromCardsCollection(playerController.getData().getLibrary().getCards()));
	}

	private static final String buildStringFromCardsCollection(Collection<Card> cards) {

		String result = "";

		for (Card card : cards) {
			result += card.getBattleId() + (card.getBattleId() < 10 ? "   | " : " | ") + card.getPrimaryCardData().getName() + " \n";
		}
		return result;
	}

	private static final String buildLogsString(Collection<AbstractEvent> events) {

		String result = "";

		for (AbstractEvent event : events) {
			result += event.toString() + " \n";
		}
		return result;
	}

	private static final String buildPlayerDataString(Player player) {

		String result = player.getName() + "\n";

		result += "\nLife: " + player.getLifeCounters();
		result += "\nPoison: " + player.getPoisonCounters();

		return result;
	}

}
