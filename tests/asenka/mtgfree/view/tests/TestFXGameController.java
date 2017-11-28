package asenka.mtgfree.view.tests;



public class TestFXGameController { //implements Observer {

//	private static MtgDataUtility dataUtility;
//
//	private static Deck testDeck;
//
////	private static Library library;
////
////	private static Battlefield battlefield;
//
//	static {
//
////		Card.setBattleIdCounter(1);
//
//		dataUtility = MtgDataUtility.getInstance();
//
//		testDeck = new Deck("Test Controller deck", "");
////		testDeck.addCardToMain(dataUtility.getMtgCard("Plains"), 14);
////		testDeck.addCardToMain(dataUtility.getMtgCard("Mountain"), 14);
////		testDeck.addCardToMain(dataUtility.getMtgCard("glorybringer"), 4);
////		testDeck.addCardToMain(dataUtility.getMtgCard("Ahn-Crop Crasher"), 4);
////		testDeck.addCardToMain(dataUtility.getMtgCard("Always watching"), 4);
////		testDeck.addCardToMain(dataUtility.getMtgCard("Angel of Sanctions"), 4);
////		testDeck.addCardToMain(dataUtility.getMtgCard("Battlefield Scavenger"), 4);
////		testDeck.addCardToMain(dataUtility.getMtgCard("Blazing Volley"), 4);
////		testDeck.addCardToMain(dataUtility.getMtgCard("Bloodlust Inciter"), 4);
////		testDeck.addCardToMain(dataUtility.getMtgCard("Brute Strength"), 4);
//
//		try {
//			library = testDeck.buildLibrary();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		battlefield = new Battlefield();
//	}
//
//	@FXML
//	private TableView<Card> battlefieldTableView;
//
//	@FXML
//	private TableColumn<Card, String> btNameTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> btTypeTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> btBattleIDTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> btCostTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> btPowerTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> btToughnessTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> btTappedTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> btVisibleTableColumn;
//
//	@FXML
//	private TableView<Card> opponentBattlefieldTableView;
//
//	@FXML
//	private TableColumn<Card, String> opponentBtNameTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> opponentBtTypeTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> opponentBtBattleIDTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> opponentBtCostTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> opponentBtPowerTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> opponentBtToughnessTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> opponentBtTappedTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> opponentBtVisibleTableColumn;
//
//	@FXML
//	private TableView<Card> handTableView;
//
//	@FXML
//	private TableColumn<Card, String> hdNameTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> hdTypeTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> hdBattleIDTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> hdCostTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> hdColorTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> hdPowerTableColumn;
//
//	@FXML
//	private TableColumn<Card, String> hdToughnessTableColumn;
//
//	@FXML
//	private TextArea selectedCardDataTextArea;
//
//	@FXML
//	private TextArea graveyardTextArea;
//
//	@FXML
//	private TextArea exileTextArea;
//
//	@FXML
//	private TextArea libraryTextArea;
//
//	@FXML
//	private TextArea playerDataTextArea;
//
//	@FXML
//	private TextArea opponentDataTextArea;
//
//	@FXML
//	private TextArea logsTextArea;
//
//	@FXML
//	private ImageView selectedCardImageView;
//
//	@FXML
//	private Button drawHandButton;
//
//	@FXML
//	private Button shuffleButton;
//
//	@FXML
//	private Button playButton;
//
//	@FXML
//	private Button revealeHideHandButton;
//
//	@FXML
//	private Button tapUntapSelectedButton;
//
//	@FXML
//	private Button showHideSelectedButton;
//
//	@FXML
//	private Button killSelectedButton;
//
//	@FXML
//	private Button exileSelectedButton;
//
//	@FXML
//	private Button handSelectedButton;
//
//	@FXML
//	private Button libraryTopSelectedButton;
//
//	@FXML
//	private Button libraryBottomSelectedButton;
//
//	@FXML
//	private Button lifeUpPlayerButton;
//
//	@FXML
//	private Button lifeDownPlayerButton;
//
//	@FXML
//	private Button poisonUpPlayerButton;
//
//	@FXML
//	private Button poisonDownPlayerButton;
//
//	private ObservableList<Card> cardsInLocalPlayerHand;
//
//	private ObservableList<Card> cardsInLocalPlayerBattlefield;
//
//	private ObservableList<Card> cardsInOpponentPlayerBattlefield;
//
//	private GameManager gameManager;
//
//	private String tableName;
//
//	private Player localPlayer;
//
//	private Card selectedCard;
//
//	private Origin selectedCardOrigin;
//
//	@FXML
//	private void initialize() {
//
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		boolean createTable = true;
//
//		try {
//			System.out.println("Create (C) or Join (J) ?");
//			String input = reader.readLine();
//
//			createTable = "c".equals(input.toLowerCase());
//		} catch (IOException e1) {
//			e1.printStackTrace();
//			System.exit(-1);
//		}
//
//		this.tableName = "JavaFX_Test_Table";
//
//		if (createTable) {
//
//			localPlayer = new Player("Bob", battlefield);
//			localPlayer.setSelectedDeck(testDeck);
//			localPlayer.setLibrary(library);
//
//			this.gameManager = GameManager.initialize(this.tableName, this.localPlayer);
//			this.gameManager.getOpponentObservers().add(this);
//
//			// Set the views
//			Player localPlayer = this.gameManager.getLocalPlayerController().getData();
//			localPlayer.addObserver(this);
//			localPlayer.getBattlefield().addObserver(this);
//			localPlayer.getLibrary().addObserver(this);
//			localPlayer.getLibrary().getCards().forEach(card -> card.addObserver(this));
//			this.gameManager.getLocalGameTable().addObserver(this);
//
//			try {
//				this.gameManager.createGame();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		} else {
//
//			localPlayer = new Player("Laura", battlefield);
//			localPlayer.setSelectedDeck(testDeck);
//			localPlayer.setLibrary(library);
//
//			this.gameManager = GameManager.initialize(this.tableName, this.localPlayer);
//			this.gameManager.getOpponentObservers().add(this);
//
//			// Set the views
//			Player localPlayer = this.gameManager.getLocalPlayerController().getData();
//			localPlayer.addObserver(this);
//			localPlayer.getBattlefield().addObserver(this);
//			localPlayer.getLibrary().addObserver(this);
//			localPlayer.getLibrary().getCards().forEach(card -> card.addObserver(this));
//			this.gameManager.getLocalGameTable().addObserver(this);
//
//			try {
//				this.gameManager.joinGame();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		this.cardsInLocalPlayerHand = FXCollections.observableArrayList();
//		this.cardsInLocalPlayerBattlefield = FXCollections.observableArrayList();
//		this.cardsInOpponentPlayerBattlefield = FXCollections.observableArrayList();
//
//		selectedCard = null;
//		selectedCardOrigin = null;
//
//		this.selectedCardDataTextArea.setWrapText(true);
//		this.libraryTextArea.setText(buildStringFromCardsCollection(library.getCards()));
//		this.playerDataTextArea.setText(buildPlayerDataString(localPlayer));
//
//		this.btBattleIDTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(Long.toString(cellData.getValue().getBattleId())));
//		this.hdBattleIDTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(Long.toString(cellData.getValue().getBattleId())));
//		this.opponentBtBattleIDTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(Long.toString(cellData.getValue().getBattleId())));
//
//		this.btNameTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));
//		this.hdNameTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));
//		this.opponentBtNameTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getName()));
//
//		this.btCostTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getManaCost()));
//		this.hdCostTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getManaCost()));
//		this.opponentBtCostTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getManaCost()));
//
//		this.btTypeTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getType()));
//		this.hdTypeTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getType()));
//		this.opponentBtTypeTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getType()));
//
//		this.btPowerTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getPower()));
//		this.hdPowerTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getPower()));
//		this.opponentBtPowerTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getPower()));
//
//		this.btToughnessTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getToughness()));
//		this.hdToughnessTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getToughness()));
//		this.opponentBtToughnessTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrimaryCardData().getToughness()));
//
//		this.btTappedTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isTapped())));
//		this.btVisibleTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isVisible())));
//		this.opponentBtTappedTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isTapped())));
//		this.opponentBtVisibleTableColumn
//				.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isVisible())));
//
//		this.hdColorTableColumn.setCellValueFactory(
//				cellData -> new SimpleStringProperty(Arrays.toString(cellData.getValue().getPrimaryCardData().getColorIdentity())));
//
//		this.battlefieldTableView.setItems(this.cardsInLocalPlayerBattlefield);
//		this.battlefieldTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//
//			Platform.runLater(() -> {
//
//				if (newSelection == null) {
//					battlefieldTableView.getSelectionModel().clearSelection();
//					selectedCardOrigin = null;
//					selectedCard = null;
//					this.selectedCardImageView.setImage(null);
//				} else {
//					selectedCard = newSelection;
//					selectedCardOrigin = Origin.BATTLEFIELD;
//					int multiverseid = selectedCard.getPrimaryCardData().getMultiverseid();
//
//					new Thread(() -> this.selectedCardImageView.setImage(
//							new Image("http://gatherer.wizards.com/Handlers/ImagesManager.ashx?multiverseid=" + multiverseid + "&type=card")))
//									.start();
//				}
//				displaySelectedCard();
//			});
//		});
//
//		this.opponentBattlefieldTableView.setItems(this.cardsInOpponentPlayerBattlefield);
//		this.opponentBattlefieldTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//
//			Platform.runLater(() -> {
//
//				if (newSelection == null) {
//					battlefieldTableView.getSelectionModel().clearSelection();
//					selectedCardOrigin = null;
//					selectedCard = null;
//					this.selectedCardImageView.setImage(null);
//				} else {
//					selectedCard = newSelection;
//					selectedCardOrigin = Origin.OPPONENT_BATTLEFIELD;
//					int multiverseid = selectedCard.getPrimaryCardData().getMultiverseid();
//
//					new Thread(() -> this.selectedCardImageView.setImage(
//							new Image("http://gatherer.wizards.com/Handlers/ImagesManager.ashx?multiverseid=" + multiverseid + "&type=card")))
//									.start();
//				}
//				displaySelectedCard();
//			});
//		});
//
//		this.handTableView.setItems(this.cardsInLocalPlayerHand);
//		this.handTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//
//			Platform.runLater(() -> {
//
//				if (newSelection == null) {
//					handTableView.getSelectionModel().clearSelection();
//					selectedCardOrigin = null;
//					selectedCard = null;
//				} else {
//					selectedCard = newSelection;
//					selectedCardOrigin = Origin.HAND;
//					final int multiverseid = selectedCard.getPrimaryCardData().getMultiverseid();
//
//					new Thread(() -> this.selectedCardImageView.setImage(
//							new Image("http://gatherer.wizards.com/Handlers/ImagesManager.ashx?multiverseid=" + multiverseid + "&type=card")))
//									.start();
//				}
//				displaySelectedCard();
//
//			});
//		});
//	}
//
//	private void displayAlert(Exception e) {
//
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Info !");
//		alert.setHeaderText("Something is wrong...");
//		alert.setContentText(e.getMessage());
//		alert.showAndWait();
//	}
//
//	@FXML
//	private void draw() {
//
//		try {
//			this.gameManager.getLocalPlayerController().draw();
//		} catch (Exception e) {
//			displayAlert(e);
//		}
//	}
//
//	@FXML
//	private void shuffleLibrary() {
//
//		this.gameManager.getLocalPlayerController().shuffleLibrary();
//	}
//
//	@FXML
//	private void lifeUp() {
//
//		this.gameManager.getLocalPlayerController().setLifeCounters(localPlayer.getLifeCounters() + 1);
//	}
//
//	@FXML
//	private void lifeDown() {
//
//		this.gameManager.getLocalPlayerController().setLifeCounters(localPlayer.getLifeCounters() - 1);
//	}
//
//	@FXML
//	private void poisonUp() {
//
//		this.gameManager.getLocalPlayerController().setPoisonCounters(localPlayer.getPoisonCounters() + 1);
//	}
//
//	@FXML
//	private void poisonDown() {
//
//		this.gameManager.getLocalPlayerController().setPoisonCounters(localPlayer.getPoisonCounters() - 1);
//	}
//
//	@FXML
//	private void play() {
//
//		try {
//			if (selectedCard != null && selectedCardOrigin != null) {
//				this.gameManager.getLocalPlayerController().play(selectedCard, selectedCardOrigin, true, 0, 0);
//			}
//		} catch (Exception e) {
//			displayAlert(e);
//		}
//	}
//
//	@FXML
//	private void destroy() {
//
//		try {
//			if (selectedCard != null && selectedCardOrigin != null) {
//				this.gameManager.getLocalPlayerController().destroy(selectedCard, selectedCardOrigin);
//			}
//		} catch (Exception e) {
//			displayAlert(e);
//		}
//	}
//
//	@FXML
//	private void exile() {
//
//		if (selectedCard != null && selectedCardOrigin != null) {
//			this.gameManager.getLocalPlayerController().exile(selectedCard, selectedCardOrigin, true);
//		}
//	}
//
//	@FXML
//	private void backToHand() {
//
//		try {
//			if (selectedCard != null && selectedCardOrigin != null) {
//				this.gameManager.getLocalPlayerController().backToHand(selectedCard, selectedCardOrigin);
//			}
//		} catch (Exception e) {
//			displayAlert(e);
//		}
//	}
//
//	@FXML
//	private void libraryTop() {
//
//		try {
//			if (selectedCard != null && selectedCardOrigin != null) {
//				this.gameManager.getLocalPlayerController().backToTopOfLibrary(selectedCard, selectedCardOrigin);
//			}
//		} catch (Exception e) {
//			displayAlert(e);
//		}
//	}
//
//	@FXML
//	private void libraryDown() {
//
//		try {
//			if (selectedCard != null && selectedCardOrigin != null) {
//				this.gameManager.getLocalPlayerController().backToBottomOfLibrary(selectedCard, selectedCardOrigin);
//			}
//		} catch (Exception e) {
//			displayAlert(e);
//		}
//	}
//
//	@FXML
//	private void toggleTapped() {
//
//		try {
//			if (selectedCard != null) {
//				this.gameManager.getLocalPlayerController().setTapped(!selectedCard.isTapped(), selectedCard);
//			}
//		} catch (Exception e) {
//			displayAlert(e);
//		}
//	}
//
//	@FXML
//	private void toggleVisible() {
//
//		try {
//			if (selectedCard != null) {
//				this.gameManager.getLocalPlayerController().setVisible(!selectedCard.isVisible(), selectedCard);
//			}
//		} catch (Exception e) {
//			displayAlert(e);
//		}
//	}
//
//	@Override
//	public void update(Observable observedObject, Object event) {
//
//		final LocalEvent localEvent = (LocalEvent) event;
//		final LocalEvent.Type eventType = localEvent.getType();
//		final Player player = localEvent.getPlayer();
//		final Serializable[] parameters = localEvent.getParameters();
//
//		final GameTable localGameTable = this.gameManager.getLocalGameTable();
//
//		switch (eventType) {
//
//			case NEW_GAMETABLE_LOG:
//				this.logsTextArea.setText(localGameTable.getStringLogs());
//				break;
//			case ADD_CARD_TO_HAND:
//				if (localGameTable.isLocalPlayer(player != null ? player : (Player) observedObject)) {
//					Card card = (Card) parameters[0];
//					card.addObserver(this);
//					this.cardsInLocalPlayerHand.add(card);
//				}
//				break;
//			case REMOVE_CARD_FROM_HAND:
//				if (localGameTable.isLocalPlayer(player != null ? player : (Player) observedObject)) {
//					Card card = (Card) parameters[0];
//					card.deleteObserver(this);
//					this.cardsInLocalPlayerHand.remove(card);
//				}
//				break;
//			case ADD_CARD_TO_LIBRARY:
//			case REMOVE_CARD_FROM_LIBRARY:
//			case SHUFFLE_LIBRARY:
//				if (localGameTable.isLocalPlayer(player)) {
//					this.libraryTextArea.setText(buildStringFromCardsCollection(this.localPlayer.getLibrary().getCards()));
//				}
//				break;
//			case ADD_CARD_TO_BATTLEFIELD: {
//				Card card = (Card) parameters[0];
//				card.addObserver(this);
//				if (localGameTable.isLocalPlayer(player)) {
//					this.cardsInLocalPlayerBattlefield.add(card);
//				} else {
//					this.cardsInOpponentPlayerBattlefield.add(card);
//				}
//				break;
//			}
//			case REMOVE_CARD_FROM_BATTLEFIELD: {
//				Card card = (Card) parameters[0];
//				card.deleteObserver(this);
//				if (localGameTable.isLocalPlayer(player)) {
//					this.cardsInLocalPlayerBattlefield.remove((Card) parameters[0]);
//				} else {
//					this.cardsInOpponentPlayerBattlefield.remove((Card) parameters[0]);
//				}
//				break;
//			}
//			case ADD_CARD_TO_GRAVEYARD:
//			case REMOVE_CARD_FROM_GRAVEYARD:
//				this.graveyardTextArea.setText(buildStringFromCardsCollection(this.localPlayer.getGraveyard()));
//				break;
//			case ADD_CARD_TO_EXILE:
//			case REMOVE_CARD_FROM_EXILE:
//				this.exileTextArea.setText(buildStringFromCardsCollection(this.localPlayer.getExile()));
//				break;
//			case CLEAR_BATTLEFIELD:
//				if (localGameTable.isLocalPlayer(player)) {
//					this.cardsInLocalPlayerBattlefield.clear();
//				} else {
//					this.cardsInOpponentPlayerBattlefield.clear();
//				}
//			case CARD_TAP:
//			case CARD_UNTAP:
//			case CARD_SHOW:
//			case CARD_HIDE:
//				manageCardEvent((Card) observedObject, player);
//				break;
//			case PLAYER_UPDATE_LIFE:
//			case PLAYER_UPDATE_POISON:
//				if (localGameTable.isLocalPlayer((Player) observedObject)) {
//					this.playerDataTextArea.setText(buildPlayerDataString((Player) observedObject));
//				} else {
//					this.opponentDataTextArea.setText(buildPlayerDataString((Player) observedObject));
//				}
//				break;
//			default:
//				Logger.getLogger(this.getClass()).info("Event not managed: " + localEvent);
//				throw new RuntimeException("Unmanaged event :" + eventType);
//		}
//	}
//
//	private void manageCardEvent(Card card, Player player) {
//
//		if (this.gameManager.getLocalGameTable().isLocalPlayer(player)) {
//			int index = this.cardsInLocalPlayerBattlefield.indexOf(card);
//
//			if (index >= 0) {
//				this.cardsInLocalPlayerBattlefield.set(index, card);
//			}
//		} else {
//			int index = this.cardsInOpponentPlayerBattlefield.indexOf(card);
//
//			if (index >= 0) {
//				this.cardsInOpponentPlayerBattlefield.set(index, card);
//			}
//		}
//	}
//
//	private void displaySelectedCard() {
//
//		if (selectedCard == null) {
//
//			this.selectedCardDataTextArea.setText("");
//		} else {
//
//			final MtgCard cardData = selectedCard.getPrimaryCardData();
//			String cardDataString = "";
//			cardDataString = "Name:\t " + cardData.getName() + "\n";
//			cardDataString += "Cost: \t " + cardData.getManaCost() + "\n";
//			cardDataString += "Type:\t " + cardData.getType() + "\n\n";
//			cardDataString += "Text:\n" + cardData.getText() + "\n";
//
//			this.selectedCardDataTextArea.setText(cardDataString);
//		}
//
//	}
//
//	private static final String buildStringFromCardsCollection(Collection<Card> cards) {
//
//		String result = "";
//
//		for (Card card : cards) {
//			result += card.getBattleId() + (card.getBattleId() < 10 ? "   | " : " | ") + card.getPrimaryCardData().getName() + " \n";
//		}
//		return result;
//	}
//
//	private static final String buildPlayerDataString(Player player) {
//
//		String result = player.getName() + "\n";
//
//		result += "\nLife: " + player.getLifeCounters();
//		result += "\nPoison: " + player.getPoisonCounters();
//
//		return result;
//	}

}
