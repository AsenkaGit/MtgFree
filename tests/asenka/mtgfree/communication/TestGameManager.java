package asenka.mtgfree.communication;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.GameTable;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;
import asenka.mtgfree.tests.MtgFreeTest;


public class TestGameManager extends MtgFreeTest {

	private static MtgDataUtility dataUtility;
	
	private static Deck testDeck;

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========  TestGameManager (START)  ================");
		System.out.println("====================================================");

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
			testDeck.addCardToSideboard(dataUtility.getMtgCard("black lotus"), 4);
			testDeck.addCardToSideboard(dataUtility.getMtgCard("Aegis of the Gods"), 4);
			testDeck.addCardToSideboard(dataUtility.getMtgCard("Always watching"), 4);
			testDeck.addCardToSideboard(dataUtility.getMtgCard("Mountain"), 3);
		} catch (Exception e) {
			fail("Unexpected exception :" + e.getMessage());
		}
	}
	
	private GameManager gameManager;
	
	private Player player1;
	
	@Before
	@Override
	public void setUp() {

		super.setUp();
		Card.setBattleIdCounter(1);
		
		Library library = null;
		try {
			library = testDeck.buildLibrary();
		} catch (Exception e) {
			fail("Unexpected exception :" + e.getMessage());
		}
		this.player1 = new Player("Youyou");
		this.player1.addAvailableDeck(testDeck);
		this.player1.setSelectedDeck(testDeck);
		this.player1.setLibrary(library);

		GameTable gameTable = new GameTable("TestGameManager", this.player1);
		this.gameManager = GameManager.initialize(this.player1);
		this.gameManager.createGame(gameTable);
		
	}
	
	@After
	public void closeBrokerConnections() {
		
		this.gameManager.endGame();
	}
	
	@Test
	public void testGetAvailableTables() throws InterruptedException {
		
		Thread.sleep(10000);
		System.out.println(this.gameManager.getAvailableTables());		
	}
	
}