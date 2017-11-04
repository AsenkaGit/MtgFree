package asenka.mtgfree.model.game;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Predicate;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.events.LocalEvent;
import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.tests.MtgFreeTest;

public class TestLibrary extends MtgFreeTest {

	private static MtgDataUtility dataUtility;

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========     TestLibrary   (START)    =============");
		System.out.println("====================================================");

		dataUtility = MtgDataUtility.getInstance();
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========     TestLibrary   (END)      =============");
		System.out.println("====================================================");
	}

	private boolean observerCalled;
	
	private List<Card> cards;

	private Library library;
	
	private Player player;

	@Override
	@Before
	public void setUp() {
	
		super.setUp();

		Card.setBattleIdCounter(0);

		this.observerCalled = false;
		cards = new ArrayList<Card>(60);

		MtgCard plains = dataUtility.getMtgCard("Plains");
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));
		cards.add(new Card(plains));

		MtgCard mountain = dataUtility.getMtgCard("Mountain");
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));
		cards.add(new Card(mountain));

		MtgCard gloryBringer = dataUtility.getMtgCard("glorybringer");
		cards.add(new Card(gloryBringer));
		cards.add(new Card(gloryBringer));
		cards.add(new Card(gloryBringer));
		cards.add(new Card(gloryBringer));

		MtgCard ahnCropCrasher = dataUtility.getMtgCard("Ahn-Crop Crasher");
		cards.add(new Card(ahnCropCrasher));
		cards.add(new Card(ahnCropCrasher));
		cards.add(new Card(ahnCropCrasher));
		cards.add(new Card(ahnCropCrasher));

		MtgCard alwaysWatching = dataUtility.getMtgCard("Always watching");
		cards.add(new Card(alwaysWatching));
		cards.add(new Card(alwaysWatching));
		cards.add(new Card(alwaysWatching));
		cards.add(new Card(alwaysWatching));

		MtgCard angelOfSanctions = dataUtility.getMtgCard("Angel of Sanctions");
		cards.add(new Card(angelOfSanctions));
		cards.add(new Card(angelOfSanctions));
		cards.add(new Card(angelOfSanctions));
		cards.add(new Card(angelOfSanctions));

		MtgCard battlefieldScavenger = dataUtility.getMtgCard("Battlefield Scavenger");
		cards.add(new Card(battlefieldScavenger));
		cards.add(new Card(battlefieldScavenger));
		cards.add(new Card(battlefieldScavenger));
		cards.add(new Card(battlefieldScavenger));

		MtgCard blazingVolley = dataUtility.getMtgCard("Blazing Volley");
		cards.add(new Card(blazingVolley));
		cards.add(new Card(blazingVolley));
		cards.add(new Card(blazingVolley));
		cards.add(new Card(blazingVolley));

		MtgCard bloodlustInciter = dataUtility.getMtgCard("Bloodlust Inciter");
		cards.add(new Card(bloodlustInciter));
		cards.add(new Card(bloodlustInciter));
		cards.add(new Card(bloodlustInciter));
		cards.add(new Card(bloodlustInciter));

		MtgCard bruteStrength = dataUtility.getMtgCard("Brute Strength");
		cards.add(new Card(bruteStrength));
		cards.add(new Card(bruteStrength));
		cards.add(new Card(bruteStrength));
		cards.add(new Card(bruteStrength));
		
		library = new Library(cards);
		player = new Player("test library");
		player.setLibrary(this.library);
	}

	@Test
	public void testLibrary() {

		new TestLibraryObserver(library);
		assertEquals(60, library.getInitialSize());
		assertEquals(60, library.getCards().size());
		assertEquals(this.cards.get(0), library.draw(player));
		assertEquals(59, library.getCards().size());

		Predicate<Card> filterInstant = (card -> card.getPrimaryCardData().getType().contains("Instant"));
		Predicate<Card> filterLand = (card -> card.getPrimaryCardData().getType().contains("Land"));
		Predicate<Card> filterCreature = (card -> card.getPrimaryCardData().getType().contains("Creature"));
		Predicate<Card> filterEnchantment = (card -> card.getPrimaryCardData().getType().contains("Enchantment"));
		Predicate<Card> filterInstantOrEnchantment = filterInstant.or(filterEnchantment);

		assertEquals(4, this.cards.stream().filter(filterInstant).count());
		assertEquals(28, this.cards.stream().filter(filterLand).count());
		assertEquals(20, this.cards.stream().filter(filterCreature).count());
		assertEquals(4, this.cards.stream().filter(filterEnchantment).count());
		assertEquals(8, this.cards.stream().filter(filterInstantOrEnchantment).count());

		List<Card> draws = library.draw(player, 7);
		assertEquals(7, draws.size());
		assertEquals(52, library.getCards().size());

		library.addToBottom(player, draws.get(0));
		assertEquals(53, library.getCards().size());
		assertEquals(draws.get(0), library.get(52));
		draws.remove(0);
		assertEquals(6, draws.size());

		library.addFromTop(player, draws.get(0), 100);
		assertEquals(54, library.getCards().size());
		assertEquals(draws.get(0), library.get(53));
		draws.remove(0);
		assertEquals(5, draws.size());

		library.addFromTop(player, draws.get(0), 10);
		assertEquals(55, library.getCards().size());
		assertEquals(draws.get(0), library.get(10));
		draws.remove(0);
		assertEquals(4, draws.size());

		assertTrue(observerCalled);
	}

	@Test
	public void testShuffle() {

		System.out.println("------------------------------------------");
		System.out.println(" Before:");
		System.out.println("------------------------------------------");

		displayLibrary(library);
		library.shuffle(player);

		System.out.println("------------------------------------------");
		System.out.println(" After:");
		System.out.println("------------------------------------------");
		displayLibrary(library);
	}
	
	@Test
	public void testChangeCardIndex() {
		
		library.shuffle(player);
		
		final Card card = library.get(59);
		
		library.changeCardIndex(player, card, 0);
		
		assertSame(card, library.get(0));
		assertNotSame(card, library.get(59));
		assertNotEquals(card, library.get(59));
	}

	public static final void displayLibrary(Library library) {

		for (Card c : library.getCards()) {
			System.out.println("Card - " + c.getBattleId() + ",\t" + c.getPrimaryCardData().getName());
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
			assertTrue(event instanceof LocalEvent);

			if (!observerCalled) {
				observerCalled = true;
			}
		}
	}
}
