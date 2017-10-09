package asenka.mtgfree.model.mtg.mtgcard;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Observable;
import java.util.Set;

import asenka.mtgfree.model.mtg.events.MtgCardContextUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgCardLocationUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgCardSelectionUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgCardRevealUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgCardTapUpdatedEvent;
import asenka.mtgfree.model.mtg.events.MtgCardVisibilityUpdatedEvent;
import asenka.mtgfree.model.mtg.exceptions.MtgContextException;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardCollectionComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardComparator;
import asenka.mtgfree.model.mtg.mtgcard.comparators.CardNameComparator;
import asenka.mtgfree.model.mtg.utilities.ManaManager;
import asenka.mtgfree.model.utilities.Localized;

/**
 * This class represents a Mtg Card. It stores all the necessary data about the cards
 * 
 * @author asenka
 * @see Observable
 * @see Comparable
 * @see Localized
 * @see Serializable
 */
public class MtgCard extends Observable implements Comparable<MtgCard>, Localized, Serializable {

	/**
	 * The serial number use to serialize this object
	 */
	private static final long serialVersionUID = 1554761911799087091L;

	/**
	 * The unique id of a card (based on the ID in the database)
	 */
	private int id;

	/**
	 * The card name
	 */
	private String name;

	/**
	 * The cost of a card is represented by an array of string because each symbol needs a string representation.
	 * 
	 * @see ManaManager
	 */
	private String cost;

	/**
	 * The text explaining the effect of the card
	 */
	private String rulesText;

	/**
	 * The text about the Mtg background. Useless for the game but it is part of the Mtg lore
	 */
	private String backgroundText;

	/**
	 * A card usually have one color : red, blue, green, black or white. But it can also have no color (like most of the
	 * artifacts) or also like some Eldrazi creatures. Also, it can have more than one color if the mana cost of a card include
	 * more than one color of mana. That is the reason why the color is an EnumSet of MtgColor
	 * 
	 * @see MtgColor
	 */
	private Set<MtgColor> colors;

	/**
	 * The name of the card collection.
	 */
	private String collectionName; // I don't to use an MtgCollection here to
									// avoid circular references

	/**
	 * If the card is a creature, it has power and toughness. The power is stored as a string because sometimes this stat is not
	 * expressed by a number.
	 */
	private String power;

	/**
	 * If the card is a creature, it has power and toughness. The toughness is stored as a string because sometimes this stat is
	 * not expressed by a number.
	 */
	private String toughness;

	/**
	 * The loyalty value for the planeswalkers (only). <br />
	 * <code>{k > 0 if and only if card type is Planeswalker} {k = -1 otherwise}</code>
	 */
	private int loyalty;

	/**
	 * The type of a card
	 */
	private MtgType type;

	/**
	 * The rarity level of a card
	 */
	private MtgRarity rarity;

	/**
	 * The legal formats for this cards
	 */
	private Set<MtgFormat> formats;

	/**
	 * The abilities related to this card
	 */
	private Set<MtgAbility> abilities;

	/**
	 * This value is not mandatory. But it could be interesting to have some comments about the card. To add some details about
	 * the cards (not localized).
	 */
	private String comments;

	/**
	 * The state of a card. Only used during a game, it stores all the necessary data to manipulate the card while playing
	 * (coordinates, context, is the card tapped or not, etc.)
	 */
	private MtgCardState state;

	/**
	 * The language used for the card data
	 */
	private Locale locale;

	/**
	 * The default comparator used to sort the cards in a collection. The default behavior is to sort 1/ with the card name and
	 * then 2/ with the collection name
	 */
	private transient CardComparator defaultComparator; // The default comparator is not serialized because it is built from another value

