package asenka.mtgfree.tests;

import java.text.NumberFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import asenka.mtgfree.controllers.TestGameController;
import asenka.mtgfree.model.utilities.MtgDataUtility;
import asenka.mtgfree.model.utilities.TestCardsManager;
import asenka.mtgfree.model.utilities.TestMtgDataUtility;

@RunWith(Suite.class)
@SuiteClasses(value = { TestMtgDataUtility.class, TestCardsManager.class, TestGameController.class })
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

		final NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(true);

		System.out.println("Used memory: " + format.format(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		MtgDataUtility.getInstance();
		System.out.println("Used memory: " + format.format(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
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
