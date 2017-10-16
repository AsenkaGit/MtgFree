package asenka.mtgfree.model.game;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import asenka.mtgfree.model.utilities.json.MtgDataUtility;


public class TestCard {
	
	private MtgDataUtility dataUtility;

	@Before
	public void setUp() throws Exception {
		this.dataUtility = MtgDataUtility.getInstance();
	}

	@Test
	public void test() {

		Card card = new Card(dataUtility.getMtgCard("black lotus"));
		new TestCardObserver(card);
		
		
		card.addCounter(new Counter("+1/+1", Color.GREEN));
		
	}
	
	private class TestCardObserver implements Observer {
		
		TestCardObserver(final Card observedCard) {

			observedCard.addObserver(this);
		}

		@Override
		public void update(Observable observedCard, Object arg) {
			
		}
	}

}
