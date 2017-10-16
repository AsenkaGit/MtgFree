package asenka.mtgfree.model.utilities.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import asenka.mtgfree.model.pojo.MtgCard;
import asenka.mtgfree.model.pojo.MtgSet;

/**
 * <p>
 * A helper class based on the singleton design pattern.
 * </p>
 * <p>
 * This object takes care of the MTG data loading (sets and cards) from a JSON file (from the web site
 * <a href="https://mtgjson.com">MTG JSON</a>) and stores in inside the objects in the package
 * <code>asenka.mtgfree.model.pojo</code>
 * </p>
 * 
 * @author asenka
 *
 */
public class MtgDataUtility {

	/**
	 * Log4j logger
	 */
	private static final Logger LOGGER = Logger.getLogger(MtgDataUtility.class);

	/**
	 * The default file path to the JSON file properly formatted
	 */
	private static final String DEFAULT_JSON_FILEPATH = "./resources/data/AllSetsArray-x.json";

	/**
	 * An estimate int value of the number of MTG sets in the JSON file. On the default JSON file, it is about 215 sets. It is
	 * better to have this estimated value to properly initialize the HashSet parameter <code>sets</code>.
	 */
	private static final int ESTIMATED_NUMBER_OF_SETS = 215;

	/**
	 * An estimate int value of the number of MTG cards in the JSON file. the value is 34506 with the default JSON file
	 */
	private static final int ESTIMATED_NUMBER_OF_CARDS = 34500;

	/**
	 * Comparator used in the standard binarySearch method to find a card among the list of cards. Thank to this comparator you
	 * can compare the name of an MtgCard to a string
	 * 
	 * @see MtgDataUtility#getMtgCard(String)
	 * @see Comparator
	 * @see Collator
	 */
	private static final Comparator<Object> SEARCH_CARD_BY_NAME_COMPARATOR = (Object card, Object name) -> Collator
		.getInstance(Locale.ENGLISH).compare(((MtgCard) card).getName().toLowerCase(), ((String) name).toLowerCase());

	/**
	 * The unique instance of this class on a JVM. You cannot create more than 1 instance of this object.
	 */
	private static MtgDataUtility singleton;

	/**
	 * The set of data. It contains all the MTG data about cards and sets.
	 * 
	 * @see MtgSet
	 */
	private final Set<MtgSet> sets;

	/**
	 * The set of cards used to search the cards
	 */
	private final List<MtgCard> cards;

	/**
	 * Creates and/or returns the unique instance of the {@link MtgDataUtility}
	 * 
	 * @return a JsonDataUtility instance
	 */
	public static MtgDataUtility getInstance() {

		if (singleton == null) {
			singleton = new MtgDataUtility(DEFAULT_JSON_FILEPATH);
		}
		return singleton;
	}

	/**
	 * Returns all the loaded sets
	 * 
	 * @return an unmodifiable Set with all the loaded MtgSet
	 * @see MtgSet
	 * @see Collections#unmodifiableSet(Set)
	 */
	public Set<MtgSet> getMtgSets() {

		return Collections.unmodifiableSet(this.sets);
	}

	/**
	 * Return an MtgSet
	 * 
	 * @param code the code of the set (e.g. <code>"AKH", "KLD",</code> etc....). This value is case sensitive
	 * @return the requested MtgSet according the the code
	 */
	public MtgSet getMtgSet(String code) {

		if (code != null) {

			Iterator<MtgSet> it = this.sets.iterator();

			while (it.hasNext()) {

				MtgSet set = it.next();

				if (code.equals(set.getCode())) {
					return set;
				}
			}
			return null;
		} else {
			throw new IllegalArgumentException("null value is not supported for the method parameter 'code'");
		}
	}

	/**
	 * Returns a sorted list (on the card's name) of all the cards
	 * 
	 * @return the list with all the cards available
	 * @see Collections#unmodifiableList(List)
	 */
	public List<MtgCard> getMtgCards() {

		return Collections.unmodifiableList(this.cards);
	}

