package asenka.mtgfree.utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import asenka.mtgfree.model.MtgCard;
import asenka.mtgfree.model.MtgColor;

/**
 * Class to use when you need to get info about a mana cost. A mana cost is always represented by a String with this format :
 * <br />
 * <br />
 * <code>[mana_element][separator][mana_element][separator][mana_element]..."</code><br />
 * <br />
 * e.g. <code>"2|r|r"</code> or <code>"b"</code> or <code>"X|u"</code> or
 * 
 * @author Asenka
 */
public class ManaManager {

	private static final String DEFAULT_SEPARATOR = "|";

	private static final int DEFAULT_MAX_MANA_VALUE = 15;

	public static final int MAX_MANA_VALUE = ManaManager.getMaxManaValue();

	public static final String SEPARATOR = ManaManager.getSeparator();

	public static final String MANA_RED = "r";

	public static final String MANA_GREEN = "g";

	public static final String MANA_WHITE = "w";

	public static final String MANA_BLACK = "b";

	public static final String MANA_BLUE = "u";

	public static final String MANA_TWO_OR_RED = "2r";

	public static final String MANA_TWO_OR_GREEN = "2g";

	public static final String MANA_TWO_OR_WHITE = "2w";

	public static final String MANA_TWO_OR_BLACK = "2b";

	public static final String MANA_TWO_OR_BLUE = "2u";

	public static final String MANA_GREEN_BLUE = "gu";

	public static final String MANA_GREEN_WHITE = "gw";

	public static final String MANA_RED_GREEN = "rg";

	public static final String MANA_RED_WHITE = "rw";

	public static final String MANA_BLUE_BLACK = "ub";

	public static final String MANA_BLUE_RED = "ur";

	public static final String MANA_WHITE_BLACK = "wb";

	public static final String MANA_WHITE_BLUE = "wu";

	public static final String MANA_BLACK_GREEN = "bg";

	public static final String MANA_BLACK_RED = "br";

	public static final String MANA_UNCOLORED = "c";

	public static final String MANA_X_ANY_COLOR = "X";

	public static final String MANA_Y_ANY_COLOR = "Y";

	public static final String MANA_Z_ANY_COLOR = "Z";

	/**
	 * The unique instance of the ManaManager
	 */
	private static ManaManager singleton;

	/**
	 * The map of legal values. For each string key, it associates an integer value representing the mana cost.
	 */
	private Map<String, Integer> manaLegalValues;

	/*
	 * Private constructor to make sure this class can not be instanciated outside.
	 */
	private ManaManager() {

		super();
		this.manaLegalValues = this.getLegalValuesAndCCM();
	}

	/**
	 * Returns the unique instance of the ManaManager
	 * 
	 * @return A ManaManager
	 */
	public static ManaManager getInstance() {

		if (singleton == null) {
			singleton = new ManaManager();
		}
		return singleton;
	}

