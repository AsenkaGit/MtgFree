package asenka.mtgfree.model.mtgcard;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * 
 * @author asenka
 * @see MtgCard
 */
public class TestMtgCard {

	private MtgDataProvider data = MtgDataProvider.getInstance();

	@Test
	public void testDefaultSorting() {

		// Use a copy of the list of cards provided
		List<MtgCard> list = new ArrayList<MtgCard>(data.listOfCards);
		Collections.sort(list);
		assertSortedByNameAndCollection(list);
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
				if (result > 0) {
					throw new AssertionError("The list of cards is not sorted with an ASCENDING order");
				}
				previous = current;
			}

		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}
	}
}
