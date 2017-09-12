package asenka.mtgfree.model.mtg.mtgcard.comparators;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.junit.Test;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.TestDataProvider;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardCollectionComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardCostComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardNameComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardPowerComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardTextComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardToughnessComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardTypeComparator;
import asenka.mtgfree.model.mtg.utilities.ManaManager;

import static asenka.mtgfree.model.mtg.mtgcard.comparators.CardComparator.*;
import static java.util.Locale.*;

/**
 * @author asenka
 * @see CardComparator
 * @see CardTextComparator
 */
public class TestCardComparators {

	/**
	 * The data provider for the tests
	 */
	private final TestDataProvider data = TestDataProvider.getInstance();

	@Test
	public void testCardNameComparator() {

		List<MtgCard> cards = new ArrayList<MtgCard>(data.getListOfCards());

		CardComparator nameComparator = new CardNameComparator(FRENCH);
		Collections.sort(cards, nameComparator);
		assertSortedByName(cards, ASCENDING);

		nameComparator = new CardNameComparator(FRENCH, DESCENDING);
		Collections.sort(cards, nameComparator);
		assertSortedByName(cards, DESCENDING);
	}

	@Test
	public void testCardCollectionNameComparator() {

		List<MtgCard> cards = new ArrayList<MtgCard>(data.getListOfCards());

		CardComparator collectionComparator = new CardCollectionComparator(FRENCH);
		Collections.sort(cards, collectionComparator);
		assertSortedByCollection(cards, ASCENDING);

		collectionComparator = new CardCollectionComparator(FRENCH, DESCENDING);
		Collections.sort(cards, collectionComparator);
		assertSortedByCollection(cards, DESCENDING);
	}

	@Test
	public void testCardCostComparator() {

		List<MtgCard> cards = new ArrayList<MtgCard>(data.getListOfCards());

		CardComparator costComparator = new CardCostComparator();
		Collections.sort(cards, costComparator);
		assertSortedByCost(cards, ASCENDING);

		costComparator = new CardCostComparator(DESCENDING);
		Collections.sort(cards, costComparator);
		assertSortedByCost(cards, DESCENDING);
	}

	@Test
	public void testCardTypeComparator() {

		List<MtgCard> cards = new ArrayList<MtgCard>(data.getListOfCards());

		CardComparator typeComparator = new CardTypeComparator();
		Collections.sort(cards, typeComparator);
		assertSortedByType(cards, ASCENDING);

		typeComparator = new CardTypeComparator(DESCENDING);
		Collections.sort(cards, typeComparator);
		assertSortedByType(cards, DESCENDING);
	}

	@Test
	public void testCardPowerComparator() {

		List<MtgCard> cards = new ArrayList<MtgCard>(data.getListOfCards());

		CardComparator powerComparator = new CardPowerComparator();
		Collections.sort(cards, powerComparator);
		assertSortedByPower(cards, ASCENDING);

		powerComparator = new CardPowerComparator(DESCENDING);
		Collections.sort(cards, powerComparator);
		assertSortedByPower(cards, DESCENDING);
	}
	
	@Test
	public void testCardToughnessComparator() {
		
		List<MtgCard> cards = new ArrayList<MtgCard>(data.getListOfCards());
		
		CardComparator toughnessComparator = new CardToughnessComparator();
		Collections.sort(cards, toughnessComparator);
		assertSortedByToughness(cards, ASCENDING);
		
		toughnessComparator = new CardToughnessComparator(DESCENDING);
		Collections.sort(cards, toughnessComparator);
		assertSortedByToughness(cards, DESCENDING);
	}

	/**
	 * Check if the list of cards is sorted with according to the name. It assumes the provided data are in FRENCH
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
	 * Check if the list of cards is sorted with according to the collection name. It assumes the provided data are in FRENCH
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
	 * Check if the list of cards is sorted with according to the cost (mana)
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

	/**
	 * 
	 * @param listOfCards
	 * @param order
	 */
	private void assertSortedByType(List<MtgCard> listOfCards, int order) throws AssertionError {

		Collator collatorFrench = Collator.getInstance(Locale.FRENCH);

		try {
			Iterator<MtgCard> it = listOfCards.iterator();
			MtgCard previous = it.next();

			while (it.hasNext()) {
				MtgCard current = it.next();
				int result = collatorFrench.compare(previous.getType().getMainType(), current.getType().getMainType());

				if (result == 0) {
					collatorFrench.compare(previous.getType().getSubType(), current.getType().getSubType());
				}

				if ((order == ASCENDING) ? (result > 0) : (result < 0)) {
					throw new AssertionError("The list of cards is not sorted according to the checked order : " + order);
				}
				previous = current;
			}

		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}

	}

	/**
	 * Check if the list is sorted according to the power of the cards
	 * @param listOfCards
	 * @param order
	 * @throws AssertionError
	 */
	private void assertSortedByPower(List<MtgCard> listOfCards, int order) throws AssertionError {

		try {
			Iterator<MtgCard> it = listOfCards.iterator();
			MtgCard previous = it.next();

			while (it.hasNext()) {
				MtgCard current = it.next();
				int result = CardPowerComparator.getIntegerStatValue(previous.getPower())
						- CardPowerComparator.getIntegerStatValue(current.getPower());

				if ((order == ASCENDING) ? (result > 0) : (result < 0)) {
					throw new AssertionError("The list of cards is not sorted according to the checked order : " + order);
				}
				previous = current;
			}
		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}
	}
	
	/**
	 * Check if the list is sorted according to the power of the cards
	 * @param listOfCards
	 * @param order
	 * @throws AssertionError
	 */
	private void assertSortedByToughness(List<MtgCard> listOfCards, int order) throws AssertionError {
		
		try {
			Iterator<MtgCard> it = listOfCards.iterator();
			MtgCard previous = it.next();
			
			while (it.hasNext()) {
				MtgCard current = it.next();
				int result = CardToughnessComparator.getIntegerStatValue(previous.getToughness())
						- CardToughnessComparator.getIntegerStatValue(current.getToughness());
				
				if ((order == ASCENDING) ? (result > 0) : (result < 0)) {
					throw new AssertionError("The list of cards is not sorted according to the checked order : " + order);
				}
				previous = current;
			}
		} catch (NoSuchElementException ex) {
			throw new AssertionError(ex);
		}
	}

}