	/**
	 * Create a exact copy of the card <code>copyFrom</code>
	 * 
	 * @param copyFrom the card to copy
	 */
	// TODO Only package access for test, we'll see later if it's relevant to have it 'public'
	MtgCard(MtgCard copyFrom) {

		this.id = copyFrom.id;
		this.name = new String(copyFrom.name);
		this.cost = new String(copyFrom.cost);
		this.rulesText = new String(copyFrom.rulesText);
		this.backgroundText = new String(copyFrom.backgroundText);
		this.colors = new HashSet<MtgColor>(copyFrom.colors);
		this.collectionName = new String(copyFrom.collectionName);
		this.power = new String(copyFrom.power);
		this.toughness = new String(copyFrom.toughness);
		this.loyalty = copyFrom.loyalty;
		this.type = copyFrom.type;
		this.state = copyFrom.state != null ? new MtgCardState(copyFrom.state) : null;
		this.rarity = copyFrom.rarity;
		this.formats = new HashSet<MtgFormat>(copyFrom.formats);
		this.abilities = new HashSet<MtgAbility>(copyFrom.abilities);
		this.comments = new String(copyFrom.comments);
		this.locale = copyFrom.locale;
		this.defaultComparator = copyFrom.defaultComparator;
	}

	/**
	 * Constructor (interesting to instantiate land cards)
	 * 
	 * @param id
	 * @param name
	 * @param collectionName
	 * @param color
	 * @param type
	 * @param rarity
	 * @param locale
	 */
	public MtgCard(int id, String name, String collectionName, MtgColor color, MtgType type, MtgRarity rarity, Locale locale) {

		this(id, name, "", "", "", null, collectionName, "", "", -1, type, MtgCardState.getNewInitialState(), rarity,
				new HashSet<MtgFormat>(), new HashSet<MtgAbility>(), "", locale);
		this.setColors(color);
	}

	/**
	 * Constructor (interesting to instantiate sort cards)
	 * 
	 * @param id
	 * @param name
	 * @param collectionName
	 * @param cost
	 * @param type
	 * @param rarity
	 * @param locale
	 */
	public MtgCard(int id, String name, String collectionName, String cost, MtgType type, MtgRarity rarity, Locale locale) {

		this(id, name, cost, "", "", ManaManager.getInstance().getColors(cost), collectionName, "", "", -1, type,
				MtgCardState.getNewInitialState(), rarity, new HashSet<MtgFormat>(), new HashSet<MtgAbility>(), "", locale);
	}

	/**
	 * Constructor (interesting to instantiate creature cards)
	 * 
	 * @param id
	 * @param name
	 * @param collectionName
	 * @param power
	 * @param toughness
	 * @param cost
	 * @param type
	 * @param rarity
	 * @param locale
	 */
	public MtgCard(int id, String name, String collectionName, String power, String toughness, String cost, MtgType type, MtgRarity rarity,
			Locale locale) {

		this(id, name, cost, "", "", ManaManager.getInstance().getColors(cost), collectionName, power, toughness, -1, type,
				MtgCardState.getNewInitialState(), rarity, new HashSet<MtgFormat>(), new HashSet<MtgAbility>(), "", locale);
	}

	/**
	 * Constructor with all values
	 * 
	 * @param id
	 * @param name
	 * @param cost
	 * @param rulesText
	 * @param backgroundText
	 * @param colors
	 * @param collectionName
	 * @param power a string with a number from 0 to 99 or X or * or a combination of the previous
	 * @param toughness a string with a number from 0 to 99 or X or * or a combination of the previous
	 * @param loyalty an integer > 0 for planeswalker and -1 for all other types of card
	 * @param type
	 * @param state
	 * @param rarity
	 * @param formats
	 * @param abilities
	 * @param comments
	 * @param locale
	 */
	public MtgCard(int id, String name, String cost, String rulesText, String backgroundText, Set<MtgColor> colors, String collectionName,
			String power, String toughness, int loyalty, MtgType type, MtgCardState state, MtgRarity rarity, Set<MtgFormat> formats,
			Set<MtgAbility> abilities, String comments, Locale locale) {

		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.rulesText = rulesText;
		this.backgroundText = backgroundText;
		this.colors = colors;
		this.collectionName = collectionName;
		this.power = power;
		this.toughness = toughness;
		this.loyalty = loyalty;
		this.type = type;
		this.state = state;
		this.rarity = rarity;
		this.formats = formats;
		this.abilities = abilities;
		this.comments = comments;
		this.locale = locale;

		// Creates the default comparator with 2 criteria
		// 1. the card name
		// 2. the collection name
		CardComparator collectionComparator = new CardCollectionComparator(this.locale);
		this.defaultComparator = new CardNameComparator(collectionComparator, this.locale);
	}

