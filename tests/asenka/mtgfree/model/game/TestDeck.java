package asenka.mtgfree.model.game;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.events.AbstractEvent;
import asenka.mtgfree.model.events.DeckEvent;
import asenka.mtgfree.tests.MtgFreeTest;

public class TestDeck extends MtgFreeTest {

	private static MtgDataUtility dataUtility;

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========       TestDeck   (START)     =============");
		System.out.println("====================================================");

		dataUtility = MtgDataUtility.getInstance();
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========       TestDeck   (END)       =============");
		System.out.println("====================================================");
	}

	private boolean observerCalled;

	private Deck deck;

	@Before
	@Override
	public void setUp() {
		
		super.setUp();

		this.observerCalled = false;
		this.deck = new Deck("Test deck", "This deck is for JUnit test");
		new TestDeckObserver(this.deck);

		this.deck.addCardToMain(dataUtility.getMtgCard("Plains"), 14);
		this.deck.addCardToMain(dataUtility.getMtgCard("Mountain"), 14);
		this.deck.addCardToMain(dataUtility.getMtgCard("glorybringer"), 4);
		this.deck.addCardToMain(dataUtility.getMtgCard("Ahn-Crop Crasher"), 4);
		this.deck.addCardToMain(dataUtility.getMtgCard("Always watching"), 4);
		this.deck.addCardToMain(dataUtility.getMtgCard("Angel of Sanctions"), 4);
		this.deck.addCardToMain(dataUtility.getMtgCard("Battlefield Scavenger"), 4);
		this.deck.addCardToMain(dataUtility.getMtgCard("Blazing Volley"), 4);
		this.deck.addCardToMain(dataUtility.getMtgCard("Bloodlust Inciter"), 4);
		this.deck.addCardToMain(dataUtility.getMtgCard("Brute Strength"), 4);

		try {
			this.deck.addCardToSideboard(dataUtility.getMtgCard("black lotus"), 1);
			this.deck.addCardToSideboard(dataUtility.getMtgCard("Aegis of the Gods"), 3);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals(60, this.deck.sizeOfMain());
		assertEquals(4, this.deck.sizeOfSideboard());
		assertTrue(observerCalled);
		System.out.println("--------------------------------------------");
	}

	@Test
	public void testGetLibrary() throws Exception {

		Library library = this.deck.getLibrary();
		assertEquals(60, library.getCards().size());
	}

	@Test
	public void testRemoveFrom() {

		this.deck.removeCardFromMain(dataUtility.getMtgCard("Aegis of the Gods"));
		assertEquals(60, this.deck.sizeOfMain());

		this.deck.removeCardFromMain(dataUtility.getMtgCard("Plains"));
		assertEquals(59, this.deck.sizeOfMain());
	}

	/*
	 * Class used to test the observer implementation
	 */
	private class TestDeckObserver implements Observer {

		TestDeckObserver(final Deck observedDeck) {

			observedDeck.addObserver(this);
		}

		@Override
		public void update(Observable observedDeck, Object event) {

			assertTrue(observedDeck instanceof Serializable);
			assertTrue(event instanceof Serializable);
			assertTrue(event instanceof AbstractEvent);
			assertEquals(DeckEvent.class, event.getClass());
			assertTrue(((DeckEvent) event).getValue() instanceof Serializable);

			if (!observerCalled) {
				observerCalled = true;
			}
		}
	}

}
