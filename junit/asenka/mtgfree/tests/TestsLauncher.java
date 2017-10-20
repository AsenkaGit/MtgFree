package asenka.mtgfree.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import asenka.mtgfree.controllers.TestPlayerController;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;
import asenka.mtgfree.model.game.TestCard;
import asenka.mtgfree.model.game.TestDeck;
import asenka.mtgfree.model.game.TestLibrary;
import asenka.mtgfree.model.game.TestPlayer;
import asenka.mtgfree.model.utilities.json.TestMtgDataUtility;

@RunWith(Suite.class)
@SuiteClasses(value = { TestMtgDataUtility.class, TestCard.class, TestLibrary.class, TestDeck.class, TestPlayer.class, TestPlayerController.class })
public class TestsLauncher {

	@BeforeClass
	public static void setUpBeforeClass() {

		System.out.println("##########################################################");
		System.out.println("#                                                        #");
		System.out.println("#                                                        #");
		System.out.println("#                LAUNCH ALL TESTS                        #");
		System.out.println("#                                                        #");
		System.out.println("#                                                        #");
		System.out.println("##########################################################");
		System.out.println("");
		System.out.println("==========================================================");
		System.out.println("==========              DATA LOADING                ======");
		System.out.println("==========================================================");
		
		
		MtgDataUtility.getInstance();
	}

	@AfterClass
	public static void afterClass() {

		System.out.println("##########################################################");
		System.out.println("#                                                        #");
		System.out.println("#                                                        #");
		System.out.println("#                        END                             #");
		System.out.println("#                                                        #");
		System.out.println("#                                                        #");
		System.out.println("##########################################################");
	}
}
