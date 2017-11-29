package asenka.mtgfree.controllers;

import static org.junit.Assert.*;

import java.io.Serializable;

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
					

					final GameTable gameTable = getGameTable();
					
					assertSame(player1, gameTable.getLocalPlayer());
					assertNull(gameTable.getOtherPlayer());
					
					perform("createGame", 2000);
					
					assertNotNull(gameTable.getOtherPlayer());
					assertEquals(player2, gameTable.getOtherPlayer());
					
					Card card = (Card) perform("draw", 500);
					card = (Card) perform("draw", 500);
					
					assertEquals(2, gameTable.getLocalPlayer().getHand().size());
		
				} catch (AssertionError e) {
					fail("One assertion test is wrong.");
				} catch (Throwable e) {
					fail("FATAL ERROR: " + e.getMessage());
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
					
					final GameTable gameTable = getGameTable();
					
					assertSame(player2, gameTable.getLocalPlayer());
					assertNull(gameTable.getOtherPlayer());
					Thread.sleep(1000);
					
					perform("joinGame", 2000);
					
					assertNotNull(gameTable.getOtherPlayer());
					assertEquals(player1, gameTable.getOtherPlayer());
					
					assertEquals(2, gameTable.getOtherPlayer().getHand().size());
					
					Card card = (Card) perform("draw", 500);
					perform("changeCardContext", 500, card, Context.HAND, Context.BATTLEFIELD, 0);
					
				} catch (AssertionError e) {
					fail("One assertion test is wrong.");
				} catch (Throwable e) {
					fail("FATAL ERROR: " + e.getMessage());
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
				case "draw" : 
					System.out.println("> " + localPlayer.getName() + " draws a card");
					result = this.draw();
					break;
				case "changeCardContext" : 
					System.out.println("> " + localPlayer.getName() + " move the card " + (Card)parameters[0] 
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
