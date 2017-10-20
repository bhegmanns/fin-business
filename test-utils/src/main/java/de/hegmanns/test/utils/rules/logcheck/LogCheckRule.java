package de.hegmanns.test.utils.rules.logcheck;

import ch.qos.logback.classic.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author B. Hegmanns
 */
public class LogCheckRule implements TestRule {

	private Logger logger;
	private LogCheckAppender appender;
	private LogCheckStatement logCheckStatement;
	private List<String> expectedMessages = new ArrayList<>();

	private LogCheckRule(Class clazz) {
		this.logger = (Logger) LoggerFactory.getLogger(clazz);
		setUpLogBackAppender();
	}

	/**
	 * Create a new instance of {@link LogCheckRule}.
	 *
	 * @return the new instance
	 */
	public static LogCheckRule instance(Class clazz) {
		return new LogCheckRule(clazz);
	}

	public void addExpectedLog(String string) {
		expectedMessages.add(string);
	}

	@Override
	public Statement apply(Statement base, Description description) {
		logCheckStatement = new LogCheckStatement(base, expectedMessages, appender);
		return logCheckStatement;
	}


	private void setUpLogBackAppender() {
		this.appender = new LogCheckAppender();
		this.logger.addAppender(this.appender);
		this.appender.start();
	}
}
