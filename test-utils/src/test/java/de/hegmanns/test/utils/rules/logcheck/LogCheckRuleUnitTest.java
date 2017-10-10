package de.hegmanns.test.utils.rules.logcheck;

import java.util.logging.Level;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some example tests, actual more like integration test.
 * Feel free implementing more and more additional  tests.
 * 
 * @author B. Hegmanns
 */
public class LogCheckRuleUnitTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Rule
	public LogCheckRule logCheckRule = LogCheckRule.instance();

	private AnyTestClass anyTestClassInstance = new AnyTestClass();
	
	/**
	 * Test for logging with SLF4J-Logger.
	 */
	@Test
	public void checkLogWithSlf4j() {
		logCheckRule.addExpectedLog("Hello");
		
		Logger logger = LoggerFactory.getLogger(LogCheckRuleUnitTest.class);
		logger.info("Hello");
	}

	/**
	 * Test for JDK-Logger (java.util.logging) 
	 */
	@Test
	public void checkLogWithJdkLogging() {
		logCheckRule.addExpectedLog("Hello");
		
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LogCheckRuleUnitTest.class.getName());
		logger.log(Level.INFO, "Hello");
	}
	
	/**
	 * Test for missing the expected log.
	 */
	@Test
	public void noLog() {
		exception.expect(AssertionError.class);
		logCheckRule.addExpectedLog("I do what");
	}
}