	/**
	 * Returns the card with the requested name (not case sensitive)
	 * 
	 * @param name the card name requested
	 * @return an MtgCard or <code>null</code> if the card is not found
	 * @see Collections#binarySearch(List, Object, Comparator)
	 */
	public MtgCard getMtgCard(String name) {

		if (name != null) {

			// Use the standard binarySearch with a custom Comparator to compare an instance of MtgCard with an instance of
			// String
			int index = Collections.binarySearch(this.cards, name, SEARCH_CARD_BY_NAME_COMPARATOR);
			return index > 0 ? this.cards.get(index) : null;
		} else {
			throw new IllegalArgumentException("null value is not supported for the method parameter 'name'");
		}

	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public List<MtgCard> getListOfCardsFromSet(String code) {

		MtgSet set = getMtgSet(code);
		return set != null ? Arrays.asList(set.getCards()) : null;
	}

	/**
	 * Constructor initializing the
	 * 
	 * @param jsonFilepath A path to a JSON file with the following structure at least :<br />
	 *        <code>[ Object {<br /> 
	 * &nbsp;&nbsp;&nbsp;&nbsp;name : String, <br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;code : String, <br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;... , <br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;cards [ Object {<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name : String, <br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;id : String, <br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cmc : int,<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;colors : String[],<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}, ...]
	 *  <br />
	 * }, ...]</code>
	 *        <p>
	 *        You can download an appropriate file on the MTG JSON web site :
	 *        <a href="https://mtgjson.com/json/AllSetsArray.json">AllSetsArray.json</a>
	 *        </p>
	 *
	 */
	private MtgDataUtility(String jsonFilepath) {

		this.sets = new HashSet<MtgSet>(ESTIMATED_NUMBER_OF_SETS);
		this.cards = new ArrayList<MtgCard>(ESTIMATED_NUMBER_OF_CARDS);

		loadDataFromFile(jsonFilepath);

	}

	/**
	 * Read the JSON file and load the data from this file into the <code>sets</code> and <code>cards</code> parameters of this
	 * MtgDataUtility instance. The cards list is also created and sorted.
	 * 
	 * @param jsonFilePath the path to the JSON file to parse. The structure of the file must be compliant with the method
	 *        expectation (see the constructor parameters javadoc of this class)
	 */
	private void loadDataFromFile(String jsonFilePath) {

		LOGGER.trace("Start to load MTG data.");
		final long start = System.currentTimeMillis();

		final Gson jsonParser = new Gson();
		FileReader jsonFileReader = null;
		BufferedReader bufferedJsonFileReader = null;

		try {
			// Read the JSON file an parse it into a JsonArray
			// TODO Optimization would be nice here (it is this part that take quite a while to load)
			jsonFileReader = new FileReader(new File(jsonFilePath));
			bufferedJsonFileReader = new BufferedReader(jsonFileReader);
			JsonArray jsonContent = jsonParser.fromJson(bufferedJsonFileReader, JsonArray.class);

			// Go through the array to create all the MtgSet with all the MtgCards inside
			for (JsonElement jsonSet : jsonContent) {

				this.sets.add(jsonParser.fromJson(jsonSet, MtgSet.class));
			}
			// Fill and sort the cards list
			initializeCardsList();

			LOGGER.trace("MTG Data succesfully loaded in " + (System.currentTimeMillis() - start) + " ms.");

		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			LOGGER.error("Error while loading JSON file with MTG Data", e);
			throw new RuntimeException(e);
		} finally {
			try {
				// Close the file readers
				if (jsonFileReader != null) {
					jsonFileReader.close();
				}

				if (bufferedJsonFileReader != null) {
					bufferedJsonFileReader.close();
				}
			} catch (IOException e) {
				LOGGER.fatal("Unable to close file readers", e);
				throw new RuntimeErrorException(new Error(e));
			}
		}
	}

	/**
	 * Initialize the list of cards. Then the list is sorted according to the card names
	 * 
	 * @see Collections#sort(List)
	 * @see Comparable
	 */
	private void initializeCardsList() {

		Iterator<MtgSet> it = this.sets.iterator();

		while (it.hasNext()) {

			MtgSet set = it.next();

			for (MtgCard card : set.getCards()) {
				this.cards.add(card);
			}
		}
		// Sort the list of cards according to the implementation of MtgCard.compareTo(...) method (compares the card's name)
		Collections.sort(this.cards);
	}
}
