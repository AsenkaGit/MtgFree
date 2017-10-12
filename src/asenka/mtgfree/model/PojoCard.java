package asenka.mtgfree.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

/**
 * 
 * 
 * @author asenka
 * @see https://mtgjson.com/documentation.html
 *
 */
public class PojoCard implements Serializable {

	/**
	 * A unique id for this card. It is made up by doing an SHA1 hash of setCode + cardName + cardImageName
	 */
	private String id;

	/**
	 * 
	 */
	private String layout;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String[] names;

	/**
	 * 
	 */
	private String manaCost;

	/**
	 * 
	 */
	private float cmc;

	/**
	 * 
	 */
	private String[] colors;

	/**
	 * 
	 */
	private String[] colorIdentity;

	/**
	 * 
	 */
	private String type;

	/**
	 * 
	 */
	private String[] supertypes;

	/**
	 * 
	 */
	private String[] types;

	/**
	 * 
	 */
	private String[] subtypes;

	/**
	 * 
	 */
	private String rarity;

	/**
	 * 
	 */
	private String text;

	/**
	 * 
	 */
	private String flavor;

	/**
	 * 
	 */
	private String artist;

	/**
	 * 
	 */
	private String number;

	/**
	 * 
	 */
	private String power;

	/**
	 * 
	 */
	private String toughness;

	/**
	 * 
	 */
	private int loyalty;

	/**
	 * 
	 */
	private int multiverseid;

	/**
	 * 
	 */
	private int[] variations;

	/**
	 * 
	 */
	private String imageName;

	/**
	 * 
	 */
	private String watermark;

	/**
	 * 
	 */
	private String border;

	/**
	 * 
	 */
	private boolean timeshifted;

	/**
	 * 
	 */
	private int hand;

	/**
	 * 
	 */
	private int life;

	/**
	 * 
	 */
	private boolean reserved;

	/**
	 * 
	 */
	private String releaseDate;

	/**
	 * 
	 */
	private boolean starter;

	/**
	 * 
	 */
	private int mciNumber;

	/**
	 * @param id
	 * @param layout
	 * @param name
	 * @param names
	 * @param manaCost
	 * @param cmc
	 * @param colors
	 * @param colorIdentity
	 * @param type
	 * @param supertypes
	 * @param types
	 * @param subtypes
	 * @param rarity
	 * @param text
	 * @param flavor
	 * @param artist
	 * @param number
	 * @param power
	 * @param toughness
	 * @param loyalty
	 * @param multiverseid
	 * @param variations
	 * @param imageName
	 * @param watermark
	 * @param border
	 * @param timeshifted
	 * @param hand
	 * @param life
	 * @param reserved
	 * @param releaseDate
	 * @param starter
	 * @param mciNumber
	 */
	public PojoCard(String id, String layout, String name, String[] names, String manaCost, float cmc, String[] colors,
			String[] colorIdentity, String type, String[] supertypes, String[] types, String[] subtypes, String rarity, String text,
			String flavor, String artist, String number, String power, String toughness, int loyalty, int multiverseid, int[] variations,
			String imageName, String watermark, String border, boolean timeshifted, int hand, int life, boolean reserved,
			String releaseDate, boolean starter, int mciNumber) {
		super();
		this.id = id;
		this.layout = layout;
		this.name = name;
		this.names = names;
		this.manaCost = manaCost;
		this.cmc = cmc;
		this.colors = colors;
		this.colorIdentity = colorIdentity;
		this.type = type;
		this.supertypes = supertypes;
		this.types = types;
		this.subtypes = subtypes;
		this.rarity = rarity;
		this.text = text;
		this.flavor = flavor;
		this.artist = artist;
		this.number = number;
		this.power = power;
		this.toughness = toughness;
		this.loyalty = loyalty;
		this.multiverseid = multiverseid;
		this.variations = variations;
		this.imageName = imageName;
		this.watermark = watermark;
		this.border = border;
		this.timeshifted = timeshifted;
		this.hand = hand;
		this.life = life;
		this.reserved = reserved;
		this.releaseDate = releaseDate;
		this.starter = starter;
		this.mciNumber = mciNumber;
	}

