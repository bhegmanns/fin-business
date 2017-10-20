package de.hegmanns.test.utils.rules.logcheck;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogCheckStatementUnitTest {

	@Mock
	private Statement statement;
	@Mock
	private LogCheckAppender logCheckAppender;
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldEvaluateLogMessageExists() throws Throwable {
		String message = "log message exists";
		List<String> expectedMessages = Arrays.asList(message);

		LogCheckStatement logCheckStatement = new LogCheckStatement(statement, expectedMessages, logCheckAppender);
		logCheckStatement.evaluate();

		verify(statement).evaluate();
		verify(logCheckAppender).validate(message);
	}

	@Test
	public void shouldThrowAssertionErrorWhenAppenderValidationFails() throws Throwable {
		String message = "log message DOESN'T exists";
		List<String> expectedMessages = Arrays.asList(message);

		doThrow(new AssertionError()).when(logCheckAppender).validate(message);
		exception.expect(AssertionError.class);

		LogCheckStatement logCheckStatement = new LogCheckStatement(statement, expectedMessages, logCheckAppender);
		logCheckStatement.evaluate();

		verify(statement).evaluate();
		verify(logCheckAppender).validate(message);
	}
}
