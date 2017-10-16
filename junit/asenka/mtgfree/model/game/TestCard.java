package asenka.mtgfree.model.game;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import asenka.mtgfree.model.events.AbstractEvent;
import asenka.mtgfree.model.events.CardEvent;
import asenka.mtgfree.model.utilities.json.MtgDataUtility;

public class TestCard {

	private MtgDataUtility dataUtility;

	private boolean observerCalled;

	@Before
	public void setUp() throws Exception {

		this.dataUtility = MtgDataUtility.getInstance();
		this.observerCalled = false;
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

	class TestCardObserver implements Observer {

		TestCardObserver(final Card observedCard) {

			observedCard.addObserver(this);
		}

		@Override
		public void update(Observable observedCard, Object event) {

			assertTrue(observedCard instanceof Serializable);
			assertTrue(event instanceof Serializable);
			assertTrue(event instanceof AbstractEvent);
			assertEquals(CardEvent.class, event.getClass());
			assertTrue(((CardEvent)event).getValue() instanceof Serializable);
	
			if(!observerCalled) {
				observerCalled = true;
			}
		}
	}

}
