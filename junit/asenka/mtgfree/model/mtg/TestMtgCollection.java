package asenka.mtgfree.model.mtg;

import static org.junit.Assert.*;

import org.junit.Test;

import asenka.mtgfree.tests.utilities.TestDataProvider;


public class TestMtgCollection {

	private TestDataProvider data = TestDataProvider.getInstance();

	@Test
	public void testContains() {

		MtgCollection amonket = new MtgCollection(data.amonketCollection);
		
		assertTrue(amonket.contains(data.mtgCard1));
		assertTrue(amonket.contains(data.mtgCard10));
		assertTrue(amonket.contains(data.mtgCard2));
		assertTrue(amonket.contains(data.mtgCard3));
	}
	
	// TODO compléter les tests pour MtgCollection
}
