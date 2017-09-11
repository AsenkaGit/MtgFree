package asenka.mtgfree.model.mtgcard;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import asenka.mtgfree.model.mtgcard.comparators.*;
import asenka.mtgfree.model.utilities.ManaManager;

public class TestMtgCard {
	
	private MtgType instantType;
	private MtgType creatureZombieType;
	private MtgType creatureHumain;
	private MtgType enchantmentType;
	private MtgType landType;
	private MtgType artefactVehicule;
	private MtgType sort;
	private MtgType artifact;
	
	private MtgCard m1;
	private MtgCard m2;
	private MtgCard m3;
	private MtgCard m4;
	private MtgCard m5;
	private MtgCard m6;
	private MtgCard m7;
	private MtgCard m8;
	private MtgCard m9;
	private MtgCard m10;
	private MtgCard m11;
	private MtgCard m12;
	private MtgCard m13;
	private MtgCard m14;
	private MtgCard m15;
	private MtgCard m16;
	private MtgCard m17;
	
	private List<MtgCard> listOfCards;
	

	
	@Before
	public void setUp() {
		
		this.instantType = new MtgType(1, "Éphémère", Locale.FRENCH);
		this.creatureZombieType = new MtgType(2, "Créature", "Créature : zombie", "", Locale.FRENCH);
		this.enchantmentType = new MtgType(3, "Echantement", Locale.FRENCH);
		this.landType = new MtgType(4, "Terrain", Locale.FRENCH);
		this.artefactVehicule = new MtgType(5, "Artefact", "Artefact : Véhicule", "", Locale.FRENCH);
		this.creatureHumain = new MtgType(6, "Créature", "Créature : humain", "", Locale.FRENCH);
		this.sort = new MtgType(7, "Rituel", Locale.FRENCH);
		this.artifact = new MtgType(8, "Artefact", Locale.FRENCH);
		
		this.m1 = new MtgCard(1, "Montagne", "Amonket", MtgColor.RED, this.landType, MtgRarity.COMMUN, Locale.FRENCH); 
		this.m2 = new MtgCard(2, "Plaine", "Amonket", MtgColor.WHITE, this.landType, MtgRarity.COMMUN, Locale.FRENCH); 
		this.m3 = new MtgCard(3, "Marrais", "Amonket", MtgColor.BLACK, this.landType, MtgRarity.COMMUN, Locale.FRENCH);
		this.m4 = new MtgCard(4, "Île", "Amonket", MtgColor.BLUE, this.landType, MtgRarity.COMMUN, Locale.FRENCH);     
		this.m5 = new MtgCard(5, "Forêt", "Amonket", MtgColor.GREEN, this.landType, MtgRarity.COMMUN, Locale.FRENCH);  
		this.m6 = new MtgCard(6, "Forêt", "Kaladesh", MtgColor.GREEN, this.landType, MtgRarity.COMMUN, Locale.FRENCH); 
		this.m7 = new MtgCard(7, "Jet", "Kaladesh", "1|r", instantType, MtgRarity.COMMUN, Locale.FRENCH);
		this.m8 = new MtgCard(8, "Croiseur roulevif", "Kaladesh", "5", "4", "5", this.artefactVehicule, MtgRarity.RARE, Locale.FRENCH);
		this.m9 = new MtgCard(9, "Jet", "Amonket", "1|r", instantType, MtgRarity.COMMUN, Locale.FRENCH);
		this.m10 = new MtgCard(10, "Moment opportun", "Amonket", "1|w", instantType, MtgRarity.COMMUN, Locale.FRENCH);
		this.m11 = new MtgCard(11, "Capitaine du diregraf", "Kaladesh", "2", "2", "1|b|u", creatureZombieType, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.m12 = new MtgCard(12, "Vizir de la Loyale", "Kaladesh", "2", "1", "3|w", creatureHumain, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.m13 = new MtgCard(13, "Épreuve de zèle", "Amonket", "2|r", enchantmentType, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.m14 = new MtgCard(14, "Conditions dangereuses", "Kaladesh", "2|b|g", sort, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.m15 = new MtgCard(15, "Cuirassé du consulat", "Révolte étherique", "7", "11", "1", this.artefactVehicule, MtgRarity.UNCOMMUN, Locale.FRENCH);
		this.m16 = new MtgCard(16, "Truc chelou", "Révolte étherique", "2+*", "X", "X|2r", this.creatureZombieType, MtgRarity.MYTHIC, Locale.FRENCH);
		this.m17 = new MtgCard(16, "Lotus Noir", "Alpha", "0", this.artifact, MtgRarity.UNDEFINED, Locale.FRENCH);
		
		this.listOfCards = new ArrayList<MtgCard>(15);
		
		this.listOfCards.add(m1);
		this.listOfCards.add(m2);
		this.listOfCards.add(m3);
		this.listOfCards.add(m4);
		this.listOfCards.add(m5);
		this.listOfCards.add(m6);
		this.listOfCards.add(m7);
		this.listOfCards.add(m8);
		this.listOfCards.add(m9);
		this.listOfCards.add(m10);
		this.listOfCards.add(m11);
		this.listOfCards.add(m12);
		this.listOfCards.add(m13);
		this.listOfCards.add(m14);
		this.listOfCards.add(m15);
		this.listOfCards.add(m16);
		this.listOfCards.add(m17);
	}
	

	@Test
	public void testDefaultSorting() {
		
		
		
		
		Collections.sort(listOfCards, new CardTypeComparator(new CardCostComparator()));
		
		System.out.println(resultToString3(listOfCards));
		
	}
	
	
	
	// TODO Supprimer les méthodes ci-dessous
	
	private String resultToString(List<MtgCard> list) {
		
		StringBuffer buffer = new StringBuffer();
		
		for(MtgCard c : list) {
			
			buffer.append(c.getId() + "\t| "
					+ c.getCost() + "\t==> " + ManaManager.getInstance().getConvertedCostMana(c.getCost()) + " | "
					+ c.getColors() + "\t| "
					+ c.getCollectionName() + " | " 	
					+ c.getName() + "\n");
		}

		return buffer.toString();
	}
	
	private String resultToString2(List<MtgCard> list) {
		
		StringBuffer buffer = new StringBuffer();
		
		for(MtgCard c : list) {
			
			buffer.append(c.getId() + "\t| "
					+ c.getCost() + "\t==> " + ManaManager.getInstance().getConvertedCostMana(c.getCost()) + " | "
					+ c.getColors() + "\t| "
					+ c.getName() + " | " 	
					+ c.getCollectionName() + "\n");
		}

		return buffer.toString();
	}
	
	private String resultToString3(List<MtgCard> list) {
		
		StringBuffer buffer = new StringBuffer();
		
		for(MtgCard c : list) {
			
			buffer.append(c.getId() + "\t| "
					+ c.getCost() + "\t==> " + ManaManager.getInstance().getConvertedCostMana(c.getCost()) + "\t|"
					+ c.getPower() + "/"
					+ c.getToughness() + "\t| "
					+ c.getType() + "\t| "
					+ c.getColors() + "\t| "
					+ c.getName() + " | " 	
					+ c.getCollectionName() + "\n");
		}

		return buffer.toString();
	}

}
