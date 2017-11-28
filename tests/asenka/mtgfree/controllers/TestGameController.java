package asenka.mtgfree.controllers;

import static org.junit.Assert.*;

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
		
		Thread player1Thread = new Thread(new PlayerTester(gameController.getGameTable()) {

			@Override
			public void run() {

				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		Thread player2Thread = new Thread(new PlayerTester(null) {

			@Override
			public void run() {

				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	abstract class PlayerTester extends GameController implements Runnable {

		public PlayerTester(GameTable gameTable) {

			super(gameTable);
		}
	}
}
