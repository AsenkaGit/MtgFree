package asenka.mtgfree.controllers;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.controlers.game.CardController;
import asenka.mtgfree.controlers.game.PlayerController;
import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.Battlefield;
import asenka.mtgfree.model.game.Card;
import asenka.mtgfree.model.game.Counter;
import asenka.mtgfree.model.game.Deck;
import asenka.mtgfree.model.game.Player;
import asenka.mtgfree.tests.MtgFreeTest;

public class TestCardController extends MtgFreeTest {

	private static MtgDataUtility dataUtility;

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========  TestCardController (START)  =============");
		System.out.println("====================================================");

		dataUtility = MtgDataUtility.getInstance();
	}
	
	private Deck testDeck;

	private PlayerController playerController;

	private MtgCard cardData;

	private Card card;

	private CardController cardController;

	private TestObserver cardView;

	@Before
	@Override
	public void setUp() {

		super.setUp();
		Card.setBattleIdCounter(1);
		
		testDeck = new Deck("Test Controller deck", "");
		testDeck.addCardToMain(dataUtility.getMtgCard("Temple of Malady"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Forest"), 6);
		testDeck.addCardToMain(dataUtility.getMtgCard("swamp"), 8);
		testDeck.addCardToMain(dataUtility.getMtgCard("Blooming Marsh"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Virulent Wound"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Splendid Agony"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Nest of Scarabs"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Sickle Ripper"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Obelisk Spider"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Hapatra, Vizier of Poisons"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Craterhoof Behemoth"), 2);
		testDeck.addCardToMain(dataUtility.getMtgCard("Channeler Initiate"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Ammit Eternal"), 4);
		testDeck.addCardToMain(dataUtility.getMtgCard("Contagion Clasp"), 4);

		try {
			testDeck.addCardToSideboard(dataUtility.getMtgCard("Blazing Volley"), 4);
			testDeck.addCardToSideboard(dataUtility.getMtgCard("Soul-Scar Mage"), 4);
			testDeck.addCardToSideboard(dataUtility.getMtgCard("Nicol Bolas, the Deceiver"), 4);
			testDeck.addCardToSideboard(dataUtility.getMtgCard("Liliana, Death Wielder"), 1);
		} catch (Exception e) {
			fail("Unexpected exception :" + e.getMessage());
		}

		Card.setBattleIdCounter(1);

		Battlefield battlefield = new Battlefield();

		Player player = new Player("Dark sheep", battlefield);
		player.addAvailableDeck(testDeck);
		player.setSelectedDeck(testDeck);
		try {
			player.setLibrary(testDeck.getLibrary());
		} catch (Exception e) {
			fail("Unexpected exception :" + e.getMessage());
		}
		playerController = new PlayerController(player);

		cardData = dataUtility.getMtgCard("Shrine of Burning Rage");
		card = new Card(cardData);
		
		playerController.getData().getLibrary().addToBottom(card);
		
		cardController = new CardController(card, playerController);
		cardView = new TestObserver(cardController);
	}

	@Test
	public void testTap() {

		assertFalse(card.isTapped());

		cardController.setTapped(true);

		assertTrue(card.isTapped());
		assertEquals(1, cardView.getNotificationCounter());
	}

	@Test
	public void testRevealed() {

		assertFalse(card.isRevealed());

		cardController.setRevealed(true);
		;

		assertTrue(card.isRevealed());
		assertEquals(1, cardView.getNotificationCounter());
	}

	@Test
	public void testVisible() {

		assertTrue(card.isVisible());

		cardController.setVisible(false);

		assertFalse(card.isVisible());
		assertEquals(1, cardView.getNotificationCounter());
	}

	@Test
	public void testCounters() {

		final Counter cp1 = new Counter("+1/+1", Color.RED);
		final Counter cm1 = new Counter("-1/-1", Color.BLUE);
		final Counter cS = new Counter("S", Color.GREEN);

		assertEquals(0, card.getCounters().size());

		cardController.addCounter(cp1);
		cardController.addCounter(cp1);
		cardController.addCounter(cm1);
		cardController.addCounter(cS);

		assertEquals(4, card.getCounters().size());

		cardController.removeCounter(cp1);

		assertEquals(3, card.getCounters().size());
		assertEquals("+1/+1", card.getCounters().get(0).getValue());
	}

	@Test
	public void testAssociatedCards() {

		playerController.shuffleLibrary();
		try {
			playerController.draw();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		final Card associatedCard = playerController.getData().getHand().iterator().next();

		cardController.addAssociatedCard(associatedCard);

		assertSame(associatedCard, card.getAssociatedCards().get(0));

		cardController.removeAssociatedCard(associatedCard);

		assertEquals(0, card.getAssociatedCards().size());
	}

	@Test
	public void testPlayerController() {

		assertEquals("Dark sheep", cardController.getPlayerController().getData().getName());
	}

	@Test
	public void testMoveCardInLibrary() {

		assertTrue(playerController.getData().getLibrary().contains(card));

		cardController.moveToTopOfLibrary();

		assertSame(card, playerController.getData().getLibrary().get(0));

		try {
			playerController.draw();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		assertTrue(playerController.getData().getHand().contains(card));
		assertEquals(1, playerController.getData().getHand().size());

		try {
			cardController.moveToTopOfLibrary();
			fail("The card shouldn't be in the library anymore. Then this method shoult triggers an exception");
		} catch (RuntimeException ex) {
		}
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========  TestCardController (END)    =============");
		System.out.println("====================================================");
	}

	private class TestObserver implements Observer {

		private int notificationCounter;

		private CardController controller;

		private TestObserver(CardController controller) {

			this.controller = controller;
			this.controller.addObserver(this);
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
