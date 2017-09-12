package asenka.mtgfree.model.mtgcard.comparators;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.junit.Test;

import asenka.mtgfree.model.mtgcard.MtgCard;
import asenka.mtgfree.model.mtgcard.TestDataProvider;
import asenka.mtgfree.model.utilities.ManaManager;

import static asenka.mtgfree.model.mtgcard.comparators.CardComparator.*;
import static java.util.Locale.*;

/**
 * 
 * @author asenka
 * @see CardComparator
 * @see CardTextComparator
 */
public class TestCardComparators {

	private final TestDataProvider data = TestDataProvider.getInstance();

	private List<MtgCard> cards = new ArrayList<MtgCard>(data.getListOfCards());

	@Test
	public void testCardNameComparator() {

		CardComparator nameComparator = new CardNameComparator(FRENCH);
		Collections.sort(cards, nameComparator);
		assertSortedByName(cards, ASCENDING);
		
		nameComparator = new CardNameComparator(FRENCH, DESCENDING);
		Collections.sort(cards, nameComparator);
		assertSortedByName(cards, DESCENDING);
	}
	

	@Test
	public void testCardCollectionNameComparator() {

		CardComparator collectionComparator = new CardCollectionComparator(FRENCH);
		Collections.sort(cards, collectionComparator);
		assertSortedByCollection(cards, ASCENDING);
		
		collectionComparator = new CardCollectionComparator(FRENCH, DESCENDING);
		Collections.sort(cards, collectionComparator);
		assertSortedByCollection(cards, DESCENDING);
	}
	
	
	@Test
	public void testCardCostComparator() {
		
		CardComparator costComparator = new CardCostComparator();
		Collections.sort(cards, costComparator);
		assertSortedByCost(cards, ASCENDING);
		
		costComparator = new CardCostComparator(DESCENDING);
		Collections.sort(cards, costComparator);
		assertSortedByCost(cards, DESCENDING);
	}
	
	
	
	/**
	 * Check if the list of cards is sorted with according to the name. 
	 * It assumes the provided data are in FRENCH
	 * 
	 * @param listOfCards
	 * @param order 
	 * @throws AssertionError
	 */
	private void assertSortedByName(List<MtgCard> listOfCards, int order) throws AssertionError {

		Collator collatorFrench = Collator.getInstance(Locale.FRENCH);

		try {
			Iterator<MtgCard> it = listOfCards.iterator();
			MtgCard previous = it.next();

			while (it.hasNext()) {
				MtgCard current = it.next();
				int result = collatorFrench.compare(previous.getName(), current.getName());
				boolean sorted = (order == ASCENDING) ? (result > 0) : (result < 0);
				
				if (sorted) {
					throw new AssertionError("The list of cards is not sorted according to the checked order : " + order);
				}
				previous = current;
			}

		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}
	}
	
	
	
	/**
	 * Check if the list of cards is sorted with according to the collection name.
	 * It assumes the provided data are in FRENCH
	 * 
	 * @param listOfCards
	 * @param order 
	 * @throws AssertionError
	 */
	private void assertSortedByCollection(List<MtgCard> listOfCards, int order) throws AssertionError {

		Collator collatorFrench = Collator.getInstance(Locale.FRENCH);

		try {
			Iterator<MtgCard> it = listOfCards.iterator();
			MtgCard previous = it.next();

			while (it.hasNext()) {
				MtgCard current = it.next();
				int result = collatorFrench.compare(previous.getCollectionName(), current.getCollectionName());
				boolean sorted = (order == ASCENDING) ? (result > 0) : (result < 0);
				
				if (sorted) {
					throw new AssertionError("The list of cards is not sorted according to the checked order : " + order);
				}
				previous = current;
			}

		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}
	}
	
	/**
	 * Check if the list of cards is sorted with according to the cost (mana).
	 * It assumes the provided data are in FRENCH
	 * 
	 * @param listOfCards
	 * @param order 
	 * @throws AssertionError
	 */
	private void assertSortedByCost(List<MtgCard> listOfCards, int order) throws AssertionError {
		
		ManaManager manaManager = ManaManager.getInstance();
		
		try {
			Iterator<MtgCard> it = listOfCards.iterator();
			MtgCard previous = it.next();
			
			while (it.hasNext()) {
				MtgCard current = it.next();
				int result = manaManager.getConvertedCostMana(previous) - manaManager.getConvertedCostMana(current);
				boolean sorted = (order == ASCENDING) ? (result > 0) : (result < 0);
				
				if (sorted) {
					throw new AssertionError("The list of cards is not sorted according to the checked order : " + order);
				}
				previous = current;
			}
			
		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}
	}

}
