package asenka.mtgfree.controllers;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;
import asenka.mtgfree.tests.MtgFreeTest;
import asenka.mtgfree.tests.AsynchTester;
import javafx.geometry.Point2D;

public class TestGameController extends MtgFreeTest {

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========   TestGameController (START)   ===========");
		System.out.println("====================================================");
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========   TestGameController (END)     ===========");
		System.out.println("====================================================");
	}
	
	private GameController gameController;
	
	private Player localPlayer;
	
	private Player opponent;
	
	@Before
	@Override
	public void beforeTest() {
		
		super.beforeTest();
		
		CardsManager cm = CardsManager.getInstance();
		
		localPlayer = new Player(1, "Asenka");
		opponent = new Player(2, "Morloss");
		
		localPlayer.getLibrary().addAll(
			cm.createCard(localPlayer, "glorybringer"),
			cm.createCard(localPlayer, "glorybringer"),
			cm.createCard(localPlayer, "glorybringer"),
			cm.createCard(localPlayer, "mountain"),
			cm.createCard(localPlayer, "mountain"),
			cm.createCard(localPlayer, "mountain"),
			cm.createCard(localPlayer, "mountain"),
			cm.createCard(localPlayer, "mountain"),
			cm.createCard(localPlayer, "mountain"),
			cm.createCard(localPlayer, "mountain"));
		
		opponent.getLibrary().addAll(
			cm.createCard(opponent, "black lotus"),
			cm.createCard(opponent, "Sulam Djinn"),
			cm.createCard(opponent, "Sulam Djinn"),
			cm.createCard(opponent, "island"),
			cm.createCard(opponent, "island"),
			cm.createCard(opponent, "island"),
			cm.createCard(opponent, "island"),
			cm.createCard(opponent, "island"),
			cm.createCard(opponent, "island"),
			cm.createCard(opponent, "island"));
		
		gameController = new GameController(new GameTable("testTable", localPlayer));
	}
	
	@After
	public void afterTest() {
		
		CardsManager.getInstance().clear();
	}
	
	
	@Test
	public void testDrawNoCommunication() {
		
		assertEquals(10, opponent.getLibrary().size());
		assertEquals(0, opponent.getHand().size());
		assertEquals(0, opponent.getBattlefield().size());
		assertEquals(0, opponent.getExile().size());
		assertEquals(0, opponent.getGraveyard().size());
		
		gameController.draw(opponent);
		
		assertEquals(9, opponent.getLibrary().size());
		assertEquals(1, opponent.getHand().size());

		gameController.draw(opponent);
		gameController.draw(opponent);
		gameController.draw(opponent);
		gameController.draw(opponent);
		
		assertEquals(5, opponent.getLibrary().size());
		assertEquals(5, opponent.getHand().size());
		assertEquals(0, opponent.getBattlefield().size());
		assertEquals(0, opponent.getExile().size());
		assertEquals(0, opponent.getGraveyard().size());
	}
	
	@Test
	public void testChangeCardContextNoCommunication() {
		
		assertEquals(10, opponent.getLibrary().size());
		assertEquals(0, opponent.getHand().size());
		assertEquals(0, opponent.getBattlefield().size());
		assertEquals(0, opponent.getExile().size());
		assertEquals(0, opponent.getGraveyard().size());
		
		Card card = gameController.draw(opponent);
		
		try {
			gameController.changeCardContext(opponent, card, Context.BATTLEFIELD, Context.HAND, GameController.TOP);
			fail("Exception expected here");
		} catch(GameException e) {
			System.out.println("Expected exception catched : " + e);
		}
		
		gameController.changeCardContext(opponent, card, Context.HAND, Context.BATTLEFIELD, GameController.TOP);
		
		assertEquals(9, opponent.getLibrary().size());
		assertEquals(0, opponent.getHand().size());
		assertEquals(1, opponent.getBattlefield().size());
		assertEquals(0, opponent.getExile().size());
		assertEquals(0, opponent.getGraveyard().size());
		
		gameController.changeCardContext(opponent, card, Context.BATTLEFIELD, Context.LIBRARY, GameController.BOTTOM);
		
		assertEquals(10, opponent.getLibrary().size());
		assertEquals(0, opponent.getHand().size());
		assertEquals(0, opponent.getBattlefield().size());
		assertEquals(0, opponent.getExile().size());
		assertEquals(0, opponent.getGraveyard().size());
		assertSame(card, opponent.getLibrary().get(opponent.getLibrary().size() - 1));
		
		card = gameController.draw(opponent);
		gameController.changeCardContext(opponent, card, Context.HAND, Context.LIBRARY, GameController.TOP);
		
		assertSame(card, gameController.draw(opponent));
		
		gameController.changeCardContext(opponent, card, Context.HAND, Context.BATTLEFIELD, GameController.TOP);
		gameController.changeCardContext(opponent, card, Context.BATTLEFIELD, Context.GRAVEYARD, GameController.TOP);
		gameController.changeCardContext(opponent, card, Context.GRAVEYARD, Context.EXILE, GameController.TOP);
		
		assertEquals(9, opponent.getLibrary().size());
		assertEquals(0, opponent.getHand().size());
		assertEquals(0, opponent.getBattlefield().size());
		assertEquals(1, opponent.getExile().size());
		assertEquals(0, opponent.getGraveyard().size());
		assertSame(card, opponent.getExile().get(0));
		
		card = opponent.getLibrary().get(0);
		gameController.changeCardContext(opponent, card, Context.LIBRARY, Context.LIBRARY, GameController.BOTTOM);
		
		assertEquals(9, opponent.getLibrary().size());
		assertEquals(0, opponent.getHand().size());
		assertEquals(0, opponent.getBattlefield().size());
		assertEquals(1, opponent.getExile().size());
		assertEquals(0, opponent.getGraveyard().size());
		assertSame(card, opponent.getLibrary().get(opponent.getLibrary().size() - 1));
		assertNotSame(card, opponent.getLibrary().get(0));
	}
	
	@Test
	public void testSetCardStatusNoCommunication() {
		
		final Card card = gameController.draw(opponent);
		
		assertEquals(false, card.isTapped());
		gameController.setTapped(opponent, card, true);
		assertEquals(true, card.isTapped());
		
		assertEquals(true, card.isVisible());
		gameController.setVisible(opponent, card, false);
		assertEquals(false, card.isVisible());
		
		assertEquals(false, card.isSelected());
		gameController.setSelected(opponent, card, true);
		assertEquals(true, card.isSelected());
		
		assertEquals(new Point2D(0d, 0d), card.getLocation());
		gameController.setLocation(opponent, card, 42d, 82d);
		assertEquals(new Point2D(42d, 82d), card.getLocation());
	}
	
	@Test
	public void testWith1Player() {
		
		gameController.createGame();
		Player localPlayer = gameController.getGameTable().getLocalPlayer();
		
		Card card = gameController.draw();
		assertNotNull(card);
		assertEquals(1, localPlayer.getHand().size());
		assertSame(card, localPlayer.getHand().get(0));
		assertFalse(card.isTapped());
		assertTrue(card.isVisible());
		
		
		gameController.changeCardContext(card, Context.HAND, Context.BATTLEFIELD, 0);
		assertEquals(0, localPlayer.getHand().size());
		assertEquals(1, localPlayer.getBattlefield().size());
		assertSame(card, localPlayer.getBattlefield().get(0));
		
		gameController.setTapped(card, true);
		assertTrue(localPlayer.getBattlefield().get(0).isTapped());
		assertTrue(card.isTapped());
		
		gameController.setVisible(card, false);
		assertFalse(card.isVisible());
		
		gameController.exitGame();
	}
	
	@Test
	public void testCommunication() {
		
		CardsManager cm = CardsManager.getInstance();
		
		final Player player1 = new Player(1, "Player_1");
		final Player player2 = new Player(2, "Player_2");
		
		player1.getLibrary().addAll(
			cm.createCard(player1, "glorybringer"),
			cm.createCard(player1, "glorybringer"),
			cm.createCard(player1, "glorybringer"),
			cm.createCard(player1, "mountain"),
			cm.createCard(player1, "mountain"),
			cm.createCard(player1, "mountain"),
			cm.createCard(player1, "mountain"),
			cm.createCard(player1, "mountain"),
			cm.createCard(player1, "mountain"),
			cm.createCard(player1, "mountain"));
		
		player2.getLibrary().addAll(
			cm.createCard(player2, "black lotus"),
			cm.createCard(player2, "Sulam Djinn"),
			cm.createCard(player2, "Sulam Djinn"),
			cm.createCard(player2, "island"),
			cm.createCard(player2, "island"),
			cm.createCard(player2, "island"),
			cm.createCard(player2, "island"),
			cm.createCard(player2, "island"),
			cm.createCard(player2, "island"),
			cm.createCard(player2, "island"));
		
		String gameTableName = "testTable";
		final GameTable gameTablePlayer1 = new GameTable(gameTableName, player1);
		final GameTable gameTablePlayer2 = new GameTable(gameTableName, player2);
		
		AsynchTester player1Thread = new AsynchTester(new GameControllerCommunicationTester(gameTablePlayer1) {

			@Override
			public void run() {
				System.out.println("BEGIN PLAYER 1 GAME COMMUNICATION");

				try {
					final GameTable player1GameTable = getGameTable();	
					assertSame("Test 0.1", player1, player1GameTable.getLocalPlayer());
					assertNull("Test 0.2", player1GameTable.getOtherPlayer());
					
					// STEP 1 [Player 1] Create the game
					perform("createGame", 1000);
					
					// STEP 2 [Player 2] Join the game
					Thread.sleep(1000);
					assertNotNull("Test 2.1", player1GameTable.getOtherPlayer());
					assertEquals("Test 2.2", player2, player1GameTable.getOtherPlayer());
					
					// STEP 3 [Player 1] Draws a card
					Card card = (Card) perform("draw", 1000);
					assertEquals("Test 3.1", 1, player1.getHand().size());
					assertSame("Test 3.2", card, player1.getHand().get(0));
					
					// STEP 4 [Player 2] Draws a card
					assertEquals("Test 4", 1, player1GameTable.getOtherPlayer().getHand().size());
					Thread.sleep(1000);
					
					// STEP 5 [Player 2] Draws a card
					assertEquals("Test 5", 2, player1GameTable.getOtherPlayer().getHand().size());
					Thread.sleep(1000);
					
					// STEP 6 [Player 1] Play a card on battlefield
					perform("changeCardContext", 1000, card, Context.HAND, Context.BATTLEFIELD, 0);
					assertEquals("Test 6.1", 0, player1.getHand().size());
					assertEquals("Test 6.2", 1, player1.getBattlefield().size());
					
					// STEP 7 [Player 2] Play a card on battlefield
					Thread.sleep(1000);
					
					// STEP 8 [Player 1] tap the card on the battlefield
					perform("setTapped", 1000, card, true);
		
				} catch (AssertionError e) {
					fail("[PLAYER 1] One assertion test is wrong: " + e.getMessage());
				} catch (Throwable e) {
					fail("[PLAYER 1] FATAL ERROR: " + e.toString());
				} finally {
					System.out.println("END PLAYER 1 GAME COMMUNICATION");
				}
				
			}
		});
		
		
		
		AsynchTester player2Thread = new AsynchTester(new GameControllerCommunicationTester(gameTablePlayer2) {

			@Override
			public void run() {

				System.err.println("BEGIN PLAYER 2 GAME COMMUNICATION");

				try {	
					final GameTable player2GameTable = getGameTable();
					assertSame("Test 0.1", player2, player2GameTable.getLocalPlayer());
					assertNull("Test 0.2", player2GameTable.getOtherPlayer());
					
					// STEP 1 [Player 1] Create the game
					Thread.sleep(1000);
					
					// STEP 2 [Player 2] Join the game
					perform("joinGame", 1000);
					assertNotNull("Test 2.1", player2GameTable.getOtherPlayer());
					assertEquals("Test 2.2", player1, player2GameTable.getOtherPlayer());
					
					// STEP 3 [Player 1] Draws a card
					Thread.sleep(1000);
					assertEquals(1, player2GameTable.getOtherPlayer().getHand().size());
					
					// STEP 4 [Player 2] Draws a card
					Card card = (Card) perform("draw", 1000);
					assertEquals(1, player2.getHand().size());
					assertSame(card, player2.getHand().get(0));
					
					// STEP 5 [Player 2] Draws a card
					card = (Card) perform("draw", 1000);
					assertEquals(2, player2.getHand().size());
					
					// STEP 6 [Player 1] Play a card on battlefield
					Thread.sleep(1000);
					
					// STEP 7 [Player 2] Play a card on battlefield
					perform("changeCardContext", 1000, card, Context.HAND, Context.BATTLEFIELD, 0);
					assertEquals("Test 7.1", 1, player2.getHand().size());
					assertEquals("Test 7.2", 1, player2.getBattlefield().size());
					
					// STEP 8 [Player 1] tap the card on the battlefield
					Thread.sleep(1000);
					assertEquals("Test 8", true, player2GameTable.getOtherPlayer().getBattlefield().get(0).isTapped());
					
				} catch (AssertionError e) {
					fail("[PLAYER 2] One assertion test is wrong: " + e.getMessage());
				} catch (Throwable e) {
					fail("[PLAYER 2] FATAL ERROR: " + e.getMessage());
				} finally {
					System.err.println("END PLAYER 2 GAME COMMUNICATION");
				}
				
			}
		});
		
		player1Thread.setName("Player1_Thread");
		player2Thread.setName("Player2_Thread");
		player1Thread.start();
		player2Thread.start();
		
		try {
			player1Thread.test();
			player2Thread.test();
			
		} catch (InterruptedException e) {
			fail(e.getMessage());
		} finally {
			cm.clear();
			System.out.println("END TEST");
		}
	}
	

	
	private abstract class GameControllerCommunicationTester extends GameController implements Runnable {

		GameControllerCommunicationTester(final GameTable gameTable) {

			super(gameTable);
		}
		
		synchronized Object perform(String action, int timeout, Serializable... parameters) {
			
			final GameTable gameTable = this.getGameTable();
			final Player localPlayer = gameTable.getLocalPlayer();
			Object result = null;
			
			switch(action) {
				
				case "createGame": 
					System.out.println("> " + localPlayer.getName() + " create the game table " + gameTable);
					this.createGame(); 
					break;
				case "joinGame" : 
					System.out.println("> " + localPlayer.getName() + " join the table " + gameTable);
					this.joinGame(); 
					break;
				case "exitGame" : 
					System.out.println("> " + localPlayer.getName() + " exit the table " + gameTable);
					this.exitGame(); 
					break;
				case "draw" : 
					System.out.println("> " + localPlayer.getName() + " draws a card");
					result = this.draw();
					break;
				case "setTapped" : 
					System.out.println("> " + localPlayer.getName() + " set the tap value of " + (Card)parameters[0] + " to " + (Boolean)parameters[1]);
					this.setTapped((Card)parameters[0], (Boolean)parameters[1]);
					break;
				case "changeCardContext" : 
					System.out.println("> " + localPlayer.getName() + " moves the card " + (Card)parameters[0] 
						+ " from " + (Context)parameters[1] + " to " + (Context)parameters[2]);
					this.changeCardContext((Card)parameters[0], (Context)parameters[1], (Context)parameters[2], (Integer)parameters[3]);
					break;
			}
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
			return result;
		}
	}
}
