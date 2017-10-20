package asenka.mtgfree.tests;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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

		Logger.getLogger(TestsLauncher.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestsLauncher.class).debug(" ######################## BEGIN ######################## ");
		MtgDataUtility.getInstance();
	}

	@AfterClass
	public static void afterClass() {

		Logger.getLogger(TestsLauncher.class).debug(" ######################## END ########################");
	}
}
