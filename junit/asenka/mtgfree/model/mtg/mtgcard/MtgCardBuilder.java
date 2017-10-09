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
	
	private MtgType type;
	
	private MtgCollection collection;
	
	private List<MtgFormat> formats;
	
	private List<MtgAbility> abilities;
	
	private List<MtgColor> colors;
	
	private MtgRarity rarity;

	private Locale locale;

	private int idCounter;


	/**
	 * 
	 * @param type
	 * @param collection
	 * @param formats
	 * @param abilities
	 */
	public MtgCardBuilder(int initID, MtgType type, MtgCollection collection, MtgFormat[] formats, Collection<MtgAbility> abilities, MtgColor[] colors, MtgRarity rarity, Locale locale) {
		
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
	 * @param id
	 * @param name
	 * @param cost
	 * @param rulesText
	 * @param backgroundText
	 * @param power
	 * @param toughness
	 * @param loyalty
	 * @return
	 */
	public MtgCard buildCard(String name, String cost, String rulesText, String backgroundText, String power, String toughness, int loyalty) {
		
		MtgCard card = new MtgCard(this.idCounter, name, cost, rulesText, backgroundText, 
				new HashSet<MtgColor>(this.colors), this.collection.getName(), power, toughness, loyalty, this.type,
				MtgCardState.getNewInitialState(), this.rarity, new HashSet<MtgFormat>(this.formats), 
				new HashSet<MtgAbility>(), "No comments",  this.locale);
		
		ManaManager manaManager = ManaManager.getInstance();
		
		try {
			card.setColors(manaManager.getColors(cost));
		} catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		this.addAbilities(card);
		this.collection.addCards(card);
		this.idCounter++;
		return card;
	}
	
	/**
	 * Analyse the card text to automatically add the ability on the card
	 * @param card
	 */
	private void addAbilities(MtgCard card) {
		
		String cardText = card.getRulesText().toLowerCase();
		
		// If the card has a text and if the card is a creature...
		if(cardText != null && !cardText.isEmpty() && !card.getPower().isEmpty()) {
			
			for(MtgAbility ability : this.abilities) {
				
				if(cardText.contains(ability.getName().toLowerCase())) {
					card.addAbilities(ability);
				}
			}
		}
	}


	
	public MtgType getType() {
	
		return type;
	}


	
	public void setType(MtgType type) {
	
		this.type = type;
	}


	
	public MtgCollection getCollection() {
	
		return collection;
	}


	
	public void setCollection(MtgCollection collection) {
	
		this.collection = collection;
	}


	
	public List<MtgFormat> getFormats() {
	
		return formats;
	}


	
	public void setFormats(MtgFormat... formats) {
	
		this.formats = Arrays.asList(formats);
	}


	
	public List<MtgAbility> getAbilities() {
	
		return abilities;
	}


	
	public void setAbilities(MtgAbility... abilities) {
	
		this.abilities = new ArrayList<MtgAbility>();
		for (MtgAbility ability : abilities) {
			this.abilities.add(ability);
		}
	}


	
	public List<MtgColor> getColors() {
	
		return colors;
	}


	
	public void setColors(MtgColor... colors) {
	
		this.colors = Arrays.asList(colors);
	}


	
	public MtgRarity getRarity() {
	
		return rarity;
	}


	
	public void setRarity(MtgRarity rarity) {
	
		this.rarity = rarity;
	}


	
	public Locale getLocale() {
	
		return locale;
	}


	
	public void setLocale(Locale locale) {
	
		this.locale = locale;
	}

	
}
