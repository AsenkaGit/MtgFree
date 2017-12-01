package asenka.mtgfree.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.Serializable;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;
import asenka.mtgfree.tests.AsynchTester;
import asenka.mtgfree.tests.MtgFreeTest;

public class TestGameControllerCommunication extends MtgFreeTest {

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("==   TestGameControllerCommunication (START)   =====");
		System.out.println("====================================================");
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("==  TestGameControllerCommunication (END)     ======");
		System.out.println("====================================================");
	}
	
	
	@Test
	public void testCommunication1() {
		
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
		
		String gameTableName = "testCommunication";
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
					
					perform("createGame", 1000);
					Thread.sleep(2000);
					
					Card card = (Card) perform("draw", 100);
					card = (Card) perform("draw", 100);
					card = (Card) perform("draw", 100);
					card = (Card) perform("draw", 100);
					
					perform("changeCardContext", 100, card, Context.HAND, Context.BATTLEFIELD, 0);
					perform("changeCardContext", 100, card, Context.BATTLEFIELD, Context.EXILE, 0);

					Thread.sleep(5000);
		
				} catch (AssertionError e) {
					fail("[PLAYER 1] One assertion test is wrong: " + e.getMessage());
				} catch (Throwable e) {
					fail("[PLAYER 1] FATAL ERROR: " + e.toString());
				} finally {
					perform("exitGame", 1000);
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
					
					Thread.sleep(1000);
					perform("joinGame", 1000);
					Thread.sleep(5000);
					
					assertNotNull("Test 1", player2GameTable.getOtherPlayer());
					assertEquals("Test 2", 1, player2GameTable.getOtherPlayer().getExile().size());
					assertEquals("Test 3", 3, player2GameTable.getOtherPlayer().getHand().size());
					
				} catch (AssertionError e) {
					fail("[PLAYER 2] One assertion test is wrong: " + e.getMessage());
				} catch (Throwable e) {
					fail("[PLAYER 2] FATAL ERROR: " + e.getMessage());
				} finally {
					perform("exitGame", 1000);
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
					System.out.println("> " + localPlayer.getName() + " set the tap value of " + parameters[0] + " to " + parameters[1]);
					this.setTapped((Card)parameters[0], (Boolean)parameters[1]);
					break;
				case "changeCardContext" : 
					System.out.println("> " + localPlayer.getName() + " moves the card " + parameters[0] 
						+ " from " + parameters[1] + " to " + parameters[2]);
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
