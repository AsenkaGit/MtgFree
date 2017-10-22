package asenka.mtgfree.view.tests;



import static org.junit.Assert.fail;

import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;


public class TestFXPlayerController {
	
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
	private TextArea playerDataTextArea;
	
	@FXML
	private TextArea logsTextArea;
	
	@FXML
	private ImageView selectedCardImageView;
	
	
	@FXML
	private Button drawHandButton;
	
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
	
	
	private PlayerController playerController;
	
	
	@FXML
	private void initialize() {
		
		playerController = new PlayerController(player);
		
		
		
	}

	@FXML
	public void draw() {
		
		playerController.draw();
	}


}
