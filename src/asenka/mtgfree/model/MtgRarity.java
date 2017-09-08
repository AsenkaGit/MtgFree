package asenka.mtgfree.model;

/**
 * An enumeration for the 4 levels of rarity of a card.
 * 
 * @author asenka
 */
public enum MtgRarity {

	COMMUN(getLocalizedRarityName("commun")), 
	UNCOMMUN(getLocalizedRarityName("uncommun")), 
	RARE(getLocalizedRarityName("rare")), 
	MYTHIC(getLocalizedRarityName("mythic")),
	UNKNOWN(getLocalizedRarityName("unknown"));
	
	/**
	 * A Localized name for the rarity level
	 */
	private String localizedName;

	/*
	 * Init the enum 
	 */
	MtgRarity(String localizedName) {

		this.localizedName = localizedName;
	}

	/**
	 * @return The localized name of the rarity level
	 */
	public String getLocalizedRarityName() {

		return this.localizedName;
	}

	/**
	 * 
	 * @param strRarity
	 * @return
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
		} else if ("unknown".equals(strRarity)) {
			return "Unknown";
		} else {
			throw new IllegalArgumentException(strRarity + " is not an valid argument for this method");
		}
	}
	
}
