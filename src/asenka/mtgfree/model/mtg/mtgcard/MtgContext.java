package asenka.mtgfree.model.mtg.mtgcard;

import java.util.Locale;

import asenka.mtgfree.model.utilities.Localized;

/**
 * All the possible locations for the cards :
 * <ul>
 * <li>player's library</li>
 * <li>player's hand</li>
 * <li>battlefield</li>
 * <li>player's graveyard</li>
 * <li>player's exile are</li>
 * </ul> 
 */
public enum MtgContext implements Localized {
	
	LIBRARY(getLocalizedContextName("library")), 
	HAND(getLocalizedContextName("hand")), 
	BATTLEFIELD(getLocalizedContextName("battlefield")),  
	GRAVEYARD(getLocalizedContextName("graveyard")), 
	EXILE(getLocalizedContextName("exile")),
	OUT_OF_GAME(getLocalizedContextName("out_of_game"));
	

	/**
	 * A Localized name for the rarity level
	 */
	private String name;

	/**
	 * The language used for the rarity name
	 */
	private Locale locale;

	/*
	 * Constructor
	 */
	MtgContext(String localizedName) {

		this.name = localizedName;
		this.locale = Locale.ENGLISH;
		// TODO par défaut on met une locale Anglaise. Il faudra utiliser par la suite le fichier
		// de configuration de l'utilisateur pour trouver la bonne locale à utiliser.
	}

	/**
	 * @return The localized name of the context
	 */
	public String getName() {

		return this.name;
	}

	@Override
	public Locale getLocale() {

		return this.locale;
	}

	/**
	 * Find a returns the localized value for the context
	 * 
	 * @param strContext Only : <code>"library", "hand", "battlefield", "graveyard", or "exile"</code>
	 * @return a localized name for the context
	 * @throws IllegalArgumentException if the strContext is not one of the 5 accepted values
	 */
	private static String getLocalizedContextName(String strContext) {

		// TODO Changer cette méthode pour intéroger un fichier de configuration avec les differentes langues
		// La langue doit venir d'un objet qui contiendra l'ensemble des préférences utilisateur.

		if ("library".equals(strContext)) {
			return "Library";
		} else if ("hand".equals(strContext)) {
			return "Hand";
		} else if ("battlefield".equals(strContext)) {
			return "Battlefield";
		} else if ("graveyard".equals(strContext)) {
			return "Graveyard";
		} else if ("exile".equals(strContext)) {
			return "Exile";
		} else if ("out_of_game".equals(strContext)) {
			return "Out of game";
		} else {
			throw new IllegalArgumentException(strContext + " is not an valid argument for this method");
		}
	}
}


