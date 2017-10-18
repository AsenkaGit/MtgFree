package asenka.mtgfree.model.game;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.geom.Point2D;
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
import asenka.mtgfree.model.events.CardEvent;

public class TestCard {

	@BeforeClass
	public static void setUpBeforeClass() {

		Logger.getLogger(TestCard.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestCard.class).debug("--------------------- Begin Junit test ---------------------");
	}

	@AfterClass
	public static void afterClass() {

		Logger.getLogger(TestCard.class).debug("--------------------- End Junit test ---------------------");
	}

	private MtgDataUtility dataUtility;

	private boolean observerCalled;

	public TestCard() {

		this.dataUtility = MtgDataUtility.getInstance();
		this.observerCalled = false;
	}

	@Test
	public void testDoubleFacedCards() {

		Card doubleFacedCard = new Card(dataUtility.getMtgCard("Chalice OF Life"));

		assertEquals("double-faced", doubleFacedCard.getLayout());
		assertEquals("Chalice of Life", doubleFacedCard.getPrimaryCardData().getName());
		assertEquals("Chalice of Death", doubleFacedCard.getSecondaryCardData().getName());
	}

	@Test
	public void testCard() {

		Card card = new Card(dataUtility.getMtgCard("black lotus"));
		new TestCardObserver(card);

		card.addCounter(new Counter("+1/+1", Color.GREEN));
		assertEquals(new Counter("+1/+1", Color.GREEN), card.getCounters().iterator().next());

		card.setLocation(250, 100);
		assertEquals(new Point2D.Double(250, 100), card.getLocation());

		card.addAssociatedCard(new Card(dataUtility.getMtgCard("Inferno Jet")));
		assertEquals(1, card.getAssociatedCards().size());

		assertTrue(observerCalled);
	}

	/*
	 * Class used to test the observer implementation
	 */
	private class TestCardObserver implements Observer {

		TestCardObserver(final Card observedCard) {

			observedCard.addObserver(this);
		}

		@Override
		public void update(Observable observedCard, Object event) {

			assertTrue(observedCard instanceof Serializable);
			assertTrue(event instanceof Serializable);
			assertTrue(event instanceof AbstractEvent);
			assertEquals(CardEvent.class, event.getClass());
			assertTrue(((CardEvent) event).getValue() instanceof Serializable);

			if (!observerCalled) {
				observerCalled = true;
			}
		}
	}

}
