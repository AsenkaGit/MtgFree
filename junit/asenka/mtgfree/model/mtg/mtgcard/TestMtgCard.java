package asenka.mtgfree.model.mtg.mtgcard;

import static asenka.mtgfree.model.mtg.mtgcard.MtgColor.*;
import static org.junit.Assert.*;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

import asenka.mtgfree.model.mtg.mtgcard.MtgAbility;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgColor;
import asenka.mtgfree.tests.utilities.TestDataProvider;

/**
 * 
 * @author asenka
 * @see MtgCard
 */
public class TestMtgCard {

	private TestDataProvider data = TestDataProvider.getInstance();

	@Test
	public void testDefaultSorting() {

		// Use a copy of the list of cards provided
		List<MtgCard> list = new ArrayList<MtgCard>(data.getListOfCards());
		Collections.sort(list);
		assertSortedByNameAndCollection(list);
	}

	@Test
	public void testEqualsAndHashCode() {

		MtgCard copyCard = new MtgCard(data.mtgCard10);
		assertEquals(copyCard, data.mtgCard10);
		assertEquals(copyCard.hashCode(), data.mtgCard10.hashCode());

		assertNotEquals(data.mtgCard1, data.mtgCard2);
		assertNotEquals(data.mtgCard1.hashCode(), data.mtgCard2.hashCode());
	}

	@Test
	public void testSetColors() {

		// TODO ce comportement est un peu bizarre... Peut être faudra-t-il simplifier pour plus de cohérence.

		MtgCard jet = new MtgCard(data.mtgCard7);
		jet.setColors(RED); // Doing this is useless, but it should be work without exception raised

		try {
			jet.setColors(GREEN);
			fail("An IllegalArgumentException was expected at this point");
		} catch (IllegalArgumentException ex) {
			// even though an exception has been raised to notify that the color change is not correct
			// the color of the cards are changed
			assertContainsColors(jet.getColors(), GREEN);
			jet.setCost("2|g"); // Change the cost to have it green now
		} finally {
			jet.setColors(GREEN); // Now it should be ok
			assertContainsColors(jet.getColors(), GREEN);
		}
	}

	@Test
	public void testSetCost() {

		MtgCard card = new MtgCard(data.mtgCard7);
		assertNotContainsColors(card.getColors(), GREEN, BLUE);
		assertContainsColors(card.getColors(), RED);
		card.setCost("2r|ur|g");
		assertContainsColors(card.getColors(), RED, GREEN, BLUE);
	}

	@Test
	public void testAddAbilities() {

		MtgCard card = new MtgCard(data.mtgCard15);
		assertEquals(card.getAbilities().size(), 0);

		List<MtgAbility> abilities = data.getListOfAbilities();

		card.addAbilities(abilities.get(0));
		card.addAbilities(abilities.get(0));
		card.addAbilities(abilities.get(0));
		card.addAbilities(abilities.get(1));
		card.addAbilities(abilities.get(2));

		assertContainsAbilities(card.getAbilities(), abilities.get(0), abilities.get(1), abilities.get(2));
		assertEquals(card.getAbilities().size(), 3);
	}

	/**
	 * Check if the list of cards is sorted with according to the name and then to the collection name with an ASCENDING order in
	 * both comparator. It assumes the provided data are in FRENCH
	 * 
	 * @param listOfCards
	 * @throws AssertionError
	 */
	private void assertSortedByNameAndCollection(List<MtgCard> listOfCards) throws AssertionError {

		Collator collatorFrench = Collator.getInstance(Locale.FRENCH);

		try {
			Iterator<MtgCard> it = listOfCards.iterator();
			MtgCard previous = it.next();

			while (it.hasNext()) {
				MtgCard current = it.next();
				int result = collatorFrench.compare(previous.getName(), current.getName());

				if (result == 0) {
					result = collatorFrench.compare(previous.getCollectionName(), current.getCollectionName());
				}

				// Check if 'current' if greater than 'previous'
				if (result > 0) {
					throw new AssertionError("The list of cards is not sorted with an ASCENDING order");
				}
				previous = current;
			}

		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}
	}

	/**
	 * 
	 * @param set
	 * @param colors
	 * @throws AssertionError
	 */
	private static void assertContainsColors(Set<MtgColor> set, MtgColor... colors) throws AssertionError {

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
	private static void assertNotContainsColors(Set<MtgColor> set, MtgColor... colors) throws AssertionError {

		for (MtgColor color : colors) {

			if (set.contains(color)) {
				throw new AssertionError("The set " + set + " contains " + color);
			}
		}
	}

	/**
	 * 
	 * @param set
	 * @param abilities
	 * @throws AssertionError
	 */
	private static void assertContainsAbilities(Set<MtgAbility> set, MtgAbility... abilities) throws AssertionError {

		for (MtgAbility ability : abilities) {

			if (!set.contains(ability)) {
				throw new AssertionError("The set " + set + " does not contains " + ability);
			}
		}
	}

}
