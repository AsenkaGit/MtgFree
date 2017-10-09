package asenka.mtgfree.tests.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import asenka.mtgfree.model.mtg.MtgCollection;
import asenka.mtgfree.model.mtg.MtgDeck;
import asenka.mtgfree.model.mtg.MtgLibrary;
import asenka.mtgfree.model.mtg.mtgcard.MtgAbility;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.model.mtg.mtgcard.MtgColor;
import asenka.mtgfree.model.mtg.mtgcard.MtgRarity;
import asenka.mtgfree.model.mtg.mtgcard.MtgType;

public class TestDataProvider {
	
	private static TestDataProvider singleton = new TestDataProvider();
	
	public final MtgType instantType;
	public final MtgType creatureZombieType;
	public final MtgType creatureHumainType;
	public final MtgType enchantmentType;
	public final MtgType landType;
	public final MtgType artefactVehicule;
	public final MtgType sortType;
	public final MtgType artifactType;
	
	public final MtgAbility flyAbility;
	public final MtgAbility pietinementAbility;
	public final MtgAbility hexproofAbility;
	public final MtgAbility firstStrikeAbility;
	public final MtgAbility doubleStrikeAbility;
	public final MtgAbility deathtouchAbility;
	public final MtgAbility hasteAbility;
	
	public final MtgCard mtgCard1;
	public final MtgCard mtgCard2;
	public final MtgCard mtgCard3;
	public final MtgCard mtgCard4;
	public final MtgCard mtgCard5;
	public final MtgCard mtgCard6;
	public final MtgCard mtgCard7;
	public final MtgCard mtgCard8;
	public final MtgCard mtgCard9;
	public final MtgCard mtgCard10;
	public final MtgCard mtgCard11;
	public final MtgCard mtgCard12;
	public final MtgCard mtgCard13;
	public final MtgCard mtgCard14;
	public final MtgCard mtgCard15;
	public final MtgCard mtgCard16;
	public final MtgCard mtgCard17;
	public final MtgCard mtgCard18;
	
	public final MtgCard blackLotus;

	public final MtgCollection amonketCollection;
	public final MtgCollection kaladeshCollection;
	
