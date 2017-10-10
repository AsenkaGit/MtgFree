package asenka.mtgfree.model.mtg.mtgcard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import asenka.mtgfree.model.mtg.MtgCollection;
import asenka.mtgfree.model.mtg.utilities.ManaManager;

/**
 * Class used to help create MtgCards easily for the tests
 * 
 * @author asenka
 *
 */
public class MtgCardBuilder {

	/**
	 * [BUILDING PARAMETER] The type of card to create
	 */
	private MtgType type;

	/**
	 * [BUILDING PARAMETER] The collection of the cards
	 */
	private MtgCollection collection;

	/**
	 * [BUILDING PARAMETER] The legal formats for the cards
	 */
	private List<MtgFormat> formats;

	/**
	 * [BUILDING PARAMETER] The color(s) of the created cards
	 */
	private List<MtgColor> colors;

	/**
	 * [BUILDING PARAMETER] The rarity of the cards
	 */
	private MtgRarity rarity;

	/**
	 * [BUILDING PARAMETER] The language of the cards
	 */
	private Locale locale;

	/**
	 * The set of abilities. It will be used to automatically add abilities on the creature cards
	 */
	private final List<MtgAbility> abilities;

	/**
	 * The id counter
	 */
	private int idCounter;

	/**
	 * 
	 * @param type
	 * @param collection
	 * @param formats
	 * @param abilities
	 */
	public MtgCardBuilder(int initID, MtgType type, MtgCollection collection, MtgFormat[] formats, Collection<MtgAbility> abilities,
			MtgColor[] colors, MtgRarity rarity, Locale locale) {

		this.idCounter = initID;
		this.type = type;
		this.collection = collection;
		this.formats = Arrays.asList(formats);
		this.abilities = new ArrayList<MtgAbility>(abilities);
		this.colors = Arrays.asList(colors);
		this.locale = locale;
		this.rarity = rarity;
	}

	/**
	 * Create a card and adds it to the current builder collection
	 * 
	 * @param name the card localized name
	 * @param cost the card cost (format = <code>"{X|X|...}"</code>) (it can be empty but not null)
	 * @param rulesText the localized text containing the card rules (it can be empty but not null)
	 * @param backgroundText the localized text containing the card story (it can be empty but not null)
	 * @param power the creature's power (if the card is not a creature, uses an empty string)
	 * @param toughness the creatures's toughness (if the card is not a creature, uses an empty string)
	 * @param loyalty the planeswalker's loyalty (uses -1 if the card is not a planeswalker)
	 * @return an MtgCard
	 */
	public MtgCard buildCard(String name, String cost, String rulesText, String backgroundText, String power, String toughness,
			int loyalty) {

		MtgCard card = new MtgCard(this.idCounter, name, cost, rulesText, backgroundText, new HashSet<MtgColor>(this.colors),
				this.collection.getName(), power, toughness, loyalty, this.type, MtgCardState.getNewInitialState(), this.rarity,
				new HashSet<MtgFormat>(this.formats), new HashSet<MtgAbility>(), "No comments", this.locale);

		ManaManager manaManager = ManaManager.getInstance();

		try {
			card.setColors(manaManager.getColors(cost));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		this.addAbilities(card);
		this.collection.addCards(card);
		this.idCounter++;
		return card;
	}

	/**
	 * Build a card with the building parameters and the specified values
	 * 
	 * @param id the card id
	 * @param name the card localized name
	 * @param cost the card cost (format = <code>"{X|X|...}"</code>) (it can be empty but not null)
	 * @param rulesText the localized text containing the card rules (it can be empty but not null)
	 * @param backgroundText the localized text containing the card story (it can be empty but not null)
	 * @param power the creature's power (if the card is not a creature, uses an empty string)
	 * @param toughness the creatures's toughness (if the card is not a creature, uses an empty string)
	 * @param loyalty the planeswalker's loyalty (uses -1 if the card is not a planeswalker)
	 * @param rarity the card rarity
	 * @return an MtgCard
	 */
	public MtgCard buildCard(int id, String name, String cost, String rulesText, String backgroundText, String power, String toughness,
			int loyalty, MtgRarity rarity) {

		MtgCard card = new MtgCard(id, name, cost, rulesText, backgroundText, new HashSet<MtgColor>(this.colors), this.collection.getName(),
				power, toughness, loyalty, this.type, MtgCardState.getNewInitialState(), rarity, new HashSet<MtgFormat>(this.formats),
				new HashSet<MtgAbility>(), "No comments", this.locale);

		ManaManager manaManager = ManaManager.getInstance();

		try {
			card.setColors(manaManager.getColors(cost));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		this.addAbilities(card);
		this.collection.addCards(card);
		return card;
	}

	/**
	 * 
	 * @param landType
	 * @param id
	 * @param name
	 * @param rulesText
	 * @param backgroundText
	 * @param rarity
	 * @return
	 */
	public MtgCard buildLandCard(MtgType landType, int id, String name, String rulesText, String backgroundText, MtgRarity rarity) {

		this.type = landType;
		return buildCard(id, name, "", rulesText, backgroundText, "", "", -1, rarity);
	}

	/**
	 * Analyze the card text to automatically add the ability on the card
	 * 
	 * @param card the card to update
	 */
	private void addAbilities(MtgCard card) {

		// TODO Revoir le fonctionnement de cette méthode qui est très approximatif pour le moment (trop simpliste)

		String cardText = card.getRulesText().toLowerCase();

		// If the card has a text and if the card is a creature...
		if (cardText != null && !cardText.isEmpty() && !card.getPower().isEmpty()) {

			for (MtgAbility ability : this.abilities) {

				if (cardText.contains(ability.getName().toLowerCase())) {
					card.addAbilities(ability);
				}
			}
		}
	}

	/**
	 * @param type
	 */
	public void setType(MtgType type) {

		this.type = type;
	}

	/**
	 * @param collection
	 */
	public void setCollection(MtgCollection collection) {

		this.collection = collection;
	}

	/**
	 * @param formats
	 */
	public void setFormats(MtgFormat... formats) {

		this.formats = Arrays.asList(formats);
	}

	/**
	 * @param colors
	 */
	public void setColors(MtgColor... colors) {

		this.colors = Arrays.asList(colors);
	}

	/**
	 * @param rarity
	 */
	public void setRarity(MtgRarity rarity) {

		this.rarity = rarity;
	}

	/**
	 * @param locale
	 */
	public void setLocale(Locale locale) {

		this.locale = locale;
	}

}
