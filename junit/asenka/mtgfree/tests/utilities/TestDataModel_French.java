package asenka.mtgfree.tests.utilities;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import asenka.mtgfree.model.mtg.MtgCollection;
import asenka.mtgfree.model.mtg.MtgDeck;
import asenka.mtgfree.model.mtg.MtgDeckList;
import asenka.mtgfree.model.mtg.MtgGameTable;
import asenka.mtgfree.model.mtg.MtgPlayer;
import asenka.mtgfree.model.mtg.exceptions.MtgDeckException;
import asenka.mtgfree.model.mtg.mtgcard.MtgAbility;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgCardBuilder;
import asenka.mtgfree.model.mtg.mtgcard.MtgColor;
import asenka.mtgfree.model.mtg.mtgcard.MtgFormat;
import asenka.mtgfree.model.mtg.mtgcard.MtgRarity;
import asenka.mtgfree.model.mtg.mtgcard.MtgType;

/**
 * Class used to provide a basic data model using all the model class to perform various JUnit test and manual UI tests.
 * 
 * 
 * @author asenka
 * @param <E>
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

	private final Map<String, MtgCollection> collectionsMap;

	private final Locale locale;

	/**
	 * @throws MtgDeckException
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
		this.collectionsMap = new HashMap<String, MtgCollection>();
		this.player1 = new MtgPlayer("Asenka");
		this.player2 = new MtgPlayer("GrosBébé");

		initTypes();
		initAbilities();
		initFormats();
		initCollections();
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
		this.typesMap.put("creatureElfArtificerDruid",
				new MtgType(110011, "Créature", "Créature : elfe et artificier et druide", "", locale));
		this.typesMap.put("creatureLizard", new MtgType(110012, "Créature", "Créature : Lézard", "", locale));
		this.typesMap.put("creatureMonster", new MtgType(110013, "Créature", "Créature : monstruosité", "", locale));
		this.typesMap.put("creatureHumanArtificer", new MtgType(110014, "Créature", "Créature : humain et artificier", "", locale));
		this.typesMap.put("creatureGremlin", new MtgType(110015, "Créature", "Créature : gremlin", "", locale));

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
		this.abilitiesMap.put("vigilance",
				new MtgAbility(200003, "Vigilance", "Attaquer ne fait pas s'engager les créatures avec la vigilance", locale));
		this.abilitiesMap.put("deathtouch", new MtgAbility(200004, "Contact Mortel", "", locale));
		this.abilitiesMap.put("fly",
				new MtgAbility(200005, "Vol",
						"Une créature avec le vol ne peut être bloquée que par des créatures avec le vol et/ou la portée. Une créature avec le vol peut bloquer une créature sans le vol",
						locale));
		this.abilitiesMap.put("reach", new MtgAbility(200006, "Portée", "", locale));
		this.abilitiesMap.put("flash", new MtgAbility(200007, "Flash", "", locale));
		this.abilitiesMap.put("scry", new MtgAbility(200008, "Regard", "", locale));
		this.abilitiesMap.put("threat", new MtgAbility(200009, "Menace", "", locale));
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
	private void initCollections() {

		this.collectionsMap.put("shadowOverInnistrad", new MtgCollection(400000, "Ténébres sur Innistrad", this.locale));
		this.collectionsMap.put("eldritchMoon", new MtgCollection(400001, "La Lune Hermétique", this.locale));
		this.collectionsMap.put("amonket", new MtgCollection(400002, "Amonket", this.locale));
		this.collectionsMap.put("hourOfDevastation", new MtgCollection(400003, "L'Âge de la Destruction", this.locale));
		this.collectionsMap.put("kaladesh", new MtgCollection(400004, "Kaladesh", this.locale));
		this.collectionsMap.put("aetherRevolt", new MtgCollection(400005, "La Révolte éthérique", this.locale));
	}

	/**
	 * 
	 */
	private void initCards() {

		final MtgCardBuilder cardBuilder = new MtgCardBuilder(500000, typesMap.get("instant"), collectionsMap.get("eldritchMoon"),
				new MtgFormat[] { formatsMap.get("standard"), formatsMap.get("modern"), formatsMap.get("commander"),
						formatsMap.get("legacy") },
				new MtgAbility[] {}, new MtgColor[] { MtgColor.WHITE }, MtgRarity.UNCOMMUN, this.locale);

		this.cardsMap.put("Alliance bénie",
				cardBuilder.buildCard(
						"Alliance bénie", "1|w", "Intensification {2}\n" + "Choisissez l'un plus -\n"
								+ "-Le joueur ciblé gagne 4 points de vie\n" + "-L'adversaire ciblé sacrifie une créature attaquante.",
						"", "", "", -1));

		cardBuilder.setCollection(collectionsMap.get("shadowOverInnistrad"));

		this.cardsMap.put("Pertinacité",
				cardBuilder.buildCard("Pertinacité", "3|w",
						"Les créatures que vous controlez gagnent +1/+1 et acquièrent le lien de vie jusqu'à "
								+ "la fin du tour. Dégagez ces créatures",
						"Thalia a créé l'Ordre de Saint Traft pour rassembler les cathares disposés à lutter "
								+ "contre la corruption du conseil des lunarques.",
						"", "", -1));

		cardBuilder.setCollection(collectionsMap.get("hourOfDevastation"));
		cardBuilder.setRarity(MtgRarity.COMMUN);

		this.cardsMap.put("Acte héroïque",
				cardBuilder.buildCard("Acte héroïque", "1|w", "Dégagez la créature ciblée. Elle gagne +2/+2 jusqu'à la fin du tour et peut "
						+ "bloquer une créature supplémentaire ce tour-ci.", "", "", "", -1));

		cardBuilder.setColors(MtgColor.BLUE);

		this.cardsMap.put("Désinvocation", cardBuilder.buildCard("Désinvocation", "u",
				"Renvoyez la créature ciblée dans la main de son propriétaire", "", "", "", -1));

		cardBuilder.setColors(MtgColor.WHITE);
		cardBuilder.setType(this.typesMap.get("enchantment"));
		cardBuilder.setCollection(collectionsMap.get("shadowOverInnistrad"));

		this.cardsMap.put("Observation Constante", cardBuilder.buildCard("Observation Constante", "1|w|w",
				"Les créatures non-jeton que vous contôlez gagnent +1/+1 et ont la vigilance.", "", "", "", -1));

		cardBuilder.setColors(MtgColor.GREEN, MtgColor.BLUE);
		cardBuilder.setCollection(collectionsMap.get("amonket"));

		this.cardsMap.put("Don du Luxa", cardBuilder.buildCard("Don du Luxa", "2|g|u",
				"Au début de votre première phase principale, retirez tous les marqueurs \"inondation\" "
						+ "du Don du Luxa. Si aucun marqueur n'a été retiré de cette manière, mettez un marqueur "
						+ "\"inondation\" sur le Don du Luxa et piochez une carte. Sinon, ajoutez {cgu} à votre réserve.",
				"", "", "", -1));

		cardBuilder.setColors(MtgColor.GREEN);
		cardBuilder.setType(this.typesMap.get("sort"));

		this.cardsMap.put("Encouragement de Nissa",
				cardBuilder.buildCard("Encouragement de Nissa", "4|g",
						"Cherchez dans votre bibliothèque et votre cimetière une carte appelée Forêt, "
								+ "une carte appelée Béhémoth trameronces, et une carte appelée Nissa, mage de génèse. "
								+ "Révélez ces cartes, mettez-les dans votre main, puis mélangez votre bibliothèque.",
						"", "", "", -1));

		cardBuilder.setColors(MtgColor.RED);
		cardBuilder.setCollection(collectionsMap.get("amonket"));
		cardBuilder.setType(typesMap.get("creatureHumanWarrior"));
		cardBuilder.setRarity(MtgRarity.MYTHIC);

		this.cardsMap.put("Célébrant de Combat",
				cardBuilder.buildCard("Célébrant de Combat", "2|r",
						"Si le Célébrant de combat n'a pas été surmené ce tour-ci, vous pouvez le surmener au moment "
								+ "où il attaque. Quand vous faites ainsi, dégagez toutes les autres créatures que vous contrôlez "
								+ "et cette phase est suivie d'une phase de combat supplémentaire.",
						"", "4", "1", -1));

		cardBuilder.setType(typesMap.get("creatureDragon"));
		cardBuilder.setRarity(MtgRarity.MYTHIC);

		this.cardsMap.put("Annonciateur de gloire",
				cardBuilder.buildCard("Annonciateur de gloire", "3|r|r",
						"Vous pouvez surmener l'Annonciateur de gloire au moment où il attaque. "
								+ "Quand vous faites ainsi, il inflige 4 blessures à une créature non-Dragon "
								+ "ciblée qu'un adversaire contrôle.",
						"Ce que les adeptes durant la dernière épreuve est à la seule discrétion d'Hazoret", "4", "4", -1));

		cardBuilder.setType(typesMap.get("creatureChacalWarrior"));
		cardBuilder.setRarity(MtgRarity.UNCOMMUN);

		this.cardsMap.put("Jumeaux au coeur pur",
				cardBuilder.buildCard("Jumeaux au coeur pur", "4|r",
						"Vous pouvez surmener les Jumeaux au cœur pur au moment où ils attaquent.\n"
								+ "À chaque fois que vous surmenez une créature, les créatures que vous contrôlez gagnent +1/+0 jusqu'à la fin du tour.",
						"Côte à côte jusqu'à ce que leurs lames soient face à face.", "4", "4", -1));

		this.cardsMap.put("Ferrailleur khenra",
				cardBuilder.buildCard("Ferrailleur khenra", "2|r",
						"Menace\n" + "Vous pouvez surmener le Ferrailleur khenra au moment où il attaque. "
								+ "Quand vous faites ainsi, il gagne +2/+0 jusqu'à la fin du tour.",
						"Demain ne viendra pas, donc ne vous retenez pas.", "2", "3", -1));
		this.cardsMap.get("Ferrailleur khenra").addAbilities(this.abilitiesMap.get("threat"));

		cardBuilder.setType(typesMap.get("creatureMinotorWarrior"));

		this.cardsMap
				.put("Fracasseur de la moisson Ahn",
						cardBuilder
								.buildCard("Fracasseur de la moisson Ahn", "2|r",
										"Vous pouvez surmener le Fracasseur de la moisson Ahn au moment où il attaque. "
												+ "Quand vous faites ainsi, la créature ciblée ne peut pas bloquer ce tour-ci.",
										"", "3", "2", -1));

		cardBuilder.setType(typesMap.get("creatureHumanWarrior"));
		cardBuilder.setCollection(collectionsMap.get("hourOfDevastation"));

		this.cardsMap.put("Survivants déterminés",
				cardBuilder.buildCard("Survivants déterminés", "1|r|w",
						"Vous pouvez surmener les Survivants déterminés au moment où ils attaquent.\n"
								+ "À chaque fois que vous surmenez une créature, les Survivants déterminés "
								+ "infligent 1 blessure à chaque adversaire et vous gagnez 1 point de vie.",
						"", "3", "3", -1));

		cardBuilder.setType(typesMap.get("creatureHumanClerc"));
		cardBuilder.setColors(MtgColor.WHITE);

		this.cardsMap.put("Vizir de la Loyale",
				cardBuilder.buildCard("Vizir de la Loyale", "3|w",
						"Vous pouvez surmener le Vizir de la Loyale au moment où il attaque.\n"
								+ "À chaque fois que vous surmenez une créature, engagez une créature ciblée qu'un adversaire contrôle.",
						"En infériorité numérique, mais pas dépassé.", "3", "2", -1));

		cardBuilder.setType(typesMap.get("creatureHumanWarrior"));
		cardBuilder.setCollection(collectionsMap.get("amonket"));

		this.cardsMap.put("Adepte guidé par la gloire",
				cardBuilder.buildCard("Adepte guidé par la gloire", "1|w",
						"Vous pouvez surmener l'Adepte guidé par la gloire au moment où il attaque. "
								+ "Quand vous faites ainsi, il gagne +1/+3 et acquiert le lien de vie jusqu'à la fin du tour.",
						"", "3", "1", -1));

		cardBuilder.setType(typesMap.get("creatureBird"));
		cardBuilder.setCollection(collectionsMap.get("aetherRevolt"));
		cardBuilder.setAbilities(this.abilitiesMap.get("fly"));

		this.cardsMap
				.put("Aigle aux plumes d'aube",
						cardBuilder.buildCard("Aigle aux plumes d'aube", "4|w",
								"Vol\n" + "Quand l'Aigle aux plumes d'aube arrive sur le champ de bataille, les créatures que "
										+ "vous contrôlez gagnent +1/+1 et acquièrent la vigilance jusqu'à la fin du tour.",
								"", "3", "3", -1));

		cardBuilder.setType(typesMap.get("creatureAngel"));
		cardBuilder.setCollection(collectionsMap.get("hourOfDevastation"));
		cardBuilder.setAbilities(this.abilitiesMap.get("vigilance"), this.abilitiesMap.get("fly"));
		cardBuilder.setRarity(MtgRarity.RARE);

		this.cardsMap.put("Angle de la réprobabtion",
				cardBuilder.buildCard("Angle de la réprobabtion", "2|w|w",
						"Vol, vigilance\n" + "{2|w},{T} : Exilez une autre créature ciblée. Renvoyez cette carte sur le champ de "
								+ "bataille sous le contrôle de son propriétaire au début de la prochaine étape de fin."
								+ "{2|w},{T}, surmenez l'Ange de la réprobation : Exilez une autre créature ciblée jusqu'à "
								+ "ce que l'Ange de la réprobation quitte le champ de bataille.",
						"", "3", "3", -1));

		cardBuilder.setType(typesMap.get("landMountain"));
		cardBuilder.setColors(MtgColor.RED);
		this.cardsMap.put("Montagne", cardBuilder.buildCard("Montagne", "", "", "", "", "", -1));

		cardBuilder.setType(typesMap.get("landForest"));
		cardBuilder.setColors(MtgColor.GREEN);
		this.cardsMap.put("Forêt", cardBuilder.buildCard("Forêt", "", "", "", "", "", -1));

		cardBuilder.setType(typesMap.get("landPlain"));
		cardBuilder.setColors(MtgColor.WHITE);
		this.cardsMap.put("Plaine", cardBuilder.buildCard("Plaine", "", "", "", "", "", -1));

	}

	/**
	 * 
	 */
	private void initDecks() {

		MtgDeck deckPlayer1 = new MtgDeck(600001, "Surmenage", "Deck rouge et blanc basée sur le surmenage");

		addCardsToDeck(deckPlayer1, this.cardsMap.get("Montagne"), 13);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Plaine"), 13);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Ange de la réprobation"), 4);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Aigle aux plumes d'aube"), 2);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Vizir de la Loyale"), 2);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Ferrailleur khenra"), 2);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Fracasseur de la moisson Ahn"), 4);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Jumeaux au coeur pur"), 2);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Annonciateur de gloire"), 2);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Observation Constante"), 4);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Adepte guidé par la gloire"), 4);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Célébrant de combat"), 4);
		addCardsToDeck(deckPlayer1, this.cardsMap.get("Survivants déterminés"), 4);

		this.decksMap.put("Surmenage", deckPlayer1);
	}

	/**
	 * 
	 */
	private void initializePlayer1() {

		try {
			player1.addAvailableDeck(this.decksMap.get("Surmenage"));
			player1.setCurrentDeck(this.decksMap.get("Surmenage"));
		} catch (MtgDeckException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 
	 */
	private void initializePlayer2() {

	}

	/**
	 * Add a card to a deck several times
	 * 
	 * @param deck
	 * @param card
	 * @param amount
	 */
	private void addCardsToDeck(MtgDeck deck, MtgCard card, int amount) {

		for (int i = 0; i < amount; i++) {
			try {
				deck.addCards(MtgDeckList.MAIN, card);
			} catch (MtgDeckException e) {
				System.out.println(e);
			}
		}
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
	
	public static void main(String[] args) {

		TestDataModel_French test = TestDataModel_French.getInstance();
		System.out.println(test.getGameTable());
	}
}
