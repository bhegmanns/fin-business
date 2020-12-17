package de.hegmanns.test.utils.rules.logcheck;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogCheckAppenderUnitTest {

	private LogCheckAppender logCheckAppender;
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		logCheckAppender = new LogCheckAppender();
	}

	@Test
	public void shouldValidateExpectedMessageExists() {
		String message = "message exists";
		ILoggingEvent loggingEvent = mock(ILoggingEvent.class);
		when(loggingEvent.getMessage()).thenReturn(message);

		logCheckAppender.append(loggingEvent);
		logCheckAppender.validate(message);

		verify(loggingEvent).getMessage();
	}

	@Test
	public void shouldThrowAssertionErrorWhenMessageNotContainedInLoggingEvents() {
		String message = "doesn't exist";
		ILoggingEvent loggingEvent = mock(ILoggingEvent.class);
		when(loggingEvent.getMessage()).thenReturn("different log message");

		exception.expect(AssertionError.class);
		exception.expectMessage(String.format("Expected message or part '%s' didn't log", message));
		logCheckAppender.append(loggingEvent);
		logCheckAppender.validate(message);
	}

	@Test
	public void shouldThrowAssertionErrorWhenValidatingWithNoLoggingEvents() {
		String message = "doesn't exist";

		exception.expect(AssertionError.class);
		exception.expectMessage(String.format("Expected message or part '%s' didn't log", message));
		logCheckAppender.validate(message);
	}
}