	/**
	 * @return the card id
	 */
	public int getId() {

		return id;
	}

	/**
	 * @param id the card id
	 */
	public void setId(int id) {

		this.id = id;
	}

	/**
	 * @return the localized card name
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name the card name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return a string representing the mana cost of the card
	 */
	public String getCost() {

		return cost;
	}

	/**
	 * Update the cost value. Updating the cost, also update the colors,
	 * 
	 * @param cost a string representing the mana cost of a card
	 * @throws IllegalArgumentException if cost is null or if it contains some illegal values
	 */
	public void setCost(String cost) throws IllegalArgumentException {

		ManaManager manaManager = ManaManager.getInstance();

		// If cost is not empty, update the colors of the card
		if (cost != null && !cost.isEmpty()) {
			this.colors = manaManager.getColors(cost);
		}
		this.cost = cost;
	}

	/**
	 * @return a string with with the rules of the card
	 */
	public String getRulesText() {

		return rulesText;
	}

	/**
	 * @param rulesText the rules of the card
	 */
	public void setRulesText(String rulesText) {

		this.rulesText = rulesText;
	}

	/**
	 * @return
	 */
	public String getBackgroundText() {

		return backgroundText;
	}

	/**
	 * @param backgroundText
	 */
	public void setBackgroundText(String backgroundText) {

		this.backgroundText = backgroundText;
	}

	/**
	 * @return a set of colors matching the card
	 */
	public Set<MtgColor> getColors() {

		return this.colors;
	}