	/**
	 * @return
	 */
	public String getId() {

		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {

		this.id = id;
	}

	/**
	 * @return
	 */
	public String getLayout() {

		return layout;
	}

	/**
	 * @param layout
	 */
	public void setLayout(String layout) {

		this.layout = layout;
	}

	/**
	 * @return
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return
	 */
	public String[] getNames() {

		return names;
	}

	/**
	 * @param names
	 */
	public void setNames(String[] names) {

		this.names = names;
	}

	/**
	 * @return
	 */
	public String getManaCost() {

		return manaCost;
	}

	/**
	 * @param manaCost
	 */
	public void setManaCost(String manaCost) {

		this.manaCost = manaCost;
	}

	/**
	 * @return
	 */
	public float getCmc() {

		return cmc;
	}

	/**
	 * @param cmc
	 */
	public void setCmc(float cmc) {

		this.cmc = cmc;
	}

	/**
	 * @return
	 */
	public String[] getColors() {

		return colors;
	}

	/**
	 * @param colors
	 */
	public void setColors(String[] colors) {

		this.colors = colors;
	}

	/**
	 * @return
	 */
	public String[] getColorIdentity() {

		return colorIdentity;
	}

	/**
	 * @param colorIdentity
	 */
	public void setColorIdentity(String[] colorIdentity) {

		this.colorIdentity = colorIdentity;
	}

	/**
	 * @return
	 */
	public String getType() {

		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {

		this.type = type;
	}

	/**
	 * @return
	 */
	public String[] getSupertypes() {

		return supertypes;
	}

	/**
	 * @param supertypes
	 */
	public void setSupertypes(String[] supertypes) {

		this.supertypes = supertypes;
	}

	/**
	 * @return
	 */
	public String[] getTypes() {

		return types;
	}

	/**
	 * @param types
	 */
	public void setTypes(String[] types) {

		this.types = types;
	}

	/**
	 * @return
	 */
	public String[] getSubtypes() {

		return subtypes;
	}

	/**
	 * @param subtypes
	 */
	public void setSubtypes(String[] subtypes) {

		this.subtypes = subtypes;
	}

	/**
	 * @return
	 */
	public String getRarity() {

		return rarity;
	}

	/**
	 * @param rarity
	 */
	public void setRarity(String rarity) {

		this.rarity = rarity;
	}

	/**
	 * @return
	 */
	public String getText() {

		return text;
	}

	/**
	 * @param text
	 */
	public void setText(String text) {

		this.text = text;
	}

	/**
	 * @return
	 */
	public String getFlavor() {

		return flavor;
	}

	/**
	 * @param flavor
	 */
	public void setFlavor(String flavor) {

		this.flavor = flavor;
	}

	/**
	 * @return
	 */
	public String getArtist() {

		return artist;
	}

	/**
	 * @param artist
	 */
	public void setArtist(String artist) {

		this.artist = artist;
	}

	/**
	 * @return
	 */
	public String getNumber() {

		return number;
	}

	/**
	 * @param number
	 */
	public void setNumber(String number) {

		this.number = number;
	}

	/**
	 * @return
	 */
	public String getPower() {

		return power;
	}

	/**
	 * @param power
	 */
	public void setPower(String power) {

		this.power = power;
	}

	/**
	 * @return
	 */
	public String getToughness() {

		return toughness;
	}

	/**
	 * @param toughness
	 */
	public void setToughness(String toughness) {

		this.toughness = toughness;
	}

	/**
	 * @return
	 */
	public int getLoyalty() {

		return loyalty;
	}

	/**
	 * @param loyalty
	 */
	public void setLoyalty(int loyalty) {

		this.loyalty = loyalty;
	}

	/**
	 * @return
	 */
	public int getMultiverseid() {

		return multiverseid;
	}

	/**
	 * @param multiverseid
	 */
	public void setMultiverseid(int multiverseid) {

		this.multiverseid = multiverseid;
	}

	/**
	 * @return
	 */
	public int[] getVariations() {

		return variations;
	}

	/**
	 * @param variations
	 */
	public void setVariations(int[] variations) {

		this.variations = variations;
	}

	/**
	 * @return
	 */
	public String getImageName() {

		return imageName;
	}

	/**
	 * @param imageName
	 */
	public void setImageName(String imageName) {

		this.imageName = imageName;
	}

	/**
	 * @return
	 */
	public String getWatermark() {

		return watermark;
	}

	/**
	 * @param watermark
	 */
	public void setWatermark(String watermark) {

		this.watermark = watermark;
	}

	/**
	 * @return
	 */
	public String getBorder() {

		return border;
	}

	/**
	 * @param border
	 */
	public void setBorder(String border) {

		this.border = border;
	}

	/**
	 * @return
	 */
	public boolean isTimeshifted() {

		return timeshifted;
	}

	/**
	 * @param timeshifted
	 */
	public void setTimeshifted(boolean timeshifted) {

		this.timeshifted = timeshifted;
	}

	/**
	 * @return
	 */
	public int getHand() {

		return hand;
	}

	/**
	 * @param hand
	 */
	public void setHand(int hand) {

		this.hand = hand;
	}

	/**
	 * @return
	 */
	public int getLife() {

		return life;
	}

	/**
	 * @param life
	 */
	public void setLife(int life) {

		this.life = life;
	}

	/**
	 * @return
	 */
	public boolean isReserved() {

		return reserved;
	}

	/**
	 * @param reserved
	 */
	public void setReserved(boolean reserved) {

		this.reserved = reserved;
	}

	/**
	 * @return
	 */
	public String getReleaseDate() {

		return releaseDate;
	}

	/**
	 * @param releaseDate
	 */
	public void setReleaseDate(String releaseDate) {

		this.releaseDate = releaseDate;
	}

	/**
	 * @return
	 */
	public boolean isStarter() {

		return starter;
	}

	/**
	 * @param starter
	 */
	public void setStarter(boolean starter) {

		this.starter = starter;
	}

	/**
	 * @return
	 */
	public int getMciNumber() {

		return mciNumber;
	}

	/**
	 * @param mciNumber
	 */
	public void setMciNumber(int mciNumber) {

		this.mciNumber = mciNumber;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		String jsonFilePath = "./resources/data/AllCards.json";
		JsonReader jsonReader = new JsonReader(new FileReader(new File(jsonFilePath)));

		Gson gson = new Gson();
		JsonObject jsonSets = gson.fromJson(jsonReader, JsonObject.class);
		
		Iterator<Entry<String, JsonElement>> it = jsonSets.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<String, JsonElement> element = it.next();
			String key = element.getKey();
			PojoCard card = gson.fromJson(element.getValue(), PojoCard.class);
			
			System.out.println(key);
			System.out.println(card);
		}
		
	}

}
