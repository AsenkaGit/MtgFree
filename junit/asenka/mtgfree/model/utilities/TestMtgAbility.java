package asenka.mtgfree.model.utilities;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import asenka.mtgfree.model.mtgcard.MtgAbility;

/**
 * 
 * @author asenka
 * @see MtgAbility
 */
public class TestMtgAbility {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSorting() {
		
		MtgAbility a1 = new MtgAbility(1, "Vol", Locale.FRENCH);
		MtgAbility a2 = new MtgAbility(2, "Pi�tinement", Locale.FRENCH);
		MtgAbility a3 = new MtgAbility(3, "D�fence talismanique", Locale.FRENCH);
		MtgAbility a4 = new MtgAbility(4, "Initiative", Locale.FRENCH);
		MtgAbility a5 = new MtgAbility(5, "Double initiative", Locale.FRENCH);
		MtgAbility a6 = new MtgAbility(6, "Contact Mortel", Locale.FRENCH);
		
		List<MtgAbility> abilities = new ArrayList<MtgAbility>(6);
		abilities.add(a1);
		abilities.add(a2);
		abilities.add(a3);
		abilities.add(a4);
		abilities.add(a5);
		abilities.add(a6);
		
		Collections.sort(abilities);
		
		assertEquals(abilities.get(0).getId(), 6);
		assertEquals(abilities.get(1).getId(), 3);
		assertEquals(abilities.get(2).getId(), 5);
		assertEquals(abilities.get(3).getId(), 4);
		assertEquals(abilities.get(4).getId(), 2);
		assertEquals(abilities.get(5).getId(), 1);
	}

}