	/**
	 * Compare a card to an array of colors and check if all the colors matches the card color
	 * 
	 * @param colors the array of color(s) (it can contains only one color)
	 * @return <code>true</code> if the array of color(s) perfectly matches the card color(s)
	 */
	public boolean isColor(MtgColor... colors) {

		boolean result = true;
		int i = 0;

		while (result && i < colors.length) {

			if (!this.colors.contains(colors[i++])) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * Set the card's color and check if they matches with the card's cost.
	 * 
	 * @param colors a set of MtgColor
	 * @throws IllegalArgumentException if the new colors and the cost are not compatible
	 */
	public void setColors(Set<MtgColor> colors) throws IllegalArgumentException {

		this.colors = colors;
	}

	/**
	 * Set the card's color and check if they matches with the card's cost.
	 * 
	 * @param colors an array of MtgColor
	 * @throws IllegalArgumentException if the new colors and the cost are not compatible
	 */
	public void setColors(MtgColor... colors) throws IllegalArgumentException {

		// Even though, an exception has been raised due to cost and
		// color incompatibility the value is still set. That's why
		// the setting is done before the checking.
		this.colors = new HashSet<MtgColor>(colors.length);

		for (MtgColor color : colors) {
			this.colors.add(color);
		}

		// Check if the cost and the new card's colors are still matching.
		if (!this.cost.isEmpty() && !this.colors.equals(ManaManager.getInstance().getColors(this.cost))) {
			throw new IllegalArgumentException("The new color(s) " + Arrays.toString(colors) + " do not match the card cost : " + cost);
		}
	}

	/**
	 * @return the localized name of the card's collection
	 */
	public String getCollectionName() {

		return collectionName;
	}

	/**
	 * Set the collectionName
	 * 
	 * @param collectionName the localized name of the card's collection
	 */
	public void setCollectionName(String collectionName) {

		this.collectionName = collectionName;
	}

	/**
	 * Get the card's power. It the card is not a creature or a vehicle it returns an empty string
	 * 
	 * @return the card power.
	 */
	public String getPower() {

		return power;
	}

	/**
	 * set the card's power
	 * 
	 * @param power a string with a number from 0 to 99 or X or * or a combination of the previous
	 */
	public void setPower(String power) {

		this.power = power;
	}

	/**
	 * @return the toughness of a card
	 */
	public String getToughness() {

		return toughness;
	}

	/**
	 * @param toughness the toughness of a card
	 */
	public void setToughness(String toughness) {

		this.toughness = toughness;
	}

	/**
	 * Only for planewalkers card. Otherwise the value is -1
	 * 
	 * @return an integer value > 0 for planewalkers
	 */
	public int getLoyalty() {

		return loyalty;
	}

	/**
	 * Only for planewalkers card. Otherwise the value is -1
	 * 
	 * @param loyalty
	 */
	public void setLoyalty(int loyalty) {

		this.loyalty = loyalty;
	}

	/**
	 * @return the type of the card
	 * @see MtgType
	 */
	public MtgType getType() {

		return type;
	}

	/**
	 * @param type the type of the card
	 * @see MtgType
	 */
	public void setType(MtgType type) {

		this.type = type;
	}

	/**
	 * @return the rarity of the card
	 * @see MtgRarity
	 */
	public MtgRarity getRarity() {

		return rarity;
	}

	/**
	 * @param rarity
	 * @see MtgRarity
	 */
	public void setRarity(MtgRarity rarity) {

		this.rarity = rarity;
	}

	/**
	 * @return the legal formats of this card
	 */
	public Set<MtgFormat> getFormats() {

		return formats;
	}

	/**
	 * Set the legal formats of the card
	 * 
	 * @param formats
	 */
	public void setFormats(Set<MtgFormat> formats) {

		this.formats = formats;
	}

	/**
	 * @return
	 * @see MtgAbility
	 */
	public Set<MtgAbility> getAbilities() {

		return abilities;
	}

	/**
	 * @param abilities the abilities of the card
	 * @see MtgAbility
	 */
	public void setAbilities(Set<MtgAbility> abilities) {

		this.abilities = abilities;
	}

	/**
	 * Add another ability to this card
	 * 
	 * @param abilities
	 */
	public void addAbilities(MtgAbility... abilities) {

		if (this.abilities == null) {
			this.abilities = new HashSet<MtgAbility>(abilities.length);
		}

		for (MtgAbility ability : abilities) {
			this.abilities.add(ability);
		}
	}

	/**
	 * @return
	 */
	public String getComments() {

		return comments;
	}

	/**
	 * @param comments
	 */
	public void setComments(String comments) {

		this.comments = comments;
	}

	/**
	 * @return the current card state (location, context, tapped or not, visible or not, etc...)
	 * @see MtgCardState
	 */
	// This method has been set to protected because the state object should
	// not be manipulated outside this package
	protected MtgCardState getState() {

		return state;
	}

	/**
	 * Update the card state with another card state. The value are copied
	 * one by one. It is not a reference update.
	 * 
	 * @param state the state to get the values from
	 * @see MtgCardState
	 */
	// This method has been set to protected because the state object should
	// not be manipulated outside this package
	protected void setState(MtgCardState state) {

		this.setContext(state.getContext());
		this.setLocation(state.getLocation().getX(), state.getLocation().getY());
		this.setRevealed(state.isRevealed());
		this.setTapped(state.isTapped());
		this.setVisible(state.isVisible());
	}

	/**
	 * The visibility indicates if the card should display the front side or the back side
	 * 
	 * @return true if the card should be visible on the battlefield
	 */
	public boolean isVisible() {

		return this.state.isVisible();
	}

	/**
	 * Update the visibility of a card and notify all the observers (if necessary). The observer are notified
	 * with a {@link MtgCardVisibilityUpdatedEvent} 
	 * @param visible <code>true</code> if you want to display the front side.
	 * @see MtgCardVisibilityUpdatedEvent
	 */
	public void setVisible(boolean visible) {

		// Check if it is a new value
		if (this.state.isVisible() != visible) {
			this.state.setVisible(visible);
			super.setChanged(); // <= Without this call, the notify method just below does nothing
			super.notifyObservers(new MtgCardVisibilityUpdatedEvent(this));
		}
	}

	/**
	 * @return <code>true</code>rue if the card is tapped
	 */
	public boolean isTapped() {

		return this.state.isTapped();
	}

	/**
	 * Update the tapped property of a card and notify all the observers (if necessary). The observer are notified
	 * with a {@link MtgCardTapUpdatedEvent} 
	 * @param tapped <code>true</code> if you want to tap the card
	 * @throws MtgContextException if the current context does not allow the new value
	 * @see MtgCardTapUpdatedEvent
	 */
	public void setTapped(boolean tapped) throws MtgContextException {

		// Check if it is a new value
		if (this.state.isTapped() != tapped) {
			this.state.setTapped(tapped);
			super.setChanged();
			super.notifyObservers(new MtgCardTapUpdatedEvent(this));
		}
	}

	/**
	 * @return <code>true</code> if the card is revealed to other players from the owner's hand
	 */
	public boolean isRevealed() {

		return this.state.isRevealed();
	}

	/**
	 * Set if a card in a player's hand is revealed and notify the observers if
	 * it is necessary (new value)
	 * 
	 * @param revealed <code>true</code> if the card is revealed from player's hand
	 * @throws MtgContextException if the current context does not allow the new value
	 * @see MtgCardRevealUpdatedEvent
	 */
	public void setRevealed(boolean revealed) throws MtgContextException {

		// Check if it is a new value
		if (this.state.isRevealed() != revealed) {
			this.state.setRevealed(revealed);
			super.setChanged();
			super.notifyObservers(new MtgCardRevealUpdatedEvent(this));
		}
	}

	/**
	 * Get the location of the card
	 * 
	 * @return a Point2D.Double object with the (x,y) double coordinates
	 * @see Point2D.Double
	 */
	public Point2D.Double getLocation() {

		return this.state.getLocation();
	}

	/**
	 * Update the card location and notify the observers that the location of the card has changed (if necessary).
	 * The observers are notified with a {@link MtgCardLocationUpdatedEvent}
	 * 
	 * @param x the new horizontal coordinate
	 * @param y the new vertical coordinate
	 * @see MtgCardState
	 * @see MtgCardLocationUpdatedEvent
	 */
	public void setLocation(final double x, final double y) {

		// Check if the new location is different
		if (this.state.getLocation().getX() != x || this.state.getLocation().getY() != y) {

			this.state.setLocation(x, y);
			super.setChanged();
			super.notifyObservers(new MtgCardLocationUpdatedEvent(this));
		}

	}

	/**
	 * @return the current context of a card (BATTLEFIELD, LIBRARY, OUT_OF_GAME, etc...)
	 * @see MtgContext
	 */
	public MtgContext getContext() {

		return this.state.getContext();
	}

	/**
	 * Change the context of the card. Updating the context change also the values of 
	 * the booleans parameters of the card state.
	 * <br />
	 * <br />
	 * <i>e.g.</i> Setting the context to HAND, will change the parameters like this:<br />
	 * <code>isRevealed = false</code> (by default a card in the player's hand it not revealed)<br />
	 * <code>isVisible = false</code> (this property make sense only on the BATTLEFIELD context)<br />
	 * <code>location = (-1, -1)</code> (this property make sense only on the BATTLEFIELD context)<br />
	 * <code>isTapped = false</code> (this property make sense only on the BATTLEFIELD context).
	 * <br /><br />
	 * 
	 * @param context the context of the card
	 * @see MtgCardContextUpdatedEvent
	 */
	public void setContext(MtgContext context) {

		switch (context) {
			case OUT_OF_GAME:
			case LIBRARY:
			case HAND:
				// Update the state values and notify the observers if necessary
				this.setRevealed(false);
				this.setTapped(false);
				this.setVisible(false);
				this.setLocation(-1.0, -1.0);
				break;
			case GRAVEYARD:
				this.setRevealed(false);
				this.setTapped(false);
				this.setVisible(true); // when moving a card to grave yard, it becomes visible automatically
				break;
			case BATTLEFIELD:
			case EXILE:
				this.setRevealed(false);
				this.setTapped(false);
				// When moving to exile, the visible status is kept as it was
				break;
		}
		this.state.setContext(context);
		super.setChanged();
		super.notifyObservers(new MtgCardContextUpdatedEvent(this));
	}

	/**
	 * 
	 * @return <code>true</code> if the card is selected
	 */
	public boolean isSelected() {
	
		return this.state.isSelected();
	}

	/**
	 * 
	 * @param selected
	 * @see MtgCardSelectionUpdatedEvent
	 */
	public void setSelected(boolean selected) {
	
		// Check if it is a new value
		if (this.state.isSelected() != selected) {
			this.state.setSelected(selected);
			super.setChanged();
			super.notifyObservers(new MtgCardSelectionUpdatedEvent(this));
		}
	}

	/**
	 * Use this value to know the card language
	 * 
	 * @return the locale used for the card
	 */
	@Override
	public Locale getLocale() {

		return this.locale;
	}

	@Override
	public String toString() {

		return "[" + id + ", " + name + ", " + cost + ", " + rulesText + ", " + backgroundText + ", " + colors + ", " + collectionName
				+ ", " + power + ", " + toughness + ", " + loyalty + ", " + type + ", " + state + ", " + rarity + ", " + formats + ", "
				+ abilities + ", " + comments + ", " + locale + "]\n";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((abilities == null) ? 0 : abilities.hashCode());
		result = prime * result + ((backgroundText == null) ? 0 : backgroundText.hashCode());
		result = prime * result + ((collectionName == null) ? 0 : collectionName.hashCode());
		result = prime * result + ((colors == null) ? 0 : colors.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((formats == null) ? 0 : formats.hashCode());
		result = prime * result + id;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + loyalty;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((power == null) ? 0 : power.hashCode());
		result = prime * result + ((rarity == null) ? 0 : rarity.hashCode());
		result = prime * result + ((rulesText == null) ? 0 : rulesText.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((toughness == null) ? 0 : toughness.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MtgCard other = (MtgCard) obj;
		if (abilities == null) {
			if (other.abilities != null)
				return false;
		} else if (!abilities.equals(other.abilities))
			return false;
		if (backgroundText == null) {
			if (other.backgroundText != null)
				return false;
		} else if (!backgroundText.equals(other.backgroundText))
			return false;
		if (collectionName == null) {
			if (other.collectionName != null)
				return false;
		} else if (!collectionName.equals(other.collectionName))
			return false;
		if (colors == null) {
			if (other.colors != null)
				return false;
		} else if (!colors.equals(other.colors))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (formats == null) {
			if (other.formats != null)
				return false;
		} else if (!formats.equals(other.formats))
			return false;
		if (id != other.id)
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (loyalty != other.loyalty)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (power == null) {
			if (other.power != null)
				return false;
		} else if (!power.equals(other.power))
			return false;
		if (rarity != other.rarity)
			return false;
		if (rulesText == null) {
			if (other.rulesText != null)
				return false;
		} else if (!rulesText.equals(other.rulesText))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (toughness == null) {
			if (other.toughness != null)
				return false;
		} else if (!toughness.equals(other.toughness))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public int compareTo(MtgCard card) {

		return this.defaultComparator.compare(this, card);
	}
}
