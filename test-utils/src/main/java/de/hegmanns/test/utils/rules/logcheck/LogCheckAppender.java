package de.hegmanns.test.utils.rules.logcheck;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import java.util.ArrayList;
import java.util.List;

public class LogCheckAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	private List<ILoggingEvent> loggingEvents = new ArrayList();

	@Override
	protected void append(ILoggingEvent eventObject) {
		loggingEvents.add(eventObject);
	}

	public void validate(String expectedMessage) throws AssertionError {
		loggingEvents.stream()
				.filter(l -> l.getMessage().contains(expectedMessage))
				.findAny()
				.orElseThrow(() -> new AssertionError(
						String.format("Expected message or part '%s' didn't log", expectedMessage)));
	}
}
