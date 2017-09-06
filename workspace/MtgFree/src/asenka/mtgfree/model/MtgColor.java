package asenka.mtgfree.model;

/**
 * This enumeration manage the 5 available color in the Mtg Game : 
 * Red, Blue, White, Green & Black. The enum is loaded from a configuration file
 * to get the localized name of the color.<br />
 * <br />
 * 
 * @author Asenka
 */
public enum MtgColor {
	
	RED(getLocalizedColorName("red")), 
	BLUE(getLocalizedColorName("blue")), 
	WHITE(getLocalizedColorName("white")), 
	GREEN(getLocalizedColorName("green")), 
	BLACK(getLocalizedColorName("black"));

	private static final String PARAM_COLOR_RED = "red";
	private static final String PARAM_COLOR_BLUE = "blue";
	private static final String PARAM_COLOR_BLACK = "black";
	private static final String PARAM_COLOR_WHITE = "white";
	private static final String PARAM_COLOR_GREEN = "green";
	
	/**
	 * A Localized name of the color.
	 */
	private String localizedName;

	/*
	 * Init the enum 
	 */
	MtgColor(String localizedName) {

		this.localizedName = localizedName;
	}

	/**
	 * @return The localized name of the color
	 */
	public String getLocalizedColorName() {

		return this.localizedName;
	}

	/**
	 * @param strColor the parameter name for the color
	 * @return a localized name for the color
	 */
	private static String getLocalizedColorName(String strColor) {

		// TODO Changer cette méthode pour intéroger un fichier de configuration avec les differentes langues
		// La langue doit venir d'un objet qui contiendra l'ensemble des préférences utilisateur.

		if (PARAM_COLOR_RED.equals(strColor)) {
			return "Red";
		} else if (PARAM_COLOR_BLUE.equals(strColor)) {
			return "Blue";
		} else if (PARAM_COLOR_WHITE.equals(strColor)) {
			return "White";
		} else if (PARAM_COLOR_GREEN.equals(strColor)) {
			return "Green";
		} else if (PARAM_COLOR_BLACK.equals(strColor)) {
			return "Black";
		} else {
			throw new IllegalArgumentException(strColor + " is not an valid argument for this method");
		}
	}
}

