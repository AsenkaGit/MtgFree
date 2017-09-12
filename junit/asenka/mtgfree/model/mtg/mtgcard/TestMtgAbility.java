package asenka.mtgfree.model.mtg.mtgcard;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import asenka.mtgfree.model.mtg.mtgcard.MtgAbility;

/**
 * 
 * @author asenka
 * @see MtgAbility
 */
public class TestMtgAbility {

	
	private TestDataProvider data = TestDataProvider.getInstance();

	@Test
	public void testSorting() {
		
		List<MtgAbility> abilities = new ArrayList<MtgAbility>(data.getListOfAbilities());
		
		Collections.sort(abilities);
		
		assertEquals(abilities.get(0).getId(), 6);
		assertEquals(abilities.get(1).getId(), 3);
		assertEquals(abilities.get(2).getId(), 5);
		assertEquals(abilities.get(3).getId(), 4);
		assertEquals(abilities.get(4).getId(), 2);
		assertEquals(abilities.get(5).getId(), 1);
	}

}
