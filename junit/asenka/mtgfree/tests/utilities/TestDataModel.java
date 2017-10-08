package asenka.mtgfree.tests.utilities;

import java.util.HashMap;
import java.util.Map;

import asenka.mtgfree.model.mtg.MtgDeck;
import asenka.mtgfree.model.mtg.MtgGameTable;
import asenka.mtgfree.model.mtg.MtgPlayer;
import asenka.mtgfree.model.mtg.mtgcard.MtgAbility;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgFormat;
import asenka.mtgfree.model.mtg.mtgcard.MtgType;

/**
 * Class used to provide a basic data model using all the model class to perform various JUnit test and manual UI tests.
 * 
 * 
 * @author asenka
 *
 */
public class TestDataModel {

	/**
	 * Use the Design Pattern Singleton to make sure we have only one instance running in the whole application
	 */
	private static TestDataModel singleton = new TestDataModel();

	private final MtgGameTable gameTable;

	private final MtgPlayer player1;

	private final MtgPlayer player2;

	private final Map<String, MtgType> typesMap;
	
	private final Map<String, MtgFormat> formatsMap;

	private final Map<String, MtgAbility> abilitiesMap;

	private final Map<String, MtgCard> cardsMap;
	
	private final Map<String, MtgDeck> decksMap;

	/**
	 * 
	 */
	private TestDataModel() {
		super();
		this.typesMap = new HashMap<String, MtgType>();
		this.abilitiesMap = new HashMap<String, MtgAbility>();
		this.cardsMap = new HashMap<String, MtgCard>();
		this.formatsMap = new HashMap<String, MtgFormat>();
		this.decksMap = new HashMap<String, MtgDeck>();

		this.player1 = new MtgPlayer("Asenka");
		this.player2 = new MtgPlayer("GrosBébé");

		initTypes();
		initAbilities();
		initFormats();
		initCards();
		initDecks();
		initializePlayer1();
		initializePlayer2();

		this.gameTable = new MtgGameTable("Test Game Table", player1, player2);
	}

	/**
	 * 
	 */
	private void initTypes() {
	
	}

	/**
	 * 
	 */
	private void initAbilities() {
	
	}

	/**
	 * 
	 */
	private void initFormats() {
	
	}

	/**
	 * 
	 */
	private void initCards() {
	
	}

	/**
	 * 
	 */
	private void initDecks() {

	
	}

	/**
	 * 
	 */
	private void initializePlayer1() {

	}

	/**
	 * 
	 */
	private void initializePlayer2() {

	}

	/**
	 * 
	 * @return
	 */
	public MtgGameTable getGameTable() {

		return gameTable;
	}

	/**
	 * Returns a data model
	 * 
	 * @return the instance of data model
	 */
	public static TestDataModel getInstance() {

		return singleton;
	}
}
