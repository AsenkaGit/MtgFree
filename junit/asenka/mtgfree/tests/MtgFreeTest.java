package asenka.mtgfree.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public abstract class MtgFreeTest {

	@Rule 
	public TestName testName = new TestName();
	
	@Before 
	public void setUp() {
		System.out.println("==========================================");
		System.out.println("Perform " + testName.getMethodName() + "    ");
		System.out.println("==========================================");
	}
	
}
