package asenka.mtgfree.model.mtgcard;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import asenka.mtgfree.model.mtgcard.MtgType;

/**
 * 
 * @author asenka
 * @see MtgType
 */
public class TestMtgType {

	@Test
	public void testSorting() {

		MtgType t1 = new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH);
		MtgType t2 = new MtgType(2, "Éphémère", "Éphémère", "", Locale.FRENCH);
		MtgType t3 = new MtgType(3, "Rituel", "Rituel", "", Locale.FRENCH);
		MtgType t4 = new MtgType(4, "Terrain", "Terrain de base : montagne", "", Locale.FRENCH);
		MtgType t5 = new MtgType(5, "Echangement", "Enchamentement : aura et mal�diction", "", Locale.FRENCH);
		MtgType t6 = new MtgType(6, "Créature", "Créature légendaire : dieu", "", Locale.FRENCH);

		List<MtgType> collection = new ArrayList<>();
		collection.add(t1);
		collection.add(t2);
		collection.add(t3);
		collection.add(t4);
		collection.add(t5);
		collection.add(t6);

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
				new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH));

	}

	@Test
	public void testHashCode() {

		assertEquals(new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH).hashCode(),
				new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH).hashCode());

		assertNotEquals(new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH).hashCode(),
				new MtgType(1, "Créature", "Créature : zombie et bête", "", Locale.FRENCH).hashCode());

	}

}
