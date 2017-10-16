package asenka.mtgfree.model.utilities.json;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestMtgDataUtility {
	
	private MtgDataUtility dataUtility;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
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
		
		assertNotNull(dataUtility.getListOfCardsFromSet("ALL"));
		
	}

}
