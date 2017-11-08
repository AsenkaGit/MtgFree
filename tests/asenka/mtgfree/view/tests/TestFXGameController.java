package asenka.mtgfree.view.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import asenka.mtgfree.communication.GameManager;
import asenka.mtgfree.controllers.game.PlayerController;
import asenka.mtgfree.events.LocalEvent;
import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.GameTable;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Origin;
import asenka.mtgfree.model.game.Player;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TestFXGameController implements Observer {

	private static MtgDataUtility dataUtility;

	private static Deck testDeck;

	private static Library library;

	private static Battlefield battlefield;

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
			library = testDeck.buildLibrary();
		} catch (Exception e) {
			e.printStackTrace();
		}

		battlefield = new Battlefield();
	}

	@FXML
	private TableView<Card> battlefieldTableView;

	@FXML
	private TableColumn<Card, String> btNameTableColumn;

	@FXML
	private TableColumn<Card, String> btTypeTableColumn;

	@FXML
	private TableColumn<Card, String> btBattleIDTableColumn;

	@FXML
	private TableColumn<Card, String> btCostTableColumn;

	@FXML
	private TableColumn<Card, String> btPowerTableColumn;

	@FXML
	private TableColumn<Card, String> btToughnessTableColumn;

	@FXML
	private TableColumn<Card, String> btTappedTableColumn;

	@FXML
	private TableColumn<Card, String> btVisibleTableColumn;

	@FXML
	private TableView<Card> opponentBattlefieldTableView;

	@FXML
	private TableColumn<Card, String> opponentBtNameTableColumn;

	@FXML
	private TableColumn<Card, String> opponentBtTypeTableColumn;

	@FXML
	private TableColumn<Card, String> opponentBtBattleIDTableColumn;

	@FXML
	private TableColumn<Card, String> opponentBtCostTableColumn;

	@FXML
	private TableColumn<Card, String> opponentBtPowerTableColumn;

	@FXML
	private TableColumn<Card, String> opponentBtToughnessTableColumn;

	@FXML
	private TableColumn<Card, String> opponentBtTappedTableColumn;

	@FXML
	private TableColumn<Card, String> opponentBtVisibleTableColumn;

	@FXML
	private TableView<Card> handTableView;

	@FXML
	private TableColumn<Card, String> hdNameTableColumn;

	@FXML
	private TableColumn<Card, String> hdTypeTableColumn;

	@FXML
	private TableColumn<Card, String> hdBattleIDTableColumn;

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
	private TextArea opponentDataTextArea;

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

	private GameTable gameTable;

	private Player localPlayer;

	private PlayerController localPlayerController;

	private Card selectedCard;

	private Origin selectedCardOrigin;

	@FXML
	private void initialize() {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean createTable = true;

		try {
			System.out.println("Create (C) or Join (J) ?");
			String input = reader.readLine();

			createTable = "c".equals(input.toLowerCase());
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}

		if (createTable) {

			localPlayer = new Player("Jean Lassalle", battlefield);
			localPlayer.addAvailableDeck(testDeck);
			localPlayer.setSelectedDeck(testDeck);
			localPlayer.setLibrary(library);

			gameTable = new GameTable("JavaFXUITestTable", localPlayer);
			gameTable.addObserver(this);
			localPlayerController = gameTable.getLocalPlayerController();
			localPlayerController.addObserver(this);
			
			

			try {
				GameManager manager = GameManager.initialize(localPlayer);
				manager.createGame(gameTable);
				manager.addOpponentObserver(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			localPlayer = new Player("Mister Join !", battlefield);
			localPlayer.addAvailableDeck(testDeck);
			localPlayer.setSelectedDeck(testDeck);
			localPlayer.setLibrary(library);

			GameManager gameManager = GameManager.initialize(localPlayer);

			try {
				gameManager.joinGame("JavaFXUITestTable", localPlayer);
			} catch (Exception e) {
				e.printStackTrace();
			}

			gameTable = gameManager.getGameTable();
			gameTable.addObserver(this);
			localPlayerController = gameTable.getLocalPlayerController();
			localPlayerController.addObserver(this);
			gameTable.getOtherPlayerControllers().forEach(controller -> controller.addObserver(this));
			
			
			
		}

		selectedCard = null;
		selectedCardOrigin = null;

		this.selectedCardDataTextArea.setWrapText(true);
		this.libraryTextArea.setText(buildStringFromCardsCollection(library.getCards()));
		this.playerDataTextArea.setText(buildPlayerDataString(localPlayer));

		this.btBattleIDTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(Long.toString(cellData.getValue().getBattleId())));
		this.hdBattleIDTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(Long.toString(cellData.getValue().getBattleId())));
		this.opponentBtBattleIDTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(Long.toString(cellData.getValue().getBattleId())));

		this.btNameTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));
		this.hdNameTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));
		this.opponentBtNameTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));

		this.btCostTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getManaCost()));
		this.hdCostTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getManaCost()));
		this.opponentBtCostTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getManaCost()));

		this.btTypeTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getType()));
		this.hdTypeTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getType()));
		this.opponentBtTypeTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getType()));

		this.btPowerTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getPower()));
		this.hdPowerTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getPower()));
		this.opponentBtPowerTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getPower()));

		this.btToughnessTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getToughness()));
		this.hdToughnessTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getToughness()));
		this.opponentBtToughnessTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getToughness()));

		this.btTappedTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isTapped())));
		this.btVisibleTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isVisible())));
		this.opponentBtVisibleTableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isVisible())));

		this.hdColorTableColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(Arrays.toString(cellData.getValue().getPrimaryCardData().getColorIdentity())));

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

					new Thread(() -> this.selectedCardImageView.setImage(
							new Image("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + multiverseid + "&type=card")))
									.start();
				}
				displaySelectedCard();
			});
		});

		opponentBattlefieldTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			Platform.runLater(() -> {

				if (newSelection == null) {
					battlefieldTableView.getSelectionModel().clearSelection();
					selectedCardOrigin = null;
					selectedCard = null;
					this.selectedCardImageView.setImage(null);
				} else {
					selectedCard = newSelection;
					selectedCardOrigin = Origin.OPPONENT_BATTLEFIELD;
					int multiverseid = selectedCard.getPrimaryCardData().getMultiverseid();

					new Thread(() -> this.selectedCardImageView.setImage(
							new Image("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + multiverseid + "&type=card")))
									.start();
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
					selectedCardOrigin = Origin.HAND;
					final int multiverseid = selectedCard.getPrimaryCardData().getMultiverseid();

					new Thread(() -> this.selectedCardImageView.setImage(
							new Image("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + multiverseid + "&type=card")))
									.start();
				}
				displaySelectedCard();

			});
		});
	}

	@FXML
	private void draw() {

		try {
			localPlayerController.draw();
		} catch (Exception e) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Info !");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void shuffleLibrary() {

		localPlayerController.shuffleLibrary();
	}

	@FXML
	private void lifeUp() {

		localPlayerController.setLifeCounters(localPlayer.getLifeCounters() + 1);
	}

	@FXML
	private void lifeDown() {

		localPlayerController.setLifeCounters(localPlayer.getLifeCounters() - 1);
	}

	@FXML
	private void poisonUp() {

		localPlayerController.setPoisonCounters(localPlayer.getPoisonCounters() + 1);
	}

	@FXML
	private void poisonDown() {

		localPlayerController.setPoisonCounters(localPlayer.getPoisonCounters() - 1);
	}

	@FXML
	private void play() {

		if (selectedCard != null && selectedCardOrigin != null) {
			localPlayerController.play(selectedCard, selectedCardOrigin, true, 0, 0);
		}
	}

	@FXML
	private void destroy() {

		if (selectedCard != null && selectedCardOrigin != null) {
			localPlayerController.destroy(selectedCard, selectedCardOrigin);
		}
	}

	@FXML
	private void exile() {

		if (selectedCard != null && selectedCardOrigin != null) {
			localPlayerController.exile(selectedCard, selectedCardOrigin, true);
		}
	}

	@FXML
	private void backToHand() {

		if (selectedCard != null && selectedCardOrigin != null) {
			localPlayerController.backToHand(selectedCard, selectedCardOrigin);
		}
	}

	@FXML
	private void libraryTop() {

		if (selectedCard != null && selectedCardOrigin != null) {
			localPlayerController.backToTopOfLibrary(selectedCard, selectedCardOrigin);
		}
	}

	@FXML
	private void libraryDown() {

		if (selectedCard != null && selectedCardOrigin != null) {
			localPlayerController.backToBottomOfLibrary(selectedCard, selectedCardOrigin);
		}
	}

	@FXML
	private void toggleTapped() {

		if (selectedCard != null) {
			localPlayerController.setTapped(!selectedCard.isTapped(), selectedCard);
		}
	}

	@FXML
	private void toggleVisible() {

		if (selectedCard != null) {
			localPlayerController.setVisible(!selectedCard.isVisible(), selectedCard);
		}
	}

	@Override
	public void update(Observable observedObject, Object event) {

		final LocalEvent localEvent = (LocalEvent) event;
		final LocalEvent.Type eventType = localEvent.getType();
		final Player sourcePlayer = localEvent.getPlayer();
//		final Serializable[] parameters = localEvent.getParameters();

		switch (eventType) {

			case NEW_GAMETABLE_LOG:
				this.logsTextArea.setText(this.gameTable.getStringLogs());
				break;
			case ADD_CARD_TO_HAND:
			case REMOVE_CARD_FROM_HAND:
				this.handTableView.setItems(FXCollections.observableList(this.localPlayer.getHand()));
				break;
			case ADD_CARD_TO_LIBRARY:
			case REMOVE_CARD_FROM_LIBRARY:
			case SHUFFLE_LIBRARY:
				if (this.gameTable.isLocalPlayer(sourcePlayer)) {
					this.libraryTextArea.setText(buildStringFromCardsCollection(this.localPlayer.getLibrary().getCards()));
				}
				break;
			case ADD_CARD_TO_BATTLEFIELD:
			case REMOVE_CARD_FROM_BATTLEFIELD:
				if (this.gameTable.isLocalPlayer(sourcePlayer)) {
					this.battlefieldTableView.setItems(FXCollections.observableList(this.localPlayer.getBattlefield().getCards()));
				} else {
					this.opponentBattlefieldTableView.setItems(FXCollections.observableList(sourcePlayer.getBattlefield().getCards()));
				}
				break;
			case ADD_CARD_TO_GRAVEYARD:
			case REMOVE_CARD_FROM_GRAVEYARD:
				this.graveyardTextArea.setText(buildStringFromCardsCollection(this.localPlayer.getGraveyard()));
				break;
			case ADD_CARD_TO_EXILE:
			case REMOVE_CARD_FROM_EXILE:
				this.exileTextArea.setText(buildStringFromCardsCollection(this.localPlayer.getExile()));
				break;
			default: 
				Logger.getLogger(this.getClass()).info("Event not managed: " + localEvent);
		}
	}

	private void displaySelectedCard() {

		if (selectedCard == null) {

			this.selectedCardDataTextArea.setText("");
		} else {

			final MtgCard cardData = selectedCard.getPrimaryCardData();
			String cardDataString = "";
			cardDataString = "Name:\t " + cardData.getName() + "\n";
			cardDataString += "Cost: \t " + cardData.getManaCost() + "\n";
			cardDataString += "Type:\t " + cardData.getType() + "\n\n";
			cardDataString += "Text:\n" + cardData.getText() + "\n";

			this.selectedCardDataTextArea.setText(cardDataString);
		}

	}

	// private void managePlayerEvent(Player observedObject, PlayerEvent event) {
	//
	// switch (event.getEventType()) {
	// case "set": {
	// this.playerDataTextArea.setText(buildPlayerDataString(localPlayerController.getData()));
	// }
	// case "add":
	// case "remove": {
	// switch (event.getProperty()) {
	// case "hand":
	// this.handTableView.setItems(FXCollections.observableList(this.localPlayerController.getData().getHand()));
	// break;
	// case "exile":
	// this.exileTextArea.setText(buildStringFromCardsCollection(localPlayerController.getData().getExile()));
	// break;
	// case "graveyard":
	// this.graveyardTextArea.setText(buildStringFromCardsCollection(localPlayerController.getData().getGraveyard()));
	// break;
	// }
	// }
	// }
	// }
	//
	// private void manageBattlefieldEvent(Battlefield observedObject, BattlefieldEvent event) {
	//
	// if (gameTable.isLocalPlayer(event.getPlayer())) {
	// this.battlefieldTableView
	// .setItems(FXCollections.observableList(this.localPlayerController.getData().getBattlefield().getCards()));
	// } else {
	// this.opponentBattlefieldTableView.setItems(FXCollections.observableList(event.getPlayer().getBattlefield().getCards()));
	// }
	// }
	//
	// private void manageLibraryEvent(Library library, LibraryEvent event) {
	//
	// this.libraryTextArea.setText(buildStringFromCardsCollection(localPlayerController.getData().getLibrary().getCards()));
	// }
	//
	// private void manageCardEvent(Card observedObject, CardEvent event) {
	//
	// int index = this.battlefieldTableView.getSelectionModel().getSelectedIndex();
	// this.battlefieldTableView.getItems().set(index, observedObject);
	// this.battlefieldTableView.requestFocus();
	// this.battlefieldTableView.getSelectionModel().select(index);
	// this.battlefieldTableView.getFocusModel().focus(index);
	//
	// }

	private static final String buildStringFromCardsCollection(Collection<Card> cards) {

		String result = "";

		for (Card card : cards) {
			result += card.getBattleId() + (card.getBattleId() < 10 ? "   | " : " | ") + card.getPrimaryCardData().getName() + " \n";
		}
		return result;
	}

	// private static final String buildLogsString(Collection<AbstractLocalEvent> events) {
	//
	// String result = "";
	//
	// for (AbstractLocalEvent event : events) {
	// result += event.toString() + " \n";
	// }
	// return result;
	// }

	private static final String buildPlayerDataString(Player player) {

		String result = player.getName() + "\n";

		result += "\nLife: " + player.getLifeCounters();
		result += "\nPoison: " + player.getPoisonCounters();

		return result;
	}

}
