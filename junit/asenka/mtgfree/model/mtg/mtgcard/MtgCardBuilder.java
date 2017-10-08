package asenka.mtgfree.model.mtg.mtgcard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import com.sun.org.glassfish.gmbal.ManagedAttribute;

import asenka.mtgfree.model.mtg.MtgCollection;
import asenka.mtgfree.model.mtg.utilities.ManaManager;

/**
 * 
 * 
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
	public MtgCardBuilder(int initID, MtgType type, MtgCollection collection, MtgFormat[] formats, MtgAbility[] abilities, MtgColor[] colors, MtgRarity rarity, Locale locale) {
		
		this.idCounter = initID;
		this.type = type;
		this.collection = collection;
		this.formats = Arrays.asList(formats);
		this.abilities = Arrays.asList(abilities);
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
				new HashSet<MtgAbility>(this.abilities), "No comments",  this.locale);
		
		ManaManager manaManager = ManaManager.getInstance();
		
		try {
			card.setColors(manaManager.getColors(cost));
		} catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		
		this.collection.addCards(card);
		this.idCounter++;
		return card;
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
	
		this.abilities = Arrays.asList(abilities);
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