package asenka.mtgfree.model.game;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.events.AbstractEvent;
import asenka.mtgfree.model.events.DeckEvent;

public class TestDeck {

	@BeforeClass
	public static void setUpBeforeClass() {

		Logger.getLogger(TestDeck.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestDeck.class).debug("--------------------- Begin Junit test ---------------------");
	}

	@AfterClass
	public static void afterClass() {

		Logger.getLogger(TestDeck.class).debug("--------------------- End Junit test ---------------------");
	}

	private MtgDataUtility dataUtility;

	private boolean observerCalled;

	private Deck deck;

	public TestDeck() throws Exception {

		this.dataUtility = MtgDataUtility.getInstance();
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

		this.deck.addCardToSideboard(dataUtility.getMtgCard("black lotus"), 1);
		this.deck.addCardToSideboard(dataUtility.getMtgCard("Aegis of the Gods"), 3);
		
		assertEquals(60, Deck.numberOfCards(this.deck.getMain()));
		assertEquals(4, Deck.numberOfCards(this.deck.getSideboard()));
		assertTrue(observerCalled);
	}

	@Test
	public void testGetLibrary() throws Exception {

		Library library = this.deck.getLibrary();
		assertEquals(60, library.getCards().size());
	}
	
	@Test
	public void testRemoveFrom() {
		
		this.deck.removeCardFromMain(dataUtility.getMtgCard("Aegis of the Gods"));
		assertEquals(60, Deck.numberOfCards(deck.getMain()));
		
		this.deck.removeCardFromMain(dataUtility.getMtgCard("Plains"));
		assertEquals(59, Deck.numberOfCards(deck.getMain()));
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
