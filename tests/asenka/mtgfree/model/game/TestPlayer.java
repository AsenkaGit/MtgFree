package asenka.mtgfree.model.game;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.events.LocalEvent;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.tests.MtgFreeTest;

public class TestPlayer extends MtgFreeTest {

	private static MtgDataUtility dataUtility;

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========      TestPlayer   (START)    =============");
		System.out.println("====================================================");

		dataUtility = MtgDataUtility.getInstance();
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========      TestPlayer   (END)      =============");
		System.out.println("====================================================");
	}

	private boolean observerCalled;

	@Before
	@Override
	public void setUp() {
		super.setUp();
		observerCalled = false;
	}

	@Test
	public void testPlayer() throws Exception {

		Player playerTest = new Player("Asenka", new Battlefield());
		new TestPlayerObserver(playerTest);

		Deck deck = new Deck("Test deck", "");

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

		playerTest.setSelectedDeck(deck);
		playerTest.setLibrary(deck.buildLibrary());

		// playerTest.draw(7);
		//
		// assertEquals(7, playerTest.getHand().size());
		// assertEquals(53, playerTest.getLibrary().getCards().size());
		//
		// Card card = playerTest.draw();
		// assertTrue(playerTest.getHand().contains(card));

		assertTrue(observerCalled);
	}

	/*
	 * Class used to test the observer implementation
	 */
	private class TestPlayerObserver implements Observer {

		TestPlayerObserver(final Player observedPlayer) {

			observedPlayer.addObserver(this);
		}

		@Override
		public void update(Observable observedCard, Object event) {

			assertTrue(observedCard instanceof Serializable);
			assertTrue(event instanceof LocalEvent);

			if (!observerCalled) {
				observerCalled = true;
			}
		}
	}
}
