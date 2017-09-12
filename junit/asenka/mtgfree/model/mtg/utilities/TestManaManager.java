package asenka.mtgfree.model.mtg.utilities;

import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import asenka.mtgfree.model.mtg.mtgcard.MtgColor;
import asenka.mtgfree.model.mtg.mtgcard.TestDataProvider;
import asenka.mtgfree.model.mtg.utilities.ManaManager;

import static asenka.mtgfree.model.mtg.mtgcard.MtgColor.*;
import static asenka.mtgfree.model.mtg.utilities.ManaManager.*;
import static org.junit.Assert.*;

/**
 * @see ManaManager
 * @author Asenka
 */
public class TestManaManager {

	private static final String TEST_STR_COST_1_CORRECT = "2" + SEPARATOR + MANA_BLACK + SEPARATOR + MANA_RED;

	private static final String TEST_STR_COST_3_WRONG = "3|XX";

	private static final String TEST_STR_COST_4_CORRECT = "X|u|u";

	private static final String TEST_STR_COST_5_CORRECT = "0";

	private static final String TEST_STR_COST_6_CORRECT = "15|r|r|g|g";
	
	private TestDataProvider data = TestDataProvider.getInstance();

	private ManaManager manager;

	@Before
	public void setUp() {

		manager = ManaManager.getInstance();
		assertNotNull(manager);
		assertEquals(ManaManager.class, manager.getClass());
	}

	@Test
	public void testIsCorrectCost() {

		assertTrue(manager.isCorrectCost(TEST_STR_COST_1_CORRECT));
		assertTrue(manager.isCorrectCost(TEST_STR_COST_4_CORRECT));
		assertTrue(manager.isCorrectCost(TEST_STR_COST_5_CORRECT));
		assertTrue(manager.isCorrectCost(TEST_STR_COST_6_CORRECT));
		assertTrue(manager.isCorrectCost(MANA_BLACK));
		assertTrue(manager.isCorrectCost(MANA_RED));
		assertTrue(manager.isCorrectCost(MANA_BLUE));
		assertTrue(manager.isCorrectCost(MANA_WHITE));
		assertTrue(manager.isCorrectCost(MANA_GREEN));
		assertTrue(manager.isCorrectCost(MANA_GREEN + SEPARATOR + MANA_GREEN + SEPARATOR + MANA_GREEN));

		assertFalse(manager.isCorrectCost(TEST_STR_COST_3_WRONG));
		assertFalse(manager.isCorrectCost("coucou"));
		assertFalse(manager.isCorrectCost("r/b|b"));
		assertFalse(manager.isCorrectCost(""));
		assertFalse(manager.isCorrectCost("|||"));
		assertFalse(manager.isCorrectCost("|"));
		assertFalse(manager.isCorrectCost(MANA_GREEN + MANA_GREEN + MANA_GREEN));
	}

	@Test
	public void testGetConvertedCostMana() {

		assertEquals(4, manager.getConvertedCostMana(TEST_STR_COST_1_CORRECT));
		assertEquals(17, manager.getConvertedCostMana(TEST_STR_COST_4_CORRECT));
		assertEquals(0, manager.getConvertedCostMana(TEST_STR_COST_5_CORRECT));
		assertEquals(19, manager.getConvertedCostMana(TEST_STR_COST_6_CORRECT));
		assertEquals(0, manager.getConvertedCostMana(""));
	

		try {
			manager.getConvertedCostMana(TEST_STR_COST_3_WRONG);
			fail("An IllegalArgumentException was expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			manager.getConvertedCostMana("|");
			fail("An IllegalArgumentException was expected");
		} catch (IllegalArgumentException e) {
		}

		// TODO faire un test à partir d'une carte quand la classe MtgCard sera
		// finalisée
	}

	@Test
	public void testGetColors() {

		assertContains(manager.getColors(TEST_STR_COST_1_CORRECT), BLACK, RED);
		assertContains(manager.getColors(TEST_STR_COST_4_CORRECT)); // No color
		assertContains(manager.getColors(TEST_STR_COST_5_CORRECT)); // No color
		assertContains(manager.getColors(TEST_STR_COST_6_CORRECT), GREEN, RED);
		assertContains(manager.getColors("2r|2u|ur"), RED, BLUE);
		assertNotContains(manager.getColors("2r|2u|ur"), BLACK, GREEN, WHITE);

		try {
			manager.getColors("2r|2u|ru");
			fail("An IllegalArgumentException was expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			manager.getColors("rr");
			fail("An IllegalArgumentException was expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			manager.getColors("");
			fail("An IllegalArgumentException was expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			manager.getColors("|");
			fail("An IllegalArgumentException was expected");
		} catch (IllegalArgumentException e) {
		}

		assertContains(manager.getColors(data.mtgCard7), RED);
		assertContains(manager.getColors(data.mtgCard8));
		assertContains(manager.getColors(data.mtgCard10), WHITE);
		assertContains(manager.getColors(data.mtgCard11), BLACK, BLUE);
		assertContains(manager.getColors(data.mtgCard11), BLUE, BLACK);
	}

	/**
	 * 
	 * @param set
	 * @param colors
	 * @throws AssertionError
	 */
	private static void assertContains(Set<MtgColor> set, MtgColor... colors) throws AssertionError {

		for (MtgColor color : colors) {

			if (!set.contains(color)) {
				throw new AssertionError("The set " + set + " does not contains " + color);
			}
		}
	}

	/**
	 * 
	 * @param set
	 * @param colors
	 * @throws AssertionError
	 */
	private static void assertNotContains(Set<MtgColor> set, MtgColor... colors) throws AssertionError {

		for (MtgColor color : colors) {

			if (set.contains(color)) {
				throw new AssertionError("The set " + set + " contains " + color);
			}
		}
	}
}
