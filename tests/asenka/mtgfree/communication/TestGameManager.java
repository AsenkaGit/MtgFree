package asenka.mtgfree.communication;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.events.NetworkEvent;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.Player;

public class TestGameManager {

	private static final String TEST_TABLE_NAME = "PouicPouicTable";

	private static MtgDataUtility dataUtility;

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========  TestGameManager (START)  ================");
		System.out.println("====================================================");

		dataUtility = MtgDataUtility.getInstance();
	}

	private PlayerData player1Data;

	private PlayerData player2Data;

	private boolean networkEventReceived;

	public void setUp() {

		this.player1Data = new PlayerData("GrosBébé", buildDeck1(), TEST_TABLE_NAME);
		this.player2Data = new PlayerData("VoleuseDePoules", buildDeck2(), TEST_TABLE_NAME);
	}
	
    @Test
    public void testSuite() {
    	setUp();
    	testDataInitialization();
    	setUp();
    	testPlayer1CreateGame();
    	setUp();
    	testPlayerJoin();
    }

	public void testDataInitialization() {

		assertNotNull(this.player1Data.gameManager);
		assertNotNull(this.player2Data.gameManager);
		assertNotNull(this.player1Data.gameManager.getLocalPlayerController());
		assertNotNull(this.player2Data.gameManager.getLocalPlayerController());
		assertNotNull(this.player1Data.gameManager.getLocalGameTable());
		assertNotNull(this.player2Data.gameManager.getLocalGameTable());
		assertNull(this.player1Data.gameManager.getOpponentPlayerController());
		assertNull(this.player2Data.gameManager.getOpponentPlayerController());

		assertSame(this.player1Data.localPlayer.getName(), this.player1Data.gameManager.getLocalPlayerController().getData().getName());
		assertEquals("GrosBébé", this.player1Data.gameManager.getLocalPlayerController().getData().getName());
		assertEquals("GrosBébé", this.player1Data.localPlayer.getName());
		assertEquals(TEST_TABLE_NAME, this.player1Data.gameManager.getLocalGameTable().getName());
		
		assertSame(this.player2Data.localPlayer.getName(), this.player2Data.gameManager.getLocalPlayerController().getData().getName());
		assertEquals("VoleuseDePoules", this.player2Data.gameManager.getLocalPlayerController().getData().getName());
		assertEquals("VoleuseDePoules", this.player2Data.localPlayer.getName());
		assertEquals(TEST_TABLE_NAME, this.player2Data.gameManager.getLocalGameTable().getName());
		
		assertEquals(buildDeck1(), this.player1Data.localPlayer.getSelectedDeck());
		assertEquals(buildDeck2(), this.player2Data.localPlayer.getSelectedDeck());
		
		assertNotNull(this.player1Data.localPlayer.getLibrary());
		assertNotNull(this.player2Data.localPlayer.getLibrary());
		
		assertTrue(this.player1Data.localPlayer.getLibrary().size() >= 60);
		assertTrue(this.player2Data.localPlayer.getLibrary().size() >= 60);
	}
	
	public void testPlayer1CreateGame() {
		
		this.networkEventReceived = false;
		this.player1Data.gameManager.getLocalGameTable().addObserver((observable, event) -> {
			this.networkEventReceived = true;
		});
		try {
			this.player1Data.gameManager.createGame();
			this.player1Data.gameManager.send(new NetworkEvent(null, this.player1Data.localPlayer, "Coucou"));
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception: " + e.getMessage());
		}
		assertTrue(this.networkEventReceived);
		this.player1Data.gameManager.endGame();
	}
	
	
	public void testPlayerJoin() {
		
		Thread player1Thread = new Thread(() -> {
			
			try {
				this.player1Data.gameManager.createGame();
			} catch (Exception e) {
				e.printStackTrace();
				fail("Unexpected exception: " + e.getMessage());
			}
		});
		
		Thread player2Thread = new Thread(() -> {
			
			try {
				this.player2Data.gameManager.joinGame();
			} catch (Exception e) {
				e.printStackTrace();
				fail("Unexpected exception: " + e.getMessage());
			}
		});
		
		try {
			player1Thread.start();
			Thread.sleep(1000);
			player2Thread.start();
			Thread.sleep(1000);
			
			assertNotNull(this.player1Data.gameManager.getOpponentPlayerController());
			assertNotNull(this.player2Data.gameManager.getOpponentPlayerController());
			assertEquals(
					this.player1Data.gameManager.getOpponentPlayerController().getData(),
					this.player2Data.localPlayer);
			assertEquals(
					this.player2Data.gameManager.getOpponentPlayerController().getData(),
					this.player1Data.localPlayer);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception: " + e.getMessage());
		}
		
		this.player1Data.gameManager.endGame();
		this.player2Data.gameManager.endGame();
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========   TestGameManager (END)   ================");
		System.out.println("====================================================");
	}

	private static Deck buildDeck1() {

		Deck deck = new Deck("RedWhite", "Player 1 deck");
		deck.addCardToMain(dataUtility.getMtgCard("Plains"), 14);
		deck.addCardToMain(dataUtility.getMtgCard("Mountain"), 14);
		deck.addCardToMain(dataUtility.getMtgCard("glorybringer"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Ahn-Crop Crasher"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Always watching"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Angel of Sanctions"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Battlefield Scavenger"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Blazing Volley"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Bloodlust Inciter"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Brute Strength"), 4);
		return deck;
	}

	private static Deck buildDeck2() {

		Deck deck = new Deck("GreenBlue", "Player 2 deck");
		deck.addCardToMain(dataUtility.getMtgCard("Forest"), 14);
		deck.addCardToMain(dataUtility.getMtgCard("Island"), 14);
		deck.addCardToMain(dataUtility.getMtgCard("Pride Sovereign"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Aetherstream Leopard"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Wily Bandar"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Wave-Wing Elemental"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Fairgrounds Trumpeter"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Displace"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Slice in Twain"), 4);
		deck.addCardToMain(dataUtility.getMtgCard("Rashmi, Eternities Crafter"), 4);
		return deck;
	}

	private class PlayerData {

		private GameManager gameManager;

		private Player localPlayer;

		private PlayerData(String playerName, Deck deck, String tableName) {

			this.localPlayer = new Player(playerName);
			this.localPlayer.setSelectedDeck(deck);
			this.gameManager = buildGameManager(tableName, this.localPlayer);
		}

		private GameManager buildGameManager(String tableName, Player localPlayer) {

			GameManager gameManager = null;
			try {
				// Get the private constructor of GameManager.
				Constructor<GameManager> gameManagerConstructor = GameManager.class.getDeclaredConstructor(String.class, Player.class);
				gameManagerConstructor.setAccessible(true);
				gameManager = gameManagerConstructor.newInstance(tableName, localPlayer);

			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
				fail("Unexpected exception: " + e.getMessage());
			}
			return gameManager;
		}
	}
}
