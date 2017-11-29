package asenka.mtgfree.tests;

import java.lang.Thread.UncaughtExceptionHandler;

public class AsynchTester {

	private Thread thread;

	private volatile AssertionError exc;

	public AsynchTester(final Runnable runnable) {

		thread = new Thread(new Runnable() {

			public void run() {

				try {
					runnable.run();
				} catch (AssertionError e) {
					exc = e;
				}
			}
		});
	}

	public void start() {

		thread.start();
	}

	public void test() throws InterruptedException {

		thread.join();
		if (exc != null)
			throw exc;
	}
	
	public void setName(String name) {
		
		thread.setName(name);
	}

	public void setUncaughtExceptionHandler(UncaughtExceptionHandler exceptionHandler) {

		thread.setUncaughtExceptionHandler(exceptionHandler);
	}
}
