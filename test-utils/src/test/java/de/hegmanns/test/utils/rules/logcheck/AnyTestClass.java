package de.hegmanns.test.utils.rules.logcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * A sample test class with some logging (jdk-logging)
 * 
 * @author B. Hegmanns
 */
public class AnyTestClass {
//	private static Logger LOG = Logger.getLogger(AnyTestClass.class.getName());
	private static Logger LOG = LoggerFactory.getLogger(AnyTestClass.class);

	public void doWhat() {
		LOG.info("I do what");
	}
	
	public void doAnything() {
		LOG.warn("I do anything");
	}
}
