package asenka.mtgfree.model.mtgcard;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class TestMtgCard {
	
	private MtgType instantType;
	private MtgType creatureType;
	private MtgType enchantmentType;
	private MtgType landType;

	
	@Before
	public void setUp() {
		
		this.instantType = new MtgType(1, "Ephémère", Locale.FRENCH);
		this.creatureType = new MtgType(2, "Créature", "Créature : zombie", "", Locale.FRENCH);
		this.enchantmentType = new MtgType(3, "Echantement", Locale.FRENCH);
		this.landType = new MtgType(4, "Terrain", Locale.FRENCH);
	}
	

	@Test
	public void testDefaultSorting() {
		
		MtgCard m1 = new MtgCard(1, "Montagne", "Amonket", MtgColor.RED, this.landType, MtgRarity.COMMUN, Locale.FRENCH);
		MtgCard m2 = new MtgCard(2, "Plaine", "Amonket", MtgColor.WHITE, this.landType, MtgRarity.COMMUN, Locale.FRENCH);
		MtgCard m3 = new MtgCard(3, "Marrais", "Amonket", MtgColor.BLACK, this.landType, MtgRarity.COMMUN, Locale.FRENCH);
		MtgCard m4 = new MtgCard(4, "Île", "Amonket", MtgColor.BLUE, this.landType, MtgRarity.COMMUN, Locale.FRENCH);
		MtgCard m5 = new MtgCard(5, "Forêt", "Amonket", MtgColor.GREEN, this.landType, MtgRarity.COMMUN, Locale.FRENCH);
		MtgCard m6 = new MtgCard(6, "Forêt", "Amonket", MtgColor.GREEN, this.landType, MtgRarity.COMMUN, Locale.FRENCH);
		
		System.out.println(m1);
		
	}

}
