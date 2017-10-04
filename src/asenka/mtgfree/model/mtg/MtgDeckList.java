package asenka.mtgfree.model.mtg;

import java.util.Locale;

import asenka.mtgfree.model.utilities.Localized;

/**
 * Enumeration used only with MtgDeck. It indicates witch list in the deck must be updated :
 * <ul>
 * <li>The main list (the cards going to the player's library during a game)</li>
 * <li>The sideboard</li>
 * </ul>
 * 
 * @author asenka
 * @see MtgDeck
 */
public enum MtgDeckList implements Localized {

	MAIN(getLocalizedDeckListName("main")), 
	SIDEBOARD(getLocalizedDeckListName("sideboard"));

	/**
	 * A Localized name of the color.
	 */
	private String name;

	/**
	 * The language used for the color name
	 */
	private Locale locale;

	/*
	 * Private Constructor
	 */
	MtgDeckList(String localizedName) {

		this.name = localizedName;
		this.locale = Locale.getDefault();
		// TODO On utilise la locale par défaut. Il faudra utiliser par la suite le fichier
		// de configuration de l'utilisateur pour trouver la bonne locale à utiliser.
	}

	/**
	 * @return The localized name of the deck list
	 */
	public String getName() {

		return this.name;
	}

	@Override
	public Locale getLocale() {

		return this.locale;
	}

	/**
	 * Find a returns the localized value for a deck list name
	 * 
	 * @param strDeckList Only : <code>"main" or "sideboard"</code>
	 * @return a localized name for the deck list
	 * @throws IllegalArgumentException if the strDeckList is not one of the 2 accepted values
	 */
	private static String getLocalizedDeckListName(String strDeckList) {

		// TODO Changer cette méthode pour intéroger un fichier de configuration avec les differentes langues
		// La langue doit venir d'un objet qui contiendra l'ensemble des préférences utilisateur.

		if ("main".equals(strDeckList)) {
			return "Main cards";
		} else if ("sideBoard".equals(strDeckList)) {
			return "Side board";
		} else {
			throw new IllegalArgumentException(strDeckList + " is not an valid argument for this method");
		}
	}
}
