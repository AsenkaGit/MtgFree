package asenka.mtgfree.controllers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.controllers.game.PlayerController;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.Origin;
import asenka.mtgfree.model.game.Library;
import asenka.mtgfree.model.game.Player;
import asenka.mtgfree.tests.MtgFreeTest;

public class TestPlayerController extends MtgFreeTest {

	private static MtgDataUtility dataUtility;

	private static Deck testDeck;

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========  TestPlayerController(START) =============");
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

	private PlayerController playerController;

	private TestObserver battlefieldView;

	private TestObserver libraryView;

	private TestObserver playerView;

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
		Battlefield battlefield = new Battlefield();

		Player player = new Player("Baboulinet", battlefield);
		player.addAvailableDeck(testDeck);
		player.setSelectedDeck(testDeck);
		player.setLibrary(library);

		this.playerController = new PlayerController(player, false);

		this.battlefieldView = new TestObserver(this.playerController);
		this.libraryView = new TestObserver(this.playerController);
		this.playerView = new TestObserver(this.playerController);
	}

	@Test
	public void testObservers() {

		assertEquals(3, playerController.getData().countObservers());
		assertEquals(3, playerController.getData().getLibrary().countObservers());
		assertEquals(3, playerController.getData().getBattlefield().countObservers());
		// assertEquals(3, playerController.getObservers().size());
	}

	@Test
	public void testPlayerCounters() {

		playerController.setLifeCounters(19);
		assertEquals(19, playerController.getData().getLifeCounters());

		playerController.setPoisonCounters(9);
		assertEquals(9, playerController.getData().getPoisonCounters());
	}

	@Test
	public void testDraw() {

		playerController.shuffleLibrary();
		try {
			playerController.draw();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		assertEquals(59, playerController.getData().getLibrary().size());
		assertEquals(1, playerController.getData().getHand().size());

		playerController.draw(6);

		assertEquals(53, playerController.getData().getLibrary().size());
		assertEquals(7, playerController.getData().getHand().size());

		assertEquals(10, this.battlefieldView.getNotificationCounter());
		assertEquals(10, this.libraryView.getNotificationCounter());
		assertEquals(10, this.playerView.getNotificationCounter());
	}

	@Test
	public void testPlay() {

		playerController.draw(7);

		List<Card> cards = new ArrayList<Card>(playerController.getData().getHand());

		assertEquals(7, cards.size());

		playerController.play(cards.get(0), Origin.HAND, true, 0d, 0d);

		assertEquals(6, playerController.getData().getHand().size());
		assertEquals(1, playerController.getData().getBattlefield().size());
		assertTrue(playerController.getData().getBattlefield().contains(cards.get(0)));
		assertSame(cards.get(0), playerController.getData().getBattlefield().getCards().iterator().next());
	}

	@Test
	public void testCardLife() {

		playerController.shuffleLibrary();

		final Card card = playerController.getData().getLibrary().get(0);

		try {
			playerController.draw();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		playerController.play(card, Origin.HAND, true, 120d, 56d);
		playerController.destroy(card, Origin.BATTLEFIELD);

		assertSame(card, playerController.getData().getGraveyard().iterator().next());

		assertFalse(playerController.getData().getHand().contains(card));
		assertFalse(playerController.getData().getLibrary().contains(card));
		assertFalse(playerController.getData().getBattlefield().contains(card));
		assertFalse(playerController.getData().getExile().contains(card));
		assertTrue(playerController.getData().getGraveyard().contains(card));

		playerController.exile(card, Origin.GRAVEYARD, false);

		assertTrue(playerController.getData().getExile().contains(card));
		assertFalse(playerController.getData().getGraveyard().contains(card));

		try {
			playerController.backToHand(card, Origin.BATTLEFIELD);
			fail("Exception was expected here");
		} catch (Exception ex) {
		}

		playerController.backToHand(card, Origin.EXILE);

		assertTrue(playerController.getData().getHand().contains(card));
		assertFalse(playerController.getData().getLibrary().contains(card));
		assertFalse(playerController.getData().getBattlefield().contains(card));
		assertFalse(playerController.getData().getExile().contains(card));
		assertFalse(playerController.getData().getGraveyard().contains(card));
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========  TestPlayerController (END)  =============");
		System.out.println("====================================================");
	}

	private class TestObserver implements Observer {

		private int notificationCounter;

		private PlayerController controller;

		private TestObserver(PlayerController controller) {

			this.controller = controller;
			Player player = this.controller.getData();
			player.addObserver(this);
			player.getLibrary().addObserver(this);
			player.getBattlefield().addObserver(this);
			this.notificationCounter = 0;
		}

		@Override
		public void update(Observable o, Object arg) {

			// Count the number of times this observer is notified
			++notificationCounter;
		}

		public int getNotificationCounter() {

			return notificationCounter;
		}
	}
}
