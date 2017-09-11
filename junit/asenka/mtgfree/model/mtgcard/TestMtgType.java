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

		List<MtgType> collection = new ArrayList<>(data.listOfTypes);

		Collections.sort(collection);

		assertEquals(collection.get(0).getId(), 1);
		assertEquals(collection.get(1).getId(), 6);
		assertEquals(collection.get(2).getId(), 5);
		assertEquals(collection.get(3).getId(), 2);
		assertEquals(collection.get(4).getId(), 3);
		assertEquals(collection.get(5).getId(), 4);
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
	

}
