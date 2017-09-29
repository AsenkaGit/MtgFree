package asenka.mtgfree.utilities;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import asenka.mtgfree.tests.utilities.TestDataProvider;


public class TestFileManager {
	
	private TestDataProvider data = TestDataProvider.getInstance();

	@Test
	public void testGetInputStream() {

		FileManager manager = FileManager.getInstance();
		
		InputStream test = manager.getCardImageInputStream(data.blackLotus);
		
		assertEquals(test.getClass(), FileInputStream.class);
	}

}
