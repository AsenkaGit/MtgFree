package asenka.mtgfree.tests;

/**
 * Utility class used to create asynchronous test based on several threads. Thanks to this implementation
 * you can test a code in a thread a still be able to ge the AssertionError from JUnit (which is impossible 
 * otherwise).
 * 
 * @author asenka
 * @see AssertionError
 */
public class AsynchTester {

	/**
	 * The thread where containing the code to test. And this code can raise AssertionError exception
	 */
	private Thread thread;

	/**
	 * The exception thrown in the thread to catch and to manage
	 */
	private volatile AssertionError exception;

	/**
	 * Build a new asynchronous tester
	 * @param runnable the code to test
	 */
	public AsynchTester(final Runnable runnable) {

		this.thread = new Thread(new Runnable() {

			public void run() {

				try {
					runnable.run();
				} catch (AssertionError e) {
					AsynchTester.this.exception = e;
				}
			}
		});
	}

	/**
	 * Execute the code to test
	 */
	public void start() {

		thread.start();
	}

	/**
	 * Wait until the code to test is finished or has raised an exception
	 * @throws InterruptedException if a concurrent error occurs
	 * @throws AssertionError if the code has raised an assertion error
	 */
	public void test() throws InterruptedException, AssertionError {

		thread.join();
		if (exception != null) {
			throw exception;
		}	
	}
	
	/**
	 * Set the name of the thread
	 * @param name
	 */
	public void setName(String name) {
		
		this.thread.setName(name);
	}
}
