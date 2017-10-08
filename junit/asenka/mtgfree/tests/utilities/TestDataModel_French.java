package asenka.mtgfree.tests.utilities;

import java.util.HashMap;
import java.util.Locale;
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
public class TestDataModel_French {

	/**
	 * Use the Design Pattern Singleton to make sure we have only one instance running in the whole application
	 */
	private static TestDataModel_French singleton = new TestDataModel_French();

	private final MtgGameTable gameTable;

	private final MtgPlayer player1;

	private final MtgPlayer player2;

	private final Map<String, MtgType> typesMap;

	private final Map<String, MtgFormat> formatsMap;

	private final Map<String, MtgAbility> abilitiesMap;

	private final Map<String, MtgCard> cardsMap;

	private final Map<String, MtgDeck> decksMap;

	private final Locale locale;

	/**
	 * 
	 */
	private TestDataModel_French() {
		super();
		this.locale = Locale.FRENCH;
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

		// Land types
		this.typesMap.put("land", new MtgType(100001, "Terrain", "", "Les terrains permettent de générer du mana", locale));
		this.typesMap.put("landForest", new MtgType(100002, "Terrain de base", "Terrain de base : forêt", "", locale));
		this.typesMap.put("landPlain", new MtgType(100003, "Terrain de base", "Terrain de base : plaine", "", locale));
		this.typesMap.put("landSwamp", new MtgType(100004, "Terrain de base", "Terrain de base : marais", "", locale));
		this.typesMap.put("landIsland", new MtgType(100005, "Terrain de base", "Terrain de base : île", "", locale));
		this.typesMap.put("landMountain", new MtgType(100006, "Terrain de base", "Terrain de base : montagne", "", locale));

		// Creatures types
		this.typesMap.put("creatureElfDruid", new MtgType(110001, "Créature", "Créature : elfe et druide", "", locale));
		this.typesMap.put("creatureBird", new MtgType(110002, "Créature", "Créature : oiseau", "", locale));
		this.typesMap.put("creatureHumanWarrior", new MtgType(110003, "Créature", "Créature : humain et guerrier", "", locale));
		this.typesMap.put("creatureAngel", new MtgType(110004, "Créature", "Créature : ange", "", locale));
		this.typesMap.put("creatureChacalWarrior", new MtgType(110005, "Créature", "Créature : chacal et guerrier", "", locale));
		this.typesMap.put("creatureMinotorWarrior", new MtgType(110006, "Créature", "Créature : minotaure et guerrier", "", locale));
		this.typesMap.put("creatureDragon", new MtgType(110007, "Créature", "Créature : dragon", "", locale));
		this.typesMap.put("creatureHumanClerc", new MtgType(110008, "Créature", "Créature : humain et clerc", "", locale));
		this.typesMap.put("creatureHydra", new MtgType(110009, "Créature", "Créature : hydre", "", locale));
		this.typesMap.put("creatureNagaDruid", new MtgType(110010, "Créature", "Créature : naga et druide", "", locale));
		this.typesMap.put("creatureElfArtificerDruid", new MtgType(110011, "Créature", "Créature : elfe et artificier et druide", "", locale));
		this.typesMap.put("creatureLizard", new MtgType(110012, "Créature", "Créature : Lézard", "", locale));
		this.typesMap.put("creatureMonster", new MtgType(110013, "Créature", "Créature : monstruosité", "", locale));
		

		// Instant type
		this.typesMap.put("instant", new MtgType(120000, "Ephémère", "",
				"Les éphémères peuvent être joués à n'importe quel moment pendant votre tour et celui de l'adversaire", locale));
		
		// Sort type
		this.typesMap.put("sort",
				new MtgType(130000, "Rituel", "", "Les rituels sont des sorts que vous pouvez uniquement pendant votre tour", locale));
		
		// Enchantment types
		this.typesMap.put("enchantment", new MtgType(140000, "Enchantement", "", "", locale));
		
		// Planeswakers types
		this.typesMap.put("planeswalkerNissa", new MtgType(150000, "Planeswalker", "Planeswalker : Nissa", "", locale));
		this.typesMap.put("planeswalkerChandra", new MtgType(150001, "Planeswalker", "Planeswalker : Chandra", "", locale));
		this.typesMap.put("planeswalkerGideon", new MtgType(150002, "Planeswalker", "Planeswalker : Gideon", "", locale));
		
		// Artefact type
		this.typesMap.put("artefactVehicule", new MtgType(160000, "Artefact", "Artefact : véhicule", "", locale));
	}