	private final List<MtgCard> listOfCards;
	private final List<MtgType> listOfTypes;
	private final List<MtgAbility> listOfAbilities;
	
	
	/**
	 * 
	 */
	public TestDataProvider() {
		
		this.instantType = new MtgType(1, "Éphémère", Locale.FRENCH);
		this.creatureZombieType = new MtgType(2, "Créature", "Créature : zombie", "", Locale.FRENCH);
		this.enchantmentType = new MtgType(3, "Echantement", Locale.FRENCH);
		this.landType = new MtgType(4, "Terrain", Locale.FRENCH);
		this.artefactVehicule = new MtgType(5, "Artefact", "Artefact : Véhicule", "", Locale.FRENCH);
		this.creatureHumainType = new MtgType(6, "Créature", "Créature : humain", "", Locale.FRENCH);
		this.sortType = new MtgType(7, "Rituel", Locale.FRENCH);
		this.artifactType = new MtgType(8, "Artefact", Locale.FRENCH);
		
		this.listOfTypes = new ArrayList<MtgType>();
		this.listOfTypes.add(instantType);
		this.listOfTypes.add(creatureZombieType);
		this.listOfTypes.add(enchantmentType);
		this.listOfTypes.add(landType);
		this.listOfTypes.add(artefactVehicule);
		this.listOfTypes.add(creatureHumainType);
		this.listOfTypes.add(sortType);
		this.listOfTypes.add(artifactType);
		
		this.flyAbility = new MtgAbility(1, "Vol", Locale.FRENCH);                        
		this.pietinementAbility = new MtgAbility(2, "Piétinement", Locale.FRENCH);        
		this.hexproofAbility = new MtgAbility(3, "Défence talismanique", Locale.FRENCH);  
		this.firstStrikeAbility = new MtgAbility(4, "Initiative", Locale.FRENCH);         
		this.doubleStrikeAbility = new MtgAbility(5, "Double initiative", Locale.FRENCH); 
		this.deathtouchAbility = new MtgAbility(6, "Contact Mortel", Locale.FRENCH); 
		this.hasteAbility = new MtgAbility(7, "Célérité", Locale.FRENCH);
		
		this.listOfAbilities = new ArrayList<MtgAbility>();
		this.listOfAbilities.add(this.flyAbility);
		this.listOfAbilities.add(this.pietinementAbility);
		this.listOfAbilities.add(this.hexproofAbility);
		this.listOfAbilities.add(this.firstStrikeAbility);
		this.listOfAbilities.add(this.doubleStrikeAbility);
		this.listOfAbilities.add(this.deathtouchAbility);
		
		this.mtgCard1 = new MtgCard(1, "Montagne", "Amonket", MtgColor.RED, this.landType, MtgRarity.COMMUN, Locale.FRENCH); 
		this.mtgCard2 = new MtgCard(2, "Plaine", "Amonket", MtgColor.WHITE, this.landType, MtgRarity.COMMUN, Locale.FRENCH); 
		this.mtgCard3 = new MtgCard(3, "Marrais", "Amonket", MtgColor.BLACK, this.landType, MtgRarity.COMMUN, Locale.FRENCH);
		this.mtgCard4 = new MtgCard(4, "Île", "Amonket", MtgColor.BLUE, this.landType, MtgRarity.COMMUN, Locale.FRENCH);     
		this.mtgCard5 = new MtgCard(5, "Forêt", "Amonket", MtgColor.GREEN, this.landType, MtgRarity.COMMUN, Locale.FRENCH);  
		this.mtgCard6 = new MtgCard(6, "Forêt", "Kaladesh", MtgColor.GREEN, this.landType, MtgRarity.COMMUN, Locale.FRENCH); 
		this.mtgCard7 = new MtgCard(7, "Jet", "Kaladesh", "1|r", instantType, MtgRarity.COMMUN, Locale.FRENCH);
		this.mtgCard8 = new MtgCard(8, "Croiseur roulevif", "Kaladesh", "5", "4", "5", this.artefactVehicule, MtgRarity.RARE, Locale.FRENCH);
		this.mtgCard9 = new MtgCard(9, "Jet", "Amonket", "1|r", instantType, MtgRarity.COMMUN, Locale.FRENCH);
		this.mtgCard10 = new MtgCard(10, "Moment opportun", "Amonket", "1|w", instantType, MtgRarity.COMMUN, Locale.FRENCH);
		this.mtgCard11 = new MtgCard(11, "Capitaine du diregraf", "Kaladesh", "2", "2", "1|b|u", creatureZombieType, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.mtgCard12 = new MtgCard(12, "Vizir de la Loyale", "Kaladesh", "2", "1", "3|w", creatureHumainType, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.mtgCard13 = new MtgCard(13, "Épreuve de zèle", "Amonket", "2|r", enchantmentType, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.mtgCard14 = new MtgCard(14, "Conditions dangereuses", "Kaladesh", "2|b|g", sortType, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.mtgCard15 = new MtgCard(15, "Cuirassé du consulat", "Révolte étherique", "7", "11", "1", this.artefactVehicule, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.mtgCard16 = new MtgCard(16, "Truc chelou", "Révolte étherique", "2+*", "X", "X|2r", this.creatureZombieType, MtgRarity.MYTHIC, Locale.FRENCH);
		this.mtgCard17 = new MtgCard(17, "Lotus Noir", "Alpha", "0", this.artifactType, MtgRarity.UNDEFINED, Locale.FRENCH);
		this.mtgCard18 = new MtgCard(18, "Lotus Bleu", "Alpha", "0", this.artifactType, MtgRarity.UNDEFINED, Locale.FRENCH);
		this.blackLotus = new MtgCard(20, "Black Lotus", "Alpha", "0", this.artifactType, MtgRarity.UNDEFINED, Locale.ENGLISH);
		
		this.mtgCard8.addAbilities(hasteAbility, pietinementAbility);
		this.mtgCard11.addAbilities(deathtouchAbility);
		this.mtgCard16.addAbilities(flyAbility, hexproofAbility, doubleStrikeAbility);
		
		this.listOfCards = new ArrayList<MtgCard>();
		
		this.listOfCards.add(mtgCard1);
		this.listOfCards.add(mtgCard2);
		this.listOfCards.add(mtgCard3);
		this.listOfCards.add(mtgCard4);
		this.listOfCards.add(mtgCard5);
		this.listOfCards.add(mtgCard6);
		this.listOfCards.add(mtgCard7);
		this.listOfCards.add(mtgCard8);
		this.listOfCards.add(mtgCard9);
		this.listOfCards.add(mtgCard10);
		this.listOfCards.add(mtgCard11);
		this.listOfCards.add(mtgCard12);
		this.listOfCards.add(mtgCard13);
		this.listOfCards.add(mtgCard14);
		this.listOfCards.add(mtgCard15);
		this.listOfCards.add(mtgCard16);
		this.listOfCards.add(mtgCard17);
		this.listOfCards.add(mtgCard18);
		
		this.amonketCollection = new MtgCollection("AKH", "Amonket", Locale.FRENCH);
		this.kaladeshCollection = new MtgCollection("KLD", "Kaladesh", Locale.FRENCH);

		
		this.amonketCollection.addCards(this.mtgCard1, this.mtgCard2, this.mtgCard3, this.mtgCard4, this.mtgCard5, 
				this.mtgCard9, this.mtgCard10, this.mtgCard13, this.mtgCard17, this.mtgCard18);
		
		this.kaladeshCollection.addCards(this.mtgCard6, this.mtgCard7, this.mtgCard8, this.mtgCard14, this.mtgCard15, 
				this.mtgCard16);
	}
	
	
	public MtgLibrary getLibrary() {
		
		List<MtgCard> cardsInLibrary = new ArrayList<MtgCard>(MtgDeck.MINIMUM_DECK_SIZE);
		addCards(10, this.mtgCard1, cardsInLibrary);
		addCards(10, this.mtgCard2, cardsInLibrary);
		addCards(10, this.mtgCard3, cardsInLibrary);
		addCards(4, this.mtgCard7, cardsInLibrary);
		addCards(4, this.mtgCard8, cardsInLibrary);
		addCards(4, this.mtgCard9, cardsInLibrary);
		addCards(4, this.mtgCard10, cardsInLibrary);
		addCards(4, this.mtgCard11, cardsInLibrary);
		addCards(2, this.mtgCard12, cardsInLibrary);
		addCards(2, this.mtgCard13, cardsInLibrary);
		addCards(2, this.mtgCard14, cardsInLibrary);
		addCards(4, this.mtgCard17, cardsInLibrary);
		
		return new MtgLibrary(cardsInLibrary);
	}
	
	/**
	 * 
	 * @return
	 */
	public static TestDataProvider getInstance() {
		return singleton;
	}

	/**
	 * @return an unmodifiable list of cards
	 */
	public List<MtgCard> getListOfCards() {
		return Collections.unmodifiableList(this.listOfCards);
	}
	
	/**
	 * @return an unmodifiable list of types
	 */
	public List<MtgType> getListOfTypes() {
		return Collections.unmodifiableList(this.listOfTypes);
	}
	
	/**
	 * @return an unmodifiable list of abilities
	 */
	public List<MtgAbility> getListOfAbilities() {
		return Collections.unmodifiableList(this.listOfAbilities);
	}
	
	/**
	 * @param x the number of time to add the card
	 * @param card the card to add x time
	 * @param list in this list
	 */
	private static void addCards(int x, MtgCard card, List<MtgCard> list) {
		
		do {
			list.add(card);
		} while(--x > 0);
	}
}
