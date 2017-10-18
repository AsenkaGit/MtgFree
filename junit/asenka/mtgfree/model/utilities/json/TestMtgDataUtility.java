package asenka.mtgfree.model.utilities.json;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;

public class TestMtgDataUtility {

	@BeforeClass
	public static void setUpBeforeClass() {

		Logger.getLogger(TestMtgDataUtility.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestMtgDataUtility.class).debug("--------------------- Begin Junit test ---------------------");
	}

	@AfterClass
	public static void afterClass() {

		Logger.getLogger(TestMtgDataUtility.class).debug("--------------------- End Junit test ---------------------");
	}

	private MtgDataUtility dataUtility;

	public TestMtgDataUtility() {
		this.dataUtility = MtgDataUtility.getInstance();
	}

	@Test
	public void testGetMtgCard() {

		assertEquals("Black Lotus", dataUtility.getMtgCard("black lotus").getName());
		assertEquals("Artifact", dataUtility.getMtgCard("blaCK lotus").getType());
		assertTrue(0f == dataUtility.getMtgCard("Black Lotus").getCmc());

		assertEquals("Always Watching", dataUtility.getMtgCard("always watching").getName());
		assertEquals("Enchantment", dataUtility.getMtgCard("always watching").getType());
		assertTrue(3f == dataUtility.getMtgCard("always watching").getCmc());
	}

	@Test
	public void testGetMtgSet() {

		assertEquals("Amonkhet", dataUtility.getMtgSet("AKH").getName());
		assertEquals("Amonkhet", dataUtility.getMtgSet("HOU").getBlock());
		assertNotNull(dataUtility.getMtgSet("KLD").getCards());
	}

	@Test
	public void testGetListOfCardsFromSet() {

		List<MtgCard> cardsFromALL = dataUtility.getListOfCardsFromSet("ALL");
		
		assertNotNull(cardsFromALL);
		assertEquals(199, cardsFromALL.size());
	}
	
	@Test
	public void testFiltering() {
		
		final String type = "Instant";
		final float cmc = 3f;
		final String colorIdentity = "U"; 
		
		Predicate<MtgCard> filterType = ( mtgCard -> {
			String[] types = mtgCard.getTypes();
			if(types != null && types.length > 0) {
				return types[0].toLowerCase().contains(type.toLowerCase());
			} else {
				return false;
			}
		});
		
		Predicate<MtgCard> filterColor = ( mtgCard -> {
			String[] colors = mtgCard.getColorIdentity();
			if(colors != null) {
				return Arrays.stream(colors).allMatch(str -> str.equals(colorIdentity));
			} else {
				return false;
			}
		});

		Predicate<MtgCard> filterCmc = ( mtgCard -> mtgCard.getCmc() == cmc);
		
		List<MtgCard> allCards = dataUtility.getMtgCards();
			
		assertEquals(34506, allCards.size());
		assertEquals(214, allCards.stream().filter(filterType.and(filterCmc).and(filterColor)).count());
		assertEquals(17782, allCards.stream().distinct().count());
	}

}
