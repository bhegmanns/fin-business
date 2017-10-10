package de.hegmanns.test.utils.rules.logcheck;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A sample test class with some logging (jdk-logging)
 * 
 * @author B. Hegmanns
 */
public class AnyTestClass {
	private static Logger LOG = Logger.getLogger(AnyTestClass.class.getName());

	public void doWhat() {
		LOG.info("I do what");
	}
	
	public void doAnything() {
		LOG.log(Level.WARNING, "I do anything");
	}
}
