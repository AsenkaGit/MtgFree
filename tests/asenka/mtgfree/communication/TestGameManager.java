package asenka.mtgfree.communication;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.communication.activemq.ActiveMQManager;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;
import asenka.mtgfree.tests.MtgFreeTest;


public class TestGameManager extends MtgFreeTest {

	private static MtgDataUtility dataUtility;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		
		System.out.println("====================================================");
		System.out.println("=========  TestGameManager (START)  ================");
		System.out.println("====================================================");

		dataUtility = MtgDataUtility.getInstance();
	}
	
	private class PlayerData {
		
		private GameManager gameManager;
		
		private Player localPlayer;
		
		private String tableName;
		
		private PlayerData(String playerName, Deck deck) {
			
			this.localPlayer = new Player(playerName);
			this.localPlayer.setSelectedDeck(deck);
		}
	}
	
	
	
	
	
	
	
	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========   TestGameManager (END)   ================");
		System.out.println("====================================================");
	}
	
//	private static Deck testDeck;
//
//	@BeforeClass
//	public static void setUpBeforeClass() {
//
//		System.out.println("====================================================");
//		System.out.println("=========  TestGameManager (START)  ================");
//		System.out.println("====================================================");
//
//		dataUtility = MtgDataUtility.getInstance();
//
//		testDeck = new Deck("Test Controller deck", "");
//		testDeck.addCardToMain(dataUtility.getMtgCard("Plains"), 14);
//		testDeck.addCardToMain(dataUtility.getMtgCard("Mountain"), 14);
//		testDeck.addCardToMain(dataUtility.getMtgCard("glorybringer"), 4);
//		testDeck.addCardToMain(dataUtility.getMtgCard("Ahn-Crop Crasher"), 4);
//		testDeck.addCardToMain(dataUtility.getMtgCard("Always watching"), 4);
//		testDeck.addCardToMain(dataUtility.getMtgCard("Angel of Sanctions"), 4);
//		testDeck.addCardToMain(dataUtility.getMtgCard("Battlefield Scavenger"), 4);
//		testDeck.addCardToMain(dataUtility.getMtgCard("Blazing Volley"), 4);
//		testDeck.addCardToMain(dataUtility.getMtgCard("Bloodlust Inciter"), 4);
//		testDeck.addCardToMain(dataUtility.getMtgCard("Brute Strength"), 4);
//
//		try {
//			testDeck.addCardToSideboard(dataUtility.getMtgCard("black lotus"), 4);
//			testDeck.addCardToSideboard(dataUtility.getMtgCard("Aegis of the Gods"), 4);
//			testDeck.addCardToSideboard(dataUtility.getMtgCard("Always watching"), 4);
//			testDeck.addCardToSideboard(dataUtility.getMtgCard("Mountain"), 3);
//		} catch (Exception e) {
//			fail("Unexpected exception :" + e.getMessage());
//		}
//	}
//	
//	private GameManager gameManager;
//	
//	private Player player1;
//	
//	private String gameTableName;
//	
//	@Before
//	@Override
//	public void setUp() {
//
//		super.setUp();
//		Card.setBattleIdCounter(1);
//		
//		Library library = null;
//		try {
//			library = testDeck.buildLibrary();
//		} catch (Exception e) {
//			fail("Unexpected exception :" + e.getMessage());
//		}
//		this.player1 = new Player("Youyou");
//		this.player1.addAvailableDeck(testDeck);
//		this.player1.setSelectedDeck(testDeck);
//		this.player1.setLibrary(library);
//		
//		this.gameTableName = "TestGameManager2";
//		
//		// Create the game manager
//		
//		this.gameManager = GameManager.initialize(this.gameTableName, this.player1);
//		boolean connectionWithBrokerSucced;
//		try {
//			this.gameManager.createGame();
//			connectionWithBrokerSucced = true;
//		} catch (Exception e) {
//			fail("Unable to create a game : " + e.getMessage());
//			connectionWithBrokerSucced = false;
//		}
//		// Do not start the test if the connection with the broker failed
//		Assume.assumeTrue(connectionWithBrokerSucced);
//
//	}
//	
//	@After
//	public void closeBrokerConnections() {
//		
//		this.gameManager.endGame();
//	}
//	
//	@Test
//	public void testGetAvailableTables() throws InterruptedException {
//		
//		List<String> tableNames = this.gameManager.getAvailableTables();
//		assertFalse(tableNames.isEmpty());
//		System.out.println(tableNames);
//		
//		tableNames.forEach(tableName -> assertFalse(tableName.contains(ActiveMQManager.TABLE_NAME_PREFIX)));
//		assertTrue(tableNames.contains(this.gameTableName));
//	}
}