	/**
	 * Check if the mana cost string is correct.
	 * 
	 * @param cost
	 * @return
	 */
	public boolean isCorrectCost(String cost) {

		if (cost == null || cost.isEmpty()) {
			return false;
		}
		StringTokenizer tokenizer = new StringTokenizer(cost.trim(), SEPARATOR);

		if (tokenizer.countTokens() == 0) {
			return false;
		}
		Set<String> legalValues = this.manaLegalValues.keySet();

		while (tokenizer.hasMoreTokens()) {

			String mana = tokenizer.nextToken();

			if (!legalValues.contains(mana)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Calculate the CCM (Converted Cost Mana) of a card
	 * 
	 * @param cost the string containing the mana cost of a card
	 * @return an integer value
	 * @throws IllegalArgumentException if the <code>cost</code> contains illegal values
	 */
	public int getConvertedCostMana(String cost) throws IllegalArgumentException {

		if (cost == null || cost.isEmpty()) {
			throw new IllegalArgumentException("The mana string [" + cost + "] is null or empty");
		}
		StringTokenizer tokenizer = new StringTokenizer(cost.trim(), SEPARATOR);

		if (tokenizer.countTokens() == 0) {
			throw new IllegalArgumentException("The mana string [" + cost + "] is not correct");
		}
		int ccm = 0; // ccm => Converted Cost Mana

		while (tokenizer.hasMoreTokens()) {

			String mana = tokenizer.nextToken();
			Integer manaValue = this.manaLegalValues.get(mana);

			if (manaValue != null) {
				ccm += manaValue;
			} else {
				throw new IllegalArgumentException("The mana string [" + cost + "] is not correct");
			}
		}
		return ccm;
	}

	/**
	 * Calculate the CCM (Converted Cost Mana) of a card
	 * 
	 * @param card an MtgCard (not null)
	 * @return an integer value
	 * @throws IllegalArgumentException if the <code>cost</code> contains illegal values or if <code>card</code> is null
	 * @see ManaManager#getConvertedCostMana(String)
	 */
	public int getConvertedCostMana(MtgCard card) throws IllegalArgumentException {

		if (card != null) {
			return this.getConvertedCostMana(card.getCost());
		} else {
			throw new IllegalArgumentException("null value for the card");
		}
	}

	/**
	 * Get the set of colors from a mana cost.
	 * 
	 * @param cost a string representing the mana cost of a card
	 * @return a set of colors (MtgColor)
	 * @see MtgColor
	 * @throws IllegalArgumentException
	 */
	public Set<MtgColor> getColors(String cost) throws IllegalArgumentException {

		if (cost == null || cost.isEmpty()) {
			throw new IllegalArgumentException("The mana string [" + cost + "] is null or empty");
		}
		StringTokenizer tokenizer = new StringTokenizer(cost.trim(), SEPARATOR);

		if (tokenizer.countTokens() == 0) {
			throw new IllegalArgumentException("The mana string [" + cost + "] is not correct");
		}
		Set<String> legalValues = this.manaLegalValues.keySet();
		Set<MtgColor> colors = new HashSet<MtgColor>(1);

		while (tokenizer.hasMoreTokens()) {

			String mana = tokenizer.nextToken();

			if (legalValues.contains(mana)) {

				if (mana.contains(MANA_RED)) {
					colors.add(MtgColor.RED);
				}
				if (mana.contains(MANA_BLUE)) {
					colors.add(MtgColor.BLUE);
				}
				if (mana.contains(MANA_GREEN)) {
					colors.add(MtgColor.GREEN);
				}
				if (mana.contains(MANA_WHITE)) {
					colors.add(MtgColor.WHITE);
				}
				if (mana.contains(MANA_BLACK)) {
					colors.add(MtgColor.BLACK);
				}
			} else {
				throw new IllegalArgumentException("The mana string [" + cost + "] is not correct");
			}
		}
		return colors;
	}

	/**
	 * 
	 * @param card
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Set<MtgColor> getColors(MtgCard card) throws IllegalArgumentException {

		if (card != null) {
			return this.getColors(card.getCost());
		} else {
			throw new IllegalArgumentException("null value for the card");
		}
	}

	/**
	 * @return
	 */
	private static String getSeparator() {

		// TODO Gestion par fichier de configuration
		return DEFAULT_SEPARATOR;
	}

	/**
	 * Build and returns the map of legal values for a mana token.
	 * 
	 * @return and HashMap<String, Integer> with all the mana values and the associated costF
	 */
	private Map<String, Integer> getLegalValuesAndCCM() {

		// TODO Gestion par fichier de configuration
		Map<String, Integer> legalsValuesAndAssociatedCCM = new HashMap<String, Integer>();

		legalsValuesAndAssociatedCCM.put(MANA_RED, 1);
		legalsValuesAndAssociatedCCM.put(MANA_GREEN, 1);
		legalsValuesAndAssociatedCCM.put(MANA_BLACK, 1);
		legalsValuesAndAssociatedCCM.put(MANA_WHITE, 1);
		legalsValuesAndAssociatedCCM.put(MANA_BLUE, 1);
		legalsValuesAndAssociatedCCM.put(MANA_UNCOLORED, 1);
		legalsValuesAndAssociatedCCM.put(MANA_BLACK_GREEN, 1);
		legalsValuesAndAssociatedCCM.put(MANA_BLACK_RED, 1);
		legalsValuesAndAssociatedCCM.put(MANA_RED_GREEN, 1);
		legalsValuesAndAssociatedCCM.put(MANA_RED_WHITE, 1);
		legalsValuesAndAssociatedCCM.put(MANA_GREEN_BLUE, 1);
		legalsValuesAndAssociatedCCM.put(MANA_GREEN_WHITE, 1);
		legalsValuesAndAssociatedCCM.put(MANA_BLUE_BLACK, 1);
		legalsValuesAndAssociatedCCM.put(MANA_BLUE_RED, 1);
		legalsValuesAndAssociatedCCM.put(MANA_WHITE_BLACK, 1);
		legalsValuesAndAssociatedCCM.put(MANA_WHITE_BLUE, 1);
		legalsValuesAndAssociatedCCM.put(MANA_TWO_OR_RED, 2);
		legalsValuesAndAssociatedCCM.put(MANA_TWO_OR_GREEN, 2);
		legalsValuesAndAssociatedCCM.put(MANA_TWO_OR_BLACK, 2);
		legalsValuesAndAssociatedCCM.put(MANA_TWO_OR_WHITE, 2);
		legalsValuesAndAssociatedCCM.put(MANA_TWO_OR_BLUE, 2);
		legalsValuesAndAssociatedCCM.put(MANA_X_ANY_COLOR, MAX_MANA_VALUE);
		legalsValuesAndAssociatedCCM.put(MANA_Y_ANY_COLOR, MAX_MANA_VALUE);
		legalsValuesAndAssociatedCCM.put(MANA_Z_ANY_COLOR, MAX_MANA_VALUE);

		// TODO Plusieurs valeurs de mana manquent ici. On complétera plus tard

		// Insert a string value for a quantity of mana (any color)
		// from 0 to MAX_MANA_VALUE (it should be 15)
		for (int i = 0; i <= MAX_MANA_VALUE; i++) {
			legalsValuesAndAssociatedCCM.put(Integer.toString(i), i);
		}
		return legalsValuesAndAssociatedCCM;
	}

	/**
	 * Returns the maximum value for the mana quantity of any color. Usually this value is 15. But the value may be changed in a
	 * configuration file
	 * 
	 * @return an integer value for the maximum mana quantity of any color
	 */
	private static int getMaxManaValue() {

		// TODO Gestion par fichier de configuration
		return DEFAULT_MAX_MANA_VALUE;
	}
}
