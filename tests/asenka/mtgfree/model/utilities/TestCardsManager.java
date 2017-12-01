package asenka.mtgfree.model.utilities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.tests.MtgFreeTest;

public class TestCardsManager extends MtgFreeTest {

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("====================================================");
		System.out.println("=========   TestCardsManager (START)   =============");
		System.out.println("====================================================");
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("====================================================");
		System.out.println("=========   TestCardsManager (END)     =============");
		System.out.println("====================================================");
	}
	
	private CardsManager cardsManager;
	
	private Player player;
	
	@Before
	@Override
	public void beforeTest() {
		
		super.beforeTest();
		this.cardsManager = CardsManager.getInstance();
		this.player = new Player(1, "Asenka");
	}
	
	@After
	public void afterTest() {
		this.cardsManager.clear();
	}
	
	@Test
	public void testCreateCard() {
		
		final Card card = cardsManager.createCard(player, "always watching");
		
		assertTrue(cardsManager.getCards().contains(card));
		assertSame(cardsManager.getCards().get(0), card);
		assertEquals(cardsManager.getCards().get(0).getPrimaryCardData().getName(), "Always Watching");
	}
	
	@Test
	public void testGetLocalCard() {
		
		add15CardsToLibrary();
		assertEquals(CardsManager.PLAYER_ID_MULTIPLICATOR + 15, cardsManager.getCurrentBattleIdCounterOf(player));
		
		final Card testCard1 = new Card(CardsManager.PLAYER_ID_MULTIPLICATOR + 2, MtgDataUtility.getInstance().getMtgCard("glorybringer"));
		final Card testCard2 = new Card(CardsManager.PLAYER_ID_MULTIPLICATOR + 3, MtgDataUtility.getInstance().getMtgCard("glorybringer"));
		final Card testCard3 = new Card(CardsManager.PLAYER_ID_MULTIPLICATOR + 13, MtgDataUtility.getInstance().getMtgCard("plains"));
		
		assertTrue(cardsManager.getCards().contains(testCard1));
		assertTrue(cardsManager.getCards().contains(testCard2));
		assertTrue(cardsManager.getCards().contains(testCard3));
		
		assertNotSame(testCard1, cardsManager.getLocalCard(testCard1));
		assertEquals(testCard1, cardsManager.getLocalCard(testCard1));
		assertNotSame(testCard2, cardsManager.getLocalCard(testCard2));
		assertEquals(testCard2, cardsManager.getLocalCard(testCard2));
		assertNotSame(testCard3, cardsManager.getLocalCard(testCard3));
		assertEquals(testCard3, cardsManager.getLocalCard(testCard3));
		
		assertEquals(testCard1.getPrimaryCardData(), testCard2.getPrimaryCardData());
		assertNotEquals(testCard1.getPrimaryCardData(), testCard3.getPrimaryCardData());
		assertNotEquals(testCard2.getPrimaryCardData(), testCard3.getPrimaryCardData());
		
		assertNotEquals(testCard1, cardsManager.getLocalCard(testCard3));
		assertNotEquals(testCard2, cardsManager.getLocalCard(testCard3));
		assertNotEquals(testCard3, cardsManager.getLocalCard(testCard1));
		assertNotEquals(testCard3, cardsManager.getLocalCard(testCard2));
		
		final Card otherCard = new Card((2 * CardsManager.PLAYER_ID_MULTIPLICATOR + 42), MtgDataUtility.getInstance().getMtgCard("black lotus"));
		assertFalse(cardsManager.getCards().contains(otherCard));
		
		final Card card = cardsManager.getLocalCard(otherCard);
		
		assertSame(card, otherCard);
		assertTrue(cardsManager.getCards().contains(otherCard));
	}
	
	@Test
	public void testGetCard() {
		
		add15CardsToLibrary();
		
		Card card = cardsManager.getCard(CardsManager.PLAYER_ID_MULTIPLICATOR + 2);
		assertNotNull(card);
		assertEquals(card.getPrimaryCardData(), MtgDataUtility.getInstance().getMtgCard("glorybringer"));
		
	}
	
	@Test
	public void testAddCardFromPlayer() {
		
		int battleId = 2 * CardsManager.PLAYER_ID_MULTIPLICATOR;		
		this.player.getLibrary().addAll(
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("glorybringer")),
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("Desecration Demon")),
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("plains")));	
		this.player.getHand().addAll(
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("plains")),
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("plains")),
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("Rampaging Ferocidon")));
		this.player.getBattlefield().addAll(
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("always watching")),
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("black lotus")),
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("plains")));
		this.player.getExile().addAll(
			new Card(battleId++, MtgDataUtility.getInstance().getMtgCard("glorybringer")));
		
		assertEquals(0, cardsManager.getCards().size());
		assertEquals(0, cardsManager.getPlayers().size());
		
		cardsManager.addCardsFromPlayer(player);
		
		assertEquals(10, cardsManager.getCards().size());
		assertEquals(1, cardsManager.getPlayers().size());
		assertEquals(battleId - 1, cardsManager.getCurrentBattleIdCounterOf(player));
	}
	
	@Test
	public void testClear() {
		
		assertEquals(0, cardsManager.getCards().size());
		assertEquals(0, cardsManager.getPlayers().size());
		add15CardsToLibrary();
		assertEquals(15, cardsManager.getCards().size());
		assertEquals(1, cardsManager.getPlayers().size());
		cardsManager.clear();
		assertEquals(0, cardsManager.getCards().size());
		assertEquals(0, cardsManager.getPlayers().size());
	}
	
	private void add15CardsToLibrary() {
		
		this.player.getLibrary().addAll(
			cardsManager.createCard(player, "always watching"),
			cardsManager.createCard(player, "always watching"),
			cardsManager.createCard(player, "glorybringer"),
			cardsManager.createCard(player, "glorybringer"),
			cardsManager.createCard(player, "Desecration Demon"),
			cardsManager.createCard(player, "Rampaging Ferocidon"),
			cardsManager.createCard(player, "Sulam Djinn"),
			cardsManager.createCard(player, "Harbinger of the Hunt"),
			cardsManager.createCard(player, "Mogg Jailer"),
			cardsManager.createCard(player, "Goblin Masons"),
			cardsManager.createCard(player, "Gate Hound"),
			cardsManager.createCard(player, "forest"),
			cardsManager.createCard(player, "Shredding Winds"),
			cardsManager.createCard(player, "plains"),
			cardsManager.createCard(player, "swamp"));
	}
}
