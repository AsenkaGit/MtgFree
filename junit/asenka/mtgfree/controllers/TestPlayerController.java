package asenka.mtgfree.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.controlers.game.CardController;
import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.controlers.game.PlayerController.Origin;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;
import junit.framework.TestCase;

public class TestPlayerController extends TestCase {

	private MtgDataUtility dataUtility;

	private Deck testDeck;

	private PlayerController playerController;

	@BeforeClass
	public static void setUpBeforeClass() {

		Logger.getLogger(TestPlayerController.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestPlayerController.class).debug("--------------------- Begin Junit test ---------------------");
	}

	@Before
	public void setUp() {
		
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
			fail("Unexcpected exception :" + e.getMessage());
		}

		Library library = null;
		try {
			library = testDeck.getLibrary();
		} catch (Exception e) {
			fail("Unexcpected exception :" + e.getMessage());
		}
		
		Player player = new Player("Baboulinet", new Battlefield());
		player.addAvailableDeck(testDeck);
		player.setSelectedDeck(testDeck);
		player.setLibrary(library);
		
		this.playerController = new PlayerController(player);
		new TestBattlefieldView(playerController);
	}
	
	@Test
	public void testDraw() {
		
		playerController.draw();
		
		assertEquals(59, playerController.getData().getLibrary().size());
		assertEquals(1, playerController.getData().getHand().size());
		
		playerController.draw(6);
		
		assertEquals(53, playerController.getData().getLibrary().size());
		assertEquals(7, playerController.getData().getHand().size());
	}
	
	public void testPlay() {
		
		testDraw();
		
		List<Card> cards = new ArrayList<Card>(playerController.getData().getHand());
		
		cards.forEach(card -> new TestCardView(new CardController(card, playerController)));
		
		assertEquals(7, cards.size());
		
		playerController.play(cards.get(0), Origin.HAND, true, 0d, 0d);
		
		assertEquals(6, playerController.getData().getHand().size());
		assertEquals(1, playerController.getData().getBattlefield().size());
		assertTrue(playerController.getData().getBattlefield().contains(cards.get(0)));
		assertSame(cards.get(0), playerController.getData().getBattlefield().getCards().iterator().next());
	}
	

	@AfterClass
	public static void afterClass() {

		Logger.getLogger(TestPlayerController.class).debug("---------------------  End Junit test  ---------------------");
	}
	
	
	private class TestCardView implements Observer {
		
		private CardController controller;
		
		private TestCardView(CardController controller) {
			this.controller = controller;
			this.controller.addView(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			System.out.println(arg);
		}
	}
	
	private class TestBattlefieldView implements Observer {
		
		private PlayerController controller;
		
		private TestBattlefieldView(PlayerController controller) {
			this.controller = controller;
			this.controller.addView(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			System.out.println(arg);
		}
	}

}
