package asenka.mtgfree.model.mtg.mtgcard;

import java.util.Locale;

import asenka.mtgfree.model.utilities.Localized;

/**
 * An enumeration for the 4 levels of rarity of a card (+ 1 in case of unknown)
 * 
 * @author asenka
 */
public enum MtgRarity implements Localized {

	COMMUN(getLocalizedRarityName("commun")), 
	UNCOMMUN(getLocalizedRarityName("uncommun")), 
	RARE(getLocalizedRarityName("rare")), 
	MYTHIC(getLocalizedRarityName("mythic")),
	UNDEFINED(getLocalizedRarityName("undefined"));

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
	MtgRarity(String localizedName) {

		this.name = localizedName;
		this.locale = Locale.getDefault();
		// TODO par défaut on met une locale Anglaise. Il faudra utiliser par la suite le fichier
		// de configuration de l'utilisateur pour trouver la bonne locale à utiliser.
	}

	/**
	 * @return The localized name of the rarity level
	 */
	public String getName() {

		return this.name;
	}

	@Override
	public Locale getLocale() {

		return this.locale;
	}

	/**
	 * Find a returns the localized value for a rarity level
	 * 
	 * @param strRarity Only : <code>"commun", "uncommun", "rare", "mythic", or "unknown"</code>
	 * @return a localized name for the rarity level
	 * @throws IllegalArgumentException if the strRarity is not one of the 5 accepted values
	 */
	private static String getLocalizedRarityName(String strRarity) {

		// TODO Changer cette méthode pour intéroger un fichier de configuration avec les differentes langues
		// La langue doit venir d'un objet qui contiendra l'ensemble des préférences utilisateur.

		if ("commun".equals(strRarity)) {
			return "Commun";
		} else if ("uncommun".equals(strRarity)) {
			return "Uncommun";
		} else if ("rare".equals(strRarity)) {
			return "Rare";
		} else if ("mythic".equals(strRarity)) {
			return "Mythic rare";
		} else if ("undefined".equals(strRarity)) {
			return "";
		} else {
			throw new IllegalArgumentException(strRarity + " is not an valid argument for this method");
		}
	}
}
