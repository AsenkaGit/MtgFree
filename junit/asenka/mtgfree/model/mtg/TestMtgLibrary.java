package asenka.mtgfree.model.mtg;


import org.junit.Test;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;
import asenka.mtgfree.tests.utilities.TestDataProvider;


public class TestMtgLibrary {
	
	private TestDataProvider data = TestDataProvider.getInstance();

	@Test
	public void test() {
		
		MtgLibrary lib = data.getLibrary();

		lib.shuffle();
		MtgCard c = lib.draw();
		System.out.println(lib);
		System.out.println(c);
		
		lib.addLast(c);
		System.out.println(lib);
	}

}