	/**
	 * 
	 */
	private void initAbilities() {

		this.abilitiesMap.put("haste",
				new MtgAbility(200001, "Célérité", "Les créatures avec la célérité ne sont pas affectées par le mal d'invocation", locale));
		this.abilitiesMap.put("trample",
				new MtgAbility(200002, "Piétinement",
						"Le piétinement est une capacité statique qui modifie les règles d'assignation des blessures de combat d'une créature attaquante. La capacité n'a aucun effet quand la créature avec le piétinement bloque ou inflige des blessures non-combat.",
						locale));
		this.abilitiesMap.put("Vigilance", new MtgAbility(200003, "Vigilance", "Attaquer ne fait pas s'engager les créatures avec la vigilance", locale));
		this.abilitiesMap.put("deathtouch", new MtgAbility(200004, "Contact Mortel", "", locale));
		this.abilitiesMap.put("fly", new MtgAbility(200005, "Vol", "Une créature avec le vol ne peut être bloquée que par des créatures avec le vol et/ou la portée. Une créature avec le vol peut bloquer une créature sans le vol", locale));
		this.abilitiesMap.put("reach", new MtgAbility(200006, "Portée", "", locale));
		this.abilitiesMap.put("flash", new MtgAbility(200007, "Flash", "", locale));
		this.abilitiesMap.put("scry", new MtgAbility(200008, "Regard", "", locale));
		

	}

	/**
	 * 
	 */
	private void initFormats() {

		this.formatsMap.put("standard",
				new MtgFormat(300001, "Standard",
						"Le Standard est un format sans cesse renouvelé dans lequel vous ne pouvez utiliser que des cartes des extensions de Magic les plus récentes pour construire vos decks",
						locale));
		this.formatsMap.put("modern",
				new MtgFormat(300002, "Modern",
						"Le format Modern est un format construit qui adhère par conséquent aux règles suivantes : Un minimum de soixante cartes, Pas de taille de deck maximale, du moment que vous pouvez le mélanger par vous-même, Jusqu'à quinze cartes dans votre réserve, si vous en utilisez une",
						locale));
		this.formatsMap.put("commander",
				new MtgFormat(300003, "Commander",
						"Commander revient avec quatre nouveaux decks tribaux de 100cartes qui incluent 56nouvelles cartes Magic légales dans les formats Eternal (Legacy et Vintage).",
						locale));
		this.formatsMap.put("legacy",
				new MtgFormat(300004, "Legacy",
						"Le format Legacy est un format construit qui adhère par conséquent aux règles suivantes : Un minimum de soixante cartes, Pas de taille de deck maximale, du moment que vous pouvez le mélanger par vous-même, Jusqu'à quinze cartes dans votre réserve, si vous en utilisez une",
						locale));
		this.formatsMap.put("vintage",
				new MtgFormat(300005, "Vintage",
						"Les decks Vintage peuvent contenir des cartes de n'importe quelle extension ou édition de base de Magic, ainsi que de toutes les extensions spéciales, les suppléments et les impressions promotionnelles publiés par Wizards of the Coast.",
						locale));
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
	 * 
	 * @return
	 */
	public Locale getLocale() {

		return locale;
	}

	/**
	 * Returns a data model
	 * 
	 * @return the instance of data model
	 */
	public static TestDataModel_French getInstance() {

		return singleton;
	}
}
