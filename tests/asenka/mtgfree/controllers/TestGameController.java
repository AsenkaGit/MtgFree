package asenka.mtgfree.controllers;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.model.GameTable;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.utilities.CardsManager;
import asenka.mtgfree.tests.MtgFreeTest;

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
		gameController.addOpponent(opponent);
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
		assertEquals(0, opponent.getBattlefield().size());
		assertEquals(0, opponent.getExile().size());
		assertEquals(0, opponent.getGraveyard().size());
	}
	
	

}
