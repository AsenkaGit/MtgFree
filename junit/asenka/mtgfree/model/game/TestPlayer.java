package asenka.mtgfree.model.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.model.events.AbstractEvent;
import asenka.mtgfree.model.events.PlayerEvent;
import asenka.mtgfree.model.utilities.json.MtgDataUtility;

public class TestPlayer {
	
	@BeforeClass
	public static void setUpBeforeClass() {
		
		Logger.getLogger(TestPlayer.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestPlayer.class).debug("--------------------- Begin Junit test ---------------------");
	}

	@AfterClass
	public static void afterClass() {

		Logger.getLogger(TestPlayer.class).debug("--------------------- End Junit test ---------------------");
	}
	
	private MtgDataUtility dataUtility;

	private boolean observerCalled;
	
	public TestPlayer() {
		
		this.dataUtility = MtgDataUtility.getInstance();
	}
	
	@Test
	public void testPlayer() throws Exception {
		
		Player playerTest = new Player("Asenka");
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
		
		playerTest.addAvailableDeck(deck);
		playerTest.setSelectedDeck(deck);
		playerTest.setLibrary(deck.getLibrary());
		
		playerTest.draw(7);
		
		assertEquals(7, playerTest.getHand().size());
		assertEquals(53, playerTest.getLibrary().getCards().size());
		
		Card card = playerTest.draw();
		assertTrue(playerTest.getHand().contains(card));
		
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
			assertTrue(event instanceof Serializable);
			assertTrue(event instanceof AbstractEvent);
			assertEquals(PlayerEvent.class, event.getClass());

			if (!observerCalled) {
				observerCalled = true;
			}
		}
	}
}
