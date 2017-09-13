package asenka.mtgfree.model.mtg.mtgcard;

import java.util.Locale;

import asenka.mtgfree.model.utilities.Localized;

/**
 * This enumeration manage the 5 available color in the Mtg Game : Red, Blue, White, Green & Black. The enum is loaded from a
 * configuration file to get the localized name of the color.<br />
 * <br />
 * 
 * @author asenka
 */
public enum MtgColor implements Localized {

	RED(getLocalizedColorName("red")), 
	BLUE(getLocalizedColorName("blue")), 
	WHITE(getLocalizedColorName("white")), 
	GREEN(getLocalizedColorName("green")), 
	BLACK(getLocalizedColorName("black"));

	/**
	 * A Localized name of the color.
	 */
	private String name;

	/**
	 * The language used for the color name
	 */
	private Locale locale;

	/*
	 * Constructor
	 */
	MtgColor(String localizedName) {

		this.name = localizedName;
		this.locale = Locale.getDefault();
		// TODO par défaut on met une locale Anglaise. Il faudra utiliser par la suite le fichier
		// de configuration de l'utilisateur pour trouver la bonne locale à utiliser.
	}

	/**
	 * @return The localized name of the color
	 */
	public String getName() {

		return this.name;
	}

	@Override
	public Locale getLocale() {

		return this.locale;
	}

	/**
	 * Find a returns the localized value for a color name
	 * 
	 * @param strColor Only : <code>"red", "blue", "black", "green", or "white"</code>
	 * @return a localized name for the color
	 * @throws IllegalArgumentException if the strColor is not one of the 5 accepted values
	 */
	private static String getLocalizedColorName(String strColor) {

		// TODO Changer cette méthode pour intéroger un fichier de configuration avec les differentes langues
		// La langue doit venir d'un objet qui contiendra l'ensemble des préférences utilisateur.

		if ("red".equals(strColor)) {
			return "Red";
		} else if ("blue".equals(strColor)) {
			return "Blue";
		} else if ("white".equals(strColor)) {
			return "White";
		} else if ("green".equals(strColor)) {
			return "Green";
		} else if ("black".equals(strColor)) {
			return "Black";
		} else {
			throw new IllegalArgumentException(strColor + " is not an valid argument for this method");
		}
	}
}
