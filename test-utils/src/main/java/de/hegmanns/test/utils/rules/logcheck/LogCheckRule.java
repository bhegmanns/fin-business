package de.hegmanns.test.utils.rules.logcheck;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.hamcrest.MatcherAssert;

import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.ContextBase;

/**
 * 
 * @author B. Hegmanns
 */
public class LogCheckRule implements TestRule{
	
	private LogCheckStatement logCheckStatement;
	private List<String> expectedMessages = new ArrayList<>();

	@Override
	public Statement apply(Statement base, Description description) {
		logCheckStatement = new LogCheckStatement();
		logCheckStatement.statement = base;
		
		return logCheckStatement;
	}

	/**
	 * Create a new instance of {@link LogCheckRule}.
	 * 
	 * @return the new instance
	 */
	public static LogCheckRule instance() {
		return new LogCheckRule();
	}

	public void addExpectedLog(String string) {
		expectedMessages.add(string);
	}

	private class LogCheckStatement extends Statement{

		private Statement statement;
		
		@Override
		public void evaluate() throws Throwable {
			statement.evaluate();
			throw new AssertionError("LogcheckRule isn't implemented yet");
		}
		
	}
}
