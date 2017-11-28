package asenka.mtgfree.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * Super class for JUnit test that display the test name in the output
 * @author asenka
 *
 */
public abstract class MtgFreeTest {

	/**
	 * Helps to get the name of the test
	 */
	@Rule 
	public TestName testName = new TestName();
	
	/**
	 * Method called before each test
	 */
	@Before 
	public void beforeTest() {
		System.out.println("Perform " + testName.getMethodName() + "   ");
		System.out.println("-------------------------------------------");
	}
	
}
