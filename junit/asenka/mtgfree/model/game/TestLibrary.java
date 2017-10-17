package asenka.mtgfree.model.game;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.junit.Test;

import asenka.mtgfree.model.events.AbstractEvent;
import asenka.mtgfree.model.events.LibraryEvent;
import asenka.mtgfree.model.pojo.MtgCard;
import asenka.mtgfree.model.utilities.json.MtgDataUtility;


public class TestLibrary {
	
	private MtgDataUtility dataUtility;

	private boolean observerCalled;
	
	private List<Card> cards;

	public TestLibrary() {
		
		Card.setBattleIdCounter(0);
		
		this.dataUtility = MtgDataUtility.getInstance();
		this.observerCalled = false;
		this.cards = new ArrayList<Card>(60);
		

		MtgCard plains = dataUtility.getMtgCard("Plains");
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));
		this.cards.add(new Card(plains));

		MtgCard mountain = dataUtility.getMtgCard("Mountain");
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		this.cards.add(new Card(mountain));
		
		MtgCard gloryBringer = dataUtility.getMtgCard("glorybringer");
		this.cards.add(new Card(gloryBringer));
		this.cards.add(new Card(gloryBringer));
		this.cards.add(new Card(gloryBringer));
		this.cards.add(new Card(gloryBringer));
		
		MtgCard ahnCropCrasher = dataUtility.getMtgCard("Ahn-Crop Crasher");
		this.cards.add(new Card(ahnCropCrasher));
		this.cards.add(new Card(ahnCropCrasher));
		this.cards.add(new Card(ahnCropCrasher));
		this.cards.add(new Card(ahnCropCrasher));
		
		MtgCard alwaysWatching = dataUtility.getMtgCard("Always watching");
		this.cards.add(new Card(alwaysWatching));
		this.cards.add(new Card(alwaysWatching));
		this.cards.add(new Card(alwaysWatching));
		this.cards.add(new Card(alwaysWatching));
		
		MtgCard angelOfSanctions = dataUtility.getMtgCard("Angel of Sanctions");
		this.cards.add(new Card(angelOfSanctions));
		this.cards.add(new Card(angelOfSanctions));
		this.cards.add(new Card(angelOfSanctions));
		this.cards.add(new Card(angelOfSanctions));
		
		MtgCard battlefieldScavenger = dataUtility.getMtgCard("Battlefield Scavenger");
		this.cards.add(new Card(battlefieldScavenger));
		this.cards.add(new Card(battlefieldScavenger));
		this.cards.add(new Card(battlefieldScavenger));
		this.cards.add(new Card(battlefieldScavenger));
		
		MtgCard blazingVolley = dataUtility.getMtgCard("Blazing Volley");
		this.cards.add(new Card(blazingVolley));
		this.cards.add(new Card(blazingVolley));
		this.cards.add(new Card(blazingVolley));
		this.cards.add(new Card(blazingVolley));
		
		MtgCard bloodlustInciter = dataUtility.getMtgCard("Bloodlust Inciter");
		this.cards.add(new Card(bloodlustInciter));
		this.cards.add(new Card(bloodlustInciter));
		this.cards.add(new Card(bloodlustInciter));
		this.cards.add(new Card(bloodlustInciter));
		
		MtgCard bruteStrength = dataUtility.getMtgCard("Brute Strength");
		this.cards.add(new Card(bruteStrength));
		this.cards.add(new Card(bruteStrength));
		this.cards.add(new Card(bruteStrength));
		this.cards.add(new Card(bruteStrength));
	}


	@Test
	public void testLibrary() {

		Library libTest = new Library(this.cards);
		new TestLibraryObserver(libTest);
		assertEquals(60, libTest.getInitialSize());
		assertEquals(60, libTest.getCards().size());
		assertEquals(this.cards.get(0), libTest.draw());
		assertEquals(59, libTest.getCards().size());
		
		List<Card> draws = libTest.draw(7);
		assertEquals(7, draws.size());
		assertEquals(52, libTest.getCards().size());
		
		libTest.addToBottom(draws.get(0));
		assertEquals(53, libTest.getCards().size());
		assertEquals(draws.get(0), libTest.getCardAt(52));
		draws.remove(0);
		assertEquals(6, draws.size());
		
		libTest.addFromTop(draws.get(0), 100);
		assertEquals(54, libTest.getCards().size());
		assertEquals(draws.get(0), libTest.getCardAt(53));
		draws.remove(0);
		assertEquals(5, draws.size());
		
		libTest.addFromTop(draws.get(0), 10);
		assertEquals(55, libTest.getCards().size());
		assertEquals(draws.get(0), libTest.getCardAt(10));
		draws.remove(0);
		assertEquals(4, draws.size());
		
		assertTrue(observerCalled);
	}
	
	@Test
	public void testShuffle() {
		
		System.out.println("[TestLibrary] Visual test for library shuffle");
		System.out.println("[TestLibrary] Current library order");
		
		Library libTest = new Library(this.cards);
		
		displayLibrary(libTest);
		libTest.shuffle();
		
		System.out.println("[TestLibrary] After shuffle :");
		displayLibrary(libTest);
	}
	
	private static final void displayLibrary(Library library) {
		
		for(Card c : library.getCards()) {
			System.out.println("[TestLibrary] Card - " + c.getBattleId() + ", " + c.getPrimaryCardData().getName());
		}
	}
	
	/*
	 * Class used to test the observer implementation 
	 */
	private class TestLibraryObserver implements Observer {

		TestLibraryObserver(final Library observedLibrary) {

			observedLibrary.addObserver(this);
		}

		@Override
		public void update(Observable observedCard, Object event) {
			
			assertTrue(observedCard instanceof Serializable);
			assertTrue(event instanceof Serializable);
			assertTrue(event instanceof AbstractEvent);
			assertEquals(LibraryEvent.class, event.getClass());
			assertTrue(((LibraryEvent)event).getValue() instanceof Serializable);
	
			if(!observerCalled) {
				observerCalled = true;
			}
		}
	}

}
