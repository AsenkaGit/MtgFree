package asenka.mtgfree.model.utilities.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import javax.management.RuntimeErrorException;

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
	 * The default file path to the JSON file properly formatted
	 */
	private static final String DEFAULT_JSON_FILEPATH = "./resources/data/AllSetsArray-x.json";

	/**
	 * An estimate int value of the number of MTG sets in the JSON file. On the default JSON file, it is about 215 sets. It is
	 * better to have this estimated value to properly initialize the HashSet parameter <code>sets</code>.
	 */
	private static final int ESTIMATED_NUMBER_OF_SETS = 215;

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
	 * 
	 * @param code
	 * @return
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
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public List<MtgCard> getMtgCards() {

		return Collections.unmodifiableList(this.cards);
	}

	public MtgCard getMtgCard(String name) {

		int index = Collections.binarySearch(this.cards, MtgCard.getSearchCardBasedOnName(name));
		return index > 0 ? this.cards.get(index) : null;
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

		loadDataFromFile(jsonFilepath);

		this.cards = new ArrayList<MtgCard>();
		initializeCardsSet();
	}

	/**
	 * 
	 * @param jsonFilePath
	 */
	private void loadDataFromFile(String jsonFilePath) {

		// final long start = System.currentTimeMillis();
		Gson jsonParser = new Gson();
		FileReader jsonFileReader = null;
		BufferedReader bufferedJsonFileReader = null;

		try {
			// Read the JSON file an parse it into a JsonArray
			jsonFileReader = new FileReader(new File(jsonFilePath));
			bufferedJsonFileReader = new BufferedReader(jsonFileReader);
			JsonArray jsonContent = jsonParser.fromJson(bufferedJsonFileReader, JsonArray.class);

			for (JsonElement jsonSet : jsonContent) {

				this.sets.add(jsonParser.fromJson(jsonSet, MtgSet.class));
			}

		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
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
				throw new RuntimeErrorException(new Error(e));
			}
		}
	}

	/**
	 * 
	 */
	private void initializeCardsSet() {

		Iterator<MtgSet> it = this.sets.iterator();

		while (it.hasNext()) {

			MtgSet set = it.next();

			for (MtgCard card : set.getCards()) {
				this.cards.add(card);
			}
		}
		Collections.sort(this.cards);
	}

	public static void main(String[] args) throws Exception {

		long start = System.currentTimeMillis();
		MtgDataUtility dataUtility = MtgDataUtility.getInstance();
		System.out.println(System.currentTimeMillis() - start + " ms");
		System.out.println(dataUtility.getMtgCard("Black Lotus"));

	}

}
