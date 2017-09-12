package asenka.mtgfree.model.mtgcard;

import static org.junit.Assert.*;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.junit.Test;

import asenka.mtgfree.model.mtgcard.MtgType;

/**
 * 
 * @author asenka
 * @see MtgType
 */
public class TestMtgType {
	
	private TestDataProvider data = TestDataProvider.getInstance();

	@Test
	public void testSorting() {

		List<MtgType> types = new ArrayList<MtgType>(data.getListOfTypes());
		Collections.sort(types);
		assertSortedByMainTypeAndSubtype(types);
		System.out.println(types);
	}

	@Test
	public void testEquals() {

		assertEquals(new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH),
				new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH));

		assertNotEquals(new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH),
				new MtgType(1, "Créature", "Créature : zombie et bete", "", Locale.FRENCH));

	}

	@Test
	public void testHashCode() {

		assertEquals(new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH).hashCode(),
				new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH).hashCode());

		assertNotEquals(new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH).hashCode(),
				new MtgType(1, "Créature", "Créature : zombie et bete", "", Locale.FRENCH).hashCode());

	}
	
	
	/**
	 * Check if the list of types is sorted with according to the main type
	 * and then with the sub type with an ASCENDING order in
	 * both comparator. It assumes the provided data are in FRENCH
	 * 
	 * @param list a list of types
	 * @throws AssertionError
	 */
	private void assertSortedByMainTypeAndSubtype(List<MtgType> list) throws AssertionError {

		Collator collatorFrench = Collator.getInstance(Locale.FRENCH);

		try {
			Iterator<MtgType> it = list.iterator();
			MtgType previous = it.next();

			while (it.hasNext()) {
				MtgType current = it.next();
				int result = collatorFrench.compare(previous.getMainType(), current.getMainType());

				if (result == 0) {
					result = collatorFrench.compare(previous.getSubType(), current.getSubType());
				}
				
				// Check if 'current' if greater than 'previous'
				if (result > 0) {
					throw new AssertionError("The list of types is not sorted with an ASCENDING order");
				}
				previous = current;
			}

		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}
	}
	

}
